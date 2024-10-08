package com.example.classicfashion.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.classicfashion.model.Discount;
import com.example.classicfashion.repository.DiscountRepository;

@Service
public class DiscountService {
	private final DiscountRepository discountRepository;

	public DiscountService(DiscountRepository discountRepository) {
		this.discountRepository = discountRepository;
	}
	public void save(Discount Discount) {
		discountRepository.save(Discount);
	}

	public void deleteById(Long Id) {
		discountRepository.deleteById(Id);
	}

	public List<Discount> getAll() {
		return discountRepository.findAll();
	}

	public Discount findById(Long Id) {
		return discountRepository.findById(Id)
				.orElseThrow(() -> new IllegalArgumentException("Khong tim thay Discount"));
	}	

}
