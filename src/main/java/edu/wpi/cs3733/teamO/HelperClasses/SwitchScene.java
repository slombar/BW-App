package edu.wpi.cs3733.teamO.HelperClasses;

import edu.wpi.cs3733.teamO.Opp;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class SwitchScene {

  /**
   * switches the scene to a new scene
   *
   * @param path to desired FXML file
   */
  public static void goToParent(String path) {
    try {
      Parent root = FXMLLoader.load(SwitchScene.class.getResource(path));
      Opp.getPrimaryStage().getScene().setRoot(root);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
