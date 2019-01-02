package com.mz.sqlite.dal.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseDescriptionJavaSQLiteTableSelectDAO {
	private Connection _connection;
	public DatabaseDescriptionJavaSQLiteTableSelectDAO(Connection SQLiteDatabaseConnection)
	{
		_connection = SQLiteDatabaseConnection;
	}
	
	private static final String SELECT_QUERY="SELECT " +
			
			""+ DatabaseDescriptionTableDescription.DatabaseName_COLUMN +", " +
			""+ DatabaseDescriptionTableDescription.Id_COLUMN +" " +
		" FROM " +
			" DatabaseDescription";
	public List<DatabaseDescriptionTableRecordPOJO> Select(String WhereArgsStr)
	{
		Statement stmt;
		try {
			stmt = _connection.createStatement();
		
			String sql = SELECT_QUERY;
			if(WhereArgsStr != null && !WhereArgsStr.isEmpty())
			{
				sql = sql + " WHERE " + WhereArgsStr; 
			}
			ResultSet resultSet = stmt.executeQuery(sql);
			List<DatabaseDescriptionTableRecordPOJO> result = new ArrayList<DatabaseDescriptionTableRecordPOJO>();
			while(resultSet.next())
			{
				DatabaseDescriptionTableRecordPOJO currentRecord = new DatabaseDescriptionTableRecordPOJO();
				
				currentRecord.setDatabaseNameString(resultSet.getString(DatabaseDescriptionTableDescription.DatabaseName_COLUMN));
				currentRecord.setIdInt(resultSet.getInt(DatabaseDescriptionTableDescription.Id_COLUMN));
				result.add(currentRecord);
			}
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
