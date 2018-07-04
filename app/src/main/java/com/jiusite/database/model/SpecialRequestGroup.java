package com.jiusite.database.model;

public class SpecialRequestGroup {

	private SpecialRequest specialRequest;
	private SpecialRequestAction specialRequestAction;
	
	public SpecialRequestGroup(SpecialRequest specialRequest, SpecialRequestAction specialRequestAction) {
		this.specialRequest = specialRequest;
		this.specialRequestAction = specialRequestAction;
	}
	
	public SpecialRequest getSpecialRequest() {
		return specialRequest;
	}
	
	public SpecialRequestAction getSpecialRequestAction() {
		return specialRequestAction;
	}
	
	public void setSpecialRequest(SpecialRequest specialRequest) {
		this.specialRequest = specialRequest;
	} 
	
	public void setSpecialRequestAction(SpecialRequestAction specialRequestAction) {
		this.specialRequestAction = specialRequestAction;
	}
	
	public String toString() {
		return specialRequestAction.getName() + specialRequest.getName();
	}
}