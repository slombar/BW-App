package edu.wpi.cs3733.teamO.Database;

import edu.wpi.cs3733.teamO.model.Edge;
import edu.wpi.cs3733.teamO.model.Node;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class NodesAndEdges {
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
      boolean visible) {
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
   * adds a new edge
   *
   * @param startNode
   * @param endNode
   */
  public static void addNewEdge(String startNode, String endNode) {
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
   * Adds a row to the edges table
   *
   * @param startNode
   * @param endNode
   */
  public static void addEdge(String startNode, String endNode, double length) {
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
      String team,
      boolean visible) {
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
      return;
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

      // add properties to the node
      n.setID(rset.getString("nodeID"));
      n.setBuilding(rset.getString("building"));
      n.setFloor(rset.getString("floor"));
      n.setShortName(rset.getString("shortName"));
      n.setLongName(rset.getString("longName"));
      n.setNodeType(rset.getString("nodeType"));
      n.setXCoord(rset.getInt("xcoord"));
      n.setYCoord(rset.getInt("ycoord"));
      n.setTeam(rset.getString("teamAssigned"));
      n.setVisible(rset.getBoolean("visible"));

      rset.close();
      pstmt.close();

    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

    return n;
  }

  /**
   * Retrieve an edge from the DB by sending the ID as a parameter
   *
   * @param id of the node you want to snag from DB
   * @return
   */
  public static Edge getEdge(String id) {
    Edge e = new Edge();

    try {
      PreparedStatement pstmt = null;
      pstmt =
          DatabaseConnection.getConnection()
              .prepareStatement("SELECT * FROM Nodes WHERE nodeID = '" + id + "'");
      ResultSet rset = pstmt.executeQuery();

      // add properties to the node
      e.setID(rset.getString("nodeID"));
      e.setStart(rset.getString("startNode"));
      e.setID(rset.getString("endNode"));
      e.setLength(rset.getDouble("length"));

      rset.close();
      pstmt.close();

    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

    return e;
  }

  /**
   * Displays all the nodes from the database
   *
   * @return ObservableList<Node> type, filled with nodes
   */
  public static ObservableList<Node> getAllNodes() {
    ObservableList<Node> nodeList = FXCollections.observableArrayList();

    try {
      // statments to grab all node values from db
      PreparedStatement pstmt = null;
      pstmt = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM Nodes");
      // returns results from DB
      ResultSet rset = pstmt.executeQuery();

      // temp variables
      String ID = "";
      int xcoord = 0;
      int ycoord = 0;
      String floor = "";
      String building = "";
      String nodeType = "";
      String longName = "";
      String shortName = "";
      String team = "";
      boolean visible = false;

      while (rset.next()) {
        // add data from result set of query to observable list for processing
        ID = rset.getString("nodeID");
        xcoord = rset.getInt("xcoord");
        ycoord = rset.getInt("ycoord");
        floor = rset.getString("floor");
        building = rset.getString("building");
        nodeType = rset.getString("nodeType");
        longName = rset.getString("longName");
        shortName = rset.getString("shortName");
        team = rset.getString("teamAssigned");
        visible = rset.getBoolean("visible");
        // add to observable list
        nodeList.add(
            new Node(
                ID, xcoord, ycoord, floor, building, nodeType, longName, shortName, team, visible));
      }
      // must close to get proper info from db
      rset.close();
      pstmt.close();

    } catch (SQLException e) {
      System.out.println("Report Node Information: Failed!");
      e.printStackTrace();
    }
    return nodeList;
  }

  /**
   * Returns all the edges from the database
   *
   * @return ObservableList<Edge> which contains all edges from DB
   */
  public static ObservableList<Edge> getAllEdges() {
    ObservableList<Edge> edgeList = FXCollections.observableArrayList();
    try {
      // database statement to grab values
      PreparedStatement pstmt = null;
      pstmt = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM Edges");
      // returns the results from query
      ResultSet rset = pstmt.executeQuery();

      // temp variables for assignment
      String ID = "";
      String startNode = "";
      String endNode = "";
      double length = 0;

      // grab everything from the result set and add to observable list for processing
      while (rset.next()) {
        ID = rset.getString("nodeID");
        startNode = rset.getString("startNode");
        endNode = rset.getString("endNode");
        length = rset.getDouble("length");

        edgeList.add(new Edge(ID, startNode, endNode, length));
      }

      // must close these for update to occur
      rset.close();
      pstmt.close();

    } catch (SQLException e) {
      System.out.println("Report Edge Information: Failed!");
      e.printStackTrace();
    }
    return edgeList;
  }
}
