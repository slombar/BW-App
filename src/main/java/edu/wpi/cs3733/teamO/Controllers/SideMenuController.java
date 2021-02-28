package edu.wpi.cs3733.teamO.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;
import edu.wpi.cs3733.teamO.Opp;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class SideMenuController implements Initializable {
  public JFXButton exitBtn;
  @FXML private JFXHamburger hamburgerSideBtn;
  @FXML private VBox sideVBox;

  public SideMenuController() {}

  public void goToMainMenu(MouseEvent mouseEvent) {}

  @Override
  public void initialize(URL location, ResourceBundle resources) {}

  public void exit(ActionEvent actionEvent) {
    Platform.exit();
  }

  public void goToLogin(ActionEvent actionEvent) {
    try {
      BorderPane root = FXMLLoader.load(getClass().getResource("/Views/Login.fxml"));
      Opp.getPrimaryStage().getScene().setRoot(root);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
