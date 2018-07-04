package com.jiusite.adapter;

import java.util.ArrayList;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jiusite.customview.SpecialRequestCategoryButton;
import com.jiusite.database.model.SpecialRequestCategory;
import com.jiusite.session.MenuSession;
import com.jiusite.global.Glob;
import com.jiusite.main.R;
import com.jiusite.session.PlaceSession;


public class SpecialRequestCategoryAdapter extends RecyclerView.Adapter<SpecialRequestCategoryAdapter.ViewHolder> {

	private View.OnClickListener onClickLister;
    private ArrayList<SpecialRequestCategory> specialRequestCategories;
	
	public SpecialRequestCategoryAdapter(View.OnClickListener onClickLister) {
		this.onClickLister = onClickLister;
		this.specialRequestCategories = MenuSession.specialRequestCategories;
    }
	
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public SpecialRequestCategoryButton spCategoryBtn;
		
		private ViewHolder(View view) {
			super(view);
			this.spCategoryBtn = (SpecialRequestCategoryButton)view.findViewById(R.id.sp_category_btn);
		}
    }

    @Override
    public SpecialRequestCategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.special_request_category_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {	
		//get item
		SpecialRequestCategory specialRequestCategory = specialRequestCategories.get(position);
		
		//set values	
		String name = specialRequestCategory.getName();
		int specialRequestCategoryId = specialRequestCategory.getSpecialRequestCategoryId();
			
		holder.spCategoryBtn.setText(name);
		holder.spCategoryBtn.setSpecialRequestCategoryId(specialRequestCategoryId);
		holder.spCategoryBtn.setOnClickListener(onClickLister);
    }

    @Override
    public int getItemCount() {
        return specialRequestCategories.size();
    }
}




