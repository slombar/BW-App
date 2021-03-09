package edu.wpi.cs3733.teamO.HelperClasses;

import static org.junit.jupiter.api.Assertions.*;

import java.security.NoSuchAlgorithmException;
import org.junit.jupiter.api.Test;

public class TestEncrypter {

  @Test
  public void checkEncryptPassword() throws NoSuchAlgorithmException {
    assertEquals(
        Encrypter.encryptPassword("Hello World"), Encrypter.encryptPassword("Hello World"));
    assertNotEquals(
        Encrypter.encryptPassword("Hello World"), Encrypter.encryptPassword("HelloWorld"));
  }
}
