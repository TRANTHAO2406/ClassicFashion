package com.example.classicfashion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.classicfashion.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
