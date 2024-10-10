package com.example.classicfashion.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.classicfashion.model.Category;
import com.example.classicfashion.model.Color;
import com.example.classicfashion.model.Image;
import com.example.classicfashion.model.Product;
import com.example.classicfashion.model.ProductDetail;
import com.example.classicfashion.model.Size;
import com.example.classicfashion.service.CategoryService;
import com.example.classicfashion.service.ColorService;
import com.example.classicfashion.service.ImageService;
import com.example.classicfashion.service.ProductService;
import com.example.classicfashion.service.SizeService;

@Controller
@RequestMapping("/product")
public class ProductController {

	private final ProductService productService;
	private final CategoryService categoryService;
	private final ColorService colorService;
	private final SizeService sizeService;
	private final ImageService imageService;

	private final String uploadDir = "uploads/";

	public ProductController(ProductService productService, CategoryService categoryService, ColorService colorService,
			SizeService sizeService, ImageService imageService) {
		this.productService = productService;
		this.categoryService = categoryService;
		this.colorService = colorService;
		this.sizeService = sizeService;
		this.imageService = imageService;
	}

	@GetMapping("/view")
	public String listProduct(Model model) {
		model.addAttribute("products", productService.getAll());
		return "product-view";
	}

	@GetMapping("/add")
	public String showAddProductForm(Model model) {
		Product product = new Product();
		ProductDetail defaultDetail = new ProductDetail();
		product.setProductDetails(new ArrayList<>(List.of(defaultDetail)));
		model.addAttribute("product", product);
		model.addAttribute("categories", categoryService.findAll());
		model.addAttribute("colors", colorService.findAll());
		model.addAttribute("sizes", sizeService.findAll());
		return "addProduct";
	}

	@PostMapping("/add")
	public String addProduct(@ModelAttribute Product product,
							@RequestParam("newImages") MultipartFile file,
							@RequestParam(required = false) String newCategory,
							@RequestParam(required = false) String newColor,
							@RequestParam(required = false) String newSize, 
							Model model) {
		Image image = null;
		if (!file.isEmpty()) {
			try {
				String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
				File uploadDirPath = new File(uploadDir);
				if (!uploadDirPath.exists()) {
					uploadDirPath.mkdirs();
				}
				Path filePath = Paths.get(uploadDir + fileName);
				Files.write(filePath, file.getBytes());

				image = new Image();
				image.setImgLink("/" + uploadDir + fileName); // Đường dẫn tương đối
				imageService.save(image); // Lưu vào cơ sở dữ liệu

			} catch (IOException e) {
				e.printStackTrace();
				model.addAttribute("error", "Có lỗi khi tải lên hình ảnh");
				return "addProduct";
			}
		}

		if (newCategory != null && !newCategory.trim().isEmpty()) {
			Category category = new Category();
			category.setCategoryName(newCategory);
			categoryService.save(category);
			product.setCategory(category);
		}

		if (newColor != null && !newColor.trim().isEmpty()) {
			Color color = new Color();
			color.setColorName(newColor);
			colorService.save(color);
			for (ProductDetail detail : product.getProductDetails()) {
				if (detail.getColorId() == null || detail.getColorId().getId() == null) {
					detail.setColorId(color);
				}
			}
		}

		if (newSize != null && !newSize.trim().isEmpty()) {
			Size size = new Size();
			size.setSizeName(newSize);
			sizeService.save(size);
			for (ProductDetail detail : product.getProductDetails()) {
				if (detail.getSizeId() == null || detail.getSizeId().getId() == null) {
					detail.setSizeId(size);
				}
			}
		}

		for (ProductDetail detail : product.getProductDetails()) {
			detail.setProductId(product);
			if (image != null) {
				detail.setImageId(image);
			}
		}

		LocalDate currentDate = LocalDate.now();
		product.setCreatedDate(currentDate);
		productService.save(product);
		return "redirect:/product/view";
	}

}
