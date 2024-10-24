package com.example.classicfashion.controller;

import com.example.classicfashion.model.Users;
import com.example.classicfashion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/customer-list")
    public String showCategoryList(Model model,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "5") int size,
                                   @RequestParam(required = false) String keyword) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<Users> usersPage;
        if(keyword != null && !keyword.isEmpty()){
            usersPage = userService.searchUsers(keyword, pageable);
            model.addAttribute("keyword", keyword);

            if(usersPage.isEmpty()){
                model.addAttribute("message", "Không có khách hàng nào với tên: " + keyword);
            }
        } else {
            usersPage = userService.getAllUsers(pageable);
        }
        model.addAttribute("users", usersPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", usersPage.getTotalPages());
        return "admin/customer-list";
    }

    @GetMapping
    public String showProductList(){
        return "admin/product-list";
    }

}
