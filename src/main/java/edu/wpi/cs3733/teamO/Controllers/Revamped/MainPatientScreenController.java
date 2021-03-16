package edu.wpi.cs3733.teamO.Controllers.Revamped;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import edu.wpi.cs3733.teamO.Database.UserHandling;
import edu.wpi.cs3733.teamO.HelperClasses.Effects;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

public class MainPatientScreenController implements Initializable {
  @FXML private VBox menuVBox;
  @FXML private JFXHamburger hamburger;
  @FXML private JFXButton profileBtn;
  @FXML private JFXButton homeBtn;
  @FXML private JFXButton navBtn1;
  @FXML private JFXButton trackBtn;
  @FXML private JFXButton reqBtn;
  @FXML private JFXButton patientsBtn;
  @FXML private JFXButton employeesBtn;
  @FXML private JFXButton loginBtn;
  @FXML private JFXDrawer drawer;
  @FXML private StackPane infoPane;
  @FXML private VBox infoBox;
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
    if (!UserHandling.getEmployee()) {
      reqBtn.setVisible(false);
      reqBtn.setDisable(true);
      employeesBtn.setVisible(false);
      employeesBtn.setDisable(true);
    } else if (!UserHandling.getAdmin() && UserHandling.getEmployee()) {
      employeesBtn.setVisible(false);
      employeesBtn.setDisable(true);
    }

    infoPane.toBack();
    infoPane.setVisible(false);
    infoBox.toBack();
    infoBox.setVisible(false);
    hoverAllBtn();
    clipImages();
    try {
      VBox vbox =
          FXMLLoader.load(getClass().getResource("/RevampedViews/DesktopApp/NewSideMenu.fxml"));
      drawer.setSidePane(vbox);
    } catch (IOException e) {
      e.printStackTrace();
    }

    // transition animation of Hamburger icon
    HamburgerBackArrowBasicTransition burgerTransition =
        new HamburgerBackArrowBasicTransition(hamburger);
    burgerTransition.setRate(-1);

    // click event - mouse click
    hamburger.addEventHandler(
        MouseEvent.MOUSE_PRESSED,
        (e) -> {
          burgerTransition.setRate(burgerTransition.getRate() * -1);
          burgerTransition.play();
          if (drawer.isOpened()) {
            drawer.close(); // this will close slide pane
          } else {
            drawer.open(); // this will open slide pane
            drawer.toFront();
            menuVBox.toFront();
          }
        });

    // transition animation of Hamburger icon
    HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
    transition.setRate(-1);
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

  public void goToAccount(ActionEvent actionEvent) {
    SwitchScene.goToParent("/RevampedViews/DesktopApp/ProfilePage.fxml");
  }

  public void goToParking(ActionEvent actionEvent) {
    SwitchScene.goToParent("/RevampedViews/DesktopApp/SaveParkingPage.fxml");
  }

  public void exitInfo(ActionEvent actionEvent) {
    infoPane.toBack();
    infoPane.setVisible(false);
    infoBox.toBack();
    infoBox.setVisible(false);
  }

  public void goToInfo(ActionEvent actionEvent) {
    infoPane.toFront();
    infoPane.setVisible(true);
    infoBox.toFront();
    infoBox.setVisible(true);
  }

  public void toProfile(ActionEvent actionEvent) {
    SwitchScene.goToParent("/RevampedViews/DesktopApp/ProfilePage.fxml");
  }

  public void toHome(ActionEvent actionEvent) {
    String sideMenu = "/RevampedViews/DesktopApp/MainStaffScreen.fxml";
    if (!UserHandling.getEmployee()) sideMenu = "/RevampedViews/DesktopApp/MainPatientScreen.fxml";
    SwitchScene.goToParent(sideMenu);
  }

  public void toNav(ActionEvent actionEvent) {
    SwitchScene.goToParent("/RevampedViews/DesktopApp/Navigation.fxml");
  }

  public void toTrack(ActionEvent actionEvent) {
    SwitchScene.goToParent("/Views/CovidSurvey.fxml");
  }

  public void toReq(ActionEvent actionEvent) {
    SwitchScene.goToParent("/RevampedViews/DesktopApp/EntryRequests.fxml");
  }

  public void toEmployees(ActionEvent actionEvent) {
    SwitchScene.goToParent("/Views/ManageEmployees.fxml");
  }

  public void toLogin(ActionEvent actionEvent) {
    SwitchScene.goToParent("/RevampedViews/DesktopApp/SignInPage.fxml");
  }
}
