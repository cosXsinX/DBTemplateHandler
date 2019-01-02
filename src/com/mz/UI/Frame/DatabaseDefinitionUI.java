package com.mz.UI.Frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;

import com.mz.UI.Frame.SubFrame.JInputDialog;
import com.mz.UI.Frame.SubFrame.JInputDialog.onOKClickListenerInterface;
import com.mz.UI.Frame.BundleSelectionUI.OnBundleSelectionDoneListener;
import com.mz.UI.Frame.TemplateSelectionUI.OnSelectionDoneListener;
import com.mz.UI.adapters.DatabaseDefinitionUITableAdapter;
import com.mz.UI.adapters.TableEntityDefinitionUITableAdapter;
import com.mz.UI.table.cell.editor.CheckBoxCellEditor;
import com.mz.UI.table.cell.editor.StringTableCellEditor;
import com.mz.UI.table.cell.editor.ValueTypeCellEditor;
import com.mz.UI.table.cell.renderer.BoldCellRenderer;
import com.mz.UI.table.cell.renderer.CheckBoxCellRenderer;
import com.mz.UI.table.cell.renderer.StringTableCellRenderer;
import com.mz.database.configuration.manager.databaseTemplateEditorConfigurationManager;
import com.mz.database.configuration.manager.databaseTemplateEditorPersistanceManager;
import com.mz.database.descriptors.AbstractDatabaseDescriptor;
import com.mz.database.descriptors.SQLLiteDatabaseDescriptor;
import com.mz.database.models.DatabaseDescriptionPOJO;
import com.mz.database.models.TableColumnDescriptionPOJO;
import com.mz.database.models.TableDescriptionPOJO;
import com.mz.database.template.handler.DatabaseTemplateHandler;
import com.mz.database.template.handler.FileTemplateHandlerNew;
import com.mz.database.template.handler.TableTemplateHandler;
import com.mz.database.template.handler.TemplateHandlerNew;
import com.mz.utilities.FileManager;

public class DatabaseDefinitionUI extends JFrame {

	private Logger LOGGER = Logger.getLogger(DatabaseDefinitionUI.class);
	
	
	private DatabaseDefinitionUITableAdapter adapter = new DatabaseDefinitionUITableAdapter();
    private JTable databaseDefinitionUITable;
    
    private TableEntityDefinitionUITableAdapter tableDefinitionUITableAdapter;
    private JTable databaseTableDefinitionUITable;
    
    private JTextField _templateSelectionTextField;

    AbstractDatabaseDescriptor _databaseDescriptor = new SQLLiteDatabaseDescriptor();
    
