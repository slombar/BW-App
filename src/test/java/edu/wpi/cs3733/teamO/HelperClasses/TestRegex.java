package edu.wpi.cs3733.teamO.HelperClasses;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TestRegex {

  @Test
  public void checkEmailTester() {
    assertTrue(RegexBoi.checkEmail("test@testing.com"));
    assertTrue(RegexBoi.checkEmail("sparks51.sp@gmail.com"));
    assertTrue(RegexBoi.checkEmail("wwong2@wpi.edu"));
    assertFalse(RegexBoi.checkEmail("poop"));
    assertFalse(RegexBoi.checkEmail("This is not an email adress."));
    assertFalse(RegexBoi.checkEmail("bilybobJoe.p"));
    assertFalse(RegexBoi.checkEmail(""));
    assertFalse(RegexBoi.checkEmail("NaN"));
    assertFalse(RegexBoi.checkEmail("\""));
  }

  @Test
  public void checkPhoneNumTester() {
    assertTrue(RegexBoi.checkPhoneNum("123-456-7890"));
    assertTrue(RegexBoi.checkPhoneNum("(123) 456-7890"));
    assertTrue(RegexBoi.checkPhoneNum("123 456 7890"));
    assertTrue(RegexBoi.checkPhoneNum("123.456.7890"));
    assertTrue(RegexBoi.checkPhoneNum("+91 (123) 456-7890"));
    assertTrue(RegexBoi.checkPhoneNum("1234567890"));
    assertFalse(RegexBoi.checkPhoneNum("1234567"));
    assertFalse(RegexBoi.checkPhoneNum("123-4567"));
    assertFalse(RegexBoi.checkPhoneNum("123456g890"));
    assertFalse(RegexBoi.checkPhoneNum("pogg"));
  }
}
