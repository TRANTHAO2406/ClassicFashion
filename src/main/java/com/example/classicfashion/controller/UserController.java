package com.example.classicfashion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.classicfashion.model.Users;
import com.example.classicfashion.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/edit/{id}")
    public String showEditUserForm(@PathVariable Long id, Model model){
        Users user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user/user-edit";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") Users user){
        System.out.println(user.toString());
        userService.saveUser(user);
        return "redirect:/user/edit/" + user.getId();
    }

}
