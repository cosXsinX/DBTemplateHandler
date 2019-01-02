package com.sqlite.dal.test.project.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class {:TDB:TABLE:CURRENT:NAME::}JavaSQLiteTableSelectDAO {
	private Connection _connection;
	public {:TDB:TABLE:CURRENT:NAME::}JavaSQLiteTableSelectDAO(Connection SQLiteDatabaseConnection)
	{
		_connection = SQLiteDatabaseConnection;
	}
	
	private static final String SELECT_QUERY="SELECT " +
			{:TDB:TABLE:COLUMN:FOREACH[
			""+ {:TDB:TABLE:CURRENT:NAME::}TableDescription.{:TDB:TABLE:COLUMN:FOREACH:CURRENT:NAME::}_COLUMN +"{:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:NOT:LAST:COLUMN(,):::} " +]::}
		" FROM " +
			" {:TDB:TABLE:CURRENT:NAME::}";
	public List<{:TDB:TABLE:CURRENT:NAME::}TableRecordPOJO> Select(String WhereArgsStr)
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
			List<{:TDB:TABLE:CURRENT:NAME::}TableRecordPOJO> result = new ArrayList<{:TDB:TABLE:CURRENT:NAME::}TableRecordPOJO>();
			while(resultSet.next())
			{
				{:TDB:TABLE:CURRENT:NAME::}TableRecordPOJO currentRecord = new {:TDB:TABLE:CURRENT:NAME::}TableRecordPOJO();
				{:TDB:TABLE:COLUMN:FOREACH[
				currentRecord.set{:TDB:TABLE:COLUMN:FOREACH:CURRENT:NAME::}{:TDB:FUNCTION:FIRST:CHARACTER:TO:UPPER:CASE({:TDB:TABLE:COLUMN:FOREACH:CURRENT:CONVERT:TYPE(JAVA)::})::}(resultSet.get{:TDB:FUNCTION:FIRST:CHARACTER:TO:UPPER:CASE({:TDB:TABLE:COLUMN:FOREACH:CURRENT:CONVERT:TYPE(JAVA)::})::}({:TDB:TABLE:CURRENT:NAME::}TableDescription.{:TDB:TABLE:COLUMN:FOREACH:CURRENT:NAME::}_COLUMN));]::}
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
