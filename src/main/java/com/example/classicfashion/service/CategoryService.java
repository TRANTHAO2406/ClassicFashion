package com.example.classicfashion.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.classicfashion.model.Category;
import com.example.classicfashion.repository.CategoryRepository;

@Service
public class CategoryService {

	private final CategoryRepository categoryRepository;

	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public List<Category> getAllCategory(){
		return categoryRepository.findAll();
	}

	public Page<Category> getAllCategory(Pageable pageable){
		return categoryRepository.findAll(pageable);
	}

	public Page<Category> searchCategories(String keyword, Pageable pageable){
		return categoryRepository.searchCategoriesByCategoryName(keyword, pageable);
	}
	public Category getCategoryById(Long id){
		return categoryRepository.findCategoryById(id).orElseThrow(() -> new RuntimeException ("Category is not found!"));
	}

	public void saveCategory(Category category){
		if(category.getId() != null){
			Category exitingCategory = categoryRepository.findCategoryById(category.getId())
					.orElseThrow(() -> new RuntimeException("Category is not found!") );
			category.setCreatedDate(exitingCategory.getCreatedDate());
			category.setUpdatedDate(LocalDate.now());
		} else {
			category.setCreatedDate(LocalDate.now());
		}
		categoryRepository.save(category);
	}
}
