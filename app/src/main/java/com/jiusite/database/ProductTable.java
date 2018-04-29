package com.jiusite.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.jiusite.database.model.Product;


public class ProductTable extends SQLiteOpenHelper {
	
	private static ProductTable productTable = null;

	private static final String[] columns = { "id", "product_id", "category_id", "name" };

	public static ProductTable getInstance(Context context) {
		if (productTable == null) {
			productTable = new ProductTable(context.getApplicationContext());
		}
		
		return productTable;
	}
	
	private ProductTable(Context context) {
		super(context, "pos", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String query = "CREATE TABLE IF NOT EXISTS pos_product ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "product_id INTEGER, " + "category_id INTEGER, "  + "name TEXT, " + "price REAL )";                                              
		db.execSQL(query);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS pos_product");
		this.onCreate(db);
	}

	public void createTable(SQLiteDatabase db) {
		String query = "CREATE TABLE IF NOT EXISTS pos_product ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "product_id INTEGER, " + "category_id INTEGER, "  + "name TEXT, " + "price REAL )";                                              
		db.execSQL(query);
    }
	
	public void insertProduct(Product product) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		try {
			ContentValues values = new ContentValues();
			values.put("product_id", product.getProductId());
			values.put("category_id", product.getCategoryId());
			values.put("name", product.getName());
			values.put("price", product.getPrice());

			db.insert("pos_product", null, values);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		} 
	}

	public Product getProduct(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query("pos_product", columns, " id = ?", new String[] { String.valueOf(id) }, null, null, null, null);

		Product product = new Product();
		
		try {
			if ((cursor != null) && (cursor.moveToFirst())) {
				product.setId(Integer.parseInt(cursor.getString(0)));
				product.setProductId(cursor.getInt(1));
				product.setCategoryId(cursor.getInt(2));
				product.setName(cursor.getString(3));
				product.setPrice(cursor.getDouble(4));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
			db.close();
		} 
	
		return product;
	}

	public int updateProduct(Product product) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("product_id", product.getProductId());
		values.put("category_id", product.getCategoryId());
		values.put("name", product.getName());
		values.put("price", product.getPrice());
		
		int id = db.update("pos_product", values, "id = ?", new String[] { String.valueOf(product.getId()) });
		db.close();
		
		return id;
	}

	public void deleteProduct(Product product) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete("product", "id = ?", new String[] { String.valueOf(product.getId()) });
		db.close();
	}
	
	public ArrayList<Product> getProducts() {
		ArrayList<Product> productList = new ArrayList<Product>();

		String query = "SELECT * FROM pos_product";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		
		try {
			if (cursor.moveToFirst()) {
				do {
					Product product = new Product();
					product.setId(Integer.parseInt(cursor.getString(0)));
					product.setProductId(cursor.getInt(1));
					product.setCategoryId(cursor.getInt(2));
					product.setName(cursor.getString(3));
					product.setPrice(cursor.getDouble(4));
					productList.add(product);
					
				} while (cursor.moveToNext());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
			db.close();
		} 
				
		return productList;
	}
	
	public ArrayList<Product> getProductsByCategoryId(int categoryId) {
		ArrayList<Product> productList = new ArrayList<Product>();

		String query = "SELECT * FROM pos_product WHERE category_id = " + categoryId;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		
		try {
			if (cursor.moveToFirst()) {
				do {
					Product product = new Product();
					product.setId(Integer.parseInt(cursor.getString(0)));
					product.setProductId(cursor.getInt(1));				
					product.setCategoryId(cursor.getInt(2));
					product.setName(cursor.getString(3));
					product.setPrice(cursor.getDouble(4));
					productList.add(product);
					
				} while (cursor.moveToNext());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
			db.close();
		} 
				
		return productList;
	}
	
	public void clearTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		
		try {
			db.execSQL("delete from pos_product");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		} 
	}
}




