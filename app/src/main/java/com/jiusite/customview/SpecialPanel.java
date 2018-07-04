package com.jiusite.customview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.jiusite.adapter.SpecialRequestActionAdapter;
import com.jiusite.adapter.SpecialRequestAdapter;
import com.jiusite.adapter.SpecialRequestCategoryAdapter;
import com.jiusite.global.Glob;
import com.jiusite.main.R;

public class SpecialPanel extends LinearLayout {
	
	private ListView specialRequestList;
	private RecyclerView specialRequestActionList;
	private RecyclerView specialRequestCategoryList;

    public SpecialPanel(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = LayoutInflater.from(context);
		View rootView = (ViewGroup)inflater.inflate(R.layout.special_panel, this);
		
		specialRequestList = (ListView)rootView.findViewById(R.id.special_request_list);
		specialRequestActionList = (RecyclerView)rootView.findViewById(R.id.special_request_action_list);
		specialRequestCategoryList = (RecyclerView)rootView.findViewById(R.id.special_request_category_list);
		
		specialRequestActionList.setLayoutManager(new LinearLayoutManager(Glob.context, LinearLayoutManager.HORIZONTAL, false));
		specialRequestCategoryList.setLayoutManager(new LinearLayoutManager(Glob.context, LinearLayoutManager.HORIZONTAL, false));
		
		SpecialRequestAdapter specialRequestAapter = new SpecialRequestAdapter();
		specialRequestList.setAdapter(specialRequestAapter); 
		
		SpecialRequestActionAdapter specialRequestActionAapter = new SpecialRequestActionAdapter();
		specialRequestActionList.setAdapter(specialRequestActionAapter);
		
		SpecialRequestCategoryAdapter specialRequestCategoryAapter = new SpecialRequestCategoryAdapter(new SpecialRequestCategoryListener());
		specialRequestCategoryList.setAdapter(specialRequestCategoryAapter);
	}
	
	private class SpecialRequestCategoryListener implements View.OnClickListener { 
        @Override
        public void onClick(View view) {
			int specialRequestCategoryId =((SpecialRequestCategoryButton)view).getSpecialRequestCategoryId();
									
			//reset special request
			SpecialRequestAdapter specialRequestAapter = new SpecialRequestAdapter(specialRequestCategoryId);
			specialRequestList.setAdapter(specialRequestAapter); 
        }
    }
}
















