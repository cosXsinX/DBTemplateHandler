package com.mz.UI.adapters;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class AvailableTemplateUITableAdapter extends AbstractTableModel {

	List<String> templateList;
	private String[] COLUMN_NAMES = {"Template"};
	
	
	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public int getRowCount() {
		if(templateList == null) return 0;
		return templateList.size();
	}

	@Override
	public String getColumnName(int columnIndex) {
		if(columnIndex> (COLUMN_NAMES.length-1)) return null;
		return COLUMN_NAMES[columnIndex];
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(templateList == null) return null;
		if(rowIndex<0 || columnIndex<0) return null;
		if(rowIndex>(templateList.size()-1))return null;
		return templateList.get(rowIndex);
	}
	
	public String getStringValueAt(int rowIndex, int columnIndex){
		return (String) getValueAt(rowIndex,columnIndex);
	}

	public void SetValues(List<String> values){
		templateList = values;
	}
}
