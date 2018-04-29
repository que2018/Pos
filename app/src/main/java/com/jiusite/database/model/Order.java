package com.jiusite.database.model;

public class Order {
	private int id;
	private int orderId;
	private String customer;
	private int type;
	
	public Order() {}
	
	public Order(int orderId, int type) {
		this.orderId = orderId;
		this.type = type;
	}
		
	public int getId() {
		return id;
	}
	
	public int getOrderId() {
		return orderId;
	}
	
	public String getCustomer() {
		return customer;
	}
	
	public int getType() {
		return type;
	}
	
	public void setId(int id) {
		this.id = id;
	} 
	
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	} 
	
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	
	public void setType(int type) {
		this.type = type;
	} 
	
	public boolean isEqual(Order order) {
		if(orderId != order.getOrderId()) {			
			return false;
		} else {
			return true;
		}
	}
}

