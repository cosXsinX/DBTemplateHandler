package com.mz.database.persistance.managers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.mz.database.configuration.manager.databaseTemplateEditorConfigurationManager;
import com.mz.database.models.DatabaseDescriptionPOJO;
import com.mz.utilities.FileManager;
import com.mz.utilities.PathManager;
import com.mz.utilities.SerializationManager;

public class FilePersistanceManager implements IPersistanceManager {

private final static String PERSISTED_FILE_EXTENSION = ".ser";
	
	
	private String getKeyAssociatedFilePath(String KeyStr){
		String persistenceDirectoryPathStr =
				databaseTemplateEditorConfigurationManager.get_savedModelDirectoryPathStr();
		persistenceDirectoryPathStr = PathManager.AppendAtEndFileSeparatorIfNeeded(persistenceDirectoryPathStr);
		String result = persistenceDirectoryPathStr + KeyStr + PERSISTED_FILE_EXTENSION;
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.mz.database.persistance.managers.IPersistanceManager#doPersistDatabaseModel(com.mz.database.models.DatabaseDescriptionPOJO, java.lang.String)
	 */
	@Override
	public boolean doPersistDatabaseModel
		(DatabaseDescriptionPOJO persistedModel,String AssociatedKeyStr) 
	{
		if(persistedModel == null) return false;
		if(AssociatedKeyStr == null) return false;
		String filePathStr = getKeyAssociatedFilePath(AssociatedKeyStr);
		SerializationManager.DoSerializeObject(persistedModel, filePathStr);
		return true;
	}

	/* (non-Javadoc)
	 * @see com.mz.database.persistance.managers.IPersistanceManager#getPersistedDatabaseModel(java.lang.String)
	 */
	@Override
	public DatabaseDescriptionPOJO getPersistedDatabaseModel(String ModelKeyStr){
		if(ModelKeyStr == null) return null;
		String filePathStr = getKeyAssociatedFilePath(ModelKeyStr);
		DatabaseDescriptionPOJO pojo =(DatabaseDescriptionPOJO)SerializationManager.DoUnserializeObject(filePathStr);
		return pojo;
	}
	
	/* (non-Javadoc)
	 * @see com.mz.database.persistance.managers.IPersistanceManager#ListEveryAvailableKey()
	 */
	@Override
	public List<String> ListEveryAvailableKey(){
		String persistanceDirectoryPathStr = databaseTemplateEditorConfigurationManager.get_savedModelDirectoryPathStr();
		File persistanceDirectory = new File(persistanceDirectoryPathStr);
		String[] filenames = persistanceDirectory.list();
		int currentFileIndex;
		String currentFileNameStr;
		List<String> result = new ArrayList<String>();
		for(currentFileIndex = 0; currentFileIndex<filenames.length;currentFileIndex++)
		{
			currentFileNameStr = filenames[currentFileIndex];
			result.add(PathManager.getFileBaseNameFromString(currentFileNameStr));
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.mz.database.persistance.managers.IPersistanceManager#IsAlreadyPresentKeyInAvailableKey(java.lang.String)
	 */
	@Override
	public boolean IsAlreadyPresentKeyInAvailableKey(String CheckedKeyStr)
	{
		if(CheckedKeyStr == null) return false;
		List<String> currentAvailableKeyList = ListEveryAvailableKey();
		int currentFileKeyNameIndex;
		String currentFileKeyNameStr;
		for(currentFileKeyNameIndex= 0; currentFileKeyNameIndex<currentAvailableKeyList.size(); currentFileKeyNameIndex++)
		{
			currentFileKeyNameStr = currentAvailableKeyList.get(currentFileKeyNameIndex);
			if(CheckedKeyStr.equals(currentFileKeyNameStr)) return true;
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see com.mz.database.persistance.managers.IPersistanceManager#DeletePersistedDatabaseModel(java.lang.String)
	 */
	@Override
	public boolean DeletePersistedDatabaseModel(String RemovedModelKeyStr)
	{
		if(RemovedModelKeyStr == null) return false;
		String filePathStr = getKeyAssociatedFilePath(RemovedModelKeyStr);
		if(!FileManager.DoesFileExists(filePathStr)) return true;
		FileManager.DeleteFile(filePathStr);
		return true;
	}
}
