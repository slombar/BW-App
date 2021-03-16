package edu.wpi.cs3733.teamO.Controllers.Revamped;

import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.teamO.Database.UserHandling;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class ProfilePageController implements Initializable {
  public JFXTextField emailBox;
  public JFXTextField usernameBox;
  public Label parkingNumberBox;
  public JFXTextField lastNameBox;
  public JFXTextField firstNameBox;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    firstNameBox.setText(UserHandling.getFirstName());
    lastNameBox.setText(UserHandling.getLastName());
    emailBox.setText(UserHandling.getEmail());
    parkingNumberBox.setText(UserHandling.getParkingSpot());
    usernameBox.setText(UserHandling.getUsername());
  }

  public void goBack(ActionEvent actionEvent) {
    if (UserHandling.getEmployee()) {
      SwitchScene.goToParent("/RevampedViews/DesktopApp/MainStaffScreen.fxml");
    } else {
      SwitchScene.goToParent("/RevampedViews/DesktopApp/MainPatientScreen.fxml");
    }
  }

  public void saveChanges(ActionEvent actionEvent) {
    // TODO we have none of this functionality
  }

  public void goToParkingSpot(ActionEvent actionEvent) {
    SwitchScene.goToParent("/RevampedViews/DesktopApp/SaveParkingPage.fxml");
  }
}
