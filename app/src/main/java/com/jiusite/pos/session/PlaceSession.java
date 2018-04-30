package com.jiusite.pos.session;

import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.jiusite.pos.constant.CONS;
import com.jiusite.pos.database.model.PlaceProduct;
import com.jiusite.pos.database.model.SpecialRequestGroup;


public class PlaceSession {
	
	public static int tableId = 0;
	public static int orderId = 0;
	public static int selectedProductId = 0;
	public static int selectSpecialRequestActionId = 0;
	public static int selectSpecialRequestCategoryId = 0;
	
	public static boolean updated = true;
	public static String customer = null;
	public static int type = CONS.PLACESESSION_NULL;
	public static ArrayList<PlaceProduct> placeProducts = new ArrayList<PlaceProduct> ();
		
	//clear place session
	public static void clear() {
		tableId = 0;
		orderId = 0;
		selectedProductId = 0;
		selectSpecialRequestActionId = 0;
		selectSpecialRequestCategoryId = 0;
		
		updated = true;
		customer = null;
		type = CONS.PLACESESSION_NULL;
		placeProducts.clear();
	}
	
	//update place product list
	public static void updatePlaceProduct(PlaceProduct placeProduct) {
		int productId = placeProduct.getProductId();
		
		boolean find = false;
		
		for (int i = 0; i < placeProducts.size(); i++) {
			PlaceProduct placeProductFind = placeProducts.get(i);
			
			int productIdFind = placeProductFind.getProductId();
			
			if(productId == productIdFind) {
				int quantity = placeProductFind.getQuantity() + placeProduct.getQuantity();
				placeProductFind.setQuantity(quantity);
				placeProducts.set(i, placeProductFind);
				
				find = true;
				break;
			}
		}
		
		if(!find) placeProducts.add(placeProduct);
	}
	
	//update product from product list
	public static void removePlaceProduct(PlaceProduct placeProduct) {
		int productId = placeProduct.getProductId();
		placeProducts.remove(productId);
	}
		
	//check if place product is cleared
	public static boolean emptyPlaceProducts() {
		return placeProducts.isEmpty();
	}
	
	//clear roduct list
	public static void clearPlaceProducts() {
		placeProducts.clear();
	}
	
	//add special selection to place product
	public static void addSpecialRequestGroup(int productId, SpecialRequestGroup SpecialRequestGroup) {
		PlaceProduct placeProduct = (PlaceProduct)placeProducts.get(productId);
		
		if (placeProduct != null) {
			placeProduct.addSpecialRequestGroup(SpecialRequestGroup);
		}
	}
}

















