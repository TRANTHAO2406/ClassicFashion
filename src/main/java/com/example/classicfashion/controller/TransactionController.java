package com.example.classicfashion.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.classicfashion.model.Order;
import com.example.classicfashion.model.Users;
import com.example.classicfashion.service.OrderService;
import com.example.classicfashion.service.UserService;

@Controller
@RequestMapping("/transaction")
public class TransactionController {
	private final UserService userService;
	private final OrderService orderService;

	public TransactionController(UserService userService, OrderService orderService) {
		this.userService = userService;
		this.orderService = orderService;
	}

	@GetMapping
	public String purchaseHistoryView(Model model) {

		Users currentUser = userService.getCurrentUser();
		model.addAttribute("user", currentUser);
		
		List<Order> orders = orderService.getByUser(currentUser);
		orders.sort((o1, o2) -> o2.getId().compareTo(o1.getId()));
		 model.addAttribute("oders", orders);

		return "purchase-history";

	}

}
