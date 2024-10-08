package com.example.classicfashion.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.classicfashion.model.Role;
import com.example.classicfashion.repository.RoleRepository;

@Service
public class RoleService {
	private final RoleRepository roleRepository;

	public RoleService(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	public void save(Role Role) {
		roleRepository.save(Role);
	}

	public void deleteById(Long Id) {
		roleRepository.deleteById(Id);
	}

	public List<Role> getAll() {
		return roleRepository.findAll();
	}

	public Role findById(Long Id) {
		return roleRepository.findById(Id).orElseThrow(() -> new IllegalArgumentException("Khong tim thay Role"));
	}
}
