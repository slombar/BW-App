package edu.wpi.cs3733.teamO.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import edu.wpi.cs3733.teamO.HelperClasses.Effects;
import edu.wpi.cs3733.teamO.Opp;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class MainPageController implements Initializable {
  public JFXDrawer drawer;
  public JFXHamburger hamburger;
  @FXML private JFXButton navBtn;
  @FXML private JFXButton giftsBtn;
  @FXML private JFXButton covidBtn;
  @FXML private JFXButton parkingBtn;

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

    // click event - mouse click
    hamburger.addEventHandler(
        MouseEvent.MOUSE_PRESSED,
        (e) -> {
          transition.setRate(transition.getRate() * -1);
          transition.play();

          if (drawer.isOpened()) drawer.close(); // this will close slide pane
          else drawer.open(); // this will open slide pane
        });
    hoverAllBtn();
  }

  public void hoverAllBtn() {
    Effects.hoverEffect(navBtn);
    Effects.hoverEffect(giftsBtn);
    Effects.hoverEffect(covidBtn);
    Effects.hoverEffect(parkingBtn);
  }

  public void goToNav(ActionEvent actionEvent) {
    try {
      GridPane root = FXMLLoader.load(getClass().getResource("/Views/NewNavPage.fxml"));
      Opp.getPrimaryStage().getScene().setRoot(root);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public void goToGifts(ActionEvent actionEvent) {}

  public void goToCovid(ActionEvent actionEvent) {}

  public void goToParking(ActionEvent actionEvent) {}
}
