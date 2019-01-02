package com.mz.database.configuration.manager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.UserDataHandler;
import org.w3c.dom.traversal.NodeFilter;

import com.mz.database.models.DatabaseDescriptionPOJO;
import com.mz.database.persistance.managers.IPersistanceManager;
import com.mz.database.persistance.managers.SQLitePersistanceManager;
import com.mz.utilities.FileManager;
import com.mz.utilities.PathManager;
import com.mz.utilities.SerializationManager;
import com.mz.utilities.XMLManager;

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
