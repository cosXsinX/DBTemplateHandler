package com.mz.UI.Frame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;

import com.mz.database.configuration.manager.databaseTemplateEditorBundleTemplateManager;
import com.mz.database.models.DatabaseDescriptionPOJO;
import com.mz.database.template.handler.BundleTemplateHandler;

public class BundleGenerationUI extends JFrame {
	private Logger LOGGER = Logger.getLogger(BundleGenerationUI.class);
	
	private final static int AVAILABLE_BUNDLE_TABLE_WIDTH = 300;
	private final static int AVAILABLE_BUNDLE_TABLE_HEIGHT = 400;
	
	private final static String VALIDATE_BUTTON_LABEL_CONTENT = "Validate";
	private final static String CANCEL_BUTTON_LABEL_CONTENT = "Cancel";
	
	private final JList<String> _availableBundleList;
	private final DefaultListModel<String> _availableBundleListModel;
	
	private final JList<String> _containedFileInSelectedBundleList;
	private final DefaultListModel<String> _containedFileInSelectedBundleListModel;
	
	private final JButton _validateButton ;
	private final JButton _cancelButton;
	
	private DatabaseDescriptionPOJO databaseDescriptionPojo;
	public void setDatabaseDescriptionPOJO(DatabaseDescriptionPOJO value)
	{
		this.databaseDescriptionPojo = value;
	}
	
	public DatabaseDescriptionPOJO getDatabaseDescriptionPOJO()
	{
		return this.databaseDescriptionPojo;
	}
	
