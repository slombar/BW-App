package edu.wpi.cs3733.teamO.Controllers;

import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import javafx.event.ActionEvent;

public class CreditsPageController {
  public void goToAboutPage(ActionEvent actionEvent) {
    SwitchScene.goToParent("/Views/AboutPage.fxml");
  }
}
