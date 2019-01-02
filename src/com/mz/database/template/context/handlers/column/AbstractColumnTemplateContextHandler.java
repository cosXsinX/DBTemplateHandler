package com.mz.database.template.context.handlers.column;

import com.mz.database.models.DatabaseDescriptionPOJO;
import com.mz.database.models.TableColumnDescriptionPOJO;
import com.mz.database.models.TableDescriptionPOJO;
import com.mz.database.template.context.handlers.AbstractTemplateContextHandler;
import com.mz.database.template.handler.TemplateHandlerNew;

public abstract class AbstractColumnTemplateContextHandler extends AbstractTemplateContextHandler{
	
	TableColumnDescriptionPOJO _tableColumnDescriptionPojo;
	public TableColumnDescriptionPOJO getAssociatedColumnDescriptorPOJO(){
		return _tableColumnDescriptionPojo;
	}
	
	public void setAssociatedColumnDescriptorPOJO(TableColumnDescriptionPOJO pojo )
	{
		_tableColumnDescriptionPojo = pojo;
	}
	
	public String HandleTrimedContext(String StringTrimedContext) throws Exception
	{
		if(StringTrimedContext == null) return null;
		TableColumnDescriptionPOJO descriptionPojo = getAssociatedColumnDescriptorPOJO();
		if(descriptionPojo == null) return StringTrimedContext;
		TableDescriptionPOJO  tableDescriptionPojo = descriptionPojo.ParentTable;
		DatabaseDescriptionPOJO databaseDescriptionPojo   = null;
		if(tableDescriptionPojo != null)
			databaseDescriptionPojo= tableDescriptionPojo.ParentDatabase;
		return TemplateHandlerNew.
				HandleTemplate(StringTrimedContext, databaseDescriptionPojo,
						tableDescriptionPojo, descriptionPojo);
	}
	
	
}
