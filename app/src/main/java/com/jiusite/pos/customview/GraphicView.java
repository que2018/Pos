package com.jiusite.pos.customview;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

import com.jiusite.pos.database.model.Table;
import com.jiusite.session.ScreenSession;


public class GraphicView extends View {
	
	private int screenWidth;
	private int screenHeight;
	private Context context;
	private ArrayList<Table> tables;
	
	public GraphicView(Context context, ArrayList<Table> tables) {
		super(context);
		
		this.context = context;
		this.tables = tables;
	}
	
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.WHITE);
		canvas.drawPaint(paint);
		
		for(int i = 0; i < tables.size(); i++) {
			Table table = tables.get(i);
			
			long x = (long)(table.getX() * ScreenSession.width);
			long y = (long)(table.getY() * ScreenSession.height);
							
			int radius = 40;
			paint.setColor(Color.parseColor("#CD5C5C"));
			canvas.drawCircle(x, y, radius, paint);
			canvas.drawCircle(200, 500, radius, paint);
		}
	}
}
