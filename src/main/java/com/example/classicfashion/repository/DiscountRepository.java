package com.example.classicfashion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.classicfashion.model.Discount;
@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {

}
