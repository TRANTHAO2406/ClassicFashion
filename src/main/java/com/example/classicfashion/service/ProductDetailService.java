package com.example.classicfashion.service;

import com.example.classicfashion.model.ProductDetail;
import com.example.classicfashion.repository.ProductDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductDetailService {

    @Autowired
    private ProductDetailRepository productDetailRepository;

    // Lưu một ProductDetail mới
    public ProductDetail save(ProductDetail productDetail) {
        return productDetailRepository.save(productDetail);
    }

    // Lấy tất cả ProductDetail
    public List<ProductDetail> findAll() {
        return productDetailRepository.findAll();
    }

    // Xóa một ProductDetail theo ID
//    public void delete(Long id) {
//        productDetailRepository.deleteById(id);
//    }

    // Thêm các phương thức khác tùy theo nhu cầu của bạn
}