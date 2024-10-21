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
@RequestMapping("/admin/user")
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
    public String saveUser(@ModelAttribute Users user){
        userService.saveUser(user);
        return "redirect:/admin/user";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id){
        userService.deleteUserById(id);
        return "redirect:/user";
    }
    @GetMapping
    public String showUserList(Model model){
        model.addAttribute("user", userService.getAllUsers());
        return "user/user-list";
    }
}
