package com.jiusite.pos.helper;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;

import com.jiusite.constant.CONF;
import com.jiusite.constant.STATS;
import com.jiusite.pos.database.model.Category;
import com.jiusite.pos.database.model.Product;
import com.jiusite.pos.database.model.SpecialRequest;
import com.jiusite.pos.database.model.SpecialRequestAction;
import com.jiusite.pos.database.model.SpecialRequestCategory;
import com.jiusite.pos.database.model.User;
import com.jiusite.pos.R;
import com.jiusite.session.ScreenSession;


public class Func {
	
	private static Context context;
	
	public static void setContext(Context context) {
		Func.context = context;
	}
	
	public static ArrayList<Category> toCategoryArray(JSONArray categoryJsonArray) {
		ArrayList<Category> categoryList = new ArrayList<Category>();
		
		try {
			for(int i = 0; i < categoryJsonArray.length(); i++) {			
				JSONObject CategoryJson = categoryJsonArray.getJSONObject(i);
				int categoryId = CategoryJson.getInt("id");
				int parentId = CategoryJson.getInt("parent_id");
				String name = CategoryJson.getString("name").toString();
				
				Category category = new Category();
				category.setCategoryId(categoryId);
				category.setParentId(parentId);
				category.setName(name);
				
				categoryList.add(category);
			}
		} catch(JSONException e) {
			e.printStackTrace();
		}
		
		return categoryList;
	}
	
	public static boolean sameCategories(ArrayList<Category> categoriesLocal, ArrayList<Category> categoriesServer) {		
		for(int i = 0; i < categoriesLocal.size(); i++) {			
			boolean find = false;
			Category categoryLocal = categoriesLocal.get(i);
								
			for(int j = 0; j < categoriesServer.size(); j++) {
				Category categoryServer = categoriesServer.get(j);
									
				if(categoryLocal.isEqual(categoryServer)) {
					find = true;
					break;
				}
			}
			
			if(!find) {				
				return false;	
			}				
		}
		
		for(int i = 0; i < categoriesServer.size(); i++) {
			boolean find = false;
			Category categoryServer = categoriesServer.get(i);
			
			for(int j = 0; j < categoriesLocal.size(); j++) {
				Category categoryLocal = categoriesLocal.get(j);
				
				if(categoryServer.isEqual(categoryLocal)) {
					find = true;
					break;
				}	
			}
			
			if(!find) {				
				return false;	
			}
		}
					
		return true;
	}
	
	public static boolean sameProducts(ArrayList<Product> productsLocal, ArrayList<Product> productsServer) {		
		for(int i = 0; i < productsLocal.size(); i++) {
			boolean find = false;
			Product productLocal = productsLocal.get(i);
					
			for(int j = 0; j < productsServer.size(); j++) {
				Product productServer = productsServer.get(j);
					
				if(productLocal.isEqual(productServer)) {
					find = true;
					break;
				}
			}
			
			if(!find) 
				return false;					
		}
		
		for(int i = 0; i < productsServer.size(); i++) {
			boolean find = false;
			Product productServer = productsServer.get(i);
			
			for(int j = 0; j < productsLocal.size(); j++) {
				Product productLocal = productsLocal.get(j);
				
				if(productServer.isEqual(productLocal)) {
					find = true;
					break;
				}	
			}
			
			if(!find) 					
				return false;	
		}
					
		return true;
	}
	
	public static boolean sameSpecialRequests(ArrayList<SpecialRequest> specialRequestsLocal, ArrayList<SpecialRequest> specialRequestsServer) {
		for(int i = 0; i < specialRequestsLocal.size(); i++) {
			boolean find = false;
			SpecialRequest specialRequestLocal = specialRequestsLocal.get(i);
					
			for(int j = 0; j < specialRequestsServer.size(); j++) {
				SpecialRequest specialRequestServer = specialRequestsServer.get(j);
					
				if(specialRequestLocal.isEqual(specialRequestServer)) {
					find = true;
					break;
				}
			}
			
			if(!find) 
				return false;					
		}
		
		for(int i = 0; i < specialRequestsServer.size(); i++) {
			boolean find = false;
			SpecialRequest specialRequestServer = specialRequestsServer.get(i);
			
			for(int j = 0; j < specialRequestsLocal.size(); j++) {
				SpecialRequest specialRequestLocal = specialRequestsLocal.get(j);
				
				if(specialRequestServer.isEqual(specialRequestLocal)) {
					find = true;
					break;
				}	
			}
			
			if(!find) 					
				return false;	
		}
					
		return true;
	}
	
	public static boolean sameSpecialRequestActions(ArrayList<SpecialRequestAction> specialRequestActionsLocal, ArrayList<SpecialRequestAction> specialRequestActionsServer) {
		for(int i = 0; i < specialRequestActionsLocal.size(); i++) {
			boolean find = false;
			SpecialRequestAction specialRequestActionLocal = specialRequestActionsLocal.get(i);
					
			for(int j = 0; j < specialRequestActionsServer.size(); j++) {
				SpecialRequestAction specialRequestActionServer = specialRequestActionsServer.get(j);
					
				if(specialRequestActionLocal.isEqual(specialRequestActionServer)) {
					find = true;
					break;
				}
			}
			
			if(!find) 
				return false;					
		}
		
		for(int i = 0; i < specialRequestActionsServer.size(); i++) {
			boolean find = false;
			SpecialRequestAction specialRequestActionServer = specialRequestActionsServer.get(i);
			
			for(int j = 0; j < specialRequestActionsLocal.size(); j++) {
				SpecialRequestAction specialRequestActionLocal = specialRequestActionsLocal.get(j);
				
				if(specialRequestActionServer.isEqual(specialRequestActionLocal)) {
					find = true;
					break;
				}	
			}
			
			if(!find) 					
				return false;	
		}
					
		return true;
	}
	
