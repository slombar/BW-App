package edu.wpi.cs3733.teamO.HelperClasses;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexBoi {

  /**
   * evaluates if the given input is a possible email address
   *
   * @param emailInput email to be evaluated
   * @return true if valid email; false otherwise
   */
  public static boolean checkEmail(String emailInput) {
    Pattern emailPattern =
        Pattern.compile(
            "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
    Matcher emailMatcher = emailPattern.matcher(emailInput);
    return emailMatcher.matches();
  }

  /**
   * evaluates if the given input is a possible phone number
   *
   * @param phoneNumInput phone Number to be checked
   * @return true if the phone number is valid; false otherwise
   */
  public static boolean checkPhoneNum(String phoneNumInput) {
    Pattern phoneNumPattern =
        Pattern.compile("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$");
    Matcher phoneNumMatcher = phoneNumPattern.matcher(phoneNumInput);
    return phoneNumMatcher.matches();
  }
}
