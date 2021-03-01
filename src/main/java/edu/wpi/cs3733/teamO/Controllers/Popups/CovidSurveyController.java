package edu.wpi.cs3733.teamO.Controllers.Popups;

import edu.wpi.cs3733.teamO.HelperClasses.PopupMaker;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;

public class CovidSurveyController {
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

//        Toggle diag = diagnosed.getSelectedToggle();
//        System.out.println(diag.getUserData());
        // TODO
        SwitchScene.goToParent("/Views/MainPage.fxml");
  }
}
