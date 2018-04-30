package com.jiusite.pos.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;

import com.jiusite.customview.SpecialRequestActionButton;
import com.jiusite.pos.database.model.SpecialRequestAction;
import com.jiusite.global.Glob;
import com.jiusite.session.MenuSession;
import com.jiusite.pos.R;
import com.jiusite.session.PlaceSession;


public class SpecialRequestActionAdapter extends RecyclerView.Adapter<SpecialRequestActionAdapter.ViewHolder> {
	
    private ArrayList<SpecialRequestAction> specialRequestActions;
	private SpecialRequestActionListener specialRequestActionListener;

	public SpecialRequestActionAdapter() {
		this.specialRequestActions = MenuSession.specialRequestActions;
		this.specialRequestActionListener = new SpecialRequestActionListener();
    }
	
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public SpecialRequestActionButton spaButton;
		
		private ViewHolder(View view) {
			super(view);
			this.spaButton = (SpecialRequestActionButton)view.findViewById(R.id.spa_btn);
		}
    }

    @Override
    public SpecialRequestActionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.special_request_action_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {	
		//get item
		SpecialRequestAction SpecialRequestAction = specialRequestActions.get(position);
		
		//set values		
		String name = SpecialRequestAction.getName();
		int specialRequestActionId = SpecialRequestAction.getSpecialRequestActionId();
				
		holder.spaButton.setText(name);
		holder.spaButton.setSpecialRequestActionId(specialRequestActionId);
		
		//set background
		if(PlaceSession.selectSpecialRequestActionId == specialRequestActionId) {
			holder.spaButton.setBackgroundDrawable(Glob.context.getResources().getDrawable(R.drawable.rect_red));
		} else {
			holder.spaButton.setBackgroundDrawable(Glob.context.getResources().getDrawable(R.drawable.btn_special_request_item));
		}
		
		//add listener
		holder.spaButton.setOnClickListener(specialRequestActionListener);
    }

    @Override
    public int getItemCount() {
        return specialRequestActions.size();
    }
	
	class SpecialRequestActionListener implements View.OnClickListener {
		@Override
		public void onClick(View view) {			
			PlaceSession.selectSpecialRequestActionId =((SpecialRequestActionButton)view).getSpecialRequestActionId();
			
			notifyDataSetChanged();
		}
	}
}




