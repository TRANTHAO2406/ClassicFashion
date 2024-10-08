package com.example.classicfashion.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.classicfashion.model.UserRole;
import com.example.classicfashion.model.UserRoleId;
import com.example.classicfashion.repository.UserRoleRepository;

@Service
public class UserRoleService {
	private final UserRoleRepository userRoleRepository;

	public UserRoleService(UserRoleRepository userRoleRepository) {
		this.userRoleRepository = userRoleRepository;
	}

	public void save(UserRole UserRole) {
		userRoleRepository.save(UserRole);
	}

	public void deleteById(UserRoleId Id) {
		userRoleRepository.deleteById(Id);
	}

	public List<UserRole> getAll() {
		return userRoleRepository.findAll();
	}

	public UserRole findById(UserRoleId Id) {
		return userRoleRepository.findById(Id)
				.orElseThrow(() -> new IllegalArgumentException("Khong tim thay UserRole"));
	}
}
