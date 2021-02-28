package edu.wpi.cs3733.teamO.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class SideMenuController implements Initializable {
  @FXML private JFXButton notificationBtn;
  @FXML private JFXButton checkInBtn;
  @FXML private JFXButton appointmentsBtn;
  @FXML private JFXButton loginBtn;
  @FXML private JFXButton settingBtn;
  @FXML private JFXButton exitBtn;
  @FXML private JFXHamburger hamburgerSideBtn;
  @FXML private VBox sideVBox;

  public SideMenuController() {}

  public void goToMainMenu(MouseEvent mouseEvent) {}

  @Override
  public void initialize(URL location, ResourceBundle resources) {}

  public void exit(ActionEvent actionEvent) {
    Platform.exit();
  }

  public void mouseOn(MouseEvent mouseEvent) {
    exitBtn.setUnderline(true);
    exitBtn.setButtonType(JFXButton.ButtonType.RAISED);
  }

  public void mouseOff(MouseEvent mouseEvent) {
    exitBtn.setUnderline(false);
    exitBtn.setButtonType(JFXButton.ButtonType.FLAT);
  }

  public void toNotifications(ActionEvent actionEvent) {}

  public void toCheckIn(ActionEvent actionEvent) {}

  public void toAppointments(ActionEvent actionEvent) {}

  public void toLogin(ActionEvent actionEvent) {}

  public void toSettings(ActionEvent actionEvent) {}
}
