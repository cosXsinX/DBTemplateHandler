package com.mz.database.template.context.handlers.database;

import com.mz.database.models.DatabaseDescriptionPOJO;
import com.mz.database.template.context.handlers.AbstractTemplateContextHandler;
import com.mz.database.template.handler.TemplateHandlerNew;

public abstract class AbstractDatabaseTemplateContextHandler extends AbstractTemplateContextHandler{

	DatabaseDescriptionPOJO _databaseDescriptionPojo;
	public DatabaseDescriptionPOJO getAssociatedDatabaseDescriptorPOJO(){
		return _databaseDescriptionPojo;
	}
	
	public void setAssociatedDatabaseDescriptorPOJO(DatabaseDescriptionPOJO pojo )
	{
		_databaseDescriptionPojo = pojo;
	}
	
	public String HandleTrimedContext(String StringTrimedContext) throws Exception
	{
		if(StringTrimedContext == null) return null;
		DatabaseDescriptionPOJO descriptionPojo = getAssociatedDatabaseDescriptorPOJO();
		if(descriptionPojo == null) return StringTrimedContext;
		return TemplateHandlerNew.
				HandleTemplate(StringTrimedContext, descriptionPojo,
						null, null);
	}
}
