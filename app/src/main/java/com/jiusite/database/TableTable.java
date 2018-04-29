package com.jiusite.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.jiusite.database.model.Table;


public class TableTable extends SQLiteOpenHelper {

	private static TableTable tableTable = null;

	public static TableTable getInstance(Context context) {
		if (tableTable == null) {
			tableTable = new TableTable(context.getApplicationContext());
		}
		
		return tableTable;
	}
	
	private TableTable(Context context) {
		super(context, "pos", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String query = "CREATE TABLE IF NOT EXISTS pos_table ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "table_id INTEGER, " + "customer INTEGER, " + "name Text, " + "x REAL, " + "y REAL, " + "anim INTEGER, " + "order_id INTEGER, " + "status INTEGER )";                                              
		db.execSQL(query);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS pos_table");
		this.onCreate(db);
	}
	
	public void createTable(SQLiteDatabase db) {
		String query = "CREATE TABLE IF NOT EXISTS pos_table ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "table_id INTEGER, " + "customer INTEGER, " + "name Text, " + "x REAL, " + "y REAL, " + "anim INTEGER, " + "order_id INTEGER, " + "status INTEGER )";                                              
		db.execSQL(query);
    }

	public void insertTable(Table table) {
		SQLiteDatabase db = this.getWritableDatabase();

		try {
			ContentValues values = new ContentValues();
			values.put("table_id", table.getTableId());
			values.put("customer", table.getCustomer());
			values.put("name", table.getName());
			values.put("x", table.getX());
			values.put("y", table.getY());
			values.put("anim", table.getAnim());
			values.put("order_id", table.getOrderId());
			values.put("status", table.getStatus());
			db.insert("pos_table", null, values);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		} 
	}

	public ArrayList<Table> getTables() {
		ArrayList<Table> tableList = new ArrayList<Table>();

		String query = "SELECT * FROM pos_table";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		Table table = null;
		
		try {
			if (cursor.moveToFirst()) {
				do {
					table = new Table();
					table.setId(Integer.parseInt(cursor.getString(0)));
					table.setTableId(cursor.getInt(1));
					table.setCustomer(cursor.getInt(2));
					table.setName(cursor.getString(3));
					table.setX(cursor.getDouble(4));
					table.setY(cursor.getDouble(5));
					table.setAnim(cursor.getInt(6));
					table.setOrderId(cursor.getInt(7));
					table.setStatus(cursor.getInt(8));
					tableList.add(table);
					
				} while (cursor.moveToNext());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
			db.close();
		} 
		
		return tableList;
	}

	public int updateTable(Table table) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("x", table.getX());
		values.put("y", table.getY());

		int i = db.update("pos_table", values, "id = ?", new String[] {String.valueOf(table.getId())});
		db.close();
		
		return i;
	}
	
	public void setAnimByTableId(int tableId) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("anim", 1);

		db.update("pos_table", values, "table_id = " + tableId, null);
		db.close();
	}
	
	public void clearAnimByTableId(int tableId) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("anim", 0);

		db.update("pos_table", values, "table_id = " + tableId, null);
		db.close();
	}

	public void clearTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		
		try {
			db.execSQL("delete from pos_table");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		} 
	}
}

