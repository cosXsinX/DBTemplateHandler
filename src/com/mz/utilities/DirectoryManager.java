package com.mz.utilities;

import java.io.File;
import java.util.LinkedList;
import java.util.Stack;

public class DirectoryManager {
	public static boolean CreateDirectoryPath(String directoryPathStr)
	{
		Stack<String> stack = new Stack<String>();
		
		String currentPathDirectory = directoryPathStr;
		while(!FileManager.DoesFolderExists(currentPathDirectory))
		{
			stack.push(currentPathDirectory);

			if((currentPathDirectory = PathManager.
					getParentDirectoryPathFromStringPath(currentPathDirectory))==null) break;
		}
		if(currentPathDirectory == null) return false;
		
		while(stack.size()>0)
		{
			File file = new File(stack.pop());
			file.mkdir();
		}
		return true;
	}
}
