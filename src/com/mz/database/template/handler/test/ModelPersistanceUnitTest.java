package com.mz.database.template.handler.test;

import java.io.File;

import org.junit.Test;

import com.mz.database.models.DatabaseDescriptionPOJO;
import com.mz.utilities.PathManager;
import com.mz.utilities.SerializationManager;

public class ModelPersistanceUnitTest {

	@Test
	public void Success_when_model_is_persisted(){
		DatabaseDescriptionPOJO pojo = new DatabaseDescriptionPOJO();
		pojo.setDatabaseNameStr("Hello");
		String projectDir = PathManager.AppendAtEndFileSeparatorIfNeeded(
				System.getProperty("user.dir"));
		String DestinationFilePath = projectDir + File.separator + "ressources" + File.separator + 
				"saved_database_models" + File.separator +"Hello.ser";
		SerializationManager.DoSerializeObject(pojo, DestinationFilePath);
	}
	
}
