package com.example.classicfashion.model;

public class CartItem {

	private Long productID;
	private String name;
	private double price;
	private String imgLink;
	private String color;
	private String size;
	private int quantity = 1;
	private boolean isSelected;

	public CartItem() {
	}

	public CartItem(Long productID, String name, double price, String imgLink, String color, String size,
			int quantity,boolean isSelected) {
		this.productID = productID;
		this.name = name;
		this.price = price;
		this.imgLink = imgLink;
		this.color = color;
		this.size = size;
		this.quantity = quantity;
		this.isSelected = isSelected;
	}
  
	public Long getProductID() {
		return productID;
	}

	public void setProductID(Long productID) {
		this.productID = productID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImgLink() {
		return imgLink;
	}

	public void setImgLink(String imgLink) {
		this.imgLink = imgLink;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	

}
