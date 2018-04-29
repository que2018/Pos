package com.jiusite.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.view.View;

public class DotView extends View {
    
    private Paint paint;

    public DotView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
		paint = new Paint();
        paint.setColor(Color.GRAY);
        canvas.drawCircle (50, 50, 100, paint);
    }
}