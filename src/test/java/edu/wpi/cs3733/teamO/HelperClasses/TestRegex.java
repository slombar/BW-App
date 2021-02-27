package edu.wpi.cs3733.teamO.HelperClasses;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TestRegex {

  @Test
  public void regexTester() {
    assertTrue(RegexBoi.checkEmail("sidney@silkfields.com"));
    assertTrue(RegexBoi.checkEmail("Balls@balls.yeaH"));
    assertTrue(RegexBoi.checkEmail("sparks51.sp@gmail.com"));
    assertTrue(RegexBoi.checkEmail("wwong2@wpi.edu"));
    assertFalse(RegexBoi.checkEmail("poop"));
    assertFalse(RegexBoi.checkEmail("This is not an email adress."));
    assertFalse(RegexBoi.checkEmail("bilybobJoe.p"));
    assertFalse(RegexBoi.checkEmail(""));
  }
}
