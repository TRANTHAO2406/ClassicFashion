package com.example.classicfashion.model;

import java.time.LocalDate;

import java.util.List;
import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "Image")
public class Image {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "imgLink", nullable = false, length = 255)
	private String imgLink;

	@Column(name = "createdDate")
	private LocalDate createdDate;

	@OneToMany(mappedBy = "imageId",cascade = CascadeType.ALL)
	private Set<ProductDetail> productDetails;

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

}
