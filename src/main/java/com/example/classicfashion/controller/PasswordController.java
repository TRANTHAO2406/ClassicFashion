package com.example.classicfashion.controller;

import com.example.classicfashion.model.PasswordResetToken;
import com.example.classicfashion.model.Users;
import com.example.classicfashion.repository.PasswordResetTokenRepository;
import com.example.classicfashion.service.TokenService;
import com.example.classicfashion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/forgot-password")
public class PasswordController {
    private final UserService userService;
    private final TokenService tokenService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    @Autowired
    public PasswordController(UserService userService, TokenService tokenService, PasswordResetTokenRepository passwordResetTokenRepository) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    @GetMapping("/forgot-password-form")
    public String showForgotPasswordFrom(){
        return "register-login/forgot-password";
    }

    @PostMapping("/forgot-password-form")
    public String processForgotPassword(@RequestParam("email") String email, Model model){
        Users user = userService.findbyEmail(email);
        if(user == null){
            model.addAttribute("error", "Email không tồn tại!");
            return "register-login/forgot-password";
        }

        tokenService.generatePasswordResetToken(user);
        model.addAttribute("message", "Hãy kiểm tra email của bạn để đặt lại mật khẩu!");
        return "register-login/forgot-password";
    }

    @GetMapping("/reset-password/{token}")
    public String showResetPasswordForm(@PathVariable String token, Model model){
        if(!tokenService.validatePasswordResetToken(token)){
            model.addAttribute("error", "Token không hợp lệ hoặc đã hết hạn!");
            return "register-login/reset-password";
        }
        model.addAttribute("token", token);
        return "register-login/reset-password";
    }

    @PostMapping("/reset-password")
    public String processResetPassword(@RequestParam("token") String token,
                                       @RequestParam("password") String newPassword, Model model){
        if(!tokenService.validatePasswordResetToken(token)){
            model.addAttribute("error", "Token không hợp ệ hoặc đã hết hạn!");
            return "register-login/reset-password";
        }
        PasswordResetToken foundToken = passwordResetTokenRepository.findPasswordResetTokenByToken(token).orElse(null);
        assert foundToken != null;
        Users user = foundToken.getUser();
        userService.updatePassword(user, newPassword);

        tokenService.markPasswordResetTokenAsUsed(token); //mark used token
        model.addAttribute("message", "Mật khẩu đã được đặt lại thành công!");
        return "register-login/reset-password-success";
    }
}
