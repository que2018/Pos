package com.jiusite.pos.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiusite.constant.CONS;
import com.jiusite.pos.R;


public class OrderView extends RelativeLayout {
	
	private int type;
	private Context context;
	private TextView orderIdText;
	private TextView customerText;
	private RelativeLayout headerLayout;
	
    public OrderView(Context context) {
		super(context);
		LayoutInflater inflater = LayoutInflater.from(context);
    }
	
    public OrderView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		LayoutInflater inflater = LayoutInflater.from(context);
    }
	
    public OrderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = LayoutInflater.from(context);
		
		View view = inflater.inflate(R.layout.order_view, this, true);
		orderIdText = (TextView)view.findViewById(R.id.order_id);
		customerText = (TextView)view.findViewById(R.id.customer);
		headerLayout = (RelativeLayout)view.findViewById(R.id.header);
    }
	
	public void setContext(Context context) {
		this.context = context;
	}
	
	public void setOrderId(int orderId) {
		String ID = context.getResources().getString(R.string.ID);
		String orderIdString = ID + ": " + orderId;
		orderIdText.setText(orderIdString);
	}
	
	public void setCustomer(String customer) {
		String table = context.getResources().getString(R.string.table);
		String pickup = context.getResources().getString(R.string.pickup);
		String togo = context.getResources().getString(R.string.togo);
		String customerString = new String();
		
		switch(type) {
			case 0:
				customerString = table + ": " + customer;
				break; 
			case 1:
				customerString = togo;
				break; 
			case 2:
				customerString = pickup;
				break; 
		}

		customerText.setText(customerString);
	}
	
	public void setType(int type) {
		this.type = type;
		
		switch(type) {
			case 0:
				headerLayout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.order_item_header_dining));
				break; 
			case 1:
				headerLayout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.order_item_header_togo));
				break; 
			case 2:
				headerLayout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.order_item_header_pickup));
				break; 
		}
	}
}











