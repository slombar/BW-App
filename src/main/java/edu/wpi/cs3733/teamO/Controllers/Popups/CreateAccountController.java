package edu.wpi.cs3733.teamO.Controllers.Popups;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.teamO.Database.UserHandling;
import edu.wpi.cs3733.teamO.Opp;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

public class CreateAccountController {

  @FXML private JFXTextField username;
  @FXML private JFXPasswordField password;
  @FXML private JFXTextField email;
  @FXML private JFXTextField fName;
  @FXML private JFXTextField lName;

  public void create(ActionEvent actionEvent) {
    UserHandling.createAccount(
        username.getText(), password.getText(), email.getText(), fName.getText(), lName.getText());
    try {
      GridPane root = FXMLLoader.load(getClass().getResource("/Views/MainPage.fxml"));
      Opp.getPrimaryStage().getScene().setRoot(root);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public void close(ActionEvent actionEvent) {}
}
