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
import com.jiusite.database.SpecialRequestActionTable;
import com.jiusite.database.model.SpecialRequestAction;
import com.jiusite.helper.Func;
import com.jiusite.network.GetNetData;
import com.jiusite.session.MenuSession;


public class SpecialRequestActionService extends Service {
	
	private Timer timer;
	private SpecialRequestActionTask specialRequestActionTask;
	private SpecialRequestActionTable specialRequestActionTable;	

	@Override
	public IBinder onBind(Intent intent) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
    public void onCreate() {
		//init tables
		specialRequestActionTable = SpecialRequestActionTable.getInstance(SpecialRequestActionService.this);

		//load menu
		timer = new Timer();
		specialRequestActionTask = new SpecialRequestActionTask();
		timer.schedule(specialRequestActionTask, 0, 2000); 
    }

	class SpecialRequestActionTask extends TimerTask {
		public void run() {			
			try {
				//get special request actions
				JSONObject outdata = GetNetData.getResult(ADDR.SPECIAL_REQUEST_ACTIONS);
				int httpCode = outdata.getInt("http_code");

				//http success
				if(httpCode == STATS.HTTP_OK) {
					JSONObject data = (JSONObject)outdata.get("data");								
					JSONArray specialRequestActionsJson = data.getJSONArray("special_request_actions");
					
					ArrayList<SpecialRequestAction> specialRequestActionsLocal = specialRequestActionTable.getSpecialRequestActions();
					ArrayList<SpecialRequestAction> specialRequestActionsServer = new ArrayList<SpecialRequestAction>();
					
					for(int i = 0; i < specialRequestActionsJson.length(); i++) {
						JSONObject specialRequestActionJson = (JSONObject)specialRequestActionsJson.get(i);
						int specialRequestActionId = specialRequestActionJson.getInt("special_request_action_id");
						String name = specialRequestActionJson.getString("name").toString();
															
						SpecialRequestAction specialRequestAction = new SpecialRequestAction();
						specialRequestAction.setSpecialRequestActionId(specialRequestActionId);
						specialRequestAction.setName(name);
						
						specialRequestActionsServer.add(specialRequestAction);
					}
					
					if(!Func.sameSpecialRequestActions(specialRequestActionsLocal, specialRequestActionsServer)) {					
						specialRequestActionTable.clearTable();
						
						for(int i = 0; i < specialRequestActionsServer.size(); i++) {
							specialRequestActionTable.insertSpecialRequestAction(specialRequestActionsServer.get(i));
						}
						
						MenuSession.specialRequestActions = specialRequestActionsServer;
						
					} else {					
						SharedPreferences.Editor editor = getSharedPreferences("SYNC", Context.MODE_PRIVATE).edit();
						editor.putBoolean("special_request_action_sync", true);
						editor.commit();
						
						Log.d("service", "special request action has sync .... ");
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
		specialRequestActionTask.cancel();					
	}
}




















