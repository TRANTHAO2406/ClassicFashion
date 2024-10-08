package com.example.classicfashion.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "COLOR")
public class Color {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	@Column(name = "ColorName")
	private String colorName;

	@OneToMany(mappedBy = "colorId", cascade = CascadeType.ALL)
	private List<ProductDetail> productDetails;
	public Color() {
	}

	public Color(Long id, String colorName) {
		Id = id;
		this.colorName = colorName;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

}
