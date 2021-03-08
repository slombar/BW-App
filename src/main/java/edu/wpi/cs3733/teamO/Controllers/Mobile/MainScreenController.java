package edu.wpi.cs3733.teamO.Controllers.Mobile;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXNodesList;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class MainScreenController implements Initializable {

  @FXML private JFXNodesList buttonsList;
  private final JFXButton welcomeBtn = new JFXButton("I would Like to...");
  private final JFXButton googleNavBtn = new JFXButton("Navigate to the hospital");
  private final JFXButton hospitalNavBtn = new JFXButton("Navigate within the hospital");
  public static boolean isBackHome = false;
  public static boolean isBackGoogle = false;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    // style the buttons
    welcomeBtn.getStyleClass().addAll("main-menu-button");
    welcomeBtn.setButtonType(JFXButton.ButtonType.RAISED);
    googleNavBtn.getStyleClass().addAll("main-button");
    googleNavBtn.setButtonType(JFXButton.ButtonType.RAISED);
    hospitalNavBtn.getStyleClass().addAll("main-button");
    hospitalNavBtn.setButtonType(JFXButton.ButtonType.RAISED);

    // add them to be in an animated node list
    buttonsList.addAnimatedNode(welcomeBtn);
    buttonsList.addAnimatedNode(googleNavBtn);
    buttonsList.addAnimatedNode(hospitalNavBtn);
    buttonsList.setSpacing(20);
    buttonFunction();
  }

  /** adding on action functionality to the buttons in the JFXNodeslist */
  private void buttonFunction() {
    googleNavBtn.setOnAction(
        actionEvent -> {
          SwitchScene.goToParentMobile("/Views/MobileApp/MobileGoogleNav.fxml", actionEvent);
        });

    hospitalNavBtn.setOnAction(
        actionEvent -> {
          isBackHome = true;
          SwitchScene.goToParentMobile("/Views/MobileApp/MobileCovidSurvey.fxml", actionEvent);
        });
  }
}
