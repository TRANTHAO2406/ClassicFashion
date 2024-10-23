package com.example.classicfashion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.classicfashion.model.Users;
import com.example.classicfashion.service.CartItemService;
import com.example.classicfashion.service.OrderDetailService;
import com.example.classicfashion.service.OrderService;
import com.example.classicfashion.service.ProductService;
import com.example.classicfashion.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {
	private CartItemService cartItemService;
	private OrderService orderService;
	private OrderDetailService orderDetailService;
	private ProductService productService;
	private UserService userService;

	public CheckoutController(CartItemService cartItemService, OrderService orderService,
			OrderDetailService orderDetailService, ProductService productService, UserService userService) {
		super();
		this.cartItemService = cartItemService;
		this.orderService = orderService;
		this.orderDetailService = orderDetailService;
		this.productService = productService;
		this.userService = userService;
	}
 @GetMapping
 public String showCheckOutPage(HttpSession session, Model model) {
	 Users currentUser = userService.getCurrentUser();
     model.addAttribute("user", currentUser);
     
	return "order-confirmation";
	 
 }
 
}
