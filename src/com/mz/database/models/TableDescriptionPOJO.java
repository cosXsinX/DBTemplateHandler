package com.mz.database.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TableDescriptionPOJO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4692281827417137606L;
	public TableDescriptionPOJO(String TableNameStr)
	{
		_NameStr = TableNameStr;
	}
	
	private String _NameStr;
	public String get_NameStr() {
		return _NameStr;
	}

	public void set_NameStr(String _NameStr) {
		this._NameStr = _NameStr;
	}
	
	
	
	
	private final List<TableColumnDescriptionPOJO> _ColumnsList = 
			new ArrayList<TableColumnDescriptionPOJO>();
	public List<TableColumnDescriptionPOJO> get_ColumnsList() {
		return _ColumnsList;
	}
	
	public void AddColumn(TableColumnDescriptionPOJO added){
		if(added == null) return;
		_ColumnsList.add(added);
	}
	
	public int get_ColumnNumber(){
		return _ColumnsList.size();
	}
	
	public void ClearColumns(){
		_ColumnsList.clear();
	}

	//Template handler specific properties
	public transient DatabaseDescriptionPOJO ParentDatabase;
	
	public void UpdateContainedColumnsParentReference()
	{
		for(TableColumnDescriptionPOJO currentColumn : _ColumnsList)
		{
			currentColumn.ParentTable = this;
		}
	}
}
