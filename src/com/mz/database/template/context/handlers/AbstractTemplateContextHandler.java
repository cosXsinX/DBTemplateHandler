package com.mz.database.template.context.handlers;

import com.mz.database.template.context.handlers.column.AbstractColumnTemplateContextHandler;
import com.mz.database.template.context.handlers.database.AbstractDatabaseTemplateContextHandler;
import com.mz.database.template.context.handlers.table.AbstractTableTemplateContextHandler;

public abstract class AbstractTemplateContextHandler {

	public abstract String getStartContextStringWrapper();
	
	public abstract String getEndContextStringWrapper();
	
	public abstract boolean isStartContextAndEndContextAnEntireWord();
	
	protected String getTemplateHandlerSignature()
	{
		return getStartContextStringWrapper() + getEndContextStringWrapper();
	}
	
	public String TrimContextFromContextWrapper(String stringContext) throws Exception{
		
		if(!stringContext.startsWith(getStartContextStringWrapper()))
		{
			throw new 
				Exception("The provided stringContext does not start with " + getStartContextStringWrapper());
		}
		
		if(!stringContext.endsWith(getEndContextStringWrapper()))
		{
			throw new 
				Exception("The provided stringContext does not end with " + getEndContextStringWrapper());
		}
		
		String result = stringContext.substring(
				getStartContextStringWrapper().length(),
					stringContext.length() - getEndContextStringWrapper().length());
		
		return result;
	}
	
	public abstract String processContext(String StringContext) throws Exception;
	
	public abstract String HandleTrimedContext(String StringTrimedContext) throws Exception;
	
	
}
