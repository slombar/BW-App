package edu.wpi.cs3733.teamO.Database;

import edu.wpi.cs3733.teamO.Controllers.model.Edge;
import edu.wpi.cs3733.teamO.Controllers.model.Node;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class NodesandEdges {
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
      String teamAssigned) {
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
            + ")";
    System.out.println(query);

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
   * Adds a row to the edges table
   *
   * @param nodeID
   * @param startNode
   * @param endNode
   */
  public static void addEdge(String nodeID, String startNode, String endNode) {
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
            + "'"
            + ")";
    System.out.println("QUERY: " + query);
    try {
      PreparedStatement preparedStmt = null;
      preparedStmt = DatabaseConnection.getConnection().prepareStatement(query);
      preparedStmt.execute();
      preparedStmt.close();

    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  public static void deleteNode(String nodeID) {

    String query = "DELETE FROM Nodes WHERE nodeID = '" + nodeID + "'";
    System.out.println("QUERY: " + query);
    try {
      PreparedStatement preparedStmt = null;
      preparedStmt = DatabaseConnection.getConnection().prepareStatement(query);

      preparedStmt.execute();
      preparedStmt.close();

    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  /** @param nodeID */
  public static void deleteEdge(String nodeID) {
    String query = "DELETE FROM Edges WHERE nodeID = '" + nodeID + "'";
    System.out.println("QUERY: " + query);
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
      String team) {
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
            + "' WHERE nodeID = '"
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
    System.out.println("Node with ID: " + nodeID + "has been changed.");
  }

  /**
   * Use the given id to edit that entry in the database (edges)
   *
   * @param nodeID
   */
  public static void editEdge(String nodeID, String startNode, String endNode) {

    String query =
        "UPDATE Edges SET startNode = '"
            + startNode
            + "', endNode = '"
            + endNode
            + "' WHERE nodeID = '"
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
   * Previously showEdges()
   *
   * @return
   */
  public static ObservableList<Edge> getAllEdges() {
    ObservableList<Edge> edgeList = FXCollections.observableArrayList();
    try {
      PreparedStatement pstmt = null;
      pstmt = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM Edges");
      ResultSet rset = pstmt.executeQuery();

      String ID = "";
      String startNode = "";
      String endNode = "";

      // Process the results
      while (rset.next()) {
        ID = rset.getString("nodeID");
        startNode = rset.getString("startNode");
        endNode = rset.getString("endNode");

        // update to add edge length
        edgeList.add(new Edge(ID, startNode, endNode, 0));
      }

      rset.close();
      pstmt.close();

    } catch (SQLException e) {
      System.out.println("Report Edge Information: Failed!");
      e.printStackTrace();
    }
    return edgeList;
  }

  /**
   * Previously showNodes()
   *
   * @return
   */
  public static ObservableList<Node> getAllNodes() {
    ObservableList<Node> nodeList = FXCollections.observableArrayList();

    try {
      PreparedStatement pstmt = null;
      pstmt = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM Nodes");
      ResultSet rset = pstmt.executeQuery();

      String ID = "";
      int xcoord = 0;
      int ycoord = 0;
      String floor = "";
      String building = "";
      String nodeType = "";
      String longName = "";
      String shortName = "";
      String team = "";

      // Process the results
      while (rset.next()) {
        ID = rset.getString("nodeID");
        xcoord = rset.getInt("xcoord");
        ycoord = rset.getInt("ycoord");
        floor = rset.getString("floor");
        building = rset.getString("building");
        nodeType = rset.getString("nodeType");
        longName = rset.getString("longName");
        shortName = rset.getString("shortName");
        team = rset.getString("teamAssigned");
        nodeList.add(
            new Node(ID, xcoord, ycoord, floor, building, nodeType, longName, shortName, team));
      }
      rset.close();
      pstmt.close();

    } catch (SQLException e) {
      System.out.println("Report Node Information: Failed!");
      e.printStackTrace();
    }
    return nodeList;
  }
}
