package com.mz.database.template.handler;

import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import com.mz.database.template.context.handlers.TemplateContextHandlerPackageProvider;
import com.mz.database.template.handler.utilities.StringUtilities;

public class TemplateValidatorForEditor extends TemplateValidator {

	
	
	public static Queue<AbstractMap.SimpleEntry<Integer,Integer>> ProvideContextDelimiterWordIndexesQueue
		(String ValidatedTemplateString)
	{
		String currentHandledTemplateString = ValidatedTemplateString;
		Stack<String> StartContextWordStack = new Stack<String>();
		Queue<AbstractMap.SimpleEntry<Integer, Integer>> result = new LinkedList<AbstractMap.SimpleEntry<Integer,Integer>>();
		
		String earliestStartContextWord = TemplateContextHandlerPackageProvider.
			getHandlerStartContextWordAtEarliestPositionInSubmittedString(currentHandledTemplateString);
		
		String earliestEndContextWord = TemplateContextHandlerPackageProvider.
				getHandlerEndContextWordAtEarliestPositionInSubmittedString(currentHandledTemplateString);
		
		int currentHandledTemplateStringStartIndexPosition = 0;
		while(earliestStartContextWord != null || earliestEndContextWord!= null)
		{
			
			if(earliestStartContextWord != null && earliestEndContextWord!= null)
			{
				int earliestStartContextWordIndex = currentHandledTemplateString.indexOf(earliestStartContextWord);
				int earliestEndContextWordIndex = currentHandledTemplateString.indexOf(earliestEndContextWord);
				
				if(earliestStartContextWordIndex < earliestEndContextWordIndex)
				{
					int earliestStartContextWordStartIndexInSubmittedString = currentHandledTemplateStringStartIndexPosition +earliestStartContextWordIndex;
					int earliestStartContextWordEndIndexInSubmittedString = earliestStartContextWordStartIndexInSubmittedString + earliestStartContextWord.length();
					
					StartContextWordStack.push(earliestStartContextWord);
					result.add(new SimpleEntry<Integer, Integer>
						(earliestStartContextWordStartIndexInSubmittedString, 
								earliestStartContextWordEndIndexInSubmittedString));
					
					currentHandledTemplateString = StringUtilities.
							getRightPartOfSubmittedStringAfterFirstSearchedWordOccurence
								(currentHandledTemplateString, earliestStartContextWord);
					
					currentHandledTemplateStringStartIndexPosition = earliestStartContextWordEndIndexInSubmittedString;
				}
				else if(earliestStartContextWordIndex > earliestEndContextWordIndex)
				{
					if(StartContextWordStack.isEmpty()) return result;
					String lastStartContextWord = StartContextWordStack.pop();
					String associatedEndContextWord = 
							TemplateContextHandlerPackageProvider.
								getStartContextCorrespondingEndContextWrapper(lastStartContextWord);
					if(!associatedEndContextWord.equals(earliestEndContextWord))
					{
						return result;
					}
					int earliestEndContextWordStartIndexInSubmittedString = currentHandledTemplateStringStartIndexPosition +earliestEndContextWordIndex;
					int earliestEndContextWordEndIndexInSubmittedString = earliestEndContextWordStartIndexInSubmittedString + earliestEndContextWord.length();
					
					result.add(new SimpleEntry<Integer, Integer>
						(earliestEndContextWordStartIndexInSubmittedString, 
								earliestEndContextWordEndIndexInSubmittedString));
				
					
					currentHandledTemplateString = 
							StringUtilities.
								getRightPartOfSubmittedStringAfterFirstSearchedWordOccurence
									(currentHandledTemplateString,earliestEndContextWord);
					
					currentHandledTemplateStringStartIndexPosition = earliestEndContextWordEndIndexInSubmittedString;
				}
			}
			else if(earliestEndContextWord != null)
			{
				if(StartContextWordStack.isEmpty()) return result;
				String lastStartContextWord = StartContextWordStack.pop();
				String associatedEndContextWord = 
						TemplateContextHandlerPackageProvider.
							getStartContextCorrespondingEndContextWrapper(lastStartContextWord);
				if(!associatedEndContextWord.equals(earliestEndContextWord))
				{
					return result;
				}
				int earliestEndContextWordIndex = currentHandledTemplateString.indexOf(earliestEndContextWord);
				int earliestEndContextWordStartIndexInSubmittedString = currentHandledTemplateStringStartIndexPosition + earliestEndContextWordIndex;
				int earliestEndContextWordEndIndexInSubmittedString = earliestEndContextWordStartIndexInSubmittedString + earliestEndContextWord.length();
				
				result.add(new SimpleEntry<Integer, Integer>
					(earliestEndContextWordStartIndexInSubmittedString, 
							earliestEndContextWordEndIndexInSubmittedString));
				
				currentHandledTemplateString = 
						StringUtilities.
							getRightPartOfSubmittedStringAfterFirstSearchedWordOccurence
								(currentHandledTemplateString,earliestEndContextWord);

				currentHandledTemplateStringStartIndexPosition = earliestEndContextWordEndIndexInSubmittedString;
			}
			earliestStartContextWord = TemplateContextHandlerPackageProvider.
				getHandlerStartContextWordAtEarliestPositionInSubmittedString(currentHandledTemplateString);
			
			earliestEndContextWord = TemplateContextHandlerPackageProvider.
					getHandlerEndContextWordAtEarliestPositionInSubmittedString(currentHandledTemplateString);
		}
		return result;
	}
	
}
