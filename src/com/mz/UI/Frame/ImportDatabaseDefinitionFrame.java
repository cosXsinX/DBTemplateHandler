package com.mz.UI.Frame;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.beans.PersistenceDelegate;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout.Constraints;

import com.mz.database.configuration.manager.databaseTemplateEditorPersistanceManager;
import com.mz.database.models.DatabaseDescriptionPOJO;
import com.mz.database.structure.loaders.manager.DatabaseStructureLoaderManager;

public class ImportDatabaseDefinitionFrame extends JFrame {

	private final static String USER_NAME_TEXT_FIELD_LABEL = "User name";
	private final static String PASSWORD_TEXT_FIELD_LABEL = "Password";
	private final static String DATABASE_NAME_TEXT_FIELD_LABEL = "Database name";
	private final static String SERVER_NAME_TEXT_FIELD_LABEL = "Server name";
	
	private final static String USER_NAME_TEXT_FIELD_DEFAULT_VALUE = "root";
	private final static String PASSWORD_TEXT_FIELD_DEFAULT_VALUE = "maximax";
	private final static String DATABASE_NAME_TEXT_FIELD_DEFAULT_VALUE = "employees";
	private final static String SERVER_NAME_TEXT_FIELD_DEFAULT_VALUE = "localhost";
	
	
	private final JTextField userTextField;
	private final JTextField passwordTextField;
	private final JTextField databaseNameTextField;
	private final JTextField serverNameTextField;
	
	public ImportDatabaseDefinitionFrame() {
		super();
		setTitle("Import database definition");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel userTextFieldPanel = new JPanel();
		userTextFieldPanel.setLayout(new GridLayout(1, 2));
		JLabel userTextFieldLabel = new JLabel(USER_NAME_TEXT_FIELD_LABEL);
		userTextField = new JTextField();
		userTextFieldPanel.add(userTextFieldLabel);
		userTextFieldPanel.add(userTextField);
		
		JPanel passwordTextFieldPanel = new JPanel();
		passwordTextFieldPanel.setLayout(new GridLayout(1, 2));
		JLabel passwordTextFieldLabel = new JLabel(PASSWORD_TEXT_FIELD_LABEL);
		passwordTextField = new JTextField();
		passwordTextFieldPanel.add(passwordTextFieldLabel);
		passwordTextFieldPanel.add(passwordTextField);
		
		JPanel databaseNameTextFieldPanel = new JPanel();
		databaseNameTextFieldPanel.setLayout(new GridLayout(1, 2));
		JLabel databaseNameTextFieldLabel = new JLabel(DATABASE_NAME_TEXT_FIELD_LABEL);
		databaseNameTextField = new JTextField();
		databaseNameTextFieldPanel.add(databaseNameTextFieldLabel);
		databaseNameTextFieldPanel.add(databaseNameTextField);
		
		JPanel serverNameTextFieldPanel = new JPanel();
		serverNameTextFieldPanel.setLayout(new GridLayout(1, 2));
		JLabel serverNameTextFieldLabel = new JLabel(SERVER_NAME_TEXT_FIELD_LABEL);
		serverNameTextField = new JTextField();
		serverNameTextFieldPanel.add(serverNameTextFieldLabel);
		serverNameTextFieldPanel.add(serverNameTextField);
		
		JPanel controlButtonsPanel = new JPanel();
		controlButtonsPanel.setLayout(new GridLayout(1, 2));
		JButton cancelButton = new JButton(new CancelAction());
		JButton importButton = new JButton(new ImportAction());
		controlButtonsPanel.add(cancelButton);
		controlButtonsPanel.add(importButton);
		
		GridLayout gridLayout = new GridLayout(5, 1);
		JPanel controlPanels = new JPanel();
		controlPanels.setLayout(gridLayout);
		
		Container pane = getContentPane();
//		pane.add(compsToExperiment, BorderLayout.NORTH);
//        pane.add(new JSeparator(), BorderLayout.CENTER);
		controlPanels.add(userTextFieldPanel);
		controlPanels.add(passwordTextFieldPanel);
		controlPanels.add(databaseNameTextFieldPanel);
		controlPanels.add(serverNameTextFieldPanel);
		controlPanels.add(controlButtonsPanel);
        pane.add(controlPanels, BorderLayout.SOUTH);
        this.pack();
        
        userTextField.setText(USER_NAME_TEXT_FIELD_DEFAULT_VALUE);
        passwordTextField.setText(PASSWORD_TEXT_FIELD_DEFAULT_VALUE);
        databaseNameTextField.setText(DATABASE_NAME_TEXT_FIELD_DEFAULT_VALUE);
        serverNameTextField.setText(SERVER_NAME_TEXT_FIELD_DEFAULT_VALUE);
        
	}
	
	private class ImportAction extends AbstractAction
	{
		public ImportAction(){
			super("Import");
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			DatabaseStructureLoaderManager structureLoaderManager = 
					new DatabaseStructureLoaderManager();
			String user = userTextField.getText();
			String password = passwordTextField.getText();
			String databaseName = databaseNameTextField.getText();
			String serverName = serverNameTextField.getText();
			try {
				DatabaseDescriptionPOJO descriptionPOJO =
					structureLoaderManager.
						GetDatabaseDescriptionPOJO(
							user,
							password,
							databaseName,
							serverName);
				databaseTemplateEditorPersistanceManager.
					doPersistDatabaseModel(descriptionPOJO, databaseName);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			JOptionPane.showMessageDialog(ImportDatabaseDefinitionFrame.this,
//					"Not implemented yet","Not implemented yet",JOptionPane.ERROR_MESSAGE);
			ImportDatabaseDefinitionFrame.this.dispose();
		}	
	}
	
	private class CancelAction extends AbstractAction
	{
		public CancelAction(){
			super("Cancel");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			ImportDatabaseDefinitionFrame.this.dispose();
		}
		
	}
}
