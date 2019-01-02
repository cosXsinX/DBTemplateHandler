package com.mz.UI.adapters;

import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import com.mz.database.models.DatabaseDescriptionPOJO;
import com.mz.database.models.TableDescriptionPOJO;

public class DatabaseDefinitionUITableAdapter extends AbstractTableModel {

	private DatabaseDescriptionPOJO _databaseDescriptionPOJO = new DatabaseDescriptionPOJO();
	private JTable _tableReference = null;
	
	private final static String[] TableHeaders = {"Table name","Column number","Primary key(s)"};
	
	public void setDatabaseDescriptionPOJO(DatabaseDescriptionPOJO databaseDescriptionPOJO)
	{
		_databaseDescriptionPOJO = databaseDescriptionPOJO;
		if(_databaseDescriptionPOJO == null) _databaseDescriptionPOJO = new DatabaseDescriptionPOJO();
		if(_databaseDescriptionPOJO.getTableList() == null) _databaseDescriptionPOJO.setTableList(new ArrayList<TableDescriptionPOJO>());
	}
	
	public DatabaseDescriptionPOJO getDatabaseDescriptionPOJO()
	{
		return _databaseDescriptionPOJO;
	}
	
	@Override
	public String getColumnName(int columnIndex) {
		return TableHeaders[columnIndex];
	}
	
	@Override
	public int getColumnCount() {
		return TableHeaders.length;
	}

	@Override
	public int getRowCount() {
		if(_databaseDescriptionPOJO== null) return 0;
		if(_databaseDescriptionPOJO.getTableList()== null) return 0;
		return _databaseDescriptionPOJO.getTableList().size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(_databaseDescriptionPOJO== null) return null;
		if(_databaseDescriptionPOJO.getTableList()== null) return null;
		TableDescriptionPOJO retrievedPojo = _databaseDescriptionPOJO.getTableList().get(rowIndex);
		switch(columnIndex)
		{
			case 0:
				return (retrievedPojo.get_NameStr()==null?"":retrievedPojo.get_NameStr());
				
			case 1:
				return retrievedPojo.get_ColumnNumber();
		}
		return null;
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
		if(_databaseDescriptionPOJO== null) return ;
		if(_databaseDescriptionPOJO.getTableList()== null) return ;
		TableDescriptionPOJO retrievedTable = _databaseDescriptionPOJO.getTableList().get(rowIndex);
		switch(columnIndex)
		{
		case 0:
			retrievedTable.set_NameStr((String)value);
		}
	}
	
	public boolean AddTableEntityRow(String tableNameStr)
	{
		if(_databaseDescriptionPOJO== null) return false;
		if(_databaseDescriptionPOJO.getTableList()== null) return false;
		for(TableDescriptionPOJO currentTableEntity : _databaseDescriptionPOJO.getTableList()){
			if(currentTableEntity.get_NameStr().equals(tableNameStr)) return false;
		}
		_databaseDescriptionPOJO.getTableList().add(new TableDescriptionPOJO(tableNameStr));
		fireTableRowsInserted(_databaseDescriptionPOJO.getTableList().size() -1, _databaseDescriptionPOJO.getTableList().size() -1);
		return true;
	}
	
	public void RemoveTableEntityRow(int rowIndex) {

		if(_databaseDescriptionPOJO== null) return;
		if(_databaseDescriptionPOJO.getTableList()== null) return;
		_databaseDescriptionPOJO.getTableList().remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

	public TableDescriptionPOJO getEntityAtIndex(int i) {
		if(_databaseDescriptionPOJO== null) return null;
		if(_databaseDescriptionPOJO.getTableList()== null) return null;
		if(i<0)return null;
		return _databaseDescriptionPOJO.getTableList().get(i);
	}
	
	public void setJTableReference(JTable value)
	{
		_tableReference = value;
	}
	
	@Override
	public void fireTableDataChanged() {
		int[] selectedRows=null; 
		if(_tableReference!=null) selectedRows = _tableReference.getSelectedRows();
		super.fireTableDataChanged();
		if(_tableReference!=null && selectedRows!=null){
			if(selectedRows.length>0){
				_tableReference.setRowSelectionInterval(selectedRows[0], selectedRows[0]);
			}
		}
	}
	
	

}
