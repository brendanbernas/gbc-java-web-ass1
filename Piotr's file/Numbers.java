package helperClasses;

import java.util.Random;
/*
 * This class is used as a "helper" class to store useful methods that have to do with numbers.
 * Remember! These methods are only as good you code them; not as they sound
 */
public class Numbers {
	
	/*
	 *  generateRandomNum is a method that returns a random integer ranging from 1 to 100.
	 */
    public static Integer generateRandomNum() {
    	Random rand = new Random();
    	return rand.nextInt(100) + 1;
	}
    /*
     * isPrimeNumber is a method that takes an integer as a parameter and returns a boolean flag on whether the integer is a prime number or not
     * returns true if number is prime
     */
  	public static boolean isPrimeNumber (int x) {
  		if(x % 2 == 0) {
  	     return false;
  		}
  		else {
  		 for(int i = 3; i * i <= x; i+=2) {
  			 if(x % i == 0)
  				 return false;
  		 }
  		}
  		return true;
  	/*
  	 * isNumericSpace is a method that takes in a character sequence as a parameter and returns a boolean flag on whether there is a number inside the character sequence with no spaces
  	 * returns true if there is no space (' ') in character sequence and only numbers
  	 */
  	}
  	public static boolean isNumericSpace(CharSequence cs) {
  		if(cs == null) {
  			return false;
  		}
  		int sz = cs.length();
  		for(int i = 0; i < sz; i++) {
  			if(Character.isDigit(cs.charAt(i)) == false && cs.charAt(i) != ' ') {
  				return false;
  			}
  		}
  		return true;
  	}
  	/*
  	 * isNumeric is a method that takes in a character sequence as a parameter and returns a boolean flag on whether there is a number inside the character sequence
  	 * returns true if there is only numbers in this character sequence
  	 */
  	public static boolean isNumeric(CharSequence cs) {
  		if(cs == null || cs.length() == 0) {
  			return false;
  		}
  		int sz = cs.length();
  		for(int i = 0; i < sz; i++) {
  			if(Character.isDigit(cs.charAt(i)) == false) {
  				return false;
  			}
  		}
  		return true;
  	}
  	/*
  	 * isFactorial is a method that takes in a long number as a parameter and returns a boolean flag on whether the number is a factorial
  	 * returns true if the number is a factorial number
  	 */
  	public static boolean isFactorial(long x) {
  		if(x == 1) {
  			return true;
  		}
  		if(x * (x - 1) > x ) {
  			return true;
  		}
  		return false;
  	}
  	/*
  	 * factorialString is a method that takes in a integer parameter and returns a string with all of its factorial multiplications
  	 */
  	public static String factorialString(int x) {
  		if(x == 1) {
  			return "1";
  		}
  		return x + "*" + factorialString((x - 1)); 
  		
  	}
  	/*
  	 * factorial is a method that takes in a integer parameter and returns all of its factorial multiplications as integers
  	 */
  	public static int factorial(int x) {
  		return x == 1 ? 1 : x * factorial(x-1);
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
  	/* tryParseInt is a method that takes in a string as a parameter and returns a boolean flag on whether the string is able to be converted to a double or not
  	 * returns true if string can be converted to an double type
  	 * this method is acting as a substitute for the tryParse method in C# that does not exist in Java for some reason...
  	 */
  	public static boolean tryParseDouble(String x) {
  		try {
  			Double.parseDouble(x);
  			return true;
  		}
  		catch(NumberFormatException e) {
  			return false;
  		}
  	}
  	/*
  	 * parseInt is a method that takes in a string as a parameter and returns an integer on whether the string is able to be converted to an integer or not
  	 * returns -1 if string can't be converted to an integer type
  	 */
  	public static Integer parseInt(String x) {
  		if(tryParseInt(x))
  			return Integer.parseInt(x);
  		return -1;
  	}
  	/*
  	 * parseInt is a method that takes in a string as a parameter and returns a double on whether the string is able to be converted to a double or not
  	 * returns -1.1 if string can't be converted to double type
  	 */
  	public static Double parseDouble(String x) {
  		if(tryParseDouble(x))
  			return Double.parseDouble(x);
  		return -1.1;
  	}
  	/*
  	 * areaOfRectangle is a method calculates the area of a rectangle and returns the area as an integer based on the the two integer parameters this method takes(width and length)
  	 * returns area as a integer based on width and length as both integers
  	 */
  	public static int areaOfRectangle(int width,int length) {
  		return width * length;
  	}
  	/*
  	 *  areaOfRectangle is a method calculates the area of a rectangle and returns the area as a double based on the the two double parameters this method takes(width and length)
  	 *  returns area as a double based on the width and length as both doubles
  	 */
  	public static double areaOfRectangle(double width,double length) {
  		return width * length;
  	}
  	/*
  	 *  areaOfEllipse is a method calculates the area of an ellipse and returns the area as a double based on the the two integer parameters this method takes
  	 *  (major axisA and minor axisB)
  	 *  returns area as a double based on the major axisA and minor axisB as integers
  	 */
  	public static double areaOfEllipse(int axisA, int axisB) {
  		return Math.PI * (axisA * axisB);
  	}
  	/*
  	 * areaOfEllipse is a method calculates the area of an ellipse and returns the area as a double based on the the two double parameters this method takes
  	 * (major axisA and minor axisB)
  	 * returns area as a double based on the major axisA and minor axisB as doubles
  	 */
  	public static double areaOfEllipse(double axisA, double axisB) {
  		return Math.PI * (axisA * axisB);
  	}

}
