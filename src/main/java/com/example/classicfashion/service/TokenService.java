package com.example.classicfashion.service;

import com.example.classicfashion.model.PasswordResetToken;

import com.example.classicfashion.model.Token;
import com.example.classicfashion.model.Users;
import com.example.classicfashion.repository.PasswordResetTokenRepository;
import com.example.classicfashion.repository.TokenRepository;
import com.example.classicfashion.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TokenService {
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    public TokenService(TokenRepository tokenRepository, EmailService emailService, UserRepository userRepository, PasswordResetTokenRepository passwordResetTokenRepository) {
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
        this.userRepository = userRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }
    public void generateAndSendToken(Users user){
        String tokenValue = UUID.randomUUID().toString(); //Generate random tokens
        Token token = new Token(tokenValue, user, LocalDateTime.now(),
                                LocalDateTime.now().plusHours(24), false);
        tokenRepository.save(token);

        //
        String subject = "Xác nhận đăng ký tài khoản của bạn";
        String body = "<p>Xin chào, </p>"
                + "<p>Vui lòng nhấp vào liên kết bên dưới để xác thực tài khoản của bạn:</p>"
                +"<p><a href=\"http://localhost:8080/auth/verify/" + tokenValue + "\">Xác nhận đăng ký</a></p>";

        try{
            emailService.sendEmail(user.getEmail(), subject, body);
        } catch (Exception e){
            throw new RuntimeException("Gửi mail thất bại", e);
        }
    }

    public boolean validateToken(String token){
        Token foundToken = tokenRepository.findTokenByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token!!"));
        if (foundToken.getExpiryDate().isBefore(LocalDateTime.now()) || foundToken.isUsed()){
            return false;
        }
        foundToken.setUsed(true); //Mark used tokens
        tokenRepository.save(foundToken);

        Users user = foundToken.getUser();

        user.setStatus("ACTIVE"); //Update user status to ACTIVE
        userRepository.save(user);
        return true;
    }

    public void generatePasswordResetToken(Users user){
        String tokenValue = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken(tokenValue, user, LocalDateTime.now().plusHours(24));
        passwordResetTokenRepository.save(resetToken);

        String subject = "Đặt lại mật khẩu của bạn";
        String body = "<p>Xin chào,</p>"
                + "<p>Vui lòng nhấp vào liên kết bên dưới để đặt lại mật khẩu của bạn:</p>"
                + "<p><a href=\"http://localhost:8080/forgot-password/reset-password/" + tokenValue + "\">Đặt lại mật khẩu</a></p>";
        try{
            emailService.sendEmail(user.getEmail(), subject, body);
        } catch (Exception e){
            throw new RuntimeException("Gửi mail thất bại", e);
        }
    }

    public boolean validatePasswordResetToken(String tokenValue){
        PasswordResetToken foundToken = passwordResetTokenRepository.findPasswordResetTokenByToken(tokenValue)
                .orElseThrow(() -> new RuntimeException("Token không hợp lệ!"));
        if(foundToken.getExpiryDate().isBefore(LocalDateTime.now()) || foundToken.isUsed()){
            return false;
        }
        return true;
    }

    public void markPasswordResetTokenAsUsed(String tokenValue){
        PasswordResetToken foundToken = passwordResetTokenRepository.findPasswordResetTokenByToken(tokenValue)
                .orElseThrow(() -> new RuntimeException("Token không hợp lệ"));
        foundToken.setUsed(true);
        passwordResetTokenRepository.save(foundToken);
    }
}
