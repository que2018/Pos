package com.jiusite.database.model;

public class Setting {
	private int id;
	private int type;
	private String key;
	private int nameId;
	private String value;

	public Setting() {
		
	}
	
	public int getId() {
		return id;
	}
	
	public int getType() {
		return type;
	}
	
	public String getKey() {
		return key;
	}
	
	public int getNameId() {
		return nameId;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public void setNameId(int nameId) {
		this.nameId = nameId;
	} 
	
	public void setValue(String value) {
		this.value = value;
	} 
	
	public boolean isEmpty() {
		if(id == 0) {
			return true;
		} else {
			return false;
		}
	}
}