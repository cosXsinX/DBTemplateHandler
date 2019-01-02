package com.mz.database.plugins.structure.adapters;

import java.sql.Connection;

import com.mz.database.models.DatabaseDescriptionPOJO;
import com.mz.database.plugins.structure.adapters.common.AbstractDatabaseStructureAdapter;
import com.mz.database.plugins.structure.adapters.common.DatabaseConnectionParameter;
import com.mz.database.plugins.structure.adapters.common.DatabaseStructureAdapterConfigParameters;

public class MSAccessDatabaseAdapter extends AbstractDatabaseStructureAdapter {

	private final static String MANAGED_DBMS_NAME = "MS Access";
	
	private final static String JDBC_ODBC_CONNECTION_DRIVER =  "sun.jdbc.odbc.JdbcOdbcDriver";
	private final static String MS_ACCESS_CONNECTION_STRING = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=" + "C:\\bank.accdb";
	
	private Connection databaseConnection;
	
	@Override
	public String get_DatabaseManagementSystemName() {
		return MANAGED_DBMS_NAME;
	}
	
	@Override
	public void Setup(DatabaseStructureAdapterConfigParameters ConnectionParameters) {
		DatabaseConnectionParameter dbConnectionParameters;
		
	}

	@Override
	public DatabaseDescriptionPOJO GetDatabaseStructureSchemaDefinition() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
