package com.mz.database.plugins.structure.adapters;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.mz.database.models.DatabaseDescriptionPOJO;
import com.mz.database.plugins.structure.adapters.common.AbstractDatabaseStructureAdapter;
import com.mz.database.plugins.structure.adapters.common.DatabaseStructureAdapterConfigParameters;

public class SQLiteDatabaseAdapter extends AbstractDatabaseStructureAdapter {

	private final static String MANAGED_DBMS_NAME = "SQLite";
	
	@Override
	public String get_DatabaseManagementSystemName() {
		return MANAGED_DBMS_NAME;
	}
	
	@Override
	public void Setup(DatabaseStructureAdapterConfigParameters ConnectionParameters) {
		Connection c = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:test.db");
	      Statement stmt = c.createStatement();
	      ResultSet rs = stmt.executeQuery( "SELECT name FROM sqlite_master WHERE type='table';" );
	      while ( rs.next() ) {
	         String type = rs.getString("type");
	         String  name = rs.getString("name");
	         String tbl_name  = rs.getString("tbl_name");
	         int rootpage = rs.getInt("rootpage");
	         String sql = rs.getString("sql");
	         System.out.println( "type = " + type );
	         System.out.println( "NAME = " + name );
	         System.out.println( "tbl_name = " + tbl_name );
	         System.out.println( "rootpage = " + rootpage );
	         System.out.println("sql = " + sql);
	         System.out.println();
	      }
	      rs.close();
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Opened database successfully");
	}

	@Override
	public DatabaseDescriptionPOJO GetDatabaseStructureSchemaDefinition() {
		// TODO Auto-generated method stub
		return null;
	}
}
