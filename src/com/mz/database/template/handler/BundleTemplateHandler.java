package com.mz.database.template.handler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.mz.database.configuration.manager.databaseTemplateEditorBundleTemplateManager;
import com.mz.database.models.DatabaseDescriptionPOJO;
import com.mz.database.models.TableColumnDescriptionPOJO;
import com.mz.database.models.TableDescriptionPOJO;
import com.mz.utilities.PathManager;

public class BundleTemplateHandler {

	private final String DATABASE_TEMPLATE_FILE_NAME_WORD = "%databaseName%";
	private final String TABLE_TEMPLATE_FILE_NAME_WORD = "%tableName%";
	private final String COLUMN_TEMPLATE_FILE_NAME_WORD = "%columnName%";
	
	
	private boolean IsBundleInternalPathADatabaseTemplatePath(String BundleInternalPath)
	{
		if(BundleInternalPath == null) return false;
		return BundleInternalPath.contains(DATABASE_TEMPLATE_FILE_NAME_WORD);
	}
	
	private boolean IsBundleInternalPathATableTemplatePath(String BundleInternalPath)
	{
		if(BundleInternalPath == null) return false;
		return BundleInternalPath.contains(TABLE_TEMPLATE_FILE_NAME_WORD);
	}
	
	private boolean IsBundleInternalPathAColumnTemplatePath(String BundleInternalPath)
	{
		if(BundleInternalPath == null) return false;
		return BundleInternalPath.contains(COLUMN_TEMPLATE_FILE_NAME_WORD);
	}
	
	public void GenerateBundleFiles(String DestinationFolderPath,String BundleName, List<String> SubmittedInternalBundlePaths,DatabaseDescriptionPOJO databaseDescriptionPOJO)
	{
		//Destination Folder Path control
		if(DestinationFolderPath == null) return; //TODO Add Error Message
		File DestinationFolderFileStructure = new File(DestinationFolderPath);
		if(!DestinationFolderFileStructure.exists()) return; //TODO Add Error Message
		if(!DestinationFolderFileStructure.isDirectory()) return; //TODO Add Error Message
		
		if(!databaseTemplateEditorBundleTemplateManager.IsBundleExist(BundleName)) return; //TODO add Error Message
		
		for(String SubmittedInternalBundlePath : SubmittedInternalBundlePaths)
		{
			if(!databaseTemplateEditorBundleTemplateManager.IsFileExistInBundle(SubmittedInternalBundlePath, BundleName)) return ;
		}
		
		for(String SubmittedInternalBundlePath : SubmittedInternalBundlePaths)
		{
			GenerateBundleFile(DestinationFolderPath, BundleName, SubmittedInternalBundlePath,databaseDescriptionPOJO);
		}
	}
	
