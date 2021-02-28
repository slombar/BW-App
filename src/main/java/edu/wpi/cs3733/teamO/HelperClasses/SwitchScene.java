package edu.wpi.cs3733.teamO.HelperClasses;

import edu.wpi.cs3733.teamO.Opp;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class SwitchScene {

  /**
   * switches teh scene to a scene made on a border pane
   * @param path to desired FXML file
   */
  public static void goToBorderPane(String path) {
    try {
      BorderPane root = FXMLLoader.load(SwitchScene.class.getResource(path));
      Opp.getPrimaryStage().getScene().setRoot(root);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * switches teh scene to a scene made on a grid pane
   * @param path to desired FXML file
   */
  public static void goToGridPane(String path) {
    try {
      GridPane root = FXMLLoader.load(SwitchScene.class.getResource(path));
      Opp.getPrimaryStage().getScene().setRoot(root);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * switches teh scene to a scene made on a anchor pane
   * @param path to desired FXML file
   */
  public static void goToAnchorPane(String path) {
    try {
      AnchorPane root = FXMLLoader.load(SwitchScene.class.getResource(path));
      Opp.getPrimaryStage().getScene().setRoot(root);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
