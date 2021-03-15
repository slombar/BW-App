package edu.wpi.cs3733.teamO.Controllers.Revamped;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.teamO.HelperClasses.Effects;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

public class MainPatientScreenController implements Initializable {
  @FXML private ImageView navImg;
  @FXML private ImageView aboutImg;
  @FXML private ImageView googleNavImg;

  @FXML private JFXButton navBtn;
  @FXML private JFXButton aboutBtn;
  @FXML private JFXButton googleNavBtn;
  @FXML private JFXButton covidBtn;
  @FXML private JFXButton mobileBtn;
  @FXML private JFXButton parkingBtn;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    hoverAllBtn();
    clipImages();
  }

  /** hovering over the button will make the buttons darker */
  public void hoverAllBtn() {
    Effects.hoverEffect(navBtn);
    Effects.hoverEffect(aboutBtn);
    Effects.hoverEffect(googleNavBtn);
    Effects.hoverEffectGray(covidBtn);
    Effects.hoverEffectGray(mobileBtn);
    Effects.hoverEffectGray(parkingBtn);
  }

  public void clipImages() {
    Circle clip1 = new Circle(150, 150, 150);
    Circle clip2 = new Circle(150, 150, 150);
    Circle clip3 = new Circle(150, 150, 150);

    navImg.setClip(clip1);
    aboutImg.setClip(clip2);
    googleNavImg.setClip(clip3);
  }

  public void goToNav(ActionEvent actionEvent) {
    SwitchScene.goToParent("/RevampedViews/DesktopApp/Navigation.fxml");
  }

  public void goToAbout(ActionEvent actionEvent) {
    SwitchScene.goToParent("/RevampedViews/DesktopApp/RevampedAboutPage.fxml");
  }

  public void goToGoogleNav(ActionEvent actionEvent) {
    SwitchScene.goToParent("/Views/GoogleMaps/GoogleMapPage.fxml");
  }

  public void goToCovid(ActionEvent actionEvent) {
    SwitchScene.goToParent("/Views/CovidSurvey.fxml");
  }

  public void goToMobile(ActionEvent actionEvent) {
    SwitchScene.newWindowParent("/RevampedViews/MobileApp/MainMobileScreen.fxml");
  }

  public void goToInfo(MouseEvent mouseEvent) {}

  public void goToAccount(ActionEvent actionEvent) {}

  public void goToParking(ActionEvent actionEvent) {}
}
