package com.example.classicfashion.controller;

import com.example.classicfashion.model.Users;
import com.example.classicfashion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final UserService userService;
    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showHomePage(Model model){
        Users currentUser = userService.getCurrentUser();
        if(currentUser !=null){
            model.addAttribute("userName", currentUser.getUserName());
        } else{
            model.addAttribute("userName", "Khách");
        }
        return "home";
    }
}
