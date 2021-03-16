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

  // TODO: add function to set employee assigned

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
      query = "SELECT * FROM SRS WHERE REQTYPE = ?";
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
      String requestedBy = "";
      String fulfilledBy = "";
      Date dateRequested = new Date();
      Date dateNeeded = new Date();
      String requestType = "";
      String location = "";
      String summary = "";

      // grab everything from the result set and add to observable list for processing
      while (rset.next()) {
        reqID = rset.getInt("requestID");
        requestedBy = rset.getString("requestedBy");
        fulfilledBy = rset.getString("fulfilledBy");
        dateRequested = rset.getDate("dateRequested");
        dateNeeded = rset.getDate("dateNeeded");
        requestType = rset.getString("reqtype");
        location = rset.getString("location");
        summary = rset.getString("summary");

        Request req =
            new Request(
                reqID,
                requestedBy,
                fulfilledBy,
                dateRequested,
                dateNeeded,
                requestType,
                location,
                summary);

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
                + summary);

        requestList.add(req);
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
            .prepareStatement("SELECT * FROM Requests WHERE REQUESTID = ?");

    pstmt.setInt(1, reqID);

    ResultSet rset = pstmt.executeQuery();

    while (rset.next()) {
      status = rset.getString("STATUS");
    }

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
    String query = "UPDATE REQUESTS SET STATUS = '" + status + "' WHERE REQUESTID = ?";

    PreparedStatement pstmt = null;
    pstmt = DatabaseConnection.getConnection().prepareStatement(query);

    pstmt.setInt(1, reqID);
    pstmt.executeUpdate();
    pstmt.close();
  }

  /**
   * Get the request from the database based off of the ID
   *
   * @param reqID
   * @return
   */
  public static Request getRequest(int reqID) {
    Request r = new Request();

    try {
      PreparedStatement pstmt =
          DatabaseConnection.getConnection()
              .prepareStatement("SELECT * FROM Requests WHERE REQUESTID = ?");
      pstmt.setInt(1, reqID);

      ResultSet rset = pstmt.executeQuery();
      rset.next();

      // add properties to the node
      r.setRequestID(reqID);
      r.setRequestedBy(rset.getString("requestedBy"));
      r.setFulfilledBy(rset.getString("fulfilledBy"));
      r.setDateRequested(rset.getDate("dateRequested"));
      r.setDateNeeded(rset.getDate("dateNeeded"));
      r.setRequestType(rset.getString("reqType"));
      r.setLocationNodeID(rset.getString("location"));
      r.setSummary(rset.getString("summary"));

      rset.close();
      pstmt.close();

    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

    return r;
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
      preparedStmt.setString(6, "Not Assigned");
      preparedStmt.setString(7, r.getLocationNodeID());

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
    String query = "DELETE FROM REQUESTS WHERE REQUESTID = ?";

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
   * TODO Finish this Allows editing of request
   *
   * @param requestID
   * @param fulfilledBy
   * @param requestType
   * @param locationNodeID
   * @param summary
   * @param customParameter1
   * @param customParameter2
   * @param customParameter3
   */
  public static void editRequest(
      int requestID,
      String fulfilledBy,
      String requestType,
      String locationNodeID,
      String summary,
      String customParameter1,
      String customParameter2,
      String customParameter3) {

    summary += customParameter1 + customParameter2 + customParameter3;

    String query =
        "UPDATE REQUESTS SET fulfilledBy = ?, "
            + "reqType = ?, location = ?, summary = ? WHERE requestID = ?";
    try {
      PreparedStatement preparedStmt = null;
      preparedStmt = DatabaseConnection.getConnection().prepareStatement(query);
      preparedStmt.setString(1, fulfilledBy);
      preparedStmt.setString(2, requestType);
      preparedStmt.setString(3, locationNodeID);
      preparedStmt.setString(4, summary);
      preparedStmt.setInt(5, requestID);

      preparedStmt.executeUpdate();
      preparedStmt.close();
      System.out.println("printed " + query);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  public static void updateRequest(int requestID, String locationNode) {
    String query = "UPDATE REQUESTS SET location = ? WHERE requestID = ?";
    try {
      PreparedStatement preparedStmt = null;
      preparedStmt = DatabaseConnection.getConnection().prepareStatement(query);
      preparedStmt.setString(1, locationNode);
      preparedStmt.setInt(2, requestID);
      preparedStmt.executeUpdate();
      preparedStmt.close();
      System.out.println("printed " + query);
      WaitingPageController.setEntrance(locationNode);
      //      WaitingPageController.notifyUser();

    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }
}