	public static boolean sameSpecialRequestCategories(ArrayList<SpecialRequestCategory> specialRequestCategoriesLocal, ArrayList<SpecialRequestCategory> specialRequestCategoriesServer) {
		for(int i = 0; i < specialRequestCategoriesLocal.size(); i++) {
			boolean find = false;
			SpecialRequestCategory SpecialRequestCategoryLocal = specialRequestCategoriesLocal.get(i);
					
			for(int j = 0; j < specialRequestCategoriesServer.size(); j++) {
				SpecialRequestCategory SpecialRequestCategoryServer = specialRequestCategoriesServer.get(j);
					
				if(SpecialRequestCategoryLocal.isEqual(SpecialRequestCategoryServer)) {
					find = true;
					break;
				}
			}
			
			if(!find) 
				return false;					
		}
		
		for(int i = 0; i < specialRequestCategoriesServer.size(); i++) {
			boolean find = false;
			SpecialRequestCategory SpecialRequestCategoryServer = specialRequestCategoriesServer.get(i);
			
			for(int j = 0; j < specialRequestCategoriesLocal.size(); j++) {
				SpecialRequestCategory SpecialRequestCategoryLocal = specialRequestCategoriesLocal.get(j);
				
				if(SpecialRequestCategoryServer.isEqual(SpecialRequestCategoryLocal)) {
					find = true;
					break;
				}	
			}
			
			if(!find) 					
				return false;	
		}
					
		return true;
	}
	
	public static boolean sameUsers(ArrayList<User> usersLocal, ArrayList<User> usersServer) {
		for(int i = 0; i < usersLocal.size(); i++) {			
			boolean find = false;
			User userLocal = usersLocal.get(i);
								
			for(int j = 0; j < usersServer.size(); j++) {
				User userServer = usersServer.get(j);
									
				if(userLocal.isEqual(userServer)) {
					find = true;
					break;
				}
			}
			
			if(!find) {				
				return false;	
			}				
		}
		
		for(int i = 0; i < usersServer.size(); i++) {
			boolean find = false;
			User userServer = usersServer.get(i);
			
			for(int j = 0; j < usersLocal.size(); j++) {
				User userLocal = usersLocal.get(j);
				
				if(userServer.isEqual(userLocal)) {
					find = true;
					break;
				}	
			}
			
			if(!find) {				
				return false;	
			}
		}
					
		return true;
	}
	
	public static String prase(int code) {
		
		String string = new String();
		
		if(code == STATS.HTTP_OK) {
			string = context.getResources().getString(R.string.confirm_delete);
		}
		
		if(code == STATS.HTTP_BAD_REQUEST) {
			string = context.getResources().getString(R.string.confirm_delete);
		}
	
		return string;
	}
	
	public static int measureTextSize(String str) {
		int len = str.length();
		
		if(len < 7) 
		{
			return CONF.MENU_BIG_SIZE;
		} 
		else if(len < 11) 
		{
			return CONF.MENU_MID_SIZE;
		} 
		else 
		{
			return CONF.MENU_SMALL_SIZE;
		}
	}

	public static String addHttpHeader(String ipaddress) {
		return "http://" + ipaddress;
	}
	
	public static String removeHttpHeader(String ipaddress) {
		if(ipaddress.indexOf("http://") != -1){
		   return ipaddress.replace("http://", "");
		} else {
			return ipaddress;
		}
	}
	
	public static boolean hasPermission(String permissions, String permission) {
		if(permissions.indexOf(permission) >= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void initScreen(Display display) {
		Point size = new Point();
		display.getSize(size);
		
		int width = size.x;
		int height = size.y;
		
		if(height > width) {
			ScreenSession.width = height;
			ScreenSession.height = width;	
		} else {
			ScreenSession.width = width;
			ScreenSession.height = height;	
		}
		
		if(ScreenSession.width > 2000) {
			ScreenSession.tableSize = CONF.TABLE_BIG_SIZE;
			ScreenSession.tableFontSize = CONF.TABLEFONT_BIG_SIZE;
			ScreenSession.connbtnRightMargin = CONF.CONNBTN_BIG_RIGHT_MARGIN;
			ScreenSession.connbtnBottomMargin = CONF.CONNBTN_BIG_BOTTOM_MARGIN;
			
		} else if(ScreenSession.width > 1650) {
			ScreenSession.tableSize = CONF.TABLE_MID_SIZE;
			ScreenSession.tableFontSize = CONF.TABLEFONT_MID_SIZE;
			ScreenSession.connbtnRightMargin = CONF.CONNBTN_MID_RIGHT_MARGIN;
			ScreenSession.connbtnBottomMargin = CONF.CONNBTN_MID_BOTTOM_MARGIN;
			
		} else {
			ScreenSession.tableSize = CONF.TABLE_SMALL_SIZE;
			ScreenSession.tableFontSize = CONF.TABLEFONT_SMALL_SIZE;
			ScreenSession.connbtnRightMargin = CONF.CONNBTN_SMALL_RIGHT_MARGIN;
			ScreenSession.connbtnBottomMargin = CONF.CONNBTN_SMALL_BOTTOM_MARGIN;
		}
		
		ScreenSession.specialPanelWidth = (int)(ScreenSession.width * 0.85);
		ScreenSession.specialPanelHeight = (int)(ScreenSession.height * 0.9);
	}
}

















