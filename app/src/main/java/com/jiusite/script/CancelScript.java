package com.jiusite.script;

import com.jiusite.constant.ADDR;
import com.jiusite.database.model.PlaceProduct;
import com.jiusite.network.PostNetData;
import com.jiusite.session.PlaceSession;
import com.jiusite.session.UserSession;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class CancelScript {
	
	public static JSONObject execute() {
		HashMap dataPlaceProducts = new HashMap();
				
		String userIdStr = Integer.toString(UserSession.userId);
		String orderIdStr = Integer.toString(PlaceSession.orderId);
		
		//prepare place data
		HashMap data = new HashMap();
		data.put("order_id", orderIdStr);
		JSONObject dataJson = new JSONObject(data);
		String dataStr = dataJson.toString();
		
		ArrayList<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("data", dataStr));
		JSONObject outdata = PostNetData.getResult(ADDR.CANCEL, nameValuePairs);
		
		return outdata;
	}
}

















