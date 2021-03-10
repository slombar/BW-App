package edu.wpi.cs3733.teamO.Database;

import edu.wpi.cs3733.teamO.HelperClasses.Encrypter;
import edu.wpi.cs3733.teamO.UserTypes.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

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

  /*
  public static void editProfile(String newPassword, String newFName, String newLname) {

    String passQuery = "UPDATE Users SET password = ? WHERE email = ?";

    // check what the user actually wants to update
    if (newPassword != null) {
      // update query in the DB

      try {
        preparedStmt = DatabaseConnection.getConnection().prepareStatement(query);
        preparedStmt.executeUpdate();
        preparedStmt.close();

      } catch (SQLException throwables) {
        throwables.printStackTrace();
        return;
      }

    } else if (newFName != null) {

    } else if (newLname != null) {

    }
  }*/

  /**
   * assign an employee to a service request
   *
   * @param reqID
   * @param employee
   */
  public static void assignEmployee(String reqID, String employee) {

    try {
      RequestHandling.setStatus(reqID, "Assigned");
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

    String query =
        "UPDATE Requests SET fulfilledBy = '"
            + employee
            + "' WHERE requestID = "
            + Integer.parseInt(reqID);
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

  public static String getTempPassword() {

    int leftLimit = 48; // numeral '0'
    int rightLimit = 122; // letter 'z'
    int targetStringLength = 10;
    Random random = new Random();

    String generatedString =
        random
            .ints(leftLimit, rightLimit + 1)
            .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
            .limit(targetStringLength)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();

    return generatedString;
  }

  /**
   * creates and sets a temporary password for the given email (sendingTo) that is then sent in an
   * email to the user associated with the sendingTo adress.
   *
   * @param sendingTo, the email of the password you want to reset bc you forgot it
   * @throws MessagingException
   */
  public static void promptForgotPassword(String sendingTo) throws MessagingException {

    // get the email from the database that matches this, to check if it exists
    String emailQuery = "SELECT * FROM USERS WHERE EMAIL = '" + sendingTo + "'";
    String passQuery = "UPDATE Users SET password = ? WHERE email = ?";
    String tempPassword = getTempPassword();
    String encryptedPassword = Encrypter.encryptPassword(tempPassword);
    String firstname = "";

    try {
      // execute DB statement
      PreparedStatement pstmt = null;
      PreparedStatement ps = null;
      // execute email query
      pstmt = DatabaseConnection.getConnection().prepareStatement(emailQuery);
      // results set for email query
      ResultSet res = pstmt.executeQuery();

      // execute update password to temp password
      ps = DatabaseConnection.getConnection().prepareStatement(passQuery);
      // set the variables in sql statement
      ps.setString(1, encryptedPassword);
      ps.setString(2, sendingTo);

      // get the first item in the result set
      res.next();

      // retrieve the first name from DB
      firstname = res.getString("fname");

      // execute, and close to fully execute
      res.close();
      pstmt.close();
      ps.executeUpdate();
      ps.close();

    } catch (SQLException throwables) {
      // Not a valid email
      System.out.println("Not a valid email");
    }

    /**
     * change the user's database password to the temporary random password (the one associated with
     * the account to reset the password for)
     */
    String from = "bwolive3733@gmail.com"; // sender's email, need default sender email !
    String host = "smtp.gmail.com"; // where email is being sent from

    Properties properties = System.getProperties(); // set up mail server
    properties.put("mail.smtp.host", host);
    properties.put("mail.smtp.port", "465");
    properties.put("mail.smtp.ssl.enable", "true");
    properties.put("mail.smtp.auth", "true");

    Session session =
        Session.getInstance(
            properties,
            new javax.mail.Authenticator() {
              protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(from, "oliveopossum69");
              }
            });

    MimeMessage message = new MimeMessage(session); // Create a default MimeMessage object

    // Setting Email Headers
    message.setFrom(new InternetAddress(from)); // Set From: header
    message.addRecipient(
        Message.RecipientType.TO, new InternetAddress(sendingTo)); // Set To: header
    message.setSubject("Your Password Reset"); // Set Subject: header

    // make new body that contains temporary password for user
    BodyPart body = new MimeBodyPart();
    Multipart multipart = new MimeMultipart();
    String m =
        "Hi "
            + firstname
            + ",\nHere is your new temporary password:"
            + tempPassword
            + "\nUse this to login to your BW-Pathfinding Account, and immediately change your password by viewing your profile, and clicking Reset Password.";
    body.setText(m);

    multipart.addBodyPart(body);
    message.setContent(multipart);

    Transport.send(message);
    System.out.println("Sent message successfully...");
  }
}
