package edu.wpi.cs3733.teamO.Controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.teamO.Database.UserHandling;
import edu.wpi.cs3733.teamO.HelperClasses.RegexBoi;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

public class CreatePatientAccountController {
  @FXML private Text errorText;
  @FXML private JFXPasswordField pass;
  @FXML private JFXTextField user;
  @FXML private JFXTextField email;
  @FXML private JFXTextField last;
  @FXML private JFXTextField first;
  @FXML private JFXPasswordField confirmPass;

  public void createAccount(ActionEvent actionEvent) {
    user.setStyle("-fx-border-color: none");
    pass.setStyle("-fx-border-color: none");
    email.setStyle("-fx-border-color: none");
    first.setStyle("-fx-border-color: none");
    last.setStyle("-fx-border-color: none");
    confirmPass.setStyle("-fx-border-color: none");
    errorText.setText("");

    String username = user.getText();
    String password = pass.getText();
    String eMail = email.getText();
    String firstName = first.getText();
    String lastName = last.getText();
    String confirmPassword = confirmPass.getText();

    // check to make sure that all fields are filled in

    if (username.equals("")) {
      user.setStyle("-fx-border-color: red");
      errorText.setText("Fields cannot be left blank");
    }
    if (password.equals("")) {
      pass.setStyle("-fx-border-color: red");
      errorText.setText("Fields cannot be left blank");
    }
    if (eMail.equals("")) {
      email.setStyle("-fx-border-color: red");
      errorText.setText("Fields cannot be left blank");
    }
    if (firstName.equals("")) {
      first.setStyle("-fx-border-color: red");
      errorText.setText("Fields cannot be left blank");
    }
    if (lastName.equals("")) {
      last.setStyle("-fx-border-color: red");
      errorText.setText("Fields cannot be left blank");
    }
    if (confirmPassword.equals("")) {
      confirmPass.setStyle("-fx-border-color: red");
      errorText.setText("Fields cannot be left blank");
    } else if (RegexBoi.checkUsername(username)) {
      if (RegexBoi.checkEmail(eMail)) {
        if (password.equals(confirmPassword)) {

          // tries to create an account in the database, only fails if there is already a matching
          // primary key

          try {
            UserHandling.createAccount(
                user.getText(), pass.getText(), email.getText(), first.getText(), last.getText());
            SwitchScene.goToParent("/RevampedViews/DesktopApp/SignInPage.fxml");

          } catch (SQLException throwables) {
            errorText.setText("Username is already in use\nTry again with a different username");

            throwables.printStackTrace();
          }
        } else {
          pass.setStyle("-fx-border-color: red");
          confirmPass.setStyle("-fx-border-color: red");
          errorText.setText("Passwords do not match\nPlease re-enter password fields");
        }
      } else {
        email.setStyle("-fx-border-color: red");
        errorText.setText("Email is not formatted correctly\nCheck formatting and retype");
      }
    } else {
      user.setStyle("-fx-border-color: red");
      errorText.setText("Username does not fit requirements\nPlease do not use special characters");
    }
  }

  public void checkEnter(KeyEvent keyEvent) {
    if (keyEvent.getCode() == KeyCode.ENTER) {
      createAccount(new ActionEvent());
    }
  }

  public void close(ActionEvent actionEvent) {
    SwitchScene.goToParent("/RevampedViews/DesktopApp/SignInPage.fxml");
  }
}
