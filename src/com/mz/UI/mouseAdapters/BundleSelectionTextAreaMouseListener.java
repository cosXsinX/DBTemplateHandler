package com.mz.UI.mouseAdapters;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.mz.UI.Frame.context.menu.DefaultContextMenu;
import com.mz.UI.popup.BundleManagementPopup;

public class BundleSelectionTextAreaMouseListener extends MouseAdapter
{
	DefaultContextMenu popup;
	
//	public String getAssociatedBundleName()
//	{
//		if (popup == null) return null;
//		return this.popup.getAssociatedBundleName();
//	}
//	public void setAssociatedBundleName(String value)
//	{
//		if (popup == null) return;
//		this.popup.setAssociatedBundleName(value);
//	}
//	
//	public String getAssociatedBundleFileOrFolderPath()
//	{
//		if(popup == null) return null;
//		return this.popup.getAssociatedBundleFileOrFolderPath();
//	}
//	
//	public void setAssociatedBundleFileOrFolderPath(String value)
//	{
//		if(popup == null) return;
//		this.popup.setAssociatedBundleFileOrFolderPath(value);
//	}
	
	public BundleSelectionTextAreaMouseListener(DefaultContextMenu popup)
	{
		this.popup = popup;
	}
	
	@Override
    public void mousePressed(MouseEvent e) {
        showPopup(e);
    }
	
    private void showPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            popup.show(e.getComponent(),
                e.getX(), e.getY());
        }
	}


}