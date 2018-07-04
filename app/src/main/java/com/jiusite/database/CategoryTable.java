package com.jiusite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jiusite.database.model.Category;

import java.util.ArrayList;


public class CategoryTable extends SQLiteOpenHelper {
	
	private static CategoryTable categoryTable = null;

	private static final String[] columns = { "id", "category_id", "name" };

	public static CategoryTable getInstance(Context context) {
		if (categoryTable == null) {
			categoryTable = new CategoryTable(context.getApplicationContext());
		}
		
		return categoryTable;
	}

	private CategoryTable(Context context) {
		super(context, "pos", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String query = "CREATE TABLE IF NOT EXISTS pos_category ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "category_id INTEGER, "  + "parent_id INTEGER, " + "name TEXT )";                                              
		db.execSQL(query);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS pos_category");
		this.onCreate(db);
	}

	public void createTable(SQLiteDatabase db) {
		String query = "CREATE TABLE IF NOT EXISTS pos_category ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "category_id INTEGER, "  + "parent_id INTEGER, " + "name TEXT )";                                              
		db.execSQL(query);
    }
	
	public void insertCategory(Category category) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		try {
			ContentValues values = new ContentValues();
			values.put("category_id", category.getCategoryId());
			values.put("parent_id", category.getParentId());
			values.put("name", category.getName());

			db.insert("pos_category", null, values);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		} 
	}

	public int updateCategory(Category category) {
		SQLiteDatabase db = this.getWritableDatabase();
		int id = 0;		
		
		try {
			ContentValues values = new ContentValues();
			values.put("category_id", category.getCategoryId());
			values.put("parent_id", category.getParentId());
			values.put("name", category.getName());
			
			id = db.update("pos_category", values, "id = ?", new String[] { String.valueOf(category.getId()) });
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		} 
		
		return id;
	}

	public void deleteCategory(Category category) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete("pos_category", "id = ?", new String[] { String.valueOf(category.getId()) });
		db.close();
	}
	
	public ArrayList<Category> getCategories() {
		ArrayList<Category> categoryList = new ArrayList<Category>();

		String query = "SELECT * FROM pos_category";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		
		try {
			if (cursor.moveToFirst()) {
				do {
					Category category = new Category();
					category.setId(Integer.parseInt(cursor.getString(0)));
					category.setCategoryId(cursor.getInt(1));
					category.setParentId(cursor.getInt(2));
					category.setName(cursor.getString(3));
					categoryList.add(category);
					
				} while (cursor.moveToNext());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
			db.close();
		} 
				
		return categoryList;
	}
	
	public ArrayList<Category> getTopCategories() {
		ArrayList<Category> categoryList = new ArrayList<Category>();

		String query = "SELECT * FROM pos_category where parent_id = 0";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		
		try {
			if(cursor.moveToFirst()) {
				do {
					Category category = new Category();
					category.setId(Integer.parseInt(cursor.getString(0)));
					category.setCategoryId(cursor.getInt(1));
					category.setParentId(cursor.getInt(2));
					category.setName(cursor.getString(3));
					categoryList.add(category);
					
				} while (cursor.moveToNext());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
			db.close();
		} 
		
		return categoryList;
	}
	
	public ArrayList<Category> getChildCategories(int categoryId) {
		ArrayList<Category> categories = new ArrayList<Category>();

		String query = "SELECT * FROM pos_category where parent_id = " + categoryId;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		
		try {
			if (cursor.moveToFirst()) {
				do {
					Category category = new Category();
					category.setId(Integer.parseInt(cursor.getString(0)));
					category.setCategoryId(cursor.getInt(1));
					category.setParentId(cursor.getInt(2));
					category.setName(cursor.getString(3));
					categories.add(category);
				} while (cursor.moveToNext());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
			db.close();
		} 
				
		return categories;
	}
	
	public void clearTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		
		try {
			db.execSQL("delete from pos_category");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		} 
	}
}