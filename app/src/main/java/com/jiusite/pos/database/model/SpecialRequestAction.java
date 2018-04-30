package com.jiusite.pos.database.model;

public class SpecialRequestAction{
	private int id;
	private int specialRequestActionId;
	private String name;

	public SpecialRequestAction() {
		
	}
		
	public int getId(){
		return id;
	}
	
	public int getSpecialRequestActionId() {
		return specialRequestActionId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setId(int id) {
		this.id = id;
	} 
	
	public void setSpecialRequestActionId(int specialRequestActionId) {
		this.specialRequestActionId = specialRequestActionId;
	} 
	
	public void setName(String name) {
		this.name = name;
	} 
	
	public boolean isEqual(SpecialRequestAction specialRequestAction) {
		if(specialRequestActionId != specialRequestAction.getSpecialRequestActionId()) {
			return false;
		} else {
			return true;
		}
	}
}