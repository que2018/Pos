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
import com.jiusite.database.SpecialRequestCategoryTable;
import com.jiusite.database.model.SpecialRequestCategory;
import com.jiusite.helper.Func;
import com.jiusite.network.GetNetData;
import com.jiusite.session.MenuSession;


public class SpecialRequestCategoryService extends Service {
	
	private Timer timer;
	private SpecialRequestCategoryTask specialRequestCategoryTask;
	private SpecialRequestCategoryTable specialRequestCategoryTable;
	
	@Override
	public IBinder onBind(Intent intent) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
    public void onCreate() {
		//init tables
		specialRequestCategoryTable = SpecialRequestCategoryTable.getInstance(SpecialRequestCategoryService.this);

		//load menu
		timer = new Timer();
		specialRequestCategoryTask = new SpecialRequestCategoryTask();
		timer.schedule(specialRequestCategoryTask, 0, 2000); 
    }

	class SpecialRequestCategoryTask extends TimerTask {
		public void run() {			
			try {
				//get special request categories
				JSONObject outdata = GetNetData.getResult(ADDR.SPECIAL_REQUEST_CATEGORIES);
				int httpCode = outdata.getInt("http_code");
				
				//http success
				if(httpCode == STATS.HTTP_OK) {
					JSONObject data = (JSONObject)outdata.get("data");			
					JSONArray specialRequestCategoriesJson = data.getJSONArray("special_request_categories");
					
					ArrayList<SpecialRequestCategory> speicalRequestCategoriesLocal = specialRequestCategoryTable.getSpecialRequestCategories();
					ArrayList<SpecialRequestCategory> speicalRequestCategoriesServer = new ArrayList<SpecialRequestCategory>();
					
					for(int i = 0; i < specialRequestCategoriesJson.length(); i++) {
						JSONObject specialRequestCategoryJson = (JSONObject)specialRequestCategoriesJson.get(i);
						int specialRequestCategoryId = specialRequestCategoryJson.getInt("special_request_category_id");
						String name = specialRequestCategoryJson.getString("name").toString();
									
						SpecialRequestCategory specialRequestCategory = new SpecialRequestCategory();
						specialRequestCategory.setSpecialRequestCategoryId(specialRequestCategoryId);
						specialRequestCategory.setName(name);
						
						speicalRequestCategoriesServer.add(specialRequestCategory);
					}
					
					if(!Func.sameSpecialRequestCategories(speicalRequestCategoriesLocal, speicalRequestCategoriesServer)) {					
						specialRequestCategoryTable.clearTable();
						
						for(int i = 0; i < speicalRequestCategoriesServer.size(); i++) {
							specialRequestCategoryTable.insertSpecialRequestCategory(speicalRequestCategoriesServer.get(i));
						}
						
						MenuSession.specialRequestCategories = speicalRequestCategoriesServer;
						
					} else {					
						SharedPreferences.Editor editor = getSharedPreferences("SYNC", Context.MODE_PRIVATE).edit();
						editor.putBoolean("special_request_category_sync", true);
						editor.commit();
						
						Log.d("service", "special request category has sync .... ");
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
		specialRequestCategoryTask.cancel();	
	}
}




















