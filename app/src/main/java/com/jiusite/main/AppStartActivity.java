package com.jiusite.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.ProgressBar;

import com.jiusite.constant.ADDR;
import com.jiusite.database.OrderProductTable;
import com.jiusite.database.OrderTable;
import com.jiusite.database.ProductTable;
import com.jiusite.database.SettingTable;
import com.jiusite.database.SpecialRequestActionTable;
import com.jiusite.database.SpecialRequestCategoryTable;
import com.jiusite.database.SpecialRequestTable;
import com.jiusite.database.TableTable;
import com.jiusite.database.CategoryTable;
import com.jiusite.database.UserTable;
import com.jiusite.database.model.Setting;
import com.jiusite.global.MenuPath;
import com.jiusite.session.MenuSession;
import com.jiusite.session.UserSession;
import com.jiusite.helper.Func;


public class AppStartActivity extends Activity {

	private boolean isInit;
	private ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appstart);
		
		//get view
		progressbar = (ProgressBar)findViewById(R.id.progressbar);
		
		//init database
		CategoryTable categoryTable = CategoryTable.getInstance(AppStartActivity.this);
		categoryTable.createTable(categoryTable.getWritableDatabase());
		
		ProductTable productTable = ProductTable.getInstance(AppStartActivity.this);
		productTable.createTable(productTable.getWritableDatabase());
		
		SpecialRequestTable specialRequestTable = SpecialRequestTable.getInstance(AppStartActivity.this);
		specialRequestTable.createTable(specialRequestTable.getWritableDatabase());
		
		SpecialRequestActionTable specialRequestActionTable = SpecialRequestActionTable.getInstance(AppStartActivity.this);
		specialRequestActionTable.createTable(specialRequestActionTable.getWritableDatabase());
		
		SpecialRequestCategoryTable specialRequestCategoryTable = SpecialRequestCategoryTable.getInstance(AppStartActivity.this);
		specialRequestCategoryTable.createTable(specialRequestCategoryTable.getWritableDatabase());
		
		TableTable tableTable = TableTable.getInstance(AppStartActivity.this);
		tableTable.createTable(tableTable.getWritableDatabase());
		
		OrderTable orderTable = OrderTable.getInstance(AppStartActivity.this);
		orderTable.createTable(orderTable.getWritableDatabase());
		
		OrderProductTable orderProductTable = OrderProductTable.getInstance(AppStartActivity.this);
		orderProductTable.createTable(orderProductTable.getWritableDatabase());
		
		SettingTable settingTable = SettingTable.getInstance(AppStartActivity.this);
		settingTable.createTable(settingTable.getWritableDatabase());
		
		UserTable userTable = UserTable.getInstance(AppStartActivity.this);
		userTable.createTable(userTable.getWritableDatabase());
		
		//init Func
		Context context = getApplicationContext();
		Func.setContext(context);
		
		//init screen
		Display display = getWindowManager().getDefaultDisplay();
		Func.initScreen(display);
		
		//init menu data to session
		MenuSession.topCategories = categoryTable.getTopCategories();
		MenuSession.categories = categoryTable.getCategories();
		MenuSession.products = productTable.getProducts();
		MenuSession.specialRequests = specialRequestTable.getSpecialRequests();
		MenuSession.specialRequestActions = specialRequestActionTable.getSpecialRequestActions();
		MenuSession.specialRequestCategories = specialRequestCategoryTable.getSpecialRequestCategories();
		MenuSession.formateData();

		//init Category Path
		MenuPath.add(0);
		
		//update server ip
		Setting setting = settingTable.getSetting("server_ip");
	
		if(!setting.isEmpty()) {
			String serverIP = setting.getValue();
			String SERVER = Func.addHttpHeader(serverIP);
			ADDR.updateADDR(SERVER);
		}
		
		//check init
		SharedPreferences prefs = getSharedPreferences("SETTING", MODE_PRIVATE); 
		isInit = prefs.getBoolean("is_init", false);
		
		if(!isInit) {
			Intent intent = new Intent(AppStartActivity.this, ServerSetActivity.class);
			startActivity(intent);		
		} else {
			//main
			if(UserSession.isLogin) {
				Intent intent = new Intent(AppStartActivity.this, MainActivity.class);
				startActivity(intent);

			//login
			} else {
				Intent intent = new Intent(AppStartActivity.this, LoginActivity.class);
				startActivity(intent);	
			}
		}
		
		AppStartActivity.this.finish();		
    }
}
