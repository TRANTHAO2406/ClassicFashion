package com.example.classicfashion.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.example.classicfashion.model.Role;
import com.example.classicfashion.model.UserRole;
import com.example.classicfashion.repository.RoleRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.classicfashion.model.Users;
import com.example.classicfashion.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}
	private static final BCryptPasswordEncoder passwordEcorder = new BCryptPasswordEncoder();
	public void registerUser(Users user, String role){
		System.out.println("Mật khẩu trước mã hóa" + user.getPassword());
		user.setPassword(passwordEncoder.encode(user.getPassword()));	//Encrypt password before saving
		System.out.println("Mật khẩu sau mã hóa" + user.getPassword());
		user.setCreatedDate(LocalDate.now());	//set createdDate
		user.setStatus("PENDING");	//set status

		Users savedUser = userRepository.save(user);	//Save user to database
		//Find role by name and assign to user
		Role userRole = roleRepository.findByRole(role).orElseThrow(() -> new RuntimeException("Role not found!!"));

		UserRole userRoleModel = new UserRole(savedUser, userRole);
		savedUser.getUserRoles().add(userRoleModel);
	}

	public Users findbyEmail(String email){
		return userRepository.findByEmail(email).orElse(null);
	}

	public void updatePassword(Users user, String newPassword){
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);
	}

	public Boolean doPasswordMatch(String rawPassword, String encodedPassword){
		return passwordEcorder.matches(rawPassword, encodedPassword);
	}
}
