package com.mz.UI.Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import com.mz.UI.adapters.AvailableTemplateUITableAdapter;
import com.mz.UI.adapters.AvailableTemplateWordForEditingUITableAdapter;
import com.mz.UI.table.cell.editor.StringTableCellEditor;
import com.mz.UI.table.cell.renderer.StringTableCellRenderer;
import com.mz.database.configuration.manager.databaseTemplateEditorConfigurationManager;
import com.mz.database.configuration.manager.databaseTemplateEditorPersistanceManager;
import com.mz.database.configuration.manager.databaseTemplateEditorTemplateManager;
import com.mz.database.models.TableDescriptionPOJO;
import com.mz.database.template.context.handlers.AbstractTemplateContextHandler;
import com.mz.database.template.context.handlers.TemplateContextHandlerPackageProvider;
import com.mz.database.template.handler.TemplateValidator;
import com.mz.database.template.handler.TemplateValidatorForEditor;

public class TemplateSelectionUI extends JFrame {
	private JTable _availableTemplateUITable;
	private JTextPane _editorTextArea;
	private String _visibleEditorTextAreaContentFileName = null;
	private JLabel _templateValidationState;
	private JTable _availableTemplateWordForEditingUITable;
	private AvailableTemplateUITableAdapter adapter;
	private AvailableTemplateWordForEditingUITableAdapter wordAdapter;
	
	
	private final List<OnSelectionDoneListener> onSelectionDoneListenerList = new ArrayList<TemplateSelectionUI.OnSelectionDoneListener>();
	private final List<OnCancelSelectionListener> onCancellationDoneListenerList = new ArrayList<TemplateSelectionUI.OnCancelSelectionListener>();
	
