package com.jiusite.database.model;

import android.util.Log;


public class Table{
	private int id;
	private int tableId;
	private int customer;
	private int orderId;
	private String name;
	private double x;
	private double y;
	private int anim;
	private int status;

	public Table() {
		
	}
	
	public int getId(){
		return id;
	}
	
	public int getTableId() {
		return tableId;
	}
	
	public int getCustomer() {
		return customer;
	}
	
	public int getOrderId() {
		return orderId;
	}
	
	public String getName() {
		return name;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public int getAnim() {
		return anim;
	}
	
	public int getStatus() {
		return status;
	}

	public void setId(int id) {
		this.id = id;
	} 
	
	public void setTableId(int tableId) {
		this.tableId = tableId;
	} 
	
	public void setCustomer(int customer) {
		this.customer = customer;
	} 
	
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	} 
	
	public void setName(String name) {
		this.name = name;
	} 
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void setAnim(int anim) {
		this.anim = anim;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public boolean isEqual(Table table) {
		if(tableId != table.getTableId()) {			
			return false;
		} else if(customer != table.getCustomer()) {			
			return false;
		} else if(status != table.getStatus()) {			
			return false;
		} else {
			return true;
		}
	} 
}