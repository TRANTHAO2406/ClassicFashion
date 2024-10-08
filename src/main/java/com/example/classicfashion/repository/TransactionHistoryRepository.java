package com.example.classicfashion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.classicfashion.model.TransactionHistory;
@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {

}
