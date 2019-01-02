
package com.sqlite.dal.test.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sqlite.dal.test.project.pojo.PAYSTableRecordPOJO;
import com.sqlite.dal.test.project.tabledefinitions.PAYSTableDefinition;


public class PAYSJavaSQLiteTableDAO
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
			String sql = "CREATE TABLE IF NOT EXISTS " + PAYSTableDefinition.TABLE_NAME + " " +
	                "(" + 
						  
	                            " " + PAYSTableDefinition.ID_PAYS_COLUMN + " INTEGER PRIMARY KEY NOT NULL " +"," + 
	                            " " + PAYSTableDefinition.NOM_COLUMN + " STRING NOT NULL " +
					")"; 
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private static final String SELECT_QUERY="SELECT " +
			
			
			""+ PAYSTableDefinition.ID_PAYS_COLUMN +"," + 
			""+ PAYSTableDefinition.NOM_COLUMN +
		" FROM " +
			" PAYS";
	public static List<PAYSTableRecordPOJO> Select(String WhereArgsStr)
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
			List<PAYSTableRecordPOJO> result = new ArrayList<PAYSTableRecordPOJO>();
			while(resultSet.next())
			{
				PAYSTableRecordPOJO currentRecord = new PAYSTableRecordPOJO();
				
				
				currentRecord.setID_PAYS(resultSet.getint(PAYSTableDefinition.ID_PAYS_COLUMN));
				currentRecord.setNOM(resultSet.getString(PAYSTableDefinition.NOM_COLUMN));
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
					
					""+PAYSTableDefinition.ID_PAYS_COLUMN+"," +  
					""+PAYSTableDefinition.NOM_COLUMN+ 
					") VALUES (" + 
					
					
					 " ?,"  + 
					 " ?"  + 
					");";
	
	public static boolean Insert(PAYSTableRecordPOJO pojo)
	{
		if(pojo == null) return false;
		PreparedStatement stmt;
		try {
			stmt = _connection.prepareStatement(INSERT_STATEMENT);
			_connection.setAutoCommit(false);
			
			
		stmt.setint(0 + 1, pojo.getID_PAYS());
		stmt.setString(1 + 1, pojo.getNOM());
		    stmt.executeUpdate();
		    
		    
		   
		   
		    _connection.commit();
		    _connection.setAutoCommit(true);
		    return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean Insert(List<PAYSTableRecordPOJO> pojoList)
	{
		if(pojoList==null) return false;
		try {
			_connection.setAutoCommit(false);
			
			for(int currentIndex = 0 ; currentIndex < pojoList.size(); currentIndex++ )
			{
				PAYSTableRecordPOJO currentPojo = pojoList.get(currentIndex);
				PreparedStatement stmt = _connection.prepareStatement(INSERT_STATEMENT);
				
				
			stmt.setint(1 /*TODO*/, currentPojo.getID_PAYS());
			stmt.setString(1 /*TODO*/, currentPojo.getNOM());
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
	
	private final static String UPDATE_QUERY = "UPDATE "+PAYSTableDefinition.TABLE_NAME+" " +
			"SET " + 
			
			
		
		 ""+PAYSTableDefinition.NOM_COLUMN+"=" + 
			" WHERE "
			
			
			 + ""+PAYSTableDefinition.ID_PAYS_COLUMN+"=, {:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:LAST:COLUMN(;):::}";
			
	public static boolean Update(PAYSTableRecordPOJO pojo)
	{
		if(pojo == null) return false;
		try {
			PreparedStatement stmt = _connection.prepareStatement(UPDATE_QUERY);
			_connection.setAutoCommit(false);
			
			
		
		 stmt.setString(1 /*TODO*/, pojo.getNOM());
			
			
			
		stmt.setint(5 /*TODO*/, pojo.getID_PAYS());
		
			stmt.execute();
			_connection.setAutoCommit(true);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean Update(List<PAYSTableRecordPOJO> pojoList)
	{
		if(pojoList == null) return false;
		try {
			_connection.setAutoCommit(false);
			for(int currentIndex=0;currentIndex<pojoList.size();currentIndex++)
			{
				PAYSTableRecordPOJO currentPOJO = pojoList.get(currentIndex);
				PreparedStatement stmt = _connection.prepareStatement(UPDATE_QUERY);
				
       			
       		
       		 stmt.setString(1 /*TODO*/, currentPOJO.getNOM());
       			
       			
       			
       		stmt.setint(5 /*TODO*/, currentPOJO.getID_PAYS());)KEY:NOT:PRIMARY:::}
				
				stmt.execute();
			}
			_connection.setAutoCommit(true);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	private final static String DELETE_QUERY="DELETE FROM "+PAYSTableDefinition.TABLE_NAME+" WHERE " +
			
   			
       				{:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:KEY:PRIMARY( + ""+PAYSTableDefinition.ID_PAYS_COLUMN+"=, {:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:LAST:COLUMN(;):::}";
       			
	public static boolean Delete(PAYSTableRecordPOJO pojo)
	{
		if(pojo == null) return false;
		try {
			PreparedStatement stmt = _connection.prepareStatement(DELETE_QUERY);
			_connection.setAutoCommit(false);
			
   			
   		stmt.setint(5 /*TODO*/, pojo.getID_PAYS());
   		
			stmt.execute();
			_connection.setAutoCommit(true);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public static boolean Delete(List<PAYSTableRecordPOJO> pojoList)
	{
		if(pojoList == null) return false;
		try {
			_connection.setAutoCommit(false);
			for(int currentIndex=0;currentIndex<pojoList.size();currentIndex++)
			{
				CompanyTableRecordPOJO currentPOJO = pojoList.get(currentIndex);
				PreparedStatement stmt = _connection.prepareStatement(DELETE_QUERY);
				
				
      			
      		stmt.setint(1 /*TODO*/, currentPOJO.getID_PAYS());
      		
				
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
			String sql = "DROP TABLE IF EXISTS "+PAYSTableDefinition.TABLE_NAME+" " ; 
			stmt.executeUpdate(sql);
			stmt.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
