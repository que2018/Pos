package com.jiusite.pos.database.model;

public class SpecialRequest {
	private int id;
	private int specialRequestId;
	private int specialRequestCategoryId;
	private String name;

	public SpecialRequest() {
		
	}
		
	public int getId(){
		return id;
	}
	
	public int getSpecialRequestId() {
		return specialRequestId;
	}
	
	public int getSpecialRequestCategoryId() {
		return specialRequestCategoryId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setId(int id) {
		this.id = id;
	} 
	
	public void setSpecialRequestId(int specialRequestId) {
		this.specialRequestId = specialRequestId;
	} 
	
	public void setSpecialRequestCategoryId(int specialRequestCategoryId) {
		this.specialRequestCategoryId = specialRequestCategoryId;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public boolean isEqual(SpecialRequest specialRequest) {
		if(specialRequestId != specialRequest.getSpecialRequestId()) {
			return false;
		} else {
			return true;
		}
	}
}