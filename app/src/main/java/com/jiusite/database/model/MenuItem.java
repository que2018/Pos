package com.jiusite.database.model;


public abstract class MenuItem {
	
	private int id;
	private String name;
	
	public MenuItem() {
		
	}
		
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setId(int id) {
		this.id = id;
	} 
	
	public void setName(String name) {
		this.name = name;
	} 
	
	public abstract int getType();
	
	public abstract int getItemId();
}

