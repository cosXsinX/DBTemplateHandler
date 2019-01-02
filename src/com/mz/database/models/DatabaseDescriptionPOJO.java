package com.mz.database.models;

import java.io.Serializable;
import java.util.ArrayList;

public class DatabaseDescriptionPOJO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6019838554830502243L;
	
	private String _databaseNameStr;
	public String getDatabaseNameStr() {
		return _databaseNameStr;
	}

	public void setDatabaseNameStr(String databaseNameStr) {
		this._databaseNameStr = databaseNameStr;
	}
	
	private ArrayList<TableDescriptionPOJO> tableList;
	public ArrayList<TableDescriptionPOJO> getTableList() {
		return tableList;
	}

	public void setTableList(ArrayList<TableDescriptionPOJO> tableList) {
		this.tableList = tableList;
	}
	
	public void UpdateContainedTablesParentReference()
	{
		for(TableDescriptionPOJO currentColumn : tableList)
		{
			currentColumn.ParentDatabase = this;
			currentColumn.UpdateContainedColumnsParentReference();
		}
	}
}
