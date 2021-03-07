package edu.wpi.cs3733.teamO.Controllers.Mobile;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXNodesList;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MobileGoogleNavController implements Initializable {

  @FXML private JFXNodesList buttonsList;
  @FXML private VBox topNavPage;
  @FXML private Pane bottomMenuPane;

  // creating icons for buttons
  Image addIcon = new Image(getClass().getResourceAsStream("/Icons/addBlack.png"));
  ImageView addIconView = new ImageView(addIcon);
  Image hospitalIcon = new Image(getClass().getResourceAsStream("/Icons/hospitalBlack.png"));
  ImageView hospitalIconView = new ImageView(hospitalIcon);
  Image parkingIcon = new Image(getClass().getResourceAsStream("/Icons/parkingBlack.png"));
  ImageView parkingIconView = new ImageView(parkingIcon);

  // adding icons to buttons
  private final JFXButton addBtn = new JFXButton(null, addIconView);
  private final JFXButton parkingBtn = new JFXButton(null, parkingIconView);
  private final JFXButton hospitalBtn = new JFXButton(null, hospitalIconView);

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // set the icons sizes
    addIconView.setFitWidth(15);
    addIconView.setFitHeight(15);
    hospitalIconView.setFitWidth(30);
    hospitalIconView.setFitHeight(30);
    parkingIconView.setFitWidth(30);
    parkingIconView.setFitHeight(30);

    // style the buttons
    addBtn.getStyleClass().addAll("nav-menu-button");
    addBtn.setButtonType(JFXButton.ButtonType.RAISED);
    parkingBtn.getStyleClass().addAll("nav-menu-button");
    parkingBtn.setButtonType(JFXButton.ButtonType.RAISED);
    hospitalBtn.getStyleClass().addAll("nav-menu-button");
    hospitalBtn.setButtonType(JFXButton.ButtonType.RAISED);

    // add them to be in an animated node list
    buttonsList.addAnimatedNode(addBtn);
    buttonsList.addAnimatedNode(parkingBtn);
    buttonsList.addAnimatedNode(hospitalBtn);
    buttonsList.setSpacing(20);
    buttonsList.setRotate(180);
    buttonFunction();
  }

  private void buttonFunction() {

    //    parkingBtn.setOnAction(
    //        actionEvent -> {
    //          // save parking spot
    //          SwitchScene.goToParentMobile("/Views/MobileApp/___.fxml", actionEvent);
    //        });

    hospitalBtn.setOnAction(
        actionEvent -> {
          // navigate hospital campus
          SwitchScene.goToParentMobile("/Views/MobileApp/MobileCovidSurvey.fxml", actionEvent);
        });
  }
}
