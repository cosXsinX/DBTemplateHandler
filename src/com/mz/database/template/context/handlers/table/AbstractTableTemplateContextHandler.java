package com.mz.database.template.context.handlers.table;

import com.mz.database.models.DatabaseDescriptionPOJO;
import com.mz.database.models.TableDescriptionPOJO;
import com.mz.database.template.context.handlers.AbstractTemplateContextHandler;
import com.mz.database.template.handler.TemplateHandlerNew;

public abstract class AbstractTableTemplateContextHandler extends AbstractTemplateContextHandler{

	TableDescriptionPOJO _tableDescriptionPojo;
	public TableDescriptionPOJO getAssociatedTableDescriptorPOJO(){
		return _tableDescriptionPojo;
	}
	
	public void setAssociatedTableDescriptorPOJO(TableDescriptionPOJO pojo )
	{
		_tableDescriptionPojo = pojo;
	}
	
	public String HandleTrimedContext(String StringTrimedContext) throws Exception
	{
		if(StringTrimedContext == null) return null;
		TableDescriptionPOJO descriptionPojo = getAssociatedTableDescriptorPOJO();
		if(descriptionPojo == null) return StringTrimedContext;
		DatabaseDescriptionPOJO databaseDescriptionPojo= descriptionPojo.ParentDatabase;
		return TemplateHandlerNew.
				HandleTemplate(StringTrimedContext, databaseDescriptionPojo,
						descriptionPojo, null );
	}
}
