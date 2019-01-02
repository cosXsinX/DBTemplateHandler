
package com.sqlite.dal.test.project.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class TableColumnDescriptionJavaSQLiteTableDAO
{
	private Connection _connection;
	
	TableColumnDescriptionJavaSQLiteTableCreateDAO _createTableDAO;
	TableColumnDescriptionJavaSQLiteTableSelectDAO _selectDAO;
	TableColumnDescriptionJavaSQLiteTableInsertDAO _insertDAO;
	TableColumnDescriptionJavaSQLiteTableUpdateDAO _updateDAO;
	TableColumnDescriptionJavaSQLiteTableDeleteDAO _deleteDAO;
	TableColumnDescriptionJavaSQLiteTableDropDAO _dropTableDAO;
	public TableColumnDescriptionJavaSQLiteTableDAO(Connection SQLiteDatabaseConnection)
	{
		if(SQLiteDatabaseConnection == null) return;
		_connection = SQLiteDatabaseConnection;
		_createTableDAO = new TableColumnDescriptionJavaSQLiteTableCreateDAO(SQLiteDatabaseConnection);
		_selectDAO = new TableColumnDescriptionJavaSQLiteTableSelectDAO(SQLiteDatabaseConnection);
		_insertDAO = new TableColumnDescriptionJavaSQLiteTableInsertDAO(SQLiteDatabaseConnection);
		_updateDAO = new TableColumnDescriptionJavaSQLiteTableUpdateDAO(SQLiteDatabaseConnection);
	}

	public void CreateAssociatedTableInDB()
	{
		if(_createTableDAO == null) return;
		_createTableDAO.CreateTable();
	}
	
	public List<TableColumnDescriptionTableRecordPOJO> Select(String WhereArgsStr)
	{
		if(_selectDAO == null) return new ArrayList<TableColumnDescriptionTableRecordPOJO>();
		return _selectDAO.Select(WhereArgsStr);
	}
	
	public boolean Insert(TableColumnDescriptionTableRecordPOJO pojo)
	{
		if(_insertDAO == null) return false;
		return _insertDAO.Insert(pojo);
	}
	
	public boolean Insert(List<TableColumnDescriptionTableRecordPOJO> pojoList)
	{
		if(_insertDAO == null) return false;
		return _insertDAO.Insert(pojoList);
	}
	
	public boolean Update(TableColumnDescriptionTableRecordPOJO pojo)
	{
		if(_updateDAO == null) return false;
		return _updateDAO.Update(pojo);
	}
	
	public boolean Update(List<TableColumnDescriptionTableRecordPOJO> pojoList)
	{
		if(_updateDAO == null) return false;
		return _updateDAO.Update(pojoList);
	}
	
	public boolean Delete(TableColumnDescriptionTableRecordPOJO pojo)
	{
		if(_deleteDAO == null) return false;
		return _deleteDAO.Delete(pojo);
	}
	
	
	public boolean Delete(List<TableColumnDescriptionTableRecordPOJO> pojoList)
	{
		if(_deleteDAO == null) return false;
		return _deleteDAO.Delete(pojoList);
	}
	
	public void DropTable()
	{
		if(_dropTableDAO == null) return;
		_dropTableDAO.DropTable();
	}
}
