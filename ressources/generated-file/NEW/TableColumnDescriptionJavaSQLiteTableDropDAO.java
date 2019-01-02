package com.sqlite.dal.test.project.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TableColumnDescriptionJavaSQLiteTableDropDAO {
	private Connection _connection;
	public TableColumnDescriptionJavaSQLiteTableDropDAO(Connection SQLiteDatabaseConnection)
	{
		_connection = SQLiteDatabaseConnection;
	}
	
	public void DropTable()
	{
		Statement stmt;
		try {
			stmt = _connection.createStatement();
			String sql = "DROP TABLE IF EXISTS "+TableColumnDescriptionTableDescription.TABLE_NAME+" " ; 
			stmt.executeUpdate(sql);
			stmt.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
