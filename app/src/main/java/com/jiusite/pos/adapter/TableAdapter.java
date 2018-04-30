package com.jiusite.pos.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jiusite.customview.TableButton;
import com.jiusite.pos.database.model.Table;
import com.jiusite.pos.R;
import com.jiusite.session.RoomSession;


public class TableAdapter extends BaseAdapter {
	
	private ArrayList<HashMap> tableRows;
	private View.OnClickListener onClickLister;
	
    public TableAdapter(View.OnClickListener onClickLister) {
		this.onClickLister = onClickLister;
		
		ArrayList<Table> tables = RoomSession.tables;
		
		int tableLen = tables.size();
		
		tableRows = new ArrayList<HashMap>();
		
		try {	
			int count = 0;
			HashMap<Integer, Table> tableRow = new HashMap<Integer, Table>();
			
			for(int i = 0; i < tableLen; i++) {
				Table order = tables.get(i);
																	
				if(count == 0) {
					tableRow = new HashMap<Integer, Table>();
					tableRow.put(count, order);
				
					if(i == (tableLen - 1)) {
						tableRows.add(tableRow);
					}
					
					count ++;
					
				} else if(count < 2) {
					tableRow.put(count, order);
					
					if(i == (tableLen - 1)) {
						tableRows.add(tableRow);
					}
					
					count++;
					
				} else {
					tableRow.put(count, order);
					tableRows.add(tableRow);
					count = 0;
				}
			}
		} catch(Exception e) {
			
		}
    }
	
    @Override
    public int getCount() {
        return tableRows.size();
    } 

    @Override
    public HashMap<Integer, Table> getItem(int position) {	
        return tableRows.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
		
		View tableView;
		HashMap<Integer, Table> item = getItem(position);
		
		int size = item.size();	
		
		int R_layout_id;
			
		switch(size) {
			case 1:
				R_layout_id = R.layout.table_item_row1;
				break;
			case 2:
				R_layout_id = R.layout.table_item_row2;
				break;
			default:
				R_layout_id = R.layout.table_item_row3;
				break;
		}
		
		/* if (convertView == null) {
			menuView = LayoutInflater.from(parent.getContext()).inflate(R_layout_id, parent, false);
        } else {
        	menuView = convertView;
        } */
				
		tableView = LayoutInflater.from(parent.getContext()).inflate(R_layout_id, parent, false);
		
		Table table0 = item.get(0);
		
		String name0 = table0.getName();
		int status0 = table0.getStatus();
		
		TableButton tableButton0 = (TableButton)tableView.findViewById(R.id.table_column0);
		tableButton0.setName(name0);
		tableButton0.setStatus(status0);		
		tableButton0.setOnClickListener(onClickLister);
				
		if(size > 1) {
			Table table1 = item.get(1);

			String name1 = table1.getName();
			int status1 = table1.getStatus();
			
			TableButton tableButton1 = (TableButton)tableView.findViewById(R.id.table_column1);
			tableButton1.setName(name1);
			tableButton1.setStatus(status1);
			tableButton1.setOnClickListener(onClickLister);
		} 
		
		if(size > 2) {
			Table table2 = item.get(2);

			String name2 = table2.getName();
			int status2 = table2.getStatus();
			
			TableButton tableButton2 = (TableButton)tableView.findViewById(R.id.table_column2);
			tableButton2.setName(name2);
			tableButton2.setStatus(status2);
			tableButton2.setOnClickListener(onClickLister);
		} 
			
        return tableView;
    }
} 


