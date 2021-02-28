package edu.wpi.cs3733.teamO.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
  public static Connection connection;
  public static String dbUrl = "jdbc:derby:Odb";
  public static String driver = "org.apache.derby.jdbc.EmbeddedDriver";

  /**
   * get the conecrtion
   *
   * @return
   */
  public static Connection getConnection() {
    return connection;
  }

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
}
