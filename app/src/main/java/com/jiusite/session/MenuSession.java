package com.jiusite.session;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import com.jiusite.database.model.Category;
import com.jiusite.database.model.Product;
import com.jiusite.database.model.SpecialRequest;
import com.jiusite.database.model.SpecialRequestAction;
import com.jiusite.database.model.SpecialRequestCategory;


public class MenuSession {
	
	public static ArrayList<Category> topCategories;
	
	public static ArrayList<Category> categories;
	
	public static ArrayList<Product> products;
	
	public static ArrayList<SpecialRequest> specialRequests;
	
	public static ArrayList<SpecialRequestCategory> specialRequestCategories;
	
	public static ArrayList<SpecialRequestAction> specialRequestActions;
	
	public static HashMap<Integer, SpecialRequest> specialRequestsMap;
	
	public static HashMap<Integer, SpecialRequestCategory> specialRequestCategoriesMap;
	
	public static HashMap<Integer, SpecialRequestAction> specialRequestActionsMap;
	
	public static HashMap<Integer, ArrayList> specialRequestsGroupMap;
	
	
	public static ArrayList<Category> getChildCategories(int categoryId) {
		ArrayList<Category> childCategories = new ArrayList<Category>();
		
		for(int i = 0; i < categories.size(); i++) {
			Category category = categories.get(i);
			
			if(category.getParentId() == categoryId) {
				childCategories.add(category);
			}
		}
		
		return childCategories;
	}
	
	public static boolean hasSubCategories(int categoryId) {
		
		boolean hasSubCategories = false;
		
		for(int i = 0; i < categories.size(); i++) {
			Category category = categories.get(i);
			
			if(category.getParentId() == categoryId) {
				hasSubCategories = true;
				break;
			}
		}
		
		return hasSubCategories;
	}
	
	public static ArrayList<Product> getProducts(int categoryId) {
		ArrayList<Product> outProducts = new ArrayList<Product>();
		
		for(int i = 0; i < products.size(); i++) {
			Product product = products.get(i);
			
			if(product.getCategoryId() == categoryId) {
				outProducts.add(product);
			}
		}
		
		return outProducts;
	}
	
	public static ArrayList<SpecialRequest> getSpecialRequests(int specialRequestCategoryId) {
		return specialRequestsGroupMap.get(specialRequestCategoryId);
	}
	
	public static void formateData() {
		//special request map
		if(specialRequestsMap == null)
			specialRequestsMap = new HashMap<Integer, SpecialRequest>();
		
		for(int i = 0; i < specialRequests.size(); i++) {
			SpecialRequest specialRequest = specialRequests.get(i);
			int specialRequestId = specialRequest.getSpecialRequestId();
			specialRequestsMap.put(specialRequestId, specialRequest);
		}
		
		//special request category map
		if(specialRequestCategoriesMap == null)
			specialRequestCategoriesMap = new HashMap<Integer, SpecialRequestCategory>();
		
		for(int i = 0; i < specialRequestCategories.size(); i++) {
			SpecialRequestCategory specialRequestCategory = specialRequestCategories.get(i);
			int specialRequestCategoryId = specialRequestCategory.getSpecialRequestCategoryId();
			specialRequestCategoriesMap.put(specialRequestCategoryId, specialRequestCategory);
		}
		
		//special request action map
		if(specialRequestActionsMap == null)
			specialRequestActionsMap = new HashMap<Integer, SpecialRequestAction>();
		
		for(int i = 0; i < specialRequestActions.size(); i++) {
			SpecialRequestAction specialRequestAction = specialRequestActions.get(i);
			int specialRequestActionId = specialRequestAction.getSpecialRequestActionId();
			specialRequestActionsMap.put(specialRequestActionId, specialRequestAction);
		}
		
		//special request map
		if(specialRequestsMap == null)
			specialRequestsMap = new HashMap<Integer, SpecialRequest>();
		
		for(int i = 0; i < specialRequests.size(); i++) {
			SpecialRequest specialRequest = specialRequests.get(i);
			int specialRequestId = specialRequest.getSpecialRequestId();
			specialRequestsMap.put(specialRequestId, specialRequest);
		}

		//special request group
		if(specialRequestsGroupMap == null)
			specialRequestsGroupMap = new HashMap<Integer, ArrayList>();
		
		for(int i = 0; i < specialRequests.size(); i++) {
			SpecialRequest specialRequest = specialRequests.get(i);
			int specialRequestCategoryId = specialRequest.getSpecialRequestCategoryId();
			
			if (specialRequestsGroupMap.containsKey(specialRequestCategoryId)) {
				ArrayList specialRequestGroup = (ArrayList)specialRequestsGroupMap.get(specialRequestCategoryId);
				specialRequestGroup.add(specialRequest);
				specialRequestsGroupMap.put(specialRequestCategoryId, specialRequestGroup);
				
			} else {
				ArrayList<SpecialRequest> specialRequestGroup = new ArrayList<SpecialRequest>();
				specialRequestGroup.add(specialRequest);
				specialRequestsGroupMap.put(specialRequestCategoryId, specialRequestGroup);
			}
		}
	}
}

















