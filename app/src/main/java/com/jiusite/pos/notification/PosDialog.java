package com.jiusite.pos.notification;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.jiusite.session.ScreenSession;
import com.jiusite.pos.R;


public class PosDialog extends Dialog {
	
	public PosDialog(Context context) {
		super(context);
		
		//get screen size
		int dialogWidth = (int)(ScreenSession.width * 0.4);
		int dialogHeight = 450;
		
		setCancelable(true);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		setContentView(R.layout.dialog);
		getWindow().setLayout(dialogWidth, dialogHeight);	
	}
	
	public void setContent(String content) {
		TextView contentText = (TextView)findViewById(R.id.content);
		contentText.setText(content);
	}
	
	public void setLeftBtnText(String text) {
		Button buttonLeft = (Button)findViewById(R.id.button_left);
		buttonLeft.setText(text);
	}
	
	public void setRightBtnText(String text) {
		Button buttonRight = (Button)findViewById(R.id.button_right);
		buttonRight.setText(text);
	}
}





