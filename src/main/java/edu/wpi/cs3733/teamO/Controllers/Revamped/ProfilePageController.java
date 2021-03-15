package edu.wpi.cs3733.teamO.Controllers.Revamped;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import javafx.event.ActionEvent;

public class ProfilePageController {
  public JFXButton backBtn;

  public void goBack(ActionEvent actionEvent) {
    SwitchScene.goToParent("/RevampedViews/DesktopApp/MainStaffScreen.fxml");
  }
}
