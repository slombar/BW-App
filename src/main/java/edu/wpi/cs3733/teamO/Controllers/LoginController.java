package edu.wpi.cs3733.teamO.Controllers;

import edu.wpi.cs3733.teamO.Database.RequestHandling;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import edu.wpi.cs3733.teamO.UserTypes.Staff;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

public class LoginController implements Initializable {

  @FXML private Circle staffBtn;
  @FXML private Circle patientBtn;
  @FXML private Circle adminBtn;
  @FXML private BorderPane borderPane;
  @FXML private StackPane loginPane;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    loginPane.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    loginPane.toBack();
  }

  /**
   * when the the circles or the images on the circle or the text below the circles are clicked, the
   * login is pop up is prompted
   *
   * @param mouseEvent
   */
  public void goToLogin(MouseEvent mouseEvent) {
    SwitchScene.goToGridPane("/Views/PatientSignInPopup.fxml");
  }

  public void goToStaffLogin(MouseEvent mouseEvent) {
    SwitchScene.goToGridPane("/Views/StaffSignInPopup.fxml");
  }

  /**
   * when the log in as guest button is pressed, the apllication goes directly to the main page
   *
   * @param actionEvent
   */
  public void goToMain(ActionEvent actionEvent) {
    SwitchScene.goToBorderPane("/Views/MainPage.fxml");
  }

  public void sadie(ActionEvent actionEvent) {
    String random = "dog";
    Staff staff = new Staff();
    Date date = new Date(2020, 10, 10);
    RequestHandling.addRequest(
        staff, staff, date, date, random, random, random, random, random, random);
  }

  public void tempBTN(ActionEvent actionEvent) {
    SwitchScene.goToBorderPane("/Views/StaffMainPage.fxml");
  }

  /**
   * hovering over the large circles and its images will enlarge the circle
   *
   * @param mouseEvent
   */
  public void hoverAdmin(MouseEvent mouseEvent) {
    adminBtn.setRadius(135);
  }

  public void hoverStaff(MouseEvent mouseEvent) {
    staffBtn.setRadius(135);
  }

  public void hoverPatient(MouseEvent mouseEvent) {
    patientBtn.setRadius(135);
  }

  public void unhoverAdmin(MouseEvent mouseEvent) {
    adminBtn.setRadius(125);
  }

  public void unhoverStaff(MouseEvent mouseEvent) {
    staffBtn.setRadius(125);
  }

  public void unhoverPatient(MouseEvent mouseEvent) {
    patientBtn.setRadius(125);
  }
}
