package edu.wpi.cs3733.teamO.Database;

import edu.wpi.cs3733.teamO.UserTypes.Staff;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class RequestHandling {

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
