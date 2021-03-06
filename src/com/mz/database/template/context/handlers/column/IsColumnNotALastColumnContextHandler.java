package com.mz.database.template.context.handlers.column;

import java.util.List;

import com.mz.database.models.TableColumnDescriptionPOJO;

public class IsColumnNotALastColumnContextHandler extends AbstractColumnTemplateContextHandler {

	public final static String START_CONTEXT_WORD = "{:TDB:TABLE:COLUMN:FOREACH:CURRENT:IS:NOT:LAST:COLUMN(";
	public final static String END_CONTEXT_WORD = "):::}";
	
	
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
		if(descriptionPojo.ParentTable == null)
			throw new Exception("The provided column has no parent table");
		List<TableColumnDescriptionPOJO> columnList = descriptionPojo.ParentTable.get_ColumnsList();
		if(columnList == null || !( columnList.size() >0))
			throw new Exception("The provided column's parent table has no column associated to");
		if(!descriptionPojo.equals(columnList.get(columnList.size()-1)) )
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
