package com.example.classicfashion.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Size")
public class Size {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;

	@Column(name = "SizeName", columnDefinition = "NVARCHAR(255)")
	private String sizeName;

	@OneToMany(mappedBy = "sizeId", cascade = CascadeType.ALL)
	private List<ProductDetail> productDetails;

	public Size() {
	}

	public Size(Long id, String sizeName) {
		Id = id;
		this.sizeName = sizeName;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getSizeName() {
		return sizeName;
	}

	public void setSizeName(String sizeName) {
		this.sizeName = sizeName;
	}

}
