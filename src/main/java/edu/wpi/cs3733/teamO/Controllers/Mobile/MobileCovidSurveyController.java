package edu.wpi.cs3733.teamO.Controllers.Mobile;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;

public class MobileCovidSurveyController implements Initializable {
  @FXML private ToggleGroup closeContact;
  @FXML private ToggleGroup diagnosis;
  @FXML private ToggleGroup symptoms;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {}
}
