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

	public void saveCategory(Category category) {
		categoryRepository.save(category);
	}

	public List<Category> getAllCategory(){
		return categoryRepository.findAll();
	}

	public Category getCategoryById(Long id){
		return categoryRepository.findCategoryById(id).orElseThrow(() -> new RuntimeException ("Category is not found!"));
	}

}
