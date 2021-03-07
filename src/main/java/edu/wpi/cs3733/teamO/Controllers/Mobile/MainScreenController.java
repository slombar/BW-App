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

  @FXML private JFXNodesList buttonsList;
  private final JFXButton welcomeBtn = new JFXButton("Welcome to the B&W Faulkner Hospital");
  private final JFXButton navBtn = new JFXButton("Navigating to the hospital");
  private final JFXButton covidBtn = new JFXButton("Get the latest COVID-19 information");
  private Stage currentStage;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    // style the buttons
    welcomeBtn.getStyleClass().addAll("main-menu-button");
    welcomeBtn.setButtonType(JFXButton.ButtonType.RAISED);
    navBtn.getStyleClass().addAll("main-menu-button");
    navBtn.setButtonType(JFXButton.ButtonType.RAISED);
    covidBtn.getStyleClass().addAll("main-menu-button");
    covidBtn.setButtonType(JFXButton.ButtonType.RAISED);

    // add them to be in an animated node list
    buttonsList.addAnimatedNode(welcomeBtn);
    buttonsList.addAnimatedNode(navBtn);
    buttonsList.addAnimatedNode(covidBtn);
    buttonsList.setSpacing(20);
    buttonFunction();
  }

  private void buttonFunction() {

    navBtn.setOnAction(
        actionEvent -> {
          SwitchScene.goToParentMobile("/Views/MobileApp/MobileGoogleNav.fxml", actionEvent);
        });

    covidBtn.setOnAction(
        actionEvent -> {
          SwitchScene.goToParentMobile("/Views/MainPage.fxml", actionEvent);
        });
  }
}
