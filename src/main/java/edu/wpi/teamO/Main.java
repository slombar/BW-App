package edu.wpi.teamO;

import edu.wpi.teamO.Controllers.DatabaseFunctionality;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

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
  public static void main(String args[]) {
    String node = "";
    String filePath = "path";
    boolean decision = true;

    DatabaseFunctionality.establishConnection();

    Opp.launch(Opp.class, args);
    // discnnt
    DatabaseFunctionality.shutDownDB();
  }
}
