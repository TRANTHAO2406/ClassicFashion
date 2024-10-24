package com.example.classicfashion.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ORDERDETAIL")
public class OrderDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "orderId")
	private Order order;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "productId", referencedColumnName = "productId"),
			@JoinColumn(name = "colorId", referencedColumnName = "colorId"),
			@JoinColumn(name = "sizeId", referencedColumnName = "sizeId") })
	private ProductDetail productDetail;

	@Column(nullable = false)
	private Integer quantity;

	@Column(name = "subTotal", nullable = false)
	private Double subTotal;

	@Column(name = "price", nullable = false)
	private Double price;

	public OrderDetail() {
	}

	public OrderDetail(Long id, Order order, ProductDetail productDetail, Integer quantity, Double subTotal,
			Double price) {
		this.id = id;
		this.order = order;
		this.productDetail = productDetail;
		this.quantity = quantity;
		this.subTotal = subTotal;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public ProductDetail getProductDetail() {
		return productDetail;
	}

	public void setProductDetail(ProductDetail productDetail) {
		this.productDetail = productDetail;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
}
