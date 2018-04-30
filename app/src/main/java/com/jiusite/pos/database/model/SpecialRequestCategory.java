package com.jiusite.pos.database.model;

public class SpecialRequestCategory{
	private int id;
	private int specialRequestCategoryId;
	private String name;

	public SpecialRequestCategory() {
		
	}
		
	public int getId(){
		return id;
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
	
	public void setSpecialRequestCategoryId(int specialRequestCategoryId) {
		this.specialRequestCategoryId = specialRequestCategoryId;
	} 
	
	public void setName(String name) {
		this.name = name;
	} 
	
	public boolean isEqual(SpecialRequestCategory specialRequestCategory) {
		if(specialRequestCategoryId != specialRequestCategory.getSpecialRequestCategoryId()) {
			return false;
		} else {
			return true;
		}
	}
}