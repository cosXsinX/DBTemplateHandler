package com.mz.database.template.handler;

import java.util.Map;
import java.util.Stack;

import com.mz.database.models.DatabaseDescriptionPOJO;
import com.mz.database.models.TableColumnDescriptionPOJO;
import com.mz.database.models.TableDescriptionPOJO;
import com.mz.database.template.context.handlers.AbstractTemplateContextHandler;
import com.mz.database.template.context.handlers.TemplateContextHandlerPackageProvider;
import com.mz.database.template.context.handlers.column.AbstractColumnTemplateContextHandler;
import com.mz.database.template.context.handlers.database.AbstractDatabaseTemplateContextHandler;
import com.mz.database.template.context.handlers.function.AbstractFunctionTemplateContextHandler;
import com.mz.database.template.context.handlers.table.AbstractTableTemplateContextHandler;
import com.mz.database.template.handler.utilities.StringUtilities;

public class TemplateHandlerNew {

	public static String HandleTemplate(String templateString,
			DatabaseDescriptionPOJO databaseDescriptionPOJO, 
			TableDescriptionPOJO tableDescriptionPOJO, 
			TableColumnDescriptionPOJO columnDescriptionPOJO) throws Exception 
	{
		if(templateString == null) return null;
		String handlerStartContext =
			TemplateContextHandlerPackageProvider.
				getHandlerStartContextWordAtEarliestPositionInSubmittedString
					(templateString);
		if(handlerStartContext == null) return templateString;
		
		AbstractTemplateContextHandler handler = TemplateContextHandlerPackageProvider
				.getStartContextCorrespondingContextHandler(handlerStartContext);
		

		if (handler instanceof AbstractFunctionTemplateContextHandler) {
			return TemplateHandlerNew.HandleFunctionTemplate(templateString, databaseDescriptionPOJO, tableDescriptionPOJO, columnDescriptionPOJO);	
		}else if (handler instanceof AbstractDatabaseTemplateContextHandler) {
			if(databaseDescriptionPOJO == null)
				return  templateString;
			return TemplateHandlerNew.HandleDatabaseTemplate(templateString, databaseDescriptionPOJO);
		}else if(handler instanceof AbstractTableTemplateContextHandler){
			if(tableDescriptionPOJO == null)
				return  templateString;
			return TemplateHandlerNew.HandleTableTemplate(templateString, tableDescriptionPOJO);
		}else if(handler instanceof AbstractColumnTemplateContextHandler){
			if(columnDescriptionPOJO == null)
				return  templateString;
			return TemplateHandlerNew.HandleTableColumnTemplate(templateString, columnDescriptionPOJO);
		}
		return null;
	}
	
	
	public static String HandleDatabaseTemplate(
			String templateString, DatabaseDescriptionPOJO descriptionPOJO) throws Exception 
	{
		if(descriptionPOJO == null)
			return templateString;
		if(!TemplateContextHandlerPackageProvider.
				isSubmittedStringContainsADatabaseHandlerStartContextWord(templateString)) return templateString;
		if(!TemplateValidator.TemplateStringValidation(templateString)) return templateString;
		descriptionPOJO.UpdateContainedTablesParentReference();
		String currentHandledTemplateString = templateString;
		Stack<String> StartContextWordStack = new Stack<String>();
		
		Stack<StringBuilder> HanldedContextStringBuilderStakce = new Stack<StringBuilder>();
		StringBuilder currentHandledContextBufferStringBuilder = new StringBuilder();
		
		String earliestStartContextWord = TemplateContextHandlerPackageProvider.
			getHandlerStartContextWordAtEarliestPositionInSubmittedString(currentHandledTemplateString);
		String earliestEndContextWord = TemplateContextHandlerPackageProvider.
				getHandlerEndContextWordAtEarliestPositionInSubmittedString(currentHandledTemplateString);
		while(earliestStartContextWord != null || earliestEndContextWord!= null)
		{
			if(earliestStartContextWord != null && earliestEndContextWord!= null)
			{
				int earliestStartContextWordIndex = currentHandledTemplateString.indexOf(earliestStartContextWord);
				int earliestEndContextWordIndex = currentHandledTemplateString.indexOf(earliestEndContextWord);
				
				if(earliestStartContextWordIndex < earliestEndContextWordIndex)
				{
					StartContextWordStack.push(earliestStartContextWord);
					
					currentHandledContextBufferStringBuilder.
						append(StringUtilities.getLeftPartOfSubmittedStringBeforeFirstSearchedWordOccurence(currentHandledTemplateString, earliestStartContextWord));
					HanldedContextStringBuilderStakce.push(currentHandledContextBufferStringBuilder);
					currentHandledContextBufferStringBuilder = new StringBuilder();
					
					currentHandledTemplateString = StringUtilities.
							getRightPartOfSubmittedStringAfterFirstSearchedWordOccurence
								(currentHandledTemplateString, earliestStartContextWord);
					
				}
				else if(earliestStartContextWordIndex > earliestEndContextWordIndex)
				{
					
					String lastStartContextWord = StartContextWordStack.pop();
					String lastStartContextWordAssociatedEndContextWord = TemplateContextHandlerPackageProvider.getStartContextCorrespondingEndContextWrapper(lastStartContextWord);
					if(lastStartContextWordAssociatedEndContextWord.equals(earliestEndContextWord))
					{
						currentHandledContextBufferStringBuilder.append(StringUtilities.
								getLeftPartOfSubmittedStringBeforeFirstSearchedWordOccurence(currentHandledTemplateString, earliestEndContextWord));
						
						AbstractDatabaseTemplateContextHandler templateContextHandler 
							= TemplateContextHandlerPackageProvider.
								getStartContextCorrespondingDatabaseContextHandler(lastStartContextWord);
						if(templateContextHandler != null)
						{
							templateContextHandler.setAssociatedDatabaseDescriptorPOJO(descriptionPOJO);
							String processContextResult = templateContextHandler.processContext(
									lastStartContextWord + 
										currentHandledContextBufferStringBuilder.toString() + 
											earliestEndContextWord);
							currentHandledContextBufferStringBuilder = HanldedContextStringBuilderStakce.pop();
							currentHandledContextBufferStringBuilder.append(processContextResult);
						}
						else
						{
							String processContextResult = 
									lastStartContextWord + 
										currentHandledContextBufferStringBuilder.toString() + 
											earliestEndContextWord;
							currentHandledContextBufferStringBuilder = HanldedContextStringBuilderStakce.pop();
							currentHandledContextBufferStringBuilder.append(processContextResult);
						}
					}
					
					currentHandledTemplateString = 
							StringUtilities.
								getRightPartOfSubmittedStringAfterFirstSearchedWordOccurence
									(currentHandledTemplateString,earliestEndContextWord);
				}
			}
			else if(earliestEndContextWord != null)
			{
				String lastStartContextWord = StartContextWordStack.pop();
				String lastStartContextWordAssociatedEndContextWord = TemplateContextHandlerPackageProvider.getStartContextCorrespondingEndContextWrapper(lastStartContextWord);
				if(lastStartContextWordAssociatedEndContextWord.equals(earliestEndContextWord))
				{
					currentHandledContextBufferStringBuilder.append(StringUtilities.
							getLeftPartOfSubmittedStringBeforeFirstSearchedWordOccurence(currentHandledTemplateString, earliestEndContextWord));
					
					AbstractDatabaseTemplateContextHandler templateContextHandler 
						= TemplateContextHandlerPackageProvider.
							getStartContextCorrespondingDatabaseContextHandler(lastStartContextWord);
					if(templateContextHandler != null)
					{
						templateContextHandler.setAssociatedDatabaseDescriptorPOJO(descriptionPOJO);
						String processContextResult = templateContextHandler.processContext(
								lastStartContextWord + 
									currentHandledContextBufferStringBuilder.toString() + 
										earliestEndContextWord);
						currentHandledContextBufferStringBuilder = HanldedContextStringBuilderStakce.pop();
						currentHandledContextBufferStringBuilder.append(processContextResult);
					}
					else
					{
						String processContextResult = 
								lastStartContextWord + 
									currentHandledContextBufferStringBuilder.toString() + 
										earliestEndContextWord;
						currentHandledContextBufferStringBuilder = HanldedContextStringBuilderStakce.pop();
						currentHandledContextBufferStringBuilder.append(processContextResult);
					}
				}
				
				currentHandledTemplateString = 
						StringUtilities.
							getRightPartOfSubmittedStringAfterFirstSearchedWordOccurence
								(currentHandledTemplateString,earliestEndContextWord);
			}
			earliestStartContextWord = TemplateContextHandlerPackageProvider.
				getHandlerStartContextWordAtEarliestPositionInSubmittedString(currentHandledTemplateString);
			earliestEndContextWord = TemplateContextHandlerPackageProvider.
					getHandlerEndContextWordAtEarliestPositionInSubmittedString(currentHandledTemplateString);
		}
		if(!currentHandledTemplateString.equals("")) currentHandledContextBufferStringBuilder.append(currentHandledTemplateString);
		return currentHandledContextBufferStringBuilder.toString();
	}
	
