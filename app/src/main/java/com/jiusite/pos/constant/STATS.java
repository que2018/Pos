package com.jiusite.pos.constant;

public class STATS {
	
	//http response
	public static int HTTP_OK                             = 200;
	public static int HTTP_BAD_REQUEST                    = 400;
	public static int HTTP_FORBIDDEN                      = 403;
	public static int HTTP_NOT_FOUND                      = 404;
	public static int HTTP_REQUEST_TIMEOUT                = 408;
	public static int HTTP_INTERNAL_SERVER_ERROR          = 500;
	public static int HTTP_INTERNAL_BAD_GATEWAY           = 502;
	public static int HTTP_INTERNAL_GATEWAY_TIMEOUT       = 504;
	public static int HTTP_TIMEOUT                        = 408;
	public static int HTTP_NO_HOST                        = 903;
	
	public static int LOGIN_SUCCESS                      = 1000;
	public static int LOGIN_ERROR                        = 1001;
	
	public static int TABLE_PLACE_SUCCESS                = 1000;

	public static int TABLE_EMPTY                        = 0;
	public static int TABLE_PLACED                       = 1;
	public static int TABLE_ORDERED                      = 2;
	
	public static int TABLE_GET_ORDER_SUCCESS            = 2000;
	public static int TABLE_GET_ORDER_NO_PRODUCTS        = 2001;
	
	public static int CONN_IS_SERVER                     = 4000;
	public static int CONN_NOT_SERVER                    = 4001;
	public static int CONN_NO_NETWORK                    = 4002;
	
	public static int PLACE_SUCCESS                      = 5000;
	public static int PLACE_ERROR                        = 5001;
	
	public static int UPDATE_SUCCESS                     = 7000;
	public static int UPDATE_ERROR                       = 7001;
	
	public static int UNSET_SUCCESS                      = 8000;
	public static int UNSET_ERROR                        = 8001;
}







