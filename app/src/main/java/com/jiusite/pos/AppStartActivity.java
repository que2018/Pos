package com.jiusite.pos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class AppStartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appstart);
		
		Intent intent = new Intent(AppStartActivity.this, MainActivity.class);
		startActivity(intent);
		
		AppStartActivity.this.finish();		
    }
}
