
package com.sqlite.dal.test.project.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class DatabaseDescriptionJavaSQLiteTableDAO
{
	private Connection _connection;
	
	DatabaseDescriptionJavaSQLiteTableCreateDAO _createTableDAO;
	DatabaseDescriptionJavaSQLiteTableSelectDAO _selectDAO;
	DatabaseDescriptionJavaSQLiteTableInsertDAO _insertDAO;
	DatabaseDescriptionJavaSQLiteTableUpdateDAO _updateDAO;
	DatabaseDescriptionJavaSQLiteTableDeleteDAO _deleteDAO;
	DatabaseDescriptionJavaSQLiteTableDropDAO _dropTableDAO;
	public DatabaseDescriptionJavaSQLiteTableDAO(Connection SQLiteDatabaseConnection)
	{
		if(SQLiteDatabaseConnection == null) return;
		_connection = SQLiteDatabaseConnection;
		_createTableDAO = new DatabaseDescriptionJavaSQLiteTableCreateDAO(SQLiteDatabaseConnection);
		_selectDAO = new DatabaseDescriptionJavaSQLiteTableSelectDAO(SQLiteDatabaseConnection);
		_insertDAO = new DatabaseDescriptionJavaSQLiteTableInsertDAO(SQLiteDatabaseConnection);
		_updateDAO = new DatabaseDescriptionJavaSQLiteTableUpdateDAO(SQLiteDatabaseConnection);
	}

	public void CreateAssociatedTableInDB()
	{
		if(_createTableDAO == null) return;
		_createTableDAO.CreateTable();
	}
	
	public List<DatabaseDescriptionTableRecordPOJO> Select(String WhereArgsStr)
	{
		if(_selectDAO == null) return new ArrayList<DatabaseDescriptionTableRecordPOJO>();
		return _selectDAO.Select(WhereArgsStr);
	}
	
	public boolean Insert(DatabaseDescriptionTableRecordPOJO pojo)
	{
		if(_insertDAO == null) return false;
		return _insertDAO.Insert(pojo);
	}
	
	public boolean Insert(List<DatabaseDescriptionTableRecordPOJO> pojoList)
	{
		if(_insertDAO == null) return false;
		return _insertDAO.Insert(pojoList);
	}
	
	public boolean Update(DatabaseDescriptionTableRecordPOJO pojo)
	{
		if(_updateDAO == null) return false;
		return _updateDAO.Update(pojo);
	}
	
	public boolean Update(List<DatabaseDescriptionTableRecordPOJO> pojoList)
	{
		if(_updateDAO == null) return false;
		return _updateDAO.Update(pojoList);
	}
	
	public boolean Delete(DatabaseDescriptionTableRecordPOJO pojo)
	{
		if(_deleteDAO == null) return false;
		return _deleteDAO.Delete(pojo);
	}
	
	
	public boolean Delete(List<DatabaseDescriptionTableRecordPOJO> pojoList)
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
