package edu.wpi.cs3733.teamO.Controllers.Popups;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.teamO.Database.UserHandling;
import edu.wpi.cs3733.teamO.HelperClasses.PopupMaker;
import edu.wpi.cs3733.teamO.HelperClasses.RegexBoi;
import edu.wpi.cs3733.teamO.Opp;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public class CreateAccountController {

  @FXML private JFXTextField user;
  @FXML private JFXPasswordField pass;
  @FXML private JFXTextField email;
  @FXML private JFXTextField fName;
  @FXML private JFXTextField lName;
  @FXML private StackPane popupPane;

  public void create(ActionEvent actionEvent) {
    if (RegexBoi.checkUsername(user.getText()) && RegexBoi.checkEmail(email.getText())) {

      UserHandling.createAccount(
          user.getText(), pass.getText(), email.getText(), fName.getText(), lName.getText());
      try {
        Parent root = FXMLLoader.load(getClass().getResource("/Views/MainPage.fxml"));
        Opp.getPrimaryStage().getScene().setRoot(root);
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    } else {
      PopupMaker.invalidUsername(popupPane);
    }
  }

  public void close(ActionEvent actionEvent) {}
}
