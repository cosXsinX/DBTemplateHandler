package com.mz.database.template.bundle.handlers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.mz.utilities.FileManager;
import com.mz.utilities.PathManager;

public class TemplateBundleManager {
	private final static String BUNDLE_FILE_EXTENSION = ".zip";
	private final String templateBundleDestinationPoolPath;
	private final ZipFileManager zipFileManager ;
	
	public TemplateBundleManager(String templateBundleDestinationPoolPath)
	{
		this.templateBundleDestinationPoolPath = PathManager.AppendAtEndFileSeparatorIfNeeded(templateBundleDestinationPoolPath); 
		this.zipFileManager = new ZipFileManager();
	}
	
	private boolean isBundle(String TestedBundleFilePath)
	{
		if(!TestedBundleFilePath.endsWith(TemplateBundleManager.BUNDLE_FILE_EXTENSION))return false;
		if(!TestedBundleFilePath.startsWith(templateBundleDestinationPoolPath)) return false;
		return true;
	}
	
	private String extractBundleNameFromFilePath(String filePath)
	{
		int endIndex = filePath.length() - TemplateBundleManager.BUNDLE_FILE_EXTENSION.length();
		if(endIndex <0) return null;
		//if(endIndex>0) endIndex = endIndex-1;
		int startIndex = templateBundleDestinationPoolPath.length();
		if (startIndex>0) startIndex = startIndex-1;
		return filePath.substring(templateBundleDestinationPoolPath.length(), endIndex);
	}
	
	private String getBundleFileName(String BundleName)
	{
		return BundleName + TemplateBundleManager.BUNDLE_FILE_EXTENSION;
	}
	
	private String getBundleFilePath(String BundleName)
	{
		return this.templateBundleDestinationPoolPath + this.getBundleFileName(BundleName);
	}
	
	public boolean IsBundleExist(String BundleName)
	{
		File templateBundleDestinationPoolFile = new File(this.templateBundleDestinationPoolPath);
		File[] containedFileList = templateBundleDestinationPoolFile.listFiles(); 
		String bundleFileNameLowered = this.getBundleFileName(BundleName).toLowerCase();
		for(File currentFile: containedFileList)	
			if(currentFile.getName().toLowerCase().equals(bundleFileNameLowered))
				return true;
		return false;
	}
	
	public boolean createBundle(String BundleName)
	{ 
		if( this.IsBundleExist(BundleName)) return false;
		zipFileManager.createZipFile(this.templateBundleDestinationPoolPath + BundleName + TemplateBundleManager.BUNDLE_FILE_EXTENSION);
		return true;
	}
	
	public boolean createFolderInBundle(String BundleName, String BundleFolderPath)
	{
		if(!this.IsBundleExist(BundleName))return false;
		zipFileManager.createFolderInZipFile(this.templateBundleDestinationPoolPath + BundleName + TemplateBundleManager.BUNDLE_FILE_EXTENSION, BundleFolderPath);
		return true;
	}
	
	public boolean deleteBundle(String BundleName)
	{
		if(!this.IsBundleExist(BundleName)) return true;
		return FileManager.DeleteFile(this.templateBundleDestinationPoolPath + BundleName + 
				TemplateBundleManager.BUNDLE_FILE_EXTENSION);
	}
	
	public boolean saveFileInBundle(String BundleName,String FileBundlePath,String FileContent)
	{
		if( !this.IsBundleExist(BundleName))
		{
			this.createBundle(BundleName);
		}
		String zipFilePath = this.getBundleFilePath(BundleName);
		return this.zipFileManager.saveTextFileInZipFile(zipFilePath, FileBundlePath, FileContent);
	}
	
	public String getFileContentFromBundle(String BundleName, String FileBundlePath)
	{
		if(!this.IsBundleExist(BundleName)) return null;
		String zipFilePath = this.getBundleFilePath(BundleName);
		return this.zipFileManager.getTextFileContentInZipFile(zipFilePath, FileBundlePath);
	}
	
	public boolean IsFolderInBundle(String BundleName , String SearchedFolderBundlePath)
	{
		if(!this.isBundle(BundleName)) return false;
		String zipFilePath = this.getBundleFilePath(BundleName);
		return this.zipFileManager.IsFolderContainedInZipFile(zipFilePath, SearchedFolderBundlePath);
	}
	
	public List<String> listFileInBundle(String BundleName)
	{
		ArrayList<String> defaultResult = new ArrayList<>();
		if(!this.IsBundleExist(BundleName)) return defaultResult;
		String zipFilePath = this.getBundleFilePath(BundleName);
		return this.zipFileManager.ListZipContainedFile(zipFilePath);
	}
	
	public List<String> listBundle()
	{
		ArrayList<String> result = new ArrayList<String>();
		File bundlePoolFolderFile = new File(this.templateBundleDestinationPoolPath);
		File[] files = bundlePoolFolderFile.listFiles();
		for (int i = 0;i<files.length;i++)
		{
			File currentFile = files[i];
			String currentFileAbsolutePath =currentFile.getAbsolutePath();
			if(this.isBundle(currentFileAbsolutePath))
			{
				String bundleName = this.extractBundleNameFromFilePath(currentFileAbsolutePath);
				result.add(bundleName);
			}
		}
		return result;
	}
	
	public boolean isFileOrFolderPathInBundle(String FileOrFolderPath, String BundleName)
	{
		if(!this.IsBundleExist(BundleName)) return false;
		String zipFilePath = this.getBundleFilePath(BundleName);
		List<String> containedFileList =  this.zipFileManager.ListZipContainedFile(zipFilePath);
		for(int i=0;i<containedFileList.size();i++)
		{
			String currentFilePath = containedFileList.get(i);
			if(currentFilePath.equals(FileOrFolderPath)) return true;
		}
		return false;
	}
	
	public boolean deleteFileFromBundle(String BundleName,String deletedFileBundlePath)
	{
		if(!this.IsBundleExist(BundleName))return false;
		String zipFilePath = this.getBundleFilePath(BundleName);
		return this.zipFileManager.deleteTextFileInZipFile(zipFilePath, deletedFileBundlePath);
	}
	
	public boolean deleteFolderFromBundle(String BundleName, String deletedFolderBundlePath)
	{
		if(!this.IsBundleExist(BundleName)) return false;
		String zipFilePath = this.getBundleFilePath(BundleName);
		//TODO Modify this
		return this.zipFileManager.deleteFolderInZipFile(zipFilePath, deletedFolderBundlePath);
	}
	
	public boolean moveFileFromBundleToBundle(String SourceBundleName, String DestinationBundleName, String movedFilePath)
	{
		if(!this.copyFileFromBundleToBundle(SourceBundleName, DestinationBundleName, movedFilePath)) return false;
		return this.deleteFileFromBundle(SourceBundleName, movedFilePath);
	}
	
	public boolean copyFileFromBundleToBundle(String SourceBundleName,String DestinationBundleName, String copiedFilePath)
	{
		String CopiedFileContent = this.getFileContentFromBundle(SourceBundleName, copiedFilePath);
		if(CopiedFileContent == null) return false;
		return this.saveFileInBundle(DestinationBundleName, copiedFilePath, CopiedFileContent);
	}
}
