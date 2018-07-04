package com.jiusite.service;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import com.jiusite.constant.ADDR;
import com.jiusite.constant.STATS;
import com.jiusite.database.ProductTable;
import com.jiusite.database.model.Product;
import com.jiusite.helper.Func;
import com.jiusite.network.GetNetData;
import com.jiusite.session.MenuSession;


public class ProductService extends Service {
	
	private Timer timer;
	private ProductTask productTask;
	private ProductTable productTable;
	
	@Override
	public IBinder onBind(Intent intent) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
    public void onCreate() {
		//init tables
		productTable = ProductTable.getInstance(ProductService.this);
		
		//load menu
		timer = new Timer();
		productTask = new ProductTask();
		timer.schedule(productTask, 0, 2000); 
    }

	class ProductTask extends TimerTask {
		public void run() {			
			try {
				//get products
				JSONObject outdata = GetNetData.getResult(ADDR.PRODUCTS);
				int httpCode = outdata.getInt("http_code");
				
				//http success
				if(httpCode == STATS.HTTP_OK) {
					JSONObject data = (JSONObject)outdata.get("data");			
					JSONArray productsJson = data.getJSONArray("products");
					
					ArrayList<Product> productsLocal = productTable.getProducts();
					ArrayList<Product> productsServer = new ArrayList<Product>();
					
					for(int i = 0; i < productsJson.length(); i++) {
						JSONObject productJson = (JSONObject)productsJson.get(i);
						int productId = productJson.getInt("product_id");
						int categoryId = productJson.getInt("category_id");
						String name = productJson.getString("name");
						double price = productJson.getDouble("price");
									
						Product product = new Product();
						product.setProductId(productId);
						product.setCategoryId(categoryId);
						product.setName(name);
						product.setPrice(price);
						
						productsServer.add(product);
					}
					
					if(!Func.sameProducts(productsLocal, productsServer)) {				
						productTable.clearTable();
						
						for(int i = 0; i < productsServer.size(); i++) {
							productTable.insertProduct(productsServer.get(i));
						}
						
						MenuSession.products = productsServer;
						
					} else {
						Log.d("service", "product has sync .... ");
					
						SharedPreferences.Editor editor = getSharedPreferences("SYNC", Context.MODE_PRIVATE).edit();
						editor.putBoolean("product_sync", true);
						editor.commit();
					}
				}
			} catch(JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();  
		
		timer.cancel();
		productTask.cancel();
	}
}




















