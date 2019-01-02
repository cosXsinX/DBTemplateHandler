package com.mz.UI.popup;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import com.mz.UI.Frame.BundleSelectionUI;
import com.mz.UI.Frame.SubFrame.JInputDialog;
import com.mz.database.configuration.manager.databaseTemplateEditorBundleTemplateManager;
import com.mz.utilities.PathManager;

public class BundleManagementPopup extends JPopupMenu {

	BundleSelectionUI _associatedFrame;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JMenuItem addBundleMenuItem = new JMenuItem("Create new bundle");
	JMenuItem importBundleMenuItem = new JMenuItem("Import bundle");
	JMenuItem deleteBundleMenuItem = new JMenuItem("Delete bundle");
	JMenuItem addFileToBundleMenuItem = new JMenuItem("Add file");
	JMenuItem deleteFileFromBundleMenuItem = new JMenuItem("Delete file");
	JMenuItem addFolderToBundleMenuItem = new JMenuItem("Add folder");
	JMenuItem deleteFolderFromBundleMenuItem = new JMenuItem("Delete folder");
	JMenuItem refreshViewMenuItem = new JMenuItem("Refresh");
	
	String _associatedBundleName;
	public String getAssociatedBundleName()
	{
		return _associatedBundleName;
	}
	public void setAssociatedBundleName(String value)
	{
		_associatedBundleName = value;
	}
	
	String _associatedBundleFileOrFolderPath;
	public String getAssociatedBundleFileOrFolderPath()
	{
		return _associatedBundleFileOrFolderPath;
	}
	
	public void setAssociatedBundleFileOrFolderPath(String value)
	{
		_associatedBundleFileOrFolderPath = value;
	}
	
	public BundleManagementPopup(BundleSelectionUI associatedFrame)
	{
		this._associatedFrame = associatedFrame;
		
		this.addBundleMenuItem.addActionListener(addBundleMenuItemAction);
		this.add(this.addBundleMenuItem);
		
		this.importBundleMenuItem.addActionListener(importBundleMenuItemAction);
		this.add(this.importBundleMenuItem);
		
		this.deleteBundleMenuItem.addActionListener(deleteBundleMenuItemAction);
		this.add(this.deleteBundleMenuItem);
		
		this.addFileToBundleMenuItem.addActionListener(addFileToBundleMenuItemAction);
		this.add(this.addFileToBundleMenuItem);
		
		this.deleteFileFromBundleMenuItem.addActionListener(deleteFileFromBundleMenuItemAction);
		this.add(this.deleteFileFromBundleMenuItem);
		
		this.addFolderToBundleMenuItem.addActionListener(addFolderToBundleMenuItemAction);
		this.add(this.addFolderToBundleMenuItem);
		
		this.deleteFolderFromBundleMenuItem.addActionListener(deleteFolderFromBundleMenuAction);
		this.add(this.deleteFolderFromBundleMenuItem);
		
		this.refreshViewMenuItem.addActionListener(refreshViewMenuItemAction);
		this.add(this.refreshViewMenuItem);
	}
	
