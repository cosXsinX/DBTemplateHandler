# DBTemplateHandler

A Database oriented DSL pre-compiler

This utility is devoted to create your own DAL generators devoted to tabular data representation database (or normalized) ( such as SQLite, SQLServer, MySql ...). 

The template handler implement a tabular and minimal tabular database domain specific language (functional way).

## Domain specific language glossary :

### Database specific words :

* {:TDB:CURRENT:NAME::} : Is replaced by the current name of the database
* {:TDB:TABLE:FOREACH[ ... ]::] : Is the iterator devoted to the table enumeration => this allows the replacement of the contextualized specified underneath in the "Table specific words" section

### Table specific words :

* {:TDB:TABLE:CURRENT:NAME::} : Is replaced by the current table name
* {:TDB:TABLE:COLUMN:FOREACH[ ... ]::} : Is the iterator devoted to the iteration of the current table columns
* {:TDB:TABLE:COLUMN:AUTO:FOREACH[ ... ]::} : Is the iterator devoted to the iteration of the current table columns auto generated value table columns
* {:TDB:TABLE:COLUMN:NOT:AUTO:FOREACH[ ... ]::} : Is the iterator devoted to the iteration of the current table columns which are not auto generated value table columns
* {:TDB:TABLE:COLUMN:NOT:NULL:FOREACH[ ... ]::} : Is the iterator devoted to the iteration of the current table columns which are not nullable value table columns
* {:TDB:TABLE:COLUMN:PRIMARY:FOREACH[ ... ]::} : Is the iterator devoted to the iteration of the current table columns which are primary key value columns
* {:TDB:TABLE:COLUMN:NOT:PRIMARY:FOREACH[ ... ]::} : Is the iterator devoted to the iteration of the current table columns which are not primary key value columns

### Column specific words :

