package com.mz.database.persistance.managers;

import java.util.List;

import com.mz.database.models.DatabaseDescriptionPOJO;

public interface IPersistanceManager {

	boolean doPersistDatabaseModel(DatabaseDescriptionPOJO persistedModel, String AssociatedKeyStr);

	DatabaseDescriptionPOJO getPersistedDatabaseModel(String ModelKeyStr);

	List<String> ListEveryAvailableKey();

	boolean IsAlreadyPresentKeyInAvailableKey(String CheckedKeyStr);

	boolean DeletePersistedDatabaseModel(String RemovedModelKeyStr);

}