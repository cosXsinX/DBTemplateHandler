package com.mz.sqlite.dal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DatabaseDescriptionJavaSQLiteTableUpdateDAO {
	private Connection _connection;
	public DatabaseDescriptionJavaSQLiteTableUpdateDAO(Connection SQLiteDatabaseConnection)
	{
		_connection = SQLiteDatabaseConnection;
	}
	
	private final static String UPDATE_QUERY = "UPDATE "+DatabaseDescriptionTableDescription.TABLE_NAME+" " +
			"SET " + 
				
				""+DatabaseDescriptionTableDescription.DatabaseName_COLUMN+"=?" +
			" WHERE "
				+ ""+DatabaseDescriptionTableDescription.Id_COLUMN+"=?"+";";
	public boolean Update(DatabaseDescriptionTableRecordPOJO pojo)
	{
		if(pojo == null) return false;
		try {
			PreparedStatement stmt = _connection.prepareStatement(UPDATE_QUERY);
			int currentColumnIndex = 1;
			_connection.setAutoCommit(false);
			stmt.setString( currentColumnIndex++ , pojo.getDatabaseNameString());
			stmt.execute();
			_connection.setAutoCommit(true);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean Update(List<DatabaseDescriptionTableRecordPOJO> pojoList)
	{
		if(pojoList == null) return false;
		try {
			_connection.setAutoCommit(false);
			for(int currentIndex=0;currentIndex<pojoList.size();currentIndex++)
			{
				DatabaseDescriptionTableRecordPOJO currentPOJO = pojoList.get(currentIndex);
				int currentColumnIndex = 1;
				PreparedStatement stmt = _connection.prepareStatement(UPDATE_QUERY);
				stmt.setString(currentColumnIndex++, currentPOJO.getDatabaseNameString());
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
