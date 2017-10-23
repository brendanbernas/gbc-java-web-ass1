package helperClasses;


public class EmployeeValidations {
	/*
	 * isMissingOrEmpty method takes a string as a parameter and returns a boolean flag on whether the string is empty or not
	 * returns true if string is empty; returns false if string is not empty
	 */
	public static boolean isMissingOrEmpty(String x) {
		if(x == null || x.trim().equals("")) {
			return true;
		}
		return false;
	}
	/*
	 * isAlphabetic method takes a string as a parameter and returns a boolean value on whether the string has any non alphabetic characters in it'
	 * returns true if string only has alphabetic characters'
	 */
	public static boolean isAlphabetic(String x) {
		if(x == null || x.trim().equals("")) {
			return false;
		}
		if(x.matches("[a-zA-Z]+")) {
			return true;
		}
		return false;
	}
	/*
  	 * hasNumbers is a method that takes in a character sequence as a parameter and returns a boolean flag on whether there is a number inside the character sequence
  	 * returns true if there is only numbers in this character sequence
  	 */
	public static boolean hasNumbers(CharSequence cs) {
  		if(cs == null || cs.length() == 0) {
  			return false;
  		}
  		int sz = cs.length();
  		for(int i = 0; i < sz; i++) {
  			if(Character.isDigit(cs.charAt(i)) == true) {
  				return true;
  			}
  		}
  		return false;
  	}
	/*
  	 * tryParseInt is a method that takes in a string as a parameter and returns a boolean flag on whether the string is able to be converted to an integer or not
  	 * returns true if string can be converted to an integer type
  	 * this method is acting as a substitute for the tryParse method in C# that does not exist in Java for some reason...
  	 */
  	public static boolean tryParseInt(String x) {
  		try {
  			Integer.parseInt(x);
  			return true;
  		}
  		catch(NumberFormatException e) {
  			return false;
  		}
  	}
  	/*
	 * isEmail method takes a string as a parameter and returns a boolean value on whether the string is a valid email format
	 * returns true if string is a valid email using regular expression
	 */
	public static boolean isEmail(String x) {
		if(x != null && x.matches("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")) {
			return true;
		}
		return false;
	}
	/*
	 * headWithTitle method takes a string as a parameter and returns a string that contains a html header and title tag
	 */
	public static String headWithTitle(String title)
	  {
	    return 
	    
	      "<!DOCTYPE html>\n<html> \n<head><title>" + title + "</title></head>\n";
	  }
}
