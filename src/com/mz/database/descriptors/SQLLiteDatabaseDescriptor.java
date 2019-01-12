package com.mz.database.descriptors;

import org.apache.log4j.Logger;

public class SQLLiteDatabaseDescriptor extends AbstractDatabaseDescriptor {
	
	private final static Logger LOGGER = Logger.getLogger(SQLLiteDatabaseDescriptor.class);
	
	private final static String BIGINT_COLUMN_TYPE = "BIGINT";
	private final static String BLOB_COLUMN_TYPE = "BLOB";
	private final static String BOOLEAN_COLUMN_TYPE = "BOOLEAN";
	private final static String CHAR_COLUMN_TYPE = "CHAR";
	private final static String DATE_COLUMN_TYPE = "DATE";
	private final static String DATETIME_COLUMN_TYPE = "DATETIME";
	private final static String DECIMAL_COLUMN_TYPE = "DECIMAL";
	private final static String DOUBLE_COLUMN_TYPE = "DOUBLE";
	private final static String INTEGER_COLUMN_TYPE = "INTEGER";
	private final static String INT_COLUMN_TYPE = "INT";
	private final static String NUMERIC_COLUMN_TYPE = "NUMERIC";
	private final static String REAL_COLUMN_TYPE = "REAL";
	private final static String STRING_COLUMN_TYPE = "STRING";
	private final static String TEXT_COLUMN_TYPE = "TEXT";
	private final static String TIME_COLUMN_TYPE = "TIME";
	private final static String VARCHAR_COLUMN_TYPE = "VARCHAR";
	
	private final static String[] _possibleColumnTypes = 
		{
			BIGINT_COLUMN_TYPE,
			BLOB_COLUMN_TYPE,
			BOOLEAN_COLUMN_TYPE,
			CHAR_COLUMN_TYPE,
			DATE_COLUMN_TYPE,
			DATETIME_COLUMN_TYPE,
			DECIMAL_COLUMN_TYPE,
			DOUBLE_COLUMN_TYPE,
			INTEGER_COLUMN_TYPE,
			INT_COLUMN_TYPE,
			NUMERIC_COLUMN_TYPE,
			REAL_COLUMN_TYPE,
			STRING_COLUMN_TYPE,
			TEXT_COLUMN_TYPE,
			TIME_COLUMN_TYPE,
			VARCHAR_COLUMN_TYPE};
	
	public String[] get_possibleColumnTypes(){
		return _possibleColumnTypes;
	}
	
	public String ConvertType(String ConvertedType,String DestinationEnvironment)
	{
		if(ConvertedType == null) return null;
		if(DestinationEnvironment == null) return null;
		
		DestinationEnvironment = DestinationEnvironment.toUpperCase();
		String result = ConvertedType;
		if(DestinationEnvironment.equals("JAVA")){
			return ConvertTypeToJava(ConvertedType);
		}else LOGGER.error("No conversion function for the destination environment : " + DestinationEnvironment);
		return result;
	}
	
	public String ConvertTypeToJava(String ConvertedType){
		if(ConvertedType == null) return null;
		ConvertedType = ConvertedType.toUpperCase();
		if(ConvertedType.equals( BIGINT_COLUMN_TYPE)) return "long";
		else if(ConvertedType.equals( BLOB_COLUMN_TYPE)) return ConvertedType; //TODO Define associated type
		else if(ConvertedType.equals( BOOLEAN_COLUMN_TYPE)) return "boolean";
		else if(ConvertedType.equals( CHAR_COLUMN_TYPE)) return "char";
		else if(ConvertedType.equals( DATE_COLUMN_TYPE)) return "Date";
		else if(ConvertedType.equals( DECIMAL_COLUMN_TYPE)) return "double";
		else if(ConvertedType.equals( DOUBLE_COLUMN_TYPE)) return "double";
		else if(ConvertedType.equals( INTEGER_COLUMN_TYPE)) return "int";
		else if(ConvertedType.equals( INT_COLUMN_TYPE)) return "int";
		else if(ConvertedType.equals( NUMERIC_COLUMN_TYPE)) return "double";
		else if(ConvertedType.equals( REAL_COLUMN_TYPE)) return "double";
		else if(ConvertedType.equals( STRING_COLUMN_TYPE)) return "String";
		else if(ConvertedType.equals( TEXT_COLUMN_TYPE)) return "String";
		else if(ConvertedType.equals( TIME_COLUMN_TYPE)) return "Date";
		else if(ConvertedType.equals(VARCHAR_COLUMN_TYPE)) return "String";
		else return ConvertedType;
	}
}
