package com.jiusite.pos.global;

import android.view.View;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;


public class PlaceCounter {
	
	private static int productId = 0;
	private static int quantity = 0;
	private static TextView countText;
	private static YoYo.AnimationComposer animationComposer;
	
	public static void setView(TextView countText) {
		PlaceCounter.countText = countText;
	}
	
	public static void setCount(int productId) {
		if(countText != null) {
			if(PlaceCounter.productId != productId) {
				PlaceCounter.productId = productId;
				PlaceCounter.quantity = 1;
				countText.setText("1");
			} else {
				if(animationComposer != null)
					animationComposer.cancel();
			
				PlaceCounter.quantity++;
				String quantityStr = Integer.toString(quantity);
				countText.setText(quantityStr);
			}
			
			PlaceCounter.countText.setVisibility(View.VISIBLE);
			PlaceCounter.animationComposer = YoYo.with(Techniques.Landing).duration(850);
			animationComposer.onEnd(new AnimatorCallbackImpl());
			animationComposer.playOn(PlaceCounter.countText);
		}
	}
	
	public static void clear() {
		PlaceCounter.productId = 0;
		PlaceCounter.quantity = 0;
	}
	
	static class AnimatorCallbackImpl implements YoYo.AnimatorCallback {
		@Override
		public void call(Animator animator) {
			PlaceCounter.countText.setVisibility(View.INVISIBLE);
		}
	}
}

















