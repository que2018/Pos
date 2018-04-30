package com.jiusite.pos.service;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.jiusite.constant.ADDR;
import com.jiusite.constant.STATS;
import com.jiusite.pos.database.model.Table;
import com.jiusite.session.RoomSession;
import com.jiusite.network.GetNetData;


public class TableService extends Service {
	
	@Override
	public IBinder onBind(Intent intent) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
    public void onCreate() {			
		//load menu
		Timer timer = new Timer();
		TableTask tableTask = new TableTask();
		timer.schedule(tableTask, 0, 4000); 
    }

	class TableTask extends TimerTask {
		public void run() {
			try {
				//get tables
				JSONObject outdata = GetNetData.getResult(ADDR.TABLES);
				int httpCode = outdata.getInt("http_code");
				
				//http success
				if(httpCode == STATS.HTTP_OK) {
					JSONObject data = (JSONObject)outdata.get("data");
					JSONArray tablesJson = data.getJSONArray("tables");
					
					ArrayList<Table> tables = new ArrayList<Table>();
					
					for(int i = 0; i < tablesJson.length(); i++) {
						JSONObject tableJson = (JSONObject)tablesJson.get(i);
						int customer = tableJson.getInt("customer");
						String name = tableJson.getString("name");
						double x = tableJson.getDouble("x");
						double y = tableJson.getDouble("y");
						int tableId = tableJson.getInt("table_id");
						int orderId = tableJson.getInt("order_id");
						int status = tableJson.getInt("status"); 					
						
						Table table = new Table();
						table.setCustomer(customer);
						table.setName(name);
						table.setX(x);
						table.setY(y);
						table.setAnim(0);
						table.setTableId(tableId);
						table.setOrderId(orderId);
						table.setStatus(status);
						tables.add(table);					
					}
					
					RoomSession.syncTables(tables);
				}
			} catch(JSONException e) {
				e.printStackTrace();
			}
		}
	}
}




















