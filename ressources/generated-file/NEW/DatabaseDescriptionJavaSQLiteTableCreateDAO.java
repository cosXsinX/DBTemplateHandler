package com.sqlite.dal.test.project.dao;

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
				"" + DatabaseDescriptionTableDescription.Id_COLUMN + " INT PRIMARY KEY )" ;
			stmt.executeUpdate(sql);
			stmt.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
