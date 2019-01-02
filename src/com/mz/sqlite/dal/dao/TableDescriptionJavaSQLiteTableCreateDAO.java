package com.mz.sqlite.dal.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TableDescriptionJavaSQLiteTableCreateDAO {
	private Connection _connection;

	public TableDescriptionJavaSQLiteTableCreateDAO(Connection SQLiteDatabaseConnection)
	{
		_connection = SQLiteDatabaseConnection;
	}
	

	public void CreateTable()
	{
		Statement stmt;
		try {
			stmt = _connection.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS " + TableDescriptionTableDescription.TABLE_NAME + " " +
	                
				"(" + TableDescriptionTableDescription.NameStr_COLUMN + " STRING  ," +
				"" + TableDescriptionTableDescription.Id_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT," +
				"" + TableDescriptionTableDescription.IdDatabaseDescription_COLUMN + " INTEGER  )" ;
			stmt.executeUpdate(sql);
			stmt.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
