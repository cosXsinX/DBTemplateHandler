package com.mz.swing.tablelists;

import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;

import com.mz.swing.tablelists.components.BundleExplorerItemProvider;
import com.mz.swing.tablelists.components.BundleExplorerListCellRenderer;

public class BundleExplorerJList extends JList {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final BundleExplorerListCellRenderer listCellRenderer;
	
	public BundleExplorerJList(BundleExplorerItemProvider itemProvider)
	{
		this.listCellRenderer = new BundleExplorerListCellRenderer();
		setCellRenderer(this.listCellRenderer);
	}
	
	
}
