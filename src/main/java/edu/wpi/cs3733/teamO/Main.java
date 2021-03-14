package edu.wpi.cs3733.teamO;

import edu.wpi.cs3733.teamO.Database.DatabaseConnection;

public class Main {

  /**
   * starts the gosh dern program XD
   *
   * @param args who really knows or cares tbh
   */
  public static void main(String args[]) {

    DatabaseConnection.establishConnection();

    Opp.launch(Opp.class, args);
    // disconnect
    DatabaseConnection.shutDownDB();
  }
}
