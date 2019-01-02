
package com.sqlite.dal.test.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sqlite.dal.test.project.pojo.ENTREPRISETableRecordPOJO;
import com.sqlite.dal.test.project.tabledefinitions.ENTREPRISETableDefinition;


public class ENTREPRISEJavaSQLiteTableDAO
{
	private static Connection _connection;
	public static void setConnection(Connection SQLiteDatabaseConnection)
	{
		_connection = SQLiteDatabaseConnection;
	}

	public static void CreateAssociatedTableInDB()
	{
		Statement stmt;
		try {
			stmt = _connection.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS " + ENTREPRISETableDefinition.TABLE_NAME + " " +
	                "(" + 
						  
	                            " " + ENTREPRISETableDefinition.ID_ENTREPRISE_COLUMN + " INTEGER PRIMARY KEY NOT NULL " +"," + 
	                            " " + ENTREPRISETableDefinition.NOM_COLUMN + " STRING NOT NULL " +"," + 
	                            " " + ENTREPRISETableDefinition.ID_ADRESSE_COLUMN + " INTEGER NOT NULL " +
					")"; 
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private static final String SELECT_QUERY="SELECT " +
			
			
			""+ ENTREPRISETableDefinition.ID_ENTREPRISE_COLUMN +"," + 
			""+ ENTREPRISETableDefinition.NOM_COLUMN +"," + 
			""+ ENTREPRISETableDefinition.ID_ADRESSE_COLUMN +
		" FROM " +
			" ENTREPRISE";
	public static List<ENTREPRISETableRecordPOJO> Select(String WhereArgsStr)
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
			List<ENTREPRISETableRecordPOJO> result = new ArrayList<ENTREPRISETableRecordPOJO>();
			while(resultSet.next())
			{
				ENTREPRISETableRecordPOJO currentRecord = new ENTREPRISETableRecordPOJO();
				
				
				currentRecord.setID_ENTREPRISE(resultSet.getint(ENTREPRISETableDefinition.ID_ENTREPRISE_COLUMN));
				currentRecord.setNOM(resultSet.getString(ENTREPRISETableDefinition.NOM_COLUMN));
				currentRecord.setID_ADRESSE(resultSet.getint(ENTREPRISETableDefinition.ID_ADRESSE_COLUMN));
				result.add(currentRecord);
			}
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	private final static String INSERT_STATEMENT = 
			"INSERT INTO COMPANY " +
					"(" + 
					
					""+ENTREPRISETableDefinition.ID_ENTREPRISE_COLUMN+"," +  
					""+ENTREPRISETableDefinition.NOM_COLUMN+"," +  
					""+ENTREPRISETableDefinition.ID_ADRESSE_COLUMN+ 
					") VALUES (" + 
					
					
					 " ?,"  + 
					 " ?,"  + 
					 " ?"  + 
					");";
	
	public static boolean Insert(ENTREPRISETableRecordPOJO pojo)
	{
		if(pojo == null) return false;
		PreparedStatement stmt;
		try {
			stmt = _connection.prepareStatement(INSERT_STATEMENT);
			_connection.setAutoCommit(false);
			
			
		stmt.setint(0 + 1, pojo.getID_ENTREPRISE());
		stmt.setString(1 + 1, pojo.getNOM());
		stmt.setint(2 + 1, pojo.getID_ADRESSE());
		    stmt.executeUpdate();
		    
		    
		   
		   
		   
		    _connection.commit();
		    _connection.setAutoCommit(true);
		    return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean Insert(List<ENTREPRISETableRecordPOJO> pojoList)
	{
		if(pojoList==null) return false;
		try {
			_connection.setAutoCommit(false);
			
			for(int currentIndex = 0 ; currentIndex < pojoList.size(); currentIndex++ )
			{
				ENTREPRISETableRecordPOJO currentPojo = pojoList.get(currentIndex);
				PreparedStatement stmt = _connection.prepareStatement(INSERT_STATEMENT);
				
				
			stmt.setint(1 /*TODO*/, currentPojo.getID_ENTREPRISE());
			stmt.setString(1 /*TODO*/, currentPojo.getNOM());
			stmt.setint(1 /*TODO*/, currentPojo.getID_ADRESSE());
			    stmt.executeUpdate();
			    {:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:KEY:AUTO(
			    currentPojo.setId(stmt.getGeneratedKeys().get{:TDB:TABLE:COLUMN:FOREACH:CURRENT:TYPE:CONVERT(JAVA)::}(1 /*TODO*/));
			    )KEY:AUTO:::}
			}
			_connection.commit();  
			_connection.setAutoCommit(true);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private final static String UPDATE_QUERY = "UPDATE "+ENTREPRISETableDefinition.TABLE_NAME+" " +
			"SET " + 
			
			
		
		 ""+ENTREPRISETableDefinition.NOM_COLUMN+"=," + 
		 ""+ENTREPRISETableDefinition.ID_ADRESSE_COLUMN+"=" + 
			" WHERE "
			
			
			 + ""+ENTREPRISETableDefinition.ID_ENTREPRISE_COLUMN+"=, {:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:LAST:COLUMN(;):::}";
			
			
	public static boolean Update(ENTREPRISETableRecordPOJO pojo)
	{
		if(pojo == null) return false;
		try {
			PreparedStatement stmt = _connection.prepareStatement(UPDATE_QUERY);
			_connection.setAutoCommit(false);
			
			
		
		 stmt.setString(1 /*TODO*/, pojo.getNOM());
		 stmt.setint(1 /*TODO*/, pojo.getID_ADRESSE());
			
			
			
		stmt.setint(5 /*TODO*/, pojo.getID_ENTREPRISE());
		
		
			stmt.execute();
			_connection.setAutoCommit(true);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean Update(List<ENTREPRISETableRecordPOJO> pojoList)
	{
		if(pojoList == null) return false;
		try {
			_connection.setAutoCommit(false);
			for(int currentIndex=0;currentIndex<pojoList.size();currentIndex++)
			{
				ENTREPRISETableRecordPOJO currentPOJO = pojoList.get(currentIndex);
				PreparedStatement stmt = _connection.prepareStatement(UPDATE_QUERY);
				
       			
       		
       		 stmt.setString(1 /*TODO*/, currentPOJO.getNOM());
       		 stmt.setint(1 /*TODO*/, currentPOJO.getID_ADRESSE());
       			
       			
       			
       		stmt.setint(5 /*TODO*/, currentPOJO.getID_ENTREPRISE());)KEY:NOT:PRIMARY:::}
				
				stmt.execute();
			}
			_connection.setAutoCommit(true);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	private final static String DELETE_QUERY="DELETE FROM "+ENTREPRISETableDefinition.TABLE_NAME+" WHERE " +
			
   			
       				{:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:KEY:PRIMARY( + ""+ENTREPRISETableDefinition.ID_ENTREPRISE_COLUMN+"=, {:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:LAST:COLUMN(;):::}";
       			
       			
	public static boolean Delete(ENTREPRISETableRecordPOJO pojo)
	{
		if(pojo == null) return false;
		try {
			PreparedStatement stmt = _connection.prepareStatement(DELETE_QUERY);
			_connection.setAutoCommit(false);
			
   			
   		stmt.setint(5 /*TODO*/, pojo.getID_ENTREPRISE());
   		
   		
			stmt.execute();
			_connection.setAutoCommit(true);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public static boolean Delete(List<ENTREPRISETableRecordPOJO> pojoList)
	{
		if(pojoList == null) return false;
		try {
			_connection.setAutoCommit(false);
			for(int currentIndex=0;currentIndex<pojoList.size();currentIndex++)
			{
				CompanyTableRecordPOJO currentPOJO = pojoList.get(currentIndex);
				PreparedStatement stmt = _connection.prepareStatement(DELETE_QUERY);
				
				
      			
      		stmt.setint(1 /*TODO*/, currentPOJO.getID_ENTREPRISE());
      		
      		
				
				stmt.execute();
			}
			_connection.setAutoCommit(true);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static void DropTable()
	{
		Statement stmt;
		try {
			stmt = _connection.createStatement();
			String sql = "DROP TABLE IF EXISTS "+ENTREPRISETableDefinition.TABLE_NAME+" " ; 
			stmt.executeUpdate(sql);
			stmt.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
