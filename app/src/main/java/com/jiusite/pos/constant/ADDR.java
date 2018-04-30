package com.jiusite.pos.constant;


public class ADDR {
	
	public static String SERVER = "";
		
	public static String USERS = SERVER + "/pos/mobile/user";		
		
	public static String LOGIN = SERVER + "/pos/mobile/login";
	
	public static String ORDER = SERVER + "/pos/mobile/order";
	
	public static String ORDERS = SERVER + "/pos/mobile/order/orders";
	
	public static String ORDER_PRODUCTS = SERVER + "/pos/mobile/order/order_products";
	
	public static String CATEGORIES = SERVER + "/pos/mobile/category/categories";
	
	public static String PRODUCTS = SERVER + "/pos/mobile/product/products";
	
	public static String SPECIAL_REQUESTS = SERVER + "/pos/mobile/special_request/special_requests";
	
	public static String SPECIAL_REQUEST_ACTIONS = SERVER + "/pos/mobile/special_request/special_request_actions";
	
	public static String SPECIAL_REQUEST_CATEGORIES = SERVER + "/pos/mobile/special_request/special_request_categories";
	
	public static String TABLES = SERVER + "/pos/mobile/table/tables";
	
	public static String PLACE = SERVER + "/pos/mobile/place";
	
	public static String UPDATE = SERVER + "/pos/mobile/update";
	
	public static String CANCEL = SERVER + "/pos/mobile/cancel";
	
	public static String VOID = SERVER + "/pos/mobile/void";
	
	public static String HOLD = SERVER + "/pos/mobile/hold";
	
	public static String UNSET = SERVER + "/pos/mobile/unset_table";
	
	public static String COMPLETE = SERVER + "/pos/mobile/complete";
	
	public static String TABLE_PLACE = SERVER + "/pos/mobile/place_table";	
	
	
	//set SERVER variable
	public static void updateADDR(String SERVER) {		
		ADDR.SERVER = SERVER;
		
		USERS = SERVER + "/pos/mobile/user";
		
		LOGIN = SERVER + "/pos/mobile/login";
	
		ORDER = SERVER + "/pos/mobile/order";
		
		ORDERS = SERVER + "/pos/mobile/order/orders";
		
		ORDER_PRODUCTS = SERVER + "/pos/mobile/order/order_products";
		
		CATEGORIES = SERVER + "/pos/mobile/category/categories";
		
		PRODUCTS = SERVER + "/pos/mobile/product/products";
		
		SPECIAL_REQUESTS = SERVER + "/pos/mobile/special_request/special_requests";
		
		SPECIAL_REQUEST_ACTIONS = SERVER + "/pos/mobile/special_request/special_request_actions";
		
		SPECIAL_REQUEST_CATEGORIES = SERVER + "/pos/mobile/special_request/special_request_categories";
		
		TABLES = SERVER + "/pos/mobile/table/tables";
		
		PLACE = SERVER + "/pos/mobile/place";
		
		UNSET = SERVER + "/pos/mobile/unset_table";
		
		UPDATE = SERVER + "/pos/mobile/update";
		
		CANCEL = SERVER + "/pos/mobile/cancel";
	
		VOID = SERVER + "/pos/mobile/void";
		
		HOLD = SERVER + "/pos/mobile/hold";
		
		COMPLETE = SERVER + "/pos/mobile/complete";
		
		TABLE_PLACE = SERVER + "/pos/mobile/place_table";	
	}
}
