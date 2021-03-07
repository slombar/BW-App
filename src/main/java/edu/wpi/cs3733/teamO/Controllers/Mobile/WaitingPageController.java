package edu.wpi.cs3733.teamO.Controllers.Mobile;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class WaitingPageController {
  @FXML private JFXButton hospitalNavBtn;
  @FXML private StackPane popupNotification;

  /**
   * go back to the covid 19 survey that will be sent ot the hospital to review entry request
   * @param actionEvent
   */
  public void goToSurvey(ActionEvent actionEvent) {
    SwitchScene.goToParentMobile("/Views/MobileApp/MobileCovidSurvey.fxml", actionEvent);
  }

  /**
   * go to the hospital navigation once a pop up appears and informs the patient of the entrance
   * @param actionEvent
   */
  public void goToHospitalNav(ActionEvent actionEvent) {
    SwitchScene.goToParentMobile("/Views/MobileApp/MobileHospitalNav.fxml", actionEvent);
  }
}
