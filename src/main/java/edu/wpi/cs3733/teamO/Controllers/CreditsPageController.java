package edu.wpi.cs3733.teamO.Controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import javafx.event.ActionEvent;

public class CreditsPageController {
  public JFXButton back;

  public void goToAboutPage(ActionEvent actionEvent) {
    SwitchScene.goToParent("/RevampedViews/DesktopApp/RevampedAboutPage.fxml");
  }
}
