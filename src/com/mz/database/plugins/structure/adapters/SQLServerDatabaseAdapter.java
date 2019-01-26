package com.mz.database.plugins.structure.adapters;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mz.database.models.DatabaseDescriptionPOJO;
import com.mz.database.models.TableColumnDescriptionPOJO;
import com.mz.database.models.TableDescriptionPOJO;

public class SQLServerDatabaseAdapter {

	  private Connection connect = null;
	  private Statement statement = null;
	  private PreparedStatement preparedStatement = null;
	  private ResultSet resultSet = null;

	  private String _user;
	  public String getUser()
	  {
		  return _user;
	  }
	  
	  private String _password;
	  public String getPassword()
	  {
		  return _password;
	  }
	  
	  private String _databaseName;
	  public String getDatabaseName()
	  {
		  return _databaseName;
	  }
	  
	  private String _serverName;
	  public String getServerName()
	  {
		  return _serverName;
	  }
	  
	  public SQLServerDatabaseAdapter(String user, String password,String databaseName,String serverName)
	  {
		  if(user == null) throw new NullPointerException("user");
		  if(password == null) throw new NullPointerException("password");
		  if(databaseName == null) throw new NullPointerException("databaseName");
		  if(serverName == null) throw new NullPointerException("serverName");
		  _user = user;
		  _password = password;
		  _databaseName = databaseName;
		  _serverName = serverName;
	  }
	  
	  private String sqlServerConnectionString = "jdbc:sqlserver://%1s/%2s;user=%3s;password=%4s";
	  
	  private String getConnectionKeyString()
	  {
		  String connectionString = String.format(sqlServerConnectionString,_serverName,_databaseName,_user,_password);
		  return connectionString;
	  }
	  
	  
	  private boolean IsConnectionAvailable() throws SQLException
	  {
		  if(connect == null) return false;
		  if(connect.isClosed()) return false;
		  return true;
	  }
	  private Connection GetOpenConnection() throws ClassNotFoundException, SQLException
	  {
		  if(IsConnectionAvailable()) return connect;
		// This will load the MySQL driver, each DB has its own driver
	      Class.forName("com.mysql.jdbc.Driver");
	      Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	      // Setup the connection with the DB
	      connect = DriverManager
	          .getConnection(getConnectionKeyString());
	      return connect;
	  }
	  
	  final static String UNKNOWN_VALUE_STRING = "";
	  
