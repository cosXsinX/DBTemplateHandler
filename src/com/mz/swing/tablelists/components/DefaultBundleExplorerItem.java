package com.mz.swing.tablelists.components;

public class DefaultBundleExplorerItem {

	private String bundleFilePath;
	public String getBundleFilePath()
	{
		return bundleFilePath;
	}
	public String setBundleFilePath()
	{
		return bundleFilePath;
	}
	
	private boolean isFolderItem;
	public boolean isFolderItem()
	{
		return isFolderItem;
	}
	public void setAsFolderItem(boolean value)
	{
		isFolderItem = value;
	}
	
}
