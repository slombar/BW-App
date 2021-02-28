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
      String specialInstructions,
      String washSettings,
      String drySettings) {
    String query =
        "INSERT INTO ServiceRequests VALUES("
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
            + specialInstructions
            + "'"
            + ", "
            + "'"
            + washSettings
            + "'"
            + ", "
            + "'"
            + drySettings
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
}
