package com.example.classicfashion.model;

import java.io.Serializable;
import java.util.Objects;

public class ProductDetailId implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long productId;
	private Long colorId;
	private Long sizeId;
	private Long imageId;
	@Override
	public int hashCode() {
		return Objects.hash(colorId, imageId, productId, sizeId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductDetailId other = (ProductDetailId) obj;
		return Objects.equals(colorId, other.colorId) && Objects.equals(imageId, other.imageId)
				&& Objects.equals(productId, other.productId) && Objects.equals(sizeId, other.sizeId);
	}

	
}
