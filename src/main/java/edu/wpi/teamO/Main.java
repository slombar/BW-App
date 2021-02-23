package edu.wpi.teamO;

import edu.wpi.teamO.Controllers.DatabaseFunctionality;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

  public static boolean fileUploading = true;

  public static void fileSave() {

    boolean connected = false;
    String filePath = "";
    connected = DatabaseFunctionality.establishConnection();

    if (connected) {

      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

      try {
        // prompt user for their file path and read in data
        System.out.println("Enter your complete file path.");
        filePath = reader.readLine();

      } catch (IOException e) {
        System.out.println("Input not read.");
      }

      // Switch saveNodes with saveEdges depending on your file
      DatabaseFunctionality.saveNodes(filePath);
      System.out.println("File Saved!");
    }
  }

  // C:\Users\sadie\IdeaProjects\BW-App\src\main\resources\MapOEdges.csv
  public static void main(String args[]) throws IOException {
    String node = "";
    String filePath = "path";
    boolean decision = true;
    // cnnect
    boolean b = DatabaseFunctionality.establishConnection();

    if (b && fileUploading) {

      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

      try {
        // museum's name
        System.out.println("Will you be uploading a node file? (y/n)");
        // remove header line (1st row) from excel file
        node = reader.readLine();

        if (node.charAt(0) == 'y') {
          decision = true;
          System.out.println("Adding data to Nodes.");
        } else if (node.charAt(0) == 'n') {
          decision = false;
          System.out.println("Adding data to Edges.");
        } else {
          System.out.println("No y/n answer provided. Try again.");
        }

        // prompt user for their file path and read in data
        System.out.println("Enter your complete file path.");
        filePath = reader.readLine();

      } catch (IOException e) {
        System.out.println("Input not read.");
      }
      DatabaseFunctionality.importExcelData(filePath, decision);
      System.out.println("Data imported!");
    }

    // Opp.launch(Opp.class, args);
    DatabaseFunctionality.shutDownDB();
  }
}
