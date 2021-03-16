package edu.wpi.cs3733.teamO.Controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.teamO.Database.UserHandling;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SignInPageController implements Initializable {
  @FXML private StackPane infoPane;
  @FXML private VBox infoBox;
  @FXML private StackPane infoPane2;
  @FXML private VBox infoBox2;
  @FXML private JFXTextField user;
  @FXML private JFXPasswordField pass;
  @FXML private Text errorText;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    infoPane.toBack();
    infoPane.setVisible(false);
    infoBox.toBack();
    infoBox.setVisible(false);
    infoPane2.toBack();
    infoPane2.setVisible(false);
    infoBox2.toBack();
    infoBox2.setVisible(false);
  }

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
          SwitchScene.goToParent("/RevampedViews/DesktopApp/MainStaffScreen.fxml");
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
    SwitchScene.goToParent("/RevampedViews/DesktopApp/CreatePatientAccount.fxml");
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

  public void goToTemp(ActionEvent actionEvent) {
    SwitchScene.goToParent("/Views/TempChecker.fxml");
  }

  public void goToMobileApp(ActionEvent actionEvent) {
    SwitchScene.newWindowParent("/RevampedViews/MobileApp/MainMobileScreen.fxml");
  }

  public void nextInfo(ActionEvent actionEvent) {
    infoPane.toBack();
    infoPane.setVisible(false);
    infoBox.toBack();
    infoBox.setVisible(false);
    infoPane2.toFront();
    infoPane2.setVisible(true);
    infoBox2.toFront();
    infoBox2.setVisible(true);
  }

  public void exitInfo(ActionEvent actionEvent) {
    infoPane.toBack();
    infoPane.setVisible(false);
    infoBox.toBack();
    infoBox.setVisible(false);
    infoPane2.toBack();
    infoPane2.setVisible(false);
    infoBox2.toBack();
    infoBox2.setVisible(false);
  }

  public void needHelp(ActionEvent actionEvent) {
    infoPane.toFront();
    infoPane.setVisible(true);
    infoBox.toFront();
    infoBox.setVisible(true);
    infoPane2.toBack();
    infoPane2.setVisible(false);
    infoBox2.toBack();
    infoBox2.setVisible(false);
  }
}
