package edu.wpi.cs3733.teamO.Controllers.Mobile;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;

public class WaitingPageController implements Initializable {
  @FXML private JFXButton hospitalNavBtn;
  @FXML private StackPane popupNotification;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    hospitalNavBtn.setDisable(true);
    // TODO: need to use the following popups
    //    PopupMaker.mainEntranceNotif(popupNotification);
    //    PopupMaker.covidEntranceNotif(popupNotification);
  }

  /**
   * go back to the covid 19 survey that will be sent ot the hospital to review entry request
   *
   * @param actionEvent
   */
  public void goToSurvey(ActionEvent actionEvent) {
    SwitchScene.goToParentMobile("/Views/MobileApp/MobileCovidSurvey.fxml", actionEvent);
  }

  /**
   * go to the hospital navigation once a pop up appears and informs the patient of the entrance
   *
   * @param actionEvent
   */
  public void goToHospitalNav(ActionEvent actionEvent) {
    SwitchScene.goToParentMobile("/Views/MobileApp/MobileHospitalNav.fxml", actionEvent);
  }
}
