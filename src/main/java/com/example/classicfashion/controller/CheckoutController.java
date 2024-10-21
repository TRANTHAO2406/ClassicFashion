package com.example.classicfashion.controller;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.classicfashion.model.CartItem;
import com.example.classicfashion.model.Order;
import com.example.classicfashion.model.OrderDetail;
import com.example.classicfashion.service.CartItemService;
import com.example.classicfashion.service.OrderDetailService;
import com.example.classicfashion.service.OrderService;
import com.example.classicfashion.service.ProductService;
import com.example.classicfashion.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

   
    // Xử lý khi người dùng gửi thông tin thanh toán
   
}