	public static String HandleTableTemplate(String templateString, TableDescriptionPOJO descriptionPOJO) throws Exception 
	{
		if(descriptionPOJO == null)
			return templateString;
		if(!TemplateContextHandlerPackageProvider.
				isSubmittedStringContainsATableHandlerStartContextWord(templateString)) return templateString;
		if(!TemplateValidator.TemplateStringValidation(templateString)) return templateString;
		descriptionPOJO.UpdateContainedColumnsParentReference();
		
		String currentHandledTemplateString = templateString;
		Stack<String> StartContextWordStack = new Stack<String>();
		
		Stack<StringBuilder> HanldedContextStringBuilderStakce = new Stack<StringBuilder>();
		StringBuilder currentHandledContextBufferStringBuilder = new StringBuilder();
		
		String earliestStartContextWord = TemplateContextHandlerPackageProvider.
			getHandlerStartContextWordAtEarliestPositionInSubmittedString(currentHandledTemplateString);
		String earliestEndContextWord = TemplateContextHandlerPackageProvider.
				getHandlerEndContextWordAtEarliestPositionInSubmittedString(currentHandledTemplateString);
		while(earliestStartContextWord != null || earliestEndContextWord!= null)
		{
			if(earliestStartContextWord != null && earliestEndContextWord!= null)
			{
				int earliestStartContextWordIndex = currentHandledTemplateString.indexOf(earliestStartContextWord);
				int earliestEndContextWordIndex = currentHandledTemplateString.indexOf(earliestEndContextWord);
				
				if(earliestStartContextWordIndex < earliestEndContextWordIndex)
				{
					StartContextWordStack.push(earliestStartContextWord);
					
					currentHandledContextBufferStringBuilder.
						append(StringUtilities.getLeftPartOfSubmittedStringBeforeFirstSearchedWordOccurence(currentHandledTemplateString, earliestStartContextWord));
					HanldedContextStringBuilderStakce.push(currentHandledContextBufferStringBuilder);
					currentHandledContextBufferStringBuilder = new StringBuilder();
					
					currentHandledTemplateString = StringUtilities.
							getRightPartOfSubmittedStringAfterFirstSearchedWordOccurence
								(currentHandledTemplateString, earliestStartContextWord);
					
				}
				else if(earliestStartContextWordIndex > earliestEndContextWordIndex)
				{
					
					String lastStartContextWord = StartContextWordStack.pop();
					String lastStartContextWordAssociatedEndContextWord = TemplateContextHandlerPackageProvider.getStartContextCorrespondingEndContextWrapper(lastStartContextWord);
					if(lastStartContextWordAssociatedEndContextWord.equals(earliestEndContextWord))
					{
						currentHandledContextBufferStringBuilder.append(StringUtilities.
								getLeftPartOfSubmittedStringBeforeFirstSearchedWordOccurence(currentHandledTemplateString, earliestEndContextWord));
						
						AbstractTableTemplateContextHandler templateContextHandler 
							= TemplateContextHandlerPackageProvider.
								getStartContextCorrespondingTableContextHandler(lastStartContextWord);
						if(templateContextHandler != null)
						{
							templateContextHandler.setAssociatedTableDescriptorPOJO(descriptionPOJO);
							String processContextResult = templateContextHandler.processContext(
									lastStartContextWord + 
										currentHandledContextBufferStringBuilder.toString() + 
											earliestEndContextWord);
							currentHandledContextBufferStringBuilder = HanldedContextStringBuilderStakce.pop();
							currentHandledContextBufferStringBuilder.append(processContextResult);
						}
						else
						{
							String processContextResult = 
									lastStartContextWord + 
										currentHandledContextBufferStringBuilder.toString() + 
											earliestEndContextWord;
							currentHandledContextBufferStringBuilder = HanldedContextStringBuilderStakce.pop();
							currentHandledContextBufferStringBuilder.append(processContextResult);
						}
					}
					
					currentHandledTemplateString = 
							StringUtilities.
								getRightPartOfSubmittedStringAfterFirstSearchedWordOccurence
									(currentHandledTemplateString,earliestEndContextWord);
				}
			}
			else if(earliestEndContextWord != null)
			{
				String lastStartContextWord = StartContextWordStack.pop();
				String lastStartContextWordAssociatedEndContextWord = TemplateContextHandlerPackageProvider.getStartContextCorrespondingEndContextWrapper(lastStartContextWord);
				if(lastStartContextWordAssociatedEndContextWord.equals(earliestEndContextWord))
				{
					currentHandledContextBufferStringBuilder.append(StringUtilities.
							getLeftPartOfSubmittedStringBeforeFirstSearchedWordOccurence(currentHandledTemplateString, earliestEndContextWord));
					
					AbstractTableTemplateContextHandler templateContextHandler 
						= TemplateContextHandlerPackageProvider.
							getStartContextCorrespondingTableContextHandler(lastStartContextWord);
					if(templateContextHandler != null)
					{
						templateContextHandler.setAssociatedTableDescriptorPOJO(descriptionPOJO);
						String processContextResult = templateContextHandler.processContext(
								lastStartContextWord + 
									currentHandledContextBufferStringBuilder.toString() + 
										earliestEndContextWord);
						currentHandledContextBufferStringBuilder = HanldedContextStringBuilderStakce.pop();
						currentHandledContextBufferStringBuilder.append(processContextResult);
					}
					else
					{
						String processContextResult = 
								lastStartContextWord + 
									currentHandledContextBufferStringBuilder.toString() + 
										earliestEndContextWord;
						currentHandledContextBufferStringBuilder = HanldedContextStringBuilderStakce.pop();
						currentHandledContextBufferStringBuilder.append(processContextResult);
					}
				}
				
				currentHandledTemplateString = 
						StringUtilities.
							getRightPartOfSubmittedStringAfterFirstSearchedWordOccurence
								(currentHandledTemplateString,earliestEndContextWord);
			}
			earliestStartContextWord = TemplateContextHandlerPackageProvider.
				getHandlerStartContextWordAtEarliestPositionInSubmittedString(currentHandledTemplateString);
			earliestEndContextWord = TemplateContextHandlerPackageProvider.
					getHandlerEndContextWordAtEarliestPositionInSubmittedString(currentHandledTemplateString);
		}
		if(!currentHandledTemplateString.equals("")) currentHandledContextBufferStringBuilder.append(currentHandledTemplateString);
		return currentHandledContextBufferStringBuilder.toString();
	}
	
