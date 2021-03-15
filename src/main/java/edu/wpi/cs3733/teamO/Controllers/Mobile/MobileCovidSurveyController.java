package edu.wpi.cs3733.teamO.Controllers.Mobile;

import com.jfoenix.controls.JFXRadioButton;
import edu.wpi.cs3733.teamO.Controllers.RevampedMobile.MainMobileScreenController;
import edu.wpi.cs3733.teamO.Database.EntryRequestHandling;
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
   * goes back either to the main screen or google nav page depending on isBackHome
   *
   * @param actionEvent
   */
  public void goBack(ActionEvent actionEvent) {
    if (MainMobileScreenController.isBackHome) {
      SwitchScene.goToParentMobile("/RevampedViews/MobileApp/MainMobileScreen.fxml", actionEvent);
      MainMobileScreenController.isBackHome = false;
    } else {
      SwitchScene.goToParentMobile("/Views/MobileApp/MobileGoogleNav.fxml", actionEvent);
      MainMobileScreenController.isBackHome = false;
    }
  }

  /**
   * go to the hospital nav page that directs within hospital campus
   *
   * @param actionEvent
   */
  public void goToHospitalNav(ActionEvent actionEvent) {
    if (no1.isSelected()
        || yes1.isSelected() && no2.isSelected()
        || yes2.isSelected() && no3.isSelected()
        || yes3.isSelected()) {

      // if all three questions are answered, submit survey review request
      SwitchScene.goToParentMobile("/Views/MobileApp/WaitingPage.fxml", actionEvent);

      String requestedBy = "user";

      long millis = System.currentTimeMillis();
      java.util.Date dateN = new java.sql.Date(millis);

      String loc = "Under Review";

      Boolean hasSymptoms = false;
      if (!(no1.isSelected() && no2.isSelected() && no3.isSelected())) hasSymptoms = true;

      System.out.println(
          "Adding this to DB: " + requestedBy + dateN.toString() + loc + symptoms + false + false);

      // RequestHandling.addRequest(requestedBy, dateN, requestType, loc, sum, f1, f2, f3);
      EntryRequestHandling.addEntryRequest(requestedBy, loc, hasSymptoms, false, false);

    } else {
      popupPane.setVisible(true);
      PopupMaker.incompletePopup(popupPane);
    }
  }
}
