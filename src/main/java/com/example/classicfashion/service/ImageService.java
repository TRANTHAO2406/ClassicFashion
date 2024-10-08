package com.example.classicfashion.service;

import com.example.classicfashion.repository.ImageRepository;
import org.springframework.stereotype.Service;

import com.example.classicfashion.model.Image;
import com.example.classicfashion.repository.ImageRepository;

@Service
public class ImageService {
	private final ImageRepository imageRepository;

	public ImageService(ImageRepository imageRepository) {
		this.imageRepository = imageRepository;
	}

	public void save(Image Image) {
		imageRepository.save(Image);
	}

	public void deleteById(Long Id) {
		imageRepository.deleteById(Id);
	}

}
