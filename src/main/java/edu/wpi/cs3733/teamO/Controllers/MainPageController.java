package edu.wpi.cs3733.teamO.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
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

public class MainPageController implements Initializable {
  public JFXDrawer drawer;
  public JFXHamburger hamburger;
  @FXML private JFXButton navBtn;
  @FXML private JFXButton googleNavButton;
  @FXML private JFXButton covidBtn;
  @FXML private JFXButton parkingBtn;

  /**
   * the page loading in
   *
   * @param location
   * @param resources
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
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

          if (drawer.isOpened()) drawer.close(); // this will close slide pane
          else drawer.open(); // this will open slide pane
        });

    navBtn.setButtonType(JFXButton.ButtonType.RAISED);
    covidBtn.setButtonType(JFXButton.ButtonType.RAISED);
    googleNavButton.setButtonType(JFXButton.ButtonType.RAISED);
    parkingBtn.setButtonType(JFXButton.ButtonType.RAISED);
    hoverAllBtn();
  }

  /** hovering over the button will make the buttons darker */
  public void hoverAllBtn() {
    Effects.hoverEffect(navBtn);
    Effects.hoverEffect(googleNavButton);
    Effects.hoverEffect(covidBtn);
    Effects.hoverEffect(parkingBtn);
  }

  /**
   * navigates to the pathfinding screen
   *
   * @param actionEvent
   */
  public void goToNav(ActionEvent actionEvent) {
    SwitchScene.goToParent("/Views/NewNavPage.fxml");
  }

  public void goToGifts(ActionEvent actionEvent) {}

  public void goToCovid(
      ActionEvent actionEvent) { // TODO this feels like it would be easy to make something
  }

  public void goToParking(ActionEvent actionEvent) {}

  public void goToGoogleNav(ActionEvent actionEvent) {
    SwitchScene.goToParent("/Views/GoogleMaps/GoogleMapPage.fxml");
  }
}
