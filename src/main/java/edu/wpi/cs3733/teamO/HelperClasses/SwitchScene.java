package edu.wpi.cs3733.teamO.HelperClasses;

import edu.wpi.cs3733.teamO.Opp;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SwitchScene {

  /**
   * switches the scene to a new scene full screen
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

  /**
   * create a new window for the mobile app
   *
   * @param path
   */
  public static void newWindowParent(String path) {
    try {
      Parent root = FXMLLoader.load(SwitchScene.class.getResource(path));
      Stage stage = new Stage();
      stage.setTitle("Mobile Application");
      stage.setScene(new Scene(root, 355, 600));
      stage.show();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * switches the scene to a new scene in mobile
   *
   * @param path to desired FXML file
   */
  public static void goToParentMobile(String path, ActionEvent actionEvent) {
    try {
      Node node = (Node) actionEvent.getSource();
      Stage thisStage = (Stage) node.getScene().getWindow();
      Parent root = FXMLLoader.load(SwitchScene.class.getResource(path));
      thisStage.getScene().setRoot(root);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