	public static String HandleTableColumnTemplate(String templateString, TableColumnDescriptionPOJO descriptionPOJO) throws Exception 
	{
		if(descriptionPOJO == null)
			return templateString;
		if(!TemplateContextHandlerPackageProvider.
				isSubmittedStringContainsAColumnHandlerStartContextWord(templateString)) return templateString;
		if(!TemplateValidator.TemplateStringValidation(templateString)) return templateString;
		
		String currentHandledTemplateString = templateString;
		Stack<String> StartContextWordStack = new Stack<String>();
		
		Stack<StringBuilder> HanldedContextStringBuilderStakce = new Stack<StringBuilder>();
		StringBuilder currentHandledContextBufferStringBuilder = new StringBuilder();
		
		String earliestStartContextWord = TemplateContextHandlerPackageProvider.
			getHandlerStartContextWordAtEarliestPositionInSubmittedString(currentHandledTemplateString);
		String earliestEndContextWord = TemplateContextHandlerPackageProvider.
				getHandlerEndContextWordAtEarliestPositionInSubmittedString(currentHandledTemplateString);
		while(earliestStartContextWord != null || earliestEndContextWord!= null)
		{
			if(earliestStartContextWord != null && earliestEndContextWord!= null)
			{
				int earliestStartContextWordIndex = currentHandledTemplateString.indexOf(earliestStartContextWord);
				int earliestEndContextWordIndex = currentHandledTemplateString.indexOf(earliestEndContextWord);
				
				if(earliestStartContextWordIndex < earliestEndContextWordIndex)
				{
					StartContextWordStack.push(earliestStartContextWord);
					
					currentHandledContextBufferStringBuilder.
						append(StringUtilities.getLeftPartOfSubmittedStringBeforeFirstSearchedWordOccurence(currentHandledTemplateString, earliestStartContextWord));
					HanldedContextStringBuilderStakce.push(currentHandledContextBufferStringBuilder);
					currentHandledContextBufferStringBuilder = new StringBuilder();
					
					currentHandledTemplateString = StringUtilities.
							getRightPartOfSubmittedStringAfterFirstSearchedWordOccurence
								(currentHandledTemplateString, earliestStartContextWord);
					
				}
				else if(earliestStartContextWordIndex > earliestEndContextWordIndex)
				{
					
					String lastStartContextWord = StartContextWordStack.pop();
					String lastStartContextWordAssociatedEndContextWord = TemplateContextHandlerPackageProvider.getStartContextCorrespondingEndContextWrapper(lastStartContextWord);
					if(lastStartContextWordAssociatedEndContextWord.equals(earliestEndContextWord))
					{
						currentHandledContextBufferStringBuilder.append(StringUtilities.
								getLeftPartOfSubmittedStringBeforeFirstSearchedWordOccurence(currentHandledTemplateString, earliestEndContextWord));
						
						AbstractColumnTemplateContextHandler templateContextHandler 
							= TemplateContextHandlerPackageProvider.
								getStartContextCorrespondingColumnContextHandler(lastStartContextWord);
						if(templateContextHandler != null)
						{
							templateContextHandler.setAssociatedColumnDescriptorPOJO(descriptionPOJO);
							String processContextResult = templateContextHandler.processContext(
									lastStartContextWord + 
										currentHandledContextBufferStringBuilder.toString() + 
											earliestEndContextWord);
							currentHandledContextBufferStringBuilder = HanldedContextStringBuilderStakce.pop();
							currentHandledContextBufferStringBuilder.append(processContextResult);
						}
						else
						{
							String processContextResult = 
									lastStartContextWord + 
										currentHandledContextBufferStringBuilder.toString() + 
											earliestEndContextWord;
							currentHandledContextBufferStringBuilder = HanldedContextStringBuilderStakce.pop();
							currentHandledContextBufferStringBuilder.append(processContextResult);
						}
					}
					
					currentHandledTemplateString = 
							StringUtilities.
								getRightPartOfSubmittedStringAfterFirstSearchedWordOccurence
									(currentHandledTemplateString,earliestEndContextWord);
				}
			}
			else if(earliestEndContextWord != null)
			{
				String lastStartContextWord = StartContextWordStack.pop();
				String lastStartContextWordAssociatedEndContextWord = TemplateContextHandlerPackageProvider.getStartContextCorrespondingEndContextWrapper(lastStartContextWord);
				if(lastStartContextWordAssociatedEndContextWord.equals(earliestEndContextWord))
				{
					currentHandledContextBufferStringBuilder.append(StringUtilities.
							getLeftPartOfSubmittedStringBeforeFirstSearchedWordOccurence(currentHandledTemplateString, earliestEndContextWord));
					
					AbstractColumnTemplateContextHandler templateContextHandler 
						= TemplateContextHandlerPackageProvider.
							getStartContextCorrespondingColumnContextHandler(lastStartContextWord);
					if(templateContextHandler != null)
					{
						templateContextHandler.setAssociatedColumnDescriptorPOJO(descriptionPOJO);
						String processContextResult = templateContextHandler.processContext(
								lastStartContextWord + 
									currentHandledContextBufferStringBuilder.toString() + 
										earliestEndContextWord);
						currentHandledContextBufferStringBuilder = HanldedContextStringBuilderStakce.pop();
						currentHandledContextBufferStringBuilder.append(processContextResult);
					}
					else
					{
						String processContextResult = 
								lastStartContextWord + 
									currentHandledContextBufferStringBuilder.toString() + 
										earliestEndContextWord;
						currentHandledContextBufferStringBuilder = HanldedContextStringBuilderStakce.pop();
						currentHandledContextBufferStringBuilder.append(processContextResult);
					}
				}
				
				currentHandledTemplateString = 
						StringUtilities.
							getRightPartOfSubmittedStringAfterFirstSearchedWordOccurence
								(currentHandledTemplateString,earliestEndContextWord);
			}
			earliestStartContextWord = TemplateContextHandlerPackageProvider.
				getHandlerStartContextWordAtEarliestPositionInSubmittedString(currentHandledTemplateString);
			earliestEndContextWord = TemplateContextHandlerPackageProvider.
					getHandlerEndContextWordAtEarliestPositionInSubmittedString(currentHandledTemplateString);
		}
		if(!currentHandledTemplateString.equals("")) currentHandledContextBufferStringBuilder.append(currentHandledTemplateString);
		return currentHandledContextBufferStringBuilder.toString();
	}
	
