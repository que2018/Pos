package com.jiusite.main;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.jiusite.adapter.SettingAdapter;
import com.jiusite.database.SettingTable;
import com.jiusite.database.model.Setting;
import com.jiusite.global.Glob;
import com.jiusite.helper.ImageHelper;
import com.jiusite.session.UserSession;


public class UserFragment extends PosFragment {
    
	private TextView nameText;
	private Button logoutButton;
	private ListView settingList;
	private ImageView portraitImage;
	private SwipeLayout swipeLayout;
	private SettingTable settingTable;
	private ArrayList<Setting> settings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_user, container, false);

		//get view
		nameText = (TextView)rootView.findViewById(R.id.name);
		logoutButton = (Button)rootView.findViewById(R.id.logout);
		swipeLayout =(SwipeLayout)rootView.findViewById(R.id.swipe);
		portraitImage = (ImageView)rootView.findViewById(R.id.portrait);
		settingList = (ListView)rootView.findViewById(R.id.setting_list);
			
		Bitmap portrait = BitmapFactory.decodeResource(getResources(), R.drawable.portrait);
		Bitmap portrait1 = ImageHelper.getRoundedCornerBitmap(portrait, 200);
		portraitImage.setImageBitmap(portrait1);
			
		swipeLayout.setSwipeEnabled(false);
			
		//get settings
		settingTable = SettingTable.getInstance(Glob.context);
		settings = settingTable.getSettings();
		
		//set adapter
		SettingAdapter settingAdapter = new SettingAdapter(settings);
		settingList.setAdapter(settingAdapter);
		
		//set value
		nameText.setText(UserSession.username);
			
		logoutButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				UserSession.clear();
				Intent intent = new Intent();
				intent.setClass(Glob.activity, LoginActivity.class);
				startActivity(intent);
				Glob.activity.finish();
			}
		});
		
        return rootView;
    }
}
