package com.mz.utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilePermission;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.apache.log4j.Logger;

public class FileManager {
	
	private static final Logger LOGGER = Logger.getLogger(FileManager.class);

	public static boolean DoesFileExists(String FilePathStr){
		File file = new File(FilePathStr);
		boolean result = file.exists();
		result = result && file.isFile();
		return result;
	}
	
	public static boolean DoesFolderExists(String FolderPathStr){
		File file = new File(FolderPathStr);
		boolean result = file.exists();
		result = result && file.isDirectory();
		return result;
	}
	
	public static boolean CreateTextFile(String FilePathStr, String FileContentString){
		return CreateTextFile(FilePathStr,
				FileContentString,false);
	}
	
	public static boolean CreateTextFile(String FilePathStr,
			String FileContentString,boolean DoCreateAssociatedDirectoryPath)
	{
		if(DoesFileExists(FilePathStr)) return false;
		String parentFolderDirectoryPath = PathManager.getParentDirectoryPathFromStringPath(FilePathStr);
		if(parentFolderDirectoryPath == null) return false; //Wrong path provided for file creation
		if(!DoesFolderExists(parentFolderDirectoryPath))
			if(DoCreateAssociatedDirectoryPath) DirectoryManager.CreateDirectoryPath(parentFolderDirectoryPath);
			else return false; // if the parent directory path do not exist and cannot be created then there is a problem in the provided path, thus no file creation is possible
		BufferedWriter output = null;
		try
		{
			File file = new File(FilePathStr);
			output = new BufferedWriter(new OutputStreamWriter( new FileOutputStream(file)));
			output.write(FileContentString);
			output.close();
		}
		catch(IOException e)
		{
			LOGGER.error(e);
		}
		return true;
	}
	
	public static boolean AppendTextFile(String FilePathStr, String AppendedFileContentString){
		if(!DoesFileExists(FilePathStr)) return false;
		BufferedWriter output = null;
		try
		{
			File file = new File(FilePathStr);
			output = new BufferedWriter(new FileWriter(file));
			output.write(AppendedFileContentString);
			output.close();
		}
		catch(IOException e)
		{
			LOGGER.error("Problem during text file appending");
		}
		return true;
	}
	
	public static boolean CreateOrAppendTextFile(String FilePathStr, String TextContentStr)
	{
		if(DoesFileExists(FilePathStr)) return AppendTextFile(FilePathStr, TextContentStr);
		else return CreateTextFile(FilePathStr, TextContentStr);		
	}
	
	
	public static boolean DeleteFile(String FilePathStr){
		File file = new File(FilePathStr);
		if(!file.exists()) return true;
		file.delete();
		return true;
	}
	
	public static boolean RenameFile(String RenamedFilePathStr, String NewFileName){
		return RenameFile(RenamedFilePathStr, NewFileName, false);
	}
	
	public static boolean RenameFile(String RenamedFilePathStr,String NewFileName,boolean ReplaceFileWithSameNewFileName)
	{
		if(RenamedFilePathStr == null) return false;
		if(NewFileName == null) return false;
		File RenamedFile = new File(RenamedFilePathStr);
		if(!RenamedFile.exists()) return false;
		if(!RenamedFile.isFile()) return false;
		String NewFilePath = PathManager.AppendAtEndFileSeparatorIfNeeded( 
				PathManager.getParentDirectoryPathFromStringPath(RenamedFilePathStr)) + NewFileName;
		File NewFile = new File(NewFilePath);
		if(NewFile.exists())
		{
			if(ReplaceFileWithSameNewFileName)
			{
				NewFile.delete();
			}
			else return false;
		}
		RenamedFile.renameTo(NewFile);
		return true;
	}
}
