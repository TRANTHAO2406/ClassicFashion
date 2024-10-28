package com.example.classicfashion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.classicfashion.model.Color;
import com.example.classicfashion.model.Product;
import com.example.classicfashion.model.ProductDetail;
import com.example.classicfashion.model.Size;
import com.example.classicfashion.repository.ProductDetailRepository;

@Service
public class ProductDetailService {

	@Autowired
	private ProductDetailRepository productDetailRepository;

	public ProductDetail save(ProductDetail productDetail) {
		return productDetailRepository.save(productDetail);
	}

	public List<ProductDetail> findAll() {
		return productDetailRepository.findAll();
	}

	public ProductDetail findByProductAndColorAndSize(Long productId, Long colorId, Long sizeId) {
		Product product = new Product();
		product.setId(productId);

		Color color = new Color();
		color.setId(colorId);

		Size size = new Size();
		size.setId(sizeId);

		return productDetailRepository.findByProductIdAndColorIdAndSizeId(product, color, size);
	}
	public List<ProductDetail> findByProductId(Product productId){
		return productDetailRepository.findByProductId(productId);
	}

}