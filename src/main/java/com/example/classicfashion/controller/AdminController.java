package com.example.classicfashion.controller;

import com.example.classicfashion.service.CategoryService;
import com.example.classicfashion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final CategoryService categoryService;
    @Autowired
    public AdminController(UserService userService, CategoryService categoryService) {
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @GetMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable Long id){
        userService.deleteUserById(id);
        return "redirect:admin/user-list";
    }

    @GetMapping("/user-list")
    public String showUserList(Model model){
        model.addAttribute("user", userService.getAllUsers());
        return "admin/user-list";
    }

    @GetMapping
    public String showProductList(){
        return "admin/product-list";
    }

    @GetMapping("/category-list")
    public String showCategoryList(Model model){
        model.addAttribute("categoryList", categoryService.getAllCategory());
        return "admin/category-list";
    }
}
