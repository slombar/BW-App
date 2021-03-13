package edu.wpi.cs3733.teamO.Controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.teamO.Database.UserHandling;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class SignInPageController {
  public JFXTextField user;
  public JFXPasswordField pass;

  public void checkEnter(KeyEvent keyEvent) {
    if (keyEvent.getCode() == KeyCode.ENTER) {
      signIn(new ActionEvent());
    }
  }

  public void signIn(ActionEvent actionEvent) {
    String username = user.getText();
    String password = pass.getText();

    if (username.equals("") || password.equals("")) {
      // PopupMaker.incompletePopup(popupPane);TODO red boxes
    } else {
      try {
        UserHandling.login(username, password);
        if (UserHandling.getLoginStatus() && UserHandling.getEmployee()) {
          SwitchScene.goToParent("/Views/MainPage.fxml");
        } else {
          SwitchScene.goToParent("/Views/CovidSurvey.fxml");
        }
      } catch (SQLException e) {
        // PopupMaker.invalidLogin(popupPane); TODO red boxes
      }
    }
  }

  public void createAccount(ActionEvent actionEvent) {
    // TODO
  }

  public void forgotPassword(ActionEvent actionEvent) {
    // TODO
  }

  public void close(ActionEvent actionEvent) {
    // TODO where should this go?
  }
}
