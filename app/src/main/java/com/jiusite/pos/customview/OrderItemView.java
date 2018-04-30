package com.jiusite.pos.customview;

import android.content.Context;

import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import com.jiusite.pos.R;


public class OrderItemView extends TextView {
    private int quantity;

    public OrderItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        		
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.OrderItemView,
                0, 0
        );

        try {
            quantity = a.getInteger(R.styleable.OrderItemView_quantity, 0);
        
        } finally {
            a.recycle();
        }
    }
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
