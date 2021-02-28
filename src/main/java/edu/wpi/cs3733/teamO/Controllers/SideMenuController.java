package edu.wpi.cs3733.teamO.Controllers;

import com.jfoenix.controls.JFXHamburger;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class SideMenuController implements Initializable {
  @FXML private JFXHamburger hamburgerSideBtn;
  @FXML private VBox sideVBox;

  public SideMenuController() {}

  public void goToMainMenu(MouseEvent mouseEvent) {}

  @Override
  public void initialize(URL location, ResourceBundle resources) {}
}
