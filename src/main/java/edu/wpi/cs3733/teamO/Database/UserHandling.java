package edu.wpi.cs3733.teamO.Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserHandling {

  private static String username;

  /**
   * Create new account and add info to the database
   *
   * @param username
   * @param password
   * @param email
   * @param fName
   * @param lName
   */
  public static void createAccount(
      String username, String password, String email, String fName, String lName) {
    // TODO encode the password in iteration 3 (no time!)
    String encodedPass = password;

    String query =
        "INSERT INTO Users VALUES("
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
            + "'"
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
   * @param username
   * @param password
   * @return null if login is bad, User with all info from DB if good
   */
  public static void login(String username, String password) {
    setUsername(username);

    String query =
        "SELECT * FROM Users WHERE username = '" + username + "' AND password = '" + password + "'";

    try {
      PreparedStatement pstmt = null;
      pstmt = DatabaseConnection.getConnection().prepareStatement(query);
      pstmt.execute();
      pstmt.close();

    } catch (SQLException throwables) {
      System.out.println("Login Credentials Wrong. Fail.");
      throwables.printStackTrace();
    }
  }

  /** @return */
  public static String getUsername() {
    return username;
  }

  public static void setUsername(String u) {
    username = u;
  }
}
