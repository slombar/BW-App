package edu.wpi.cs3733.teamO.Database;

import edu.wpi.cs3733.teamO.Controllers.Mobile.WaitingPageController;
import edu.wpi.cs3733.teamO.SRequest.EntryRequest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EntryRequestHandling {
  /**
   * Retrieve all service requests from database
   *
   * @return
   */
  public static ObservableList<EntryRequest> getRequests() {
    ObservableList<EntryRequest> requestList = FXCollections.observableArrayList();
    try {
      String query = "SELECT * FROM Entry_Requests";
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
      String location = "";
      Boolean symptoms = null;
      Boolean check1 = false;
      Boolean check2 = false;

      // grab everything from the result set and add to observable list for processing
      while (rset.next()) {
        reqID = rset.getInt("entryReqID");
        requestedBy = rset.getString("requestedBy");
        fulfilledBy = rset.getString("fulfilledBy");
        dateRequested = rset.getDate("dateRequested");
        location = rset.getString("location");
        symptoms = rset.getBoolean("symptoms");
        check1 = rset.getBoolean("check1");
        check2 = rset.getBoolean("check2");

        EntryRequest req =
            new EntryRequest(
                reqID, requestedBy, fulfilledBy, dateRequested, location, symptoms, check1, check2);

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
                + location
                + ", "
                + symptoms
                + ", "
                + check1
                + ", "
                + check2);

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
            .prepareStatement("SELECT * FROM Entry_Requests WHERE ENTRYREQID = ?");

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
    String query = "UPDATE ENTRY_REQUESTS SET STATUS = '" + status + "' WHERE ENTRYREQID = ?";

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
  public static EntryRequest getRequest(int reqID) {
    EntryRequest r = new EntryRequest();

    try {
      PreparedStatement pstmt =
          DatabaseConnection.getConnection()
              .prepareStatement("SELECT * FROM Entry_Requests WHERE ENTRYREQID = ?");
      pstmt.setInt(1, reqID);

      ResultSet rset = pstmt.executeQuery();
      rset.next();

      // add properties to the node
      r.setRequestID(reqID);
      r.setRequestedBy(rset.getString("requestedBy"));
      r.setFulfilledBy(rset.getString("fulfilledBy"));
      r.setDateRequested(rset.getDate("dateRequested"));
      r.setLocationNodeID(rset.getString("location"));
      r.setIfSymptoms(rset.getBoolean("symptoms"));
      r.setCheck1(rset.getBoolean("check1"));
      r.setCheck2(rset.getBoolean("check2"));

      rset.close();
      pstmt.close();

    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

    return r;
  }

  /**
   * @param requestedBy
   * @param locationNodeID
   * @param symptoms
   * @param check1
   * @param check2
   */
  public static void addEntryRequest(
      String requestedBy, String locationNodeID, Boolean symptoms, Boolean check1, Boolean check2) {

    // get current date
    long millis = System.currentTimeMillis();
    Date dateRequested = new java.sql.Date(millis);

    String query =
        "INSERT INTO ENTRY_REQUESTS (requestedBy, dateRequested, LOCATION, SYMPTOMS, CHECK1, CHECK2) "
            + "VALUES( '"
            + requestedBy
            + "', '"
            + dateRequested
            + "', '"
            + locationNodeID
            + "', '"
            + symptoms
            + "', '"
            + check1
            + "', '"
            + check2
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
  public static void deleteEntryRequest(int requestID) {
    String query = "DELETE FROM ENTRY_REQUESTS WHERE ENTRYREQID = ?";

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

  public static void setEntrance(int reqID, String status) throws SQLException {
    String query = "";

    if (status.equals("Main")) {
      query = "UPDATE ENTRY_REQUESTS SET LOCATION = 'MAIN' WHERE ENTRYREQID = ?";
      WaitingPageController.setEntrance("Main");
    } else {
      query = "UPDATE ENTRY_REQUESTS SET LOCATION = 'EMERGENCY' WHERE ENTRYREQID = ?";
      WaitingPageController.setEntrance("Covid");
    }
    WaitingPageController.setSurveyApproved(true);

    PreparedStatement pstmt = null;
    pstmt = DatabaseConnection.getConnection().prepareStatement(query);

    pstmt.setInt(1, reqID);
    pstmt.executeUpdate();
    pstmt.close();
  }
}
