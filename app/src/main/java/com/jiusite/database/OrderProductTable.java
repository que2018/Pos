package com.jiusite.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.jiusite.database.model.OrderProduct;


public class OrderProductTable extends SQLiteOpenHelper {
	
	private static OrderProductTable orderProductTable = null;
	
	public static OrderProductTable getInstance(Context context) {
		if (orderProductTable == null) {
			orderProductTable = new OrderProductTable(context.getApplicationContext());
		}
		
		return orderProductTable;
	}
	
	private OrderProductTable(Context context) {
		super(context, "pos", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String query = "CREATE TABLE IF NOT EXISTS pos_order_product ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "order_id INTEGER, " + "product_id INTEGER, " + "name TEXT, " + "quantity INTEGER, " + "price REAL, " + "special_request TEXT )";                                              
		db.execSQL(query);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS pos_order_product");
		this.onCreate(db);
	}

	public void createTable(SQLiteDatabase db) {
		String query = "CREATE TABLE IF NOT EXISTS pos_order_product ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "order_id INTEGER, " + "product_id INTEGER, " + "name TEXT, " + "quantity INTEGER, " + "price REAL, " + "special_request TEXT )";                                              
		db.execSQL(query);
    }
	
	public void insertOrderProduct(OrderProduct orderProduct) {
		SQLiteDatabase db = this.getWritableDatabase();
	
		try {
			ContentValues values = new ContentValues();
			values.put("order_id", orderProduct.getOrderId());
			values.put("product_id", orderProduct.getProductId());
			values.put("name", orderProduct.getName());
			values.put("quantity", orderProduct.getQuantity());
			values.put("price", orderProduct.getPrice());
			values.put("special_request", orderProduct.getSpecialRequest());

			db.insert("pos_order_product", null, values);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		} 
	}

	public ArrayList<OrderProduct> getOrderProducts() {
		ArrayList<OrderProduct> orderProducts = new ArrayList<OrderProduct>();

		String query = "SELECT * FROM pos_order_product";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		
		try {
			if (cursor.moveToFirst()) {
				do {
					OrderProduct orderProduct = new OrderProduct();
					orderProduct.setId(Integer.parseInt(cursor.getString(0)));
					orderProduct.setOrderId(cursor.getInt(1));
					orderProduct.setProductId(cursor.getInt(2));
					orderProduct.setName(cursor.getString(3));
					orderProduct.setQuantity(cursor.getInt(4));
					orderProduct.setPrice(cursor.getDouble(5));
					orderProduct.setSpecialRequest(cursor.getString(6));
					orderProducts.add(orderProduct);
					
				} while (cursor.moveToNext());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
			db.close();
		} 
				
		return orderProducts;
	}
	
	public ArrayList<OrderProduct> getOrderProductsByOrderId(int orderId) {
		ArrayList<OrderProduct> orderProducts = new ArrayList<OrderProduct>();

		String query = "SELECT * FROM pos_order_product WHERE order_id = " + orderId;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		
		try {
			if (cursor.moveToFirst()) {
				do {
					OrderProduct orderProduct = new OrderProduct();
					orderProduct.setId(Integer.parseInt(cursor.getString(0)));
					orderProduct.setOrderId(cursor.getInt(1));
					orderProduct.setProductId(cursor.getInt(2));
					orderProduct.setName(cursor.getString(3));
					orderProduct.setQuantity(cursor.getInt(4));
					orderProduct.setPrice(cursor.getDouble(5));
					orderProduct.setSpecialRequest(cursor.getString(6));
					orderProducts.add(orderProduct);
					
				} while (cursor.moveToNext());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
			db.close();
		} 
				
		return orderProducts;
	}
	
	public void deleteOrderProduct(OrderProduct orderProduct) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete("pos_order_product", "id = ?", new String[] { String.valueOf(orderProduct.getId()) });
		db.close();
	}
	
	public void clearTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		
		try {
			db.execSQL("delete from pos_order_product");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		} 
	}
}



















