package com.sqlite.dal.test.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class TableColumnDescriptionJavaSQLiteTableInsertDAO {
	private Connection _connection;
	public TableColumnDescriptionJavaSQLiteTableInsertDAO(Connection SQLiteDatabaseConnection)
	{
		_connection = SQLiteDatabaseConnection;
	}
	
	private final static String INSERT_STATEMENT = 
			"INSERT INTO "+TableColumnDescriptionTableDescription.TABLE_NAME+" " +
					"(" +
						
						""+TableColumnDescriptionTableDescription.NameStr_COLUMN+"," +
						""+TableColumnDescriptionTableDescription.TypeStr_COLUMN+"," +
						""+TableColumnDescriptionTableDescription.PrimaryKey_COLUMN+"," +
						""+TableColumnDescriptionTableDescription.AutoGeneratedValueBln_COLUMN+"," +
						""+TableColumnDescriptionTableDescription.NotNull_COLUMN+"," +
						""+TableColumnDescriptionTableDescription.Id_COLUMN+"," +
						""+TableColumnDescriptionTableDescription.IdTableDescription_COLUMN +
					") VALUES (?,?,?,?,?,?,?);";
	
	public boolean Insert(TableColumnDescriptionTableRecordPOJO pojo)
	{
		if(pojo == null) return false;
		PreparedStatement stmt;
		try {
			stmt = _connection.prepareStatement(INSERT_STATEMENT);
			_connection.setAutoCommit(false);
			
			stmt.setString(0, pojo.getNameStrString());
			stmt.setString(1, pojo.getTypeStrString());
			stmt.setBoolean(2, pojo.getPrimaryKeyBoolean());
			stmt.setBoolean(3, pojo.getAutoGeneratedValueBlnBoolean());
			stmt.setBoolean(4, pojo.getNotNullBoolean());
			stmt.setInt(5, pojo.getIdInt());
			stmt.set(6, pojo.getIdTableDescription());
		    stmt.executeUpdate();
		    
		    _connection.commit();
		    _connection.setAutoCommit(true);
		    return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean Insert(List<TableColumnDescriptionTableRecordPOJO> pojoList)
	{
		if(pojoList==null) return false;
		try {
			_connection.setAutoCommit(false);
			
			for(int currentIndex = 0 ; currentIndex < pojoList.size(); currentIndex++ )
			{
				TableColumnDescriptionTableRecordPOJO currentPojo = pojoList.get(currentIndex);
				PreparedStatement stmt = _connection.prepareStatement(INSERT_STATEMENT);
				
				stmt.setString(0, currentPojo.getNameStrString());
				stmt.setString(1, currentPojo.getTypeStrString());
				stmt.setBoolean(2, currentPojo.getPrimaryKeyBoolean());
				stmt.setBoolean(3, currentPojo.getAutoGeneratedValueBlnBoolean());
				stmt.setBoolean(4, currentPojo.getNotNullBoolean());
				stmt.setInt(5, currentPojo.getIdInt());
				stmt.set(6, currentPojo.getIdTableDescription());
			    stmt.executeUpdate();
			    currentPojo.setId(stmt.getGeneratedKeys().getInt(1));
			}
			_connection.commit();  
			_connection.setAutoCommit(true);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
