package com.example.classicfashion.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.classicfashion.model.OrderDetail;
import com.example.classicfashion.repository.OrderDetailRepository;

@Service
public class OrderDetailService {
	private OrderDetailRepository orderDetailRepository;

	public OrderDetailService(OrderDetailRepository orderDetailRepository) {
		this.orderDetailRepository = orderDetailRepository;
	}

	public void save(OrderDetail OrderDetail) {
		orderDetailRepository.save(OrderDetail);
	}

	public void deleteById(Long Id) {
		orderDetailRepository.deleteById(Id);
	}

	public List<OrderDetail> getAll() {
		return orderDetailRepository.findAll();
	}

	public OrderDetail findById(Long Id) {
		return orderDetailRepository.findById(Id)
				.orElseThrow(() -> new IllegalArgumentException("Khong tim thay OrderDetail"));
	}

}
