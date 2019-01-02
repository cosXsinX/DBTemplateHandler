package com.mz.database.template.context.handlers.column;

import com.mz.database.models.TableColumnDescriptionPOJO;

public class IsColumnNotPrimaryKeyColumnContextHandler extends AbstractColumnTemplateContextHandler {

	public final static String START_CONTEXT_WORD = "{:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:KEY:NOT:PRIMARY(";
	public final static String END_CONTEXT_WORD = ")KEY:NOT:PRIMARY:::}";
	
	
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
		if(!descriptionPojo.is_PrimaryKey())
		{
			return  HandleTrimedContext(TrimedStringContext);
		}
		else return "";
	}
	
	@Override
	public boolean isStartContextAndEndContextAnEntireWord() {
		return false;
	}

}
