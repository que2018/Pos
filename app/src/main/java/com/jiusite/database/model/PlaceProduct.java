package com.jiusite.database.model;

import java.util.ArrayList;

public class PlaceProduct {
	private int id;
	private int productId;
	private String name;
	private int quantity;
	private double price;
	private ArrayList<SpecialRequestGroup> specialRequestGroups;
		
	public PlaceProduct() {
		specialRequestGroups = new ArrayList<SpecialRequestGroup>();
	}
		
	public int getId() {
		return id;
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

	public ArrayList<SpecialRequestGroup> getSpecialRequestGroups() {
		return specialRequestGroups;
	}
	
	public void setId(int id) {
		this.id = id;
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

	public void addSpecialRequestGroup(SpecialRequestGroup specialRequestGroup) {
		specialRequestGroups.add(specialRequestGroup);
	}
	
	public void clearSpecialRequestGroup() {
		specialRequestGroups.clear();
	}
	
	public boolean isEmpty() {
		if(id == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isEqual(PlaceProduct placeProduct) {
		if(productId != placeProduct.getProductId()) {			
			return false;
		} else {			
			return true;
		}
	}
}

