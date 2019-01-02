package com.sqlite.dal.test.project.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TableDescriptionJavaSQLiteTableSelectDAO {
	private Connection _connection;
	public TableDescriptionJavaSQLiteTableSelectDAO(Connection SQLiteDatabaseConnection)
	{
		_connection = SQLiteDatabaseConnection;
	}
	
	private static final String SELECT_QUERY="SELECT " +
			
			""+ TableDescriptionTableDescription.NameStr_COLUMN +", " +
			""+ TableDescriptionTableDescription.Id_COLUMN +", " +
			""+ TableDescriptionTableDescription.IdDatabaseDescription_COLUMN +" " +
		" FROM " +
			" TableDescription";
	public List<TableDescriptionTableRecordPOJO> Select(String WhereArgsStr)
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
			List<TableDescriptionTableRecordPOJO> result = new ArrayList<TableDescriptionTableRecordPOJO>();
			while(resultSet.next())
			{
				TableDescriptionTableRecordPOJO currentRecord = new TableDescriptionTableRecordPOJO();
				
				currentRecord.setNameStrString(resultSet.getString(TableDescriptionTableDescription.NameStr_COLUMN));
				currentRecord.setIdInt(resultSet.getInt(TableDescriptionTableDescription.Id_COLUMN));
				currentRecord.setIdDatabaseDescriptionInt(resultSet.getInt(TableDescriptionTableDescription.IdDatabaseDescription_COLUMN));
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
