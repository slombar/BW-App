package edu.wpi.cs3733.teamO.Controllers.Popups;

import com.jfoenix.controls.JFXRadioButton;
import edu.wpi.cs3733.teamO.HelperClasses.PopupMaker;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;

public class CovidSurveyController {
  @FXML private JFXRadioButton yes1;
  @FXML private JFXRadioButton yes2;
  @FXML private JFXRadioButton yes3;
  @FXML private StackPane popupPane;
  @FXML private ToggleGroup diagnosed;
  @FXML private ToggleGroup contact;
  @FXML private ToggleGroup symptoms;

  public void covidSymptoms(ActionEvent actionEvent) {
    PopupMaker.covidSymptoms(popupPane);
  }

  public void goToLoginMenu(ActionEvent actionEvent) {
    SwitchScene.goToParent("/Views/Login.fxml");
  }

  public void submitCovidForm(ActionEvent actionEvent) {

    if (yes1.isSelected() || yes2.isSelected() || yes3.isSelected()) {
      PopupMaker.covidRisk(popupPane);
    } else {
      SwitchScene.goToParent("/Views/MainPage.fxml");
    }
  }
}
