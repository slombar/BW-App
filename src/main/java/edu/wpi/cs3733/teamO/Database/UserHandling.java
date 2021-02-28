package edu.wpi.cs3733.teamO.Database;

import edu.wpi.cs3733.teamO.HelperClasses.Encrypter;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserHandling {

  private static String username;

  /**
   * Create new account and add info to the database password is encrypted through Encryptor from
   * SAM
   *
   * @param username
   * @param password
   * @param email
   * @param fName
   * @param lName
   */
  public static void createAccount(
      String username, String password, String email, String fName, String lName) {

    String encodedPass = null;
    try {
      encodedPass = Encrypter.encryptPassword(password);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }

    String query =
        "INSERT INTO USERS VALUES("
            + "'"
            + username
            + "'"
            + ", "
            + "'"
            + encodedPass
            + "'"
            + ", "
            + "'"
            + email
            + "', "
            + "'"
            + fName
            + "', "
            + "'"
            + lName
            + "', "
            + false
            + ", "
            + false
            + ")";

    System.out.println("CREATE ACCT QUERY: " + query);
    try {
      PreparedStatement preparedStmt = null;
      preparedStmt = DatabaseConnection.getConnection().prepareStatement(query);

      preparedStmt.execute();
      preparedStmt.close();

    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  /**
   * Logs in the user utilizing DB
   *
   * @param u
   * @param p
   * @return null if login is bad, User with all info from DB if good
   */
  public static void login(String u, String p) throws SQLException {
    setUsername(username);

    String encodedPass = null;
    try {
      encodedPass = Encrypter.encryptPassword(p);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    p = encodedPass;

    String vu = "";
    String vp = "";

    String query = "SELECT * FROM USERS WHERE username = '" + u + "' AND password = '" + p + "'";

    try {
      PreparedStatement pstmt = null;
      pstmt = DatabaseConnection.getConnection().prepareStatement(query);

      ResultSet res = pstmt.executeQuery();

      vu = res.getString("username");
      vp = res.getString("password");

      pstmt.close();

    } catch (SQLException throwable) {
      System.out.println("Login Credentials Wrong. Fail.");
      throw throwable;
    }
  }

  public static void loginEmployee(String username, String password) throws SQLException {
    setUsername(username);

    String encodedPass = null;
    try {
      encodedPass = Encrypter.encryptPassword(password);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    password = encodedPass;

    String vu = "";
    String vp = "";

    String query =
        "SELECT * FROM USERS WHERE username = '"
            + username
            + "' AND password = '"
            + password
            + "' AND = employee'"
            + true
            + "'";

    try {
      PreparedStatement pstmt = null;
      pstmt = DatabaseConnection.getConnection().prepareStatement(query);

      ResultSet res = pstmt.executeQuery();

      vu = res.getString("username");
      vp = res.getString("password");

      pstmt.close();

    } catch (SQLException throwables) {
      throwables.printStackTrace();
      System.out.println("Login Credentials Wrong. Fail.");
      throw throwables;
    }
  }

  public static String getUsername() {
    return username;
  }

  public static void setUsername(String u) {
    username = u;
  }
}
