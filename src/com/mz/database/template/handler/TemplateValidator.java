package com.mz.database.template.handler;

import java.util.Stack;

import com.mz.database.template.context.handlers.TemplateContextHandlerPackageProvider;
import com.mz.database.template.handler.utilities.StringUtilities;

public class TemplateValidator {

	public static boolean TemplateStringValidation(String ValidatedTemplateString)
	{
		if(!ContextOpeningAndClosureTemplateStringValidation(ValidatedTemplateString)) 
			return false;
		if(!ContextDepthTemplateStringValidation(ValidatedTemplateString))
			return false;
		return true;
	}
	
	private static boolean ContextOpeningAndClosureTemplateStringValidation
		(String ValidatedTemplateString)
	{
		int startContextCount = TemplateContextHandlerPackageProvider.countStartContextWordInSubmittedString(ValidatedTemplateString);
		int endContextCount = TemplateContextHandlerPackageProvider.countEndContextWordInSubmittedString(ValidatedTemplateString);
		return startContextCount == endContextCount;
	}
	
	private static boolean ContextDepthTemplateStringValidation
		(String ValidatedTemplateString)
	{
		String currentHandledTemplateString = ValidatedTemplateString;
		Stack<String> StartContextWordStack = new Stack<String>();
		
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
					currentHandledTemplateString = StringUtilities.
							getRightPartOfSubmittedStringAfterFirstSearchedWordOccurence
								(currentHandledTemplateString, earliestStartContextWord);
				}
				else if(earliestStartContextWordIndex > earliestEndContextWordIndex)
				{
					if(StartContextWordStack.isEmpty()) return false;
					String lastStartContextWord = StartContextWordStack.pop();
					String associatedEndContextWord = 
							TemplateContextHandlerPackageProvider.
								getStartContextCorrespondingEndContextWrapper(lastStartContextWord);
					if(!associatedEndContextWord.equals(earliestEndContextWord))
					{
						return false;
					}
					currentHandledTemplateString = 
							StringUtilities.
								getRightPartOfSubmittedStringAfterFirstSearchedWordOccurence
									(currentHandledTemplateString,earliestEndContextWord);
				}
			}
			else if(earliestEndContextWord != null)
			{
				if(StartContextWordStack.isEmpty()) return false;
				String lastStartContextWord = StartContextWordStack.pop();
				String associatedEndContextWord = 
						TemplateContextHandlerPackageProvider.
							getStartContextCorrespondingEndContextWrapper(lastStartContextWord);
				if(!associatedEndContextWord.equals(earliestEndContextWord))
				{
					return false;
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
		if(!StartContextWordStack.isEmpty()) return false;
		return true;
	}
	
}
