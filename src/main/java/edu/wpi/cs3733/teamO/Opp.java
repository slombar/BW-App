package edu.wpi.cs3733.teamO;

import static edu.wpi.cs3733.teamO.GraphSystem.Graph.GRAPH;

import edu.wpi.cs3733.teamO.GraphSystem.Graph;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Opp extends Application {

  private static Stage primaryStage;

  public static Stage getPrimaryStage() {
    return primaryStage;
  }

  public void init() {
    // This happens every time a scene starts up. Kinda cool but not currently useful

  }

  public void start(Stage ps) {
    Graph g = GRAPH;

    Opp.primaryStage = ps;

    try {

      Parent root =
          FXMLLoader.load(getClass().getResource("/RevampedViews/DesktopApp/SignInPage.fxml"));
      Scene scene = new Scene(root);
      Image icon =
          new Image(getClass().getResourceAsStream("/Brigham_and_Womens_Hospital_logo.png"));
      ps.getIcons().add(icon);
      ps.setScene(scene);
      ps.setFullScreen(true);
      ps.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void stop() {
    // shut down database and print message to user

  }
}
