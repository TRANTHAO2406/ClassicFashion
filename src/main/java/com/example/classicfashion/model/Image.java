package com.example.classicfashion.model;

import java.time.LocalDate;

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
@Table(name = "Image")
public class Image {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "imgLink", length = 255)
	private String imgLink;

	@Column(name = "createdDate")
	private LocalDate createdDate;

	@ManyToOne
	@JoinColumns({ 
			@JoinColumn(name = "productId", referencedColumnName = "productId"),
			@JoinColumn(name = "colorId", referencedColumnName = "colorId"),
			@JoinColumn(name = "sizeId", referencedColumnName = "sizeId") })
	private ProductDetail productDetail;

	public Image(Long id, String imgLink, LocalDate createdDate) {
		super();
		this.id = id;
		this.imgLink = imgLink;
		this.createdDate = createdDate;
	}

	public Image() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImgLink() {
		return imgLink;
	}

	public void setImgLink(String imgLink) {
		this.imgLink = imgLink;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public ProductDetail getProductDetail() {
		return productDetail;
	}

	public void setProductDetail(ProductDetail productDetail) {
		this.productDetail = productDetail;
	}

}
