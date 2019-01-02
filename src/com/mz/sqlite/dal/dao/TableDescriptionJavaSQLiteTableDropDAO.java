package com.mz.sqlite.dal.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TableDescriptionJavaSQLiteTableDropDAO {
	private Connection _connection;
	public TableDescriptionJavaSQLiteTableDropDAO(Connection SQLiteDatabaseConnection)
	{
		_connection = SQLiteDatabaseConnection;
	}
	
	public void DropTable()
	{
		Statement stmt;
		try {
			stmt = _connection.createStatement();
			String sql = "DROP TABLE IF EXISTS "+TableDescriptionTableDescription.TABLE_NAME+" " ; 
			stmt.executeUpdate(sql);
			stmt.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
