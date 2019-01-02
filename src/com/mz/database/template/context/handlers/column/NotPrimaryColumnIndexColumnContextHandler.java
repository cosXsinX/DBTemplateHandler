package com.mz.database.template.context.handlers.column;

import java.util.List;

import com.mz.database.models.TableColumnDescriptionPOJO;

public class NotPrimaryColumnIndexColumnContextHandler extends AbstractColumnTemplateContextHandler {

	private final static String START_CONTEXT_WORD = "{:TDB:TABLE:COLUMN:PRIMARY:FOREACH:CURRENT:INDEX";
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
		if(!TrimedStringContext.equals( ""))
			throw new Exception("There is a problem with the provided StringContext :'" + StringContext + "' to the suited word '" + (START_CONTEXT_WORD + END_CONTEXT_WORD) + "'" );
		if(descriptionPojo.ParentTable == null)
			return Integer.toString(0);
		int currentIndex = 0;
		int currentAutoIndex = 0;
		List<TableColumnDescriptionPOJO> columnList = 
				descriptionPojo.ParentTable.get_ColumnsList();
		for(currentIndex = 0; currentIndex < columnList.size();currentIndex++)
		{
			TableColumnDescriptionPOJO currentColumn = columnList.get(currentIndex);
			if(currentColumn.is_PrimaryKey())
			{
				if(currentColumn.equals(descriptionPojo))
				{
					return Integer.toString(currentAutoIndex);
				}
				currentAutoIndex++;
			}
		}
		return Integer.toString( 0);
	}

	@Override
	public boolean isStartContextAndEndContextAnEntireWord() {
		return true;
	}

}
