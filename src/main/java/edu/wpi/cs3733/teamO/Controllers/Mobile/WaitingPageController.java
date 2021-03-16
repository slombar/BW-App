package edu.wpi.cs3733.teamO.Controllers.Mobile;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.teamO.HelperClasses.PopupMaker;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javax.swing.*;

public class WaitingPageController extends JComponent implements Initializable {
  private static boolean isSurveyApproved;
  private static String location;

  @FXML private JFXButton hospitalNavBtn;
  @FXML private JFXButton entryStatusBtn;
  @FXML private StackPane popupNotification;
  @FXML private StackPane spinnerPane;
  // @FXML private JFXPanel jfxPanel;
  @FXML private StackPane stackPane;
  @FXML private AnchorPane wPageAnchorPane;
  public static boolean isMainEntrance;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    hospitalNavBtn.setDisable(true);
    entryStatusBtn.setDisable(false);
    // spinnerPane.setVisible(true);

    Task<Void> EntryTask =
        new Task<Void>() {

          @Override
          protected Void call() throws Exception {
            checkStatus();
            return null;
          }
        };

    Thread entryThread = new Thread(EntryTask);
    entryThread.start();
    awaitingEntryApproval(EntryTask);

    SwingNode s = new SwingNode();
    WaitingPageController c = new WaitingPageController();
    s.setContent(c);
  }

  void awaitingEntryApproval(Task<Void> EntryTask) {
    //    ProgressIndicator progress = new ProgressIndicator();
    //    // spinnerPane.getChildren().add(progress);
    //    progress.setProgress(1.0);
    //    progress.progressProperty().bind(EntryTask.progressProperty());
    //    // spinnerPane.toFront();

    FXMLLoader fxmlLoader =
        new FXMLLoader(getClass().getResource("/Views/MobileApp/DinoGame.fxml"));
    try {
      wPageAnchorPane.getChildren().add(fxmlLoader.load());
    } catch (IOException e) {
      e.printStackTrace();
    }

    stackPane.toFront();
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
      // spinnerPane.setVisible(false);
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
      // spinnerPane.setVisible(false);
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
