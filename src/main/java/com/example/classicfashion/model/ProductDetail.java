package com.example.classicfashion.model;

import java.math.BigDecimal;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "PRODUCTDETAIL")
@IdClass(ProductDetailId.class)
public class ProductDetail {

	@Id
	@ManyToOne
	@JoinColumn(name = "productId")
	private Product productId;

	@Id
	@ManyToOne
	@JoinColumn(name = "colorId")
	private Color colorId;

	@Id
	@ManyToOne
	@JoinColumn(name = "sizeId")
	private Size sizeId;

	@Id
	@ManyToOne
	@JoinColumn(name = "imageId")
	private Image imageId;

	@Column(name = "price", nullable = false)
	private BigDecimal price;

	@Column(nullable = false)
	private Integer quantity;

	@Column(name = "Description")
	private String description;

	public ProductDetail() {
	}

	public ProductDetail(Product productId, Color colorId, Size sizeId, Image imageId, BigDecimal price, Integer quantity, String description) {
		this.productId = productId;
		this.colorId = colorId;
		this.sizeId = sizeId;
		this.imageId = imageId;
		this.price = price;
		this.quantity = quantity;
		this.description = description;
	}

	public Product getProductId() {
		return productId;
	}

	public void setProductId(Product productId) {
		this.productId = productId;
	}

	public Color getColorId() {
		return colorId;
	}

	public void setColorId(Color colorId) {
		this.colorId = colorId;
	}

	public Size getSizeId() {
		return sizeId;
	}

	public void setSizeId(Size sizeId) {
		this.sizeId = sizeId;
	}

	public Image getImageId() {
		return imageId;
	}

	public void setImageId(Image imageId) {
		this.imageId = imageId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ProductDetail that)) return false;
		return Objects.equals(getProductId(), that.getProductId()) && Objects.equals(getColorId(), that.getColorId()) && Objects.equals(getSizeId(), that.getSizeId()) && Objects.equals(getImageId(), that.getImageId()) && Objects.equals(getPrice(), that.getPrice()) && Objects.equals(getQuantity(), that.getQuantity()) && Objects.equals(getDescription(), that.getDescription());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getProductId(), getColorId(), getSizeId(), getImageId(), getPrice(), getQuantity(), getDescription());
	}
}
