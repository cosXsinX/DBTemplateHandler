package com.sqlite.dal.test.project.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class {:TDB:TABLE:CURRENT:NAME::}JavaSQLiteTableCreateDAO {
	private Connection _connection;

	public {:TDB:TABLE:CURRENT:NAME::}JavaSQLiteTableCreateDAO(Connection SQLiteDatabaseConnection)
	{
		_connection = SQLiteDatabaseConnection;
	}
	

	public void CreateTable()
	{
		Statement stmt;
		try {
			stmt = _connection.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS " + {:TDB:TABLE:CURRENT:NAME::}TableDescription.TABLE_NAME + " " +
	                {:TDB:TABLE:COLUMN:FOREACH[
				"{:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:FIRST:COLUMN(():::}" + {:TDB:TABLE:CURRENT:NAME::}TableDescription.{:TDB:TABLE:COLUMN:FOREACH:CURRENT:NAME::}_COLUMN + " {:TDB:TABLE:COLUMN:FOREACH:CURRENT:TYPE::} {:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:KEY:PRIMARY(PRIMARY KEY)KEY:PRIMARY:::} {:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:KEY:AUTO(AUTOINCREMENT)KEY:AUTO:::}{:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:NOT:LAST:COLUMN(,):::}{:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:LAST:COLUMN()):::}" {:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:NOT:LAST:COLUMN(+):::}{:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:LAST:COLUMN(;):::}]::}
			stmt.executeUpdate(sql);
			stmt.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
