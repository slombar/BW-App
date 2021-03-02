package edu.wpi.cs3733.teamO.Database;

import java.awt.*;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;

import edu.wpi.cs3733.teamO.Opp;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * TYPES OF REQUESTS language interpreters sanitation services – cleaning up spills, rooms, and
 * public spaces laundry services gift delivery service for presents purchased at the hospital
 * floral delivery service medicine delivery service religious requests such as blessings or last
 * rites. If you implement this component, be aware that multiple religions need to be taken into
 * account. internal patient transportation (transportation for a patient inside the hospital)
 * external patient transportation (ambulance, helicopter, etc) for a patient to be transported to a
 * location outside of the hospital security services facilities maintenance requests including
 * elevator and power issues computer service requests audio/visual requests
 */
public class DataHandling {

  private Desktop desktop = Desktop.getDesktop();

  /**
   * Opens the explorer and sends back the chosen file path
   *
   * @param stage, the stage we are currently on
   * @return
   */
  public static String explorer(Stage stage) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Upload your file to the database.");
    File file = fileChooser.showOpenDialog(stage);

    String filePath = file.getAbsolutePath();

    return filePath;
  }

  /**
   * imports data from csv (delimiter = ,|\n) and determines which database to add it to
   *
   * @param node, whether or not this file is a node file or an edge file
   */
  public static void importExcelData(boolean node) {

    // Open file chooser instead of asking for user input

    String url = explorer(Opp.getPrimaryStage());
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
        boolean visible = false;

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
              nodeID,
              xcoord,
              ycoord,
              floor,
              building,
              nodeType,
              longName,
              shortName,
              teamAssigned,
              true);
        }
        scan.close();

      } else {
        String nodeID = "";
        String startNode = "";
        String endNode = "";
        double length = 0;

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
          length = scan.nextDouble();

          System.out.println(
              "nodeID:"
                  + nodeID
                  + "\nstartNode:"
                  + startNode
                  + "\nendNode:"
                  + endNode
                  + "\nlength:"
                  + length);

          NodesAndEdges.addEdge(startNode, endNode, length);
        }
      }
    } else {
      System.out.println("File is empty.");
    }
  }

  public static void save(boolean node) {
    String url = explorer(Opp.getPrimaryStage());
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
    boolean visible = false;

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
        visible = rset.getBoolean("visible");

        String line =
            String.format(
                "%s,%d,%d,%s,%s,%s,%s,%s,%s,%b ",
                nodeID,
                xcoord,
                ycoord,
                floor,
                building,
                nodeType,
                longName,
                shortName,
                teamAssigned,
                visible);

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
    double length = 0;

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
        length = rset.getDouble("length");
        String line = String.format("%s,%s,%s,%d ", ID, startNode, endNode, length);
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
