package com.mz.UI.mouseAdapters;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.mz.UI.popup.BundleManagementPopup;

public class BundleTableViewMouseListener extends MouseAdapter
{
	BundleManagementPopup popup;
	
	public String getAssociatedBundleName()
	{
		if (popup == null) return null;
		return this.popup.getAssociatedBundleName();
	}
	public void setAssociatedBundleName(String value)
	{
		if (popup == null) return;
		this.popup.setAssociatedBundleName(value);
	}
	
	public String getAssociatedBundleFileOrFolderPath()
	{
		if(popup == null) return null;
		return this.popup.getAssociatedBundleFileOrFolderPath();
	}
	
	public void setAssociatedBundleFileOrFolderPath(String value)
	{
		if(popup == null) return;
		this.popup.setAssociatedBundleFileOrFolderPath(value);
	}
	
	public BundleTableViewMouseListener(BundleManagementPopup popup)
	{
		this.popup = popup;
	}
	
	@Override
    public void mousePressed(MouseEvent e) {
        showPopup(e);
    }
	
	@Override
	public void mouseReleased(MouseEvent e)
	{
		showPopup(e);
	}
    private void showPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            popup.show(e.getComponent(),
                e.getX(), e.getY());
        }
	}


}