package com.example.classicfashion.model;

import java.io.Serializable;

public class ProductDetailId implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long productId;
	private Long colorId;
	private Long sizeId;
//	private Long imageId;
	
	public ProductDetailId() {
	}

	public ProductDetailId(Long productId, Long colorId, Long sizeId) {
		this.productId = productId;
		this.colorId = colorId;
		this.sizeId = sizeId;
//		this.imageId = imageId;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((colorId == null) ? 0 : colorId.hashCode());
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
		result = prime * result + ((sizeId == null) ? 0 : sizeId.hashCode());
		return result;
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
		if (colorId == null) {
			if (other.colorId != null)
				return false;
		} else if (!colorId.equals(other.colorId))
			return false;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		if (sizeId == null) {
			if (other.sizeId != null)
				return false;
		} else if (!sizeId.equals(other.sizeId))
			return false;
		return true;
	}

//	public Long getImageId() {
//		return imageId;
//	}
//
//	public void setImageId(Long imageId) {
//		this.imageId = imageId;
//	}

	
}
