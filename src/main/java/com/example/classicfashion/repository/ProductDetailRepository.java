package com.example.classicfashion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.classicfashion.model.ProductDetail;
import com.example.classicfashion.model.ProductDetailId;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, ProductDetailId> {
}
