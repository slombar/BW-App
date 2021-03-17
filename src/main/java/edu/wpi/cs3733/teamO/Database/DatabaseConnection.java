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
   * @return the connection of the database
   */
  public static Connection getConnection() {
    return connection;
  }

  /**
   * connect to the database that is: if the embedded driver wasn't fucked trash shit!! (:
   *
   * @return true if you properly connected
   */
  public static boolean establishConnection() {

    try {
      Class.forName(driver);
      connection = DriverManager.getConnection(dbUrl);

    } catch (ClassNotFoundException e) {

      e.printStackTrace();
      return false;
    } catch (SQLException t) {

      t.printStackTrace();
      return false;
    }

    return true;
  }

  /** shuts down connection to db */
  public static void shutDownDB() {
    try {
      DriverManager.getConnection(dbUrl + ";shutdown=true");

    } catch (SQLException throwables) {

    }
  }
}
