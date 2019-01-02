package com.sqlite.dal.test.project.dao;

public class {:TDB:TABLE:CURRENT:NAME::}TableDescription {

	public final static String TABLE_NAME = "{:TDB:TABLE:CURRENT:NAME::}";
	{:TDB:TABLE:COLUMN:FOREACH[
	public final static String {:TDB:TABLE:COLUMN:FOREACH:CURRENT:NAME::}_COLUMN = "{:TDB:TABLE:COLUMN:FOREACH:CURRENT:NAME::}";
	]::}
	
}
