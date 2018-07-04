package com.jiusite.customview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jiusite.listener.OnClickListener;
import com.jiusite.main.R;

public class DotsList extends LinearLayout {
	
	private int selected_bg_id;
	private int unselected_bg_id;
	
	private ImageView dot0Image;
	private ImageView dot1Image;
	private ImageView dot2Image;
	private ImageView dot3Image;
	
    public DotsList(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = LayoutInflater.from(context);
		View rootView = (ViewGroup)inflater.inflate(R.layout.dots_list, this);
		
		//get view
		dot0Image = (ImageView)rootView.findViewById(R.id.dot0);
		dot1Image = (ImageView)rootView.findViewById(R.id.dot1);
		dot2Image = (ImageView)rootView.findViewById(R.id.dot2);
		dot3Image = (ImageView)rootView.findViewById(R.id.dot3);
		
		//init drawable background
		selected_bg_id = R.drawable.dot_select_circle;
		unselected_bg_id = R.drawable.dot_unselect_circle;
		
		//set background
		dot0Image.setImageResource(R.drawable.dot_unselect_circle);
		dot1Image.setImageResource(R.drawable.dot_unselect_circle);
		dot2Image.setImageResource(R.drawable.dot_unselect_circle);
		dot3Image.setImageResource(R.drawable.dot_unselect_circle);
	}
	
	public void select(int position) {
		switch(position) {
			case 0:
				dot0Image.setImageResource(selected_bg_id);
				break;
			case 1:
				dot1Image.setImageResource(selected_bg_id);
				break;
			case 2:
				dot2Image.setImageResource(selected_bg_id);
				break;
			case 3:
				dot3Image.setImageResource(selected_bg_id);
				break;
		}
	}
	
	public void unSelect(int position) {
		switch(position) {
			case 0:
				dot0Image.setImageResource(unselected_bg_id);
				break;
			case 1:
				dot1Image.setImageResource(unselected_bg_id);
				break;
			case 2:
				dot2Image.setImageResource(unselected_bg_id);
				break;
			case 3:
				dot3Image.setImageResource(unselected_bg_id);
				break;
		}
	}
	
	private boolean isSelected(int position) {
		
		boolean isSelected = false;
		Drawable selectedBg = getResources().getDrawable(selected_bg_id);
		
		switch (position) {
			case 0:
				if(dot0Image.getDrawable() == selectedBg)
					isSelected = true;
				break;
			case 1:
				if(dot1Image.getDrawable() == selectedBg)
					isSelected = true;
				break;
			case 2:
				if(dot2Image.getDrawable() == selectedBg)
					isSelected = true;
				break;
			case 3:
				if(dot3Image.getDrawable() == selectedBg)
					isSelected = true;
				break;
		}
		
		return isSelected;
	}
	
	private int currentPosition() {
		if(isSelected(3)) {
			return 3;
		} else if(isSelected(2)) {
			return 2;
		} else if(isSelected(1)) {
			return 1;
		} else if (isSelected(0)) {
			return 0;
		} else {
			return -1;
		} 
	}
	
	public void clearSelect() {
		dot0Image.setImageResource(unselected_bg_id);
		dot1Image.setImageResource(unselected_bg_id);
		dot2Image.setImageResource(unselected_bg_id);
		dot3Image.setImageResource(unselected_bg_id);
	}
	
	public void setSelectedBg(int selected_bg_id) {
		this.selected_bg_id = selected_bg_id;
	}
	
	public void setUnselectedBg(int unselected_bg_id) {
		this.unselected_bg_id = unselected_bg_id;
	}
	
	public void addListener(OnClickListener onClickListener) {
		dot0Image.setOnClickListener(onClickListener);
		dot1Image.setOnClickListener(onClickListener);
		dot2Image.setOnClickListener(onClickListener);
		dot3Image.setOnClickListener(onClickListener);
	}
}
















