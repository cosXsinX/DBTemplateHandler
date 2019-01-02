package com.sqlite.dal.test.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class {:TDB:TABLE:CURRENT:NAME::}JavaSQLiteTableDeleteDAO {
	private Connection _connection;
	public {:TDB:TABLE:CURRENT:NAME::}JavaSQLiteTableDeleteDAO(Connection SQLiteDatabaseConnection)
	{
		_connection = SQLiteDatabaseConnection;
	}
	
	private final static String DELETE_QUERY="DELETE FROM "+{:TDB:TABLE:CURRENT:NAME::}TableDescription.TABLE_NAME+" WHERE " +
			""+{:TDB:TABLE:CURRENT:NAME::}TableDescription.ID_COLUMN+"=?";
	public boolean Delete({:TDB:TABLE:CURRENT:NAME::}TableRecordPOJO pojo)
	{
		if(pojo == null) return false;
		try {
			PreparedStatement stmt = _connection.prepareStatement(DELETE_QUERY);
			_connection.setAutoCommit(false);
			{:TDB:TABLE:COLUMN:PRIMARY:FOREACH[
			stmt.set{:TDB:FUNCTION:FIRST:CHARACTER:TO:UPPER:CASE({:TDB:TABLE:COLUMN:FOREACH:CURRENT:CONVERT:TYPE(JAVA)::})::}({:TDB:TABLE:COLUMN:PRIMARY:FOREACH:CURRENT:INDEX::}, pojo.get{:TDB:TABLE:COLUMN:FOREACH:CURRENT:NAME::}{:TDB:FUNCTION:FIRST:CHARACTER:TO:UPPER:CASE({:TDB:TABLE:COLUMN:FOREACH:CURRENT:CONVERT:TYPE(JAVA)::})::}());]::}
			
			stmt.execute();
			_connection.setAutoCommit(true);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public boolean Delete(List<{:TDB:TABLE:CURRENT:NAME::}TableRecordPOJO> pojoList)
	{
		if(pojoList == null) return false;
		try {
			_connection.setAutoCommit(false);
			for(int currentIndex=0;currentIndex<pojoList.size();currentIndex++)
			{
				{:TDB:TABLE:CURRENT:NAME::}TableRecordPOJO currentPOJO = pojoList.get(currentIndex);
				PreparedStatement stmt = _connection.prepareStatement(DELETE_QUERY);
				{:TDB:TABLE:COLUMN:PRIMARY:FOREACH[
				stmt.set{:TDB:FUNCTION:FIRST:CHARACTER:TO:UPPER:CASE({:TDB:TABLE:COLUMN:FOREACH:CURRENT:CONVERT:TYPE(JAVA)::})::}({:TDB:TABLE:COLUMN:PRIMARY:FOREACH:CURRENT:INDEX::}, currentPOJO.get{:TDB:TABLE:COLUMN:FOREACH:CURRENT:NAME::}{:TDB:FUNCTION:FIRST:CHARACTER:TO:UPPER:CASE({:TDB:TABLE:COLUMN:FOREACH:CURRENT:CONVERT:TYPE(JAVA)::})::}());]::}
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
