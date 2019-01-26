package com.mz.database.plugins.structure.adapters.test;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;

import com.mz.database.models.DatabaseDescriptionPOJO;
import com.mz.database.models.TableDescriptionPOJO;
import com.mz.database.plugins.structure.adapters.SQLServerDatabaseAdapter;

public class SQLServerDatabaseAdapterIntegrationTest {

	final static String user = "Maximilien";
	final static String password = "maximax";
	final static String databaseName = "AdventureWorks2017";
	final static String serverName = "DESKTOP-JNFJSV9\\SQLEXPRESS01";
	
	@Test
	public void test2()
	{
		SQLServerDatabaseAdapter adapter = new SQLServerDatabaseAdapter
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
