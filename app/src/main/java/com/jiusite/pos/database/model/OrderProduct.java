package com.jiusite.pos.database.model;


public class OrderProduct {
	private int id;
	private int orderId;
	private int productId;
	private String name;
	private int quantity;
	private double price;
	private String specialRequest;
	
	public OrderProduct() {
		
	}
		
	public int getId() {
		return id;
	}
	
	public int getOrderId() {
		return orderId;
	}
	
	public int getProductId() {
		return productId;
	}
	
	public String getName() {
		return name;
	}
	
	public int getQuantity() {
		return quantity;
	}

	public double getPrice() {
		return price;
	}
	
	public String getSpecialRequest() {
		return specialRequest;
	}
	
	public void setId(int id) {
		this.id = id;
	} 
	
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	} 
	
	public void setProductId(int productId) {
		this.productId = productId;
	} 
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	} 
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public void setSpecialRequest(String specialRequest) {
		this.specialRequest = specialRequest;
	} 
	
	public boolean isEmpty() {
		if(id == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isEqual(OrderProduct orderProduct) {
		if(orderId != orderProduct.getOrderId()) {			
			return false;
		} else if(productId != orderProduct.getProductId()) {
			return false;
		} else if(quantity != orderProduct.getQuantity()) {
			return false;
		} else {
			return true;
		}
	}
}

