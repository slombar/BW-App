package edu.wpi.cs3733.teamO.Controllers.Popups;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class PatientSignInPopupController {

  @FXML private JFXPasswordField password;
  @FXML private JFXTextField username;

  public void signIn(ActionEvent actionEvent) {}

  public void close(ActionEvent actionEvent) {
    System.out.println("EASY");
  }

  public void createAccount(ActionEvent actionEvent) {}
}
