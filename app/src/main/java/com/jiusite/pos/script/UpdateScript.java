package com.jiusite.pos.script;

import com.jiusite.constant.ADDR;
import com.jiusite.pos.database.model.PlaceProduct;
import com.jiusite.network.PostNetData;
import com.jiusite.session.PlaceSession;
import com.jiusite.session.UserSession;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class UpdateScript {
	
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
			dataPlaceProduct.put("special_requests", "");
			
			String key = Integer.toString(i);
			dataPlaceProducts.put(key, dataPlaceProduct);
		}
			
		String userIdStr = Integer.toString(UserSession.userId);
		String orderIdStr = Integer.toString(PlaceSession.orderId);
		
		//prepare place data
		HashMap data = new HashMap();
		data.put("user_id", userIdStr);
		data.put("order_id", orderIdStr);
		data.put("order_products", dataPlaceProducts);
		JSONObject dataJson = new JSONObject(data);
		String dataStr = dataJson.toString();
		
		ArrayList<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("data", dataStr));
		JSONObject outdata = PostNetData.getResult(ADDR.UPDATE, nameValuePairs);
		
		return outdata;
	}
}

















