package edu.wpi.cs3733.teamO.Controllers;

import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class AboutController {
  public Button backBtn;

  public void back(ActionEvent actionEvent) {
    SwitchScene.goToParent("/Views/MainPage.fxml");
  }
}
