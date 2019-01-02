package com.mz.database.structure.loaders.manager;

import java.sql.SQLException;

import com.mz.database.models.DatabaseDescriptionPOJO;
import com.mz.database.plugins.structure.adapters.MySQLDatabaseAdapter;

public class DatabaseStructureLoaderManager {

	private final static String MYSQL_DATABASE_TYPE = "MYSQL";
	
	public DatabaseStructureLoaderManager()
	{
		
	}
	
	public DatabaseDescriptionPOJO 
		GetDatabaseDescriptionPOJO(
				String user, String password, String databaseName,String serverName) throws SQLException, ClassNotFoundException
	{
		//TODO make other sgbd database structure loader available
		MySQLDatabaseAdapter adapter = new MySQLDatabaseAdapter
				(user, password, databaseName, serverName);
		return adapter.GetDatabasePOJO();
	}
	
}
