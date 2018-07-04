package com.jiusite.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class SpecialRequestActionButton extends Button {
	
    private int specialRequestActionId;

    public SpecialRequestActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
	
	public int getSpecialRequestActionId() {
		return specialRequestActionId;
	}
	
	public void setSpecialRequestActionId(int specialRequestActionId) {
		this.specialRequestActionId = specialRequestActionId;
	}
}

