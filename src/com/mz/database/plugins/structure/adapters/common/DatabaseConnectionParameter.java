package com.mz.database.plugins.structure.adapters.common;

public class DatabaseConnectionParameter {

	private String _javaConnectionDriverClassName;
	public String get_JavaConnectionDriverClassName()
	{
		return _javaConnectionDriverClassName;
	}
	public void set_JavaConnectionDriverClassName(String value)
	{
		_javaConnectionDriverClassName =value;
	}
	
	private String _databaseConnectionString;
	public String get_DatabaseConnectionString()
	{
		return _databaseConnectionString;
	}
	public void set_DatabaseConnectionString(String value)
	{
		_databaseConnectionString = value;
	}

	private String _databaseConnectionUserNameString;
	public String get_DatabaseConnectionUserNameString() {
		return _databaseConnectionUserNameString;
	}
	public void set_DatabaseConnectionUserNameString(
			String _databaseConnectionUserNameString) {
		this._databaseConnectionUserNameString = _databaseConnectionUserNameString;
	}

	private String _databaseConnectionUserPasswordString;
	public String get_DatabaseConnectionUserPasswordString() {
		return _databaseConnectionUserPasswordString;
	}
	public void set_DatabaseConnectionUserPasswordString(
			String _databaseConnectionUserPasswordString) {
		this._databaseConnectionUserPasswordString = _databaseConnectionUserPasswordString;
	}
}
