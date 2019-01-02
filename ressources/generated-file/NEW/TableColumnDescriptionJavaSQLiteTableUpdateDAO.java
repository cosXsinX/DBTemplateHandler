package com.sqlite.dal.test.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class TableColumnDescriptionJavaSQLiteTableUpdateDAO {
	private Connection _connection;
	public TableColumnDescriptionJavaSQLiteTableUpdateDAO(Connection SQLiteDatabaseConnection)
	{
		_connection = SQLiteDatabaseConnection;
	}
	
	private final static String UPDATE_QUERY = "UPDATE "+TableColumnDescriptionTableDescription.TABLE_NAME+" " +
			"SET " + 
				
				""+TableColumnDescriptionTableDescription.NameStr_COLUMN+"=?," +
				""+TableColumnDescriptionTableDescription.TypeStr_COLUMN+"=?," +
				""+TableColumnDescriptionTableDescription.PrimaryKey_COLUMN+"=?," +
				""+TableColumnDescriptionTableDescription.AutoGeneratedValueBln_COLUMN+"=?," +
				""+TableColumnDescriptionTableDescription.NotNull_COLUMN+"=?," +
				""+TableColumnDescriptionTableDescription.IdTableDescription_COLUMN+"=?" +
			" WHERE "
				+ ""+TableColumnDescriptionTableDefinition.Id_COLUMN+"=?"+";";
	public boolean Update(TableColumnDescriptionTableRecordPOJO pojo)
	{
		if(pojo == null) return false;
		try {
			PreparedStatement stmt = _connection.prepareStatement(UPDATE_QUERY);
			int currentColumnIndex = 1;
			_connection.setAutoCommit(false);
			stmt.setString( currentColumnIndex++ , pojo.getNameStrString());
			stmt.setString( currentColumnIndex++ , pojo.getTypeStrString());
			stmt.setBoolean( currentColumnIndex++ , pojo.getPrimaryKeyBoolean());
			stmt.setBoolean( currentColumnIndex++ , pojo.getAutoGeneratedValueBlnBoolean());
			stmt.setBoolean( currentColumnIndex++ , pojo.getNotNullBoolean());
			stmt.set( currentColumnIndex++ , pojo.getIdTableDescription());
			stmt.execute();
			_connection.setAutoCommit(true);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean Update(List<TableColumnDescriptionTableRecordPOJO> pojoList)
	{
		if(pojoList == null) return false;
		try {
			_connection.setAutoCommit(false);
			for(int currentIndex=0;currentIndex<pojoList.size();currentIndex++)
			{
				TableColumnDescriptionTableRecordPOJO currentPOJO = pojoList.get(currentIndex);
				int currentColumnIndex = 1;
				PreparedStatement stmt = _connection.prepareStatement(UPDATE_QUERY);
				stmt.setString(currentColumnIndex++, currentPOJO.getNameStrString());
				stmt.setString(currentColumnIndex++, currentPOJO.getTypeStrString());
				stmt.setBoolean(currentColumnIndex++, currentPOJO.getPrimaryKeyBoolean());
				stmt.setBoolean(currentColumnIndex++, currentPOJO.getAutoGeneratedValueBlnBoolean());
				stmt.setBoolean(currentColumnIndex++, currentPOJO.getNotNullBoolean());
				stmt.set(currentColumnIndex++, currentPOJO.getIdTableDescription());
				stmt.execute();
			}
			_connection.setAutoCommit(true);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
