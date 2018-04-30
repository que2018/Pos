package com.jiusite.pos.session;

import java.util.ArrayList;
import com.jiusite.pos.database.model.Table;


public class RoomSession {
	
	public static boolean tableSynced = false;
	public static ArrayList<Table> tables = new ArrayList<Table>();
	public static ArrayList<Table> changedTables = new ArrayList<Table>();

	//update data 
	public static void syncTables(ArrayList<Table> tablesServer) {
		
		boolean sameTables = RoomSession.sameTables(RoomSession.tables, tablesServer);
		
		if(sameTables) {
			RoomSession.tableSynced = true;
		} else {
			RoomSession.tableSynced = false;
			RoomSession.changedTables = RoomSession.diffTables(RoomSession.tables, tablesServer);
			RoomSession.tables = tablesServer;
		}
	}
	
	//check tables are the same
	public static boolean sameTables(ArrayList<Table> tablesLocal, ArrayList<Table> tablesServer) {
		for(int i = 0; i < tablesLocal.size(); i++) {
			boolean find = false;
			Table tableLocal = tablesLocal.get(i);
					
			for(int j = 0; j < tablesServer.size(); j++) {
				Table tableServer = tablesServer.get(j);
					
				if(tableLocal.isEqual(tableServer)) {
					find = true;
					break;
				}
			}
			
			if(!find) 
				return false;					
		}
		
		for(int i = 0; i < tablesServer.size(); i++) {
			boolean find = false;
			Table tableServer = tablesServer.get(i);
			
			for(int j = 0; j < tablesLocal.size(); j++) {
				Table tableLocal = tablesLocal.get(j);
				
				if(tableServer.isEqual(tableLocal)) {
					find = true;
					break;
				}	
			}
			
			if(!find) 					
				return false;	
		}
					
		return true;
	}
	
	//get changed table
	public static ArrayList<Table> diffTables(ArrayList<Table> tablesLocal, ArrayList<Table> tablesServer) {
		
		ArrayList<Table> changedTables = new ArrayList<Table>();
		
		for(int i = 0; i < tablesLocal.size(); i++) {
			
			Table tableLocal = tablesLocal.get(i);
			Table tableServer = tablesServer.get(i);	

			if(tableLocal.getStatus() != tableServer.getStatus()) {
				changedTables.add(tableServer);
			}		
		}
				
		return changedTables;
	}
	
	public static Table getTableByName(String name) {
		
		Table tableFind = null;
		
		for(int i = 0; i < RoomSession.tables.size(); i++) {
			
			Table table = RoomSession.tables.get(i);
			String nameFind = table.getName();

			if(nameFind.equals(name)) {
				tableFind = table;
				break;
			}		
		}
		
		return tableFind;
	}
}

















