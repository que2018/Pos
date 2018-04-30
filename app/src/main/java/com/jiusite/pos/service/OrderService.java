package com.jiusite.pos.service;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.jiusite.constant.ADDR;
import com.jiusite.constant.STATS;
import com.jiusite.pos.database.model.Order;
import com.jiusite.pos.database.model.OrderProduct;
import com.jiusite.session.OrderSession;
import com.jiusite.network.GetNetData;


public class OrderService extends Service {
		
	@Override
	public IBinder onBind(Intent intent) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
    public void onCreate() {	
		//load menu
		Timer timer = new Timer();
		OrderTask orderTask = new OrderTask();
		timer.schedule(orderTask, 0, 3000); 
    }

	class OrderTask extends TimerTask {
		public void run() {	
			try {
				//get orders
				JSONObject outdata = GetNetData.getResult(ADDR.ORDERS);
				int httpCode = outdata.getInt("http_code");
				
				//http success
				if(httpCode == STATS.HTTP_OK) {
					JSONObject data = (JSONObject)outdata.get("data");				
					JSONArray ordersJson = data.getJSONArray("orders");
					
					ArrayList<Order> orders = new ArrayList<Order>();
					
					for(int i = 0; i < ordersJson.length(); i++) {
						JSONObject orderJson = (JSONObject)ordersJson.get(i);
						int orderId = orderJson.getInt("order_id");
						int type = orderJson.getInt("type");
						String customer = orderJson.getString("customer");
						
						Order order = new Order();
						order.setOrderId(orderId);
						order.setCustomer(customer);
						order.setType(type);
						orders.add(order);
					}
					
					OrderSession.syncOrders(orders);
				}
			
				//get order products
				outdata = GetNetData.getResult(ADDR.ORDER_PRODUCTS);
				httpCode = outdata.getInt("http_code");
				
				//http success
				if(httpCode == STATS.HTTP_OK) {
					JSONObject data = (JSONObject)outdata.get("data");	
					JSONArray orderProductsJson = data.getJSONArray("order_products");
					
					ArrayList<OrderProduct> orderProducts = new ArrayList<OrderProduct>();
					
					for(int i = 0; i < orderProductsJson.length(); i++) {
						JSONObject orderProductJson = (JSONObject)orderProductsJson.get(i);
						int orderId = orderProductJson.getInt("order_id");
						int productId = orderProductJson.getInt("product_id");
						String name = orderProductJson.getString("name");
						int quantity = orderProductJson.getInt("quantity");
						double price = orderProductJson.getDouble("price");
						String specialRequest = orderProductJson.getString("special_request");
						
						OrderProduct orderProduct = new OrderProduct();
						orderProduct.setOrderId(orderId);
						orderProduct.setProductId(productId);
						orderProduct.setName(name);
						orderProduct.setQuantity(quantity);
						orderProduct.setPrice(price);
						orderProduct.setSpecialRequest(specialRequest);
						orderProducts.add(orderProduct);
					}
					
					OrderSession.syncOrderProducts(orderProducts);
				}
			} catch(JSONException e) {
				e.printStackTrace();
			}
		}
	}
}




















