package com.mz.database.template.context.handlers.database;

import com.mz.database.models.DatabaseDescriptionPOJO;
import com.mz.database.models.TableDescriptionPOJO;
import com.mz.database.template.context.handlers.database.AbstractDatabaseTemplateContextHandler;
import com.mz.database.template.handler.TemplateHandlerNew;

public class ForEachTableDatabaseContextHandler extends AbstractDatabaseTemplateContextHandler {

	public final static String START_CONTEXT_WORD = "{:TDB:TABLE:FOREACH[";
	public final static String END_CONTEXT_WORD = "]::}";
	
	
	@Override
	public String getStartContextStringWrapper() {
		return START_CONTEXT_WORD;
	}

	@Override
	public String getEndContextStringWrapper() {
		return END_CONTEXT_WORD;
	}

	@Override
	public String processContext(String StringContext) throws Exception {
		if(StringContext == null)
			throw new Exception("The provided StringContext is null");
		DatabaseDescriptionPOJO descriptionPojo = getAssociatedDatabaseDescriptorPOJO();
		if(descriptionPojo == null) 
			throw new Exception("The AssociatedDatabaseDescriptorPOJO is not set");
		
		String TrimedStringContext = TrimContextFromContextWrapper(StringContext);
		StringBuilder stringBuilder = new StringBuilder();
		for(TableDescriptionPOJO currentColumn : descriptionPojo.getTableList())
		{
			String treated =TemplateHandlerNew.
					HandleTableTemplate(TrimedStringContext, currentColumn);
			treated = TemplateHandlerNew.HandleFunctionTemplate(treated, descriptionPojo, currentColumn, null);
			stringBuilder.append(treated);
		}
		return stringBuilder.toString();
	}
	
	@Override
	public boolean isStartContextAndEndContextAnEntireWord() {
		return false;
	}
}

