package com.example.classicfashion.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.classicfashion.model.Token;
import com.example.classicfashion.model.Users;
import com.example.classicfashion.repository.TokenRepository;
import com.example.classicfashion.repository.UserRepository;

@Service
public class TokenService {
	private final TokenRepository tokenRepository;
	private final EmailService emailService;
	private final UserRepository userRepository;

	public TokenService(TokenRepository tokenRepository, EmailService emailService, UserRepository userRepository) {
		this.tokenRepository = tokenRepository;
		this.emailService = emailService;
		this.userRepository = userRepository;
	}

	public void generateAndSendToken(Users user) {
		String tokenValue = UUID.randomUUID().toString(); // Generate random tokens
		Token token = new Token(tokenValue, user, LocalDateTime.now(), LocalDateTime.now().plusHours(24), false);
		tokenRepository.save(token);

		//
		String subject = "Xác nhận đăng ký tài khoản của bạn";
		String body = "<p>Xin chào, </p>" + "<p>Vui lòng nhấp vào liên kết bên dưới để xác thực tài khoản của bạn:</p>"
				+ "<p><a href=\"http://localhost:8080/auth/verify/" + tokenValue + "\">Xác nhận đăng ký</a></p>";

		try {
			emailService.sendEmail(user.getEmail(), subject, body);
		} catch (Exception e) {
			throw new RuntimeException("Gửi mail thất bại", e);
		}
	}

	public boolean validateToken(String token) {
		Token foundToken = tokenRepository.findTokenByToken(token)
				.orElseThrow(() -> new RuntimeException("Invalid token!!"));
		if (foundToken.getExpiryDate().isBefore(LocalDateTime.now()) || foundToken.getUsed()) {
			return false;
		}
		foundToken.setUsed(true); // Mark used tokens
		tokenRepository.save(foundToken);

		Users user = foundToken.getUser();

		user.setStatus("ACTIVE"); // Update user status to ACTIVE
		userRepository.save(user);
		return true;
	}
}