* {:TDB:TABLE:COLUMN:AUTO:FOREACH:CURRENT:INDEX::} : Is replaced by the index of the current auto generated value column index iterate column in the table definition
* {:TDB:TABLE:COLUMN:AUTO:FOREACH:CURRENT:IS:FIRST:COLUMN( ... )::} : Is replaced by the content between parentheses when the column correspond to the first auto generated value content
* {:TDB:TABLE:COLUMN:AUTO:FOREACH:CURRENT:IS:NOT:FIRST:COLUMN( ... )::} : Is replaced by the content between parentheses when the column do not correspond to the first auto generated value content
* {:TDB:TABLE:COLUMN:AUTO:FOREACH:CURRENT:IS:LAST:COLUMN( ... )::} : Is replaced by the content between parentheses when the column correspond to the last auto generated value content
* {:TDB:TABLE:COLUMN:AUTO:FOREACH:CURRENT:IS:NOT:LAST:COLUMN( ... )::} : Is replaced by the content between parentheses when the column correspond to the last auto generated value content
* {:TDB:TABLE:COLUMN:FOREACH:CURRENT:CONVERT:TYPE( ... )::} : Is replaced by targeted conversion type. Nowadays only one available JAVA => {:TDB:TABLE:COLUMN:FOREACH:CURRENT:CONVERT:TYPE(JAVA)::} only applicable pattern
* {:TDB:TABLE:COLUMN:FOREACH:CURRENT:INDEX::} : Is replaced by the current column index
* {:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:FIRST:COLUMN( ... )::} :  Is replaced by the content between parentheses when the column correspond to the first column of the enumerated columns
* {:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:KEY:AUTO( ... )KEY:AUTO:::} : Is replaced by the content between parentheses when the column is auto generated value content
* {:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:KEY:NOT:AUTO( ... )KEY:NOT:AUTO:::}  Is replaced by the content between parentheses when the column is not auto generated value content
* {:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:KEY:NOT:PRIMARY( ... )KEY:NOT:PRIMARY:::} : Is replaced by the content between parentheses when the column is not primary key value content
* {:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:KEY:PRIMARY( ... )KEY:PRIMARY:::} : Is replaced by the content between parentheses when the column is not primary key value content
* {:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:LAST:COLUMN( ... ):::} : Is replaced by the content between parentheses when the column correspond to the last column
* {:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:NOT:FIRST:COLUMN( ... ):::} : Is replaced by the content between parentheses when the column correspond is not the first column of the enumerated columns
* {:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:NOT:LAST:COLUMN( ... ):::} : Is replaced by the content between parentheses when the column correspond is not the last column of the enumerated columns
* {:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:NOT:NULL( ... ):::} : Is replaced by the content between parentheses when the column values can be nullable
* {:TDB:TABLE:COLUMN:FOREACH:CURRENT:NAME::} : Is replaced by the column name
* {:TDB:TABLE:COLUMN:FOREACH:CURRENT:TYPE::} : Is replaced by the column type
* {:TDB:TABLE:COLUMN:NOT:AUTO:FOREACH:CURRENT:INDEX::} : Is replaced by the column index in the context of not auto generated column values
* {:TDB:TABLE:COLUMN:NOT:AUTO:FOREACH:CURRENT:IS:FIRST:COLUMN( ... ):::} : Is replaced by the content between parentheses when the column correspond to the first column of auto generated column collection
* {:TDB:TABLE:COLUMN:NOT:AUTO:FOREACH:CURRENT:IS:LAST:COLUMN( ... ):::} : Is replaced by the content between parentheses when the column correspond to the last column of auto generated column collection
* {:TDB:TABLE:COLUMN:NOT:AUTO:FOREACH:CURRENT:IS:NOT:FIRST:COLUMN( ... ):::} : Is replaced by the content between parentheses when the column correspond is not the first column of auto column collection
* {:TDB:TABLE:COLUMN:NOT:AUTO:FOREACH:CURRENT:IS:NOT:LAST:COLUMN( ... ):::} : Is replaced by the content between parentheses when the column correspond is not the last column of auto column collection
* {:TDB:TABLE:COLUMN:NOT:NULL:CURRENT:IS:FIRST:COLUMN( ... ):::} : Is replaced by the content between parentheses when the column correspond is the first column of not nullable value column collection
* {:TDB:TABLE:COLUMN:NOT:NULL:FOREACH:CURRENT:IS:LAST:COLUMN( ... ):::} : Is replaced by the content between parentheses when the column correspond is the last column of not nullable value column collection
* {:TDB:TABLE:COLUMN:NOT:PRIMARY:FOREACH:CURRENT:INDEX::} : Is replaced by the column index in the context of not primary column enumeration
* {:TDB:TABLE:COLUMN:NOT:PRIMARY:FOREACH:CURRENT:IS:FIRST:COLUMN( ... ):::} : Is replaced by the content between parentheses when the column correspond to the first column of primary generated column collection
* {:TDB:TABLE:COLUMN:NOT:PRIMARY:FOREACH:CURRENT:IS:LAST:COLUMN( ... ):::} : Is replaced by the content between parentheses when the column correspond to the last column of primary generated column collection
* {:TDB:TABLE:COLUMN:NOT:PRIMARY:FOREACH:CURRENT:IS:NOT:FIRST:COLUMN( ... ):::} : Is replaced by the content between parentheses when the column do not correspond to the first column of primary generated column collection
* {:TDB:TABLE:COLUMN:NOT:PRIMARY:FOREACH:CURRENT:IS:NOT:LAST:COLUMN( ... ):::} : Is replaced by the content between parentheses when the column do not correspond to the last column of primary generated column collection
* {:TDB:TABLE:COLUMN:PRIMARY:FOREACH:CURRENT:INDEX::} : Is replaced by the column index in the context of primary column enumeration
* {:TDB:TABLE:COLUMN:PRIMARY:FOREACH:CURRENT:IS:FIRST:COLUMN( ... ):::} : Is replaced by the content between parentheses when the column correspond to the first column of primary enumerated column collection
* {:TDB:TABLE:COLUMN:PRIMARY:FOREACH:CURRENT:IS:LAST:COLUMN( ... ):::} : Is replaced by the content between parentheses when the column correspond to the last column of primary enumerated column collection
* {:TDB:TABLE:COLUMN:PRIMARY:FOREACH:CURRENT:IS:NOT:FIRST:COLUMN( ... ):::} : Is replaced by the content between parentheses when the column do not correspond to the first column of primary enumerated column collection
* {:TDB:TABLE:COLUMN:PRIMARY:FOREACH:CURRENT:IS:NOT:LAST:COLUMN( ... ):::} : Is replaced by the content between parentheses when the column do not correspond to the last column of primary enumerated column collection
* {:TDB:TABLE:COLUMN:PRIMARY:NOT:NULL:CURRENT:INDEX::} : Is replaced by the column index in the context of not nullable value column enumeration
 
