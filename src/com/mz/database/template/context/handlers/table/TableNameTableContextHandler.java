package com.mz.database.template.context.handlers.table;

import com.mz.database.models.TableDescriptionPOJO;
import com.mz.database.template.context.handlers.table.AbstractTableTemplateContextHandler;

public class TableNameTableContextHandler extends AbstractTableTemplateContextHandler {

	private final static String START_CONTEXT_WORD = "{:TDB:TABLE:CURRENT:NAME";
	private final static String END_CONTEXT_WORD = "::}";
	
	public final static String TEMPLATE_TABLE_WORD = START_CONTEXT_WORD + END_CONTEXT_WORD;
	
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
		TableDescriptionPOJO descriptionPojo = getAssociatedTableDescriptorPOJO();
		if(descriptionPojo == null) 
			throw new Exception("The AssociatedDatabaseDescriptorPOJO is not set");
		
		String TrimedStringContext = TrimContextFromContextWrapper(StringContext);
		if(!TrimedStringContext.equals( ""))
			throw new Exception("There is a problem with the provided StringContext :'" + StringContext + "' to the suited word '" + (START_CONTEXT_WORD + END_CONTEXT_WORD) + "'" );
		return descriptionPojo.get_NameStr();
	}
	
	@Override
	public boolean isStartContextAndEndContextAnEntireWord() {
		return true;
	}
}

