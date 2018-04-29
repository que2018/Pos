package com.jiusite.database.model;


public class Product {
	
	private int id;
	private String name;
	private double price;
	private int productId;
	private int categoryId;
	
	public Product() {
		
	}
		
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}	
	
	public double getPrice() {
		return price;
	}
		
	public int getProductId() {
		return productId;
	}
	
	public int getCategoryId() {
		return categoryId;
	}
	
	public void setId(int id) {
		this.id = id;
	} 
	
	public void setName(String name) {
		this.name = name;
	} 
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public void setProductId(int productId) {
		this.productId = productId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	} 	
	
	public boolean isEqual(Product product) {
		if(productId != product.getProductId()) {
			return false;
		} else {
			return true;
		}
	}
}

