package com.jiusite.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SwipeLayout;

import com.jiusite.customview.LabelRelativeLayout;
import com.jiusite.customview.SpecialPanel;
import com.jiusite.database.SpecialRequestActionTable;
import com.jiusite.database.SpecialRequestTable;
import com.jiusite.database.model.PlaceProduct;
import com.jiusite.database.model.SpecialRequest;
import com.jiusite.database.model.SpecialRequestAction;
import com.jiusite.database.model.SpecialRequestGroup;
import com.jiusite.notification.PosDialog;
import com.jiusite.session.PlaceSession;
import com.jiusite.global.Glob;
import com.jiusite.main.R;


public class PlaceProductAdapter extends BaseAdapter {
		
	private SwipeLayout swipeLayout;
	private DeleteListener deleteListener;
	private SpecialListener specialListener;
	private SpecialRequestTable specialRequestTable;
	private SpecialRequestActionTable specialRequestActionTable;
	private ArrayList<PlaceProduct> placeProducts;

    public PlaceProductAdapter(SwipeLayout swipeLayout) {
		this.swipeLayout = swipeLayout;
		this.deleteListener = new DeleteListener();
		this.specialListener = new SpecialListener();
		this.specialRequestTable = SpecialRequestTable.getInstance(Glob.context);
		this.specialRequestActionTable = SpecialRequestActionTable.getInstance(Glob.context);
				
		this.placeProducts = PlaceSession.placeProducts;
    }
	
    @Override
    public int getCount() {
        return placeProducts.size();
    } 

    @Override
    public PlaceProduct getItem(int position) {	
        return placeProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
	
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
	
		LabelRelativeLayout placeProductView = (LabelRelativeLayout)LayoutInflater.from(parent.getContext()).inflate(R.layout.place_product_item, parent, false);
				
		//get view
		TextView nameText = (TextView)placeProductView.findViewById(R.id.name);
		TextView quantityText = (TextView)placeProductView.findViewById(R.id.quantity);
		TextView specialRequestText = (TextView)placeProductView.findViewById(R.id.special_request);
		
		//get item
		PlaceProduct placeProduct = getItem(position);
		
		//set item info
		int productId = placeProduct.getProductId();
		String name = placeProduct.getName();
		int quantity = placeProduct.getQuantity();
		String quantityString = Integer.toString(quantity);
		ArrayList<SpecialRequestGroup> specialRequestGroups = placeProduct.getSpecialRequestGroups();
		
		String specialRequestStr = new String();
		
		for(int i = 0; i < specialRequestGroups.size(); i++) {
			SpecialRequestGroup specialRequestGroup = specialRequestGroups.get(i);
			specialRequestStr += specialRequestGroup.toString();
		}

		nameText.setText(name);
		quantityText.setText(quantityString);
		specialRequestText.setText(specialRequestStr);

		//set labels
		placeProductView.setLabel("position", position);
		placeProductView.setLabel("product_id", productId);
		
		//add long touch listener
		placeProductView.setOnLongClickListener(deleteListener);
		placeProductView.setOnClickListener(specialListener);
		
        return placeProductView;
    }
	
	class DeleteListener implements View.OnLongClickListener {
		@Override
		public boolean onLongClick(View view) {
			//get position
			final int position = ((LabelRelativeLayout)view).getLabel("position");
			
			//set delay time
			Vibrator vibrator = (Vibrator)Glob.placeActivity.getSystemService(Context.VIBRATOR_SERVICE);
			vibrator.vibrate(100);
		
			//set dialog
			String content = Glob.placeActivity.getResources().getString(R.string.confirm_delete);
			String rightBtnText = Glob.placeActivity.getResources().getString(R.string.yes);
			String leftBtnText = Glob.placeActivity.getResources().getString(R.string.cancel);
		
			final PosDialog posDialog = new PosDialog(Glob.placeActivity);
			posDialog.setContent(content);
			posDialog.setLeftBtnText(leftBtnText);
			posDialog.setRightBtnText(rightBtnText);
			posDialog.show();
			
			//right button listener
			Button buttonRight = (Button)posDialog.findViewById(R.id.button_right);
			
			buttonRight.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					//remove from database
					PlaceProduct placeProduct = getItem(position);
					PlaceSession.removePlaceProduct(placeProduct);
					
					//remove from list
					placeProducts.remove(position);
					notifyDataSetChanged();
					posDialog.dismiss();
				}
			});
			
			//left button listener
			Button buttonLeft = (Button)posDialog.findViewById(R.id.button_left);
			
			buttonLeft.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					posDialog.dismiss();
				}
			});

			return true;
		}
	}
	
	class SpecialListener implements View.OnClickListener {
		@Override
		public void onClick(View view) {
			//get product id
			PlaceSession.selectedProductId = ((LabelRelativeLayout)view).getLabel("product_id");

			Glob.posViewPager.setPagingEnabled(false);
			SpecialPanel specialPanel = Glob.specialPanel;
			
			if(specialPanel.getVisibility() == View.INVISIBLE) {
				specialPanel.setVisibility(View.VISIBLE);
				YoYo.with(Techniques.BounceInUp).duration(700).playOn(specialPanel);
			} 
		}
	}
	
	private String decodeSpecialRequest(String specialRequestString) {
		if(!specialRequestString.isEmpty()) {
			String[] specialRequestData = specialRequestString.split("_");
			int specialRequestActionId = Integer.valueOf(specialRequestData[0]);
			int specialRequestId = Integer.valueOf(specialRequestData[1]);
			
			SpecialRequest specialRequest = specialRequestTable.getSpecialRequest(specialRequestId);
			SpecialRequestAction specialRequestAction = specialRequestActionTable.getSpecialRequestAction(specialRequestActionId);
			
			String specialRequestName = specialRequest.getName();
			String specialRequestActionName = specialRequestAction.getName();
			
			return specialRequestActionName + specialRequestName;
		} else {
			return "";
		}
	}
} 














