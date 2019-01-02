
package com.sqlite.dal.test.project.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class {:TDB:TABLE:CURRENT:NAME::}JavaSQLiteTableDAO
{
	private Connection _connection;
	
	{:TDB:TABLE:CURRENT:NAME::}JavaSQLiteTableCreateDAO _createTableDAO;
	{:TDB:TABLE:CURRENT:NAME::}JavaSQLiteTableSelectDAO _selectDAO;
	{:TDB:TABLE:CURRENT:NAME::}JavaSQLiteTableInsertDAO _insertDAO;
	{:TDB:TABLE:CURRENT:NAME::}JavaSQLiteTableUpdateDAO _updateDAO;
	{:TDB:TABLE:CURRENT:NAME::}JavaSQLiteTableDeleteDAO _deleteDAO;
	{:TDB:TABLE:CURRENT:NAME::}JavaSQLiteTableDropDAO _dropTableDAO;
	public {:TDB:TABLE:CURRENT:NAME::}JavaSQLiteTableDAO(Connection SQLiteDatabaseConnection)
	{
		if(SQLiteDatabaseConnection == null) return;
		_connection = SQLiteDatabaseConnection;
		_createTableDAO = new {:TDB:TABLE:CURRENT:NAME::}JavaSQLiteTableCreateDAO(SQLiteDatabaseConnection);
		_selectDAO = new {:TDB:TABLE:CURRENT:NAME::}JavaSQLiteTableSelectDAO(SQLiteDatabaseConnection);
		_insertDAO = new {:TDB:TABLE:CURRENT:NAME::}JavaSQLiteTableInsertDAO(SQLiteDatabaseConnection);
		_updateDAO = new {:TDB:TABLE:CURRENT:NAME::}JavaSQLiteTableUpdateDAO(SQLiteDatabaseConnection);
	}

	public void CreateAssociatedTableInDB()
	{
		if(_createTableDAO == null) return;
		_createTableDAO.CreateTable();
	}
	
	public List<{:TDB:TABLE:CURRENT:NAME::}TableRecordPOJO> Select(String WhereArgsStr)
	{
		if(_selectDAO == null) return new ArrayList<{:TDB:TABLE:CURRENT:NAME::}TableRecordPOJO>();
		return _selectDAO.Select(WhereArgsStr);
	}
	
	public boolean Insert({:TDB:TABLE:CURRENT:NAME::}TableRecordPOJO pojo)
	{
		if(_insertDAO == null) return false;
		return _insertDAO.Insert(pojo);
	}
	
	public boolean Insert(List<{:TDB:TABLE:CURRENT:NAME::}TableRecordPOJO> pojoList)
	{
		if(_insertDAO == null) return false;
		return _insertDAO.Insert(pojoList);
	}
	
	public boolean Update({:TDB:TABLE:CURRENT:NAME::}TableRecordPOJO pojo)
	{
		if(_updateDAO == null) return false;
		return _updateDAO.Update(pojo);
	}
	
	public boolean Update(List<{:TDB:TABLE:CURRENT:NAME::}TableRecordPOJO> pojoList)
	{
		if(_updateDAO == null) return false;
		return _updateDAO.Update(pojoList);
	}
	
	public boolean Delete({:TDB:TABLE:CURRENT:NAME::}TableRecordPOJO pojo)
	{
		if(_deleteDAO == null) return false;
		return _deleteDAO.Delete(pojo);
	}
	
	
	public boolean Delete(List<{:TDB:TABLE:CURRENT:NAME::}TableRecordPOJO> pojoList)
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
