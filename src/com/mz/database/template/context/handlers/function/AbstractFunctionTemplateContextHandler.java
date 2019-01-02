package com.mz.database.template.context.handlers.function;

import com.mz.database.models.DatabaseDescriptionPOJO;
import com.mz.database.models.TableColumnDescriptionPOJO;
import com.mz.database.models.TableDescriptionPOJO;
import com.mz.database.template.context.handlers.AbstractTemplateContextHandler;
import com.mz.database.template.handler.TemplateHandlerNew;

public abstract class AbstractFunctionTemplateContextHandler extends AbstractTemplateContextHandler{

	DatabaseDescriptionPOJO _databaseDescriptionPojo;
	public DatabaseDescriptionPOJO getAssociatedDatabaseDescriptionPOJO(){
		return _databaseDescriptionPojo;
	}
	public void setAssociatedDatabaseDescriptionPOJO(DatabaseDescriptionPOJO pojo)
	{
		_databaseDescriptionPojo = pojo;
	}
	
	TableDescriptionPOJO _tableDescriptionPojo;
	public TableDescriptionPOJO getAssociatedTableDescriptorPOJO(){
		return _tableDescriptionPojo;
	}
	public void setAssociatedTableDescriptorPOJO(TableDescriptionPOJO pojo )
	{
		_tableDescriptionPojo = pojo;
	}
	
	TableColumnDescriptionPOJO _columnDescriptionPojo;
	public TableColumnDescriptionPOJO getAssocatedColumnDescriptionPOJO(){
		return _columnDescriptionPojo;
	}
	public void setAssociatedColumnDescriptionPOJO(TableColumnDescriptionPOJO pojo){
		_columnDescriptionPojo = pojo;
	}
	
	public String HandleTrimedContext(String StringTrimedContext) throws Exception
	{
		if(StringTrimedContext == null) return null;
		TableColumnDescriptionPOJO columnDescriptionPojo = getAssocatedColumnDescriptionPOJO();
		TableDescriptionPOJO tableDescriptionPojo = getAssociatedTableDescriptorPOJO();
		DatabaseDescriptionPOJO databaseDescriptionPojo = getAssociatedDatabaseDescriptionPOJO();
		return TemplateHandlerNew.
				HandleTemplate(StringTrimedContext, databaseDescriptionPojo,
						tableDescriptionPojo, columnDescriptionPojo);
	}
}
