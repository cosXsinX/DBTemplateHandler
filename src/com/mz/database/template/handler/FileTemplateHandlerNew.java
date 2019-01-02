package com.mz.database.template.handler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.mz.database.models.DatabaseDescriptionPOJO;
import com.mz.database.models.TableColumnDescriptionPOJO;
import com.mz.database.models.TableDescriptionPOJO;
import com.mz.utilities.PathManager;

public class FileTemplateHandlerNew {
	
	private final String DATABASE_TEMPLATE_FILE_NAME_WORD = "%databaseName%";
	private final String TABLE_TEMPLATE_FILE_NAME_WORD = "%tableName%";
	private final String COLUMN_TEMPLATE_FILE_NAME_WORD = "%columnName%";
	
	private String _outpuFolderPath;
	public String get_outpuFolderPath() {
		return _outpuFolderPath;
	}
	public void set_outpuFolderPath(String value) {
		this._outpuFolderPath = value;
	}
	
	
	public boolean GenerateDatabaseTemplateFiles
		(String handledTemplateFilePath,
				String specifiedDestinationRelativePath,
					DatabaseDescriptionPOJO databaseDescriptionPOJO) throws Exception
	{
		if(databaseDescriptionPOJO == null) return false;
		if(specifiedDestinationRelativePath == null) return false;
		String handledTemplateStringContent = getHandledTemplateStringContent(handledTemplateFilePath);
		if(handledTemplateStringContent == null) return false;
		boolean containsTblWord =
				specifiedDestinationRelativePath.
					contains(TABLE_TEMPLATE_FILE_NAME_WORD);
		boolean containsColWord = 
				handledTemplateFilePath.
					contains(COLUMN_TEMPLATE_FILE_NAME_WORD);
		if(containsColWord)
		{
			String currentDatabaseReplacedDestinationRelativePath = 
					specifiedDestinationRelativePath.replace(
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
					String destinationFilePath = PathManager.AppendAtEndFileSeparatorIfNeeded(get_outpuFolderPath()) + currentColumnReplacedDestinationRelativePath;
					CreateOrReplaceFileWithContent(destinationFilePath,handlerOutput);
				}
			}
		}
		else if (containsTblWord)
		{
			String currentDatabaseReplacedDestinationRelativePath = 
					specifiedDestinationRelativePath.replace(
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
				String destinationFilePath = PathManager.AppendAtEndFileSeparatorIfNeeded(get_outpuFolderPath()) + currentTableReplacedDestinationRelativePath;
				CreateOrReplaceFileWithContent(destinationFilePath,handlerOutput);
			}
		}
		else
		{
			String currentDatabaseReplacedDestinationRelativePath = 
					specifiedDestinationRelativePath.replace(
							DATABASE_TEMPLATE_FILE_NAME_WORD, 
								databaseDescriptionPOJO.getDatabaseNameStr());
			
			String handlerOutput = TemplateHandlerNew.HandleTemplate(
						handledTemplateStringContent, 
							databaseDescriptionPOJO, null, null);
			String destinationFilePath = PathManager.AppendAtEndFileSeparatorIfNeeded(get_outpuFolderPath()) + currentDatabaseReplacedDestinationRelativePath;
			CreateOrReplaceFileWithContent(destinationFilePath,handlerOutput);
			
		}
		return true;
	}
	
	public boolean GenerateTableTemplateFiles
	(String handledTemplateFilePath,
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
				String destinationFilePath = PathManager.AppendAtEndFileSeparatorIfNeeded(get_outpuFolderPath()) + currentColumnReplacedDestinationRelativePath;
				
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
			String destinationFilePath = PathManager.AppendAtEndFileSeparatorIfNeeded(get_outpuFolderPath()) + currentTableReplacedDestinationRelativePath;
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
