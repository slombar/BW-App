package edu.wpi.cs3733.teamO.Controllers.Revamped;

import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import javafx.event.ActionEvent;

public class NavHelpController {
  public void goToNav(ActionEvent actionEvent) {
    SwitchScene.goToParent("/RevampedViews/DesktopApp/Navigation.fxml");
  }
}
