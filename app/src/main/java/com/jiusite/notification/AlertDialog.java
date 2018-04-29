package com.jiusite.notification;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiusite.session.ScreenSession;
import com.jiusite.main.R;


public class AlertDialog extends Dialog {
	
	public AlertDialog(Context context) {
		super(context);
		
		//get screen size
		int dialogWidth = (int)(ScreenSession.width * 0.4);
		int dialogHeight = 450;
		
		setCancelable(true);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_alert);
		getWindow().setLayout(dialogWidth, dialogHeight);	
	}
	
	public void setImage(Drawable drawable) {
		ImageView imageView = (ImageView)findViewById(R.id.image);
		imageView.setImageDrawable(drawable);
	}
		
	public void setButtonText(String text) {
		Button button = (Button)findViewById(R.id.button);
		button.setText(text);
	}
}





