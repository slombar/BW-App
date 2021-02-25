package edu.wpi.cs3733.teamO.Database;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataHandling {
    public static void save(String url, boolean node) {
        if (node) {
            saveNodes(url);
        } else {
            saveEdges(url);
        }
    }



    private static void saveNodes(String url) {

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
