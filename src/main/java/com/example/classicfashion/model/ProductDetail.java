package com.example.classicfashion.model;

import java.math.BigDecimal;

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
	private Long producId;

	@Id
	private Long colorId;

	@Id
	private Long sizeId;

	@Id
	private Long imageId;

	@Column(nullable = false)
	private BigDecimal price;

	@Column(nullable = false)
	private Integer quantity;

	@Column(name = "Description")
	private String description;

	@ManyToOne
	@JoinColumn(name = "producId", referencedColumnName = "id", nullable = false)
	private Product product;

	@ManyToOne
	@JoinColumn(name = "colorId", referencedColumnName = "id", nullable = false)
	private Color color;

	@ManyToOne
	@JoinColumn(name = "sizeId", referencedColumnName = "id", nullable = false)
	private Size size;

	@ManyToOne
	@JoinColumn(name = "imageId", referencedColumnName = "id", nullable = false)
	private Image image;

	public ProductDetail() {
		super();
	}

	public ProductDetail(Long producId, Long colorId, Long sizeId, Long imageId, BigDecimal price, Integer quantity,
			String description, Product product, Color color, Size size, Image image) {
		this.producId = producId;
		this.colorId = colorId;
		this.sizeId = sizeId;
		this.imageId = imageId;
		this.price = price;
		this.quantity = quantity;
		this.description = description;
		this.product = product;
		this.color = color;
		this.size = size;
		this.image = image;
	}

	public Long getProducId() {
		return producId;
	}

	public void setProducId(Long producId) {
		this.producId = producId;
	}

	public Long getColorId() {
		return colorId;
	}

	public void setColorId(Long colorId) {
		this.colorId = colorId;
	}

	public Long getSizeId() {
		return sizeId;
	}

	public void setSizeId(Long sizeId) {
		this.sizeId = sizeId;
	}

	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
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

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

}
