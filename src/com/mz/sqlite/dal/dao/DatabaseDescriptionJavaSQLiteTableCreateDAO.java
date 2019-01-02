package com.mz.sqlite.dal.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseDescriptionJavaSQLiteTableCreateDAO {
	private Connection _connection;

	public DatabaseDescriptionJavaSQLiteTableCreateDAO(Connection SQLiteDatabaseConnection)
	{
		_connection = SQLiteDatabaseConnection;
	}
	

	public void CreateTable()
	{
		Statement stmt;
		try {
			stmt = _connection.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS " + DatabaseDescriptionTableDescription.TABLE_NAME + " " +
	                
				"(" + DatabaseDescriptionTableDescription.DatabaseName_COLUMN + " STRING  ," +
				"" + DatabaseDescriptionTableDescription.Id_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT )" ;
			stmt.executeUpdate(sql);
			stmt.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
