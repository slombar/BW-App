package edu.wpi.cs3733.teamO.Database;

import edu.wpi.cs3733.teamO.SRequest.Request;
import edu.wpi.cs3733.teamO.UserTypes.Staff;
import edu.wpi.cs3733.teamO.model.Edge;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RequestHandling {

  public static ObservableList<Request> getAllRequests() {
    ObservableList<Request> requestList = FXCollections.observableArrayList();
    try {
      // database statement to grab values
      PreparedStatement pstmt = null;
      pstmt = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM Requests");
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
        requestType = rset.getString("requestType");
        location = rset.getString("location");
        summary = rset.getString("summary");
        para1 = rset.getString("para1");
        para2 = rset.getString("para2");
        para3 = rset.getString("para3");

        Request req = new Request(reqID, requestedBy, fulfilledBy, dateRequested, dateNeeded, requestType, location, summary, para1, para2, para3);
        requestList.add(req);
      }

      // must close these for update to occur
      rset.close();
      pstmt.close();

    } catch (SQLException e) {
      System.out.println("Report Edge Information: Failed!");
      e.printStackTrace();
    }
    return requestList;
  }

  public Request getRequest(String reqID){
    Request r = new Request();

    try {
      PreparedStatement pstmt = null;
      pstmt =
              DatabaseConnection.getConnection()
                      .prepareStatement("SELECT * FROM Requests WHERE reqID = '" + reqID + "'");
      ResultSet rset = pstmt.executeQuery();

      // add properties to the node
      r.setRequestID(reqID);
      r.setRequestedBy(rset.getString("requestedBy"));
      r.setFulfilledBy(rset.getString("fulfilledBy"));
      r.setDateRequested(rset.getDate("dateRequested"));
      r.setDateNeeded(rset.getDate("dateNeeded"));
      r.setRequestType(rset.getString("requestType"));
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


  public static void addRequest(
      Staff requestedBy,
      Staff fulfilledBy,
      Date dateRequested,
      Date dateNeeded,
      String requestType,
      String locationNodeID,
      String summary,
      String customParameter1,
      String customParameter2,
      String customParameter3) {
    String query =
        "INSERT INTO REQUESTS VALUES("
            + "'"
            + requestedBy
            + "'"
            + ", '"
            + fulfilledBy
            + "', '"
            + dateRequested
            + "', "
            + "'"
            + dateNeeded
            + "'"
            + ", "
            + "'"
            + requestType
            + "'"
            + ", "
            + "'"
            + locationNodeID
            + "'"
            + ", "
            + "'"
            + summary
            + "'"
            + ", "
            + "'"
            + customParameter1
            + "'"
            + ", "
            + "'"
            + customParameter2
            + "'"
            + ", "
            + "'"
            + customParameter3
            + "'"
            + ")";
    try {
      PreparedStatement preparedStmt = null;
      preparedStmt = DatabaseConnection.getConnection().prepareStatement(query);
      preparedStmt.execute();
      preparedStmt.close();

    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  public static void deleteRequest(int requestID) {
    String query = "DELETE FROM ServiceRequests WHERE requestID =" + requestID;
    try {
      PreparedStatement preparedStmt = null;
      preparedStmt = DatabaseConnection.getConnection().prepareStatement(query);
      preparedStmt.execute();
      preparedStmt.close();

    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  public static void editRequest(
      int requestID,
      Staff requestedBy,
      Staff fulfilledBy,
      Date dateRequested,
      Date dateNeeded,
      String requestType,
      String locationNodeID,
      String summary,
      String customParameter1,
      String customParameter2,
      String customParameter3) {
    String query =
        "UPDATE ServiceRequests SET "
            + "requestedBy = "
            + requestedBy
            + "fulfilledBy = "
            + fulfilledBy
            + ", dateRequested = "
            + dateRequested
            + ", dateNeeded = "
            + dateNeeded
            + ", requestType = "
            + requestType
            + ", locationNodeID = "
            + locationNodeID
            + ", summary = "
            + summary
            + ", customParameter1 = "
            + customParameter1
            + ", customParameter2 = "
            + customParameter2
            + ", customParameter3 = "
            + customParameter3
            + "WHERE requestID ="
            + requestID;
    try {
      PreparedStatement preparedStmt = null;
      preparedStmt = DatabaseConnection.getConnection().prepareStatement(query);
      preparedStmt.execute();
      preparedStmt.close();

    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }
}
