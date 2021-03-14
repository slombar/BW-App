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
  public static Boolean isMainEntrance = null;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    hospitalNavBtn.setDisable(true);
    entryStatusBtn.setDisable(false);
    spinnerPane.setVisible(true);

    Task<Void> EntryTask =
        new Task<Void>() {

          @Override
          protected Void call() throws Exception {
            checkStatus();

            /*Platform.runLater(
               new Runnable() {
                 @Override
                 public void run() {

                   // popup to say you can now continue
                   spinnerPane.setOnMouseClicked(null);
                   if (location.toLowerCase().contains("main")) {
                     spinnerPane.setVisible(false);
                     PopupMaker.mainEntranceNotif(popupNotification);
                     isMainEntrance = true;
                   } else {
                     spinnerPane.setVisible(false);
                     PopupMaker.covidEntranceNotif(popupNotification);
                     isMainEntrance = false;
                   }
                 }
               });

            */
            return null;
          }
        };

    Thread entryThread = new Thread(EntryTask);
    entryThread.start();
    awaitingEntryApproval(EntryTask);

    // TODO: need to use the following popups
    // enable button once employee edits form and grab location

    //    PopupMaker.mainEntranceNotif(popupNotification);
    //    PopupMaker.covidEntranceNotif(popupNotification);
  }

  void awaitingEntryApproval(Task<Void> EntryTask) {
    ProgressIndicator progress = new ProgressIndicator();
    spinnerPane.getChildren().add(progress);
    progress.setProgress(1.0);
    progress.progressProperty().bind(EntryTask.progressProperty());
    spinnerPane.toFront();
  }
  /*btn.setOnAction(event -> {
      iv.setImage(openImage);
      btn.setGraphic(iv);

      PauseTransition pause = new PauseTransition(Duration.seconds(2));
      pause.setOnFinished(e -> iv.setImage(unnamedImage));
      pause.play();

  });

     */

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
      spinnerPane.setVisible(false);
      // popup to say you can now continue
      if (location.toLowerCase().contains("covid")) {
        PopupMaker.covidEntranceNotif(popupNotification);
        isMainEntrance = false;
      } else {
        PopupMaker.mainEntranceNotif(popupNotification);
        isMainEntrance = true;
      }
      // TODO grab which entrance
    }
  }

  public void checkStatus() {

    if (isSurveyApproved) {
      hospitalNavBtn.setDisable(false);
      spinnerPane.setVisible(false);
      // popup to say you can now continue
      if (location.toLowerCase().contains("main")) {
        PopupMaker.mainEntranceNotif(popupNotification);
        isMainEntrance = true;
      } else {
        PopupMaker.covidEntranceNotif(popupNotification);
        isMainEntrance = false;
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
