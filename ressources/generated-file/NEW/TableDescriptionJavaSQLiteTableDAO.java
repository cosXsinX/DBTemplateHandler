
package com.sqlite.dal.test.project.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class TableDescriptionJavaSQLiteTableDAO
{
	private Connection _connection;
	
	TableDescriptionJavaSQLiteTableCreateDAO _createTableDAO;
	TableDescriptionJavaSQLiteTableSelectDAO _selectDAO;
	TableDescriptionJavaSQLiteTableInsertDAO _insertDAO;
	TableDescriptionJavaSQLiteTableUpdateDAO _updateDAO;
	TableDescriptionJavaSQLiteTableDeleteDAO _deleteDAO;
	TableDescriptionJavaSQLiteTableDropDAO _dropTableDAO;
	public TableDescriptionJavaSQLiteTableDAO(Connection SQLiteDatabaseConnection)
	{
		if(SQLiteDatabaseConnection == null) return;
		_connection = SQLiteDatabaseConnection;
		_createTableDAO = new TableDescriptionJavaSQLiteTableCreateDAO(SQLiteDatabaseConnection);
		_selectDAO = new TableDescriptionJavaSQLiteTableSelectDAO(SQLiteDatabaseConnection);
		_insertDAO = new TableDescriptionJavaSQLiteTableInsertDAO(SQLiteDatabaseConnection);
		_updateDAO = new TableDescriptionJavaSQLiteTableUpdateDAO(SQLiteDatabaseConnection);
	}

	public void CreateAssociatedTableInDB()
	{
		if(_createTableDAO == null) return;
		_createTableDAO.CreateTable();
	}
	
	public List<TableDescriptionTableRecordPOJO> Select(String WhereArgsStr)
	{
		if(_selectDAO == null) return new ArrayList<TableDescriptionTableRecordPOJO>();
		return _selectDAO.Select(WhereArgsStr);
	}
	
	public boolean Insert(TableDescriptionTableRecordPOJO pojo)
	{
		if(_insertDAO == null) return false;
		return _insertDAO.Insert(pojo);
	}
	
	public boolean Insert(List<TableDescriptionTableRecordPOJO> pojoList)
	{
		if(_insertDAO == null) return false;
		return _insertDAO.Insert(pojoList);
	}
	
	public boolean Update(TableDescriptionTableRecordPOJO pojo)
	{
		if(_updateDAO == null) return false;
		return _updateDAO.Update(pojo);
	}
	
	public boolean Update(List<TableDescriptionTableRecordPOJO> pojoList)
	{
		if(_updateDAO == null) return false;
		return _updateDAO.Update(pojoList);
	}
	
	public boolean Delete(TableDescriptionTableRecordPOJO pojo)
	{
		if(_deleteDAO == null) return false;
		return _deleteDAO.Delete(pojo);
	}
	
	
	public boolean Delete(List<TableDescriptionTableRecordPOJO> pojoList)
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
