package com.jiusite.pos.service;

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
import com.jiusite.pos.database.UserTable;
import com.jiusite.pos.database.model.User;
import com.jiusite.helper.Func;
import com.jiusite.network.GetNetData;


public class UserService extends Service {
	
	private Timer timer;
	private UserTask userTask;
	private UserTable userTable;	

	@Override
	public IBinder onBind(Intent intent) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
    public void onCreate() {
		//init tables
		userTable = UserTable.getInstance(UserService.this);
		
		//load menu
		timer = new Timer();
		userTask = new UserTask();
		timer.schedule(userTask, 0, 2000); 
    }

	class UserTask extends TimerTask {
		public void run() {			
			try {				
				//get categories
				JSONObject outdata = GetNetData.getResult(ADDR.USERS);
				int httpCode = outdata.getInt("http_code");
				
				//http success
				if(httpCode == STATS.HTTP_OK) {
					JSONObject data = (JSONObject)outdata.get("data");
					JSONArray usersJson = data.getJSONArray("users");
					
					ArrayList<User> usersLocal = userTable.getUsers();
					ArrayList<User> usersServer = new ArrayList<User>();
					
					for(int i = 0; i < usersJson.length(); i++) {
						JSONObject userJson = (JSONObject)usersJson.get(i);
						int userId = userJson.getInt("user_id");
						String username = userJson.getString("username").toString();
						String permissions = userJson.getString("permissions").toString();
						
						User user = new User();
						user.setUserId(userId);
						user.setUsername(username);
						user.setPermissions(permissions);
						
						usersServer.add(user);
					}
					
					if(!Func.sameUsers(usersLocal, usersServer)) {				
						userTable.clearTable();
						
						for(int i = 0; i < usersServer.size(); i++) {
							userTable.insertUser(usersServer.get(i));
						}
					} else {					
						SharedPreferences.Editor editor = getSharedPreferences("SYNC", Context.MODE_PRIVATE).edit();
						editor.putBoolean("user_sync", true);
						editor.commit();
						
						Log.d("service", "user has sync .... ");
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
		userTask.cancel();
	}
}




















