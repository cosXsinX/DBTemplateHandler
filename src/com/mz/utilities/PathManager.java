package com.mz.utilities;

import java.io.File;

public class PathManager {

	public static String AppendAtEndFileSeparatorIfNeeded(String pathStr)
	{
		String result = pathStr;
		if(!pathStr.endsWith(File.separator)) result = result + File.separator;
		return result;
	}
	
	public static String getParentDirectoryPathFromStringPath(String PathStr){
		String result = PathStr;
		if(result == null) return result;
		if(StringManager.countStringInString(PathStr, File.separator)<=1) return null;
		if(result.endsWith(File.separator)) result = result.substring(0,result.length()-1);
		int lastIndexOfPathSeparator = result.lastIndexOf(File.separator);
		if(lastIndexOfPathSeparator<1) return null;
		result = result.substring(0,lastIndexOfPathSeparator);
		return result;
	}
	
	
	public static String getFileOrFolderNameFromStringPath(String PathStr)
	{
		String result = PathStr;
		if(result == null) return result;
		if(result.endsWith(File.separator)) result = result.substring(0,result.length()-1);
		int lastIndexOfPathSeparator = result.lastIndexOf(File.separator);
		if(lastIndexOfPathSeparator<1) return result;
		result = result.substring(lastIndexOfPathSeparator,result.length());
		return result;
	}
	
	public static String getFileBaseNameFromString(String PathStr)
	{
		String result = getFileOrFolderNameFromStringPath(PathStr);
		int lastIndexOfFileExtensionSeparation = result.lastIndexOf(".");
		if(lastIndexOfFileExtensionSeparation<1) return result;
		result = result.substring(0,lastIndexOfFileExtensionSeparation);
		return result;
	}
}
