package com.jiusite.session;

import android.util.Log;


public class UserSession {
		
	public static int userId = 0;
	public static boolean isLogin = false;
	public static String username = null;
	public static String permissions = null;
	
	//check if has permission
	public static boolean hasPermission(String permission) {
		if(UserSession.permissions != null) {
			return UserSession.permissions.contains(permission);
		} else {
			return false;
		}
	}
	
	//clear session
	public static void clear() {
		UserSession.userId = 0;
		UserSession.isLogin = false;
		UserSession.username = null;
		UserSession.permissions = null;
	}
}

















