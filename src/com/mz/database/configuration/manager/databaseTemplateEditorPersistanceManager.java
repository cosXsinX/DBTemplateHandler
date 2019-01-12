package com.mz.database.configuration.manager;

import java.util.List;

import com.mz.database.models.DatabaseDescriptionPOJO;
import com.mz.database.persistance.managers.IPersistanceManager;
import com.mz.database.persistance.managers.SQLitePersistanceManager;

public class databaseTemplateEditorPersistanceManager {
	
	private final static String PERSISTED_FILE_EXTENSION = ".ser";
	
	private static IPersistanceManager _persistanceManager; 
	private static IPersistanceManager get_persistanceManager()
	{
		if(_persistanceManager == null)
		{
			_persistanceManager = new SQLitePersistanceManager();
		}
		return _persistanceManager;
	}
	
	
	public static boolean doPersistDatabaseModel
		(DatabaseDescriptionPOJO persistedModel,String AssociatedKeyStr) 
	{
		return get_persistanceManager().
				doPersistDatabaseModel(persistedModel, AssociatedKeyStr);
	}

	public static DatabaseDescriptionPOJO getPersistedDatabaseModel(String ModelKeyStr){
		return get_persistanceManager().getPersistedDatabaseModel(ModelKeyStr);
	}
	
	public static List<String> ListEveryAvailableKey(){
		return get_persistanceManager().ListEveryAvailableKey();
	}
	
	public static boolean IsAlreadyPresentKeyInAvailableKey(String CheckedKeyStr)
	{
		return get_persistanceManager().IsAlreadyPresentKeyInAvailableKey(CheckedKeyStr);
	}
	
	public static boolean DeletePersistedDatabaseModel(String RemovedModelKeyStr)
	{
		return get_persistanceManager().DeletePersistedDatabaseModel(RemovedModelKeyStr);
	}
	
}
