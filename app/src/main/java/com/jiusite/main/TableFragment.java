package com.jiusite.main;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.jiusite.constant.CONS;
import com.jiusite.constant.STATS;
import com.jiusite.customview.TableButton;
import com.jiusite.database.model.Table;
import com.jiusite.global.Glob;
import com.jiusite.session.PlaceSession;
import com.jiusite.adapter.TableAdapter;
import com.jiusite.session.RoomSession;


public class TableFragment extends PosFragment {
	
	private Timer timer;
	private TableTask tableTask;
	
	private Button togoButton;
	private Button pickupButton;
	private ListView tableList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.d("xxxx", "looks like the table fragment is create over again .... ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//get view
		ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_table, container, false);
		tableList = (ListView)rootView.findViewById(R.id.table_list);
		togoButton = (Button)rootView.findViewById(R.id.togo);
		pickupButton = (Button)rootView.findViewById(R.id.pickup);

		//load table
		TableAdapter menuAdapter = new TableAdapter(new TableListener());
		tableList.setAdapter(menuAdapter);
		
		//add listeners
		togoButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				PlaceSession.clear();
				PlaceSession.type = CONS.PLACESESSION_PLACED_TOGO;
				PlaceSession.customer = "togo";
				PlaceSession.updated = false;
				moveToFragment(1);
			}
		});
		
		//add listeners
		pickupButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				PlaceSession.clear();
				PlaceSession.type = CONS.PLACESESSION_PLACED_PICKUP;
				PlaceSession.customer = "pickup";
				PlaceSession.updated = false;
				moveToFragment(1);
			}
		});
		
		return rootView;
    }
	
	@Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
				
		if(visible) {			
			timer = new Timer();
			tableTask = new TableTask();
			timer.schedule(tableTask, 0, 3000);
			
		} else {
			if(timer != null) {
				timer.cancel();
				timer.purge();
				timer = null;
			}
		}
    }

	class TableTask extends TimerTask {
		public void run() {
			Glob.activity.runOnUiThread(new Runnable() {
				public void run() {
					//load table
					TableAdapter menuAdapter = new TableAdapter(new TableListener());
					tableList.setAdapter(menuAdapter);
				}
			});
		}
	}
	
	private class TableListener implements View.OnClickListener { 
        @Override
        public void onClick(View view) {	
			TableButton tableButton = (TableButton)view;
		
			String name = tableButton.getName();
			
			Table table = RoomSession.getTableByName(name);
			
			int status  = table.getStatus();
			int tableId = table.getTableId();
			int orderId = table.getOrderId();
			
			//place seat
			if(status == STATS.TABLE_EMPTY) {
				PlaceSession.clear();
				PlaceSession.updated  = false;
				PlaceSession.customer = name;
				PlaceSession.tableId = tableId;
				PlaceSession.type = CONS.PLACESESSION_PLACE;
				
				Intent intent = new Intent(Glob.activity, PlaceActivity.class);
				intent.putExtra("fragment_index", 0);
				startActivity(intent);	
			}

			//place order
			if(status == STATS.TABLE_PLACED) {
				PlaceSession.clear();
				PlaceSession.updated = false;
				PlaceSession.customer = name;
				PlaceSession.tableId = tableId;
				PlaceSession.type = CONS.PLACESESSION_PLACED_DINING;

				Intent intent = new Intent(Glob.activity, PlaceActivity.class);
				intent.putExtra("fragment_index", 1);
				startActivity(intent);	
			}

			//view order
			if(status == STATS.TABLE_ORDERED) {						
				PlaceSession.clear();
				PlaceSession.updated = false;
				PlaceSession.customer = name;
				PlaceSession.tableId = tableId;
				PlaceSession.orderId = orderId;
				PlaceSession.type = CONS.PLACESESSION_ORDERED;
				
				Intent intent = new Intent(Glob.activity, PlaceActivity.class);
				intent.putExtra("fragment_index", 1);
				startActivity(intent);	
			}
        }
    }
}
