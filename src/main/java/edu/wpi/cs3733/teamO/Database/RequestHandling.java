package edu.wpi.cs3733.teamO.Database;

import edu.wpi.cs3733.teamO.Controllers.Mobile.WaitingPageController;
import edu.wpi.cs3733.teamO.SRequest.Request;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RequestHandling {
  /**
   * Get the request from the database based off of the ID
   *
   * @param reqID
   * @return
   */
  public static Request getRequest(int reqID) {
    Request r = new Request();

    try {
      String query = "SELECT * FROM SRS WHERE REQUESTID = ?";
      PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
      ps.setInt(1, reqID);

      ResultSet rset = ps.executeQuery();
      rset.next();

      // add properties to the node
      r.setRequestID(reqID);
      r.setRequestedBy(rset.getString("PERSON"));
      r.setAssignedTo(rset.getString("ASSIGNED"));
      r.setDateRequested(rset.getDate("DATECREATED"));
      r.setDateNeeded(rset.getDate("DATENEEDED"));
      r.setRequestType(rset.getString("REQUESTTYPE"));
      r.setRequestLocation(rset.getString("LOCATION"));
      r.setSummary(rset.getString("SUMMARY"));
      r.setStatus(rset.getString("STATUS"));

      rset.close();
      ps.close();

    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

    return r;
  }

  /**
   * assign an employee to a service request
   *
   * @param reqID
   * @param employee, either takes in a name like Sadie Lombardi or a username like slombardi
   */
  public static void assignEmployee(int reqID, String employee) {
    String query = "UPDATE SRS SET ASSIGNED = ? WHERE ID = ?";

    // if this is a name like "Sadie Lombardi" we will parse it and grab employee username
    if (employee.contains(" ")) {
      employee = UserHandling.getUserByName(employee);
    }

    try {
      PreparedStatement preparedStmt = DatabaseConnection.getConnection().prepareStatement(query);
      preparedStmt.setString(1, employee);
      preparedStmt.setInt(2, reqID);
      preparedStmt.executeUpdate();
      preparedStmt.close();

      RequestHandling.setStatus(reqID, "Assigned");

    } catch (SQLException throwables) {
      throwables.printStackTrace();
      return;
    }
  }

  /**
   * Retrieve all service requests from database, depending on their type
   *
   * @param type the type of request you want return. For all use "ALL"
   * @return
   */
  public static ObservableList<Request> getRequests(String type) {
    ObservableList<Request> requestList = FXCollections.observableArrayList();
    PreparedStatement pstmt = null;
    ResultSet rset = null;
    String query = "";

    if (type.equals("ALL")) {
      query = "SELECT * FROM SRS";

      try {
        // database statement to grab values
        pstmt = DatabaseConnection.getConnection().prepareStatement(query);
        // returns the results from query
        rset = pstmt.executeQuery();
      } catch (SQLException throwables) {
        throwables.printStackTrace();
      }

    } else {
      query = "SELECT * FROM SRS WHERE REQUESTTYPE = ?";
      try {
        // database statement to grab values
        pstmt = DatabaseConnection.getConnection().prepareStatement(query);
        pstmt.setString(1, type);
        // returns the results from query
        rset = pstmt.executeQuery();
      } catch (SQLException throwables) {
        throwables.printStackTrace();
      }
    }

    try {
      // temp variables for assignment
      int reqID = 0;
      String assignedto = "";
      String requestedBy = "";
      Date dateRequested = new Date();
      Date dateNeeded = new Date();
      String requestType = "";
      String location = "";
      String summary = "";
      String status = "";

      // grab everything from the result set and add to observable list for processing
      while (rset.next()) {
        reqID = rset.getInt("ID");
        requestedBy = rset.getString("PERSON");
        assignedto = rset.getString("ASSIGNED");
        dateRequested = rset.getDate("DATECREATED");
        dateNeeded = rset.getDate("DATENEEDED");
        requestType = rset.getString("REQUESTTYPE");
        location = rset.getString("LOCATION");
        summary = rset.getString("SUMMARY");
        status = (rset.getString("STATUS"));

        System.out.println("Assigned to:" + assignedto);
        // requests generate here
        Request r = new Request();
        // add properties to the node
        r.setRequestID(reqID);
        r.setRequestedBy(rset.getString("PERSON"));
        r.setAssignedTo(rset.getString("ASSIGNED"));
        r.setDateRequested(rset.getDate("DATECREATED"));
        r.setDateNeeded(rset.getDate("DATENEEDED"));
        r.setRequestType(rset.getString("REQUESTTYPE"));
        r.setRequestLocation(rset.getString("LOCATION"));
        r.setSummary(rset.getString("SUMMARY"));
        r.setStatus(rset.getString("STATUS"));

        requestList.add(r);
      }

      // must close these for update to occur
      rset.close();
      pstmt.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return requestList;
  }

  /**
   * get the status of the current request
   *
   * @param reqID the id of the request you need the status of
   * @return
   */
  public static String getStatus(int reqID) throws SQLException {

    String status = "";

    PreparedStatement pstmt = null;

    pstmt =
        DatabaseConnection.getConnection()
            .prepareStatement("SELECT STATUS FROM Requests WHERE ID = ?");

    pstmt.setInt(1, reqID);

    ResultSet rset = pstmt.executeQuery();
    rset.next();
    status = rset.getString("STATUS");

    rset.close();
    pstmt.close();

    return status;
  }

  /**
   * set the status of the current request
   *
   * @param reqID
   */
  public static void setStatus(int reqID, String status) throws SQLException {
    String query = "UPDATE SRS SET STATUS = ? WHERE ID = ?";

    PreparedStatement pstmt = null;
    pstmt = DatabaseConnection.getConnection().prepareStatement(query);

    pstmt.setString(1, status);
    pstmt.setInt(2, reqID);
    pstmt.executeUpdate();
    pstmt.close();
  }

  public static void addRequest(Request r) {
    // get current date
    long millis = System.currentTimeMillis();
    java.sql.Date dateRequested = new java.sql.Date(millis);

    String query =
        "INSERT INTO SRS (PERSON, DATECREATED, DATENEEDED, REQUESTTYPE, SUMMARY, STATUS, LOCATION) VALUES(?, ?, ?, ?, ?, ?, ?)";
    try {
      PreparedStatement preparedStmt = DatabaseConnection.getConnection().prepareStatement(query);
      preparedStmt.setString(1, r.getRequestedBy());
      preparedStmt.setDate(2, dateRequested);
      preparedStmt.setDate(3, (java.sql.Date) r.getDateNeeded());
      preparedStmt.setString(4, r.getRequestType());
      preparedStmt.setString(5, r.getSummary());
      preparedStmt.setString(6, r.getStatus());
      preparedStmt.setString(7, r.getRequestLocation());

      preparedStmt.execute();
      preparedStmt.close();

    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  /**
   * Remove request from database, provide w/ ID of desired deleted request
   *
   * @param requestID
   */
  public static void deleteRequest(int requestID) {
    String query = "DELETE FROM SRS WHERE ID = ?";

    try {
      PreparedStatement preparedStmt = null;
      preparedStmt = DatabaseConnection.getConnection().prepareStatement(query);
      preparedStmt.setInt(1, requestID);

      preparedStmt.execute();
      preparedStmt.close();

    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  /**
   * Edit the request in the database Updates the request type, summary, location for this specific
   * request
   *
   * @param r
   */
  public static void editRequest(Request r) {

    String query = "UPDATE SRS SET REQUESTTYPE = ?, SUMMARY = ?, LOCATION = ? WHERE requestID = ?";
    try {
      PreparedStatement preparedStmt = null;
      preparedStmt = DatabaseConnection.getConnection().prepareStatement(query);
      preparedStmt.setString(1, r.getRequestType());
      preparedStmt.setString(2, r.getSummary());
      preparedStmt.setString(3, r.getRequestLocation());
      preparedStmt.setInt(4, r.getRequestID());

      preparedStmt.executeUpdate();
      preparedStmt.close();

    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  /**
   * Get every request for the given employee
   *
   * @param employee the first and last name of an employee, in this format ('Sadie Lombardi')
   * @return
   */
  public static ObservableList<Request> getEmployeeRequests(String employee) {
    String employeeUsername = UserHandling.getUserByName(employee);
    ObservableList<Request> requests = FXCollections.observableArrayList();
    try {
      // get the requests
      PreparedStatement requestPS =
          DatabaseConnection.getConnection()
              .prepareStatement("SELECT * FROM SRS WHERE ASSIGNED = ?");
      requestPS.setString(1, employeeUsername);

      ResultSet requestSet = requestPS.executeQuery();

      while (requestSet.next()) {
        Request r = new Request();
        r.setRequestID(requestSet.getInt("ID"));
        r.setAssignedTo(requestSet.getString("ASSIGNED"));
        r.setDateRequested(requestSet.getDate("DATECREATED"));
        r.setDateNeeded(requestSet.getDate("DATENEEDED"));
        r.setSummary(requestSet.getString("SUMMARY"));
        r.setRequestType(requestSet.getString("REQUESTTYPE"));
        r.setStatus(requestSet.getString("STATUS"));
        r.setRequestLocation(requestSet.getString("LOCATION"));

        requests.add(r);
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return requests;
  }

  public static void updateRequest(int requestID, String locationNode) {
    String query = "UPDATE SRS SET LOCATION = ? WHERE ID = ?";
    try {
      PreparedStatement preparedStmt = null;
      preparedStmt = DatabaseConnection.getConnection().prepareStatement(query);
      preparedStmt.setString(1, locationNode);
      preparedStmt.setInt(2, requestID);
      preparedStmt.executeUpdate();
      preparedStmt.close();
      WaitingPageController.setEntrance(locationNode);
      //      WaitingPageController.notifyUser();

    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  /**
   * returns a sorted list based on the users choice of filter. (ascending/descending)
   *
   * @param dateAction
   * @return
   */
  public static ObservableList<Request> getDateSortedRequests(String dateAction) {
    ObservableList<Request> requests = FXCollections.observableArrayList();
    String query = "";

    if (dateAction.contains("Old")) {
      query = "SELECT * FROM SRS ORDER BY DATENEEDED DESC";
    } else {
      query = "SELECT * FROM SRS ORDER BY DATENEEDED ASC";
    }

    try {
      PreparedStatement preparedStatement =
          DatabaseConnection.getConnection().prepareStatement(query);

      ResultSet rset = preparedStatement.executeQuery();
      while (rset.next()) {
        Request r = new Request();
        r.setRequestID(rset.getInt("ID"));
        r.setAssignedTo(rset.getString("ASSIGNED"));
        r.setDateRequested(rset.getDate("DATECREATED"));
        r.setDateNeeded(rset.getDate("DATENEEDED"));
        r.setSummary(rset.getString("SUMMARY"));
        r.setRequestType(rset.getString("REQUESTTYPE"));
        r.setStatus(rset.getString("STATUS"));
        r.setRequestLocation(rset.getString("LOCATION"));

        requests.add(r);
      }
      preparedStatement.close();

      //      WaitingPageController.notifyUser();

    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

    return requests;
  }
}