	Action addBundleMenuItemAction = new AbstractAction() {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JInputDialog inputDialog = new JInputDialog("Bundle name input", "Write the created bundle name :",
					new com.mz.UI.Frame.SubFrame.JInputDialog.onOKClickListenerInterface() {
						
						@Override
						public void OnClick(String InputValue) {
							if(InputValue == null || InputValue.isEmpty())
							{
								JOptionPane.showMessageDialog(BundleManagementPopup.this._associatedFrame,
										"There is no seized bundle name",
										"Bundle name error",JOptionPane.ERROR_MESSAGE);
								return;
							}
							
							if(!databaseTemplateEditorBundleTemplateManager.CreateBundle(InputValue))
							{
								JOptionPane.showMessageDialog(BundleManagementPopup.this._associatedFrame,
										"The seized bundle name is already present in the database definition",
										"Bundle name error",JOptionPane.ERROR_MESSAGE);
								return;
							}
							
							JOptionPane.showMessageDialog(BundleManagementPopup.this._associatedFrame,
									"The bundle was created","Bundle created",JOptionPane.DEFAULT_OPTION);
							
							BundleManagementPopup.this._associatedFrame.RefreshAvailableTemplateUITable();
							
						}
					},
					new com.mz.UI.Frame.SubFrame.JInputDialog.onCancelListenerInterface() {
						
						@Override
						public void OnCancel() {
							return;
						}
					});
			inputDialog.setVisible(true);
		}
	};
	
	Action importBundleMenuItemAction = new AbstractAction(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			
			
			
		}
	};
	
	Action deleteBundleMenuItemAction = new AbstractAction(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if(_associatedBundleName == null || _associatedBundleName.isEmpty())
			{
				JOptionPane.showMessageDialog(BundleManagementPopup.this._associatedFrame,
						"Not selected bundle to delete",
						"Bundle name error",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(!databaseTemplateEditorBundleTemplateManager.
				DeleteBundle(BundleManagementPopup.this._associatedBundleName))
			{
				JOptionPane.showMessageDialog(BundleManagementPopup.this._associatedFrame,
						"Deleted bundle with the name does not exist any more",
						"Bundle name error",JOptionPane.ERROR_MESSAGE);
			}
			BundleManagementPopup.this._associatedFrame.RefreshAvailableTemplateUITable();
		}
	};
	
	Action addFileToBundleMenuItemAction = new AbstractAction(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if(_associatedBundleName == null || _associatedBundleName.isEmpty())
			{
				JOptionPane.showMessageDialog(BundleManagementPopup.this._associatedFrame,
						"Not selected destination bundle to add file",
						"Bundle name error",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			JInputDialog inputDialog = new JInputDialog("Bundle name input", "Write the created bundle name :",
					new com.mz.UI.Frame.SubFrame.JInputDialog.onOKClickListenerInterface() {
						
						@Override
						public void OnClick(String InputValue) {
							if(InputValue == null || InputValue.isEmpty())
							{
								JOptionPane.showMessageDialog(BundleManagementPopup.this._associatedFrame,
										"There is no seized file name",
										"Bundle name error",JOptionPane.ERROR_MESSAGE);
								return;
							}
							
							String CreatedBundleFilePath = null;
							if(databaseTemplateEditorBundleTemplateManager.IsFolderInBundle(BundleManagementPopup.this._associatedBundleFileOrFolderPath, BundleManagementPopup.this._associatedBundleName))
							{
								CreatedBundleFilePath =   BundleManagementPopup.this._associatedBundleFileOrFolderPath+ InputValue;
							}
							else
							{
								String ParentPath = BundleManagementPopup.this._associatedBundleFileOrFolderPath;
								ParentPath = ParentPath.substring(0, ParentPath.lastIndexOf(File.separator));
								CreatedBundleFilePath = ParentPath + File.separator + InputValue;
								
							}
							if(!databaseTemplateEditorBundleTemplateManager.AddFileToBundle(
									CreatedBundleFilePath, 
										BundleManagementPopup.this._associatedBundleName))
							{
								JOptionPane.showMessageDialog(BundleManagementPopup.this._associatedFrame,
										"The file creation error",
										"File creation error",JOptionPane.ERROR_MESSAGE);
								return;
							}
							
							JOptionPane.showMessageDialog(BundleManagementPopup.this._associatedFrame,
									"The file were created file",
									"File creation success",JOptionPane.DEFAULT_OPTION);
							
							_associatedFrame.RefreshBundleValueInBundleTemplateUITable(BundleManagementPopup.this._associatedBundleName);
							
						}
					},
					new com.mz.UI.Frame.SubFrame.JInputDialog.onCancelListenerInterface() {
						@Override
						public void OnCancel() {
							return;
						}
					});
			inputDialog.setVisible(true);
		}
	};
	
	Action deleteFileFromBundleMenuItemAction = new AbstractAction(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if(_associatedBundleName == null || _associatedBundleName.isEmpty())
			{
				JOptionPane.showMessageDialog(BundleManagementPopup.this._associatedFrame,
						"Not selected destination bundle to remove file from",
						"Bundle name error",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(_associatedBundleFileOrFolderPath == null || _associatedBundleFileOrFolderPath.isEmpty())
			{
				JOptionPane.showMessageDialog(BundleManagementPopup.this._associatedFrame,
						"Not selected bundle internal file path to delete",
						"Internal file path error",JOptionPane.ERROR_MESSAGE);
				return;
			}
			databaseTemplateEditorBundleTemplateManager.
				RemoveFileFromBundle(_associatedBundleFileOrFolderPath, _associatedBundleName);
			_associatedFrame.RefreshBundleValueInBundleTemplateUITable(_associatedBundleName);
		}
	};
	
	Action addFolderToBundleMenuItemAction = new AbstractAction(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(_associatedBundleName == null || _associatedBundleName.isEmpty())
			{
				JOptionPane.showMessageDialog(BundleManagementPopup.this._associatedFrame,
						"Not selected destination bundle to add folder in",
						"Bundle name error",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			JInputDialog inputDialog = new JInputDialog("Bundle internal folder name input", "Write the created bundle internal folder name :",
					new com.mz.UI.Frame.SubFrame.JInputDialog.onOKClickListenerInterface() {
						
						@Override
						public void OnClick(String InputValue) {
							if(InputValue == null || InputValue.isEmpty())
							{
								JOptionPane.showMessageDialog(BundleManagementPopup.this._associatedFrame,
										"There is no seized folder name",
										"Bundle Folder name error",JOptionPane.ERROR_MESSAGE);
								return;
							}
							String addedInternalFolderPath = InputValue;
							if(BundleManagementPopup.this._associatedBundleFileOrFolderPath != null)
							{
								String parentDirectoryPath = BundleManagementPopup.this._associatedBundleFileOrFolderPath;
								if(databaseTemplateEditorBundleTemplateManager.IsFolderInBundle(parentDirectoryPath, _associatedBundleName))
								{
									addedInternalFolderPath = parentDirectoryPath + InputValue;
								}
								else
								{
									addedInternalFolderPath = parentDirectoryPath.substring(0, parentDirectoryPath.lastIndexOf(File.separator)) 
											+ File.separator + InputValue;
									
								}
							}
							
							if(databaseTemplateEditorBundleTemplateManager.IsFileExistInBundle(addedInternalFolderPath, _associatedBundleName))
							{
								JOptionPane.showMessageDialog(BundleManagementPopup.this._associatedFrame,
										"The folder '" + addedInternalFolderPath + "' already exist in bundle : '" +_associatedBundleName+ "'",
										"Bundle Folder name error",JOptionPane.ERROR_MESSAGE);
								return;
							}
							
							if(!databaseTemplateEditorBundleTemplateManager.AddFolderToBundle(
									addedInternalFolderPath, 
										BundleManagementPopup.this._associatedBundleName))
							{
								JOptionPane.showMessageDialog(BundleManagementPopup.this._associatedFrame,
										"The file creation error",
										"File creation error",JOptionPane.ERROR_MESSAGE);
								return;
							}
							
							JOptionPane.showMessageDialog(BundleManagementPopup.this._associatedFrame,
									"The file were created file",
									"File creation success",JOptionPane.DEFAULT_OPTION);
							
							_associatedFrame.RefreshBundleValueInBundleTemplateUITable(BundleManagementPopup.this._associatedBundleName);
							
						}
					},
					new com.mz.UI.Frame.SubFrame.JInputDialog.onCancelListenerInterface() {
						@Override
						public void OnCancel() {
							return;
						}
					});
			inputDialog.setVisible(true);
		}
	};
	
	Action deleteFolderFromBundleMenuAction = new AbstractAction(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if(_associatedBundleName == null || _associatedBundleName.isEmpty())
			{
				JOptionPane.showMessageDialog(BundleManagementPopup.this._associatedFrame,
						"Not selected destination bundle to remove file from",
						"Bundle name error",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(_associatedBundleFileOrFolderPath == null || _associatedBundleFileOrFolderPath.isEmpty())
			{
				JOptionPane.showMessageDialog(BundleManagementPopup.this._associatedFrame,
						"Not selected bundle internal file path to delete",
						"Internal file path error",JOptionPane.ERROR_MESSAGE);
				return;
			}
			databaseTemplateEditorBundleTemplateManager.
				RemoveFileFromBundle(_associatedBundleFileOrFolderPath, _associatedBundleName);
			_associatedFrame.RefreshBundleValueInBundleTemplateUITable(_associatedBundleName);
		}
	};
	
	Action refreshViewMenuItemAction = new AbstractAction(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if(_associatedBundleName == null) return ;
			_associatedFrame.RefreshBundleValueInBundleTemplateUITable(_associatedBundleName);
		}
	};
	
	
}


