package com.jiusite.main;

import java.util.ArrayList;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.jiusite.adapter.MenuAdapter;
import com.jiusite.constant.ADDR;
import com.jiusite.constant.CONS;
import com.jiusite.constant.STATS;
import com.jiusite.database.model.Category;
import com.jiusite.global.MenuPath;
import com.jiusite.global.Glob;
import com.jiusite.session.MenuSession;
import com.jiusite.global.PlaceCounter;
import com.jiusite.session.PlaceSession;
import com.jiusite.listener.SwipeListener;
import com.jiusite.network.PostNetData;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;


public class MenuFragment extends PosFragment {
	
	private ListView menuList;
	private Button backButton;
	private TextView countText;
	private ProgressBar progressbar;
	private SwipeLayout swipeLayout;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
				
        View rootView = (ViewGroup)inflater.inflate(R.layout.fragment_menu, container, false);
		
		//get view
		countText = (TextView)rootView.findViewById(R.id.count);
		backButton = (Button)rootView.findViewById(R.id.back);		
		menuList = (ListView)rootView.findViewById(R.id.menu_list);
		swipeLayout =(SwipeLayout)rootView.findViewById(R.id.swipe);
		progressbar = (ProgressBar)rootView.findViewById(R.id.progressbar);
		
		swipeLayout.setSwipeEnabled(false);
		
		//init global vars
		Glob.backButton = backButton;
		PlaceCounter.setView(countText);
		
		//load menu
		MenuAdapter menuAdapter = new MenuAdapter();
		menuList.setAdapter(menuAdapter);
		
		//init listeners
		backButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				MenuPath.back();
							
				int categoryId = MenuPath.current();
				
				if(categoryId == 0) {
					MenuAdapter menuAdapter = new MenuAdapter();
					menuList.setAdapter(menuAdapter);
				} else {
					ArrayList<Category> categories = MenuSession.getChildCategories(categoryId);
					MenuAdapter menuAdapter = new MenuAdapter(categories);
					menuList.setAdapter(menuAdapter);
				}
			}
		});
		
		backButton.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View view) {
				Vibrator vibrator = (Vibrator)Glob.context.getSystemService(Context.VIBRATOR_SERVICE);
				vibrator.vibrate(150);
				
				swipeLayout.setSwipeEnabled(true);
				swipeLayout.open(true);
				
				return true;
			}
		});
		
		swipeLayout.addSwipeListener(new SwipeListener() {
			@Override
			public void onClose(SwipeLayout swipeLayout) {				
				swipeLayout.setSwipeEnabled(false);
			}
		});
		
        return rootView;
    }
	
	@Override
	public void onResume() {
		super.onResume();
		
		//maybe place task
		if(!PlaceSession.updated) {
			if(PlaceSession.type == CONS.PLACESESSION_PLACE) {
				menuList.setVisibility(View.INVISIBLE);
				progressbar.setVisibility(View.VISIBLE);
			
				PlaceTask placeTask = new PlaceTask();
				placeTask.execute();
			}
		}
	}
	
	class PlaceTask extends AsyncTask<Void, Void, Void> {
		
		private JSONObject outdata;
		
		@Override
        protected Void doInBackground(Void... params) {
			ArrayList<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
			String tableIdStr = Integer.toString(PlaceSession.tableId);
			nameValuePairs.add(new BasicNameValuePair("id", tableIdStr));
			outdata = PostNetData.getResult(ADDR.TABLE_PLACE, nameValuePairs);
		
			return null;
		}
		
		@Override
        protected void onPostExecute(Void v) {
			menuList.setVisibility(View.VISIBLE);
			progressbar.setVisibility(View.INVISIBLE);
						
			try {
				JSONObject data = (JSONObject)outdata.get("data");
				int status = data.getInt("status");
			
				if(status == STATS.TABLE_PLACE_SUCCESS) {
					PlaceSession.type = CONS.PLACESESSION_PLACED_DINING;
					PlaceSession.updated = false;
					
					Glob.placeFragment.updatePlaceView(false);
				}

			} catch(JSONException e) {
				e.printStackTrace();
			}
		}
	}
}
























