package com.jiusite.script;

import com.jiusite.constant.ADDR;
import com.jiusite.constant.CONS;
import com.jiusite.database.model.PlaceProduct;
import com.jiusite.database.model.SpecialRequest;
import com.jiusite.database.model.SpecialRequestAction;
import com.jiusite.database.model.SpecialRequestGroup;
import com.jiusite.network.PostNetData;
import com.jiusite.session.PlaceSession;
import com.jiusite.session.UserSession;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class PlaceScript {
	
	public static JSONObject execute() {
		HashMap dataPlaceProducts = new HashMap();
			
		//get place products
		ArrayList<PlaceProduct> placeProducts = PlaceSession.placeProducts;
		
		for(int i = 0; i < placeProducts.size(); i++) {
			PlaceProduct placeProduct = placeProducts.get(i);
			HashMap dataPlaceProduct = new HashMap();
			
			int productId = placeProduct.getProductId();
			int quantity = placeProduct.getQuantity();
			double price = placeProduct.getPrice();
			ArrayList<SpecialRequestGroup> specialRequestGroups = placeProduct.getSpecialRequestGroups();
			
			String productIdStr = Integer.toString(productId);
			String quantityStr = Integer.toString(quantity);
			String priceStr = Double.toString(price);
			
			dataPlaceProduct.put("product_id", productIdStr);
			dataPlaceProduct.put("quantity", quantityStr);
			dataPlaceProduct.put("price", priceStr);
			dataPlaceProduct.put("force", "0");
			dataPlaceProduct.put("make", "");
			dataPlaceProduct.put("option", "");
			dataPlaceProduct.put("flavor", "");
			dataPlaceProduct.put("material", "");
							
			int size = specialRequestGroups.size();
			HashMap specialRequests = new HashMap();
			
			for(int j = 0; j < size; j++) {
				SpecialRequestGroup specialRequestGroup = specialRequestGroups.get(j);
				SpecialRequest specialRequest = specialRequestGroup.getSpecialRequest();
				SpecialRequestAction specialRequestAction = specialRequestGroup.getSpecialRequestAction();
				
				int specialRequestId = specialRequest.getSpecialRequestId();
				int specialRequestActionId = specialRequestAction.getSpecialRequestActionId();
				
				HashMap hM = new HashMap();
				hM.put("id", specialRequestId);
				hM.put("action_id", specialRequestActionId);
				
				String k = Integer.toString(j);
				specialRequests.put(k, hM);
			}
			
			dataPlaceProduct.put("special_requests", specialRequests);
			
			String key = Integer.toString(i);
			dataPlaceProducts.put(key, dataPlaceProduct);
		}
				
		String userIdString = Integer.toString(UserSession.userId);
		
		//prepare place data
		HashMap data = new HashMap();
		
		if(PlaceSession.type == CONS.PLACESESSION_PLACED_DINING) {
			data.put("type", 0);
		} 
		
		if(PlaceSession.type == CONS.PLACESESSION_PLACED_TOGO) {
			data.put("type", 1);
		} 
		
		if(PlaceSession.type == CONS.PLACESESSION_PLACED_PICKUP) {
			data.put("type", 2);
		} 

		data.put("seed", "");	
		data.put("customer", PlaceSession.customer);
		data.put("user_id", userIdString);
		data.put("order_products", dataPlaceProducts);
		JSONObject dataJson = new JSONObject(data);
		String dataString = dataJson.toString();
					
		ArrayList<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("data", dataString));
		JSONObject outdata = PostNetData.getResult(ADDR.PLACE, nameValuePairs);
		
		return outdata;
	}
}

















