package edu.wpi.cs3733.teamO.Controllers.Popups;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.teamO.Database.UserHandling;
import edu.wpi.cs3733.teamO.HelperClasses.PopupMaker;
import edu.wpi.cs3733.teamO.HelperClasses.RegexBoi;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

public class CreateEmployeeAccountController {

  @FXML private JFXCheckBox adminCheck;
  @FXML private JFXTextField user;
  @FXML private JFXPasswordField pass;
  @FXML private JFXTextField email;
  @FXML private JFXTextField fName;
  @FXML private JFXTextField lName;
  @FXML private StackPane popupPane;

  /**
   * creates a new employee account
   *
   * @param actionEvent
   */
  public void create(ActionEvent actionEvent) {
    if (RegexBoi.checkUsername(user.getText())) {
      if (RegexBoi.checkEmail(email.getText())) {

        try {
          UserHandling.createEmployee(
              user.getText(),
              pass.getText(),
              email.getText(),
              fName.getText(),
              lName.getText(),
              adminCheck.isSelected());
          SwitchScene.goToParent("/Views/ManageEmployees.fxml");
        } catch (SQLException throwables) {
          PopupMaker.usernameAlreadyInUse(popupPane);
          throwables.printStackTrace();
        }
      } else {
        PopupMaker.invalidEmail(popupPane);
      }
    } else {
      PopupMaker.invalidUsername(popupPane);
    }
  }

  /**
   * checks if the pressed key is *enter* and submits the form if so.
   *
   * @param keyEvent
   */
  public void checkEnter(KeyEvent keyEvent) {
    if (keyEvent.getCode() == KeyCode.ENTER) {
      create(new ActionEvent());
    }
  }

  /**
   * pretty self explanatory tbh... it goes to that page.
   *
   * @param actionEvent
   */
  public void goToManageEmployee(ActionEvent actionEvent) {
    SwitchScene.goToParent("/Views/ManageEmployees.fxml");
  }
}
