package com.jiusite.pos.notification;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.jiusite.global.Glob;
import com.jiusite.main.MainActivity;
import com.jiusite.pos.R;

public class SuccessAlertDialog extends Dialog {
	
	public SuccessAlertDialog(Context context) {
		super(context);
		
		//get screen size
		int dialogWidth = 600;
		int dialogHeight = 540;
		
		setCancelable(false);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_success_alert);
		getWindow().setLayout(dialogWidth, dialogHeight);	
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		
		Button confirmButton = (Button)findViewById(R.id.confirm);
		
		confirmButton.setOnClickListener(new ClickListener(context));
	}
	
	private class ClickListener implements View.OnClickListener
    {   
		private Context context;
	
		ClickListener(Context context) {
			this.context = context;
		}
	
        @Override
        public void onClick(View view)
        {
			Intent intent = new Intent(context, MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			Glob.placeActivity.startActivity(intent);
        }
    }
}





