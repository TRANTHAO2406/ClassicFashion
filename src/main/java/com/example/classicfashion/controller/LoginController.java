package com.example.classicfashion.controller;

import com.example.classicfashion.model.Users;
import com.example.classicfashion.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/loginform")
    public String showLoginForm(Model model){
        model.addAttribute("user", new Users());
        return "login";
    }

    @PostMapping("/loginsuccess")
    public String login(@ModelAttribute("user") Users user, Model model){
        return "login";
    }
}
