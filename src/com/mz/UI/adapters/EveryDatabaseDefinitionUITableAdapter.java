package com.mz.UI.adapters;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.mz.database.models.TableDescriptionPOJO;

public class EveryDatabaseDefinitionUITableAdapter extends AbstractTableModel {

	private final static String TableHeaders[] = new String[] {"Database"};
	
	private List<String> DatabaseModelNameList = new ArrayList<String>();
	
	public void SetValues(List<String> list){
		DatabaseModelNameList = list;
	}
	
	@Override
	public String getColumnName(int columnIndex) {
		return TableHeaders[columnIndex];
	}
	
	@Override
	public int getColumnCount() {
		return 1;
	}

	@Override
	public int getRowCount() {
		return DatabaseModelNameList.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return DatabaseModelNameList.get(rowIndex);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		switch(columnIndex)
		{
		case 0:
			return true;
		default:
			return false;
		}
	}
	
	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		if(value == null) return ;
		switch(columnIndex)
		{
		case 0:
			DatabaseModelNameList.set(rowIndex, (String)value);
		}
	}
	
	public String getEntityAtIndex(int index) {

		if(DatabaseModelNameList== null) return null;
		if((DatabaseModelNameList.size()-1)<index) return null;
		if(index<0) return null;
		return DatabaseModelNameList.get(index);
	}
	
}
