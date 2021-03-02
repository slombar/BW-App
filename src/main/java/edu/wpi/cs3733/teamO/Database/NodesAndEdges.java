package edu.wpi.cs3733.teamO.Database;

import edu.wpi.cs3733.teamO.model.Edge;
import edu.wpi.cs3733.teamO.model.Node;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    String nodeID2 = endNode + "_" + startNode;

    try {
      getEdge(nodeID);
      getEdge(nodeID2);
      System.out.println("Edge already exists in database. Try again stinky.");
      throw new SQLException();

    } catch (SQLException throwables) {
      System.out.println("Adding edge because there are no current edges with this ID");
    }

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
    String query = "DELETE FROM Edges WHERE nodeID = '" + nodeID + "'";
    // get the edge to throw error if it doesn't exist.
    getEdge(nodeID);
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

  /**
   * Retrieve an edge from the DB by sending the ID as a parameter
   *
   * @param id of the node you want to snag from DB
   * @return
   */
  public static Edge getEdge(String id) throws SQLException {
    Edge e = new Edge();

    PreparedStatement pstmt = null;
    pstmt =
        DatabaseConnection.getConnection()
            .prepareStatement("SELECT * FROM Edges WHERE nodeID = '" + id + "'");
    ResultSet rset = pstmt.executeQuery();

    // add properties to the node
    e.setID(rset.getString("NODEID"));
    e.setStart(rset.getString("STARTNODE"));
    e.setID(rset.getString("ENDNODE"));
    e.setLength(rset.getDouble("LENGTH"));

    rset.close();
    pstmt.close();

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

      Node n = new Node();

      while (rset.next()) {
        // add data from result set of query to observable list for processing
        ID = rset.getString("NODEID");
        xcoord = rset.getInt("XCOORD");
        ycoord = rset.getInt("YCOORD");
        floor = rset.getString("FLOOR");
        building = rset.getString("BUILDING");
        nodeType = rset.getString("NODETYPE");
        longName = rset.getString("LONGNAME");
        shortName = rset.getString("SHORTNAME");
        team = rset.getString("TEAMASSIGNED");
        visible = rset.getBoolean("VISIBLE");
        // add to observable list
        n =
            new Node(
                ID, xcoord, ycoord, floor, building, nodeType, longName, shortName, team, visible);
        nodeList.add(n);
      }
      // must close to get proper info from db
      rset.close();
      pstmt.close();

    } catch (SQLException e) {
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

      Edge e = new Edge();
      // grab everything from the result set and add to observable list for processing
      while (rset.next()) {
        ID = rset.getString("NODEID");
        startNode = rset.getString("STARTNODE");
        endNode = rset.getString("ENDNODE");
        length = rset.getDouble("LENGTH");

        e = new Edge(ID, startNode, endNode, length);

        e.setID(ID);
        e.setStart(startNode);

        edgeList.add(e);
      }

      // must close these for update to occur
      rset.close();
      pstmt.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return edgeList;
  }
}
