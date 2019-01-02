package com.mz.UI.Frame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.mz.UI.MainClass;
import com.mz.UI.Frame.SubFrame.JInputDialog;
import com.mz.UI.Frame.SubFrame.JInputDialog.onOKClickListenerInterface;
import com.mz.UI.adapters.EveryDatabaseDefinitionUITableAdapter;
import com.mz.UI.table.cell.editor.StringTableCellEditor;
import com.mz.UI.table.cell.renderer.StringTableCellRenderer;
import com.mz.database.configuration.manager.databaseTemplateEditorPersistanceManager;
import com.mz.database.descriptors.AbstractDatabaseDescriptor;
import com.mz.database.descriptors.SQLLiteDatabaseDescriptor;

public class EveryDatabaseModelManagementUI extends JFrame {

	private EveryDatabaseDefinitionUITableAdapter everyDatabaseAdapter = 
			new EveryDatabaseDefinitionUITableAdapter();
	private JTable everyDatabaseDefinitionUITable;
	
	private String SelectedValueStr;

    AbstractDatabaseDescriptor _databaseDescriptor = new SQLLiteDatabaseDescriptor();
    
	public EveryDatabaseModelManagementUI(AbstractDatabaseDescriptor databaseDescriptor){
		super();
		_databaseDescriptor = databaseDescriptor;
		
		setTitle("Database Template Editor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel UITablePanel = new JPanel();
        everyDatabaseAdapter.SetValues(databaseTemplateEditorPersistanceManager.ListEveryAvailableKey());
        everyDatabaseDefinitionUITable = new JTable(everyDatabaseAdapter);
        everyDatabaseDefinitionUITable.setDefaultRenderer(String.class, new StringTableCellRenderer());
        everyDatabaseDefinitionUITable.setDefaultEditor(String.class, new StringTableCellEditor());
        everyDatabaseDefinitionUITable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        everyDatabaseDefinitionUITable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent listSelectionEvent) {
				int SelectedIndex = everyDatabaseDefinitionUITable.getSelectedRow();
				String value = everyDatabaseAdapter.getEntityAtIndex(SelectedIndex);
				SelectedValueStr = value;
			}
		});
        JScrollPane everyDatabaseTableDefinitionUITableScrollPane =new JScrollPane(everyDatabaseDefinitionUITable);
        UITablePanel.add(everyDatabaseTableDefinitionUITableScrollPane,BorderLayout.EAST);
        getContentPane().add(UITablePanel, BorderLayout.CENTER);
        
		JPanel boutons = new JPanel();
		
        boutons.add(new JButton(new ShowAction()));
        boutons.add(new JButton(new AddAction()));
        boutons.add(new JButton(new RemoveAction()));
        boutons.add(new JButton(new ImportFromDatabaseAction()));
        

        getContentPane().add(boutons, BorderLayout.SOUTH);
		pack();
	}
    
    private class ShowAction extends AbstractAction {
        private ShowAction() {
            super("Show");
        }

        public void actionPerformed(ActionEvent e) {
        	int selectionRowInt = everyDatabaseDefinitionUITable.getSelectedRow();
        	int selectionColumnInt = everyDatabaseDefinitionUITable.getSelectedColumn();
        	String SelectedValueKey =
        			(String)everyDatabaseAdapter.getValueAt(selectionRowInt, selectionColumnInt);
        	MainClass.ShowDatabaseDefinitionUI(SelectedValueKey);
        }
    }
    
    private class AddAction extends AbstractAction {
        private AddAction() {
            super("Add");
            
        }

        public void actionPerformed(ActionEvent e) {
        	//TODO
        	JInputDialog inputDialog = new JInputDialog("Database name", "Please enter the database name", 
        			new onOKClickListenerInterface() {
						@Override
						public void OnClick(String InputValue) {
							if(!MainClass.CreateNewDatabaseDescriptionAndShowDefinitionUI(InputValue))
							{
								JOptionPane.showMessageDialog(EveryDatabaseModelManagementUI.this,
										"The seized database name is already present in the database definitions",
										"Database name error",JOptionPane.ERROR_MESSAGE);
							}
							else
							{
								EveryDatabaseModelManagementUI.this.everyDatabaseAdapter.SetValues(databaseTemplateEditorPersistanceManager.ListEveryAvailableKey());
								EveryDatabaseModelManagementUI.this.everyDatabaseAdapter.fireTableDataChanged();
							}
						}
					}, null);
        	inputDialog.setVisible(true);
        	
        }
    }

    private class RemoveAction extends AbstractAction {
        private RemoveAction() {
            super("Remove");
        }

        public void actionPerformed(ActionEvent e) {
            if(MainClass.DeleteDatabaseModelAndDisposeAssociatedFrameIfNeeded (SelectedValueStr))
            {
	            EveryDatabaseModelManagementUI.this.everyDatabaseAdapter.SetValues(databaseTemplateEditorPersistanceManager.ListEveryAvailableKey());
				EveryDatabaseModelManagementUI.this.everyDatabaseAdapter.fireTableDataChanged();
            }
        }
    }
    
    private class ImportFromDatabaseAction extends AbstractAction{
    	private ImportFromDatabaseAction(){
    		super("Import from DB");
    	}
    	
		@Override
		public void actionPerformed(ActionEvent arg0) {
			ImportDatabaseDefinitionFrame importFrame = new ImportDatabaseDefinitionFrame();
			importFrame.setVisible(true);
			
		}
	}
    
    
}
