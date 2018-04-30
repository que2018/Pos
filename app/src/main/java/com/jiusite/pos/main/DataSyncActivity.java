package com.jiusite.pos.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.jiusite.pos.database.CategoryTable;
import com.jiusite.pos.database.ProductTable;
import com.jiusite.pos.database.SpecialRequestActionTable;
import com.jiusite.pos.database.SpecialRequestCategoryTable;
import com.jiusite.pos.database.SpecialRequestTable;
import com.jiusite.global.Glob;
import com.jiusite.session.MenuSession;
import com.jiusite.service.CategoryService;
import com.jiusite.service.ProductService;
import com.jiusite.service.SpecialRequestActionService;
import com.jiusite.service.SpecialRequestCategoryService;
import com.jiusite.service.SpecialRequestService;
import com.jiusite.service.UserService;

import java.util.Timer;
import java.util.TimerTask;


public class DataSyncActivity extends Activity {
	
	private int percentage;
	
	private Timer timer;
	private PercentageTask percentageTask;
	
	private TextView percentageText;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_sync);
		
		percentageText = (TextView)findViewById(R.id.percentage);
		
		percentage = 0;
		timer = new Timer();
		percentageTask = new PercentageTask();
		timer.schedule(percentageTask, 0, 500);
		
		//import menu
		startService(new Intent(DataSyncActivity.this, UserService.class));
		startService(new Intent(DataSyncActivity.this, CategoryService.class));
		startService(new Intent(DataSyncActivity.this, ProductService.class));
		startService(new Intent(DataSyncActivity.this, SpecialRequestService.class));
		startService(new Intent(DataSyncActivity.this, SpecialRequestActionService.class));
		startService(new Intent(DataSyncActivity.this, SpecialRequestCategoryService.class));
		
		//check import status
		ImportCheckTask importCheckTask = new ImportCheckTask();
		importCheckTask.execute();
    }
	
	class PercentageTask extends TimerTask {
		public void run() {			
			runOnUiThread(new Runnable() {
				public void run() {
					String percentageStr = Integer.toString(percentage) + "%";
					percentageText.setText(percentageStr);
				}
			});
		}
	}
	
	class ImportCheckTask extends AsyncTask<Void, Void, Void> {
		
		private boolean userSynced = false;
		private boolean categorySynced = false;
		private boolean productSynced = false;
		private boolean specialRequestSynced = false;
		private boolean specialRequestActionSynced = false;
		private boolean specialRequestCategorySynced = false;
		
		@Override
        protected Void doInBackground(Void... params) {			
			try{
				while(true) {
					Thread.sleep(500); 
										
					SharedPreferences prefs = getSharedPreferences("SYNC", MODE_PRIVATE);
					boolean userSync = prefs.getBoolean("user_sync", false);
					boolean categorySync = prefs.getBoolean("category_sync", false);
					boolean productSync = prefs.getBoolean("product_sync", false);
					boolean specialRequestSync = prefs.getBoolean("special_request_sync", false);
					boolean specialRequestActionSync = prefs.getBoolean("special_request_action_sync", false);
					boolean specialRequestCategorySync = prefs.getBoolean("special_request_category_sync", false);
					
					if(userSync && !userSynced) {percentage += 16; userSynced = true;}
					if(categorySync && !categorySynced){percentage += 16; categorySynced = true;}
					if(productSync && !productSynced) {percentage += 16; productSynced = true;}
					if(specialRequestSync && !specialRequestSynced) {percentage += 16; specialRequestSynced = true;}
					if(specialRequestActionSync && !specialRequestActionSynced) {percentage += 16; specialRequestActionSynced = true;}
					if(specialRequestCategorySync && !specialRequestCategorySynced) {percentage += 16; specialRequestCategorySynced = true;}
					
					if(userSync && categorySync && productSync && specialRequestSync && specialRequestActionSync && specialRequestCategorySync)
						break; 					
				}
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			
			return null;
		}
		
		@Override
        protected void onPostExecute(Void v) {			
			//stop service
			stopService(new Intent(DataSyncActivity.this, UserService.class));
			stopService(new Intent(DataSyncActivity.this, CategoryService.class));
			stopService(new Intent(DataSyncActivity.this, ProductService.class));
			stopService(new Intent(DataSyncActivity.this, SpecialRequestService.class));
			stopService(new Intent(DataSyncActivity.this, SpecialRequestActionService.class));
			stopService(new Intent(DataSyncActivity.this, SpecialRequestCategoryService.class));
				
			//set init status 
			SharedPreferences.Editor editor = getSharedPreferences("SETTING", Context.MODE_PRIVATE).edit();
			editor.putBoolean("is_init", true);
			editor.commit();
			
			//load menu data to glob
			CategoryTable categoryTable = CategoryTable.getInstance(DataSyncActivity.this);
			ProductTable productTable = ProductTable.getInstance(DataSyncActivity.this);
			SpecialRequestTable specialRequestTable = SpecialRequestTable.getInstance(DataSyncActivity.this);
			SpecialRequestActionTable specialRequestActionTable = SpecialRequestActionTable.getInstance(DataSyncActivity.this);
			SpecialRequestCategoryTable specialRequestCategoryTable = SpecialRequestCategoryTable.getInstance(DataSyncActivity.this);
			
			MenuSession.topCategories = categoryTable.getTopCategories();
			MenuSession.categories = categoryTable.getCategories();
			MenuSession.products = productTable.getProducts();
			MenuSession.specialRequests = specialRequestTable.getSpecialRequests();
			MenuSession.specialRequestActions = specialRequestActionTable.getSpecialRequestActions();
			MenuSession.specialRequestCategories = specialRequestCategoryTable.getSpecialRequestCategories();
			MenuSession.formateData();

			//go to login
			Intent intent = new Intent(DataSyncActivity.this, LoginActivity.class);
			startActivity(intent);			
			DataSyncActivity.this.finish();
		}
	}
}
