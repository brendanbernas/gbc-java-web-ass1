package helperClasses;

public class Validations {
	/*
	 * isStringEmpty method takes a string as a parameter and returns a boolean flag on whether the string is empty or not
	 * returns true if string is empty; returns false if string is not empty
	 */
	public static boolean isStringEmpty(String x) {
		if(x.isEmpty() || x == null) {
			return true;
		}
		return false;
	}
	/*
	 * 
	 */
	public static boolean isMissingOrEmpty(String x) {
		if(x == null || x.trim().equals("")) {
			return true;
		}
		return false;
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
	 * isPhoneNumber method takes a string as a parameter and returns a boolean value on whether the string is a valid phone number (US/CANADA)
	 * returns true if string is a valid phone number using regular expression 
	 */
	public static boolean isPhoneNumber(String x) {
		if(x != null && x.matches("((\\(\\d{3}\\) ?)|(\\d{3}-))?\\d{3}-\\d{4}")) {
			return true;
		}
		return false;
	}
	/*
	 * isAddress method takes a string as a parameter and returns a boolean value on whether the string resembles an address without special characters that don't appear in address'
	 * returns true if string resembles address without special characters not used in address'
	 */
	public static boolean isAddress(String x) {
		if(x != null && x.matches("[\\w',-\\\\/.\\s]")) {
			return true;
		}
		return false;
	}

}
