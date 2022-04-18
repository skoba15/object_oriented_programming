package assign1;

import java.util.HashSet;
import java.util.Set;

// CS108 HW1 -- String static methods

public class StringCode {

	/**
	 * Given a string, returns the length of the largest run.
	 * A a run is a series of adajcent chars that are the same.
	 * @param str
	 * @return max run length
	 */
	public static int maxRun(String str) {
		int maxLength=0;
		for(int i=0; i<str.length(); i++){
			char currentChar=str.charAt(i);
			int currentLength=0;
			for(int j=i; j<str.length(); j++){
				if(str.charAt(j)!=currentChar){
					break;
				}
				else{
					currentLength++;
				}
			}
			maxLength=Math.max(currentLength, maxLength);
			i+=currentLength-1;
		}
		return maxLength;
	}

	
	/**
	 * Given a string, for each digit in the original string,
	 * replaces the digit with that many occurrences of the character
	 * following. So the string "a3tx2z" yields "attttxzzz".
	 * @param str
	 * @return blown up string
	 */
	
	
	public static boolean isDigit(char c){
		return (c>='0' && c<='9');
	}
	
	
	
	public static String blowup(String str) {
		String result="";
		for(int i=0; i<str.length(); i++){
			char currentChar=str.charAt(i);
			if(isDigit(currentChar) && i<str.length()-1){
				char nextChar=str.charAt(i+1);
				int occurence=(int)(currentChar-'0');
				for(int j=0; j<occurence; j++){
					result+=nextChar;
				}
			}
			else{
				if(!isDigit(currentChar)){
					result+=str.charAt(i);
				}
				
			}
		}
		return result; 
	}
	
	/**
	 * Given 2 strings, consider all the substrings within them
	 * of length len. Returns true if there are any such substrings
	 * which appear in both strings.
	 * Compute this in linear time using a HashSet. Len will be 1 or more.
	 */
	public static boolean stringIntersect(String a, String b, int len) {
		HashSet<String> wordsSet=new HashSet();
		for(int i=0; i<a.length()-len+1; i++){
			wordsSet.add(a.substring(i, i+len));
		}
		for(int i=0; i<b.length()-len+1; i++){
			String curString=b.substring(i, i+len);
			if(wordsSet.contains(curString)){
				return true;
			}
		}
		return false; 
	}
}
