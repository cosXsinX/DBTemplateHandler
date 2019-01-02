package com.sqlite.dal.test.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class TableDescriptionJavaSQLiteTableDeleteDAO {
	private Connection _connection;
	public TableDescriptionJavaSQLiteTableDeleteDAO(Connection SQLiteDatabaseConnection)
	{
		_connection = SQLiteDatabaseConnection;
	}
	
	private final static String DELETE_QUERY="DELETE FROM "+TableDescriptionTableDescription.TABLE_NAME+" WHERE " +
			""+TableDescriptionTableDescription.ID_COLUMN+"=?";
	public boolean Delete(TableDescriptionTableRecordPOJO pojo)
	{
		if(pojo == null) return false;
		try {
			PreparedStatement stmt = _connection.prepareStatement(DELETE_QUERY);
			_connection.setAutoCommit(false);
			
			stmt.setInt(0, pojo.getIdInt());
			
			stmt.execute();
			_connection.setAutoCommit(true);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public boolean Delete(List<TableDescriptionTableRecordPOJO> pojoList)
	{
		if(pojoList == null) return false;
		try {
			_connection.setAutoCommit(false);
			for(int currentIndex=0;currentIndex<pojoList.size();currentIndex++)
			{
				TableDescriptionTableRecordPOJO currentPOJO = pojoList.get(currentIndex);
				PreparedStatement stmt = _connection.prepareStatement(DELETE_QUERY);
				
				stmt.setInt(0, currentPOJO.getIdInt());
				stmt.execute();
			}
			_connection.setAutoCommit(true);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
