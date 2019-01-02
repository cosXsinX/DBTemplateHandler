package com.mz.database.template.handler.utilities;

public class StringUtilities {

	public static String getLeftPartOfSubmittedStringBeforeFirstSearchedWordOccurence
	(String submittedString , String SearchedWord)
	{
		if(submittedString == null) return null;
		if(SearchedWord == null) return null;
		if(SearchedWord.equals("")) return submittedString;
		int FirstOccurenceIndex = submittedString.indexOf(SearchedWord);
		if(FirstOccurenceIndex == -1) return submittedString;
		return submittedString.substring(0,FirstOccurenceIndex);
	}

	public static String getRightPartOfSubmittedStringAfterFirstSearchedWordOccurence
	(String submittedString, String SearchedWord)
	{
		if(submittedString == null) return null;
		if(SearchedWord == null) return null;
		if(SearchedWord.equals("")) return submittedString;
		int FirstOccurenceIndex = submittedString.indexOf(SearchedWord);
		if(FirstOccurenceIndex == -1) return submittedString;
		return submittedString.substring(FirstOccurenceIndex + SearchedWord.length(),submittedString.length());
	}
}
