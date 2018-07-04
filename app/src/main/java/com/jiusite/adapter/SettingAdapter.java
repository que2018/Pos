package com.jiusite.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.jiusite.constant.CONS;
import com.jiusite.customview.SettingLinearLayout;
import com.jiusite.database.model.Setting;
import com.jiusite.global.Glob;
import com.jiusite.listener.EditTextWatcher;
import com.jiusite.main.R;

import java.util.ArrayList;


public class SettingAdapter extends BaseAdapter {
	
	private ArrayList<Setting> settingList;
	
    public SettingAdapter(ArrayList<Setting> settingList) {
		this.settingList = settingList;
    }
	
    @Override
    public int getCount() {
        return settingList.size();
    } 

    @Override
    public Setting getItem(int position) {	
        return settingList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
		
		View settingView;
		
		Setting setting = getItem(position);
		int type = setting.getType();
		int nameId = setting.getNameId();
		String name = Glob.context.getResources().getString(nameId);
		String value = setting.getValue();
		
		if(type == CONS.SETTING_TEXT) {
			settingView = LayoutInflater.from(parent.getContext()).inflate(R.layout.setting_text, parent, false);
			
			TextView nameText = (TextView)settingView.findViewById(R.id.name);
			TextView valueText = (TextView)settingView.findViewById(R.id.value);
			SettingLinearLayout wrapLayout = (SettingLinearLayout)settingView.findViewById(R.id.wrap);
			
			nameText.setText(name);
			valueText.setText(value);
			wrapLayout.setName(name);
			wrapLayout.setValue(value);
			wrapLayout.setType(CONS.SETTING_TEXT);
			
		} else if(type == CONS.SETTING_EDIT) {
			settingView = LayoutInflater.from(parent.getContext()).inflate(R.layout.setting_edit, parent, false);
			
			//get view
			TextView nameText = (TextView)settingView.findViewById(R.id.name);
			EditText valueEdit = (EditText)settingView.findViewById(R.id.value);
			final SettingLinearLayout wrapLayout = (SettingLinearLayout)settingView.findViewById(R.id.wrap);
			
			//set value
			nameText.setText(name);
			valueEdit.setText(value);
			wrapLayout.setName(name);
			wrapLayout.setValue(value);
			wrapLayout.setType(CONS.SETTING_EDIT);
			
			//add listener
			valueEdit.addTextChangedListener(new EditTextWatcher() {
				@Override
				public void onTextChanged(CharSequence s, int start, int count, int after) {
					String value = s.toString();
					wrapLayout.setValue(value);
				}
			});
	
		} else if(type == CONS.SETTING_RADIO) {
			settingView = LayoutInflater.from(parent.getContext()).inflate(R.layout.setting_radio, parent, false);
			
		} else if(type == CONS.SETTING_SWIPE) {
			settingView = LayoutInflater.from(parent.getContext()).inflate(R.layout.setting_swipe, parent, false);

		} else if(type == CONS.SETTING_IMAGE) {
			settingView = LayoutInflater.from(parent.getContext()).inflate(R.layout.setting_image, parent, false);

		} else {
			settingView = LayoutInflater.from(parent.getContext()).inflate(R.layout.setting_default, parent, false);
		}
					
        return settingView;
    }
} 


