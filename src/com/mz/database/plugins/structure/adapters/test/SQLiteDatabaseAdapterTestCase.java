package com.mz.database.plugins.structure.adapters.test;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;

import com.mz.database.models.DatabaseDescriptionPOJO;
import com.mz.database.models.TableDescriptionPOJO;
import com.mz.database.plugins.structure.adapters.MySQLDatabaseAdapter;

public class SQLiteDatabaseAdapterTestCase {

	final static String user = "root";
	final static String password = "maximax";
	final static String databaseName = "EMPLOYEES";
	final static String serverName = "localhost";
	
	@Test
	public void test2()
	{
		MySQLDatabaseAdapter adapter = new MySQLDatabaseAdapter
				(user, password, databaseName,serverName);
		try {
			DatabaseDescriptionPOJO databaseDescriptionPOJO = 
						adapter.GetDatabasePOJO();
			if(databaseDescriptionPOJO == null) return;
			ArrayList<TableDescriptionPOJO> tableList =
					databaseDescriptionPOJO.getTableList();
			for(TableDescriptionPOJO currentTable : tableList)
			{
				System.out.println(currentTable.get_NameStr());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
