package edu.wpi.cs3733.teamO.Database;

import edu.wpi.cs3733.teamO.HelperClasses.Encrypter;
import edu.wpi.cs3733.teamO.UserTypes.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserHandling {

  private static String username;
  private static Boolean isLoggedIn;
  private static String fName;
  private static String parkingSpot;

  public static String getParkingSpot() {
    return parkingSpot;
  }

  public static void setParkingSpot(String parkingSpot) {
    UserHandling.parkingSpot = parkingSpot;
  }

  /**
   * Delete the given user from the database based on Username
   *
   * @param uname
   */
  public static void deleteUser(String uname) {

    String query = "DELETE FROM USERS WHERE username = '" + uname + "'";

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
   * retrieve all users from database
   *
   * @return
   */
  public static ObservableList<User> getUsers() {

    ObservableList<User> userList = FXCollections.observableArrayList();

    try {
      String query = "SELECT * FROM Users";
      // database statement to grab values
      PreparedStatement pstmt = null;
      pstmt = DatabaseConnection.getConnection().prepareStatement(query);
      // returns the results from query
      ResultSet rset = pstmt.executeQuery();

      // temp variables for assignment
      String uname = "";
      String pword = "";
      String email = "";
      String fName = "";
      String lName = "";
      boolean employee = false;
      boolean admin = false;

      // grab everything from the result set and add to observable list for processing
      while (rset.next()) {
        uname = rset.getString("USERNAME");
        pword = rset.getString("PASSWORD");
        email = rset.getString("EMAIL");
        fName = rset.getString("fName");
        lName = rset.getString("lName");
        employee = rset.getBoolean("EMPLOYEE");
        admin = rset.getBoolean("ADMIN");

        User user = new User(uname, pword, email, fName, lName, employee, admin);

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

    // Added by sam to make username and email case insensitive:
    username = username.toLowerCase();
    email = email.toLowerCase();

    String encodedPass = null;
    encodedPass = Encrypter.encryptPassword(password);

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

  /**
   * Make a brand new employee in the Db
   *
   * @param username
   * @param password
   * @param email
   * @param fName
   * @param lName
   * @param admin
   * @throws SQLException
   */
  public static void createEmployee(
      String username, String password, String email, String fName, String lName, boolean admin)
      throws SQLException {

    // Added by sam to make username and email case insensitive:
    username = username.toLowerCase();
    email = email.toLowerCase();

    String encodedPass = null;
    encodedPass = Encrypter.encryptPassword(password);

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

    // Added by sam to make username and email case insensitive:
    u = u.toLowerCase();

    setLoginStatus(true);
    setUsername(u);

    String encodedPass = "";
    encodedPass = Encrypter.encryptPassword(p);

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

  /**
   * logs in the employee from the database
   *
   * @param u
   * @param p
   * @throws SQLException
   */
  public static void loginEmployee(String u, String p) throws SQLException {
    // Added by sam to make username and email case insensitive:
    u = u.toLowerCase();

    // setFirstName(fName);
    setLoginStatus(true);
    setUsername(u);

    String encodedPass = "";
    encodedPass = Encrypter.encryptPassword(p);

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

  /**
   * get true if the current user is an admin
   *
   * @return
   */
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

  /**
   * get true if the current user is an employee
   *
   * @return
   */
  public static boolean getEmployee() {
    boolean b = false;

    String query = "SELECT * FROM USERS WHERE USERNAME = '" + username + "'";

    try {
      PreparedStatement pstmt = null;
      pstmt = DatabaseConnection.getConnection().prepareStatement(query);
      ResultSet res = pstmt.executeQuery();
      res.next();

      b = res.getBoolean("employee");
      System.out.println("Employee check : " + b);

      res.close();
      pstmt.close();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

    return b;
  }

  /**
   * assign an employee to a service request
   *
   * @param reqID
   * @param employee
   */
  public static void assignEmployee(int reqID, String employee) {

    try {
      RequestHandling.setStatus(reqID, "Assigned");
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

    String query =
        "UPDATE Requests SET fulfilledBy = '" + employee + "' WHERE requestID = " + reqID;
    PreparedStatement preparedStmt = null;

    try {
      preparedStmt = DatabaseConnection.getConnection().prepareStatement(query);
      preparedStmt.executeUpdate();
      preparedStmt.close();

    } catch (SQLException throwables) {
      throwables.printStackTrace();
      return;
    }
    System.out.println(
        "Request with ID: " + reqID + "has been assigned employee: " + employee + ".");
  }

  public static String getUsername() {
    return username;
  }

  // gettas and settas
  public static void setUsername(String u) {
    username = u;
  }

  public static Boolean getLoginStatus() {
    return isLoggedIn;
  }

  public static void setLoginStatus(Boolean b) {
    isLoggedIn = b;
  }

  public static String getFirstName() {
    return fName;
  }

  public static void setFirstName(String f) {
    fName = f;
  }
}
