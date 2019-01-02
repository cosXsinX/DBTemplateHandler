package com.mz.database.template.context.handlers.column;

import java.util.HashMap;
import java.util.Map;

import com.mz.database.descriptors.AbstractDatabaseDescriptor;
import com.mz.database.descriptors.SQLLiteDatabaseDescriptor;
import com.mz.database.models.TableColumnDescriptionPOJO;

public class ColumnValueConvertTypeColumnContextHandler extends AbstractColumnTemplateContextHandler {

	private final static String START_CONTEXT_WORD = "{:TDB:TABLE:COLUMN:FOREACH:CURRENT:CONVERT:TYPE(";
	private final static String END_CONTEXT_WORD = ")::}";
	
	public final static String TEMPLATE_TABLE_WORD = START_CONTEXT_WORD + END_CONTEXT_WORD;
	
	@Override
	public String getStartContextStringWrapper() {
		return START_CONTEXT_WORD;
	}

	@Override
	public String getEndContextStringWrapper() {
		return END_CONTEXT_WORD;
	}

	Map<String,AbstractConversionHandler> ConversionHandlerMap = null;
	private boolean InitConversionHandlerMap()
	{
		if(ConversionHandlerMap != null) return true;
		ConversionHandlerMap = new HashMap<String, ColumnValueConvertTypeColumnContextHandler.AbstractConversionHandler>();
		JavaConversionHandler javaConversionHandler = new JavaConversionHandler();
		ConversionHandlerMap.put(javaConversionHandler.getTargetEnvironmentKey(), javaConversionHandler);
		return (ConversionHandlerMap != null);
	}
	
	private AbstractConversionHandler GetConversionHandlerForEnvironmentDestinationKey(String EnvironmentDestinationKey)
	{
		if(EnvironmentDestinationKey == null) return null;
		if(!InitConversionHandlerMap()) return null;
		if(!ConversionHandlerMap.containsKey(EnvironmentDestinationKey.toUpperCase()))return null;
		return ConversionHandlerMap.get(EnvironmentDestinationKey.toUpperCase());
	}
	
	@Override
	public String processContext(String StringContext) throws Exception {
		if(StringContext == null)
			throw new Exception("The provided StringContext is null");
		TableColumnDescriptionPOJO descriptionPojo = getAssociatedColumnDescriptorPOJO();
		if(descriptionPojo == null) 
			throw new Exception("The AssociatedColumnDescriptorPOJO is not set");
		
		String TrimedStringContext = TrimContextFromContextWrapper(StringContext);
		if(TrimedStringContext == "")
			throw new Exception("There is a problem with the function provided in template '" +
					(START_CONTEXT_WORD +TrimedStringContext+ END_CONTEXT_WORD) + 
						"' -> The value parameter cannot be empty" );
		AbstractConversionHandler abstractConversionHandler =
				GetConversionHandlerForEnvironmentDestinationKey
					(TrimedStringContext);
		return abstractConversionHandler.ConvertType(descriptionPojo.get_TypeStr());//TODO Here provide database descriptor throughout pojo object
	}
	
	private abstract class AbstractConversionHandler
	{
		public abstract AbstractDatabaseDescriptor getDatabaseDescriptor();
		
		public abstract void setDatabaseDescriptor(AbstractDatabaseDescriptor descriptor);
		
		public abstract String getTargetEnvironmentKey();
		
		public abstract String ConvertType(String ConvertedTypeString);
	}
	
	private class JavaConversionHandler extends AbstractConversionHandler
	{
		
		private final static String TARGET_CONVERSION_KEY = "JAVA";
		@Override
		public String getTargetEnvironmentKey() {
			return TARGET_CONVERSION_KEY.toUpperCase();
		}

		@Override
		public String ConvertType(String ConvertedTypeString) {
			if(ConvertedTypeString == null) return null;
			if(ConvertedTypeString.equals("")) return "";
			return _databaseDescriptor.ConvertTypeToJava(ConvertedTypeString);
		}

		private AbstractDatabaseDescriptor _databaseDescriptor = 
				new SQLLiteDatabaseDescriptor();// TODO Make an other initialization
		@Override
		public AbstractDatabaseDescriptor getDatabaseDescriptor() {
			return _databaseDescriptor;
		}

		@Override
		public void setDatabaseDescriptor(AbstractDatabaseDescriptor descriptor) {
			_databaseDescriptor = descriptor;
		}
		
	}

	@Override
	public boolean isStartContextAndEndContextAnEntireWord() {
		return false;
	}

}
