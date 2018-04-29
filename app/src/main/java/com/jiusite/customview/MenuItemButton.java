package com.jiusite.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;


public class MenuItemButton extends Button {
	
    private int menuItemId;

    public MenuItemButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
	
	public int getMenuItemId() {
		return menuItemId;
	}
	
	public void setMenuItemId(int menuItemId) {
		this.menuItemId = menuItemId;
	}
}

