package com.sqlite.dal.test.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class {:TDB:TABLE:CURRENT:NAME::}JavaSQLiteTableUpdateDAO {
	private Connection _connection;
	public {:TDB:TABLE:CURRENT:NAME::}JavaSQLiteTableUpdateDAO(Connection SQLiteDatabaseConnection)
	{
		_connection = SQLiteDatabaseConnection;
	}
	
	private final static String UPDATE_QUERY = "UPDATE "+{:TDB:TABLE:CURRENT:NAME::}TableDescription.TABLE_NAME+" " +
			"SET " + 
				{:TDB:TABLE:COLUMN:NOT:PRIMARY:FOREACH[{:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:KEY:NOT:AUTO(
				""+{:TDB:TABLE:CURRENT:NAME::}TableDescription.{:TDB:TABLE:COLUMN:FOREACH:CURRENT:NAME::}_COLUMN+"=?{:TDB:TABLE:COLUMN:NOT:PRIMARY:FOREACH:CURRENT:IS:NOT:LAST:COLUMN(,):::}" +)KEY:NOT:AUTO:::}]::}
			" WHERE "
				{:TDB:TABLE:COLUMN:PRIMARY:FOREACH[+ ""+{:TDB:TABLE:CURRENT:NAME::}TableDescription.{:TDB:TABLE:COLUMN:FOREACH:CURRENT:NAME::}_COLUMN+"=?{:TDB:TABLE:COLUMN:PRIMARY:FOREACH:CURRENT:IS:NOT:LAST:COLUMN(,):::}"+";";]::}
	public boolean Update({:TDB:TABLE:CURRENT:NAME::}TableRecordPOJO pojo)
	{
		if(pojo == null) return false;
		try {
			PreparedStatement stmt = _connection.prepareStatement(UPDATE_QUERY);
			int currentColumnIndex = 1;
			_connection.setAutoCommit(false);{:TDB:TABLE:COLUMN:NOT:PRIMARY:FOREACH[
			stmt.set{:TDB:FUNCTION:FIRST:CHARACTER:TO:UPPER:CASE({:TDB:TABLE:COLUMN:FOREACH:CURRENT:CONVERT:TYPE(JAVA)::})::}( currentColumnIndex++ , pojo.get{:TDB:TABLE:COLUMN:FOREACH:CURRENT:NAME::}{:TDB:FUNCTION:FIRST:CHARACTER:TO:UPPER:CASE({:TDB:TABLE:COLUMN:FOREACH:CURRENT:CONVERT:TYPE(JAVA)::})::}());]::}
			stmt.execute();
			_connection.setAutoCommit(true);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean Update(List<{:TDB:TABLE:CURRENT:NAME::}TableRecordPOJO> pojoList)
	{
		if(pojoList == null) return false;
		try {
			_connection.setAutoCommit(false);
			for(int currentIndex=0;currentIndex<pojoList.size();currentIndex++)
			{
				{:TDB:TABLE:CURRENT:NAME::}TableRecordPOJO currentPOJO = pojoList.get(currentIndex);
				int currentColumnIndex = 1;
				PreparedStatement stmt = _connection.prepareStatement(UPDATE_QUERY);{:TDB:TABLE:COLUMN:NOT:PRIMARY:FOREACH[
				stmt.set{:TDB:FUNCTION:FIRST:CHARACTER:TO:UPPER:CASE({:TDB:TABLE:COLUMN:FOREACH:CURRENT:CONVERT:TYPE(JAVA)::})::}(currentColumnIndex++, currentPOJO.get{:TDB:TABLE:COLUMN:FOREACH:CURRENT:NAME::}{:TDB:FUNCTION:FIRST:CHARACTER:TO:UPPER:CASE({:TDB:TABLE:COLUMN:FOREACH:CURRENT:CONVERT:TYPE(JAVA)::})::}());]::}
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
