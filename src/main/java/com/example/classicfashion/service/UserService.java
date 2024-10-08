package com.example.classicfashion.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.classicfashion.model.User;
import com.example.classicfashion.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void save(User User) {
		userRepository.save(User);
	}

	public void deleteById(Long Id) {
		userRepository.deleteById(Id);
	}

	public List<User> getAll() {
		return userRepository.findAll();
	}

	public User findById(Long Id) {
		return userRepository.findById(Id).orElseThrow(() -> new IllegalArgumentException("Khong tim thay User"));
	}
}
