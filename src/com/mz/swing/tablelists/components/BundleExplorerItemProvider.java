package com.mz.swing.tablelists.components;

import java.util.ArrayList;
import java.util.List;

import com.mz.database.configuration.manager.databaseTemplateEditorBundleTemplateManager;

public class BundleExplorerItemProvider {

	public List<BundleExplorerItem> GetAvailableBundleItems()
	{
		List<BundleExplorerItem> result = new ArrayList<BundleExplorerItem>();
		List<String> bundleList = databaseTemplateEditorBundleTemplateManager.ListBundles();
		if(bundleList == null) return result;
		for(int i = 0 ; i<bundleList.size();i++)
		{
			BundleExplorerItem resultItem = new BundleExplorerItem();
			resultItem.setBundleName(bundleList.get(i));
		}
		return result;
	}
}
