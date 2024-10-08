package com.example.classicfashion.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/product")
public class ProductContronller {

	@GetMapping("/view")
	public String listProduct() {
		return "prodcuts-view";
	}
	
}
