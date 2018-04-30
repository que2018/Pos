package com.jiusite.pos.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jiusite.customview.SpecialRequestButton;
import com.jiusite.pos.database.model.SpecialRequest;
import com.jiusite.pos.database.model.SpecialRequestCategory;
import com.jiusite.pos.R;
import com.jiusite.session.MenuSession;


public class SpecialRequestAdapter extends BaseAdapter {
	
	private ArrayList<HashMap> specialRequestList;

	public SpecialRequestAdapter() {
		this.specialRequestList = new ArrayList<HashMap>();	
		
		SpecialRequestCategory specialRequestCategory = MenuSession.specialRequestCategories.get(0);
		int specialRequestCategoryId = specialRequestCategory.getSpecialRequestCategoryId();
		ArrayList<SpecialRequest> specialRequests = MenuSession.getSpecialRequests(specialRequestCategoryId);
		initAdapter(specialRequests);
	}
	
	public SpecialRequestAdapter(int specialRequestCategoryId) {
		this.specialRequestList = new ArrayList<HashMap>();	
		
		ArrayList<SpecialRequest> specialRequests = MenuSession.getSpecialRequests(specialRequestCategoryId);
		initAdapter(specialRequests);
	}
	
    private void initAdapter(ArrayList<SpecialRequest> specialRequests) {		
		try {	
			int count = 0;
			HashMap<Integer, SpecialRequest> tableRow = new HashMap<Integer, SpecialRequest>();
			
			for(int i = 0; i < specialRequests.size(); i++) {				
				SpecialRequest specialRequest = specialRequests.get(i);
																	
				if(count == 0) {
					tableRow = new HashMap<Integer, SpecialRequest>();
					tableRow.put(count, specialRequest);
				
					if(i == (specialRequests.size() - 1)) {
						specialRequestList.add(tableRow);
					}
					
					count ++;
					
				} else if(count < 2) {
					tableRow.put(count, specialRequest);
					
					if(i == (specialRequests.size() - 1)) {
						specialRequestList.add(tableRow);
					}
					
					count++;
					
				} else {
					tableRow.put(count, specialRequest);
					specialRequestList.add(tableRow);
					count = 0;
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
	
    @Override
    public int getCount() {
        return specialRequestList.size();
    } 

    @Override
    public HashMap<Integer, SpecialRequest> getItem(int position) {	
        return specialRequestList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
		
		View specialRequestView;
		HashMap<Integer, SpecialRequest> item = getItem(position);
		
		int size = item.size();	
		
		int R_layout_id;
			
		switch(size) {
			case 1:
				R_layout_id = R.layout.special_request_row1;
				break;
			case 2:
				R_layout_id = R.layout.special_request_row2;
				break;
			default:
				R_layout_id = R.layout.special_request_row3;
				break;
		}
			
		specialRequestView = LayoutInflater.from(parent.getContext()).inflate(R_layout_id, parent, false);
		
		SpecialRequest specialRequest0 = item.get(0);
			
		SpecialRequestButton specialRequestButton0 = (SpecialRequestButton)specialRequestView.findViewById(R.id.special_request_item0);
		String name0 = specialRequest0.getName();
		int specialRequestId0 = specialRequest0.getSpecialRequestId();
	
		specialRequestButton0.setText(name0);
		specialRequestButton0.setSpecialRequestId(specialRequestId0);
				
		if(size > 1) {
			SpecialRequest specialRequest1 = item.get(1);
			
			SpecialRequestButton specialRequestButton1 = (SpecialRequestButton)specialRequestView.findViewById(R.id.special_request_item1);
			String name1 = specialRequest1.getName();
			int specialRequestId1 = specialRequest1.getSpecialRequestId();
			
			specialRequestButton1.setText(name1);
			specialRequestButton1.setSpecialRequestId(specialRequestId1);
		} 
		
		if(size > 2) {
			SpecialRequest specialRequest2 = item.get(2);
			
			SpecialRequestButton specialRequestButton2 = (SpecialRequestButton)specialRequestView.findViewById(R.id.special_request_item2);
			String name2 = specialRequest2.getName();
			int specialRequestId2 = specialRequest2.getSpecialRequestId();
			
			specialRequestButton2.setText(name2);
			specialRequestButton2.setSpecialRequestId(specialRequestId2);
		}  
		
        return specialRequestView;
    }

	private String encodeSpecialRequest(int specialRequestActionId, int specialRequestId) {
		return specialRequestActionId + "_" +specialRequestId;
	}
} 


