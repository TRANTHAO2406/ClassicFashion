package com.example.classicfashion.controller;


import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.classicfashion.model.Order;
import com.example.classicfashion.model.OrderDetail;
import com.example.classicfashion.model.Users;
import com.example.classicfashion.service.OrderDetailService;
import com.example.classicfashion.service.OrderService;
import com.example.classicfashion.service.UserService;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/orderDetails")
public class OrderDetailController {

	private final OrderService orderService;
	private final OrderDetailService orderDetailService;
	private final UserService userService;
	public OrderDetailController(OrderService orderService, OrderDetailService orderDetailService, UserService userService) {
		this.orderService = orderService;
		this.orderDetailService = orderDetailService;
		this.userService = userService;
	}
	@GetMapping("/{id}")
	public String getOrderDetail(@PathVariable Long id, Model model, HttpSession session) {
		Users currentUser = userService.getCurrentUser();
		model.addAttribute("user", currentUser);
		
		Order order = orderService.findById(id);
		if (order == null) {
			model.addAttribute("errorMessage", "Order not found.");
			return "404"; // Redirect to PageNotFound page
		}
		
		// Check if the order belongs to the logged-in user
	    if (!order.getUser().getId().equals(currentUser.getId())) {
	        model.addAttribute("errorMessage", "You do not have permission to view this order.");
	        return "403"; // Redirect to permission-denied page
	    }
		
		List<OrderDetail> orderDetails = orderDetailService.getOrderDetailsByOrderId(id);
		
		model.addAttribute("order", order);
		model.addAttribute("orderDetails", orderDetails);
		return "orderDetails";
	}
	
}
