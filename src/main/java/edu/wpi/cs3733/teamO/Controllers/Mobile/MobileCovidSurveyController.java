package edu.wpi.cs3733.teamO.Controllers.Mobile;

import com.jfoenix.controls.JFXRadioButton;
import edu.wpi.cs3733.teamO.HelperClasses.PopupMaker;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;

public class MobileCovidSurveyController implements Initializable {
  @FXML private StackPane popupPane;
  @FXML private JFXRadioButton yes1;
  @FXML private JFXRadioButton no1;
  @FXML private JFXRadioButton yes2;
  @FXML private JFXRadioButton no2;
  @FXML private JFXRadioButton yes3;
  @FXML private JFXRadioButton no3;
  @FXML private ToggleGroup closeContact;
  @FXML private ToggleGroup diagnosis;
  @FXML private ToggleGroup symptoms;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {}

  /**
   * go to the google nav page that directs to and from hospital
   * @param actionEvent
   */
  public void goToGoogleNav(ActionEvent actionEvent) {
    SwitchScene.goToParentMobile("/Views/MobileApp/MobileGoogleNav.fxml", actionEvent);
  }

  /**
   * go to the hospital nav page that directs within hospital campus
   * @param actionEvent
   */
  public void goToHospitalNav(ActionEvent actionEvent) {
    if (no1.isSelected()
        || yes1.isSelected() && no2.isSelected()
        || yes2.isSelected() && no3.isSelected()
        || yes3.isSelected()) {
      if (no1.isSelected() && no2.isSelected() && no3.isSelected()) {
        // go to waiting page
        SwitchScene.goToParentMobile("/Views/MobileApp/WaitingPage.fxml", actionEvent);
      } else {
        // go to additional questions
        SwitchScene.goToParentMobile("/Views/MobileApp/WaitingPage.fxml", actionEvent);
      }
    } else {
      popupPane.setVisible(true);
      PopupMaker.incompletePopup(popupPane);
    }
  }
}
