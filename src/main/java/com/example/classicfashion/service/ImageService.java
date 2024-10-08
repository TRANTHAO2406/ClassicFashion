package com.example.classicfashion.service;

import org.springframework.stereotype.Service;

import com.example.classicfashion.model.Image;

@Service
public class ImageService {
	private final ImageService imgService;

	public ImageService(ImageService imgService) {
		this.imgService = imgService;
	}
	public void save(Image Image) {
		imgService.save(Image);
	}

	public void deleteById(Long Id) {
		imgService.deleteById(Id);
	}
	

}
