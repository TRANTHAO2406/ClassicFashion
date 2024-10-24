package com.example.classicfashion.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.classicfashion.model.CartItem;
import com.example.classicfashion.model.Color;
import com.example.classicfashion.model.ProductDetail;
import com.example.classicfashion.model.Size;
import com.example.classicfashion.model.Users;
import com.example.classicfashion.service.CartItemService;
import com.example.classicfashion.service.ColorService;
import com.example.classicfashion.service.ProductDetailService;
import com.example.classicfashion.service.SizeService;
import com.example.classicfashion.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/shopping-cart")
public class CartItemController {

	private CartItemService cartItemService;
	private ProductDetailService productDetailService;
	private ColorService colorService;
	private SizeService sizeService;
	private UserService userService;

	public CartItemController(CartItemService cartItemService, ProductDetailService productDetailService,
			ColorService colorService, SizeService sizeService,UserService userService) {
		this.cartItemService = cartItemService;
		this.productDetailService = productDetailService;
		this.colorService = colorService;
		this.sizeService = sizeService;
		this.userService = userService;
	}

	@GetMapping("/view")
	public String viewCart(Model model, HttpSession session) {
		Users currentUser = userService.getCurrentUser();
        model.addAttribute("user", currentUser);
		Collection<CartItem> cartItems = cartItemService.getAllItems();
		model.addAttribute("cart", cartItems);

		if (cartItems.isEmpty()) {
			model.addAttribute("message", "Your cart is empty!");
		} else {
			model.addAttribute("totalAmount", cartItemService.getAmount());
			model.addAttribute("totalItems", cartItemService.getCount());
		}

		return "cart-item";
	}

	// add product from cart
	@PostMapping("/add")
	public String addToCart(@RequestParam Long productId,
							@RequestParam String color, 
							@RequestParam String size,
							@RequestParam int quantity,
							@RequestParam String action, 
							HttpSession session, Model model) {
		Color colorID = colorService.findByName(color);
		Size sizeID = sizeService.findByName(size);
		ProductDetail productDetail = productDetailService.findByProductAndColorAndSize(productId, colorID.getId(),
				sizeID.getId());
		if (productDetail == null) {
			model.addAttribute("error", "Product not found!");
			return "redirect:/product/view";
		}

		// create object CartItem to ProductDetail
		CartItem cartItem = new CartItem(productDetail.getProductId().getId(),
				productDetail.getProductId().getProductName(), productDetail.getPrice().doubleValue(),
				productDetail.getImages().isEmpty() ? null : productDetail.getImages().get(0).getImgLink(),
				productDetail.getColorId().getColorName(), productDetail.getSizeId().getSizeName(), quantity, true);

		cartItemService.add(cartItem);

		model.addAttribute("cart", cartItemService.getAllItems());
		model.addAttribute("totalAmount", cartItemService.getAmount());
		model.addAttribute("totalItems", cartItemService.getCount());

		return "buy".equals(action) ? "redirect:/shopping-cart/view" : "redirect:/product/view";
	}

	// Remove product
	@GetMapping("/remove")
	public String removeFromCart(@RequestParam Long productId,
								@RequestParam String colorId,
								@RequestParam String sizeId, 
								Model model) {

		cartItemService.remove(productId, colorId, sizeId);

		model.addAttribute("cart", cartItemService.getAllItems());
		model.addAttribute("totalAmount", cartItemService.getAmount());
		model.addAttribute("totalItems", cartItemService.getCount());
		return "redirect:/shopping-cart/view";
	}

	
	
	
	@PostMapping("/submit")
	public String processCheckout(@RequestParam String cartItems, Model model, HttpSession session) {
		Collection<CartItem> cartItemsList = parseCartItems(cartItems);
		if (cartItemsList.isEmpty()) {
			model.addAttribute("message", "Your cart is empty!");
			return "redirect:/shopping-cart/view";
		}
		session.setAttribute("cartItems", cartItemsList);
		return "redirect:/checkout";
	}
	

	private Collection<CartItem> parseCartItems(String cartItemsJson) {
		ObjectMapper objectMapper = new ObjectMapper();
		Collection<CartItem> cartItems = new ArrayList<>();
		try {
			cartItems = objectMapper.readValue(cartItemsJson, new TypeReference<Collection<CartItem>>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cartItems;
	}

}
