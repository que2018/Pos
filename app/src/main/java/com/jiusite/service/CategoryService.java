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
import com.jiusite.database.CategoryTable;
import com.jiusite.database.model.Category;
import com.jiusite.helper.Func;
import com.jiusite.network.GetNetData;
import com.jiusite.session.MenuSession;


public class CategoryService extends Service {
	
	private Timer timer;
	private CategoryTask categoryTask;
	private CategoryTable categoryTable;	

	@Override
	public IBinder onBind(Intent intent) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
    public void onCreate() {
		//init tables
		categoryTable = CategoryTable.getInstance(CategoryService.this);
		
		//load menu
		timer = new Timer();
		categoryTask = new CategoryTask();
		timer.schedule(categoryTask, 0, 2000); 
    }

	class CategoryTask extends TimerTask {
		public void run() {			
			try {				
				JSONObject outdata = GetNetData.getResult(ADDR.CATEGORIES);
				int httpCode = outdata.getInt("http_code");
				
				//http success
				if(httpCode == STATS.HTTP_OK) {
					JSONObject data = (JSONObject)outdata.get("data");
					JSONArray categoriesJson = data.getJSONArray("categories");
				
					ArrayList<Category> categoriesLocal = categoryTable.getCategories();
					ArrayList<Category> categoriesServer = new ArrayList<Category>();
					
					for(int i = 0; i < categoriesJson.length(); i++) {
						JSONObject categoryJson = (JSONObject)categoriesJson.get(i);
						int categoryId = categoryJson.getInt("category_id");
						int parentId = categoryJson.getInt("parent_id");
						String name = categoryJson.getString("name").toString();
						
						Category category = new Category();
						category.setCategoryId(categoryId);
						category.setParentId(parentId);
						category.setName(name);
						
						categoriesServer.add(category);
					}
					
					if(!Func.sameCategories(categoriesLocal, categoriesServer)) {				
						categoryTable.clearTable();
						
						for(int i = 0; i < categoriesServer.size(); i++) {
							categoryTable.insertCategory(categoriesServer.get(i));
						}
						
						MenuSession.categories = categoriesServer;
						
					} else {					
						SharedPreferences.Editor editor = getSharedPreferences("SYNC", Context.MODE_PRIVATE).edit();
						editor.putBoolean("category_sync", true);
						editor.commit();
						
						Log.d("category service", "category has sync .... ");
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
		categoryTask.cancel();
	}
}




