	public static String HandleFunctionTemplate(
			String templateString, DatabaseDescriptionPOJO databaseDescriptionPOJO,
			TableDescriptionPOJO tableDescriptionPOJO, TableColumnDescriptionPOJO columnDescriptionPojo) throws Exception 
	{
		if(!TemplateContextHandlerPackageProvider.
				isSubmittedStringContainsAFunctionHandlerStartContextWord(templateString)) return templateString;
		if(!TemplateValidator.TemplateStringValidation(templateString)) return templateString;
		if(databaseDescriptionPOJO != null) databaseDescriptionPOJO.UpdateContainedTablesParentReference();
		if(tableDescriptionPOJO != null) tableDescriptionPOJO.UpdateContainedColumnsParentReference();
		
		String currentHandledTemplateString = templateString;
		Stack<String> StartContextWordStack = new Stack<String>();
		
		Stack<StringBuilder> HanldedContextStringBuilderStakce = new Stack<StringBuilder>();
		StringBuilder currentHandledContextBufferStringBuilder = new StringBuilder();
		
		String earliestStartContextWord = TemplateContextHandlerPackageProvider.
			getHandlerStartContextWordAtEarliestPositionInSubmittedString(currentHandledTemplateString);
		String earliestEndContextWord = TemplateContextHandlerPackageProvider.
				getHandlerEndContextWordAtEarliestPositionInSubmittedString(currentHandledTemplateString);
		while(earliestStartContextWord != null || earliestEndContextWord!= null)
		{
			if(earliestStartContextWord != null && earliestEndContextWord!= null)
			{
				int earliestStartContextWordIndex = currentHandledTemplateString.indexOf(earliestStartContextWord);
				int earliestEndContextWordIndex = currentHandledTemplateString.indexOf(earliestEndContextWord);
				
				if(earliestStartContextWordIndex < earliestEndContextWordIndex)
				{
					StartContextWordStack.push(earliestStartContextWord);
					
					currentHandledContextBufferStringBuilder.
						append(StringUtilities.getLeftPartOfSubmittedStringBeforeFirstSearchedWordOccurence(currentHandledTemplateString, earliestStartContextWord));
					HanldedContextStringBuilderStakce.push(currentHandledContextBufferStringBuilder);
					currentHandledContextBufferStringBuilder = new StringBuilder();
					
					currentHandledTemplateString = StringUtilities.
							getRightPartOfSubmittedStringAfterFirstSearchedWordOccurence
								(currentHandledTemplateString, earliestStartContextWord);
					
				}
				else if(earliestStartContextWordIndex > earliestEndContextWordIndex)
				{
					
					String lastStartContextWord = StartContextWordStack.pop();
					String lastStartContextWordAssociatedEndContextWord = TemplateContextHandlerPackageProvider.getStartContextCorrespondingEndContextWrapper(lastStartContextWord);
					if(lastStartContextWordAssociatedEndContextWord.equals(earliestEndContextWord))
					{
						currentHandledContextBufferStringBuilder.append(StringUtilities.
								getLeftPartOfSubmittedStringBeforeFirstSearchedWordOccurence(currentHandledTemplateString, earliestEndContextWord));
						
						AbstractFunctionTemplateContextHandler templateContextHandler 
							= TemplateContextHandlerPackageProvider.
								getStartContextCorrespondingFunctionContextHandler(lastStartContextWord);
						if(templateContextHandler != null)
						{
							templateContextHandler.setAssociatedDatabaseDescriptionPOJO(databaseDescriptionPOJO);
							templateContextHandler.setAssociatedTableDescriptorPOJO(tableDescriptionPOJO);
							templateContextHandler.setAssociatedColumnDescriptionPOJO(columnDescriptionPojo);
							String processContextResult = templateContextHandler.processContext(
									lastStartContextWord + 
										currentHandledContextBufferStringBuilder.toString() + 
											earliestEndContextWord);
							currentHandledContextBufferStringBuilder = HanldedContextStringBuilderStakce.pop();
							currentHandledContextBufferStringBuilder.append(processContextResult);
						}
						else
						{
							String processContextResult = 
									lastStartContextWord + 
										currentHandledContextBufferStringBuilder.toString() + 
											earliestEndContextWord;
							currentHandledContextBufferStringBuilder = HanldedContextStringBuilderStakce.pop();
							currentHandledContextBufferStringBuilder.append(processContextResult);
						}
					}
					
					currentHandledTemplateString = 
							StringUtilities.
								getRightPartOfSubmittedStringAfterFirstSearchedWordOccurence
									(currentHandledTemplateString,earliestEndContextWord);
				}
			}
			else if(earliestEndContextWord != null)
			{
				String lastStartContextWord = StartContextWordStack.pop();
				String lastStartContextWordAssociatedEndContextWord = TemplateContextHandlerPackageProvider.getStartContextCorrespondingEndContextWrapper(lastStartContextWord);
				if(lastStartContextWordAssociatedEndContextWord.equals(earliestEndContextWord))
				{
					currentHandledContextBufferStringBuilder.append(StringUtilities.
							getLeftPartOfSubmittedStringBeforeFirstSearchedWordOccurence(currentHandledTemplateString, earliestEndContextWord));
					
					AbstractFunctionTemplateContextHandler templateContextHandler 
						= TemplateContextHandlerPackageProvider.
							getStartContextCorrespondingFunctionContextHandler(lastStartContextWord);
					if(templateContextHandler != null)
					{
						templateContextHandler.setAssociatedDatabaseDescriptionPOJO(databaseDescriptionPOJO);
						templateContextHandler.setAssociatedTableDescriptorPOJO(tableDescriptionPOJO);
						templateContextHandler.setAssociatedColumnDescriptionPOJO(columnDescriptionPojo);
						String processContextResult = templateContextHandler.processContext(
								lastStartContextWord + 
									currentHandledContextBufferStringBuilder.toString() + 
										earliestEndContextWord);
						currentHandledContextBufferStringBuilder = HanldedContextStringBuilderStakce.pop();
						currentHandledContextBufferStringBuilder.append(processContextResult);
					}
					else
					{
						String processContextResult = 
								lastStartContextWord + 
									currentHandledContextBufferStringBuilder.toString() + 
										earliestEndContextWord;
						currentHandledContextBufferStringBuilder = HanldedContextStringBuilderStakce.pop();
						currentHandledContextBufferStringBuilder.append(processContextResult);
					}
				}
				
				currentHandledTemplateString = 
						StringUtilities.
							getRightPartOfSubmittedStringAfterFirstSearchedWordOccurence
								(currentHandledTemplateString,earliestEndContextWord);
			}
			earliestStartContextWord = TemplateContextHandlerPackageProvider.
				getHandlerStartContextWordAtEarliestPositionInSubmittedString(currentHandledTemplateString);
			earliestEndContextWord = TemplateContextHandlerPackageProvider.
					getHandlerEndContextWordAtEarliestPositionInSubmittedString(currentHandledTemplateString);
		}
		if(!currentHandledTemplateString.equals("")) currentHandledContextBufferStringBuilder.append(currentHandledTemplateString);
		return currentHandledContextBufferStringBuilder.toString();
	}
	