	private JTable CreateAvailableTemplateWordForEditingUITable()
	{
		wordAdapter = new AvailableTemplateWordForEditingUITableAdapter
				(TemplateContextHandlerPackageProvider.getAllContextHandlerIterable());
		JTable result = new JTable(wordAdapter);
		result.setDefaultRenderer(String.class, new StringTableCellRenderer());
		result.setDefaultEditor(String.class, new StringTableCellEditor());
		result.addMouseListener(new MouseAdapter() {
		
			@Override
			public void mousePressed(MouseEvent me) {
				JTable table =(JTable) me.getSource();
		        Point p = me.getPoint();
		        int row = table.rowAtPoint(p);
		        if (me.getClickCount() == 2) {
		        	//Double click
		        	AbstractTemplateContextHandler contextHandler = wordAdapter.getContextHandlerAt(row);
		        	if(contextHandler == null) return;
		        	String selectedTextComponent = _editorTextArea.getSelectedText();
		        	if(selectedTextComponent == null) return ;
		        	_isTextColoringAnalysisSwitchedOn = false;
		        	if(contextHandler.isStartContextAndEndContextAnEntireWord())
		        	{
		        		String contextHandlerWordString = contextHandler.getStartContextStringWrapper() + contextHandler.getEndContextStringWrapper();
		        		_editorTextArea.replaceSelection(contextHandlerWordString);
		        	}
		        	else
		        	{
		        		String contextHandlerWordString = contextHandler.getStartContextStringWrapper() + selectedTextComponent + contextHandler.getEndContextStringWrapper();
		        		_editorTextArea.replaceSelection(contextHandlerWordString);
		        	}
		        	_isTextColoringAnalysisSwitchedOn = true;
		        	StyledDocument styleDocument = _editorTextArea.getStyledDocument();
		        	try {
						HandleWithDocumentEnglightment(styleDocument);
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
			}
		});
		return result;
	}
	
	public void AddOnSelectionDoneListener(OnSelectionDoneListener onSelectionDoneListner){
		if(onSelectionDoneListner == null) return;
		int currentListenerIndex;
		OnSelectionDoneListener currentListener;
		for(currentListenerIndex = 0 ; currentListenerIndex< onSelectionDoneListenerList.size(); currentListenerIndex++){
			currentListener = onSelectionDoneListenerList.get(currentListenerIndex);
			if(currentListener.equals(onSelectionDoneListner)) break;
		}
		if(currentListenerIndex == onSelectionDoneListenerList.size()) onSelectionDoneListenerList.add(onSelectionDoneListner);
	}
	
	public void RemoveOnSelectionDoneListener(OnSelectionDoneListener onSelectionDoneListener){
		if(onSelectionDoneListener == null) return;
		int currentListenerIndex;
		OnSelectionDoneListener currentListener;
		for(currentListenerIndex= 0; currentListenerIndex<onSelectionDoneListenerList.size();currentListenerIndex ++)
		{
			currentListener = onSelectionDoneListenerList.get(currentListenerIndex);
			if(currentListener.equals(onSelectionDoneListener)) break;
		}
		if(currentListenerIndex != onSelectionDoneListenerList.size())onSelectionDoneListenerList.remove(currentListenerIndex);
	}
	
	public void AddOnCancelSelectionListener(OnCancelSelectionListener onCancelSelectionListener){
		if(onCancelSelectionListener == null) return;
		int currentListenerIndex;
		OnCancelSelectionListener currentListener;
		for(currentListenerIndex=0; currentListenerIndex< onCancellationDoneListenerList.size(); currentListenerIndex ++){
			currentListener = onCancellationDoneListenerList.get(currentListenerIndex);
			if(currentListener.equals(onCancelSelectionListener)) break;
		}
		if(currentListenerIndex == onCancellationDoneListenerList.size()) onCancellationDoneListenerList.add(onCancelSelectionListener);
	}
	
	public void RemoveOnCancelSelectionListener(OnCancelSelectionListener onCancelSelectionListener){
		if(onCancelSelectionListener == null) return;
		int currentListenerIndex;
		OnCancelSelectionListener currentListener;
		for(currentListenerIndex= 0 ; currentListenerIndex < onCancellationDoneListenerList.size();currentListenerIndex++){
			currentListener = onCancellationDoneListenerList.get(currentListenerIndex);
			if(currentListener.equals(onCancelSelectionListener)) break;
		}
		if(currentListenerIndex != onCancellationDoneListenerList.size()) onCancellationDoneListenerList.remove(currentListenerIndex);
	}
	
	
	public TemplateSelectionUI(){
		
		setTitle("Database Template Editor");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel UITablePanel = new JPanel();
        adapter = new AvailableTemplateUITableAdapter();
        adapter.SetValues(databaseTemplateEditorTemplateManager.ListAvailableTemplates());
        _availableTemplateUITable = new JTable(adapter);
        _availableTemplateUITable.setDefaultRenderer(String.class, new StringTableCellRenderer());
        _availableTemplateUITable.setDefaultEditor(String.class, new StringTableCellEditor());
        _availableTemplateUITable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        _availableTemplateUITable.setPreferredScrollableViewportSize(new Dimension(300,500));
        _availableTemplateUITable.setFillsViewportHeight(true);
        _availableTemplateUITable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        resizeColumnWidth(_availableTemplateUITable);
        JScrollPane everyDatabaseTableDefinitionUITableScrollPane =new JScrollPane(_availableTemplateUITable);
        UITablePanel.add(everyDatabaseTableDefinitionUITableScrollPane,BorderLayout.WEST);
        
        _editorTextArea = new JTextPane();
        _editorTextArea.setText("Hello");
        _editorTextArea.setDocument(getEditorStyledDocument());
        JPanel editorPanel = new JPanel(new BorderLayout());
        editorPanel.add(_editorTextArea);
        JScrollPane scrollPane = new JScrollPane(editorPanel); 
        _editorTextArea.setEditable(true);
        
        _availableTemplateUITable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent listSelectionEvent) {
				int SelectedIndex = _availableTemplateUITable.getSelectedRow();
				String value = adapter.getStringValueAt(SelectedIndex, 0);
				String outputString = value;
				try {
					outputString = databaseTemplateEditorTemplateManager.
						getTemplateFileStringContent(value);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				_visibleEditorTextAreaContentFileName = value;
				_isTextColoringAnalysisSwitchedOn = false;
				ClearDocumentEnlightment();
				_editorTextArea.setText(outputString);
				_isTextColoringAnalysisSwitchedOn = true;
				try {
					HandleWithDocumentEnglightment(_editorTextArea.getStyledDocument());
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		});
        
        scrollPane.setPreferredSize(new Dimension(500,500));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        UITablePanel.add(scrollPane, BorderLayout.CENTER);
        
        _availableTemplateWordForEditingUITable = CreateAvailableTemplateWordForEditingUITable();
        _availableTemplateWordForEditingUITable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);;
        _availableTemplateWordForEditingUITable.setPreferredScrollableViewportSize(new Dimension(300,500));
        resizeColumnWidth(_availableTemplateWordForEditingUITable);
        JScrollPane availableWordScrollPane = new JScrollPane(_availableTemplateWordForEditingUITable);
        UITablePanel.add(availableWordScrollPane,BorderLayout.EAST);
        UITablePanel.setPreferredSize(new Dimension(1200,550));
        getContentPane().add(UITablePanel, BorderLayout.CENTER);
		JPanel boutons = new JPanel();
        boutons.add(new JButton(new CancelAction()));
        boutons.add(new JButton(new SelectAction()));
        
        getContentPane().add(boutons, BorderLayout.SOUTH);
        JPanel templateBoutons = new JPanel();
        JButton SaveTemplateBoutons = new JButton(new SaveTemplateAction());
        templateBoutons.add(SaveTemplateBoutons);
        _templateValidationState = new JLabel("Validation state");
        _editorTextArea.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				String content = _editorTextArea.getText();
				ManageContentValidation(content);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				String content = _editorTextArea.getText();
				ManageContentValidation(content);
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				String content = _editorTextArea.getText();
				ManageContentValidation(content);
			}
			
			private void ManageContentValidation(String content)
			{
				boolean validationState = TemplateValidator.
						TemplateStringValidation(content);
				if(validationState)
				{
					_templateValidationState.setText("Valid template");
					_templateValidationState.setForeground(Color.GREEN);
				}
				else
				{
					_templateValidationState.setText("Not valid template");
					_templateValidationState.setForeground(Color.RED);
				}
			}
		});
        templateBoutons.add(_templateValidationState);
        getContentPane().add(templateBoutons, BorderLayout.NORTH);
		pack();
	}
	
	public class SelectAction extends AbstractAction{
		public SelectAction(){
			super("Select");
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			StringBuilder templateSelectionAppaendedStringBuilder = new StringBuilder();
			int[] selection = _availableTemplateUITable.getSelectedRows();
            for(int i = selection.length - 1; i >= 0; i--){
                String currentTemplateNameStr = (String) adapter.getValueAt(selection[i], 0);
                templateSelectionAppaendedStringBuilder.append(currentTemplateNameStr + ";");
            }
			String templateSelectionString = templateSelectionAppaendedStringBuilder.toString();
            int currentIndex;
            OnSelectionDoneListener currentSelectionListener;
            for(currentIndex = 0 ; currentIndex< onSelectionDoneListenerList.size(); currentIndex ++)
            {
            	currentSelectionListener = onSelectionDoneListenerList.get(currentIndex);
            	currentSelectionListener.onSelectionDone(templateSelectionString);
            }
            dispose();
		}
	}
	
	public class CancelAction extends AbstractAction{
		public CancelAction(){
			super("Cancel");
		}
		
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			int currentIndex;
			OnCancelSelectionListener currentListener;
			for(currentIndex = 0 ; currentIndex< onCancellationDoneListenerList.size(); currentIndex ++){
				currentListener = onCancellationDoneListenerList.get(currentIndex);
				currentListener.onCancelSelection();
			}
			dispose();
		}
		
	}
	
	public class SaveTemplateAction extends AbstractAction{

		public SaveTemplateAction(){
			super("Save template contnet");
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(_visibleEditorTextAreaContentFileName == null) return;
			try {
				databaseTemplateEditorTemplateManager.saveTemplateString(_visibleEditorTextAreaContentFileName, _editorTextArea.getText());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public interface OnSelectionDoneListener{
		public void onSelectionDone(String SelectedValuesString);
	}
	
	public interface OnCancelSelectionListener{
		public void onCancelSelection();
	}
	
	private void resizeColumnWidth(JTable table) {
	    final TableColumnModel columnModel = table.getColumnModel();
	    for (int column = 0; column < table.getColumnCount(); column++) {
	        int width = 50; // Min width
	        for (int row = 0; row < table.getRowCount(); row++) {
	            TableCellRenderer renderer = table.getCellRenderer(row, column);
	            Component comp = table.prepareRenderer(renderer, row, column);
	            width = Math.max(comp.getPreferredSize().width +1 , width);
	        }
	        columnModel.getColumn(column).setPreferredWidth(width);
	    }
	}
	
	private boolean _isTextColoringAnalysisSwitchedOn = true;
	
	private TemplateEditorStyledDocument getEditorStyledDocument()
	{
		TemplateEditorStyledDocument styledDocument = new TemplateEditorStyledDocument();
		return styledDocument;
	}
	
	
	public class TemplateEditorStyledDocument extends DefaultStyledDocument
	{
		public void insertString (int offset, String str, AttributeSet a) throws BadLocationException {
            super.insertString(offset, str, a);
            HandleWithDocumentEnglightment(this);
        }

        public void remove (int offs, int len) throws BadLocationException {
            super.remove(offs, len);
			HandleWithDocumentEnglightment(this);		
        }
        
        public void ClearEnlightment()
        {
        	ClearDocumentEnlightment();
        }
	}
	private Queue<SimpleEntry<Integer, Integer>> _FormerElementQueue = 
			new LinkedList<SimpleEntry<Integer, Integer>>();
	private Map<SimpleEntry<Integer,Integer>,Boolean> _ReferencedHighLightment =
			new HashMap<AbstractMap.SimpleEntry<Integer,Integer>, Boolean>();
	static final StyleContext cont = StyleContext.getDefaultStyleContext();
    static final AttributeSet attr = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.RED);
    static final AttributeSet attrBlack = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.BLACK);
    
	private void HandleWithDocumentEnglightment(StyledDocument styledDocument) throws BadLocationException 
	{
        if(!_isTextColoringAnalysisSwitchedOn) return;
        final String text = styledDocument.getText(0, styledDocument.getLength());
		if(text.equals("")) return;
        final Queue<SimpleEntry<Integer, Integer>> delimiterIndexesQueue = 
        		ProvideContextDelimiterWordIndexesQueueAsync(text);
        if(delimiterIndexesQueue == null) return;
        Map<SimpleEntry<Integer,Integer>,Boolean> NewElementHashMap =
        		new HashMap<AbstractMap.SimpleEntry<Integer,Integer>, Boolean>();
        for(SimpleEntry<Integer, Integer> delimitersPair : delimiterIndexesQueue)
        {
			NewElementHashMap.put(delimitersPair, true);
        }
        
        if(_FormerElementQueue != null)
		{
			for(SimpleEntry<Integer, Integer> delimitersPair : _FormerElementQueue)
	        {
				if(!NewElementHashMap.containsKey(delimitersPair))
					styledDocument.setCharacterAttributes(delimitersPair.getKey(),
							delimitersPair.getValue() - delimitersPair.getKey() , attrBlack, false);
	        }
		}
        for(SimpleEntry<Integer, Integer> delimitersPair : delimiterIndexesQueue)
        {
			if(!_ReferencedHighLightment.containsKey(delimitersPair))
				styledDocument.setCharacterAttributes(delimitersPair.getKey(),
						delimitersPair.getValue() - delimitersPair.getKey() , attr, false);
        }
		
		_ReferencedHighLightment = NewElementHashMap;
		_FormerElementQueue =delimiterIndexesQueue;
	}
	
	private void ClearDocumentEnlightment()
	{
		StyledDocument styledDoc = _editorTextArea.getStyledDocument();
		styledDoc.setCharacterAttributes(0,
				styledDoc.getLength()-1 , attrBlack, false);
		
		
		styledDoc.setCharacterAttributes(0,
					styledDoc.getLength(), attrBlack, false);
    
		_FormerElementQueue = null;
		_ReferencedHighLightment.clear();
	}
	
	private Queue<SimpleEntry<Integer, Integer>> ProvideContextDelimiterWordIndexesQueueAsync(String text)
	{
		setAnalyzedRunnableTextReferenceLockObj(text);
		new Thread(_getDelimiterIndexesRunnable).run();
		return getDelimiterIndexesQueueSynchronized();
	}
	
	private String _analyzedRunnableTextReference=null;
	private Object _analyzedRunnableTextReferenceLockObj = new Object();
	private String getAnalyzedRunnableTextReferenceLockObj()
	{
		synchronized(_analyzedRunnableTextReferenceLockObj)
		{
			return _analyzedRunnableTextReference;
		}
	}
	private void setAnalyzedRunnableTextReferenceLockObj(String value)
	{
		synchronized(_analyzedRunnableTextReferenceLockObj)
		{
			_analyzedRunnableTextReference = value;
		}
	}
	
	private Queue<SimpleEntry<Integer, Integer>> _delimiterIndexesQueueSynchronized = null;
	private Object _delimiterIndexesQueueSynchronizedLockObj = new Object();
	private Queue<SimpleEntry<Integer, Integer>> getDelimiterIndexesQueueSynchronized()
	{
		synchronized(_delimiterIndexesQueueSynchronizedLockObj)
		{
			return _delimiterIndexesQueueSynchronized;
		}
	}
	private void setdelimiterIndexesQueueSynchronized(Queue<SimpleEntry<Integer, Integer>> value)
	{
		synchronized(_delimiterIndexesQueueSynchronizedLockObj)
		{
			_delimiterIndexesQueueSynchronized = value;
		}
	}
	Runnable _getDelimiterIndexesRunnable = new Runnable() {
		
		@Override
		public void run() {
			String analyzedText = getAnalyzedRunnableTextReferenceLockObj();
			if(analyzedText == null) return;
			if(analyzedText.equals("")) return;
            Queue<SimpleEntry<Integer, Integer>> delimiterIndexesQueue = 
            		TemplateValidatorForEditor.
            			ProvideContextDelimiterWordIndexesQueue(_analyzedRunnableTextReference);
			if(!analyzedText.equals(getAnalyzedRunnableTextReferenceLockObj())) return;
			setdelimiterIndexesQueueSynchronized(delimiterIndexesQueue);
		}
	};
}
