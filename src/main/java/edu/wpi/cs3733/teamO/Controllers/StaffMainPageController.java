package edu.wpi.cs3733.teamO.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import edu.wpi.cs3733.teamO.HelperClasses.Effects;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import edu.wpi.cs3733.teamO.Opp;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class StaffMainPageController implements Initializable {

  @FXML private JFXButton navBtn;
  @FXML private JFXButton requestBtn;
  @FXML private JFXButton covidBtn;
  @FXML private JFXButton parkingBtn;
  @FXML private JFXDrawer drawer;
  @FXML private JFXHamburger hamburger;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    // Set drawer to SideMenu
    try {
      VBox vbox = FXMLLoader.load(getClass().getResource("/Views/SideMenu.fxml"));
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
    Effects.hoverEffect(covidBtn);
    Effects.hoverEffect(parkingBtn);
  }

  public void goToNav(ActionEvent actionEvent) {
    SwitchScene.goToGridPane("/Views/NewNavPage.fxml");
  }

  public void goToCovid(ActionEvent actionEvent) {}

  public void goToParking(ActionEvent actionEvent) {}

  public void goToRequest(ActionEvent actionEvent) {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("/Views/ServiceRequests/RequestPage.fxml"));
      Opp.getPrimaryStage().getScene().setRoot(root);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    SwitchScene.goToBorderPane("/Views/ServiceRequests/RequestPage.fxml");
  }
}
