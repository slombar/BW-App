package edu.wpi.cs3733.teamO;

import edu.wpi.cs3733.teamO.Database.DatabaseConnection;

public class Main {

  // C:\Users\sadie\IdeaProjects\BW-App\src\main\resources\MapOEdges.csv
  public static void main(String args[]) {
    String node = "";
    String filePath = "path";
    boolean decision = true;

    DatabaseConnection.establishConnection();

    Opp.launch(Opp.class, args);
    // disconnect
    DatabaseConnection.shutDownDB();
  }
}
