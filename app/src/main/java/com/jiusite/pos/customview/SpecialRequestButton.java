package com.jiusite.pos.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;


public class SpecialRequestButton extends Button {
	
    private int specialRequestId;

    public SpecialRequestButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
	
	public int getSpecialRequestId() {
		return specialRequestId;
	}
	
	public void setSpecialRequestId(int specialRequestId) {
		this.specialRequestId = specialRequestId;
	}
}

