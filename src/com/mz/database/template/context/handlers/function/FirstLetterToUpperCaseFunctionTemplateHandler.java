package com.mz.database.template.context.handlers.function;

public class FirstLetterToUpperCaseFunctionTemplateHandler extends AbstractFunctionTemplateContextHandler {

	public final static String START_CONTEXT_WORD = "{:TDB:FUNCTION:FIRST:CHARACTER:TO:UPPER:CASE(";
	public final static String END_CONTEXT_WORD = ")::}";
	
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
			String TrimedStringContext = 
					TrimContextFromContextWrapper(StringContext);
			TrimedStringContext = HandleTrimedContext(TrimedStringContext);
			//Function performed operation
			if(TrimedStringContext.equals("")) return TrimedStringContext;
			String s1 = TrimedStringContext.substring(0, 1).toUpperCase();
		    return s1 + TrimedStringContext.substring(1);
	}

	
	@Override
	public boolean isStartContextAndEndContextAnEntireWord() {
		return false;
	}
}
