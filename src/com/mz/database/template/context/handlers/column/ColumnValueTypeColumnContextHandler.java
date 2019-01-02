package com.mz.database.template.context.handlers.column;

import com.mz.database.models.TableColumnDescriptionPOJO;

public class ColumnValueTypeColumnContextHandler extends AbstractColumnTemplateContextHandler {

	private final static String START_CONTEXT_WORD = "{:TDB:TABLE:COLUMN:FOREACH:CURRENT:TYPE";
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
		TableColumnDescriptionPOJO descriptionPojo = getAssociatedColumnDescriptorPOJO();
		if(descriptionPojo == null) 
			throw new Exception("The AssociatedColumnDescriptorPOJO is not set");
		
		String TrimedStringContext = TrimContextFromContextWrapper(StringContext);
		if(!TrimedStringContext.equals(""))
			throw new Exception("There is a problem with the provided StringContext :'" + StringContext + "' to the suited word '" + (START_CONTEXT_WORD + END_CONTEXT_WORD) + "'" );
		return descriptionPojo.get_TypeStr();
	}

	@Override
	public boolean isStartContextAndEndContextAnEntireWord() {
		return true;
	}

}
