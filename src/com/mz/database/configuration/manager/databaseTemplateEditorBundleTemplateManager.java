package com.mz.database.configuration.manager;

import java.util.ArrayList;
import java.util.List;

import com.mz.database.template.bundle.handlers.TemplateBundleManager;

public class databaseTemplateEditorBundleTemplateManager {

	private static final String BUNDLE_TEMPLATE_FILE_DEFAULT_CONTENT = "Write template content here";
	
	private static TemplateBundleManager _templateBundleManager = 
			new TemplateBundleManager(databaseTemplateEditorConfigurationManager.
					get_templateBundlesDirectoryRootFolderPathStr());
	private static TemplateBundleManager getTemplateBundleManager()
	{
		return _templateBundleManager;
	}
	
	public static List<String> ListBundles()
	{
		TemplateBundleManager templateBundleManager = getTemplateBundleManager();
		return templateBundleManager.listBundle();
	}
	
	public static boolean CreateBundle(String bundleName)
	{
		if (bundleName == null) return false;
		TemplateBundleManager templateBundleManager = getTemplateBundleManager();
		return templateBundleManager.createBundle(bundleName);
	}
	
	public static boolean DeleteBundle(String bundleName)
	{
		if(bundleName == null) return false;
		TemplateBundleManager templateBundleManager = getTemplateBundleManager();
		return templateBundleManager.deleteBundle(bundleName);
	}
	
	public static boolean AddFileToBundle(String FileBundleInternalPath, String bundleName)
	{
		if(!IsBundleExist(bundleName)) return false;
		TemplateBundleManager templateBundleManager = getTemplateBundleManager();
		return templateBundleManager.saveFileInBundle(bundleName, FileBundleInternalPath, 
				databaseTemplateEditorBundleTemplateManager.BUNDLE_TEMPLATE_FILE_DEFAULT_CONTENT);
	}
	
	public static boolean RemoveFileFromBundle(String FileBundleInternalPath,String bundleName)
	{
		if(!IsBundleExist(bundleName)) return false;
		TemplateBundleManager templateBundleManager = getTemplateBundleManager();
		return templateBundleManager.deleteFileFromBundle(bundleName, FileBundleInternalPath);
	}
	
	public static boolean RemoveFolderFromBundle(String FolderBundleInternalPath, String bundleName)
	{
		if(!IsBundleExist(bundleName))return false;
		TemplateBundleManager templateBundleManager = getTemplateBundleManager();
		return templateBundleManager.deleteFolderFromBundle(bundleName, FolderBundleInternalPath);
	}
	
	public static String getTextFromBundleFile(String FileBundleInternalPath, String bundleName)
	{
		if(!IsBundleExist(bundleName))return "";
		TemplateBundleManager templateBundleManager = getTemplateBundleManager();
		return templateBundleManager.getFileContentFromBundle(bundleName, FileBundleInternalPath);
	}
	
	public static boolean IsBundleExist(String bundleName)
	{
		if(bundleName == null || bundleName.isEmpty()) return false;
		TemplateBundleManager templateBundleManager = getTemplateBundleManager();
		return templateBundleManager.IsBundleExist(bundleName);
	}
	
	public static boolean IsFileExistInBundle( String FileOrFolderPath,String bundleName)
	{
		if(!IsBundleExist(bundleName)) return false;
		TemplateBundleManager templateBundleManager = getTemplateBundleManager();
		return templateBundleManager.isFileOrFolderPathInBundle(FileOrFolderPath,bundleName);
	}
	
	public static boolean IsFolderInBundle(String FolderPath, String bundleName)
	{
		if(!IsBundleExist(bundleName)) return false;
		TemplateBundleManager templateBundleManager = getTemplateBundleManager();
		return templateBundleManager.IsFolderInBundle(bundleName, FolderPath);
	}
	
	public static List<String> ListBundleFiles(String bundleName)
	{
		List<String> result = new ArrayList<String>();
		if(!IsBundleExist(bundleName)) return result;
		TemplateBundleManager templateBundleManager = getTemplateBundleManager();
		return templateBundleManager.listFileInBundle(bundleName);
	}
	
	public static boolean SaveFileContentInBundleFile(String FileContent, String FileBundlePath,String bundleName)
	{
		if(!IsBundleExist(bundleName))return false;
		TemplateBundleManager templateBundleManager = getTemplateBundleManager();
		return templateBundleManager.saveFileInBundle(bundleName, FileBundlePath, FileContent);
	}
	
	public static boolean AddFolderToBundle(String FolderBundlePath,String bundleName)
	{
		if(!IsBundleExist(bundleName))return false;
		TemplateBundleManager templateBundleManager = getTemplateBundleManager();
		return templateBundleManager.createFolderInBundle(bundleName, FolderBundlePath);
	}
	
}
