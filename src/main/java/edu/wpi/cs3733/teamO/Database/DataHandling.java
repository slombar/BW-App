package edu.wpi.cs3733.teamO.Database;

import edu.wpi.cs3733.teamO.Opp;
import java.awt.*;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class DataHandling {

  private Desktop desktop = Desktop.getDesktop();
  private static FileChooser fileChooser = new FileChooser();

  /**
   * Opens the explorer and sends back the chosen file path
   *
   * @param stage, the stage we are currently on
   * @return
   */
  public static String explorer(Stage stage) {
    File file = fileChooser.showOpenDialog(stage);
    String filePath = file.getAbsolutePath();

    return filePath;
  }

  private static void uploadEdges() {}

  private static void uploadNodes() {}

  /** imports data from csv (delimiter = ,|\n) and determines which database to add it to */
  public static void importExcelData() {
    fileChooser.setTitle("Import your Node File");
    String url = explorer(Opp.getPrimaryStage());
    // Open file chooser instead of asking for user input

    Scanner scan = null;
    Pattern d = Pattern.compile(",|\r\n");

    /** Nodes upload */

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
        visible = true;

        try {
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
              visible);
        } catch (SQLException throwables) {
          throwables.printStackTrace();
        }

        scan.close();
      }
    } else {
      System.out.println("File is empty");
    }

    /** Edges upload */
    fileChooser.setTitle("Import your Edge File");
    scan = null;
    url = explorer(Opp.getPrimaryStage());
    // Open file chooser instead of asking for user input

    // try to open file
    try {
      scan = new Scanner(new File(url)).useDelimiter(d);
      System.out.println("File read! Importing data...");

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    // if the file isn't empty, read it in
    if (scan.hasNext()) {
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
        scan.next();
        startNode = scan.next();
        endNode = scan.next();

        try {
          NodesAndEdges.addNewEdge(startNode, endNode);
        } catch (SQLException throwables) {
          throwables.printStackTrace();
        }
      }
    }
  }

  /** Save both the nodes and the edges files */
  public static void save() {
    fileChooser.setTitle("Choose the file to save Nodes to");
    String url = explorer(Opp.getPrimaryStage());
    saveNodes(url);

    fileChooser.setTitle("Choose the file to save Edges to");
    url = explorer(Opp.getPrimaryStage());
    saveEdges(url);
  }

  /**
   * Save the node file to your chosen destination
   *
   * @param url
   */
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

  /**
   * Save the edge file to your chosen destination
   *
   * @param url
   */
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
        String line = String.format("%s,%s,%s,%d ", ID, startNode, endNode);
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
