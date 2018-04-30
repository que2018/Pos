package com.jiusite.pos.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.jiusite.pos.database.model.SpecialRequest;
import com.jiusite.pos.database.model.Table;


public class SpecialRequestTable extends SQLiteOpenHelper {

	private static SpecialRequestTable specialRequestTable = null;

	public static SpecialRequestTable getInstance(Context context) {
		if (specialRequestTable == null) {
			specialRequestTable = new SpecialRequestTable(context.getApplicationContext());
		}
		
		return specialRequestTable;
	}
	
	private SpecialRequestTable(Context context) {
		super(context, "pos", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String query = "CREATE TABLE IF NOT EXISTS special_request ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "special_request_id INTEGER, " + "special_request_category_id TEXT, " + "name TEXT )";                                              
		db.execSQL(query);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS special_request");
		this.onCreate(db);
	}
	
	public void createTable(SQLiteDatabase db) {
		String query = "CREATE TABLE IF NOT EXISTS special_request ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "special_request_id INTEGER, " + "special_request_category_id TEXT, " + "name TEXT )";                                              
		db.execSQL(query);
    }

	public void insertSpecialRequest(SpecialRequest speicalRequest) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		try {
			ContentValues values = new ContentValues();
			values.put("special_request_id", speicalRequest.getSpecialRequestId());
			values.put("special_request_category_id", speicalRequest.getSpecialRequestCategoryId());
			values.put("name", speicalRequest.getName());
			db.insert("special_request", null, values);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		} 
	}

	public ArrayList<SpecialRequest> getSpecialRequests() {
		ArrayList<SpecialRequest> specialRequestList = new ArrayList<SpecialRequest>();

		String query = "SELECT * FROM special_request";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		
		try {
			if (cursor.moveToFirst()) {
				do {
					SpecialRequest SpecialRequest = new SpecialRequest();
					SpecialRequest.setId(Integer.parseInt(cursor.getString(0)));
					SpecialRequest.setSpecialRequestId(cursor.getInt(1));
					SpecialRequest.setSpecialRequestCategoryId(cursor.getInt(2));
					SpecialRequest.setName(cursor.getString(3));
					specialRequestList.add(SpecialRequest);
					
				} while (cursor.moveToNext());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
			db.close();
		} 
		
		return specialRequestList;
	}
	
	public ArrayList<SpecialRequest> getSpecialRequestsByCategoryId(int sepcialRequestCategoryId) {
		ArrayList<SpecialRequest> specialRequests = new ArrayList<SpecialRequest>();

		String query = "SELECT * FROM special_request WHERE special_request_category_id = " + sepcialRequestCategoryId;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		
		try {
			if (cursor.moveToFirst()) {
				do {
					SpecialRequest specialRequest = new SpecialRequest();
					specialRequest.setId(Integer.parseInt(cursor.getString(0)));
					specialRequest.setSpecialRequestId(cursor.getInt(1));
					specialRequest.setSpecialRequestCategoryId(cursor.getInt(2));
					specialRequest.setName(cursor.getString(3));
					specialRequests.add(specialRequest);
					
				} while (cursor.moveToNext());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
			db.close();
		} 
		
		return specialRequests;
	}
	
	public SpecialRequest getSpecialRequest(int specialRequestId) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		SpecialRequest specialRequest = new SpecialRequest();
		
		Cursor cursor = db.rawQuery("SELECT * FROM special_request WHERE special_request_id=?", new String[] {String.valueOf(specialRequestId)});
		
		try {
			if (cursor.moveToFirst()) {
				specialRequest.setId(Integer.parseInt(cursor.getString(0)));
				specialRequest.setSpecialRequestId(cursor.getInt(1));
				specialRequest.setSpecialRequestCategoryId(cursor.getInt(2));
				specialRequest.setName(cursor.getString(3));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
			db.close();
		} 
				
		return specialRequest;
	}

	public void clearTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		
		try {
			db.execSQL("delete from special_request");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		} 
	}
}

