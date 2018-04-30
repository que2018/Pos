package com.jiusite.pos.helper;


import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

public class AnimFactory {

	public static Animation getHeartBeat() {
		// Change alpha from fully visible to invisible
		Animation animation = new AlphaAnimation(1, 0); 
		// duration - half a second
		animation.setDuration(500);
		// do not alter animation rate		
		animation.setInterpolator(new LinearInterpolator()); 
		// Repeat animation infinitely
		animation.setRepeatCount(5); 
		// Reverse animation at the end so the button will fade back in
		animation.setRepeatMode(Animation.REVERSE); 
		
		return animation;
	}
}

















