package edu.wpi.cs3733.teamO.Controllers.Mobile;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.teamO.HelperClasses.PopupMaker;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;

public class WaitingPageController implements Initializable {
  private static boolean isSurveyApproved;
  private static String location;
  @FXML private JFXButton hospitalNavBtn;
  @FXML private JFXButton entryStatusBtn;
  @FXML private StackPane popupNotification;
  @FXML private StackPane spinnerPane;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    hospitalNavBtn.setDisable(true);
    entryStatusBtn.setDisable(false);
/*
    Task<Void> EntryTask =
            new Task<Void>() {

              @Override
              protected Void call() throws Exception {
                checkEntryStatus();
                return null;
              }
            };


    ProgressIndicator progress = new ProgressIndicator();
    spinnerPane.getChildren().add(progress);
    progress.setProgress(1.0);
    progress.progressProperty().bind(EntryTask.progressProperty());
    spinnerPane.toFront();
 */
    // TODO: need to use the following popups
    // enable button once employee edits form and grab location

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
    isSurveyApproved = false;
    hospitalNavBtn.setDisable(true);
  }

  public void checkEntryStatus(ActionEvent actionEvent) {

    if (isSurveyApproved) {
      hospitalNavBtn.setDisable(false);
      // popup to say you can now continue
      if (location.toLowerCase().contains("covid")) {
        PopupMaker.covidEntranceNotif(popupNotification);
      } else {
        PopupMaker.mainEntranceNotif(popupNotification);
      }
      // TODO grab which entrance
    }
  }

  public void checkEntryStatus() {

    if (isSurveyApproved) {
      hospitalNavBtn.setDisable(false);
      // popup to say you can now continue
      if (location.toLowerCase().contains("covid")) {
        PopupMaker.covidEntranceNotif(popupNotification);
      } else {
        PopupMaker.mainEntranceNotif(popupNotification);
      }
      // TODO grab which entrance
    }
  }

  public static void setSurveyApproved(boolean status) {
    isSurveyApproved = status;
  }

  public static void setEntrance(String entrance) {
    location = entrance;
  }
}
