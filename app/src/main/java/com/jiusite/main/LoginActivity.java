package com.jiusite.main;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SwipeLayout;
import com.jiusite.constant.ADDR;
import com.jiusite.constant.STATS;
import com.jiusite.customview.DotsList;
import com.jiusite.database.UserTable;
import com.jiusite.database.model.User;
import com.jiusite.session.UserSession;
import com.jiusite.listener.OnClickListener;
import com.jiusite.network.GetNetData;


public class LoginActivity extends Activity {
	
	private DotsList dotsList;
	private UserTable userTable;
	private Button syncUserButton;
	private SwipeLayout swipeLayout;
	private ProgressBar progressbar;
	private LinearLayout panelLayout;
	private ProgressBar progressbarUser;
	private ArrayList<String> passwordList;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_login);
		
		//get view
		dotsList = (DotsList)findViewById(R.id.dots);
		panelLayout = (LinearLayout)findViewById(R.id.panel);
		progressbar = (ProgressBar)findViewById(R.id.progressbar);
		swipeLayout = (SwipeLayout)findViewById(R.id.swipe);
		syncUserButton = (Button)findViewById(R.id.sync_user);
		progressbarUser = (ProgressBar)findViewById(R.id.progressbar_user);
				
		//init variable
		passwordList = new ArrayList<String>();
		
		//get database
		userTable = UserTable.getInstance(LoginActivity.this);
		
		//add listener to dotsList
		dotsList.addListener(new DotsListener());
		
		//add listener to sync
		syncUserButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				progressbarUser.setVisibility(View.VISIBLE);
				syncUserButton.setVisibility(View.INVISIBLE);
				
				SyncUserTask syncUserTask = new SyncUserTask();
				syncUserTask.execute();
			}
		});
	}
	
	public void key(View view) {
		String digit = ((Button)view).getText().toString();
		passwordList.add(digit);
		
		int size = passwordList.size();
		
		dotsList.select(size - 1);
		
		if(size == 4) {			
			LoginTask loginTask = new LoginTask();
			loginTask.execute();
		}
	}
	
	private String toUserName(ArrayList<String> passwordList) {		
		String username = new String();
		
		for(int i = 0; i < passwordList.size(); i++) {
			String digit = passwordList.get(i);
			username += digit;
		}
		
		return username;
	}
	
	class DotsListener extends OnClickListener {
		@Override
		public void onClick(View v) {			
			passwordList.clear();
			
			dotsList.unSelect(0);
			dotsList.unSelect(1);
			dotsList.unSelect(2);
			dotsList.unSelect(3);
		}
	}
	
	class LoginTask extends AsyncTask<Void, Void, Void> {
		
		int status = STATS.LOGIN_ERROR;
		User user = new User();
		
		@Override
        protected Void doInBackground(Void... params) {	
			String username = toUserName(passwordList);
			user = userTable.getUserByUsername(username);
			
			if(!user.isEmpty())
				status = STATS.LOGIN_SUCCESS;
			
			return null;
		}
		
		@Override
        protected void onPostExecute(Void v) {								
			//login success
			if(status == STATS.LOGIN_SUCCESS) {
				UserSession.isLogin = true;
				UserSession.userId = user.getUserId();
				UserSession.username = user.getUsername();
				UserSession.permissions = user.getPermissions();
								
				Intent intent = new Intent();
				intent.setClass(LoginActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			
			//login error
			} else {
				passwordList.clear();
				dotsList.clearSelect();
			
				panelLayout.setVisibility(View.VISIBLE);
				YoYo.with(Techniques.Shake).duration(600).playOn(panelLayout);
			}
		}
	}
	
	class SyncUserTask extends AsyncTask<Void, Void, Void> {
		
		@Override
        protected Void doInBackground(Void... params) {	
			try {				
				JSONObject outdata = GetNetData.getResult(ADDR.USERS);
				int httpCode = outdata.getInt("http_code");
				
				//http success
				if(httpCode == STATS.HTTP_OK) {
					JSONObject data = (JSONObject)outdata.get("data");
					JSONArray usersJson = data.getJSONArray("users");
					
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
					
					userTable.clearTable();
						
					for(int i = 0; i < usersServer.size(); i++) {
						userTable.insertUser(usersServer.get(i));
					}
				}
			} catch(JSONException e) {
				e.printStackTrace();
			}
			
			return null;
		}
		
		@Override
        protected void onPostExecute(Void v) {
			syncUserButton.setVisibility(View.VISIBLE);
			progressbarUser.setVisibility(View.INVISIBLE);
			swipeLayout.close(true);
		}
	}
}

