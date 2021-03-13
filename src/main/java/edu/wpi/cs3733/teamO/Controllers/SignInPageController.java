package edu.wpi.cs3733.teamO.Controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.teamO.Database.UserHandling;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import java.sql.SQLException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

public class SignInPageController {
  public JFXTextField user;
  public JFXPasswordField pass;
  public Text errorText;

  public void checkEnter(KeyEvent keyEvent) {
    if (keyEvent.getCode() == KeyCode.ENTER) {
      signIn(new ActionEvent());
    }
  }

  public void signIn(ActionEvent actionEvent) {
    user.setStyle("-fx-border-color: none");
    pass.setStyle("-fx-border-color: none");
    errorText.setText("");
    String username = user.getText();
    String password = pass.getText();

    if (username.equals("")) {
      user.setStyle("-fx-border-color: red");
      errorText.setText("Username cannot be blank\n");
    }
    if (password.equals("")) {
      pass.setStyle("-fx-border-color: red");
      errorText.setText(errorText.getText() + "Password cannot be blank");
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
        errorText.setText("Incorrect username or password");
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

  public void exit(ActionEvent actionEvent) {
    Platform.exit();
  }

  public void goToTemp(ActionEvent actionEvent) {}

  public void goToMobileApp(ActionEvent actionEvent) {
    SwitchScene.newWindowParent("/Views/MobileApp/MainScreen.fxml");
  }
}
