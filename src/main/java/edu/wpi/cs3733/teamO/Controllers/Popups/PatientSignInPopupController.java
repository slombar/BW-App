package edu.wpi.cs3733.teamO.Controllers.Popups;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.teamO.Database.UserHandling;
import edu.wpi.cs3733.teamO.HelperClasses.PopupMaker;
import edu.wpi.cs3733.teamO.Opp;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class PatientSignInPopupController {

  @FXML private StackPane popupPane;
  @FXML private JFXTextField user;
  @FXML private JFXPasswordField pass;

  @FXML
  public void signIn(ActionEvent actionEvent) {
    try {
      UserHandling.login(user.getText(), pass.getText());
    }catch (Exception e){
      e.printStackTrace();
      PopupMaker.invalidLogin(popupPane);
    }

    try {
      BorderPane root = FXMLLoader.load(getClass().getResource("/Views/MainPage.fxml"));
      Opp.getPrimaryStage().getScene().setRoot(root);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public void close(ActionEvent actionEvent) {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("/Views/Login.fxml"));
      Opp.getPrimaryStage().getScene().setRoot(root);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public void createAccount(ActionEvent actionEvent) {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("/Views/CreateAccount.fxml"));
      Opp.getPrimaryStage().getScene().setRoot(root);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
