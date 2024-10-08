package com.example.classicfashion.service;

import org.springframework.stereotype.Service;

import com.example.classicfashion.model.Image;
import com.example.classicfashion.repository.ImageRepository;

@Service
public class ImageService {
	private final ImageRepository imgService;

	public ImageService(ImageRepository imgService) {
		this.imgService = imgService;
	}

	public void save(Image Image) {
		imgService.save(Image);
	}

	public void deleteById(Long Id) {
		imgService.deleteById(Id);
	}

}
