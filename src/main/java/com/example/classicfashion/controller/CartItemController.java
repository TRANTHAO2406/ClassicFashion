package com.example.classicfashion.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.classicfashion.model.CartItem;
import com.example.classicfashion.model.Color;
import com.example.classicfashion.model.Order;
import com.example.classicfashion.model.OrderDetail;
import com.example.classicfashion.model.ProductDetail;
import com.example.classicfashion.model.Size;
import com.example.classicfashion.model.Users;
import com.example.classicfashion.service.CartItemService;
import com.example.classicfashion.service.ColorService;
import com.example.classicfashion.service.OrderDetailService;
import com.example.classicfashion.service.OrderService;
import com.example.classicfashion.service.ProductDetailService;
import com.example.classicfashion.service.ProductService;
import com.example.classicfashion.service.SizeService;
import com.example.classicfashion.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/shopping-cart")
public class CartItemController {

	@Autowired
	private CartItemService cartItemService;

	@Autowired
	private ProductDetailService productDetailService;

	@Autowired
	private ColorService colorService;
	@Autowired
	private SizeService sizeService;

	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderDetailService orderDetailService;
	@Autowired
	private ProductService productService;

	@Autowired
	private UserService userService;
	
	@GetMapping("/view")
	public String viewCart(Model model, HttpSession session) {
		Collection<CartItem> cartItems = cartItemService.getAllItems();
		model.addAttribute("cart", cartItems);

		if (cartItems.isEmpty()) {
			model.addAttribute("message", "Your cart is empty!");
		} else {
			model.addAttribute("totalAmount", cartItemService.getAmount());
			model.addAttribute("totalItems", cartItemService.getCount());
		}

		return "cart-item";
	}
	// add product from cart
	@PostMapping("/add")
	public String addToCart(@RequestParam Long productId,
							@RequestParam String color,
							@RequestParam String size,
							@RequestParam int quantity, 
							@RequestParam String action, 
							HttpSession session, Model model) {
		Color colorID = colorService.findByName(color);
		Size sizeID = sizeService.findByName(size);
		ProductDetail productDetail = productDetailService.findByProductAndColorAndSize(productId, colorID.getId(),
				sizeID.getId());
		if (productDetail == null) {
			model.addAttribute("error", "Product not found!");
			return "redirect:/product/view";
		}

		// create object CartItem to ProductDetail
		CartItem cartItem = new CartItem(productDetail.getProductId().getId(),
				productDetail.getProductId().getProductName(), productDetail.getPrice().doubleValue(),
				productDetail.getImages().isEmpty() ? null : productDetail.getImages().get(0).getImgLink(),
				productDetail.getColorId().getColorName(), productDetail.getSizeId().getSizeName(), quantity, true);

		cartItemService.add(cartItem);

		model.addAttribute("cart", cartItemService.getAllItems());
		model.addAttribute("totalAmount", cartItemService.getAmount());
		model.addAttribute("totalItems", cartItemService.getCount());

		return "buy".equals(action) ? "redirect:/shopping-cart/view" : "redirect:/product/view";
	}

	

	// Remove product
	@GetMapping("/remove")
	public String removeFromCart(@RequestParam Long productId,
								@RequestParam String colorId,
								@RequestParam String sizeId,
								Model model) {

		cartItemService.remove(productId, colorId, sizeId);

		model.addAttribute("cart", cartItemService.getAllItems());
		model.addAttribute("totalAmount", cartItemService.getAmount());
		model.addAttribute("totalItems", cartItemService.getCount());
		return "redirect:/shopping-cart/view";
	}

	@PostMapping("/checkout")
	public String processCheckout(@RequestParam String cartItems, 
								  Model model, HttpSession session) {

	    // Parse JSON từ chuỗi cartItems sang đối tượng Collection<CartItem>
		System.out.println("cartItemsJson: " + cartItems);
		
	    Collection<CartItem> cartItemsList = parseCartItems(cartItems);

	    // Nếu giỏ hàng trống, chuyển hướng lại trang giỏ hàng
	    if (cartItemsList.isEmpty()) {
	        model.addAttribute("message", "Your cart is empty!");
	        return "redirect:/shopping-cart/view"; 
	    }

	    // Tạo một đối tượng Order mới
	    Order order = new Order();
	    
	    
	    // Lấy thông tin người dùng từ session (nếu có)
	    Users currentUser = (Users) session.getAttribute("user");
	    if (currentUser != null) {
	        order.setUser(currentUser);
	    }

	    // Set các thuộc tính cơ bản cho Order
	    order.setOrderDate(LocalDate.now());
	    order.setShippingPrice(5.0); // Phí vận chuyển cố định, bạn có thể thay đổi logic này
	    double totalAmount = 0.0;

	    // Duyệt qua danh sách CartItem và thêm vào OrderDetail
	    for (CartItem cartItem : cartItemsList) {
	        if (cartItem.getIsSelected()) { // Kiểm tra xem sản phẩm có được chọn không
	            OrderDetail orderDetail = new OrderDetail();
	            orderDetail.setOrder(order);
	            orderDetail.setProduct(productService.findById(cartItem.getProductID())); // Tìm sản phẩm từ productService
	            orderDetail.setQuantity(cartItem.getQuantity());
	            orderDetail.setSubTotal(cartItem.getQuantity() * cartItem.getPrice());

	            // Lưu OrderDetail vào CSDL
	            orderDetailService.save(orderDetail);

	            // Cộng tổng giá trị của sản phẩm đã chọn
	            totalAmount += orderDetail.getSubTotal();
	        }
	    }

	    // Set tổng giá trị đơn hàng và lưu Order
	    order.setTotalPrice(totalAmount + order.getShippingPrice());
	    order.setStatus("Pending"); // Trạng thái đơn hàng có thể thay đổi tùy theo yêu cầu
	    order.setUpdatedDate(LocalDate.now());

	    // Lưu Order vào CSDL
	    orderService.save(order);

	    // Xóa các sản phẩm đã chọn khỏi giỏ hàng
	    cartItemService.removeSelectedItems(cartItemsList);

	    // Thêm thông tin vào Model để hiển thị trong trang xác nhận
	    model.addAttribute("message", "Your order has been placed successfully!");
	    model.addAttribute("order", order);
	    // Điều hướng tới trang xác nhận đơn hàng
	    return "order-confirmation";
	    
	}

	// Phương thức để parse chuỗi JSON sang Collection<CartItem>
	private Collection<CartItem> parseCartItems(String cartItemsJson) {
	    ObjectMapper objectMapper = new ObjectMapper();
	    Collection<CartItem> cartItems = new ArrayList<>();
	    try {
	        // Chuyển chuỗi JSON thành Collection<CartItem>
	        cartItems = objectMapper.readValue(cartItemsJson, new TypeReference<Collection<CartItem>>() {});
	    } catch (IOException e) {
	        e.printStackTrace(); // Log lỗi nếu có
	    }
	    return cartItems;
	}

}
