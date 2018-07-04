package com.jiusite.main;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;

import com.jiusite.adapter.OrderAdapter;
import com.jiusite.database.model.Order;
import com.jiusite.session.OrderSession;


public class OrdersFragment extends Fragment {
	
	private ListView orderList;
	private TextView orderNotice;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {		
        super.onCreate(savedInstanceState);
    }
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = (ViewGroup)inflater.inflate(R.layout.fragment_orders, container, false);

		//get view
		orderList = (ListView)rootView.findViewById(R.id.order_list);
		orderNotice = (TextView)rootView.findViewById(R.id.order_notice);

		//set adapter
		if(OrderSession.hasOrders()) {
			orderNotice.setVisibility(View.INVISIBLE);
			OrderAdapter adapter = new OrderAdapter(OrderSession.orders);
			orderList.setAdapter(adapter);
		}

        return rootView;
    }
	
	@Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
		
		if(visible) {
			updateOrdersView();
		} 
    }
	
	public void updateOrdersView() {
		if(!OrderSession.updated) {	
			OrderAdapter adapter = new OrderAdapter(OrderSession.orders);
			orderList.setAdapter(adapter);
			
			OrderSession.updated = true;
		}
	}
}

























