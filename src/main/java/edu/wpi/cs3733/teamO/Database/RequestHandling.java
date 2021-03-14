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
   * Retrieve all service requests from database
   *
   * @return
   */
  public static ObservableList<Request> getRequests() {
    ObservableList<Request> requestList = FXCollections.observableArrayList();
    try {
      String query = "SELECT * FROM Requests";
      // database statement to grab values
      PreparedStatement pstmt = null;
      pstmt = DatabaseConnection.getConnection().prepareStatement(query);
      // returns the results from query
      ResultSet rset = pstmt.executeQuery();

      // temp variables for assignment
      int reqID = 0;
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
        reqID = rset.getInt("requestID");
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

        Request req =
            new Request(
                reqID,
                requestedBy,
                fulfilledBy,
                dateRequested,
                dateNeeded,
                requestType,
                location,
                summary,
                para1,
                para2,
                para3);

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
      r.setPara1(rset.getString("para1"));
      r.setPara2(rset.getString("para2"));
      r.setPara3(rset.getString("para3"));

      rset.close();
      pstmt.close();

    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

    return r;
  }

  /**
   * Add request to DB
   *
   * @param requestedBy
   * @param dateNeeded
   * @param requestType
   * @param locationNodeID
   * @param summary
   * @param customParameter1
   * @param customParameter2
   * @param customParameter3
   */
  public static void addRequest(
      String requestedBy,
      Date dateNeeded,
      String requestType,
      String locationNodeID,
      String summary,
      String customParameter1,
      String customParameter2,
      String customParameter3) {

    // get current date
    long millis = System.currentTimeMillis();
    Date dateRequested = new java.sql.Date(millis);

    String query =
        "INSERT INTO REQUESTS (requestedBy, dateRequested, dateNeeded, REQTYPE, LOCATION, SUMMARY, PARA1, PARA2, PARA3) "
            + "VALUES( '"
            + requestedBy
            + "', '"
            + dateRequested
            + "', '"
            + dateNeeded
            + "', '"
            + requestType
            + "', '"
            + locationNodeID
            + "', '"
            + summary
            + "', '"
            + customParameter1
            + "', '"
            + customParameter2
            + "', '"
            + customParameter3
            + "')";
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
    String query =
        "UPDATE REQUESTS SET fulfilledBy = ?, "
            + "reqType = ?, location = ?, summary = ?, para1 = ?, "
            + "para2 = ?, para3 = ? WHERE requestID = ?";
    try {
      PreparedStatement preparedStmt = null;
      preparedStmt = DatabaseConnection.getConnection().prepareStatement(query);
      preparedStmt.setString(1, fulfilledBy);
      preparedStmt.setString(2, requestType);
      preparedStmt.setString(3, locationNodeID);
      preparedStmt.setString(4, summary);
      preparedStmt.setString(5, customParameter1);
      preparedStmt.setString(6, customParameter2);
      preparedStmt.setString(7, customParameter3);
      preparedStmt.setInt(8, requestID);

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
