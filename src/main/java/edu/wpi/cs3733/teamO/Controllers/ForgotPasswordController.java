package edu.wpi.cs3733.teamO.Controllers;

import edu.wpi.cs3733.teamO.Database.UserHandling;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ForgotPasswordController {

  @FXML TextField forgotEmail;

  public void resetPassword(ActionEvent actionEvent) {
    UserHandling.promptForgotPassword(forgotEmail.getText());
    SwitchScene.goToParent("/Views/Login.fxml");
  }

  public void close(ActionEvent actionEvent) {
    SwitchScene.goToParent("/Views/Login.fxml");
  }
}
