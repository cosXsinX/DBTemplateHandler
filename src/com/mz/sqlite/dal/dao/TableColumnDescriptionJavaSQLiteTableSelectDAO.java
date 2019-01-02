package com.mz.sqlite.dal.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TableColumnDescriptionJavaSQLiteTableSelectDAO {
	private Connection _connection;
	public TableColumnDescriptionJavaSQLiteTableSelectDAO(Connection SQLiteDatabaseConnection)
	{
		_connection = SQLiteDatabaseConnection;
	}
	
	private static final String SELECT_QUERY="SELECT " +
			
			""+ TableColumnDescriptionTableDescription.NameStr_COLUMN +", " +
			""+ TableColumnDescriptionTableDescription.TypeStr_COLUMN +", " +
			""+ TableColumnDescriptionTableDescription.PrimaryKey_COLUMN +", " +
			""+ TableColumnDescriptionTableDescription.AutoGeneratedValueBln_COLUMN +", " +
			""+ TableColumnDescriptionTableDescription.NotNull_COLUMN +", " +
			""+ TableColumnDescriptionTableDescription.Id_COLUMN +", " +
			""+ TableColumnDescriptionTableDescription.IdTableDescription_COLUMN +" " +
		" FROM " +
			" TableColumnDescription";
	public List<TableColumnDescriptionTableRecordPOJO> Select(String WhereArgsStr)
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
			List<TableColumnDescriptionTableRecordPOJO> result = new ArrayList<TableColumnDescriptionTableRecordPOJO>();
			while(resultSet.next())
			{
				TableColumnDescriptionTableRecordPOJO currentRecord = new TableColumnDescriptionTableRecordPOJO();
				
				currentRecord.setNameStrString(resultSet.getString(TableColumnDescriptionTableDescription.NameStr_COLUMN));
				currentRecord.setTypeStrString(resultSet.getString(TableColumnDescriptionTableDescription.TypeStr_COLUMN));
				currentRecord.setPrimaryKeyBoolean(resultSet.getBoolean(TableColumnDescriptionTableDescription.PrimaryKey_COLUMN));
				currentRecord.setAutoGeneratedValueBlnBoolean(resultSet.getBoolean(TableColumnDescriptionTableDescription.AutoGeneratedValueBln_COLUMN));
				currentRecord.setNotNullBoolean(resultSet.getBoolean(TableColumnDescriptionTableDescription.NotNull_COLUMN));
				currentRecord.setIdInt(resultSet.getInt(TableColumnDescriptionTableDescription.Id_COLUMN));
				currentRecord.setIdTableDescriptionInt(resultSet.getInt(TableColumnDescriptionTableDescription.IdTableDescription_COLUMN));
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
