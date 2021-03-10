package edu.wpi.cs3733.teamO.Controllers;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class StaffMainPageController implements Initializable {

  @FXML private JFXButton navBtn;
  @FXML private JFXButton requestBtn;
  @FXML private JFXButton googleNavButton;
  @FXML private JFXButton parkingBtn;
  @FXML private JFXDrawer drawer;
  @FXML private JFXHamburger hamburger;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    System.out.println("Employee " + UserHandling.getEmployee());
    System.out.println("Admin " + UserHandling.getAdmin());

    String sideMenuUrl;
    if (UserHandling.getEmployee()) {
      System.out.println("EMPLOYEE");
      sideMenuUrl = "/Views/SideMenuStaff.fxml";
      if (UserHandling.getAdmin()) {
        sideMenuUrl = "/Views/SideMenuAdmin.fxml";
        System.out.println("ADMIN");
      }
    } else {
      sideMenuUrl = "/Views/SideMenu.fxml";
    }

    // Set drawer to SideMenu
    try {
      VBox vbox = FXMLLoader.load(getClass().getResource(sideMenuUrl));
      drawer.setSidePane(vbox);
    } catch (IOException e) {
      e.printStackTrace();
    }

    // transition animation of Hamburger icon
    HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
    transition.setRate(-1);

    // click event - mouse click
    hamburger.addEventHandler(
        MouseEvent.MOUSE_PRESSED,
        (e) -> {
          transition.setRate(transition.getRate() * -1);
          transition.play();
          if (drawer.isOpened()) {
            drawer.close(); // this will close slide pane
          } else {
            drawer.open(); // this will open slide pane
          }
        });
    hoverAllBtn();
  }

  public void hoverAllBtn() {
    Effects.hoverEffect(navBtn);
    Effects.hoverEffect(requestBtn);
    Effects.hoverEffect(googleNavButton);
    Effects.hoverEffect(parkingBtn);
  }

  public void goToNav(ActionEvent actionEvent) {
    SwitchScene.goToParent("/Views/NewNavPage.fxml");
  }

  public void goToParking(ActionEvent actionEvent) {}

  public void goToRequest(ActionEvent actionEvent) {
    SwitchScene.goToParent("/Views/ServiceRequests/RequestPage.fxml");
  }

  public void goToGoogleNav(ActionEvent actionEvent) {
    SwitchScene.goToParent("/Views/GoogleMaps/GoogleMapPage.fxml");
  }
}