	public static boolean IsEarliestPositionStartContextWrapperADatabaseStartContextWrapper(String templateString)
	{
		if(templateString == null) return false;
		if(templateString.equals("")) return false;
		String earliestStartContextWrapperString = TemplateContextHandlerPackageProvider.
			getColumnHandlerStartContextWordAtEarliestPositionInSubmittedString(templateString);
		Map<String,AbstractDatabaseTemplateContextHandler> map =
				TemplateContextHandlerPackageProvider.
					getStartContextWrapperStringIndexedDatabaseContextHandlerMap();
		return map.containsKey(earliestStartContextWrapperString);
	}
	
	public static boolean IsEarliestPositionStartContextWrapperATableStartContextWrapper(String templateString)
	{
		if(templateString == null) return false;
		if(templateString.equals("")) return false;
		String earliestStartContextWrapperString = TemplateContextHandlerPackageProvider.
			getColumnHandlerStartContextWordAtEarliestPositionInSubmittedString(templateString);
		Map<String,AbstractTableTemplateContextHandler> map =
				TemplateContextHandlerPackageProvider.
					getStartContextWrapperStringIndexedTableContextHandlerMap();
		return map.containsKey(earliestStartContextWrapperString);
	}
	
	public static boolean IsEarliestPositionStartContextWrapperAColumnStartContextWrapper(String templateString)
	{
		if(templateString == null) return false;
		if(templateString.equals("")) return false;
		String earliestStartContextWrapperString = TemplateContextHandlerPackageProvider.
			getColumnHandlerStartContextWordAtEarliestPositionInSubmittedString(templateString);
		Map<String,AbstractColumnTemplateContextHandler> map =
				TemplateContextHandlerPackageProvider.
					getStartContextWrapperStringIndexedColumnContextHandlerMap();
		return map.containsKey(earliestStartContextWrapperString);
	}
	
	
	
	
}
