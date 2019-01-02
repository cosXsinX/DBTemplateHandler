package com.mz.sqlite.dal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class TableDescriptionJavaSQLiteTableUpdateDAO {
	private Connection _connection;
	public TableDescriptionJavaSQLiteTableUpdateDAO(Connection SQLiteDatabaseConnection)
	{
		_connection = SQLiteDatabaseConnection;
	}
	
	private final static String UPDATE_QUERY = "UPDATE "+TableDescriptionTableDescription.TABLE_NAME+" " +
			"SET " + 
				
				""+TableDescriptionTableDescription.NameStr_COLUMN+"=?," +
				""+TableDescriptionTableDescription.IdDatabaseDescription_COLUMN+"=?" +
			" WHERE "
				+ ""+TableDescriptionTableDescription.Id_COLUMN+"=?"+";";
	public boolean Update(TableDescriptionTableRecordPOJO pojo)
	{
		if(pojo == null) return false;
		try {
			PreparedStatement stmt = _connection.prepareStatement(UPDATE_QUERY);
			int currentColumnIndex = 1;
			_connection.setAutoCommit(false);
			stmt.setString( currentColumnIndex++ , pojo.getNameStrString());
			stmt.setInt( currentColumnIndex++ , pojo.getIdDatabaseDescriptionInt());
			stmt.execute();
			_connection.setAutoCommit(true);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean Update(List<TableDescriptionTableRecordPOJO> pojoList)
	{
		if(pojoList == null) return false;
		try {
			_connection.setAutoCommit(false);
			for(int currentIndex=0;currentIndex<pojoList.size();currentIndex++)
			{
				TableDescriptionTableRecordPOJO currentPOJO = pojoList.get(currentIndex);
				int currentColumnIndex = 1;
				PreparedStatement stmt = _connection.prepareStatement(UPDATE_QUERY);
				stmt.setString(currentColumnIndex++, currentPOJO.getNameStrString());
				stmt.setInt(currentColumnIndex++, currentPOJO.getIdDatabaseDescriptionInt());
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
