package edu.wpi.cs3733.teamO.Controllers.Mobile;

import static edu.wpi.cs3733.teamO.Database.UserHandling.getUsername;

import com.jfoenix.controls.JFXRadioButton;
import edu.wpi.cs3733.teamO.Database.RequestHandling;
import edu.wpi.cs3733.teamO.HelperClasses.PopupMaker;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import java.net.URL;
import java.sql.Date;
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
   *
   * @param actionEvent
   */
  public void goBack(ActionEvent actionEvent) {
    if (MainScreenController.isBackHome) {
      SwitchScene.goToParentMobile("/Views/MobileApp/MobileMainScreen.fxml", actionEvent);
      MainScreenController.isBackHome = false;
    } else {
      SwitchScene.goToParentMobile("/Views/MobileApp/MobileGoogleNav.fxml", actionEvent);
      MainScreenController.isBackHome = false;
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
      // if (no1.isSelected() && no2.isSelected() && no3.isSelected()) {

      // if all three questions are answered, submit survey review request
      SwitchScene.goToParentMobile("/Views/MobileApp/WaitingPage.fxml", actionEvent);

      String requestedBy = getUsername();

      java.util.Date utilDate = new java.util.Date();
      java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
      java.sql.Date dateN = (Date) new java.util.Date(sqlDate.getTime());

      String requestType = "CV19";
      String loc = null;
      String sum = null;
      String f1 = String.valueOf(no1.isSelected());
      String f2 = String.valueOf(no2.isSelected());
      String f3 = String.valueOf(no3.isSelected());

      System.out.println(
          "Adding this to DB: "
              + requestedBy
              + dateN.toString()
              + requestType
              + loc
              + sum
              + f1
              + f2
              + f3);

      RequestHandling.addRequest(requestedBy, dateN, requestType, loc, sum, f1, f2, f3);

    } else {
      popupPane.setVisible(true);
      PopupMaker.incompletePopup(popupPane);
    }
  }
}
