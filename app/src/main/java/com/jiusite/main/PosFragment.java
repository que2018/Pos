package com.jiusite.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;


public class PosFragment extends Fragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    }
	
	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}
		
	public void moveToFragment(int position) {
		ViewPager viewPager = (ViewPager)getActivity().findViewById(R.id.pager);
		viewPager.setCurrentItem(position, true);
	}
}
