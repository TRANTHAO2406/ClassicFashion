package com.example.classicfashion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.classicfashion.model.Users;
import com.example.classicfashion.service.UserService;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final UserService userService;
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showHomePage(Model model){
        Users currentUser = userService.getCurrentUser();
        if(currentUser != null){
            model.addAttribute("user", currentUser);

            boolean isAdmin = userService.isCurrentUserAdmin();
            model.addAttribute("isAdmin", isAdmin);
        }
        return "home";
    }
}
