package com.jiusite.pos.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.jiusite.pos.database.model.Setting;
import com.jiusite.pos.database.model.Table;


public class SettingTable extends SQLiteOpenHelper {

	private static SettingTable settingTable = null;

	public static SettingTable getInstance(Context context) {
		if (settingTable == null) {
			settingTable = new SettingTable(context.getApplicationContext());
		}
		
		return settingTable;
	}
	
	private SettingTable(Context context) {
		super(context, "pos", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String query = "CREATE TABLE IF NOT EXISTS pos_setting ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "type INTEGER, " + "key TEXT, " + "name_id INTEGER, " + "value TEXT )";                                              
		db.execSQL(query);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS pos_setting");
		this.onCreate(db);
	}
	
	public void createTable(SQLiteDatabase db) {
		String query = "CREATE TABLE IF NOT EXISTS pos_setting ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "type INTEGER, " + "key TEXT, " + "name_id INTEGER, " + "value TEXT )";                                              
		db.execSQL(query);
    }

	public void insertSetting(Setting setting) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		try {
			ContentValues values = new ContentValues();
			values.put("type", setting.getType());
			values.put("key", setting.getKey());
			values.put("name_id", setting.getNameId());
			values.put("value", setting.getValue());
			db.insert("pos_setting", null, values);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		} 
	}

	public ArrayList<Setting> getSettings() {
		ArrayList<Setting> settingList = new ArrayList<Setting>();

		String query = "SELECT * FROM pos_setting";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		try {
			if (cursor.moveToFirst()) {
				do {
					Setting setting = new Setting();
					setting.setId(Integer.parseInt(cursor.getString(0)));
					setting.setType(cursor.getInt(1));
					setting.setKey(cursor.getString(2));
					setting.setNameId(cursor.getInt(3));
					setting.setValue(cursor.getString(4));
					settingList.add(setting);
					
				} while (cursor.moveToNext());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
			db.close();
		} 
		
		return settingList;
	}
	
	public Setting getSetting(String key) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		Setting setting = new Setting();
		Cursor cursor = db.rawQuery("SELECT * FROM pos_setting WHERE key=?", new String[] {key});
		
		try {
			if (cursor.moveToFirst()) {
				setting.setId(Integer.parseInt(cursor.getString(0)));
				setting.setType(cursor.getInt(1));
				setting.setKey(cursor.getString(2));
				setting.setNameId(cursor.getInt(3));
				setting.setValue(cursor.getString(4));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
			db.close();
		} 
		
		return setting;
	}
	
	public void clearTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		
		try {
			db.execSQL("delete from pos_setting");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		} 
	}
}

