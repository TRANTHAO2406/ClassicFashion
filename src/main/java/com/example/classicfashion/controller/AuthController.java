package com.example.classicfashion.controller;

import com.example.classicfashion.model.Users;
import com.example.classicfashion.service.TokenService;
import com.example.classicfashion.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final TokenService tokenService;
    @Autowired
    public AuthController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }
    @GetMapping("/register")
    public String showRegisterForm(Model model){
        model.addAttribute("user", new Users());
        return "register-login/register";
    }

    @PostMapping("/registersuccess")
    public String register(@Valid @ModelAttribute("user") Users user, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "register-login/register";
        }
        userService.registerUser(user, "USER");
        tokenService.generateAndSendToken(user);

        return "register-login/register-success";
    }

    @GetMapping("/verify/{token}")
    public String verify(@PathVariable String token){
        if(tokenService.validateToken(token)){
            return "register-login/verify-success";
        }
        return "register-login/verify-failure";
    }
}