	public void GenerateBundleFile(String DestinationFolderPath,String BundleName,String SubmittedInternalBundlePath,DatabaseDescriptionPOJO databaseDescriptionPOJO)
	{
		//Destination Folder Path control
		if(DestinationFolderPath == null) return; //TODO Add Error Message
		File DestinationFolderFileStructure = new File(DestinationFolderPath);
		if(!DestinationFolderFileStructure.exists()) return; //TODO Add Error Message
		if(!DestinationFolderFileStructure.isDirectory()) return; //TODO Add Error Message
		

		if(!databaseTemplateEditorBundleTemplateManager.IsBundleExist(BundleName)) return; //TODO add Error Message
		
		if(SubmittedInternalBundlePath == null) return; //TODO Add Error Message
		if(!databaseTemplateEditorBundleTemplateManager.IsFileExistInBundle(SubmittedInternalBundlePath, BundleName)) return ;
		
		
//		if(IsBundleInternalPathAColumnTemplatePath(SubmittedInternalBundlePath))
//		{
//			//TODO Add here the column management process call here
//			return;
//		}
//		else if(IsBundleInternalPathATableTemplatePath(SubmittedInternalBundlePath))
//		{
//			//TODO Add here the table management process call here
//			return;
//		}
		
		try {
			GenerateDatabaseTemplateFiles(DestinationFolderPath,BundleName,SubmittedInternalBundlePath,databaseDescriptionPOJO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private boolean HandleColumnTemplate(String handledTemplateStringContent,String BundleInternalPath,
			DatabaseDescriptionPOJO databaseDescriptionPOJO,String OutputFolderPath) throws Exception
	{
		String currentDatabaseReplacedDestinationRelativePath = 
				BundleInternalPath.replace(
						DATABASE_TEMPLATE_FILE_NAME_WORD, 
							databaseDescriptionPOJO.getDatabaseNameStr());
		for(TableDescriptionPOJO currentTable : databaseDescriptionPOJO.getTableList())
		{
			String currentTableReplacedDestinationRelativePath =
					currentDatabaseReplacedDestinationRelativePath.
						replace(TABLE_TEMPLATE_FILE_NAME_WORD, 
								currentTable.get_NameStr());
			for(TableColumnDescriptionPOJO currentColumn : currentTable.get_ColumnsList())
			{
				String currentColumnReplacedDestinationRelativePath =
						currentTableReplacedDestinationRelativePath.
							replace(COLUMN_TEMPLATE_FILE_NAME_WORD, currentColumn.get_NameStr());
				String handlerOutput = TemplateHandlerNew.HandleTemplate(
						handledTemplateStringContent, 
							databaseDescriptionPOJO, currentTable, currentColumn);
				String destinationFilePath = PathManager.AppendAtEndFileSeparatorIfNeeded(OutputFolderPath) + currentColumnReplacedDestinationRelativePath;
				CreateOrReplaceFileWithContent(destinationFilePath,handlerOutput);
			}
		}
		return true;
	}
	
	private boolean HandleTableTemplate(String handledTemplateStringContent, String BundleInternalPath,
			DatabaseDescriptionPOJO databaseDescriptionPOJO,String OutputFolderPath) throws Exception
	{
		String currentDatabaseReplacedDestinationRelativePath = 
				BundleInternalPath.replace(
						DATABASE_TEMPLATE_FILE_NAME_WORD, 
							databaseDescriptionPOJO.getDatabaseNameStr());
		for(TableDescriptionPOJO currentTable : databaseDescriptionPOJO.getTableList())
		{
			String currentTableReplacedDestinationRelativePath =
					currentDatabaseReplacedDestinationRelativePath.
						replace(TABLE_TEMPLATE_FILE_NAME_WORD, 
								currentTable.get_NameStr());
			String handlerOutput = TemplateHandlerNew.HandleTemplate(
						handledTemplateStringContent, 
							databaseDescriptionPOJO, currentTable, null);
			String destinationFilePath = PathManager.AppendAtEndFileSeparatorIfNeeded(OutputFolderPath) + currentTableReplacedDestinationRelativePath;
			CreateOrReplaceFileWithContent(destinationFilePath,handlerOutput);
		}
		return true;
	}
	
	private boolean HandleDatabaseTemplate(String handledTemplateStringContent, String BundleInternalPath,
			DatabaseDescriptionPOJO databaseDescriptionPOJO,String OutputFolderPath) throws Exception
	{
		String currentDatabaseReplacedDestinationRelativePath = 
				BundleInternalPath.replace(
						DATABASE_TEMPLATE_FILE_NAME_WORD, 
							databaseDescriptionPOJO.getDatabaseNameStr());
		
		String handlerOutput = TemplateHandlerNew.HandleTemplate(
					handledTemplateStringContent, 
						databaseDescriptionPOJO, null, null);
		String destinationFilePath = PathManager.AppendAtEndFileSeparatorIfNeeded(OutputFolderPath) + currentDatabaseReplacedDestinationRelativePath;
		CreateOrReplaceFileWithContent(destinationFilePath,handlerOutput);
		return true;
	}
	
	private boolean GenerateDatabaseTemplateFiles
	(String OutputFolderPath ,String BundleName,
			String BundleInternalPath,DatabaseDescriptionPOJO databaseDescriptionPOJO) throws Exception
	{
		if(databaseDescriptionPOJO == null) return false;
		if(BundleInternalPath == null) return false;
		String handledTemplateStringContent = databaseTemplateEditorBundleTemplateManager.getTextFromBundleFile(BundleInternalPath, BundleName);
		if(handledTemplateStringContent == null) return false;
		boolean containsTblWord =
				BundleInternalPath.
					contains(TABLE_TEMPLATE_FILE_NAME_WORD);
		boolean containsColWord = 
				BundleInternalPath.
					contains(COLUMN_TEMPLATE_FILE_NAME_WORD);
		if(containsColWord)
		{
			return HandleColumnTemplate(handledTemplateStringContent,BundleInternalPath,databaseDescriptionPOJO,OutputFolderPath);
		}
		else if (containsTblWord)
		{
			return HandleTableTemplate(handledTemplateStringContent,BundleInternalPath,databaseDescriptionPOJO,OutputFolderPath);
		}
		else
		{
			HandleDatabaseTemplate(handledTemplateStringContent,BundleInternalPath,databaseDescriptionPOJO,OutputFolderPath);	
		}
		return true;
	}

	private boolean GenerateTableTemplateFiles
	(String OutputFolderPath ,String handledTemplateFilePath,
			String specifiedDestinationRelativePath,
					TableDescriptionPOJO tableDescriptionPOJO) throws Exception
	{
		if(tableDescriptionPOJO == null) return false;
		if(specifiedDestinationRelativePath == null) return false;
		String handledTemplateStringContent = getHandledTemplateStringContent(handledTemplateFilePath);
		if(handledTemplateStringContent == null) return false;
		boolean containsColWord = 
				specifiedDestinationRelativePath.
					contains(COLUMN_TEMPLATE_FILE_NAME_WORD);
		if(containsColWord)
		{
			String currentDatabaseReplacedDestinationRelativePath = 
					specifiedDestinationRelativePath.replace(
							DATABASE_TEMPLATE_FILE_NAME_WORD, 
								tableDescriptionPOJO.ParentDatabase.getDatabaseNameStr());
			
			String currentTableReplacedDestinationRelativePath =
					currentDatabaseReplacedDestinationRelativePath.
						replace(TABLE_TEMPLATE_FILE_NAME_WORD, 
								tableDescriptionPOJO.get_NameStr());
			for(TableColumnDescriptionPOJO currentColumn : tableDescriptionPOJO.get_ColumnsList())
			{
				String currentColumnReplacedDestinationRelativePath =
						currentTableReplacedDestinationRelativePath.
							replace(COLUMN_TEMPLATE_FILE_NAME_WORD, currentColumn.get_NameStr());
				String handlerOutput = TemplateHandlerNew.HandleTemplate(
						handledTemplateStringContent, 
							tableDescriptionPOJO.ParentDatabase, tableDescriptionPOJO, currentColumn);
				String destinationFilePath = PathManager.AppendAtEndFileSeparatorIfNeeded(OutputFolderPath) + currentColumnReplacedDestinationRelativePath;
				
				CreateOrReplaceFileWithContent(destinationFilePath,handlerOutput);
			}
		}
		else
		{
			String currentDatabaseReplacedDestinationRelativePath = 
					specifiedDestinationRelativePath.replace(
							DATABASE_TEMPLATE_FILE_NAME_WORD, 
								tableDescriptionPOJO.ParentDatabase.getDatabaseNameStr());
			
			String currentTableReplacedDestinationRelativePath =
					currentDatabaseReplacedDestinationRelativePath.
						replace(TABLE_TEMPLATE_FILE_NAME_WORD, 
								tableDescriptionPOJO.get_NameStr());
			String handlerOutput = TemplateHandlerNew.HandleTemplate(
						handledTemplateStringContent, 
							tableDescriptionPOJO.ParentDatabase, tableDescriptionPOJO, null);
			String destinationFilePath = PathManager.AppendAtEndFileSeparatorIfNeeded(OutputFolderPath) + currentTableReplacedDestinationRelativePath;
			CreateOrReplaceFileWithContent(destinationFilePath,handlerOutput);
		}
		return true;
	}

	private String getHandledTemplateStringContent(String handledTemplateFilePath) throws IOException
	{
		if(handledTemplateFilePath == null) return null;
		File file = new File(handledTemplateFilePath);
		if(!file.exists()) return null;
		if(!file.isFile()) return null;
		if(!file.canRead()) return null;
		return readFile(handledTemplateFilePath);
	}

	private String readFile(String file) throws IOException {
	    BufferedReader reader = new BufferedReader(new FileReader(file));
	    String         line = null;
	    StringBuilder  stringBuilder = new StringBuilder();
	    String         ls = System.getProperty("line.separator");
	
	    try {
	        while((line = reader.readLine()) != null) {
	            stringBuilder.append(line);
	            stringBuilder.append(ls);
	        }
	
	        return stringBuilder.toString();
	    } finally {
	        reader.close();
	    }
	}

	private boolean CreateOrReplaceFileWithContent(String filePath,String fileContent) throws IOException{
		File file = new File(filePath);
		if(file.exists())
		{
			file.delete();
		}
		if(file.getParent() != null)
		{
			File ParentFolderFile = new File(file.getParent());
			if(!ParentFolderFile.exists()) ParentFolderFile.mkdirs();
		}
		FileWriter fw = new FileWriter( file );
		BufferedWriter bw = new BufferedWriter( fw );
		bw.write( fileContent );
		bw.flush();
		bw.close();
		fw.close();
		return true;
	}
	
}
