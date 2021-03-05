package edu.wpi.cs3733.teamO.Database;

import edu.wpi.cs3733.teamO.model.Node;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ArchiveNE {
  /**
   * Adds the given data into a table row in the database
   *
   * @param nodeID
   * @param xcoord
   * @param ycoord
   * @param floor
   * @param building
   * @param longName
   * @param shortName
   * @param teamAssigned
   */
  public static void addNode(
      String nodeID,
      String xcoord,
      String ycoord,
      String floor,
      String building,
      String nodeType,
      String longName,
      String shortName,
      String teamAssigned,
      boolean visible)
      throws SQLException {
    String query =
        "INSERT INTO Nodes VALUES("
            + "'"
            + nodeID
            + "'"
            + ", "
            + xcoord
            + ", "
            + ycoord
            + ", "
            + "'"
            + floor
            + "'"
            + ", "
            + "'"
            + building
            + "'"
            + ", "
            + "'"
            + nodeType
            + "'"
            + ", "
            + "'"
            + longName
            + "'"
            + ", "
            + "'"
            + shortName
            + "'"
            + ", "
            + "'"
            + teamAssigned
            + "'"
            + ", "
            + visible
            + ")";

    try {
      PreparedStatement preparedStmt = null;
      preparedStmt = DatabaseConnection.getConnection().prepareStatement(query);
      preparedStmt.execute();
      preparedStmt.close();

    } catch (SQLException throwables) {
      throwables.printStackTrace();
      throw throwables;
    }
  }

  /**
   * adds a new edge
   *
   * @param startNode
   * @param endNode
   */
  public static void addNewEdge(String startNode, String endNode) throws SQLException {
    String nodeID = startNode + "_" + endNode;
    String query =
        "INSERT INTO Edges VALUES("
            + "'"
            + nodeID
            + "'"
            + ", "
            + "'"
            + startNode
            + "'"
            + ", "
            + "'"
            + endNode
            + "', "
            + 0
            + ")";
    try {
      PreparedStatement preparedStmt = null;
      preparedStmt = DatabaseConnection.getConnection().prepareStatement(query);

      preparedStmt.execute();
      preparedStmt.close();

    } catch (SQLException throwables) {
      throwables.printStackTrace();
      throw throwables;
    }
  }

  /**
   * Adds a row to the edges table
   *
   * @param startNode
   * @param endNode
   */
  public static void addEdge(String startNode, String endNode, double length) throws SQLException {
    String nodeID = startNode + "_" + endNode;

    String query =
        "INSERT INTO Edges VALUES("
            + "'"
            + nodeID
            + "'"
            + ", "
            + "'"
            + startNode
            + "'"
            + ", "
            + "'"
            + endNode
            + "', "
            + length
            + ")";
    try {
      PreparedStatement preparedStmt = null;
      preparedStmt = DatabaseConnection.getConnection().prepareStatement(query);

      preparedStmt.execute();
      preparedStmt.close();

    } catch (SQLException throwables) {
      throwables.printStackTrace();
      throw throwables;
    }
  }

  /**
   * Deletes all corresponding edges from the given node
   *
   * @param nodeID, the node that you want to delete all edges from
   * @return
   */
  public static void deleteAllEdges(String nodeID) {

    ArrayList<String> edgesList = new ArrayList<>();
    String query =
        "SELECT * FROM Edges WHERE startNode = '" + nodeID + "' OR endNode ='" + nodeID + "'";

    try {
      PreparedStatement preparedStmt = null;
      preparedStmt = DatabaseConnection.getConnection().prepareStatement(query);
      ResultSet rset = preparedStmt.executeQuery();

      while (rset.next()) {
        edgesList.add(rset.getString("nodeID"));
      }

      preparedStmt.close();

    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

    for (String edgeID : edgesList) {
      try {
        deleteEdge(edgeID);
      } catch (SQLException throwables) {
        throwables.printStackTrace();
      }
    }
  }

  public static void deleteNode(String nodeID) throws SQLException {
    String query = "DELETE FROM Nodes WHERE nodeID = '" + nodeID + "'";
    try {
      PreparedStatement preparedStmt = null;
      preparedStmt = DatabaseConnection.getConnection().prepareStatement(query);

      preparedStmt.execute();
      preparedStmt.close();

    } catch (SQLException throwables) {
      throwables.printStackTrace();
      throw throwables;
    }

    // delete all corresponding edges
    deleteAllEdges(nodeID);
  }

  /** @param nodeID */
  public static void deleteEdge(String nodeID) throws SQLException {
    // get the edge to throw error if it doesn't exist.
    String query = "DELETE FROM Edges WHERE nodeID = '" + nodeID + "'";

    try {
      PreparedStatement preparedStmt = null;
      preparedStmt = DatabaseConnection.getConnection().prepareStatement(query);

      preparedStmt.execute();
      preparedStmt.close();

    } catch (SQLException throwables) {
      throwables.printStackTrace();
      throw throwables;
    }
  }

  /**
   * Use the given id to edit that entry in the database (nodes)
   *
   * @param nodeID
   */
  public static void editNode(
      String nodeID,
      int x,
      int y,
      String floor,
      String building,
      String nodeType,
      String longName,
      String shortName,
      String team,
      boolean visible)
      throws SQLException {
    String query =
        "UPDATE Nodes SET xcoord = "
            + x
            + ", ycoord = "
            + y
            + ", floor = '"
            + floor
            + "', building = '"
            + building
            + "', nodeType = '"
            + nodeType
            + "', longName = '"
            + longName
            + "', shortName = '"
            + shortName
            + "', teamAssigned = '"
            + team
            + "', visible = "
            + visible
            + " WHERE nodeID = '"
            + nodeID
            + "'";
    PreparedStatement preparedStmt = null;

    try {
      preparedStmt = DatabaseConnection.getConnection().prepareStatement(query);
      preparedStmt.executeUpdate();
      preparedStmt.close();

    } catch (SQLException throwables) {
      throwables.printStackTrace();
      throw throwables;
    }
    System.out.println("Node with ID: " + nodeID + "has been changed.");
  }

  /**
   * Use the given id to edit that entry in the database (edges)
   *
   * @param nodeID
   */
  public static void editEdge(String nodeID, String startNode, String endNode, double length) {

    String query =
        "UPDATE Edges SET startNode = '"
            + startNode
            + "', endNode = '"
            + endNode
            + "', length = "
            + length
            + " WHERE nodeID = '"
            + nodeID
            + "'";
    PreparedStatement preparedStmt = null;

    try {
      preparedStmt = DatabaseConnection.getConnection().prepareStatement(query);
      preparedStmt.executeUpdate();
      preparedStmt.close();

    } catch (SQLException throwables) {
      throwables.printStackTrace();
      return;
    }
    System.out.println("Edge with ID: " + nodeID + "has been changed.");
  }

  /**
   * Gets a specific node from the DB based on the given ID
   *
   * @param id of the Node you want
   * @return
   */
  public static Node getNode(String id) {
    Node n = new Node();

    try {
      PreparedStatement pstmt = null;
      pstmt =
          DatabaseConnection.getConnection()
              .prepareStatement("SELECT * FROM Nodes WHERE nodeID = '" + id + "'");
      ResultSet rset = pstmt.executeQuery();

      while (rset.next()) {
        // add properties to the node
        n.setID(rset.getString("NODEID"));
        n.setBuilding(rset.getString("BUILDING"));
        n.setFloor(rset.getString("FLOOR"));
        n.setShortName(rset.getString("SHORTNAME"));
        n.setLongName(rset.getString("LONGNAME"));
        n.setNodeType(rset.getString("NODETYPE"));
        n.setXCoord(rset.getInt("XCOORD"));
        n.setYCoord(rset.getInt("YCOORD"));
        n.setTeam(rset.getString("TEAMASSIGNED"));
        n.setVisible(rset.getBoolean("VISIBLE"));
      }

      rset.close();
      pstmt.close();

    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

    return n;
  }
}