	  public DatabaseDescriptionPOJO GetDatabasePOJO() throws SQLException, ClassNotFoundException
	  {
		  if(connect == null) GetOpenConnection();
		  String databaseProductName = connect.getMetaData().getDatabaseProductName();
		  String databaseProductVersion = connect.getMetaData().getDatabaseProductVersion();
		  DatabaseDescriptionPOJO result = new DatabaseDescriptionPOJO();
		  result.setDatabaseNameStr(_databaseName);
		  String catalog = null;
		  String schemaPattern = null;
		  String tableNamePattern = null;
		  String types[] = null;
		  ResultSet resultSet = connect.getMetaData().
		  	getTables(catalog, 
		  			schemaPattern, tableNamePattern, types);
		  ArrayList<TableDescriptionPOJO> tableList= new ArrayList<TableDescriptionPOJO>();
		  while(resultSet.next())
		  {
			  String TABLE_CAT = resultSet.getString("TABLE_CAT");
			  String TABLE_SCHEM = resultSet.getString("TABLE_SCHEM");
			  String TABLE_NAME = resultSet.getString("TABLE_NAME");
			  String TABLE_TYPE = resultSet.getString("TABLE_TYPE");
			  String REMARKS = resultSet.getString("REMARKS");
			  //String TYPE_CAT = resultSet.getString("TYPE_CAT");
			  //String TYPE_SCHEM = resultSet.getString("TYPE_SCHEM");
			  //String TYPE_NAME = resultSet.getString("TYPE_NAME");
			  //String SELF_REFERENCING_COL_NAME = resultSet.getString("SELF_REFERENCING_COL_NAME");
			  //String REF_GENERATION = resultSet.getString("REF_GENERATION");
			  TableDescriptionPOJO currentTable = new TableDescriptionPOJO(TABLE_NAME);
			  tableList.add(currentTable);
		  }
		  resultSet.close();
		  
		  //Columns retrieval
		  String ColumnNamePattern = null;
		  for(TableDescriptionPOJO currentPojo : tableList)
		  {
			  ResultSet columnResultSet = connect.getMetaData().getColumns(catalog, schemaPattern,
				  currentPojo.get_NameStr(), ColumnNamePattern);
			  while(columnResultSet.next())
			  {
				  String TABLE_CAT = UNKNOWN_VALUE_STRING;
				  String TABLE_SCHEM = UNKNOWN_VALUE_STRING;
				  String TABLE_NAME = UNKNOWN_VALUE_STRING;
				  String COLUMN_NAME = UNKNOWN_VALUE_STRING;
				  int DATA_TYPE = 0;
				  String  TYPE_NAME = UNKNOWN_VALUE_STRING;
				  int COLUMN_SIZE = 0;
				  int DECIMAL_DIGITS = 0; 
				  int NUM_PREC_RADIX = 0;
				  int NULLABLE = 0; 
				  String  REMARKS = UNKNOWN_VALUE_STRING; 
				  String COLUMN_DEF = UNKNOWN_VALUE_STRING;
				  int SQL_DATA_TYPE = 0; 
				  int SQL_DATETIME_SUB = 0; 
				  int CHAR_OCTET_LENGTH = 0;
				  int ORDINAL_POSITION = 0;
				  String IS_NULLABLE = UNKNOWN_VALUE_STRING;
				  String SCOPE_CATALOG = UNKNOWN_VALUE_STRING;
				  String SCOPE_SCHEMA = UNKNOWN_VALUE_STRING;
				  String SCOPE_TABLE = UNKNOWN_VALUE_STRING;
				  short SOURCE_DATA_TYPE = 0;
				  String IS_AUTOINCREMENT ="";
				  String IS_GENERATEDCOLUMN = "";
				  
				  TABLE_CAT = columnResultSet.getString("TABLE_CAT");
				  TABLE_SCHEM = columnResultSet.getString("TABLE_SCHEM");
				  TABLE_NAME = columnResultSet.getString("TABLE_NAME");
				  COLUMN_NAME = columnResultSet.getString("COLUMN_NAME");
				  DATA_TYPE = columnResultSet.getInt("DATA_TYPE"); //int => SQL type from java.sql.Types
				  TYPE_NAME = columnResultSet.getString("TYPE_NAME"); //Data source dependent type name, for a UDT the type name is fully qualified
				  COLUMN_SIZE = columnResultSet.getInt("COLUMN_SIZE");
				  DECIMAL_DIGITS = columnResultSet.getInt("DECIMAL_DIGITS"); //the number of fractional digits. Null is returned for data types where DECIMAL_DIGITS is not applicable.
				  NUM_PREC_RADIX = columnResultSet.getInt("NUM_PREC_RADIX"); //Radix (typically either 10 or 2)
				  NULLABLE = columnResultSet.getInt("NUM_PREC_RADIX"); //is NULL allowed. - columnNoNulls - might not allow NULL values - columnNullable - definitely allows NULL values - columnNullableUnknown - nullability unknown
				  REMARKS = columnResultSet.getString("REMARKS"); //comment describing column (may be null)
				  COLUMN_DEF = columnResultSet.getString("COLUMN_DEF");//default value for the column, which should be interpreted as a string when the value is enclosed in single quotes (may be null)
				  SQL_DATA_TYPE = columnResultSet.getInt("SQL_DATA_TYPE"); // unused
				  SQL_DATETIME_SUB = columnResultSet.getInt("SQL_DATETIME_SUB"); // unused
				  CHAR_OCTET_LENGTH = columnResultSet.getInt("CHAR_OCTET_LENGTH"); //=> for char types the maximum number of bytes in the column
				  ORDINAL_POSITION = columnResultSet.getInt("ORDINAL_POSITION"); //index of column in table (starting at 1)
				  IS_NULLABLE = columnResultSet.getString("IS_NULLABLE"); //ISO rules are used to determine the nullability for a column.
				  //YES --- if the column can include NULLs
				  //NO --- if the column cannot include NULLs
				  //empty string --- if the nullability for the column is unknown
				  SCOPE_CATALOG = columnResultSet.getString("SCOPE_CATALOG"); //catalog of table that is the scope of a reference attribute (null if DATA_TYPE isn't REF)
				  SCOPE_SCHEMA = columnResultSet.getString("SCOPE_SCHEMA"); //schema of table that is the scope of a reference attribute (null if the DATA_TYPE isn't REF)
				  SCOPE_TABLE = columnResultSet.getString("SCOPE_TABLE"); //table name that this the scope of a reference attribute (null if the DATA_TYPE isn't REF)
				  SOURCE_DATA_TYPE = columnResultSet.getShort("SOURCE_DATA_TYPE"); //source type of a distinct type or user-generated Ref type, SQL type from java.sql.Types (null if DATA_TYPE isn't DISTINCT or user-generated REF)
				  IS_AUTOINCREMENT = columnResultSet.getString("IS_AUTOINCREMENT"); //Indicates whether this column is auto incremented
				  //YES --- if the column is auto incremented
				  //NO --- if the column is not auto incremented
				  //empty string --- if it cannot be determined whether the column is auto incremented
				  //IS_GENERATEDCOLUMN = columnResultSet.getString("IS_GENERATED_COLUMN"); //Indicates whether this is a generated column
				  //YES --- if this a generated column
				  //NO --- if this not a generated column
				  //empty string --- if it cannot be determined whether this is a generated column
				  TableColumnDescriptionPOJO currentColumn = 
						  new TableColumnDescriptionPOJO(COLUMN_NAME,
								  TYPE_NAME, (IS_AUTOINCREMENT.equals("YES")?true:false));
				  currentColumn.set_AutoGeneratedValueBln((IS_AUTOINCREMENT.equals("YES")?true:false));
				  currentColumn.set_NotNull((IS_NULLABLE.equals("YES")?true:false));
				  currentPojo.AddColumn(currentColumn);
			  }
			  
		  }
		  result.setTableList(tableList);
		  return result;
	  }


	  // You need to close the resultSet
	  private void close() {
	    try {
	      if (resultSet != null) {
	        resultSet.close();
	      }

	      if (statement != null) {
	        statement.close();
	      }

	      if (connect != null) {
	        connect.close();
	      }
	    } catch (Exception e) {

	    }
	  }
}
