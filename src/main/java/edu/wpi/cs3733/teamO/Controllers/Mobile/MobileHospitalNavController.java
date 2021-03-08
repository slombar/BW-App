package edu.wpi.cs3733.teamO.Controllers.Mobile;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXNodesList;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MobileHospitalNavController implements Initializable {
  @FXML private JFXNodesList directionsList;
  @FXML private JFXNodesList buttonsList;

  // creating icons for buttons
  Image addIcon = new Image(getClass().getResourceAsStream("/Icons/addBlack.png"));
  ImageView addIconView = new ImageView(addIcon);
  Image hospitalIcon = new Image(getClass().getResourceAsStream("/Icons/hospitalBlack.png"));
  ImageView hospitalIconView = new ImageView(hospitalIcon);
  Image parkingIcon = new Image(getClass().getResourceAsStream("/Icons/parkingBlack.png"));
  ImageView parkingIconView = new ImageView(parkingIcon);
  Image exitIcon = new Image(getClass().getResourceAsStream("/Icons/exitBlack.png"));
  ImageView exitIconView = new ImageView(exitIcon);
  Image navIcon = new Image(getClass().getResourceAsStream("/Icons/navIconBlack.png"));
  ImageView navIconView = new ImageView(navIcon);
  Image startIcon = new Image(getClass().getResourceAsStream("/Icons/arrowIconBlack.png"));
  ImageView startIconView = new ImageView(startIcon);

  // adding icons to buttons
  private final JFXButton addBtn = new JFXButton(null, addIconView);
  private final JFXButton parkingBtn = new JFXButton("Save Parking Spot", parkingIconView);
  private final JFXButton hospitalBtn = new JFXButton("Hospital Navigation", hospitalIconView);
  private final JFXButton exitBtn = new JFXButton("Exit Navigation", exitIconView);
  private final JFXButton directionsBtn = new JFXButton(null, navIconView);
  private final JFXButton startBtn = new JFXButton("Start Navigation", startIconView);

  JFXTextField startLoc = new JFXTextField();
  JFXTextField endLoc = new JFXTextField();

  HBox hbox = new HBox();
  VBox locBox = new VBox();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // set the icons sizes
    addIconView.setFitWidth(15);
    addIconView.setFitHeight(15);
    hospitalIconView.setFitWidth(25);
    hospitalIconView.setFitHeight(25);
    parkingIconView.setFitWidth(25);
    parkingIconView.setFitHeight(25);
    exitIconView.setFitWidth(20);
    exitIconView.setFitHeight(20);
    navIconView.setFitWidth(15);
    navIconView.setFitHeight(15);
    startIconView.setFitWidth(15);
    startIconView.setFitHeight(15);

    // set prompt text
    startLoc.setPromptText("Start Location");
    endLoc.setPromptText("Destination");
    locBox.getChildren().addAll(startLoc, endLoc);
    locBox.setPadding(new Insets(5, 15, 15, 15));

    // style the buttons
    addBtn.getStyleClass().addAll("nav-menu-button");
    addBtn.setButtonType(JFXButton.ButtonType.RAISED);
    parkingBtn.getStyleClass().addAll("nav-buttons");
    parkingBtn.setButtonType(JFXButton.ButtonType.RAISED);
    hospitalBtn.getStyleClass().addAll("nav-buttons");
    hospitalBtn.setButtonType(JFXButton.ButtonType.RAISED);
    exitBtn.getStyleClass().addAll("nav-buttons");
    exitBtn.setButtonType(JFXButton.ButtonType.RAISED);
    directionsBtn.getStyleClass().addAll("nav-menu-button");
    directionsBtn.setButtonType(JFXButton.ButtonType.RAISED);
    startBtn.getStyleClass().addAll("nav-buttons");
    locBox.getStyleClass().addAll("nav-text");

    // add them to be in an animated node list
    buttonsList.addAnimatedNode(addBtn);
    buttonsList.addAnimatedNode(parkingBtn);
    buttonsList.addAnimatedNode(hospitalBtn);
    buttonsList.addAnimatedNode(exitBtn);
    buttonsList.setSpacing(20);
    buttonsList.setRotate(180);
    buttonsList.setAlignment(Pos.CENTER_RIGHT);

    directionsList.addAnimatedNode(directionsBtn);
    directionsList.addAnimatedNode(locBox);
    directionsList.addAnimatedNode(startBtn);
    directionsList.setSpacing(10);
    directionsList.setRotate(0);
    directionsList.setAlignment(Pos.CENTER_RIGHT);
    buttonFunction();
  }

  /** adding on action functionality to the buttons in the JFXNodeslist */
  private void buttonFunction() {
    parkingBtn.setOnAction(
        actionEvent -> {
          // TODO: save parking spot
        });

    hospitalBtn.setOnAction(
        actionEvent -> {
          // TODO: navigate hospital campus, maybe another nodes list with drop downs?
        });

    exitBtn.setOnAction(
        actionEvent -> {
          SwitchScene.goToParentMobile("/Views/MobileApp/MainScreen.fxml", actionEvent);
        });
  }
}
