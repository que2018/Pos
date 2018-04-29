package com.jiusite.database.model;


public class User {

	private int id;
	private int userId;
	private String username;
	private String permissions;
	
	public User() {
		
	}
	
	public int getId() {
		return id;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public String getUsername() {
		return username;
	}
			
	public String getPermissions() {
		return permissions;
	}

	public void setId(int id) {
		this.id = id;
	} 
	
	public void setUserId(int userId) {
		this.userId = userId;
	} 
	
	public void setUsername(String username) {
		this.username = username;
	} 
	
	public void setPermissions(String permissions) {
		this.permissions = permissions;
	} 
		
	public boolean isEqual(User user) {
		if(userId != user.getUserId()) {			
			return false;
		} else {			
			return true;
		}
	}
	
	public boolean isEmpty() {
		if(userId == 0) {
			return true;
		} else {
			return false;
		}
	}
}

