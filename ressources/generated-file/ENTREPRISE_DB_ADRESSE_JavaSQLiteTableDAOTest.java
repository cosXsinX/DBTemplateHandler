
package com.sqlite.dal.test.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sqlite.dal.test.project.pojo.ADRESSETableRecordPOJO;
import com.sqlite.dal.test.project.tabledefinitions.ADRESSETableDefinition;


public class ADRESSEJavaSQLiteTableDAO
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
			String sql = "CREATE TABLE IF NOT EXISTS " + ADRESSETableDefinition.TABLE_NAME + " " +
	                "(" + 
						  
	                            " " + ADRESSETableDefinition.ID_ADRESSE_COLUMN + " INTEGER PRIMARY KEY NOT NULL " +"," + 
	                            " " + ADRESSETableDefinition.ADRESSE_COLUMN + " STRING NOT NULL " +"," + 
	                            " " + ADRESSETableDefinition.ID_VILLE_COLUMN + " INTEGER NOT NULL " +
					")"; 
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private static final String SELECT_QUERY="SELECT " +
			
			
			""+ ADRESSETableDefinition.ID_ADRESSE_COLUMN +"," + 
			""+ ADRESSETableDefinition.ADRESSE_COLUMN +"," + 
			""+ ADRESSETableDefinition.ID_VILLE_COLUMN +
		" FROM " +
			" ADRESSE";
	public static List<ADRESSETableRecordPOJO> Select(String WhereArgsStr)
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
			List<ADRESSETableRecordPOJO> result = new ArrayList<ADRESSETableRecordPOJO>();
			while(resultSet.next())
			{
				ADRESSETableRecordPOJO currentRecord = new ADRESSETableRecordPOJO();
				
				
				currentRecord.setID_ADRESSE(resultSet.getint(ADRESSETableDefinition.ID_ADRESSE_COLUMN));
				currentRecord.setADRESSE(resultSet.getString(ADRESSETableDefinition.ADRESSE_COLUMN));
				currentRecord.setID_VILLE(resultSet.getint(ADRESSETableDefinition.ID_VILLE_COLUMN));
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
					
					""+ADRESSETableDefinition.ID_ADRESSE_COLUMN+"," +  
					""+ADRESSETableDefinition.ADRESSE_COLUMN+"," +  
					""+ADRESSETableDefinition.ID_VILLE_COLUMN+ 
					") VALUES (" + 
					
					
					 " ?,"  + 
					 " ?,"  + 
					 " ?"  + 
					");";
	
	public static boolean Insert(ADRESSETableRecordPOJO pojo)
	{
		if(pojo == null) return false;
		PreparedStatement stmt;
		try {
			stmt = _connection.prepareStatement(INSERT_STATEMENT);
			_connection.setAutoCommit(false);
			
			
		stmt.setint(0 + 1, pojo.getID_ADRESSE());
		stmt.setString(1 + 1, pojo.getADRESSE());
		stmt.setint(2 + 1, pojo.getID_VILLE());
		    stmt.executeUpdate();
		    
		    
		   
		   
		   
		    _connection.commit();
		    _connection.setAutoCommit(true);
		    return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean Insert(List<ADRESSETableRecordPOJO> pojoList)
	{
		if(pojoList==null) return false;
		try {
			_connection.setAutoCommit(false);
			
			for(int currentIndex = 0 ; currentIndex < pojoList.size(); currentIndex++ )
			{
				ADRESSETableRecordPOJO currentPojo = pojoList.get(currentIndex);
				PreparedStatement stmt = _connection.prepareStatement(INSERT_STATEMENT);
				
				
			stmt.setint(1 /*TODO*/, currentPojo.getID_ADRESSE());
			stmt.setString(1 /*TODO*/, currentPojo.getADRESSE());
			stmt.setint(1 /*TODO*/, currentPojo.getID_VILLE());
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
	
	private final static String UPDATE_QUERY = "UPDATE "+ADRESSETableDefinition.TABLE_NAME+" " +
			"SET " + 
			
			
		
		 ""+ADRESSETableDefinition.ADRESSE_COLUMN+"=," + 
		 ""+ADRESSETableDefinition.ID_VILLE_COLUMN+"=" + 
			" WHERE "
			
			
			 + ""+ADRESSETableDefinition.ID_ADRESSE_COLUMN+"=, {:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:LAST:COLUMN(;):::}";
			
			
	public static boolean Update(ADRESSETableRecordPOJO pojo)
	{
		if(pojo == null) return false;
		try {
			PreparedStatement stmt = _connection.prepareStatement(UPDATE_QUERY);
			_connection.setAutoCommit(false);
			
			
		
		 stmt.setString(1 /*TODO*/, pojo.getADRESSE());
		 stmt.setint(1 /*TODO*/, pojo.getID_VILLE());
			
			
			
		stmt.setint(5 /*TODO*/, pojo.getID_ADRESSE());
		
		
			stmt.execute();
			_connection.setAutoCommit(true);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean Update(List<ADRESSETableRecordPOJO> pojoList)
	{
		if(pojoList == null) return false;
		try {
			_connection.setAutoCommit(false);
			for(int currentIndex=0;currentIndex<pojoList.size();currentIndex++)
			{
				ADRESSETableRecordPOJO currentPOJO = pojoList.get(currentIndex);
				PreparedStatement stmt = _connection.prepareStatement(UPDATE_QUERY);
				
       			
       		
       		 stmt.setString(1 /*TODO*/, currentPOJO.getADRESSE());
       		 stmt.setint(1 /*TODO*/, currentPOJO.getID_VILLE());
       			
       			
       			
       		stmt.setint(5 /*TODO*/, currentPOJO.getID_ADRESSE());)KEY:NOT:PRIMARY:::}
				
				stmt.execute();
			}
			_connection.setAutoCommit(true);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	private final static String DELETE_QUERY="DELETE FROM "+ADRESSETableDefinition.TABLE_NAME+" WHERE " +
			
   			
       				{:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:KEY:PRIMARY( + ""+ADRESSETableDefinition.ID_ADRESSE_COLUMN+"=, {:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:LAST:COLUMN(;):::}";
       			
       			
	public static boolean Delete(ADRESSETableRecordPOJO pojo)
	{
		if(pojo == null) return false;
		try {
			PreparedStatement stmt = _connection.prepareStatement(DELETE_QUERY);
			_connection.setAutoCommit(false);
			
   			
   		stmt.setint(5 /*TODO*/, pojo.getID_ADRESSE());
   		
   		
			stmt.execute();
			_connection.setAutoCommit(true);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public static boolean Delete(List<ADRESSETableRecordPOJO> pojoList)
	{
		if(pojoList == null) return false;
		try {
			_connection.setAutoCommit(false);
			for(int currentIndex=0;currentIndex<pojoList.size();currentIndex++)
			{
				CompanyTableRecordPOJO currentPOJO = pojoList.get(currentIndex);
				PreparedStatement stmt = _connection.prepareStatement(DELETE_QUERY);
				
				
      			
      		stmt.setint(1 /*TODO*/, currentPOJO.getID_ADRESSE());
      		
      		
				
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
			String sql = "DROP TABLE IF EXISTS "+ADRESSETableDefinition.TABLE_NAME+" " ; 
			stmt.executeUpdate(sql);
			stmt.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
