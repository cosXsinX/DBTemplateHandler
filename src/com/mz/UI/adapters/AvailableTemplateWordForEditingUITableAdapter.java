package com.mz.UI.adapters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import com.mz.database.models.DatabaseDescriptionPOJO;
import com.mz.database.template.context.handlers.AbstractTemplateContextHandler;
import com.mz.database.template.context.handlers.column.AbstractColumnTemplateContextHandler;
import com.mz.database.template.context.handlers.database.AbstractDatabaseTemplateContextHandler;
import com.mz.database.template.context.handlers.function.AbstractFunctionTemplateContextHandler;
import com.mz.database.template.context.handlers.table.AbstractTableTemplateContextHandler;

public class AvailableTemplateWordForEditingUITableAdapter extends AbstractTableModel {

	List<AbstractDatabaseTemplateContextHandler> _databaseTemplateContextHandler = new ArrayList<AbstractDatabaseTemplateContextHandler>();
	List<AbstractTableTemplateContextHandler> _tableTemplateContextHandler = new ArrayList<AbstractTableTemplateContextHandler>();;
	List<AbstractColumnTemplateContextHandler> _columnTemplateContextHandler = new ArrayList<AbstractColumnTemplateContextHandler>();;
	List<AbstractFunctionTemplateContextHandler> _functionTemplateContextHandler = new ArrayList<AbstractFunctionTemplateContextHandler>();;
	
	List<String> _exposedValueList = new ArrayList<String>();
	Map<String, AbstractTemplateContextHandler> _wordContextIndexedContextHandlerMap = new HashMap<String, AbstractTemplateContextHandler>();
	
	public AvailableTemplateWordForEditingUITableAdapter
		(Iterable<AbstractTemplateContextHandler> templateContextHandler) {
		setTemplateContextHandlers(templateContextHandler);
	}
	
	public void setTemplateContextHandlers
		(Iterable<AbstractTemplateContextHandler> templateContextHandlers)
	{
		if(templateContextHandlers == null) return;
		_databaseTemplateContextHandler.clear();
		_tableTemplateContextHandler.clear();
		_columnTemplateContextHandler.clear();
		_functionTemplateContextHandler.clear();
		_exposedValueList.clear();
		_wordContextIndexedContextHandlerMap.clear();
		for(AbstractTemplateContextHandler currentContextHanlder : templateContextHandlers)
		{
			if(currentContextHanlder instanceof AbstractDatabaseTemplateContextHandler)
			{
				_databaseTemplateContextHandler.add((AbstractDatabaseTemplateContextHandler)currentContextHanlder);
			}
			else if(currentContextHanlder instanceof AbstractTableTemplateContextHandler)
			{
				_tableTemplateContextHandler.add((AbstractTableTemplateContextHandler)currentContextHanlder);
			}
			else if(currentContextHanlder instanceof AbstractColumnTemplateContextHandler)
			{
				_columnTemplateContextHandler.add((AbstractColumnTemplateContextHandler)currentContextHanlder);
			}
			else if(currentContextHanlder instanceof AbstractFunctionTemplateContextHandler)
			{
				_functionTemplateContextHandler.add((AbstractFunctionTemplateContextHandler)currentContextHanlder);
			}
		}
		
		
		_exposedValueList.add("-------- Database words -------");
		Collections.sort(_databaseTemplateContextHandler, new Comparator<AbstractDatabaseTemplateContextHandler>() {
			@Override
			public int compare(AbstractDatabaseTemplateContextHandler o1, AbstractDatabaseTemplateContextHandler o2) {
				return o1.getStartContextStringWrapper().compareTo(o2.getStartContextStringWrapper());
			}
		});
		for(AbstractDatabaseTemplateContextHandler currentContextHandler : _databaseTemplateContextHandler)
		{
			String ContextWord = 
					currentContextHandler.getStartContextStringWrapper()+
						currentContextHandler.getEndContextStringWrapper();
			_exposedValueList.add(ContextWord);
			_wordContextIndexedContextHandlerMap.put(ContextWord, currentContextHandler);
			
		}
		_exposedValueList.add("-------- Table words -------");
		Collections.sort(_tableTemplateContextHandler, new Comparator<AbstractTableTemplateContextHandler>() {
			@Override
			public int compare(AbstractTableTemplateContextHandler o1, AbstractTableTemplateContextHandler o2) {
				return o1.getStartContextStringWrapper().compareTo(o2.getStartContextStringWrapper());
			}
		});
		for(AbstractTableTemplateContextHandler currentContextHandler : _tableTemplateContextHandler)
		{
			String ContextWord = 
					currentContextHandler.getStartContextStringWrapper()+
						currentContextHandler.getEndContextStringWrapper();
			_exposedValueList.add(ContextWord);
			_wordContextIndexedContextHandlerMap.put(ContextWord, currentContextHandler);
		}
		_exposedValueList.add("-------- Column words -------");
		Collections.sort(_columnTemplateContextHandler, new Comparator<AbstractColumnTemplateContextHandler>() {
			@Override
			public int compare(AbstractColumnTemplateContextHandler o1, AbstractColumnTemplateContextHandler o2) {
				return o1.getStartContextStringWrapper().compareTo(o2.getStartContextStringWrapper());
			}
		});
		for(AbstractColumnTemplateContextHandler currentContextHandler : _columnTemplateContextHandler)
		{
			String ContextWord = 
					currentContextHandler.getStartContextStringWrapper()+
						currentContextHandler.getEndContextStringWrapper();
			_exposedValueList.add(ContextWord);
			_wordContextIndexedContextHandlerMap.put(ContextWord, currentContextHandler);
		}
		_exposedValueList.add("-------- Function words -------");
		Collections.sort(_functionTemplateContextHandler, new Comparator<AbstractFunctionTemplateContextHandler>() {
			@Override
			public int compare(AbstractFunctionTemplateContextHandler o1, AbstractFunctionTemplateContextHandler o2) {
				return o1.getStartContextStringWrapper().compareTo(o2.getStartContextStringWrapper());
			}
		});
		for(AbstractFunctionTemplateContextHandler currentContextHandler : _functionTemplateContextHandler)
		{
			String ContextWord = 
					currentContextHandler.getStartContextStringWrapper()+
						currentContextHandler.getEndContextStringWrapper();
			_exposedValueList.add(ContextWord);
			_wordContextIndexedContextHandlerMap.put(ContextWord, currentContextHandler);
		}
	}
	@Override
	public int getColumnCount() {
		return 1;
	}

	@Override
	public int getRowCount() {
		if(_exposedValueList == null) return 0;
		return _exposedValueList.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		if(_exposedValueList == null) return "";
		if(arg0> _exposedValueList.size()-1) return "out of range";
		return _exposedValueList.get(arg0);
	}
	
	public AbstractTemplateContextHandler getContextHandlerAt(int index)
	{
		if(_exposedValueList == null) return null;
		if(_wordContextIndexedContextHandlerMap == null) return null;
		if(index > _exposedValueList.size() -1) return null;
		String selectedWord = _exposedValueList.get(index);
		if(!_wordContextIndexedContextHandlerMap.containsKey(selectedWord)) return null;
		return _wordContextIndexedContextHandlerMap.get(selectedWord);
	}

}
