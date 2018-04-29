package com.jiusite.customview;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import java.util.HashMap;

public class SettingLinearLayout extends LinearLayout {

	private int type;
	private String name;
	private String value;
    private LayoutInflater layoutInflater;
	
    public SettingLinearLayout(Context context) {
        super(context);
		layoutInflater = LayoutInflater.from(context);
    }
	
   public SettingLinearLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		layoutInflater = LayoutInflater.from(context);
    }
	
    public SettingLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		layoutInflater = LayoutInflater.from(context);
    }
	
	public void setType(int type) {
		this.type = type;
	}
		
	public void setName(String name) {
		this.name = name;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public int getType() {
		return this.type;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getValue() {
		return this.value;
	}
}