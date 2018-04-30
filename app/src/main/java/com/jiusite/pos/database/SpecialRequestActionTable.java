package com.jiusite.pos.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.jiusite.pos.database.model.SpecialRequestAction;
import com.jiusite.pos.database.model.Table;


public class SpecialRequestActionTable extends SQLiteOpenHelper {
	
	private static SpecialRequestActionTable specialRequestActionTable = null;

	public static SpecialRequestActionTable getInstance(Context context) {
		if (specialRequestActionTable == null) {
			specialRequestActionTable = new SpecialRequestActionTable(context.getApplicationContext());
		}
		
		return specialRequestActionTable;
	}
	
	private SpecialRequestActionTable(Context context) {
		super(context, "pos", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String query = "CREATE TABLE IF NOT EXISTS special_request_action ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "special_request_action_id INTEGER, " + "name TEXT )";                                              
		db.execSQL(query);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS special_request_action");
		this.onCreate(db);
	}
	
	public void createTable(SQLiteDatabase db) {
		String query = "CREATE TABLE IF NOT EXISTS special_request_action ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "special_request_action_id INTEGER, " + "name TEXT )";                                              
		db.execSQL(query);
    }

	public void insertSpecialRequestAction(SpecialRequestAction speicalRequestAction) {
		SQLiteDatabase db = this.getWritableDatabase();
	
		try {
			ContentValues values = new ContentValues();
			values.put("special_request_action_id", speicalRequestAction.getSpecialRequestActionId());
			values.put("name", speicalRequestAction.getName());
			db.insert("special_request_action", null, values);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		} 
	}

	public ArrayList<SpecialRequestAction> getSpecialRequestActions() {
		ArrayList<SpecialRequestAction> specialRequestActionList = new ArrayList<SpecialRequestAction>();

		String query = "SELECT * FROM special_request_action";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		
		try {
			if (cursor.moveToFirst()) {
				do {
					SpecialRequestAction specialRequestAction = new SpecialRequestAction();
					specialRequestAction.setId(Integer.parseInt(cursor.getString(0)));
					specialRequestAction.setSpecialRequestActionId(cursor.getInt(1));
					specialRequestAction.setName(cursor.getString(2));
					specialRequestActionList.add(specialRequestAction);
					
				} while (cursor.moveToNext());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
			db.close();
		} 
		
		return specialRequestActionList;
	}
	
	public SpecialRequestAction getSpecialRequestAction(int specialRequestActionId) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		SpecialRequestAction specialRequestAction = new SpecialRequestAction();
		Cursor cursor = db.rawQuery("SELECT * FROM special_request_action WHERE special_request_action_id=?", new String[] {String.valueOf(specialRequestActionId)});
		
		try {
			if (cursor.moveToFirst()) {
				specialRequestAction.setId(Integer.parseInt(cursor.getString(0)));
				specialRequestAction.setSpecialRequestActionId(cursor.getInt(1));
				specialRequestAction.setName(cursor.getString(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
			db.close();
		} 
				
		return specialRequestAction;
	}

	public void clearTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		
		try {
			db.execSQL("delete from special_request_action");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		} 
	}
}

