package com.jiusite.pos.customview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.Button;

import com.jiusite.session.ScreenSession;
import com.jiusite.global.Glob;
import com.jiusite.pos.R;


public class TableButton extends Button {
    
	private int status;
    private String name;
	
    public TableButton(Context context, AttributeSet attrs) {
          super(context, attrs);
				
		//setGravity(Gravity.CENTER);
		//setTextColor(Color.parseColor("#ffffff"));
		//setTextSize(TypedValue.COMPLEX_UNIT_SP, ScreenSession.tableFontSize);
    }
	
	public void setStatus(int status) {
		this.status = status;
		
		switch(status) {
			case 0:
				setBackgroundDrawable(getResources().getDrawable(R.drawable.tbl_empty));
				break; 
			case 1:
				setBackgroundDrawable(getResources().getDrawable(R.drawable.tbl_placed));
				break; 
			case 2:
				setBackgroundDrawable(getResources().getDrawable(R.drawable.tbl_ordered));
				break; 
		}
	}
	
	public void setName(String name) {
		this.name = name;
		setText(name);
	}
	
	public int getStatus() {
		return this.status;
	}
	
	public String getName() {
		return this.name;
	}
}





