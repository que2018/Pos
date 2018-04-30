package com.jiusite.pos.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.jiusite.pos.database.model.SpecialRequestCategory;
import com.jiusite.pos.database.model.Table;


public class SpecialRequestCategoryTable extends SQLiteOpenHelper {

	private static SpecialRequestCategoryTable specialRequestCategoryTable = null;

	public static SpecialRequestCategoryTable getInstance(Context context) {
		if (specialRequestCategoryTable == null) {
			specialRequestCategoryTable = new SpecialRequestCategoryTable(context.getApplicationContext());
		}
		
		return specialRequestCategoryTable;
	}
	
	private SpecialRequestCategoryTable(Context context) {
		super(context, "pos", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String query = "CREATE TABLE IF NOT EXISTS special_request_category ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "special_request_category_id INTEGER, " + "name TEXT )";                                              
		db.execSQL(query);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS special_request_category");
		this.onCreate(db);
	}
	
	public void createTable(SQLiteDatabase db) {
		String query = "CREATE TABLE IF NOT EXISTS special_request_category ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "special_request_category_id INTEGER, " + "name TEXT )";                                              
		db.execSQL(query);
    }

	public void insertSpecialRequestCategory(SpecialRequestCategory speicalRequestCategory) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		try {
			ContentValues values = new ContentValues();
			values.put("special_request_category_id", speicalRequestCategory.getSpecialRequestCategoryId());
			values.put("name", speicalRequestCategory.getName());
			db.insert("special_request_category", null, values);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		} 
	}

	public ArrayList<SpecialRequestCategory> getSpecialRequestCategories() {
		ArrayList<SpecialRequestCategory> specialRequestCategoryList = new ArrayList<SpecialRequestCategory>();

		String query = "SELECT * FROM special_request_category";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		
		try {
			if (cursor.moveToFirst()) {
				do {
					SpecialRequestCategory SpecialRequestCategory = new SpecialRequestCategory();
					SpecialRequestCategory.setId(Integer.parseInt(cursor.getString(0)));
					SpecialRequestCategory.setSpecialRequestCategoryId(cursor.getInt(1));
					SpecialRequestCategory.setName(cursor.getString(2));
					specialRequestCategoryList.add(SpecialRequestCategory);
					
				} while (cursor.moveToNext());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
			db.close();
		} 
	
		return specialRequestCategoryList;
	}

	public void clearTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		
		try {
			db.execSQL("delete from special_request_category");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		} 
	}
}

