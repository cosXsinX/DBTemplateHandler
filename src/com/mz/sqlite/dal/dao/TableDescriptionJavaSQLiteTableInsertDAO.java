package com.mz.sqlite.dal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class TableDescriptionJavaSQLiteTableInsertDAO {
	private Connection _connection;
	public TableDescriptionJavaSQLiteTableInsertDAO(Connection SQLiteDatabaseConnection)
	{
		_connection = SQLiteDatabaseConnection;
	}
	
	private final static String INSERT_STATEMENT = 
			"INSERT INTO "+TableDescriptionTableDescription.TABLE_NAME+" " +
					"(" +
						
						""+TableDescriptionTableDescription.NameStr_COLUMN+"," +
						//""+TableDescriptionTableDescription.Id_COLUMN+"," +
						""+TableDescriptionTableDescription.IdDatabaseDescription_COLUMN +
					") VALUES (?,?);";
	
	public boolean Insert(TableDescriptionTableRecordPOJO pojo)
	{
		if(pojo == null) return false;
		PreparedStatement stmt;
		try {
			stmt = _connection.prepareStatement(INSERT_STATEMENT);
			_connection.setAutoCommit(false);
			
			stmt.setString(1, pojo.getNameStrString());
			//stmt.setInt(1, pojo.getIdInt());
			stmt.setInt(2, pojo.getIdDatabaseDescriptionInt());
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
	
	public boolean Insert(List<TableDescriptionTableRecordPOJO> pojoList)
	{
		if(pojoList==null) return false;
		try {
			_connection.setAutoCommit(false);
			
			for(int currentIndex = 0 ; currentIndex < pojoList.size(); currentIndex++ )
			{
				TableDescriptionTableRecordPOJO currentPojo = pojoList.get(currentIndex);
				PreparedStatement stmt = _connection.prepareStatement(INSERT_STATEMENT);
				
				stmt.setString(1, currentPojo.getNameStrString());
				//stmt.setInt(1, currentPojo.getIdInt());
				stmt.setInt(2, currentPojo.getIdDatabaseDescriptionInt());
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
