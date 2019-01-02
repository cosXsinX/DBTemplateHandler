package com.mz.database.plugins.structure.adapters.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionManager {
	
	
	public Connection GetConnection(DatabaseConnectionParameter connectionParameter){
		if(connectionParameter == null) return null;
		Connection connection = null;
		try {
			Class.forName(connectionParameter.get_JavaConnectionDriverClassName());
			String url = connectionParameter.get_DatabaseConnectionString();
			connection = DriverManager.getConnection(url, connectionParameter.get_DatabaseConnectionUserNameString(), connectionParameter.get_DatabaseConnectionUserPasswordString());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	
}
