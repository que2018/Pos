package com.jiusite.service;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import com.jiusite.constant.ADDR;
import com.jiusite.constant.STATS;
import com.jiusite.database.SpecialRequestTable;
import com.jiusite.database.model.SpecialRequest;
import com.jiusite.helper.Func;
import com.jiusite.network.GetNetData;
import com.jiusite.session.MenuSession;


public class SpecialRequestService extends Service {

	private Timer timer;
	private SpecialRequestTask specialRequestTask;
	private SpecialRequestTable specialRequestTable;
	
	@Override
	public IBinder onBind(Intent intent) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
    public void onCreate() {
		//init tables
		specialRequestTable = SpecialRequestTable.getInstance(SpecialRequestService.this);

		//load menu
		timer = new Timer();
		specialRequestTask = new SpecialRequestTask();
		timer.schedule(specialRequestTask, 0, 5000); 
    }

	class SpecialRequestTask extends TimerTask {
		public void run() {			
			try {
				//get special requests
				JSONObject outdata = GetNetData.getResult(ADDR.SPECIAL_REQUESTS);
				int httpCode = outdata.getInt("http_code");
				
				//http success
				if(httpCode == STATS.HTTP_OK) {
					JSONObject data = (JSONObject)outdata.get("data");	
					JSONArray specialRequestsJson = data.getJSONArray("special_requests");
				
					ArrayList<SpecialRequest> speicalRequestsLocal = specialRequestTable.getSpecialRequests();
					ArrayList<SpecialRequest> speicalRequestsServer = new ArrayList<SpecialRequest>();
					
					for(int i = 0; i < specialRequestsJson.length(); i++) {
						JSONObject specialRequestJson = (JSONObject)specialRequestsJson.get(i);
						int specialRequestId = specialRequestJson.getInt("special_request_id");
						int specialRequestCategoryId = specialRequestJson.getInt("special_request_category_id");
						String name = specialRequestJson.getString("name").toString();
									
						SpecialRequest specialRequest = new SpecialRequest();
						specialRequest.setSpecialRequestId(specialRequestId);
						specialRequest.setSpecialRequestCategoryId(specialRequestCategoryId);
						specialRequest.setName(name);
						
						speicalRequestsServer.add(specialRequest);
					}
					
					if(!Func.sameSpecialRequests(speicalRequestsLocal, speicalRequestsServer)) {					
						specialRequestTable.clearTable();
						
						for(int i = 0; i < speicalRequestsServer.size(); i++) {
							specialRequestTable.insertSpecialRequest(speicalRequestsServer.get(i));
						}
						
						MenuSession.specialRequests = speicalRequestsServer;
						
					} else {					
						SharedPreferences.Editor editor = getSharedPreferences("SYNC", Context.MODE_PRIVATE).edit();
						editor.putBoolean("special_request_sync", true);
						editor.commit();
						
						Log.d("service", "special request has sync .... ");
					}
				}
			} catch(JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();  
		
		timer.cancel();
		specialRequestTask.cancel();	
	}
}




















