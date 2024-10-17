package com.example.classicfashion.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.classicfashion.service.CartItemService;
import com.example.classicfashion.service.ColorService;
import com.example.classicfashion.service.ProductDetailService;
import com.example.classicfashion.service.SizeService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/shopping-cart")
public class CartItemController {

	@Autowired
	private CartItemService cartItemService;

	@Autowired
	private ProductDetailService productDetailService;

	@Autowired
	private ColorService colorService;
	@Autowired
	private SizeService sizeService;

	// Thêm sản phẩm vào giỏ hàng
	@PostMapping("/add")
	public String addToCart(@RequestParam Long productId, 
			@RequestParam String color,
			@RequestParam String size,
			@RequestParam int quantity,
			@RequestParam String action,
			HttpSession session,
			Model model) {
		Color colorID = colorService.findByName(color);
		Size sizeID = sizeService.findByName(size);
		ProductDetail productDetail = productDetailService.findByProductAndColorAndSize(productId, colorID.getId(),
				sizeID.getId());
		if (productDetail == null) {
			model.addAttribute("error", "Product not found!");
			return "redirect:/product/view";
		}

		// Tạo đối tượng CartItem từ ProductDetail
		CartItem cartItem = new CartItem(productDetail.getProductId().getId(),
				productDetail.getProductId().getProductName(),
				productDetail.getPrice().doubleValue(),
				productDetail.getImages().isEmpty() ? null : productDetail.getImages().get(0).getImgLink(),
				productDetail.getColorId().getColorName(), productDetail.getSizeId().getSizeName(), quantity, true );

		cartItemService.add(cartItem);

		model.addAttribute("cart", cartItemService.getAllItems());
		model.addAttribute("totalAmount", cartItemService.getAmount());
		model.addAttribute("totalItems", cartItemService.getCount());
		

		return "buy".equals(action) ? "redirect:/shopping-cart/view" : "redirect:/product/view";
	}

	@GetMapping("/view")
	public String viewCart(Model model) {
		model.addAttribute("cart", cartItemService.getAllItems());
		model.addAttribute("totalAmount", cartItemService.getAmount());
		model.addAttribute("totalItems", cartItemService.getCount());
		return "cart-item"; // Tên file HTML hiển thị giỏ hàng
	}

	@PostMapping("/update")
	public String updateCart(@RequestParam Long productId, 
							@RequestParam String color,
							@RequestParam String size, 
							@RequestParam int quantity, 
							Model model) {

		cartItemService.update(productId, color, size, quantity);

		model.addAttribute("cart", cartItemService.getAllItems());
		model.addAttribute("totalAmount", cartItemService.getAmount());
		model.addAttribute("totalItems", cartItemService.getCount());
		return "redirect:/shopping-cart/view";
	}

	// Xóa sản phẩm khỏi giỏ hàng
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
}
