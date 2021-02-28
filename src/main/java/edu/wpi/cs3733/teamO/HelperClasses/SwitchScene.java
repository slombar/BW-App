package edu.wpi.cs3733.teamO.HelperClasses;

import edu.wpi.cs3733.teamO.Opp;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class SwitchScene {

  public static void goToBorderPane(String path) {
    try {
      BorderPane root = FXMLLoader.load(SwitchScene.class.getResource(path));
      Opp.getPrimaryStage().getScene().setRoot(root);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public static void goToGridPane(String path) {
    try {
      GridPane root = FXMLLoader.load(SwitchScene.class.getResource(path));
      Opp.getPrimaryStage().getScene().setRoot(root);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public static void goToAnchorPane(String path) {
    try {
      AnchorPane root = FXMLLoader.load(SwitchScene.class.getResource(path));
      Opp.getPrimaryStage().getScene().setRoot(root);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
