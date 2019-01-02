package com.mz.sqlite.dal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DatabaseDescriptionJavaSQLiteTableInsertDAO {
	private Connection _connection;
	public DatabaseDescriptionJavaSQLiteTableInsertDAO(Connection SQLiteDatabaseConnection)
	{
		_connection = SQLiteDatabaseConnection;
	}
	
	private final static String INSERT_STATEMENT = 
			"INSERT INTO "+DatabaseDescriptionTableDescription.TABLE_NAME+" " +
					"(" +
						
						""+DatabaseDescriptionTableDescription.DatabaseName_COLUMN+ //"," +
						//""+DatabaseDescriptionTableDescription.Id_COLUMN +
					") VALUES (?);";
	
	public boolean Insert(DatabaseDescriptionTableRecordPOJO pojo)
	{
		if(pojo == null) return false;
		PreparedStatement stmt;
		try {
			stmt = _connection.prepareStatement(INSERT_STATEMENT);
			_connection.setAutoCommit(false);
			
			stmt.setString(1, pojo.getDatabaseNameString());
			//stmt.setInt(2, pojo.getIdInt());
		    stmt.executeUpdate();
		    pojo.setIdInt(stmt.getGeneratedKeys().getInt(1));
		    _connection.commit();
		    _connection.setAutoCommit(true);
		    return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean Insert(List<DatabaseDescriptionTableRecordPOJO> pojoList)
	{
		if(pojoList==null) return false;
		try {
			_connection.setAutoCommit(false);
			
			for(int currentIndex = 0 ; currentIndex < pojoList.size(); currentIndex++ )
			{
				DatabaseDescriptionTableRecordPOJO currentPojo = pojoList.get(currentIndex);
				PreparedStatement stmt = _connection.prepareStatement(INSERT_STATEMENT);
				
				stmt.setString(1, currentPojo.getDatabaseNameString());
				stmt.setInt(2, currentPojo.getIdInt());
			    stmt.executeUpdate();
			    currentPojo.setIdInt(stmt.getGeneratedKeys().getInt(1));
			}
			_connection.commit();  
			_connection.setAutoCommit(true);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
