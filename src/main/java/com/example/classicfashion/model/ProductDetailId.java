package com.example.classicfashion.model;

import java.io.Serializable;
import java.util.Objects;

public class ProductDetailId implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long productId;
	private Long colorId;
	private Long sizeId;
	private Long imageId;

	public ProductDetailId() {
	}

	public ProductDetailId(Long productId, Long colorId, Long sizeId, Long imageId) {
		this.productId = productId;
		this.colorId = colorId;
		this.sizeId = sizeId;
		this.imageId = imageId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ProductDetailId that)) return false;
		return Objects.equals(productId, that.productId) && Objects.equals(colorId, that.colorId) && Objects.equals(sizeId, that.sizeId) && Objects.equals(imageId, that.imageId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(productId, colorId, sizeId, imageId);
	}
}
