package edu.wpi.cs3733.teamO.HelperClasses;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import lombok.SneakyThrows;

public class Encrypter {

  /**
   * Uses SHA-256 to encrypt a given string;
   *
   * @param text Any String that should be encrypted, primarily passwords;
   * @return String form of the encrypted input;
   */
  @SneakyThrows
  public static String encryptPassword(String text) {
    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    byte[] encodedBytes = digest.digest(text.getBytes(StandardCharsets.UTF_8));
    //
    return Base64.getEncoder().encodeToString(encodedBytes);
  }
}
