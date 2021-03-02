package edu.wpi.cs3733.teamO.Database;

import edu.wpi.cs3733.teamO.HelperClasses.Encrypter;
import edu.wpi.cs3733.teamO.UserTypes.User;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserHandling {

  private static String username;

  public static ObservableList<User> getUsers() {

    ObservableList<User> userList = FXCollections.observableArrayList();

    try {
      String query = "SELECT * FROM Requests";
      // database statement to grab values
      PreparedStatement pstmt = null;
      pstmt = DatabaseConnection.getConnection().prepareStatement(query);
      // returns the results from query
      ResultSet rset = pstmt.executeQuery();

      // temp variables for assignment
      String reqID = "";
      String requestedBy = "";
      String fulfilledBy = "";
      Date dateRequested = new Date();
      Date dateNeeded = new Date();
      String requestType = "";
      String location = "";
      String summary = "";
      String para1 = "";
      String para2 = "";
      String para3 = "";

      // grab everything from the result set and add to observable list for processing
      while (rset.next()) {
        reqID = rset.getString("requestID");
        requestedBy = rset.getString("requestedBy");
        fulfilledBy = rset.getString("fulfilledBy");
        dateRequested = rset.getDate("dateRequested");
        dateNeeded = rset.getDate("dateNeeded");
        requestType = rset.getString("reqtype");
        location = rset.getString("location");
        summary = rset.getString("summary");
        para1 = rset.getString("para1");
        para2 = rset.getString("para2");
        para3 = rset.getString("para3");

        User user = new User();

        System.out.println(
            "Retrieved this from Services: "
                + reqID
                + ", "
                + requestedBy
                + ", "
                + fulfilledBy
                + ", "
                + dateRequested.toString()
                + ", "
                + dateNeeded.toString()
                + ", "
                + requestType
                + ", "
                + location
                + ", "
                + summary
                + ", "
                + para1
                + ", "
                + para2
                + ", "
                + para3);

        userList.add(user);
      }

      // must close these for update to occur
      rset.close();
      pstmt.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return userList;
  }

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
      String username, String password, String email, String fName, String lName)
      throws SQLException {

    String encodedPass = null;
    try {
      encodedPass = Encrypter.encryptPassword(password);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }

    String query =
        "INSERT INTO USERS VALUES('"
            + username
            + "', '"
            + encodedPass
            + "', '"
            + email
            + "', '"
            + fName
            + "', '"
            + lName
            + "', "
            + false
            + ", "
            + false
            + ")";

    System.out.println("CREATE ACCT QUERY: " + query);

    PreparedStatement preparedStmt = null;
    try {
      preparedStmt = DatabaseConnection.getConnection().prepareStatement(query);
      preparedStmt.execute();
      preparedStmt.close();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
      throw new SQLException();
    }
  }

  public static void createEmployee(
      String username, String password, String email, String fName, String lName, boolean admin)
      throws SQLException {

    String encodedPass = null;
    try {
      encodedPass = Encrypter.encryptPassword(password);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }

    String query =
        "INSERT INTO USERS VALUES('"
            + username
            + "', '"
            + encodedPass
            + "', '"
            + email
            + "', '"
            + fName
            + "', '"
            + lName
            + "', "
            + true
            + ", "
            + admin
            + ")";

    System.out.println("CREATE ACCT QUERY: " + query);

    PreparedStatement preparedStmt = null;
    try {
      preparedStmt = DatabaseConnection.getConnection().prepareStatement(query);
      preparedStmt.execute();
      preparedStmt.close();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
      throw new SQLException();
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
    setUsername(u);

    String encodedPass = "";
    try {
      encodedPass = Encrypter.encryptPassword(p);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }

    p = encodedPass;

    String vu = "";
    String vp = "";

    String query = "SELECT * FROM USERS WHERE USERNAME = '" + u + "' AND PASSWORD = '" + p + "'";

    PreparedStatement pstmt = null;
    try {
      pstmt = DatabaseConnection.getConnection().prepareStatement(query);
      ResultSet res = pstmt.executeQuery();
      res.next();

      vu = res.getString("USERNAME");
      vp = res.getString("PASSWORD");

      res.close();
      pstmt.close();

    } catch (SQLException throwables) {
      throwables.printStackTrace();
      throw new SQLException();
    }
  }

  public static void loginEmployee(String u, String p) throws SQLException {
    setUsername(u);

    String encodedPass = "";
    try {
      encodedPass = Encrypter.encryptPassword(p);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    p = encodedPass;

    String vu = "";
    String vp = "";

    String query =
        "SELECT * FROM USERS WHERE USERNAME = '"
            + u
            + "' AND PASSWORD = '"
            + p
            + "' AND EMPLOYEE = "
            + true;

    try {
      PreparedStatement pstmt = null;
      pstmt = DatabaseConnection.getConnection().prepareStatement(query);
      ResultSet res = pstmt.executeQuery();
      res.next();

      vu = res.getString("USERNAME");
      vp = res.getString("PASSWORD");

      res.close();
      pstmt.close();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
      throw new SQLException();
    }
  }

  public static boolean getAdmin() {
    boolean b = false;

    String query = "SELECT * FROM USERS WHERE USERNAME = '" + username + "'";

    try {
      PreparedStatement pstmt = null;
      pstmt = DatabaseConnection.getConnection().prepareStatement(query);
      ResultSet res = pstmt.executeQuery();
      res.next();

      b = res.getBoolean("admin");
      System.out.println("Admin check : " + b);

      res.close();
      pstmt.close();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

    return b;
  }

  public static boolean getEmployee() {
    boolean b = false;

    String query = "SELECT * FROM USERS WHERE USERNAME = '" + username + "'";

    try {
      PreparedStatement pstmt = null;
      pstmt = DatabaseConnection.getConnection().prepareStatement(query);
      ResultSet res = pstmt.executeQuery();
      res.next();

      b = res.getBoolean("admin");
      System.out.println("Employee check : " + b);

      res.close();
      pstmt.close();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

    return b;
  }

  public static String getUsername() {
    return username;
  }

  public static void setUsername(String u) {
    username = u;
  }
}
