package com.jiusite.pos.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;

import com.jiusite.pos.R;

public class AppStartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appstart);

        //init Func
        Context context = getApplicationContext();
        //Func.setContext(context);

        //init screen
        Display display = getWindowManager().getDefaultDisplay();
        //Func.initScreen(display);

		Intent intent = new Intent(AppStartActivity.this, MainActivity.class);
		startActivity(intent);
		
		AppStartActivity.this.finish();		
    }
}
