package edu.wpi.cs3733.teamO.Robot;

import com.jfoenix.controls.JFXRadioButton;
import edu.wpi.cs3733.teamO.HelperClasses.PopupMaker;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;

public class TempCheckerController extends Thread implements Initializable {

  @FXML private JFXRadioButton No2;
  @FXML private JFXRadioButton No3;
  @FXML private JFXRadioButton No1;
  @FXML private JFXRadioButton yes1;
  @FXML private JFXRadioButton yes2;
  @FXML private JFXRadioButton yes3;
  @FXML private StackPane popupPane;

  private final Serial serial = new Serial();
  private final BooleanProperty connection = new SimpleBooleanProperty(false);

  @Override
  public void initialize(URL location, ResourceBundle resources) {}

  /**
   * this is connected to a link and shows the covid symptom popup
   *
   * @param actionEvent
   */
  public void covidSymptoms(ActionEvent actionEvent) {
    PopupMaker.covidSymptoms(popupPane);
  }

  /**
   * button to go back to the previous page instead of completing the survey
   *
   * @param actionEvent
   */
  public void backTempCheck(ActionEvent actionEvent) {
    SwitchScene.goToParent("/RevampedViews/DesktopApp/SignInPage.fxml");
  }

  /**
   * submit button for the covid survey
   *
   * @param actionEvent
   */
  public void checkTemp(ActionEvent actionEvent) {
    // TODO we should do something with this data, also i think this should use the toggle group
    if (No1.isSelected()
        || yes1.isSelected() && No2.isSelected()
        || yes2.isSelected() && No3.isSelected()
        || yes3.isSelected()) {
      if (No1.isSelected() && No2.isSelected() && No3.isSelected()) {
        //        SwitchScene.goToParent("/Views/MainPage.fxml");
        // SwitchScene.goToParent("/RevampedViews/DesktopApp/MainPatientScreen.fxml");
      } else {
        PopupMaker.covidRisk(popupPane);
      }
    } else {
      PopupMaker.incompletePopup(popupPane);
    }

    connectArduino();
  }

  private void connectArduino() {
    // Connect to Arduino port and start listening
    if (!connection.get()) {

      serial.connect();
      connection.set(!serial.getPortName().isEmpty());
    }
  }
}
