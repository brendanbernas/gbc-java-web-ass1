package helperClasses;

import java.util.Random;

public class HTML_Tags
{
  public HTML_Tags() {}
  
  public static String headWithTitle(String title)
  {
    return 
    
      "<!DOCTYPE html>\n<html> \n<head><title>" + title + "</title></head>\n";
  }
  
  public static Integer generateRandomNum()
  {
    Random rand = new Random();
    return Integer.valueOf(rand.nextInt(100) + 1);
  }
  
  public static String dateTimeString()
  {
    java.util.Date date = new java.util.Date();
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("hh:mm:ss dd,MM,yyyy");
    return sdf.format(date);
  }
  
  public static boolean isPrimeNumber(int x) {
    if (x % 2 == 0) {
      return false;
    }
    
    for (int i = 3; i * i <= x; i += 2) {
      if (x % i == 0) {
        return false;
      }
    }
    return true;
  }
}
