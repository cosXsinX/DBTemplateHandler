package com.mz.UI;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.mz.UI.Frame.DatabaseDefinitionUI;
import com.mz.UI.Frame.EveryDatabaseModelManagementUI;
import com.mz.database.configuration.manager.databaseTemplateEditorConfigurationManager;
import com.mz.database.configuration.manager.databaseTemplateEditorPersistanceManager;
import com.mz.database.descriptors.SQLLiteDatabaseDescriptor;
import com.mz.database.models.DatabaseDescriptionPOJO;

public class MainClass {

	private static Logger logger = Logger.getLogger(MainClass.class);
	
	private final static HashMap<String,DatabaseDefinitionUI> _KeyIndexedDatabaseDefinitionUIHashMap = 
			new HashMap<String,DatabaseDefinitionUI>();
	
	 public static void main(String[] args) {
		 logger.info("MainClass start");
		 	databaseTemplateEditorConfigurationManager.InitApplicationConfigurationFileSystem();
		 	new EveryDatabaseModelManagementUI(new SQLLiteDatabaseDescriptor()).setVisible(true);
	    	//new DatabaseDefinitionUI(new SQLLiteDatabaseDescriptor()).setVisible(true);
	    }
	 
	 public static void ShowDatabaseDefinitionUI(String SelectedDescriptionDatabaseModel)
	 {
		 DatabaseDescriptionPOJO instanciatedPOJO = 
				 databaseTemplateEditorPersistanceManager.
				 	getPersistedDatabaseModel(SelectedDescriptionDatabaseModel);
		 DatabaseDefinitionUI databaseFrame = new DatabaseDefinitionUI(instanciatedPOJO ,new SQLLiteDatabaseDescriptor());
		 databaseFrame.setVisible(true);
		 _KeyIndexedDatabaseDefinitionUIHashMap.put(SelectedDescriptionDatabaseModel,databaseFrame); 
	 }
	 
	 public static boolean CreateOrGetDatabaseDescriptionAndShowDefinitionUI(String SelectedDescriptionDatabaseModel)
	 {
		if(databaseTemplateEditorPersistanceManager.IsAlreadyPresentKeyInAvailableKey(SelectedDescriptionDatabaseModel)){
			DatabaseDescriptionPOJO instanciatedPOJO = databaseTemplateEditorPersistanceManager.getPersistedDatabaseModel(SelectedDescriptionDatabaseModel);
			if(instanciatedPOJO == null) return false;
			instanciatedPOJO.setDatabaseNameStr(SelectedDescriptionDatabaseModel);
			DatabaseDefinitionUI databaseFrame = new DatabaseDefinitionUI(instanciatedPOJO, new SQLLiteDatabaseDescriptor()); // TODO remove this
			 _KeyIndexedDatabaseDefinitionUIHashMap.put(SelectedDescriptionDatabaseModel,databaseFrame); 
			 return true;
		}
		else
		{
			return CreateNewDatabaseDescriptionAndShowDefinitionUI(SelectedDescriptionDatabaseModel);
		}
	 }
	 
	 public static boolean CreateNewDatabaseDescriptionAndShowDefinitionUI(String SelectedDescriptionDatabaseModel)
	 {
		 if(databaseTemplateEditorPersistanceManager.IsAlreadyPresentKeyInAvailableKey(SelectedDescriptionDatabaseModel)) return false;
		 DatabaseDescriptionPOJO instanciatedPOJO = new DatabaseDescriptionPOJO();
		 instanciatedPOJO.setDatabaseNameStr(SelectedDescriptionDatabaseModel);
		 if(!databaseTemplateEditorPersistanceManager.doPersistDatabaseModel(instanciatedPOJO, SelectedDescriptionDatabaseModel)) return false;
		 DatabaseDefinitionUI databaseFrame = new DatabaseDefinitionUI(instanciatedPOJO, new SQLLiteDatabaseDescriptor()); // TODO remove this
		 _KeyIndexedDatabaseDefinitionUIHashMap.put(SelectedDescriptionDatabaseModel,databaseFrame); 
		 return true;
	 }
	 
	 public static boolean DeleteDatabaseModelAndDisposeAssociatedFrameIfNeeded
	 	(String DeletedDatabaseModelKeyStr)
	 {
		 if(DeletedDatabaseModelKeyStr == null) return false;
		 if(!databaseTemplateEditorPersistanceManager.DeletePersistedDatabaseModel(DeletedDatabaseModelKeyStr)) return false;
		 if(_KeyIndexedDatabaseDefinitionUIHashMap.containsKey(DeletedDatabaseModelKeyStr))
		 {
			 DatabaseDefinitionUI datbaseFrame = _KeyIndexedDatabaseDefinitionUIHashMap.get(DeletedDatabaseModelKeyStr);
			 datbaseFrame.dispose();
		 }
		 return true;
	 }
}
