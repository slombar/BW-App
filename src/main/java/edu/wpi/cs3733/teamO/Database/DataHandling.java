package edu.wpi.cs3733.teamO.Database;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class DataHandling {

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
          pstmt = DatabaseConnection.getConnection().prepareStatement("DELETE FROM Nodes");

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

          NodesAndEdges.addNode(
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
          pstmt = DatabaseConnection.getConnection().prepareStatement("DELETE FROM Edges");

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

          NodesAndEdges.addEdge(nodeID, startNode, endNode);
        }
      }
    } else {
      System.out.println("File is empty.");
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
      pstmt = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM Nodes");
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

  private static void saveEdges(String url) {

    String ID = "";
    String startNode = "";
    String endNode = "";

    // Process the results

    try {
      PreparedStatement pstmt = null;
      pstmt = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM Edges");
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
}
