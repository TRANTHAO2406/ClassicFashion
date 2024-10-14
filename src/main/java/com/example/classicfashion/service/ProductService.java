package com.example.classicfashion.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.classicfashion.model.Product;
import com.example.classicfashion.repository.ProductRepository;

@Service
public class ProductService {
	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public void save(Product Product) {
		productRepository.save(Product);
	}

	public List<Product> getAll() {
		return productRepository.findAll();
	}
	public List<Product> getProductsByStatus(int status) {
        return productRepository.findByStatus(status);
    }

	
	public Product findById(Long Id) {
		return productRepository.findById(Id).orElseThrow(() -> new IllegalArgumentException("Khong tim thay Product"));
	}
	
}
