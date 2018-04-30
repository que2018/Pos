package com.jiusite.pos.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class SpecialRequestCategoryButton extends Button {
	
    private int specialRequestCategoryId;

    public SpecialRequestCategoryButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
	
	public int getSpecialRequestCategoryId() {
		return specialRequestCategoryId;
	}
	
	public void setSpecialRequestCategoryId(int specialRequestCategoryId) {
		this.specialRequestCategoryId = specialRequestCategoryId;
	}
}

