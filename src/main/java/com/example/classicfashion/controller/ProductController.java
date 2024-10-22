package com.example.classicfashion.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.classicfashion.model.Color;
import com.example.classicfashion.model.Image;
import com.example.classicfashion.model.Product;
import com.example.classicfashion.model.ProductDetail;
import com.example.classicfashion.model.Size;
import com.example.classicfashion.model.Users;
import com.example.classicfashion.service.CategoryService;
import com.example.classicfashion.service.ColorService;
import com.example.classicfashion.service.ImageService;
import com.example.classicfashion.service.ProductService;
import com.example.classicfashion.service.SizeService;
import com.example.classicfashion.service.UserService;

@Controller
@RequestMapping("/product")
public class ProductController {

	private final ProductService productService;
	private final CategoryService categoryService;
	private final ColorService colorService;
	private final SizeService sizeService;
	private final ImageService imageService;

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
		List<Product> products = productService.getProductsByStatus(1);
		products.sort(Comparator.comparing(Product::getId).reversed());
		model.addAttribute("products", products);
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
//			@RequestParam Map<String, MultipartFile[]> newImages,
			@RequestParam ("newImages")MultipartFile[] newImages,
			@RequestParam List<String> newColors, 
			@RequestParam List<String> newSizes,
			@RequestParam List<BigDecimal> prices,
			@RequestParam List<Integer> quantities,
			@RequestParam(required = false) String newCategory) {
//		  System.out.println("Parameters: " + params);
		System.out.println("newImages content:"+ newImages);
//		 System.out.println("New Images: " + newImages.keySet() +"--"+ newImages.values());
// 
// Xử lý màu sắc
		List<Color> colorList = new ArrayList<>();
		for (String colorName : newColors) {
			Color color = colorService.findByName(colorName);
			if (color == null) {
				color = new Color();
				color.setColorName(colorName);
				colorService.save(color);
			}
			colorList.add(color);
		}
		System.out.println("Số màu sắc đã xử lý: " + colorList.size());

// Xử lý kích thước
		List<Size> sizeList = new ArrayList<>();
		for (String sizeName : newSizes) {
			Size size = sizeService.findByName(sizeName);
			if (size == null) {
				size = new Size();
				size.setSizeName(sizeName);
				sizeService.save(size);
			}
			sizeList.add(size);
		}
		System.out.println("Số kích thước đã xử lý: " + sizeList.size());

		 // Tạo chi tiết sản phẩm
		
	    List<ProductDetail> productDetails = new ArrayList<>();
	    int index = 0;

	    for (Color color : colorList) {
	        for (Size size : sizeList) {
	            if (index >= prices.size() || index >= quantities.size()) {
	                break; // Kiểm tra để không vượt quá kích thước của giá và số lượng
	            }

	            ProductDetail detail = new ProductDetail();
	            detail.setProductId(product);
	            detail.setColorId(color);
	            detail.setSizeId(size);
	            detail.setPrice(prices.get(index));
	            detail.setQuantity(quantities.get(index));

	            // Lấy các hình ảnh cho chi tiết sản phẩm
	            String key = product.getProductName() + "-" + color.getColorName() + "-" + size.getSizeName();
	          //  MultipartFile[] imagesForDetailFiles = newImages.get(key);
	            MultipartFile[] imagesForDetailFiles = newImages;
	            if (imagesForDetailFiles != null) {
	                List<Image> imagesForDetail = imageService.uploadImages(imagesForDetailFiles); // Lưu ảnh và nhận lại danh sách các đường dẫn
	                for (Image image : imagesForDetail) {
	                    image.setProductDetail(detail); // Thiết lập liên kết về ProductDetail
	                }
	                detail.setImages(imagesForDetail); // Thiết lập danh sách ảnh cho ProductDetail
	            }

	            productDetails.add(detail);
	            index++;
	        }
	    }

	    product.setProductDetails(productDetails);
	    product.setCreatedDate(LocalDate.now());
	    product.setStatus(1);

	    // Kiểm tra sản phẩm trước khi lưu
	    System.out.println("Sản phẩm sắp lưu: " + product.getProductName());
	    System.out.println("Số lượng chi tiết sản phẩm: " + productDetails.size());

	    productService.save(product);
	    return "redirect:/product/view";
	}

	@GetMapping("/delete/{id}")
	public String deleteProduct(@PathVariable Long id) {
		Product product = productService.findById(id);
		product.setStatus(0);
		productService.save(product);
		return "redirect:/product/view";
	}

	@GetMapping("/update/{id}")
	public String showUpdateForm(@PathVariable("id") Long id, Model model) {
		Product product = productService.findById(id);
		System.out.println(product.toString());
		model.addAttribute("product", product);
		model.addAttribute("categories", product.getCategory());

		return "updateProduct";
	}

//    @PostMapping("/update/{id}")
//    public String updateProduct(@PathVariable("id") Long id, 
//                                @ModelAttribute("product") Product product, 
//                                @RequestParam("newColor") String newColor,
//                                @RequestParam("newSize") String newSize,
//                                @RequestParam("image") MultipartFile imageFile,
//                                RedirectAttributes redirectAttributes) {
//        try {
//            productService.updateProduct(id, product, newColor, newSize, imageFile);
//
//            redirectAttributes.addFlashAttribute("successMessage", "Sản phẩm đã được cập nhật thành công.");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi cập nhật sản phẩm.");
//        }
//
//        return "redirect:/product/view";
//    }

}
