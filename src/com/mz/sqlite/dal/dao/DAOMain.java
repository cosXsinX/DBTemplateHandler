package com.mz.sqlite.dal.dao;

import java.sql.Connection;

public class DAOMain {

	public DAOMain(Connection connection) {
		this.databaseDescriptionDao = new DatabaseDescriptionJavaSQLiteTableDAO(connection);
		this.tableColumnDescriptionDao = new TableColumnDescriptionJavaSQLiteTableDAO(connection);
		this.tableDescriptionDao = new TableDescriptionJavaSQLiteTableDAO(connection);
	}

	private DatabaseDescriptionJavaSQLiteTableDAO databaseDescriptionDao;
	public DatabaseDescriptionJavaSQLiteTableDAO getDatabaseDescriptionDao() {
		return databaseDescriptionDao;
	}


	private TableDescriptionJavaSQLiteTableDAO tableDescriptionDao;
	public TableDescriptionJavaSQLiteTableDAO getTableDescriptionDao() {
		return tableDescriptionDao;
	}

	private TableColumnDescriptionJavaSQLiteTableDAO tableColumnDescriptionDao;
	public TableColumnDescriptionJavaSQLiteTableDAO getTableColumnDescriptionDao() {
		return tableColumnDescriptionDao;
	}
}
