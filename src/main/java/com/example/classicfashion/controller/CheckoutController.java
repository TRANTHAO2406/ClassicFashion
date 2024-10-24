package com.example.classicfashion.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
import com.example.classicfashion.model.Payment;
import com.example.classicfashion.model.Product;
import com.example.classicfashion.model.ProductDetail;
import com.example.classicfashion.model.Size;
import com.example.classicfashion.model.TransactionHistory;
import com.example.classicfashion.model.Users;
import com.example.classicfashion.service.CartItemService;
import com.example.classicfashion.service.ColorService;
import com.example.classicfashion.service.OrderDetailService;
import com.example.classicfashion.service.OrderService;
import com.example.classicfashion.service.PaymentService;
import com.example.classicfashion.service.ProductDetailService;
import com.example.classicfashion.service.ProductService;
import com.example.classicfashion.service.SizeService;
import com.example.classicfashion.service.TransactionHistoryService;
import com.example.classicfashion.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {
	private final CartItemService cartItemService;
	private final OrderService orderService;
	private final OrderDetailService orderDetailService;
	private final ProductService productService;
	private final UserService userService;
	private final PaymentService paymentService;
	private final ProductDetailService productDetailService;
	private final ColorService colorService;
	private final SizeService sizeService;
	private final TransactionHistoryService transactionHistoryService;
	

	public CheckoutController(CartItemService cartItemService, OrderService orderService,
			OrderDetailService orderDetailService, ProductService productService, UserService userService,
			PaymentService paymentService, ProductDetailService productDetailService, SizeService sizeService, ColorService colorService,TransactionHistoryService transactionHistoryService) {
		this.cartItemService = cartItemService;
		this.orderService = orderService;
		this.orderDetailService = orderDetailService;
		this.productService = productService;
		this.userService = userService;
		this.paymentService = paymentService;
		this.productDetailService = productDetailService;
		this.colorService = colorService;
		this.sizeService = sizeService;
		this.transactionHistoryService = transactionHistoryService;
	}

	@GetMapping
	public String showCheckOutPage(HttpSession session, Model model) {
		Users currentUser = userService.getCurrentUser();
		model.addAttribute("user", currentUser);
		@SuppressWarnings("unchecked")
		List<CartItem> selectedCartItems = (List<CartItem>) session.getAttribute("cartItems");
		List<Payment> payments = paymentService.getAll();

		double totalPrice = selectedCartItems.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
		model.addAttribute("cartItems", selectedCartItems);
		model.addAttribute("payments", payments);
		model.addAttribute("totalPrice", totalPrice);
		return "order-confirmation";

	}

	@PostMapping("/submit")
	public String submitChecout(HttpSession session, Model model, 
								@RequestParam String paymentMethod,
								@RequestParam String shippingMethod) {
		Users currentUser = userService.getCurrentUser();
		model.addAttribute("user", currentUser);
		@SuppressWarnings("unchecked")
		List<CartItem> selectedItems = (List<CartItem>) session.getAttribute("cartItems");
		double totalPrice = selectedItems.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();

		double shippingCost = Double.parseDouble(shippingMethod);
		double totalPriceWithShipping = totalPrice + shippingCost;
		
		Order order = new Order();
		order.setUser(currentUser);
		order.setOrderDate(LocalDate.now());
		order.setShippingPrice(shippingCost);
		order.setTotalPrice(totalPriceWithShipping);
		order.setStatus("Chờ phê duyệt"); // Change status according to payment method
		order.setUpdatedDate(LocalDate.now());
		orderService.save(order);
		
		List<OrderDetail> orderDetails= new ArrayList<OrderDetail>();
		for (CartItem cartItem : selectedItems) {
			if (cartItem.getIsSelected()) {
				Product product = productService.findById(cartItem.getProductID());
				Color colorID = colorService.findByName(cartItem.getColor());
				Size sizeID = sizeService.findByName(cartItem.getSize());
				ProductDetail productDetail = productDetailService.findByProductAndColorAndSize(product.getId(), colorID.getId(), sizeID.getId());
				int newQuantity = productDetail.getQuantity() - cartItem.getQuantity();
				if (newQuantity < 0) {
					throw new RuntimeException("Số lượng sản phẩm trong kho không đủ: " + product.getProductName());
				}
				// Update the product quantity in the database
				productDetail.setQuantity(newQuantity);
				productDetailService.save(productDetail);
				
				OrderDetail orderDetail = new OrderDetail();
				orderDetail.setOrder(order);
				orderDetail.setProductDetail(productDetail);
				orderDetail.setQuantity(cartItem.getQuantity());
				orderDetail.setPrice(cartItem.getPrice());
				orderDetail.setSubTotal(cartItem.getQuantity() * cartItem.getPrice());
				orderDetailService.save(orderDetail);
				orderDetails.add(orderDetail);
				
			}
		}

		order.setOrderDetails(orderDetails);
		orderService.save(order);
		
		cartItemService.removeSelectedItems(selectedItems);
		// Use CheckoutService to find payment method and save transaction history
				Payment selectedPayment = paymentService.findPaymentByMethod(paymentMethod);
				TransactionHistory transactionHistory = new TransactionHistory();
				transactionHistory.setOrder(order);
				transactionHistory.setPayment(selectedPayment);
				transactionHistory.setTransactionDate(LocalDate.now());
				transactionHistory.setTransactionAmount(totalPriceWithShipping);
				if (paymentMethod.equalsIgnoreCase("Tiền mặt")) {
					transactionHistory.setStatus("Chờ thanh toán");
				} else {
					transactionHistory.setStatus("Hoàn tất");
				}
				transactionHistoryService.save(transactionHistory);

				// Pass the order object to the model so it can be used in the Thymeleaf
				// template
				model.addAttribute("order", order);
				model.addAttribute("totalPrice", totalPrice);

		return "redirect:/transaction";
	}

}
