package com.jiusite.pos.database.model;


public class Category {

	private int id;
	private String name;
	private int categoryId;
	private int parentId;
	
	public Category() {
		
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
			
	public int getCategoryId() {
		return categoryId;
	}
	
	public int getParentId() {
		return parentId;
	}
	
	public void setId(int id) {
		this.id = id;
	} 
	
	public void setName(String name) {
		this.name = name;
	} 
	
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	} 
	
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
		
	public boolean isEqual(Category category) {
		if(categoryId != category.getCategoryId()) {			
			return false;
		} else if(parentId != category.getParentId()) {			
			return false;
		} else {			
			return true;
		}
	}
}

