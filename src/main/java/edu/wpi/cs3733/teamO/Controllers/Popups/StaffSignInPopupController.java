package edu.wpi.cs3733.teamO.Controllers.Popups;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.teamO.Database.UserHandling;
import edu.wpi.cs3733.teamO.HelperClasses.PopupMaker;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import edu.wpi.cs3733.teamO.Opp;
import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

public class StaffSignInPopupController {

  @FXML private StackPane popupPane;
  @FXML private JFXTextField user;
  @FXML private JFXPasswordField pass;

  /**
   * attempts to sign in to an admin or employee account
   *
   * @param actionEvent
   */
  public void signIn(ActionEvent actionEvent) {
    String username = user.getText();
    String password = pass.getText();

    if (username.equals("") || password.equals("")) {
      PopupMaker.incompletePopup(popupPane);
    } else {
      try {
        UserHandling.loginEmployee(username, password);
        SwitchScene.goToParent("/Views/StaffMainPage.fxml");
      } catch (SQLException e) {
        PopupMaker.invalidLogin(popupPane);
      }
    }
  }

  /**
   * goes to the previous page (login page)
   *
   * @param actionEvent
   */
  public void close(ActionEvent actionEvent) {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("/Views/Login.fxml"));
      Opp.getPrimaryStage().getScene().setRoot(root);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * allows the enter key to submit the login
   *
   * @param keyEvent
   */
  public void checkEnter(KeyEvent keyEvent) {
    if (keyEvent.getCode() == KeyCode.ENTER) {
      signIn(new ActionEvent());
    }
  }
}
