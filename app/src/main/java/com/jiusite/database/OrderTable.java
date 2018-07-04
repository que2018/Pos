package com.jiusite.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jiusite.database.model.Order;


public class OrderTable extends SQLiteOpenHelper {

	private static OrderTable orderTable = null;

	private static final String[] columns = { "id", "order_id", "customer", "type" };

	public static OrderTable getInstance(Context context) {
		if (orderTable == null) {
			orderTable = new OrderTable(context.getApplicationContext());
		}
		
		return orderTable;
	}
	
	private OrderTable(Context context) {
		super(context, "pos", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String query = "CREATE TABLE IF NOT EXISTS pos_order ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "order_id INTEGER, " + "customer TEXT, " + "type INTEGER )";                                              
		db.execSQL(query);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS pos_order");
		this.onCreate(db);
	}
	
	public void createTable(SQLiteDatabase db) {
		String query = "CREATE TABLE IF NOT EXISTS pos_order ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "order_id INTEGER, " + "customer TEXT, " + "type INTEGER )";                                              
		db.execSQL(query);
    }

	public void insertOrder(Order order) {
		SQLiteDatabase db = this.getWritableDatabase();

		try {
			ContentValues values = new ContentValues();
			values.put("order_id", order.getOrderId());
			values.put("customer", order.getCustomer());
			values.put("type", order.getType());

			db.insert("pos_order", null, values);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		} 
	}

	public Order getOrder(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query("pos_order", columns, " id = ?", new String[] { String.valueOf(id) }, null, null, null, null);
		Order order = new Order();
		
		try {
			if (cursor.moveToFirst()) {
				order.setId(Integer.parseInt(cursor.getString(0)));
				order.setOrderId(cursor.getInt(1));
				order.setCustomer(cursor.getString(2));
				order.setType(cursor.getInt(3));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
			db.close();
		} 
		
		return order;
	}

	public ArrayList<Order> getOrders() {
		ArrayList<Order> ordersList = new ArrayList<Order>();

		String query = "SELECT * FROM pos_order";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		Order order = null;
		
		try {
			if (cursor.moveToFirst()) {
				do {
					order = new Order();
					order.setId(Integer.parseInt(cursor.getString(0)));
					order.setOrderId(cursor.getInt(1));
					order.setCustomer(cursor.getString(2));
					order.setType(cursor.getInt(3));
					ordersList.add(order);
					
				} while (cursor.moveToNext());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
			db.close();
		} 
				
		return ordersList;
	}

	public int updateOrder(Order order) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("order_id", order.getOrderId());
		values.put("customer", order.getCustomer());
		values.put("type", order.getType());

		int id = db.update("pos_order", values, "id = ?", new String[] { String.valueOf(order.getId()) });
		
		db.close();
		
		return id;
	}

	public void clearTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		
		try {
			db.execSQL("delete from pos_order");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		} 
	}
}

