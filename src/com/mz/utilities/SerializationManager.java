package com.mz.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;

public class SerializationManager {

	private static final Logger LOGGER = Logger.getLogger(SerializationManager.class);
	
	public static void DoSerializeObject(Object PersistedObject,String DestinationFilePathStr)
	{
		try{
			FileOutputStream fileOut = new FileOutputStream(DestinationFilePathStr);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(PersistedObject);
			out.close();
			fileOut.close();
		}
		catch(IOException exception)
		{
			LOGGER.error("Cannot persist the object.");
		}
	}
	
	public static Object DoUnserializeObject(String SourceFilePathStr){
		try
	      {
	         FileInputStream fileIn = new FileInputStream(SourceFilePathStr);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         Object result= in.readObject();
	         in.close();
	         fileIn.close();
	         return result;
	      }catch(IOException i)
	      {
	    	  LOGGER.error(i);
	         return null;
	      }catch(ClassNotFoundException c)
	      {
	         LOGGER.error("Employee class not found");
	         return null;
	      }	
	}
}
