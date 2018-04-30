package com.jiusite.pos.main;

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

public class PlaceActivity extends ActionBarActivity {
 
    PosViewPager posViewPager;
	PosPagerAdapter posPagerAdapter;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        posViewPager = (PosViewPager)findViewById(R.id.pager);
		
		//init global var
		Glob.placeActivity = PlaceActivity.this;

		//set adapter
		Intent intent = getIntent();
		int fragmentIndex = intent.getIntExtra("fragment_index", 0);
		
		posPagerAdapter = new PosPagerAdapter(getSupportFragmentManager());
        posViewPager.setAdapter(posPagerAdapter);
		posViewPager.setCurrentItem(1);
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
					fragment = new MenuFragment();
					break;
				case 1:
                    fragment = new PlaceFragment();
					break;
			}
			
			return fragment;
        }
 
        @Override
        public int getCount() {
            return 2;
        }
    }
}

