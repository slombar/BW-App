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
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

public class MainStaffScreenController implements Initializable {
  @FXML private Label usernameTextBox;
  @FXML private JFXDrawer drawer;
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
  @FXML private VBox infoBox;
  @FXML private StackPane infoPane;
  @FXML private ImageView navImg;
  @FXML private ImageView servReqImg;
  @FXML private ImageView googleNavImg;

  @FXML private JFXButton navBtn;
  @FXML private JFXButton servReqBtn;
  @FXML private JFXButton googleNavBtn;
  @FXML private JFXButton covidBtn;
  @FXML private JFXButton entryReqBtn;
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

    usernameTextBox.setText(UserHandling.getSessionUsername());
    infoPane.toBack();
    infoPane.setVisible(false);
    infoBox.toBack();
    infoBox.setVisible(false);

    // determines employee/staff

    String sideMenuUrl;
    if (UserHandling.getEmployee()) {

      sideMenuUrl = "/Views/SideMenuStaff.fxml";
      if (UserHandling.getAdmin()) {
        sideMenuUrl = "/Views/SideMenuAdmin.fxml";
      }
    } else {
      sideMenuUrl = "/Views/SideMenu.fxml";
    }
    // TODO: add side menu stuff

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
            drawer.toBack();
          } else {
            drawer.open(); // this will open slide pane
            drawer.toFront();
            menuVBox.toFront();
          }
        });

    // transition animation of Hamburger icon
    HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
    transition.setRate(-1);

    // customization
    hoverAllBtn();
    clipImages();
  }

  /** hovering over the button will make the buttons darker */
  public void hoverAllBtn() {
    Effects.hoverEffect(navBtn);
    Effects.hoverEffect(servReqBtn);
    Effects.hoverEffect(googleNavBtn);
    Effects.hoverEffectGray(covidBtn);
    Effects.hoverEffectGray(entryReqBtn);
    Effects.hoverEffectGray(parkingBtn);
  }

  public void clipImages() {
    Circle clip1 = new Circle(150, 150, 150);
    Circle clip2 = new Circle(150, 150, 150);
    Circle clip3 = new Circle(150, 150, 150);

    navImg.setClip(clip1);
    servReqImg.setClip(clip2);
    googleNavImg.setClip(clip3);
  }

  public void goToNav(ActionEvent actionEvent) {
    SwitchScene.goToParent("/RevampedViews/DesktopApp/Navigation.fxml");
  }

  public void goToServReq(ActionEvent actionEvent) {
    SwitchScene.goToParent("/RevampedViews/DesktopApp/Services.fxml");
  }

  public void goToGoogleNav(ActionEvent actionEvent) {
    SwitchScene.goToParent("/Views/GoogleMaps/GoogleMapPage.fxml");
  }

  public void goToCovid(ActionEvent actionEvent) {
    SwitchScene.goToParent("/Views/CovidSurvey.fxml");
  }

  public void goToEntryReq(ActionEvent actionEvent) {
    SwitchScene.goToParent("/RevampedViews/DesktopApp/EntryRequests.fxml");
    // SwitchScene.goToParent("/Views/ServiceRequests/RequestList.fxml");
    // temporary until there is a page for this
  }

  public void goToParking(ActionEvent actionEvent) {
    SwitchScene.goToParent("/RevampedViews/DesktopApp/SaveParkingPage.fxml");
  }

  public void goToAccount(ActionEvent actionEvent) {
    SwitchScene.goToParent("/RevampedViews/DesktopApp/ProfilePage.fxml");
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
    SwitchScene.goToParent("/RevampedViews/DesktopApp/Services.fxml");
  }

  public void toEmployees(ActionEvent actionEvent) {
    SwitchScene.goToParent("/Views/ManageEmployees.fxml");
  }

  public void toLogin(ActionEvent actionEvent) {
    SwitchScene.goToParent("/RevampedViews/DesktopApp/SignInPage.fxml");
  }
}
