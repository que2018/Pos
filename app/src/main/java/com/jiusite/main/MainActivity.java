package com.jiusite.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.jiusite.customview.PosViewPager;
import com.jiusite.global.Glob;
import com.jiusite.service.OrderService;
import com.jiusite.service.TableService;

public class MainActivity extends ActionBarActivity {
 
    PosViewPager posViewPager;
	PosPagerAdapter posPagerAdapter;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        posViewPager = (PosViewPager)findViewById(R.id.pager);
		
		//init global var
		Glob.activity = MainActivity.this;
		Glob.context = (Context)MainActivity.this;
		Glob.posViewPager = posViewPager;
		
		//set adapter
		posPagerAdapter = new PosPagerAdapter(getSupportFragmentManager());
        posViewPager.setAdapter(posPagerAdapter);
		posViewPager.setCurrentItem(0);
		
		//start service
		startService(new Intent(MainActivity.this, TableService.class));
		startService(new Intent(MainActivity.this, OrderService.class));
    }
 
    public class PosPagerAdapter extends FragmentPagerAdapter {
 
        public PosPagerAdapter(FragmentManager fm) {
            super(fm);
        }
 
        @Override
        public Fragment getItem(int position) {
			Fragment fragment = new Fragment();
			
            switch (position) {
				case 0:
					fragment = new TableFragment();
					break;
				case 1:
                    fragment = new OrdersFragment();
					break;
				case 2:
                    fragment = new UserFragment();
					break;
			}
			
			return fragment;
        }
 
        @Override
        public int getCount() {
            return 3;
        }
    }
}

