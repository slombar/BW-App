package edu.wpi.cs3733.teamO.Controllers.Mobile;

import edu.wpi.cs3733.teamO.Database.UserHandling;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SaveParkingController implements Initializable {

  public Label currentSpot;
  public TextField input;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    currentSpot.setText(UserHandling.getParkingSpot());
  }

  /**
   * returns the previous map page based on booleans
   *
   * @param actionEvent
   */
  public void goBack(ActionEvent actionEvent) {
    SwitchScene.goToParentMobile("/Views/MobileApp/MobileHospitalNav.fxml", actionEvent);
  }

  // TODO make the textfield autocomplete things?
  public void saveSpot(ActionEvent actionEvent) {
    String spot = input.getText().substring(6, 8);
    UserHandling.setParkingSpot(spot);
    System.out.println(spot);
    currentSpot.setText(spot);
  }
}
