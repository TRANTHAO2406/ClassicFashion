package com.example.classicfashion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.classicfashion.model.Product;
import com.example.classicfashion.service.ProductDetailService;
import com.example.classicfashion.service.ProductService;

@Controller
@RequestMapping("/productDetails")
public class ProductDetailController {
	private final ProductService productService;
	private final ProductDetailService productDetailService;
	
	 

	public ProductDetailController(ProductService productService, ProductDetailService productDetailService) {
		this.productService = productService;
		this.productDetailService = productDetailService;
	}



	@GetMapping("/{id}")
	public String productDetailView(@PathVariable("id") Long Id, Model model) {
		
		Product product = productService.findById(Id);
		model.addAttribute("product",product);
		return "productDetail";
		
	}
	
}
