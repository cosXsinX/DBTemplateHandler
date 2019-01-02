
package com.sqlite.dal.test.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sqlite.dal.test.project.pojo.VILLETableRecordPOJO;
import com.sqlite.dal.test.project.tabledefinitions.VILLETableDefinition;


public class VILLEJavaSQLiteTableDAO
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
			String sql = "CREATE TABLE IF NOT EXISTS " + VILLETableDefinition.TABLE_NAME + " " +
	                "(" + 
						  
	                            " " + VILLETableDefinition.ID_VILLE_COLUMN + " INTEGER PRIMARY KEY NOT NULL " +"," + 
	                            " " + VILLETableDefinition.NOM_COLUMN + " STRING NOT NULL " +"," + 
	                            " " + VILLETableDefinition.ID_PAYS_COLUMN + " INTEGER NOT NULL " +
					")"; 
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private static final String SELECT_QUERY="SELECT " +
			
			
			""+ VILLETableDefinition.ID_VILLE_COLUMN +"," + 
			""+ VILLETableDefinition.NOM_COLUMN +"," + 
			""+ VILLETableDefinition.ID_PAYS_COLUMN +
		" FROM " +
			" VILLE";
	public static List<VILLETableRecordPOJO> Select(String WhereArgsStr)
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
			List<VILLETableRecordPOJO> result = new ArrayList<VILLETableRecordPOJO>();
			while(resultSet.next())
			{
				VILLETableRecordPOJO currentRecord = new VILLETableRecordPOJO();
				
				
				currentRecord.setID_VILLE(resultSet.getint(VILLETableDefinition.ID_VILLE_COLUMN));
				currentRecord.setNOM(resultSet.getString(VILLETableDefinition.NOM_COLUMN));
				currentRecord.setID_PAYS(resultSet.getint(VILLETableDefinition.ID_PAYS_COLUMN));
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
					
					""+VILLETableDefinition.ID_VILLE_COLUMN+"," +  
					""+VILLETableDefinition.NOM_COLUMN+"," +  
					""+VILLETableDefinition.ID_PAYS_COLUMN+ 
					") VALUES (" + 
					
					
					 " ?,"  + 
					 " ?,"  + 
					 " ?"  + 
					");";
	
	public static boolean Insert(VILLETableRecordPOJO pojo)
	{
		if(pojo == null) return false;
		PreparedStatement stmt;
		try {
			stmt = _connection.prepareStatement(INSERT_STATEMENT);
			_connection.setAutoCommit(false);
			
			
		stmt.setint(0 + 1, pojo.getID_VILLE());
		stmt.setString(1 + 1, pojo.getNOM());
		stmt.setint(2 + 1, pojo.getID_PAYS());
		    stmt.executeUpdate();
		    
		    
		   
		   
		   
		    _connection.commit();
		    _connection.setAutoCommit(true);
		    return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean Insert(List<VILLETableRecordPOJO> pojoList)
	{
		if(pojoList==null) return false;
		try {
			_connection.setAutoCommit(false);
			
			for(int currentIndex = 0 ; currentIndex < pojoList.size(); currentIndex++ )
			{
				VILLETableRecordPOJO currentPojo = pojoList.get(currentIndex);
				PreparedStatement stmt = _connection.prepareStatement(INSERT_STATEMENT);
				
				
			stmt.setint(1 /*TODO*/, currentPojo.getID_VILLE());
			stmt.setString(1 /*TODO*/, currentPojo.getNOM());
			stmt.setint(1 /*TODO*/, currentPojo.getID_PAYS());
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
	
	private final static String UPDATE_QUERY = "UPDATE "+VILLETableDefinition.TABLE_NAME+" " +
			"SET " + 
			
			
		
		 ""+VILLETableDefinition.NOM_COLUMN+"=," + 
		 ""+VILLETableDefinition.ID_PAYS_COLUMN+"=" + 
			" WHERE "
			
			
			 + ""+VILLETableDefinition.ID_VILLE_COLUMN+"=, {:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:LAST:COLUMN(;):::}";
			
			
	public static boolean Update(VILLETableRecordPOJO pojo)
	{
		if(pojo == null) return false;
		try {
			PreparedStatement stmt = _connection.prepareStatement(UPDATE_QUERY);
			_connection.setAutoCommit(false);
			
			
		
		 stmt.setString(1 /*TODO*/, pojo.getNOM());
		 stmt.setint(1 /*TODO*/, pojo.getID_PAYS());
			
			
			
		stmt.setint(5 /*TODO*/, pojo.getID_VILLE());
		
		
			stmt.execute();
			_connection.setAutoCommit(true);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean Update(List<VILLETableRecordPOJO> pojoList)
	{
		if(pojoList == null) return false;
		try {
			_connection.setAutoCommit(false);
			for(int currentIndex=0;currentIndex<pojoList.size();currentIndex++)
			{
				VILLETableRecordPOJO currentPOJO = pojoList.get(currentIndex);
				PreparedStatement stmt = _connection.prepareStatement(UPDATE_QUERY);
				
       			
       		
       		 stmt.setString(1 /*TODO*/, currentPOJO.getNOM());
       		 stmt.setint(1 /*TODO*/, currentPOJO.getID_PAYS());
       			
       			
       			
       		stmt.setint(5 /*TODO*/, currentPOJO.getID_VILLE());)KEY:NOT:PRIMARY:::}
				
				stmt.execute();
			}
			_connection.setAutoCommit(true);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	private final static String DELETE_QUERY="DELETE FROM "+VILLETableDefinition.TABLE_NAME+" WHERE " +
			
   			
       				{:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:KEY:PRIMARY( + ""+VILLETableDefinition.ID_VILLE_COLUMN+"=, {:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:LAST:COLUMN(;):::}";
       			
       			
	public static boolean Delete(VILLETableRecordPOJO pojo)
	{
		if(pojo == null) return false;
		try {
			PreparedStatement stmt = _connection.prepareStatement(DELETE_QUERY);
			_connection.setAutoCommit(false);
			
   			
   		stmt.setint(5 /*TODO*/, pojo.getID_VILLE());
   		
   		
			stmt.execute();
			_connection.setAutoCommit(true);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public static boolean Delete(List<VILLETableRecordPOJO> pojoList)
	{
		if(pojoList == null) return false;
		try {
			_connection.setAutoCommit(false);
			for(int currentIndex=0;currentIndex<pojoList.size();currentIndex++)
			{
				CompanyTableRecordPOJO currentPOJO = pojoList.get(currentIndex);
				PreparedStatement stmt = _connection.prepareStatement(DELETE_QUERY);
				
				
      			
      		stmt.setint(1 /*TODO*/, currentPOJO.getID_VILLE());
      		
      		
				
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
			String sql = "DROP TABLE IF EXISTS "+VILLETableDefinition.TABLE_NAME+" " ; 
			stmt.executeUpdate(sql);
			stmt.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
