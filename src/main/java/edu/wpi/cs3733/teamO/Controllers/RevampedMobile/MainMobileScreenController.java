package edu.wpi.cs3733.teamO.Controllers.RevampedMobile;

import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

public class MainMobileScreenController implements Initializable {

  @Override
  public void initialize(URL location, ResourceBundle resources) {}

  public void goToNav(ActionEvent actionEvent) {
    SwitchScene.goToParentMobile("/Views/MobileApp/MobileCovidSurvey.fxml", actionEvent);
  }

  public void goToAbout(ActionEvent actionEvent) {}

  public void goToGoogleNav(ActionEvent actionEvent) {
    SwitchScene.goToParentMobile("/Views/MobileApp/MobileGoogleNav.fxml", actionEvent);
  }

  public void goToParking(ActionEvent actionEvent) {}

  public void gotToLogin(ActionEvent actionEvent) {}

  public void goToProfile(ActionEvent actionEvent) {}

  public void goToLogin(ActionEvent actionEvent) {}
}