	public BundleGenerationUI(){
		
		setTitle("Database Template Editor");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel UITablePanel = new JPanel();
        _availableBundleListModel = new DefaultListModel<String>();
        List<String> bundleList = databaseTemplateEditorBundleTemplateManager.ListBundles();
        
        for(String currentBundle : bundleList) _availableBundleListModel.addElement(currentBundle);
        _availableBundleList = new JList<String>(_availableBundleListModel);
        _availableBundleList.setSelectionModel(new SelectUnselectLisetSelectionModel());
        _availableBundleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        _availableBundleList.setCellRenderer(new SelectionCellRenderer());
        _availableBundleList.setPreferredSize(new Dimension(AVAILABLE_BUNDLE_TABLE_WIDTH, AVAILABLE_BUNDLE_TABLE_HEIGHT));
        _availableBundleList.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				JList lsm = (JList)e.getSource();
				int firstIndex = e.getFirstIndex();
				String bundleName = GetSelectedBundle(firstIndex,lsm);
		        FillContainedFileInSelectedBundleList(bundleName);
			}
		});
        UITablePanel.add(_availableBundleList,BorderLayout.CENTER);
        
        _containedFileInSelectedBundleListModel = new DefaultListModel<String>();
        _containedFileInSelectedBundleList = new JList<String>(_containedFileInSelectedBundleListModel);
        
        _containedFileInSelectedBundleList.setSelectionModel(new SelectUnselectLisetSelectionModel());
        _containedFileInSelectedBundleList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        _containedFileInSelectedBundleList.setCellRenderer(new SelectionCellRenderer());
        JScrollPane containedFileInSelectionBundleListScrollPane = new JScrollPane( _containedFileInSelectedBundleList);
        containedFileInSelectionBundleListScrollPane.setPreferredSize(new Dimension(AVAILABLE_BUNDLE_TABLE_WIDTH, AVAILABLE_BUNDLE_TABLE_HEIGHT));
        UITablePanel.add(containedFileInSelectionBundleListScrollPane,BorderLayout.CENTER);
        
        _validateButton = new JButton(VALIDATE_BUTTON_LABEL_CONTENT);
        _validateButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String bundleName = GetSelectedBundle(_availableBundleList.getSelectedIndex(),_availableBundleList);
				
				SelectedBundleParameterResult SelectionResult = new SelectedBundleParameterResult();
				System.out.println(bundleName);
				SelectionResult.setBundleName(bundleName);
				List<String> SelectedContainedFileInSelectedBundle = _containedFileInSelectedBundleList.getSelectedValuesList();
				for(String current : SelectedContainedFileInSelectedBundle) System.out.println(current);
				SelectionResult.AddSelectedBundleInternalFileReference(SelectedContainedFileInSelectedBundle);
				//TODO Call file browser to get destination file path
				
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new java.io.File(".")); // start at application current directory
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fc.showSaveDialog(BundleGenerationUI.this);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
				    File Folder = fc.getSelectedFile();
				    JOptionPane.showMessageDialog(BundleGenerationUI.this, Folder.getPath());
				    BundleTemplateHandler handler = new BundleTemplateHandler();
				    DatabaseDescriptionPOJO databaseDescriptionPOJO =  BundleGenerationUI.this.getDatabaseDescriptionPOJO();
				    handler.GenerateBundleFiles( Folder.getPath(), bundleName, SelectedContainedFileInSelectedBundle, databaseDescriptionPOJO);
				}
			}
		});
        
        _cancelButton = new JButton(CANCEL_BUTTON_LABEL_CONTENT);
        _cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				BundleGenerationUI.this.dispose();
			}
		});
        JPanel buttons = new JPanel();
        buttons.add(_cancelButton,BorderLayout.WEST);
        buttons.add(_validateButton,BorderLayout.EAST);
        this.add(UITablePanel,BorderLayout.CENTER);
        this.add(buttons,BorderLayout.SOUTH);
       	pack();
	}
	
	private String GetSelectedBundle(int selectedIndex,JList list)
	{
        System.out.append(" \n");
        String bundleName = null;
        if(!list.isSelectionEmpty())
        	bundleName= _availableBundleListModel.elementAt(selectedIndex);
        return bundleName;
	}
	
	private void FillContainedFileInSelectedBundleList(String BundleName)
	{
		_containedFileInSelectedBundleListModel.clear();
		List<String> bundleFiles = databaseTemplateEditorBundleTemplateManager.ListBundleFiles(BundleName);
		for(String currentBundleFile : bundleFiles) 
		{
			if(currentBundleFile.endsWith(File.separator)) continue;
			_containedFileInSelectedBundleListModel.addElement(currentBundleFile);
		}
	}
	
	public class SelectionCellRenderer extends JPanel implements ListCellRenderer<String>
	{
		private class CellRendererWrapper
		{
			private final JList<? extends String> target;
			public final JPanel mainContainerPanel = new JPanel();
			public final JCheckBox checkBox = new JCheckBox();
			public final JLabel label = new JLabel();
			
			public CellRendererWrapper(JList<? extends String> list,String labelValue)
			{
				this.target = list;
				mainContainerPanel.setLayout(new BorderLayout(10,10));
				mainContainerPanel.add(checkBox,BorderLayout.WEST);
				mainContainerPanel.add(label,BorderLayout.CENTER);
				label.setText(labelValue);
			}
			
			public void setSelected(boolean isSelected)
			{
				if(isSelected)
				{
					mainContainerPanel.setBackground(target.getSelectionBackground());
				}
				else 
				{
					mainContainerPanel.setBackground(target.getBackground());
				}
				checkBox.setSelected(isSelected);
			}
		}
		private HashMap<String, CellRendererWrapper> CellRendererWrapperHashMap = new HashMap<String,CellRendererWrapper>();
		@Override
		public Component getListCellRendererComponent(JList<? extends String> list, String value, int index,
				boolean isSelected, boolean cellHasFocus) {
			if(!CellRendererWrapperHashMap.containsKey(value))
			{
				CellRendererWrapperHashMap.put(value, new CellRendererWrapper(list,value));
			}
			CellRendererWrapper wrapper = CellRendererWrapperHashMap.get(value);
			wrapper.setSelected(isSelected);
			return wrapper.mainContainerPanel;
		}	
	}
	
	public class SelectUnselectLisetSelectionModel extends DefaultListSelectionModel
	{
		private boolean mGestureStarted;

	    @Override
	    public void setSelectionInterval(int index0, int index1) {
	        // Toggle only one element while the user is dragging the mouse
	        if (!mGestureStarted) {
	            if (isSelectedIndex(index0)) {
	                super.removeSelectionInterval(index0, index1);
	            } else {
	                if (getSelectionMode() == SINGLE_SELECTION) {
	                    super.setSelectionInterval(index0, index1);
	                } else {
	                    super.addSelectionInterval(index0, index1);
	                }
	            }
	        }

	        // Disable toggling till the adjusting is over, or keep it
	        // enabled in case setSelectionInterval was called directly.
	        mGestureStarted = getValueIsAdjusting();
	    }

	    @Override
	    public void setValueIsAdjusting(boolean isAdjusting) {
	        super.setValueIsAdjusting(isAdjusting);

	        if (isAdjusting == false) {
	            // Enable toggling
	            mGestureStarted = false;
	        }
	    }  
	}
	
	public class SelectedBundleParameterResult
	{
		private String _BundleName;
		public String getBundleName()
		{
			return _BundleName;
		}
		public void setBundleName(String value)
		{
			_BundleName = value;
		}
		
		private final List<String> _SelectedBundleInternalFileList = new ArrayList<String>();
		public void AddSelectedBundleInternalFileReference(String SelectedBundleInternalFile)
		{
			_SelectedBundleInternalFileList.add(SelectedBundleInternalFile);
		}
		public void AddSelectedBundleInternalFileReference(List<String> SelectedBundleInternalFileList)
		{
			for(String SelectedBundleInternalFile : SelectedBundleInternalFileList)
			{
				AddSelectedBundleInternalFileReference(SelectedBundleInternalFile);
			}
		}
		
		public void ClearSelectedBundleInternalFileReference()
		{
			_SelectedBundleInternalFileList.clear();
		}
		
	}
}