	public DatabaseDefinitionUI(DatabaseDescriptionPOJO associatedPOJO,
			AbstractDatabaseDescriptor databaseDescriptor){
		super();
		_databaseDescriptor = databaseDescriptor;
		
		setTitle("Database Template Editor");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel UITablePanel = new JPanel();
        adapter.setDatabaseDescriptionPOJO(associatedPOJO);
        databaseDefinitionUITable = new JTable(adapter);
        adapter.setJTableReference(databaseDefinitionUITable);
        databaseDefinitionUITable.setDefaultRenderer(String.class, new StringTableCellRenderer());
        databaseDefinitionUITable.setDefaultEditor(String.class, new StringTableCellEditor());
        if(_databaseDescriptor != null)
        {
        	databaseDefinitionUITable.getColumnModel().getColumn(1).
        		setCellEditor(new ValueTypeCellEditor(_databaseDescriptor.get_possibleColumnTypes()));
        }
        JScrollPane databaseDefinitionUITableScrollPane =new JScrollPane(databaseDefinitionUITable);
        UITablePanel.add(databaseDefinitionUITableScrollPane,BorderLayout.CENTER);
        databaseDefinitionUITable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        databaseDefinitionUITable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent listSelectionEvent) {
				int SelectedIndex = databaseDefinitionUITable.getSelectedRow();
				LOGGER.info("Selected index :" + SelectedIndex);
				TableDescriptionPOJO tableEntity = adapter.getEntityAtIndex(SelectedIndex);
				tableDefinitionUITableAdapter.setTableEntity(tableEntity);
				tableDefinitionUITableAdapter.fireTableDataChanged();
			}
		});
        
        tableDefinitionUITableAdapter = new TableEntityDefinitionUITableAdapter(null, adapter);

        databaseTableDefinitionUITable = new JTable(tableDefinitionUITableAdapter);

        databaseTableDefinitionUITable.setDefaultRenderer(String.class, new StringTableCellRenderer());

        databaseTableDefinitionUITable.setDefaultEditor(String.class, new StringTableCellEditor());
        databaseTableDefinitionUITable.getColumnModel().getColumn(1).setCellRenderer(new BoldCellRenderer());
        if(_databaseDescriptor != null)
        {
        	databaseTableDefinitionUITable.getColumnModel().getColumn(1).
        		setCellEditor(new ValueTypeCellEditor(_databaseDescriptor.get_possibleColumnTypes()));
        }
        databaseTableDefinitionUITable.getColumnModel().getColumn(2).setCellRenderer(new CheckBoxCellRenderer());
        databaseTableDefinitionUITable.getColumnModel().getColumn(2).setCellEditor(new CheckBoxCellEditor());
        databaseTableDefinitionUITable.getColumnModel().getColumn(3).setCellRenderer(new CheckBoxCellRenderer());
        databaseTableDefinitionUITable.getColumnModel().getColumn(3).setCellEditor(new CheckBoxCellEditor());
        JScrollPane databaseTableDefinitionUITableScrollPane =new JScrollPane(databaseTableDefinitionUITable);
        UITablePanel.add(databaseTableDefinitionUITableScrollPane,BorderLayout.EAST);
        getContentPane().add(UITablePanel, BorderLayout.CENTER);
        
        
		JPanel boutons = new JPanel();
		
		boutons.add(new JButton(new AddTableAction()));
        boutons.add(new JButton(new RemoveTableAction()));
        boutons.add(new JButton(new AddColumnAction()));
        boutons.add(new JButton(new RemoveColumnAction()));
        boutons.add(new JButton(new SaveAction()));
        boutons.add(new JButton(new LoadAction()));
        boutons.add(new JButton(new OpenTemplateBundlesEditorAction()));
        boutons.add(new JButton(new LaunchBundleFileGenerationAction()));
        
        getContentPane().add(boutons, BorderLayout.NORTH);
        _templateSelectionTextField = new JTextField("...");
        _templateSelectionTextField.setEditable(false);
        _templateSelectionTextField.setPreferredSize(new Dimension(500,20));
        
        
        JPanel TemplateSelectionPanel = new JPanel();
        TemplateSelectionPanel.add(_templateSelectionTextField);
        TemplateSelectionPanel.add(new JButton(new SelectAssociatedModelTemplatesAction()));
        TemplateSelectionPanel.add(new JButton(new LaunchTableTemplatedFileGenerationAction()));
		TemplateSelectionPanel.add(new JButton(new LaunchDatabaseTemplatedFileGeneration()));
        getContentPane().add(TemplateSelectionPanel,BorderLayout.SOUTH);
        
        pack();
	}
	
	
	private class AddTableAction extends AbstractAction {
        private AddTableAction() {
            super("Add Table");
        }

        public void actionPerformed(ActionEvent e) 
        {
        	//TODO
        	JInputDialog inputDialog = new JInputDialog("Table name", "Please enter the table name", 
        			new onOKClickListenerInterface() {
						@Override
						public void OnClick(String InputValue) {
							if(!adapter.AddTableEntityRow(InputValue))
							{
								JOptionPane.showMessageDialog(DatabaseDefinitionUI.this,
										"The seized table name is already present in the database definition",
										"Table name error",JOptionPane.ERROR_MESSAGE);
							}
						}
					}, null);
        	inputDialog.setVisible(true);
    
        }
    }

    private class RemoveTableAction extends AbstractAction {
        private RemoveTableAction() {
            super("Delete table");
        }

        public void actionPerformed(ActionEvent e) {
        	int[] selection = databaseDefinitionUITable.getSelectedRows();
            for(int i = selection.length - 1; i >= 0; i--){
                adapter.RemoveTableEntityRow(selection[i]);
            }
        }
    }
    
    private class SaveAction extends AbstractAction{
    	private SaveAction(){
    		super("Save");
    	}
    	
    	public void actionPerformed(ActionEvent e){
    		DatabaseDescriptionPOJO pojo = adapter.getDatabaseDescriptionPOJO();
    		databaseTemplateEditorPersistanceManager.
    			doPersistDatabaseModel(pojo,pojo.getDatabaseNameStr());
    	}
    }
    
    private class LoadAction extends AbstractAction{
    	private LoadAction(){
    		super("Load");
    	}
    	
    	public void actionPerformed(ActionEvent e){
    		DatabaseDescriptionPOJO pojo = databaseTemplateEditorPersistanceManager.
    				getPersistedDatabaseModel(adapter.getDatabaseDescriptionPOJO().getDatabaseNameStr());
    		if(pojo ==null) return;
    		adapter.setDatabaseDescriptionPOJO(pojo);
    		adapter.fireTableDataChanged();
    	}
    }
    
    private class AddColumnAction extends AbstractAction {
        private AddColumnAction() {
            super("Add column");
        }

        public void actionPerformed(ActionEvent e) {
        	tableDefinitionUITableAdapter.addColumn(new TableColumnDescriptionPOJO("COLUMN_NAME", "",false));
        }
    }

    private class RemoveColumnAction extends AbstractAction {
        private RemoveColumnAction() {
            super("Remove column");
        }

        public void actionPerformed(ActionEvent e) {
            int[] selection = databaseTableDefinitionUITable.getSelectedRows();
            for(int i = selection.length - 1; i >= 0; i--){
            	tableDefinitionUITableAdapter.removeColumn(selection[i]);
            }
        }
    }
    
    private class SelectAssociatedModelTemplatesAction extends AbstractAction{
    	private SelectAssociatedModelTemplatesAction(){
    		super("Select associated templates");
    	}
    	
    	public void actionPerformed(ActionEvent e){
    		final TemplateSelectionUI templateSelectionUI =  new TemplateSelectionUI();
    		templateSelectionUI.AddOnSelectionDoneListener(new OnSelectionDoneListener() {
				
				@Override
				public void onSelectionDone(String SelectedValueList) {
					_templateSelectionTextField.setText(SelectedValueList);
				}
			});
    		templateSelectionUI.setVisible(true);
    	}
    }
    
    private class OpenTemplateBundlesEditorAction extends AbstractAction{
    	private OpenTemplateBundlesEditorAction(){
    		super("Bundle editor");
    	}
    	
    	public void actionPerformed(ActionEvent e){
    		final BundleSelectionUI bundleSelectionUI =  new BundleSelectionUI();
    		bundleSelectionUI.AddOnSelectionDoneListener(new OnBundleSelectionDoneListener() {
				
				@Override
				public void onSelectionDone(String SelectedValueList) {
					_templateSelectionTextField.setText(SelectedValueList);
				}
			});
    		bundleSelectionUI.setVisible(true);
    	}
    }
    
    private class LaunchBundleFileGenerationAction extends AbstractAction{
    	private LaunchBundleFileGenerationAction(){
    		super("Bundle file generation");
    	}
    	
    	public void actionPerformed(ActionEvent e){
    		final BundleGenerationUI bundleGenerationUI = new BundleGenerationUI();
    		DatabaseDescriptionPOJO databaseDescriptionPojo = adapter.getDatabaseDescriptionPOJO();
    		bundleGenerationUI.setDatabaseDescriptionPOJO(databaseDescriptionPojo);
    		bundleGenerationUI.setVisible(true);
    	}
    }
    
    private String[] getSelectedTemplateFileNames(){
    	String templateSelectionTextFieldText = _templateSelectionTextField.getText();
		if(templateSelectionTextFieldText.equals("...") || templateSelectionTextFieldText.equals("")) return null;
		String[] splittedTemplateSelectionTextField =
				templateSelectionTextFieldText.split(";");
		return splittedTemplateSelectionTextField;
    }
    
    
    private class LaunchTableTemplatedFileGenerationAction extends AbstractAction{
    	private LaunchTableTemplatedFileGenerationAction(){
    		super("Generate table file");
    	}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(tableDefinitionUITableAdapter == null) return;
			TableDescriptionPOJO tableEntity = tableDefinitionUITableAdapter.getTableEntity();
			if(tableEntity == null) return; //TODO add error message
			//TableTemplateHandler templateHandler = TableTemplateHandler.TableDescriptionPOJOToTableTemplateHandler(tableEntity,_databaseDescriptor);
			//if(templateHandler == null) return;
			FileTemplateHandlerNew templateHandlerNew = new 
					FileTemplateHandlerNew();
			
			
			
			String[] splittedTemplateSelectionTextField =getSelectedTemplateFileNames();
			if(splittedTemplateSelectionTextField == null) return;
			
			int currentIndexSplitted;
			String currentSplittedValueStr;
			String templateFolderPathStr = databaseTemplateEditorConfigurationManager.get_templateDirectoryRootFolderPathStr();
			for(currentIndexSplitted = 0; currentIndexSplitted<splittedTemplateSelectionTextField.length; currentIndexSplitted ++){
				currentSplittedValueStr = splittedTemplateSelectionTextField[currentIndexSplitted];
				File currentTemplateFile = new File(templateFolderPathStr+currentSplittedValueStr);
				try {
					templateHandlerNew.set_outpuFolderPath(databaseTemplateEditorConfigurationManager.
							get_generatedFileFromTemplateDirectoryPathStr());
					templateHandlerNew.GenerateTableTemplateFiles(currentTemplateFile.getAbsolutePath(), "NEW" + File.separator + currentTemplateFile.getName(), tableEntity);
					//templateHandler.generateTableFileFromTemplateFile(currentTemplateFile);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
    }
    
    private class LaunchDatabaseTemplatedFileGeneration extends AbstractAction
    {
    	public LaunchDatabaseTemplatedFileGeneration()
    	{
    		super("Generate database file");
    	}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(adapter== null) return;
			DatabaseDescriptionPOJO pojo = adapter.getDatabaseDescriptionPOJO();
			//DatabaseTemplateHandler templateHandler = new DatabaseTemplateHandler(pojo,_databaseDescriptor);
			FileTemplateHandlerNew templateHandlerNew = new FileTemplateHandlerNew();
			
			
			String[] splittedTemplateSelectionTextField =getSelectedTemplateFileNames();
			if(splittedTemplateSelectionTextField == null) return;
			
			int currentIndexSplitted;
			String currentSplittedValueStr;
			String templateFolderPathStr = databaseTemplateEditorConfigurationManager.get_templateDirectoryRootFolderPathStr();
			for(currentIndexSplitted = 0; currentIndexSplitted<splittedTemplateSelectionTextField.length; currentIndexSplitted ++){
				currentSplittedValueStr = splittedTemplateSelectionTextField[currentIndexSplitted];
				File currentTemplateFile = new File(templateFolderPathStr+currentSplittedValueStr);
				try {
					templateHandlerNew.set_outpuFolderPath(databaseTemplateEditorConfigurationManager.
							get_generatedFileFromTemplateDirectoryPathStr());
					templateHandlerNew.GenerateDatabaseTemplateFiles(
							currentTemplateFile.getAbsolutePath(),
							"NEW" + File.separator + currentTemplateFile.getName(),
							pojo);
					//templateHandler.GenerateDatabaseFilesFromTemplateFile(currentTemplateFile);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
    }
    
    private void FireTableChanged()
    {
    	int[] selection = databaseDefinitionUITable.getSelectedRows();
        adapter.fireTableDataChanged();
        if(!(selection.length>0)) return;
        databaseDefinitionUITable.setRowSelectionInterval(selection[0], selection[0]);
    }
}
