package com.example.classicfashion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.classicfashion.model.Size;
import com.example.classicfashion.repository.SizeRepository;

@Service
public class SizeService {
	@Autowired
	SizeRepository sizeRepository;
	

	public List<Size> findAll() {
		return sizeRepository.findAll();
	}


	public void save(Size size) {
		sizeRepository.save(size);
	}


	public Size findById(Long id) {
		return sizeRepository.findById(id).orElse(null);
	}


	public Size findByName(String sizeName) {
		return sizeRepository.findBySizeName(sizeName);
	}

}
