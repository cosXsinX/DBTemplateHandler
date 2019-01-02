package com.mz.swing.tablelists.components;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

public class BundleExplorerListCellRenderer extends DefaultListCellRenderer {
	

	public Component getListCellRendererComponent(JList list,
            Object value,
            int index,
            boolean isSelected,
            boolean hasFocus) {
		
		if(value instanceof FileBundleExplorerItem)
			return GetComponentForFileBundleExplorerItem((FileBundleExplorerItem)value);
		else if (value instanceof FolderBundleExplorerItem)
			return GetComponentForFolderBundleExplorerItem((FolderBundleExplorerItem)value);
		else if(value instanceof DefaultBundleExplorerItem)
			return GetDefaultComponentForBundleExplorerItem((DefaultBundleExplorerItem)value);
		JLabel label =
		(JLabel)super.getListCellRendererComponent(list,
		             value,
		             index,
		             isSelected,
		             hasFocus);
		return label;
	}
	
	private Component GetComponentForFileBundleExplorerItem(FileBundleExplorerItem item)
	{
		JPanel result =new JPanel();
		JCheckBox checkBox = new JCheckBox();
		JLabel label = new JLabel(item.getBundleFilePath());
		result.add(checkBox);
		result.add(label);
		return result;
	}
	
	private Component GetComponentForFolderBundleExplorerItem(FolderBundleExplorerItem item)
	{
		JPanel result = new JPanel();
		JButton button = new JButton("+");
		JLabel label = new JLabel(item.getBundleFilePath());
		result.add(button);
		result.add(label);
		return result;
	}
	
	private Component GetDefaultComponentForBundleExplorerItem(DefaultBundleExplorerItem item)
	{
		JPanel result = new JPanel();
		JLabel label = new JLabel(item.getBundleFilePath());
		result.add(label);
		return result;
	}
	
	

}
