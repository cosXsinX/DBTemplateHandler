package com.mz.database.plugins.structure.adapters.common;

import com.mz.database.models.DatabaseDescriptionPOJO;

public abstract class AbstractDatabaseStructureAdapter {

	public abstract String get_DatabaseManagementSystemName();
	
	public abstract void Setup(DatabaseStructureAdapterConfigParameters ConnectionParameters);
	
	public abstract DatabaseDescriptionPOJO GetDatabaseStructureSchemaDefinition();
}
