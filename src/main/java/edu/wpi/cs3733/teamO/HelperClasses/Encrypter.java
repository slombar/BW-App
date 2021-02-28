package edu.wpi.cs3733.teamO.HelperClasses;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Encrypter {

  public static String encryptPassword(String text) throws NoSuchAlgorithmException {
    text.getBytes(StandardCharsets.UTF_8);
    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    byte[] encodedBytes = digest.digest(text.getBytes(StandardCharsets.UTF_8));
    String encodedString = Base64.getEncoder().encodeToString(encodedBytes);
    System.out.println(encodedString);
    return encodedString;
  }
}
