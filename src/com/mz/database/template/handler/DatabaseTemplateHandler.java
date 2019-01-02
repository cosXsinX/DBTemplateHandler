package com.mz.database.template.handler;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.mz.database.descriptors.AbstractDatabaseDescriptor;
import com.mz.database.models.DatabaseDescriptionPOJO;
import com.mz.database.models.TableDescriptionPOJO;
import com.mz.database.template.semantic.TemplateSemanticReferenceClass;
import com.mz.utilities.FileManager;

public class DatabaseTemplateHandler {
	DatabaseDescriptionPOJO generatedDatabaseDescription;
	AbstractDatabaseDescriptor _databaseDescriptor;
	
	public DatabaseDescriptionPOJO getGeneratedDatabaseDescription()
	{
		return generatedDatabaseDescription;
	}
	
	public void setGeneratedDatabaseDescription(DatabaseDescriptionPOJO value)
	{
		generatedDatabaseDescription = value;
	}
	
	public AbstractDatabaseDescriptor setDatabaseDescriptor()
	{
		return _databaseDescriptor;
	}
	
	public void setDatabaseDescriptor(AbstractDatabaseDescriptor databaseDescriptor){
		_databaseDescriptor = databaseDescriptor;
	}
	
	public DatabaseTemplateHandler(DatabaseDescriptionPOJO generatedDatabaseDescription,AbstractDatabaseDescriptor databaseDescriptor)
	{
		setGeneratedDatabaseDescription(generatedDatabaseDescription);
		setDatabaseDescriptor(databaseDescriptor);
	}
	
	public boolean GenerateDatabaseFilesFromTemplateFile(File templateFile) throws IOException
	{
		boolean result = false;
		if(templateFile == null) return result;
		if(generatedDatabaseDescription == null) return result;
		String savedFileTempalteName = templateFile.getName();
		if(savedFileTempalteName.contains(TemplateSemanticReferenceClass.TEMPLATE_NAME_DATABASE_NAME_WORD_IDENTIFIER))
			savedFileTempalteName = savedFileTempalteName.
				replace(TemplateSemanticReferenceClass.TEMPLATE_NAME_DATABASE_NAME_WORD_IDENTIFIER, generatedDatabaseDescription.getDatabaseNameStr());
		if(savedFileTempalteName.contains(TemplateSemanticReferenceClass.TEMPLATE_NAME_TABLE_NAME_WORD_IDENTIFIER))
		{
			List<TableDescriptionPOJO> tableList = generatedDatabaseDescription.getTableList();
			for(TableDescriptionPOJO currentTable : tableList)
			{
				TableTemplateHandler tableTemplateHandler = 
						TableTemplateHandler.TableDescriptionPOJOToTableTemplateHandler(currentTable,_databaseDescriptor);
				File currentResult = tableTemplateHandler.generateTableFileFromTemplateFile(templateFile);
				if(currentResult.getName().contains(TemplateSemanticReferenceClass.TEMPLATE_NAME_DATABASE_NAME_WORD_IDENTIFIER)){
					String currentNewSavedFileName = currentResult.getName().
							replace(TemplateSemanticReferenceClass.TEMPLATE_NAME_DATABASE_NAME_WORD_IDENTIFIER,
									generatedDatabaseDescription.getDatabaseNameStr());
					if(currentNewSavedFileName.endsWith
							(TemplateSemanticReferenceClass.TEMPLATE_FILE_NAME_EXTENSION)){
						currentNewSavedFileName = currentNewSavedFileName.substring(0,currentNewSavedFileName.length()-TemplateSemanticReferenceClass.TEMPLATE_FILE_NAME_EXTENSION.length());
					}
					FileManager.RenameFile(currentResult.getAbsolutePath(), currentNewSavedFileName, true); 
				}
				
			}
		}
		
		return result;
	}
}
