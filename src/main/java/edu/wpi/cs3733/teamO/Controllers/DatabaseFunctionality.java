package edu.wpi.cs3733.teamO.Controllers;

import edu.wpi.cs3733.teamO.Controllers.model.Edge;
import edu.wpi.cs3733.teamO.Controllers.model.Node;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;
import javafx.collections.ObservableList;

public class DatabaseFunctionality {

  // variables for db connection
  public static Connection connection;
  public static String dbUrl = "jdbc:derby:Odb";
  public static String driver = "org.apache.derby.jdbc.EmbeddedDriver";

  /**
   * connect to the database that is: if the embedded driver wasn't fucked trash shit!! (:
   *
   * @return
   */
  public static boolean establishConnection() {

    System.out.println("Connecting to DB...");
    try {
      Class.forName(driver);
      connection = DriverManager.getConnection(dbUrl);

    } catch (ClassNotFoundException e) {
      System.out.println("Error: Embedded driver not found");
      e.printStackTrace();
      return false;
    } catch (SQLException t) {
      System.out.println("Error: Connection Failed.");
      t.printStackTrace();
      return false;
    }

    System.out.println("...Connected!");
    return true;
  }

  /** shuts down connection to db */
  public static void shutDownDB() {
    try {
      DriverManager.getConnection(dbUrl + ";shutdown=true");
      System.out.println("Database Shutting down...");
    } catch (SQLException throwables) {
      System.out.println("Database Disconnected");
    }
  }

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
      preparedStmt = connection.prepareStatement(query);
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
      preparedStmt = connection.prepareStatement(query);
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
      preparedStmt = connection.prepareStatement(query);

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
      preparedStmt = connection.prepareStatement(query);
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
      preparedStmt = connection.prepareStatement(query);
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
      preparedStmt = connection.prepareStatement(query);
      preparedStmt.executeUpdate();
      preparedStmt.close();

    } catch (SQLException throwables) {
      throwables.printStackTrace();
      return;
    }
    System.out.println("Edge with ID: " + nodeID + "has been changed.");
  }

  public static void displayEdges() {
    try {
      PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM Edges");
      ResultSet rset = pstmt.executeQuery();

      String ID = "";
      String startNode = "";
      String endNode = "";

      System.out.println("Information on Edges\n");

      // Process the results
      while (rset.next()) {
        ID = rset.getString("nodeid");
        startNode = rset.getString("startnode");
        endNode = rset.getString("endnode");

        System.out.println(
            "Edge ID: "
                + ID
                + "\n"
                + "Start Node: "
                + startNode
                + "\n"
                + "End Node: "
                + endNode
                + "\n");
      }

      rset.close();
      pstmt.close();

    } catch (SQLException e) {
      System.out.println("Report Edge Information: Failed!");
      e.printStackTrace();
      return;
    }
  }

  /**
   * returns the given node corresponding to the String nodeID in order to create test graph
   *
   * @param nodeID
   * @return
   */
  public static ArrayList<String> getNode(String nodeID) {

    Node node = new Node();

    // store nodeid[0], x[1] y[2]
    ArrayList<String> list = new ArrayList<>();

    String ID = "";
    int x = 0;
    int y = 0;

    try {
      PreparedStatement pstmt = null;
      pstmt = connection.prepareStatement("SELECT * FROM Nodes WHERE nodeID = '" + nodeID + "'");
      ResultSet rset = pstmt.executeQuery();

      while (rset.next()) {
        ID = rset.getString("nodeID");
        x = rset.getInt("xcoord");
        y = rset.getInt("ycoord");
      }
      rset.close();
      pstmt.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }

    list.set(0, ID);
    list.set(1, String.valueOf(x));
    list.set(2, String.valueOf(y));

    System.out.println("VARIABLES: id,x,y" + list.get(0) + list.get(1) + list.get(2));

    return list;
  }

  public static ObservableList<Edge> showEdges(ObservableList<Edge> edgeList) {
    try {
      PreparedStatement pstmt = null;
      pstmt = connection.prepareStatement("SELECT * FROM Edges");
      ResultSet rset = pstmt.executeQuery();

      String ID = "";
      String startNode = "";
      String endNode = "";

      // Process the results
      while (rset.next()) {
        ID = rset.getString("nodeID");
        startNode = rset.getString("startNode");
        endNode = rset.getString("endNode");

        //get length of edge and then change 0 to that
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

  public static ObservableList<Node> showNodes(ObservableList<Node> nodeList) {
    try {
      PreparedStatement pstmt = null;
      pstmt = connection.prepareStatement("SELECT * FROM Nodes");
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
        nodeList.add(new Node(ID, xcoord, ycoord, floor, building, nodeType, longName, shortName, team));
      }
      rset.close();
      pstmt.close();

    } catch (SQLException e) {
      System.out.println("Report Node Information: Failed!");
      e.printStackTrace();
    }
    return nodeList;
  }

  public static void saveEdges(String url) {

    String ID = "";
    String startNode = "";
    String endNode = "";

    // Process the results

    try {
      PreparedStatement pstmt = null;
      pstmt = connection.prepareStatement("SELECT * FROM Edges");
      ResultSet rset = pstmt.executeQuery();

      // String url = "C:\\Users\\Kyle Lopez\\Project-BWApp\\MapOEdges.csv";
      BufferedWriter bw;
      bw = new BufferedWriter(new FileWriter(url));
      while (rset.next()) {
        ID = rset.getString("nodeid");
        startNode = rset.getString("startNode");
        endNode = rset.getString("endNode");
        String line = String.format("%s,%s,%s ", ID, startNode, endNode);
        bw.newLine();
        bw.write(line);
      }

      rset.close();
      pstmt.close();

      bw.close();
    } catch (IOException | SQLException e) {
      System.out.println("Save Edge CSV Information: Failed!");
      e.printStackTrace();
      return;
    }
  }

  public static void save(String url, boolean node) {
    if (node) {
      saveNodes(url);
    } else {
      saveEdges(url);
    }
  }

  public static void saveNodes(String url) {

    String nodeID = "";
    int xcoord = 0;
    int ycoord = 0;
    String floor = "";
    String building = "";
    String nodeType = "";
    String longName = "";
    String shortName = "";
    String teamAssigned = "";

    // Process the results

    try {
      PreparedStatement pstmt = null;
      // Get the Node database
      pstmt = connection.prepareStatement("SELECT * FROM Nodes");
      ResultSet rset = pstmt.executeQuery();

      // Obtain csv file for nodes from user

      // String url = "C:\\Users\\Kyle Lopez\\Desktop\\SoftEng -
      // Code\\Project-BWApp\\MapONodes.csv";

      BufferedWriter bw;
      bw = new BufferedWriter(new FileWriter(url));
      while (rset.next()) {
        nodeID = rset.getString("nodeid");
        xcoord = rset.getInt("xcoord");
        ycoord = rset.getInt("ycoord");
        floor = rset.getString("floor");
        building = rset.getString("building");
        nodeType = rset.getString("nodeType");
        longName = rset.getString("longName");
        shortName = rset.getString("shortName");
        teamAssigned = rset.getString("teamAssigned");

        String line =
            String.format(
                "%s,%d,%d,%s,%s,%s,%s,%s,%s ",
                nodeID,
                xcoord,
                ycoord,
                floor,
                building,
                nodeType,
                longName,
                shortName,
                teamAssigned);

        // writes "enter", so we more to the next line
        bw.newLine();
        bw.write(line);
      }

      rset.close();
      pstmt.close();

      bw.close();
    } catch (IOException | SQLException e) {
      System.out.println("Save Node CSV Information: Failed!");
      e.printStackTrace();
      return;
    }
  }

  /**
   * imports data from csv (delimiter = ,|\n) and determines which database to add it to
   *
   * @param url, the url of the file on the computer
   * @param node, whether or not this file is a node file or an edge file
   */
  public static void importExcelData(String url, boolean node) {
    Scanner scan = null;
    Pattern d = Pattern.compile(",|\r\n");

    // try to open file
    try {
      scan = new Scanner(new File(url)).useDelimiter(d);
      System.out.println("File read! Importing data...");

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    // if the file is not empty
    if (scan.hasNext()) {
      // remove header line in beginning of file
      System.out.println("Removing header line: " + scan.nextLine());

      if (node) {
        // function variables for simplicity
        String nodeID = "";
        String xcoord = "";
        String ycoord = "";
        String floor = "";
        String building = "";
        String nodeType = "";
        String longName = "";
        String shortName = "";
        String teamAssigned = "";

        // delete current nodes
        PreparedStatement pstmt = null;

        try {
          pstmt = connection.prepareStatement("DELETE FROM Nodes");

          pstmt.execute();
          pstmt.close();
        } catch (SQLException throwables) {
          throwables.printStackTrace();
        }

        // while the file is not at its end
        while (scan.hasNext()) {
          nodeID = scan.next();
          xcoord = scan.next();
          ycoord = scan.next();
          floor = scan.next();
          building = scan.next();
          nodeType = scan.next();
          longName = scan.next();
          shortName = scan.next();
          teamAssigned = scan.next();

          addNode(
              nodeID, xcoord, ycoord, floor, building, nodeType, longName, shortName, teamAssigned);
        }
        scan.close();

      } else {
        String nodeID = "";
        String startNode = "";
        String endNode = "";

        // delete current nodes
        PreparedStatement pstmt = null;

        try {
          pstmt = connection.prepareStatement("DELETE FROM Edges");

          pstmt.execute();
          pstmt.close();

        } catch (SQLException throwables) {
          throwables.printStackTrace();
        }

        while (scan.hasNext()) {

          nodeID = scan.next();
          startNode = scan.next();
          endNode = scan.next();

          System.out.println(
              "nodeID:" + nodeID + "\nstartNode:" + startNode + "\nendNode:" + endNode);

          addEdge(nodeID, startNode, endNode);
        }
      }
    } else {
      System.out.println("File is empty.");
    }
  }

  /**
   * @param id
   * @return ArrayList<String>
   * @throws SQLException
   */
  public static ArrayList<String> getInfo(String id) {
    PreparedStatement pstmt = null;
    ArrayList<String> list = new ArrayList<String>();

    try {
      pstmt = connection.prepareStatement("SELECT * FROM Tools WHERE nodeID = '" + id + "'");

      ResultSet rset = pstmt.executeQuery();
      String title = rset.getString("TITLE");
      String toolText = rset.getString("DESCRIP");

      // Adds title and text description of node to a list
      list.add(title);
      list.add(toolText);

      rset.close();
      pstmt.close();

    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

    return list;
  }
}
