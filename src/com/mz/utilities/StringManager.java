package com.mz.utilities;

public class StringManager {
	public static int countCharInString(String analyzedString,char searchedCharacter)
	{
		if(analyzedString == null) return -1;
		if(analyzedString.length()<1) return 0;
		int currentCharacterIndex;
		char currentCharacter;
		int analyzedStringLength = analyzedString.length();
		int counter = 0;
		for(currentCharacterIndex=0; currentCharacterIndex<analyzedStringLength ;currentCharacterIndex++)
		{
			currentCharacter= analyzedString.charAt(currentCharacterIndex);
			if(currentCharacter == searchedCharacter) counter++;
		}
		return counter;
	}
	
	public static int countStringInString(String analyzedString, String searchedString){
		if(analyzedString == null) return -1;
		String[] splittedAnalyzedString = analyzedString.split(searchedString);
		int result = splittedAnalyzedString.length;
		if(result>0) result--;
		return result;
	}
}
