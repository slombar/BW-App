package edu.wpi.cs3733.teamO;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Opp extends Application {

  private static Stage primaryStage;

  public static Stage getPrimaryStage() {
    return primaryStage;
  }

  public void init() {
    // This happens every time a scene starts up. Kinda cool but not currently useful
    System.out.println("Starting Up");
  }

  public void start(Stage ps) {

    Opp.primaryStage = ps;

    try {
      AnchorPane root = FXMLLoader.load(getClass().getResource("/Views/Index.fxml"));
      Scene scene = new Scene(root);
      ps.setScene(scene);
      ps.setFullScreen(true);
      ps.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void stop() {
    // shut down database and print message to user

    System.out.println("Shutting Down");
  }
}
