package edu.wpi.cs3733.teamO.Controllers.Mobile;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXNodesList;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MobileNavController implements Initializable {

  @FXML private JFXNodesList buttonsList;
  private final JFXButton addBtn = new JFXButton("+");
  private final JFXButton parkingBtn = new JFXButton("P");
  private final JFXButton hospitalBtn = new JFXButton("H");
  @FXML private VBox topNavPage;
  @FXML private Pane bottomMenuPane;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // style the buttons
    addBtn.getStyleClass().addAll("nav-menu-button");
    parkingBtn.getStyleClass().addAll("nav-menu-button");
    hospitalBtn.getStyleClass().addAll("nav-menu-button");

    // add them to be in an animated node list
    buttonsList.addAnimatedNode(addBtn);
    buttonsList.addAnimatedNode(parkingBtn);
    buttonsList.addAnimatedNode(hospitalBtn);
    buttonsList.setSpacing(20);
    //    buttonFunction();
  }

  private void buttonFunction() {

    parkingBtn.setOnAction(
        actionEvent -> {
          // save parking spot
          SwitchScene.goToParentMobile("/Views/MobileApp/___.fxml", actionEvent);
        });

    hospitalBtn.setOnAction(
        actionEvent -> {
          // navigate hospital campus
          SwitchScene.goToParentMobile("/Views/MobileApp/___.fxml", actionEvent);
        });
  }
}
