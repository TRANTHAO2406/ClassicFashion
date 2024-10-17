package com.example.classicfashion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.classicfashion.model.Color;
import com.example.classicfashion.model.Product;
import com.example.classicfashion.model.ProductDetail;
import com.example.classicfashion.model.ProductDetailId;
import com.example.classicfashion.model.Size;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, ProductDetailId> {
	ProductDetail findByProductIdAndColorIdAndSizeId(Product productId, Color colorId, Size sizeId);
}
