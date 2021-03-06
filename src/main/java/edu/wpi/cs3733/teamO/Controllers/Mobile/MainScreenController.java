package edu.wpi.cs3733.teamO.Controllers.Mobile;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXNodesList;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

public class MainScreenController implements Initializable {

  @FXML private JFXNodesList buttonList;
  private final JFXButton welcomeBtn = new JFXButton("Welcome to the B&W Faulkner Hospital");
  private final JFXButton navBtn = new JFXButton("Navigating to the hospital");
  private final JFXButton covidBtn = new JFXButton("Get the latest COVID-19 information");
  private Stage currentStage;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    // style the buttons
    welcomeBtn.getStyleClass().addAll("animated-option-button");
    navBtn.getStyleClass().addAll("animated-option-button");
    covidBtn.getStyleClass().addAll("animated-option-button");

    // add them to be in an animated node list
    buttonList.addAnimatedNode(welcomeBtn);
    buttonList.addAnimatedNode(navBtn);
    buttonList.addAnimatedNode(covidBtn);
    buttonList.setSpacing(20);
    //    currentStage = (Stage) welcomeBtn.getScene().getWindow();
    //    buttonFunction(currentStage);
  }

  private void buttonFunction(Stage currStage) {

    navBtn.setOnAction(
        actionEvent -> {
          SwitchScene.goToParentMobile("/Views/MobileApp/MobileNav.fxml", actionEvent);
        });

    covidBtn.setOnAction(
        actionEvent -> {
          SwitchScene.goToParentMobile("/Views/MobileApp/MobileCovidSurvey.fxml", actionEvent);
        });
  }
}
