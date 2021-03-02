package edu.wpi.cs3733.teamO.Controllers;

import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import edu.wpi.cs3733.teamO.Opp;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class SubmittedController {
  /**
   * Send user back to login page
   *
   * @param actionEvent
   * @throws IOException
   */
  public void back(ActionEvent actionEvent) throws IOException {
    SwitchScene.goToParent("/Views/Login.fxml");
  }
}
