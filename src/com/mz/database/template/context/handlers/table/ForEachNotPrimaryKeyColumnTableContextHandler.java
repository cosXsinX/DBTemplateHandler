package com.mz.database.template.context.handlers.table;

import com.mz.database.models.TableColumnDescriptionPOJO;
import com.mz.database.models.TableDescriptionPOJO;
import com.mz.database.template.handler.TemplateHandlerNew;

public class ForEachNotPrimaryKeyColumnTableContextHandler extends AbstractTableTemplateContextHandler {

	public final static String START_CONTEXT_WORD = "{:TDB:TABLE:COLUMN:NOT:PRIMARY:FOREACH[";
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
		TableDescriptionPOJO descriptionPojo = getAssociatedTableDescriptorPOJO();
		if(descriptionPojo == null) 
			throw new Exception("The AssociatedTableDescriptorPOJO is not set");
		
		String TrimedStringContext = TrimContextFromContextWrapper(StringContext);
		StringBuilder stringBuilder = new StringBuilder();
		for(TableColumnDescriptionPOJO currentColumn : descriptionPojo.get_ColumnsList())
		{
			if(!currentColumn.is_PrimaryKey())
			{
				String treated =
						TemplateHandlerNew.HandleTableColumnTemplate
							(TrimedStringContext, currentColumn);
				treated = TemplateHandlerNew.
						HandleFunctionTemplate
							(treated, descriptionPojo.ParentDatabase, 
									descriptionPojo, currentColumn);
				stringBuilder.append(treated);
			}
		}
		return stringBuilder.toString();
	}
	
	@Override
	public boolean isStartContextAndEndContextAnEntireWord() {
		return false;
	}
}