### Function specific words :

* {:TDB:FUNCTION:FIRST:CHARACTER:TO:UPPER:CASE( ... )::} : Replace the first character of the content by it's upper case


## Examples

Table description class template implementation

``` java
package com.sqlite.dal.test.project.dao;

public class {:TDB:TABLE:CURRENT:NAME::}TableDescription {

	public final static String TABLE_NAME = "{:TDB:TABLE:CURRENT:NAME::}";
	{:TDB:TABLE:COLUMN:FOREACH[
	public final static String {:TDB:TABLE:COLUMN:FOREACH:CURRENT:NAME::}_COLUMN = "{:TDB:TABLE:COLUMN:FOREACH:CURRENT:NAME::}";
	]::}
	
}
```

SQL Lite CRUD Dao writting :

Read DAO implementation for sql lite

``` java
package com.sqlite.dal.test.project.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class {:TDB:TABLE:CURRENT:NAME::}JavaSQLiteTableSelectDAO {
	private Connection _connection;
	public {:TDB:TABLE:CURRENT:NAME::}JavaSQLiteTableSelectDAO(Connection SQLiteDatabaseConnection)
	{
		_connection = SQLiteDatabaseConnection;
	}
	
	private static final String SELECT_QUERY="SELECT " +
			{:TDB:TABLE:COLUMN:FOREACH[
			""+ {:TDB:TABLE:CURRENT:NAME::}TableDescription.{:TDB:TABLE:COLUMN:FOREACH:CURRENT:NAME::}_COLUMN +"{:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:NOT:LAST:COLUMN(,):::} " +]::}
		" FROM " +
			" {:TDB:TABLE:CURRENT:NAME::}";
	public List<{:TDB:TABLE:CURRENT:NAME::}TableRecordPOJO> Select(String WhereArgsStr)
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
			List<{:TDB:TABLE:CURRENT:NAME::}TableRecordPOJO> result = new ArrayList<{:TDB:TABLE:CURRENT:NAME::}TableRecordPOJO>();
			while(resultSet.next())
			{
				{:TDB:TABLE:CURRENT:NAME::}TableRecordPOJO currentRecord = new {:TDB:TABLE:CURRENT:NAME::}TableRecordPOJO();
				{:TDB:TABLE:COLUMN:FOREACH[
				currentRecord.set{:TDB:TABLE:COLUMN:FOREACH:CURRENT:NAME::}{:TDB:FUNCTION:FIRST:CHARACTER:TO:UPPER:CASE({:TDB:TABLE:COLUMN:FOREACH:CURRENT:CONVERT:TYPE(JAVA)::})::}(resultSet.get{:TDB:FUNCTION:FIRST:CHARACTER:TO:UPPER:CASE({:TDB:TABLE:COLUMN:FOREACH:CURRENT:CONVERT:TYPE(JAVA)::})::}({:TDB:TABLE:CURRENT:NAME::}TableDescription.{:TDB:TABLE:COLUMN:FOREACH:CURRENT:NAME::}_COLUMN));]::}
				result.add(currentRecord);
			}
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
```

Insert dao

``` java
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

```
