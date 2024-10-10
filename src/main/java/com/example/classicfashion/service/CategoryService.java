package com.example.classicfashion.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.classicfashion.model.Category;
import com.example.classicfashion.repository.CategoryRepository;

@Service
public class CategoryService {

	private final CategoryRepository categoryRepository;

	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public void save(Category category) {
		categoryRepository.save(category);
	}

	public void deleteById(Long Id) {
		categoryRepository.deleteById(Id);
	}

	public List<Category> findAll() {
		return categoryRepository.findAll();
	}

	public Category findById(Long Id) {
		return categoryRepository.findById(Id)
				.orElseThrow(() -> new IllegalArgumentException("Khong tim thay category"));
	}

}
