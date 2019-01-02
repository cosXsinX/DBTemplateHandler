
package com.sqlite.dal.test.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sqlite.dal.test.project.pojo.{:TDB:TABLE::}TableRecordPOJO;
import com.sqlite.dal.test.project.tabledefinitions.{:TDB:TABLE::}TableDefinition;


public class {:TDB:TABLE::}JavaSQLiteTableDAO
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
			String sql = "CREATE TABLE IF NOT EXISTS " + {:TDB:TABLE::}TableDefinition.TABLE_NAME + " " +
	                "(" + {:TDB:TABLE:COLUMN:FOREACH[
	                            " " + {:TDB:TABLE::}TableDefinition.{:TDB:TABLE:COLUMN:FOREACH:CURRENT::}_COLUMN + " {:TDB:TABLE:COLUMN:FOREACH:CURRENT:TYPE::} {:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:KEY:PRIMARY( PRIMARY KEY)KEY:PRIMARY:::} {:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:KEY:AUTO( AUTOINCREMENT)KEY:AUTO:::} {:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:NOT:NULL( NOT NULL):::} " + {:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:NOT:LAST:COLUMN("," + ):::}
						  ]::}
					")"; 
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private static final String SELECT_QUERY="SELECT " +
			{:TDB:TABLE:COLUMN:FOREACH[
			""+ {:TDB:TABLE::}TableDefinition.{:TDB:TABLE:COLUMN:FOREACH:CURRENT::}_COLUMN + {:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:NOT:LAST:COLUMN("," + ):::}
			]::}
		" FROM " +
			" {:TDB:TABLE::}";
	public static List<{:TDB:TABLE::}TableRecordPOJO> Select(String WhereArgsStr)
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
			List<{:TDB:TABLE::}TableRecordPOJO> result = new ArrayList<{:TDB:TABLE::}TableRecordPOJO>();
			while(resultSet.next())
			{
				{:TDB:TABLE::}TableRecordPOJO currentRecord = new {:TDB:TABLE::}TableRecordPOJO();
				{:TDB:TABLE:COLUMN:FOREACH[
				currentRecord.set{:TDB:TABLE:COLUMN:FOREACH:CURRENT::}(resultSet.get{:TDB:TABLE:COLUMN:FOREACH:CURRENT:TYPE:CONVERT(JAVA)::}({:TDB:TABLE::}TableDefinition.{:TDB:TABLE:COLUMN:FOREACH:CURRENT::}_COLUMN));
				]::}
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
					"(" + {:TDB:TABLE:COLUMN:FOREACH[
						{:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:KEY:NOT:AUTO(""+{:TDB:TABLE::}TableDefinition.{:TDB:TABLE:COLUMN:FOREACH:CURRENT::}_COLUMN+ {:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:NOT:LAST:COLUMN("," + ):::} )KEY:NOT:AUTO:::}
					]::}
					") VALUES (" + 
					{:TDB:TABLE:COLUMN:FOREACH[
					 {:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:KEY:NOT:AUTO( " ? {:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:NOT:LAST:COLUMN(,):::}" )KEY:NOT:AUTO:::} + 
					]::}
					");";
	
	public static boolean Insert({:TDB:TABLE::}TableRecordPOJO pojo)
	{
		if(pojo == null) return false;
		PreparedStatement stmt;
		try {
			stmt = _connection.prepareStatement(INSERT_STATEMENT);
			_connection.setAutoCommit(false);
			{:TDB:TABLE:COLUMN:FOREACH[
			{:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:KEY:NOT:AUTO(stmt.set{:TDB:TABLE:COLUMN:FOREACH:CURRENT:TYPE:CONVERT(JAVA)::}({:TDB:TABLE:COLUMN:FOREACH:CURRENT:INDEX::} + 1, pojo.get{:TDB:TABLE:COLUMN:FOREACH:CURRENT::}());)KEY:NOT:AUTO:::}
			]::}
		    stmt.executeUpdate();
		    {:TDB:TABLE:COLUMN:FOREACH[
		    {:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:KEY:AUTO(pojo.set{:TDB:TABLE:COLUMN:FOREACH:CURRENT::}(stmt.getGeneratedKeys().get{:TDB:TABLE:COLUMN:FOREACH:CURRENT:TYPE:CONVERT(JAVA)::}({:TDB:TABLE:COLUMN:FOREACH:CURRENT:INDEX::} + 1));)KEY:AUTO:::}
		    ]::}
		    _connection.commit();
		    _connection.setAutoCommit(true);
		    return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean Insert(List<{:TDB:TABLE::}TableRecordPOJO> pojoList)
	{
		if(pojoList==null) return false;
		try {
			_connection.setAutoCommit(false);
			
			for(int currentIndex = 0 ; currentIndex < pojoList.size(); currentIndex++ )
			{
				{:TDB:TABLE::}TableRecordPOJO currentPojo = pojoList.get(currentIndex);
				PreparedStatement stmt = _connection.prepareStatement(INSERT_STATEMENT);
				{:TDB:TABLE:COLUMN:FOREACH[
				{:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:KEY:NOT:AUTO(stmt.set{:TDB:TABLE:COLUMN:FOREACH:CURRENT:TYPE:CONVERT(JAVA)::}(1 /*TODO*/, currentPojo.get{:TDB:TABLE:COLUMN:FOREACH:CURRENT::}());)KEY:NOT:AUTO:::}
				]::}
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
	
	private final static String UPDATE_QUERY = "UPDATE "+{:TDB:TABLE::}TableDefinition.TABLE_NAME+" " +
			"SET " + 
			{:TDB:TABLE:COLUMN:FOREACH[
			{:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:KEY:NOT:PRIMARY( ""+{:TDB:TABLE::}TableDefinition.{:TDB:TABLE:COLUMN:FOREACH:CURRENT::}_COLUMN+"=?{:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:NOT:LAST:COLUMN(,):::}" + )KEY:NOT:PRIMARY:::}
			]::}
			" WHERE "
			{:TDB:TABLE:COLUMN:FOREACH[
				{:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:KEY:PRIMARY( + ""+{:TDB:TABLE::}TableDefinition.{:TDB:TABLE:COLUMN:FOREACH:CURRENT::}_COLUMN+"=?{:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:NOT:LAST:COLUMN(,):::} {:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:LAST:COLUMN(;):::}";)KEY:PRIMARY:::}
			]::}
	public static boolean Update({:TDB:TABLE::}TableRecordPOJO pojo)
	{
		if(pojo == null) return false;
		try {
			PreparedStatement stmt = _connection.prepareStatement(UPDATE_QUERY);
			_connection.setAutoCommit(false);
			{:TDB:TABLE:COLUMN:FOREACH[
			{:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:KEY:NOT:PRIMARY( stmt.set{:TDB:TABLE:COLUMN:FOREACH:CURRENT:TYPE:CONVERT(JAVA)::}(1 /*TODO*/, pojo.get{:TDB:TABLE:COLUMN:FOREACH:CURRENT::}());)KEY:NOT:PRIMARY:::}
			]::}
			
			{:TDB:TABLE:COLUMN:FOREACH[
			{:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:KEY:PRIMARY(stmt.set{:TDB:TABLE:COLUMN:FOREACH:CURRENT:TYPE:CONVERT(JAVA)::}(5 /*TODO*/, pojo.get{:TDB:TABLE:COLUMN:FOREACH:CURRENT::}());)KEY:PRIMARY:::}
			]::}
			stmt.execute();
			_connection.setAutoCommit(true);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean Update(List<{:TDB:TABLE::}TableRecordPOJO> pojoList)
	{
		if(pojoList == null) return false;
		try {
			_connection.setAutoCommit(false);
			for(int currentIndex=0;currentIndex<pojoList.size();currentIndex++)
			{
				{:TDB:TABLE::}TableRecordPOJO currentPOJO = pojoList.get(currentIndex);
				PreparedStatement stmt = _connection.prepareStatement(UPDATE_QUERY);
				{:TDB:TABLE:COLUMN:FOREACH[
       			{:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:KEY:NOT:PRIMARY( stmt.set{:TDB:TABLE:COLUMN:FOREACH:CURRENT:TYPE:CONVERT(JAVA)::}(1 /*TODO*/, currentPOJO.get{:TDB:TABLE:COLUMN:FOREACH:CURRENT::}());)KEY:NOT:PRIMARY:::}
       			]::}
       			
       			{:TDB:TABLE:COLUMN:FOREACH[
       			{:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:KEY:PRIMARY(stmt.set{:TDB:TABLE:COLUMN:FOREACH:CURRENT:TYPE:CONVERT(JAVA)::}(5 /*TODO*/, currentPOJO.get{:TDB:TABLE:COLUMN:FOREACH:CURRENT::}());)KEY:NOT:PRIMARY:::}
       			]::}
				
				stmt.execute();
			}
			_connection.setAutoCommit(true);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	private final static String DELETE_QUERY="DELETE FROM "+{:TDB:TABLE::}TableDefinition.TABLE_NAME+" WHERE " +
			{:TDB:TABLE:COLUMN:FOREACH[
       				{:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:KEY:PRIMARY( + ""+{:TDB:TABLE::}TableDefinition.{:TDB:TABLE:COLUMN:FOREACH:CURRENT::}_COLUMN+"=?{:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:NOT:LAST:COLUMN(,):::} {:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:LAST:COLUMN(;):::}";)KEY:PRIMARY:::}
   			]::}
	public static boolean Delete({:TDB:TABLE::}TableRecordPOJO pojo)
	{
		if(pojo == null) return false;
		try {
			PreparedStatement stmt = _connection.prepareStatement(DELETE_QUERY);
			_connection.setAutoCommit(false);
			{:TDB:TABLE:COLUMN:FOREACH[
   			{:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:KEY:PRIMARY(stmt.set{:TDB:TABLE:COLUMN:FOREACH:CURRENT:TYPE:CONVERT(JAVA)::}(5 /*TODO*/, pojo.get{:TDB:TABLE:COLUMN:FOREACH:CURRENT::}());)KEY:PRIMARY:::}
   			]::}
			stmt.execute();
			_connection.setAutoCommit(true);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public static boolean Delete(List<{:TDB:TABLE::}TableRecordPOJO> pojoList)
	{
		if(pojoList == null) return false;
		try {
			_connection.setAutoCommit(false);
			for(int currentIndex=0;currentIndex<pojoList.size();currentIndex++)
			{
				CompanyTableRecordPOJO currentPOJO = pojoList.get(currentIndex);
				PreparedStatement stmt = _connection.prepareStatement(DELETE_QUERY);
				
				{:TDB:TABLE:COLUMN:FOREACH[
      			{:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:KEY:PRIMARY(stmt.set{:TDB:TABLE:COLUMN:FOREACH:CURRENT:TYPE:CONVERT(JAVA)::}(1 /*TODO*/, currentPOJO.get{:TDB:TABLE:COLUMN:FOREACH:CURRENT::}());)KEY:PRIMARY:::}
      			]::}
				
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
			String sql = "DROP TABLE IF EXISTS "+{:TDB:TABLE::}TableDefinition.TABLE_NAME+" " ; 
			stmt.executeUpdate(sql);
			stmt.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}