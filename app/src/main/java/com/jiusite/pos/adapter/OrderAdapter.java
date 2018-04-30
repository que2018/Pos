package com.jiusite.pos.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jiusite.constant.CONS;
import com.jiusite.customview.OrderView;
import com.jiusite.pos.database.model.Order;
import com.jiusite.pos.database.model.OrderProduct;
import com.jiusite.pos.database.model.PlaceProduct;
import com.jiusite.global.Glob;
import com.jiusite.session.OrderSession;
import com.jiusite.session.PlaceSession;
import com.jiusite.pos.R;


public class OrderAdapter extends BaseAdapter {
	
	private ArrayList<HashMap> orderRows;
	
    public OrderAdapter(ArrayList<Order> orders) {
		orderRows = new ArrayList<HashMap>();
		
		int orderLen = orders.size();

		try {	
			int count = 0;
			HashMap<Integer, Order> orderRow = new HashMap<Integer, Order>();
			
			for(int i = 0; i < orderLen; i++) {
				Order order = orders.get(i);
																	
				if(count == 0) {
					orderRow = new HashMap<Integer, Order>();
					orderRow.put(count, order);
				
					if(i == (orderLen - 1)) {
						orderRows.add(orderRow);
					}
					
					count ++;
					
				} else if(count < 1) {
					orderRow.put(count, order);
					
					if(i == (orderLen - 1)) {
						orderRows.add(orderRow);
					}
					
					count++;
					
				} else {
					orderRow.put(count, order);
					orderRows.add(orderRow);
					count = 0;
				}
			}
		} catch(Exception e) {
			
		}
    }
	
    @Override
    public int getCount() {
        return orderRows.size();
    } 

    @Override
    public HashMap<Integer, Order> getItem(int position) {	
        return orderRows.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
		
		View ordersView;
		HashMap<Integer, Order> item = getItem(position);
		
		int size = item.size();	
		
		int R_layout_id;
			
		switch(size) {
			case 1:
				R_layout_id = R.layout.order_row1;
				break;
			default:
				R_layout_id = R.layout.order_row2;
				break;
		}
		
		/* if (convertView == null) {
			menuView = LayoutInflater.from(parent.getContext()).inflate(R_layout_id, parent, false);
        } else {
        	menuView = convertView;
        } */
		
		String id = Glob.context.getResources().getString(R.string.ID);
		
		ordersView = LayoutInflater.from(parent.getContext()).inflate(R_layout_id, parent, false);
		
		Order order0 = item.get(0);
		
		int type0 = order0.getType();
		int orderId0 = order0.getOrderId();
		String customer0 = order0.getCustomer();
		
		OrderView orderView0 = (OrderView)ordersView.findViewById(R.id.order_column0);
		orderView0.setContext(Glob.context);
		orderView0.setType(type0);
		orderView0.setOrderId(orderId0);
		orderView0.setCustomer(customer0);
		
		orderView0.setOnClickListener(new OrderListener(order0));
				
		if(size > 1) {
			Order order1 = item.get(1);
			
			int type1 = order1.getType();
			int orderId1 = order1.getOrderId();
			String customer1 = order1.getCustomer();
			
			OrderView orderView1 = (OrderView)ordersView.findViewById(R.id.order_column1);
			orderView1.setContext(Glob.context);
			orderView1.setType(type1);
			orderView1.setOrderId(orderId1);
			orderView1.setCustomer(customer1);
			
			orderView1.setOnClickListener(new OrderListener(order1));
		} 
			
        return ordersView;
    }
	
	//order listener
	private class OrderListener implements View.OnClickListener { 
		
		private Order order;
		
		public OrderListener(Order order) {
			this.order = order;
		}
	
        @Override
        public void onClick(View view) {
			PlaceSession.orderId = order.getOrderId();
			PlaceSession.customer = order.getCustomer();
			PlaceSession.type = CONS.PLACESESSION_ORDERED;
			PlaceSession.clearPlaceProducts();
			PlaceSession.updated = false;
		
			ArrayList<OrderProduct> orderProducts = OrderSession.getOrderProducts(PlaceSession.orderId);
			
			if(orderProducts.size() > 0) {
				for(int i = 0; i < orderProducts.size(); i++) {
					OrderProduct orderProduct = orderProducts.get(i);
					
					PlaceProduct placeProduct = new PlaceProduct();
					placeProduct.setProductId(orderProduct.getProductId());
					placeProduct.setName(orderProduct.getName());
					placeProduct.setQuantity(orderProduct.getQuantity());
					
					PlaceSession.updatePlaceProduct(placeProduct);
				}
			}
			
			Glob.posViewPager.setCurrentItem(1, true);
        }
    } 	
} 


