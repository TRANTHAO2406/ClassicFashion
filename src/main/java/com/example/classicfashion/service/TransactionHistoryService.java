package com.example.classicfashion.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.classicfashion.model.TransactionHistory;
import com.example.classicfashion.repository.TransactionHistoryRepository;

@Service
public class TransactionHistoryService {
	private final TransactionHistoryRepository transactionHistoryRepository;

	public TransactionHistoryService(TransactionHistoryRepository transactionHistoryRepository) {
		this.transactionHistoryRepository = transactionHistoryRepository;
	}

	public void save(TransactionHistory TransactionHistory) {
		transactionHistoryRepository.save(TransactionHistory);
	}

	public void deleteById(Long Id) {
		transactionHistoryRepository.deleteById(Id);
	}

	public List<TransactionHistory> getAll() {
		return transactionHistoryRepository.findAll();
	}

	public TransactionHistory findById(Long Id) {
		return transactionHistoryRepository.findById(Id)
				.orElseThrow(() -> new IllegalArgumentException("Khong tim thay TransactionHistory"));
	}
}
