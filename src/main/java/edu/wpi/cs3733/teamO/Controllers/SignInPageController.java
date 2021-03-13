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
    user.setStyle("-fx-border-color: none");
    pass.setStyle("-fx-border-color: none");
    String username = user.getText();
    String password = pass.getText();

    if (username.equals("")) {
      user.setStyle("-fx-border-color: red");
      // TODO explain error
    }
    if (password.equals("")) {
      pass.setStyle("-fx-border-color: red");
      // TODO explain error
    } else {
      try {
        UserHandling.login(username, password);
        if (UserHandling.getLoginStatus() && UserHandling.getEmployee()) {
          SwitchScene.goToParent("/Views/StaffMainPage.fxml");
        } else {
          SwitchScene.goToParent("/Views/CovidSurvey.fxml");
        }
      } catch (SQLException e) {
        user.setStyle("-fx-border-color: red");
        pass.setStyle("-fx-border-color: red");
        // TODO explain error
      }
    }
  }

  public void createAccount(ActionEvent actionEvent) {
    SwitchScene.goToParent("/Views/CreatePatientAccount.fxml");
  }

  public void forgotPassword(ActionEvent actionEvent) {
    // TODO
  }

  public void close(ActionEvent actionEvent) {
    // TODO where should this go?
  }
}
