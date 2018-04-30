package com.jiusite.pos.session;

import android.util.Log;

import com.jiusite.pos.database.model.Order;
import com.jiusite.pos.database.model.OrderProduct;

import java.util.ArrayList;


public class OrderSession {
	
	public static boolean updated = false;
	public static boolean orderProductSynced = false;
	public static ArrayList<Order> orders = new ArrayList<Order>();
	public static ArrayList<OrderProduct> orderProducts = new ArrayList<OrderProduct>();
	
	//update order 
	public static void syncOrders(ArrayList<Order> ordersServer) {
		
		boolean sameOrders = OrderSession.sameOrders(OrderSession.orders, ordersServer);
		
		if(sameOrders) {
			OrderSession.updated = true;
		} else {
			OrderSession.orders = ordersServer;
			OrderSession.updated = false;
		}
	}
	
	//update order products 
	public static void syncOrderProducts(ArrayList<OrderProduct> orderProductsServer) {
		
		boolean sameOrderProducts = OrderSession.sameOrderProducts(OrderSession.orderProducts, orderProductsServer);
		
		if(sameOrderProducts) {
			OrderSession.orderProductSynced = true;
		} else {
			OrderSession.orderProductSynced = false;
			OrderSession.orderProducts = orderProductsServer;
		}
	}
	
	//check if the same orders
	public static boolean sameOrders(ArrayList<Order> ordersLocal, ArrayList<Order> ordersServer) {
		for(int i = 0; i < ordersLocal.size(); i++) {
			boolean find = false;
			Order orderLocal = ordersLocal.get(i);
					
			for(int j = 0; j < ordersServer.size(); j++) {
				Order orderServer = ordersServer.get(j);
					
				if(orderLocal.isEqual(orderServer)) {
					find = true;
					break;
				}
			}
			
			if(!find) 
				return false;					
		}
		
		for(int i = 0; i < ordersServer.size(); i++) {
			boolean find = false;
			Order orderServer = ordersServer.get(i);
			
			for(int j = 0; j < ordersLocal.size(); j++) {
				Order orderLocal = ordersLocal.get(j);
				
				if(orderServer.isEqual(orderLocal)) {
					find = true;
					break;
				}	
			}
			
			if(!find) 					
				return false;	
		}
					
		return true;
	}
	
	//check if the same order products
	public static boolean sameOrderProducts(ArrayList<OrderProduct> orderProductsLocal, ArrayList<OrderProduct> orderProductsServer) {
		for(int i = 0; i < orderProductsLocal.size(); i++) {
			boolean find = false;
			OrderProduct orderProductLocal = orderProductsLocal.get(i);
					
			for(int j = 0; j < orderProductsServer.size(); j++) {
				OrderProduct orderProductServer = orderProductsServer.get(j);
					
				if(orderProductLocal.isEqual(orderProductServer)) {
					find = true;
					break;
				}
			}
			
			if(!find) 
				return false;					
		}
		
		for(int i = 0; i < orderProductsServer.size(); i++) {
			boolean find = false;
			OrderProduct orderProductServer = orderProductsServer.get(i);
			
			for(int j = 0; j < orderProductsLocal.size(); j++) {
				OrderProduct orderProductLocal = orderProductsLocal.get(j);
				
				if(orderProductServer.isEqual(orderProductLocal)) {
					find = true;
					break;
				}	
			}
			
			if(!find) 					
				return false;	
		}
					
		return true;
	}
	
	//get order products by id
	public static ArrayList<OrderProduct> getOrderProducts(int orderId) {
		ArrayList<OrderProduct> orderProducts = new ArrayList<OrderProduct>();
		
		for(int i = 0; i < OrderSession.orderProducts.size(); i++) {
			OrderProduct orderProduct = OrderSession.orderProducts.get(i);
			
			if(orderProduct.getOrderId() == orderId) {
				orderProducts.add(orderProduct);
			} 
		}
		
		return orderProducts;
	}
	
	public static boolean hasOrders() {
		if(OrderSession.orderProducts.size() > 0) {
			return true;
		} else {
			return false;
		}
	}
}

















