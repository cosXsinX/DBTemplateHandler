package com.sqlite.dal.test.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class {:TDB:TABLE:CURRENT:NAME::}JavaSQLiteTableInsertDAO {
	private Connection _connection;
	public {:TDB:TABLE:CURRENT:NAME::}JavaSQLiteTableInsertDAO(Connection SQLiteDatabaseConnection)
	{
		_connection = SQLiteDatabaseConnection;
	}
	
	private final static String INSERT_STATEMENT = 
			"INSERT INTO "+{:TDB:TABLE:CURRENT:NAME::}TableDescription.TABLE_NAME+" " +
					"(" +
						{:TDB:TABLE:COLUMN:NOT:AUTO:FOREACH[
						""+{:TDB:TABLE:CURRENT:NAME::}TableDescription.{:TDB:TABLE:COLUMN:FOREACH:CURRENT:NAME::}_COLUMN{:TDB:TABLE:COLUMN:NOT:AUTO:FOREACH:CURRENT:IS:NOT:LAST:COLUMN(+","):::} +]::}
					") VALUES ({:TDB:TABLE:COLUMN:NOT:AUTO:FOREACH[?{:TDB:TABLE:COLUMN:NOT:AUTO:FOREACH:CURRENT:IS:NOT:LAST:COLUMN(,):::}]::});";
	
	public boolean Insert({:TDB:TABLE:CURRENT:NAME::}TableRecordPOJO pojo)
	{
		if(pojo == null) return false;
		PreparedStatement stmt;
		try {
			stmt = _connection.prepareStatement(INSERT_STATEMENT);
			_connection.setAutoCommit(false);
			{:TDB:TABLE:COLUMN:NOT:AUTO:FOREACH[
			stmt.set{:TDB:FUNCTION:FIRST:CHARACTER:TO:UPPER:CASE({:TDB:TABLE:COLUMN:FOREACH:CURRENT:CONVERT:TYPE(JAVA)::})::}({:TDB:TABLE:COLUMN:NOT:AUTO:FOREACH:CURRENT:INDEX::}, pojo.get{:TDB:TABLE:COLUMN:FOREACH:CURRENT:NAME::}{:TDB:FUNCTION:FIRST:CHARACTER:TO:UPPER:CASE({:TDB:TABLE:COLUMN:FOREACH:CURRENT:CONVERT:TYPE(JAVA)::})::}());]::}
		    stmt.executeUpdate();
		    {:TDB:TABLE:COLUMN:AUTO:FOREACH[
		pojo.set{:TDB:TABLE:COLUMN:FOREACH:CURRENT:NAME::}(stmt.getGeneratedKeys().getInt({:TDB:TABLE:COLUMN:AUTO:FOREACH:CURRENT:INDEX::}));]::}
		    _connection.commit();
		    _connection.setAutoCommit(true);
		    return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean Insert(List<{:TDB:TABLE:CURRENT:NAME::}TableRecordPOJO> pojoList)
	{
		if(pojoList==null) return false;
		try {
			_connection.setAutoCommit(false);
			
			for(int currentIndex = 0 ; currentIndex < pojoList.size(); currentIndex++ )
			{
				{:TDB:TABLE:CURRENT:NAME::}TableRecordPOJO currentPojo = pojoList.get(currentIndex);
				PreparedStatement stmt = _connection.prepareStatement(INSERT_STATEMENT);
				{:TDB:TABLE:COLUMN:NOT:AUTO:FOREACH[
				stmt.set{:TDB:FUNCTION:FIRST:CHARACTER:TO:UPPER:CASE({:TDB:TABLE:COLUMN:FOREACH:CURRENT:CONVERT:TYPE(JAVA)::})::}({:TDB:TABLE:COLUMN:NOT:AUTO:FOREACH:CURRENT:INDEX::}, currentPojo.get{:TDB:TABLE:COLUMN:FOREACH:CURRENT:NAME::}{:TDB:FUNCTION:FIRST:CHARACTER:TO:UPPER:CASE({:TDB:TABLE:COLUMN:FOREACH:CURRENT:CONVERT:TYPE(JAVA)::})::}());]::}
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
