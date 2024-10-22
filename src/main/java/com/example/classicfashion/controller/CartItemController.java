package com.example.classicfashion.controller;

import java.io.IOException;
import java.time.LocalDate;
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
import com.example.classicfashion.model.Order;
import com.example.classicfashion.model.OrderDetail;
import com.example.classicfashion.model.ProductDetail;
import com.example.classicfashion.model.Size;
import com.example.classicfashion.model.Users;
import com.example.classicfashion.service.CartItemService;
import com.example.classicfashion.service.ColorService;
import com.example.classicfashion.service.OrderDetailService;
import com.example.classicfashion.service.OrderService;
import com.example.classicfashion.service.ProductDetailService;
import com.example.classicfashion.service.ProductService;
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
	private OrderService orderService;
	private OrderDetailService orderDetailService;
	private ProductService productService;
	private UserService userService;

	public CartItemController(CartItemService cartItemService, ProductDetailService productDetailService,
			ColorService colorService, SizeService sizeService, OrderService orderService,
			OrderDetailService orderDetailService, ProductService productService,UserService userService) {
		this.cartItemService = cartItemService;
		this.productDetailService = productDetailService;
		this.colorService = colorService;
		this.sizeService = sizeService;
		this.orderService = orderService;
		this.orderDetailService = orderDetailService;
		this.productService = productService;
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

		Order order = new Order();

		Users currentUser = userService.getCurrentUser();
		if (currentUser != null) {
			order.setUser(currentUser);
		}
		order.setOrderDate(LocalDate.now());
		order.setShippingPrice(5.0);
		double totalAmount = 0.0;

		order.setStatus("Pending");
		order.setUpdatedDate(LocalDate.now());
		order.setTotalPrice(totalAmount + order.getShippingPrice());
		orderService.save(order);
		for (CartItem cartItem : cartItemsList) {
			if (cartItem.getIsSelected()) {
				OrderDetail orderDetail = new OrderDetail();
				orderDetail.setOrder(order);
				orderDetail.setProduct(productService.findById(cartItem.getProductID()));
				orderDetail.setQuantity(cartItem.getQuantity());
				orderDetail.setPrice(cartItem.getPrice());
				orderDetail.setSubTotal(cartItem.getQuantity() * cartItem.getPrice());

				orderDetailService.save(orderDetail);

				totalAmount += orderDetail.getSubTotal();
			}
		}

		order.setTotalPrice(totalAmount + order.getShippingPrice());
		orderService.save(order);

		cartItemService.removeSelectedItems(cartItemsList);

		model.addAttribute("message", "Your order has been placed successfully!");
		model.addAttribute("order", order);
		System.out.println(order.toString());
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
