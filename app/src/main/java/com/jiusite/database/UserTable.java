package com.jiusite.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jiusite.database.model.User;


public class UserTable extends SQLiteOpenHelper {
	
	private static UserTable userTable = null;

	private static final String[] columns = { "id", "user_id", "username", "permissions" };

	public static UserTable getInstance(Context context) {
		if (userTable == null) {
			userTable = new UserTable(context.getApplicationContext());
		}
		
		return userTable;
	}
	
	private UserTable(Context context) {
		super(context, "pos", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String query = "CREATE TABLE IF NOT EXISTS pos_user ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "user_id INTEGER, " + "username TEXT, " + "permissions TEXT )";                                              
		db.execSQL(query);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS pos_user");
		this.onCreate(db);
	}

	public void createTable(SQLiteDatabase db) {
		String query = "CREATE TABLE IF NOT EXISTS pos_user ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "user_id INTEGER, " + "username TEXT, " + "permissions TEXT )";                                              
		db.execSQL(query);
    }
	
	public void insertUser(User user) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		try {
			ContentValues values = new ContentValues();
			values.put("user_id", user.getUserId());
			values.put("username", user.getUsername());
			values.put("permissions", user.getPermissions());
			
			db.insert("pos_user", null, values);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		} 
	}

	public int updateUser(User user) {
		SQLiteDatabase db = this.getWritableDatabase();

		int id = 0;
		
		try {
			ContentValues values = new ContentValues();
			values.put("user_id", user.getUserId());
			values.put("username", user.getUsername());
			values.put("permissions", user.getPermissions());
		
			id = db.update("pos_user", values, "id = ?", new String[] { String.valueOf(user.getId()) });
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		} 
			
		return id;
	}

	public void deleteUser(User user) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		try {
			db.delete("pos_user", "id = ?", new String[] { String.valueOf(user.getId()) });
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		} 
	}
	
	public ArrayList<User> getUsers() {
		ArrayList<User> userList = new ArrayList<User>();

		String query = "SELECT * FROM pos_user";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		
		try {
			if (cursor.moveToFirst()) {
				do {
					User user = new User();
					user.setId(Integer.parseInt(cursor.getString(0)));
					user.setUserId(cursor.getInt(1));
					user.setUsername(cursor.getString(2));
					user.setPermissions(cursor.getString(3));
					userList.add(user);
					
				} while (cursor.moveToNext());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
			db.close();
		} 
				
		return userList;
	}
	
	public User getUserByUsername(String username) {
		SQLiteDatabase db = this.getReadableDatabase();

		User user = new User();
		
		Cursor cursor = db.query("pos_user", columns, " username = ?", new String[] { username }, null, null, null, null);

		try {
			if ((cursor != null) && (cursor.moveToFirst())) {
				user.setId(Integer.parseInt(cursor.getString(0)));
				user.setUserId(cursor.getInt(1));
				user.setUsername(cursor.getString(2));
				user.setPermissions(cursor.getString(3));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
			db.close();
		} 
	
		return user;
	}
	
	public void clearTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		
		try {
			db.execSQL("delete from pos_user");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		} 
	}
}