package com.mz.database.configuration.manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import com.mz.utilities.PathManager;

public class databaseTemplateEditorTemplateManager {
	
	public static List<String> ListAvailableTemplates()
	{
		String templateDirectoryPathStr = 
				databaseTemplateEditorConfigurationManager.
					get_templateDirectoryRootFolderPathStr();
		File templateDirectoryFile = new File(templateDirectoryPathStr);
		return Arrays.asList( templateDirectoryFile.list());
	}
	
	
	private final static char NEW_LINE_CHAR = '\n';
	public static String getTemplateFileStringContent(String templateFileName) throws IOException
	{
		String templateDirectoryPathStr =
				databaseTemplateEditorConfigurationManager.
					get_templateDirectoryRootFolderPathStr();
		templateDirectoryPathStr = PathManager.AppendAtEndFileSeparatorIfNeeded(templateDirectoryPathStr);
		String templateFilePath = templateDirectoryPathStr + templateFileName;
		File templateFile = new File(templateFilePath);
		if(!templateFile.exists()) return "File does not exist";
		if(!templateFile.isFile()) return "The file path is not a path to a file";
		if(!templateFile.canRead()) return "The file cannot be read";
		FileInputStream fileInputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;
		StringBuilder outputStringBuilder = new StringBuilder();
		
		try {
			fileInputStream =  new FileInputStream(templateFile);
			inputStreamReader = new InputStreamReader(fileInputStream);
			bufferedReader = new BufferedReader(inputStreamReader);
			String currentLine;
	        
			while ((currentLine = bufferedReader.readLine()) != null) {
			    outputStringBuilder.append(currentLine + NEW_LINE_CHAR);
			}
			
		}
		finally {
	         if (bufferedReader != null)  bufferedReader.close();
	         if (inputStreamReader != null) inputStreamReader.close();
	         if (fileInputStream != null) fileInputStream.close();
		}
		return outputStringBuilder.toString();
	}
	
	public static void saveTemplateString(String TemplateFileNameString,
			String TemplateFileContent) throws IOException
	{
		if(TemplateFileNameString == null) return;
		if(TemplateFileContent == null) return;
		String templateDirectoryPathStr =
				databaseTemplateEditorConfigurationManager.
					get_templateDirectoryRootFolderPathStr();
		templateDirectoryPathStr = PathManager.AppendAtEndFileSeparatorIfNeeded(templateDirectoryPathStr);
		String templateFilePath = templateDirectoryPathStr + TemplateFileNameString;
		File templateFile = new File(templateFilePath);
		if(!templateFile.canWrite()) return;
		if(templateFile.exists())
			templateFile.delete();
		FileWriter fileWriter;
		fileWriter = new FileWriter(templateFile);
		fileWriter.write(TemplateFileContent);
		fileWriter.close();
	}
}
