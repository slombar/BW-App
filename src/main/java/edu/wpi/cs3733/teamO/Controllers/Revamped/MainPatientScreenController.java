package edu.wpi.cs3733.teamO.Controllers.Revamped;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

public class MainPatientScreenController {
  public JFXButton navBtn;
  public JFXButton aboutBtn;
  public JFXButton googleNavBtn;
  public JFXButton covidBtn;
  public JFXButton mobileBtn;
  public JFXButton parkingBtn;

  public void goToNav(ActionEvent actionEvent) {
    SwitchScene.goToParent("/Views/NewNavPage.fxml");
  }

  public void goToAbout(ActionEvent actionEvent) {
    SwitchScene.goToParent("/RevampedViews/DesktopApp/RevampedAboutPage.fxml");
  }

  public void goToGoogleNav(ActionEvent actionEvent) {
    SwitchScene.goToParent("/Views/GoogleMaps/GoogleMapPage.fxml");
  }

  public void goToCovid(ActionEvent actionEvent) {}

  public void goToMobile(ActionEvent actionEvent) {
    SwitchScene.newWindowParent("/Views/MobileApp/MainScreen.fxml");
  }

  public void goToInfo(MouseEvent mouseEvent) {}

  public void goToAccount(ActionEvent actionEvent) {}

  public void goToParking(ActionEvent actionEvent) {}
}
