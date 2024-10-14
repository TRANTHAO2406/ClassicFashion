package com.example.classicfashion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.classicfashion.model.Color;
import com.example.classicfashion.repository.ColorRepository;

@Service
public class ColorService {

	@Autowired
	ColorRepository colorRepository;
	
	public List<Color> findAll() {
		return colorRepository.findAll();
	}

	public void save(Color color) {
		colorRepository.save(color);
		
	}

	public Color findById(Long id) {
		return colorRepository.findById(id).orElse(null);
	}

	public Color findByName(String colorName) {
		  return colorRepository.findByColorName(colorName);
	}
	

}
