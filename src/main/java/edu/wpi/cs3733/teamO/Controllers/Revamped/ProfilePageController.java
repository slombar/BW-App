package edu.wpi.cs3733.teamO.Controllers.Revamped;

import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.teamO.Database.UserHandling;
import edu.wpi.cs3733.teamO.HelperClasses.RegexBoi;
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
    firstNameBox.setText(UserHandling.getSessionFirstName());
    lastNameBox.setText(UserHandling.getSessionLastName());
    emailBox.setText(UserHandling.getSessionEmail());
    parkingNumberBox.setText(UserHandling.getParkingSpot());
    usernameBox.setText(UserHandling.getSessionUsername());
    usernameBox.setDisable(true);
  }

  public void goBack(ActionEvent actionEvent) {
    if (UserHandling.getEmployee()) {
      SwitchScene.goToParent("/RevampedViews/DesktopApp/MainStaffScreen.fxml");
    } else {
      SwitchScene.goToParent("/RevampedViews/DesktopApp/MainPatientScreen.fxml");
    }
  }

  public void saveChanges(ActionEvent actionEvent) {
    firstNameBox.setStyle("-fx-border-color: none");
    lastNameBox.setStyle("-fx-border-color: none");
    emailBox.setStyle("-fx-border-color: none");

    String newFName = firstNameBox.getText();
    String newLName = lastNameBox.getText();
    String newEMail = emailBox.getText();
    boolean isGood = true;

    if (newFName.equals("")) {
      isGood = false;
      firstNameBox.setStyle("-fx-border-color: red");
    }
    if (newLName.equals("")) {
      isGood = false;
      lastNameBox.setStyle("-fx-border-color: red");
    }
    if(!RegexBoi.checkEmail(newEMail)){
      isGood = false;
      emailBox.setStyle("-fx-border-color: red");
    }
    if(isGood){
      UserHandling.editSessionUser(newFName, newLName, newEMail);
    }
  }

  public void goToParkingSpot(ActionEvent actionEvent) {
    SwitchScene.goToParent("/RevampedViews/DesktopApp/SaveParkingPage.fxml");
  }
}
