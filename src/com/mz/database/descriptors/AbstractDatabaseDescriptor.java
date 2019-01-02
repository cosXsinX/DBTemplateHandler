package com.mz.database.descriptors;

public abstract class AbstractDatabaseDescriptor {

	public abstract String[] get_possibleColumnTypes();
	
	public abstract String ConvertType(String ConvertedType,String DestinationEnvironment);
	
	public abstract String ConvertTypeToJava(String ConvertedType);
}
