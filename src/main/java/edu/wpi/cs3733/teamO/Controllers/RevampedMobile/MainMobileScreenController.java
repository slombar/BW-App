package edu.wpi.cs3733.teamO.Controllers.RevampedMobile;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

public class MainMobileScreenController implements Initializable {

  public static boolean isBackHome =
      false; // keeps track of whether the last page was the home page
  public static boolean isBackGoogle =
      false; // keeps track of whether the last page was the google nav page
  @FXML private JFXButton exitAppBtn;

  @Override
  public void initialize(URL location, ResourceBundle resources) {}

  public void goToNav(ActionEvent actionEvent) {
    isBackHome = true;
    SwitchScene.goToParentMobile("/Views/MobileApp/MobileCovidSurvey.fxml", actionEvent);
  }

  public void goToAbout(ActionEvent actionEvent) {
    SwitchScene.goToParentMobile("/RevampedViews/MobileApp/MobileAbout.fxml", actionEvent);
  }

  public void goToGoogleNav(ActionEvent actionEvent) {
    SwitchScene.goToParentMobile("/Views/MobileApp/MobileGoogleNav.fxml", actionEvent);
  }

  public void goToParking(ActionEvent actionEvent) {
    isBackHome = true;
    SwitchScene.goToParentMobile("/Views/MobileApp/SaveParking.fxml", actionEvent);
  }

  public void exit(ActionEvent actionEvent) {
    Stage stage = (Stage) exitAppBtn.getScene().getWindow();
    stage.close();
  }
}
