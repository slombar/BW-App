package edu.wpi.cs3733.teamO.Controllers.Mobile;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class MobileDirectionsController implements Initializable {
  @FXML private Label textDirections;
  @FXML private JFXButton backButton;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    // TODO: popuplate (Label) textDirections with text directions
    backButton.setButtonType(JFXButton.ButtonType.RAISED);
  }

  /**
   * returns the previous map page based on booleans
   *
   * @param actionEvent
   */
  public void goBack(ActionEvent actionEvent) {
    if (MainScreenController.isBackGoogle) {
      MainScreenController.isBackGoogle = false;
      SwitchScene.goToParentMobile("/Views/MobileApp/MobileGoogleNav.fxml", actionEvent);
    } else {
      MainScreenController.isBackGoogle = false;
      SwitchScene.goToParentMobile("/Views/MobileApp/MobileHospitalNav.fxml", actionEvent);
    }
  }
}
