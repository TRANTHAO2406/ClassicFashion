package com.example.classicfashion.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.classicfashion.model.Payment;
import com.example.classicfashion.repository.PaymentRepository;

@Service
public class PaymentService {
	private final PaymentRepository paymentRepository;

	public PaymentService(PaymentRepository paymentRepository) {
		this.paymentRepository = paymentRepository;
	}

	public void save(Payment Payment) {
		paymentRepository.save(Payment);
	}

	public void deleteById(Long Id) {
		paymentRepository.deleteById(Id);
	}

	public List<Payment> getAll() {
		return paymentRepository.findAll();
	}

	public Payment findById(Long Id) {
		return paymentRepository.findById(Id)
				.orElseThrow(() -> new IllegalArgumentException("Khong tim thay Payment"));
	}

	public Payment findPaymentByMethod(String paymentMethod) {
		return paymentRepository.findByPaymentMethod(paymentMethod);
		
	}
	

}
