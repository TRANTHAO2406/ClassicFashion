package com.example.classicfashion.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.classicfashion.model.Order;
import com.example.classicfashion.model.Users;
import com.example.classicfashion.repository.OrderRepository;

@Service
public class OrderService {
	private final OrderRepository orderRepository;

	public OrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	public Order save(Order Order) {
		return orderRepository.save(Order);
	}

	public void deleteById(Long Id) {
		orderRepository.deleteById(Id);
	}

	public List<Order> getAll() {
		return orderRepository.findAll();
	}

	public Order findById(Long Id) {
		return orderRepository.findById(Id).orElseThrow(() -> new IllegalArgumentException("Khong tim thay Order"));
	}
	public List<Order> getByUser(Users users){
		return orderRepository.findByUser(users);
		
	}

}
