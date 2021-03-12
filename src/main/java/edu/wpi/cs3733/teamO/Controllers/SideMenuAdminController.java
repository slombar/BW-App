package edu.wpi.cs3733.teamO.Controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.teamO.Database.UserHandling;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class SideMenuAdminController implements Initializable {
  @FXML private JFXButton employeeButton;
  @FXML private JFXButton reqBtn;
  @FXML private Label nameLabel;
  @FXML private JFXButton navBtn;
  @FXML private JFXButton mainMenuBtn;
  @FXML private VBox sideVBox;
  @FXML private JFXButton logout;
  @FXML private JFXButton notificationBtn;
  @FXML private JFXButton checkInBtn;
  @FXML private JFXButton appointmentsBtn;
  @FXML private JFXButton settingBtn;
  @FXML private JFXButton exitBtn;

  public SideMenuAdminController() {}

  /**
   * sets the name at the top to the username of the user
   *
   * @param location
   * @param resources
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    nameLabel.setText(UserHandling.getUsername());
  }

  /**
   * closes the application
   *
   * @param actionEvent
   */
  public void exit(ActionEvent actionEvent) {
    Platform.exit();
  }

  public void toNotifications(ActionEvent actionEvent) {}

  public void toCheckIn(ActionEvent actionEvent) {}

  public void toAppointments(ActionEvent actionEvent) {}

  public void toSettings(ActionEvent actionEvent) {}

  /**
   * goes to the login page
   *
   * @param actionEvent
   */
  public void goToLogin(ActionEvent actionEvent) {
    SwitchScene.goToParent("/Views/Login.fxml");
  }

  /**
   * goes to the main page
   *
   * @param actionEvent
   */
  public void toMain(ActionEvent actionEvent) {
    SwitchScene.goToParent("/Views/StaffMainPage.fxml");
  }

  /**
   * goes to the navigation page
   *
   * @param actionEvent
   */
  public void toNav(ActionEvent actionEvent) {
    SwitchScene.goToParent("/Views/NewNavPage.fxml");
  }

  /**
   * goes to the menu of service requests
   *
   * @param actionEvent
   */
  public void toReq(ActionEvent actionEvent) {
    SwitchScene.goToParent("/Views/ServiceRequests/RequestPage.fxml");
  }

  /**
   * goes to the manage employees page
   *
   * @param actionEvent
   */
  public void manageEmployees(ActionEvent actionEvent) {
    SwitchScene.goToParent("/Views/ManageEmployees.fxml");
  }

  // TODO theres no way this shit has to be this long; not sure what the best way is though
  public void mouseOnNot(MouseEvent mouseEvent) {
    notificationBtn.setUnderline(true);
    notificationBtn.setButtonType(JFXButton.ButtonType.RAISED);
  }

  public void mouseOffNot(MouseEvent mouseEvent) {
    notificationBtn.setUnderline(false);
    notificationBtn.setButtonType(JFXButton.ButtonType.FLAT);
  }

  public void mouseOnCheck(MouseEvent mouseEvent) {
    checkInBtn.setUnderline(true);
    checkInBtn.setButtonType(JFXButton.ButtonType.RAISED);
  }

  public void mouseOffCheck(MouseEvent mouseEvent) {
    checkInBtn.setUnderline(false);
    checkInBtn.setButtonType(JFXButton.ButtonType.FLAT);
  }

  public void mouseOnApp(MouseEvent mouseEvent) {
    appointmentsBtn.setUnderline(true);
    appointmentsBtn.setButtonType(JFXButton.ButtonType.RAISED);
  }

  public void mouseOffApp(MouseEvent mouseEvent) {
    appointmentsBtn.setUnderline(false);
    appointmentsBtn.setButtonType(JFXButton.ButtonType.FLAT);
  }

  public void mouseOnLog(MouseEvent mouseEvent) {
    logout.setUnderline(true);
    logout.setButtonType(JFXButton.ButtonType.RAISED);
  }

  public void mouseOffLog(MouseEvent mouseEvent) {
    logout.setUnderline(false);
    logout.setButtonType(JFXButton.ButtonType.FLAT);
  }

  public void mouseOnExit(MouseEvent mouseEvent) {
    exitBtn.setUnderline(true);
    exitBtn.setButtonType(JFXButton.ButtonType.RAISED);
  }

  public void mouseOffExit(MouseEvent mouseEvent) {
    exitBtn.setUnderline(false);
    exitBtn.setButtonType(JFXButton.ButtonType.FLAT);
  }

  public void mouseOnSet(MouseEvent mouseEvent) {
    settingBtn.setUnderline(true);
    settingBtn.setButtonType(JFXButton.ButtonType.RAISED);
  }

  public void mouseOffSet(MouseEvent mouseEvent) {
    settingBtn.setUnderline(false);
    settingBtn.setButtonType(JFXButton.ButtonType.FLAT);
  }

  public void mouseOnMain(MouseEvent mouseEvent) {
    mainMenuBtn.setUnderline(true);
    mainMenuBtn.setButtonType(JFXButton.ButtonType.RAISED);
  }

  public void mouseOffMain(MouseEvent mouseEvent) {
    mainMenuBtn.setUnderline(false);
    mainMenuBtn.setButtonType(JFXButton.ButtonType.FLAT);
  }

  public void mouseOnNav(MouseEvent mouseEvent) {
    navBtn.setUnderline(true);
    navBtn.setButtonType(JFXButton.ButtonType.RAISED);
  }

  public void mouseOffNav(MouseEvent mouseEvent) {
    navBtn.setUnderline(false);
    navBtn.setButtonType(JFXButton.ButtonType.FLAT);
  }

  public void mouseOnReq(MouseEvent mouseEvent) {
    reqBtn.setUnderline(true);
    reqBtn.setButtonType(JFXButton.ButtonType.RAISED);
  }

  public void mouseOffReq(MouseEvent mouseEvent) {
    reqBtn.setUnderline(false);
    reqBtn.setButtonType(JFXButton.ButtonType.FLAT);
  }

  public void mouseOnEmp(MouseEvent mouseEvent) {
    employeeButton.setUnderline(true);
    employeeButton.setButtonType(JFXButton.ButtonType.RAISED);
  }

  public void mouseOffEmp(MouseEvent mouseEvent) {
    employeeButton.setUnderline(false);
    employeeButton.setButtonType(JFXButton.ButtonType.FLAT);
  }
}
