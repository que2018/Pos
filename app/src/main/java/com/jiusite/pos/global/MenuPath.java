package com.jiusite.pos.global;


public class MenuPath {
	
	private static String path = "";
	
	//add category path
	public static void add(int categoryId) {
		if(MenuPath.path.isEmpty()) {
			MenuPath.path = "" + categoryId;
		} else {
			MenuPath.path = MenuPath.path + "_" + categoryId;
		}
	}
	
	//step back one category level
	public static void back() {
		String[] parts = MenuPath.path.split("_");
		int len = parts.length;
		
		MenuPath.path = parts[0];
		
		for(int i = 1; i < (len - 1); i++) {			
			MenuPath.path = MenuPath.path + "_" + parts[i];
		}	
	}
	
	//get current category id
	public static int current() {
		String[] parts = MenuPath.path.split("_");
		int len = parts.length;
		
		return Integer.parseInt(parts[len-1]);
	}
	
	//return full path
	public static String path() {
		return MenuPath.path;
	}
	
	public static boolean isRoot() {
		if(MenuPath.path.isEmpty()) {
			return false;
		}
		
		String[] parts = MenuPath.path.split("_");
		int len = parts.length;
		
		if(len > 1) {
			return false;
		} else {
			return true;
		}
	}
}

















