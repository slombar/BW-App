package edu.wpi.cs3733.teamO.Controllers.Mobile;

import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsStep;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXNodesList;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.teamO.HelperClasses.PopupMaker;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import edu.wpi.cs3733.teamO.Maps.Directions;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

public class MobileGoogleNavController implements Initializable {
  @FXML private StackPane popupPane;
  @FXML private WebView mapView;
  @FXML private JFXNodesList buttonsList;
  @FXML private JFXNodesList directionsList;

  private static ArrayList<DirectionsStep> directions;
  private static String directionsURL;

  // creating icons for buttons
  Image addIcon = new Image(getClass().getResourceAsStream("/Icons/addBlack.png"));
  ImageView addIconView = new ImageView(addIcon);
  Image hospitalIcon = new Image(getClass().getResourceAsStream("/Icons/hospitalBlack.png"));
  ImageView hospitalIconView = new ImageView(hospitalIcon);
  Image exitIcon = new Image(getClass().getResourceAsStream("/Icons/exitBlack.png"));
  ImageView exitIconView = new ImageView(exitIcon);
  Image navIcon = new Image(getClass().getResourceAsStream("/Icons/navIconBlack.png"));
  ImageView navIconView = new ImageView(navIcon);
  Image startIcon = new Image(getClass().getResourceAsStream("/Icons/arrowIconBlack.png"));
  ImageView startIconView = new ImageView(startIcon);
  Image textIcon = new Image(getClass().getResourceAsStream("/Icons/bookIconBlack.png"));
  ImageView textIconView = new ImageView(textIcon);

  // adding icons to buttons
  private final JFXButton addBtn = new JFXButton(null, addIconView);
  private final JFXButton exitBtn = new JFXButton("Exit Navigation", exitIconView);
  private final JFXButton hospitalBtn = new JFXButton("Hospital Navigation", hospitalIconView);
  private final JFXButton directionsBtn = new JFXButton(null, navIconView);
  private final JFXButton startBtn = new JFXButton("Start Navigation", startIconView);
  private final JFXButton textBtn = new JFXButton("Text Directions", textIconView);

  // components for location textfields
  JFXTextField startLoc = new JFXTextField();
  JFXTextField endLoc = new JFXTextField();
  VBox locBox = new VBox();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // set the icons sizes
    addIconView.setFitWidth(15);
    addIconView.setFitHeight(15);
    hospitalIconView.setFitWidth(25);
    hospitalIconView.setFitHeight(25);
    exitIconView.setFitWidth(20);
    exitIconView.setFitHeight(20);
    navIconView.setFitWidth(15);
    navIconView.setFitHeight(15);
    startIconView.setFitWidth(15);
    startIconView.setFitHeight(15);
    textIconView.setFitWidth(25);
    textIconView.setFitHeight(25);

    // set up for location selection
    startLoc.setPromptText("Start Location");
    endLoc.setPromptText("Destination");
    locBox.getChildren().addAll(startLoc, endLoc);
    locBox.setPadding(new Insets(5, 15, 15, 15));

    // style the buttons
    addBtn.getStyleClass().addAll("nav-menu-button");
    addBtn.setButtonType(JFXButton.ButtonType.RAISED);
    exitBtn.getStyleClass().addAll("nav-buttons");
    exitBtn.setButtonType(JFXButton.ButtonType.RAISED);
    hospitalBtn.getStyleClass().addAll("nav-buttons");
    hospitalBtn.setButtonType(JFXButton.ButtonType.RAISED);
    directionsBtn.getStyleClass().addAll("nav-menu-button");
    directionsBtn.setButtonType(JFXButton.ButtonType.RAISED);
    textBtn.getStyleClass().addAll("nav-buttons");
    textBtn.setButtonType(JFXButton.ButtonType.RAISED);
    startBtn.getStyleClass().addAll("nav-buttons");
    locBox.getStyleClass().addAll("nav-text");

    // add buttons to bottom right animated node list (additional buttons)
    buttonsList.addAnimatedNode(addBtn);
    buttonsList.addAnimatedNode(textBtn);
    buttonsList.addAnimatedNode(hospitalBtn);
    buttonsList.addAnimatedNode(exitBtn);
    buttonsList.setSpacing(20);
    buttonsList.setRotate(180);
    buttonsList.setAlignment(Pos.CENTER_RIGHT);

    // add buttons to top left animated node list (directions)
    directionsList.addAnimatedNode(directionsBtn);
    directionsList.addAnimatedNode(locBox);
    directionsList.addAnimatedNode(startBtn);
    directionsList.setSpacing(10);
    directionsList.setRotate(0);
    directionsList.setAlignment(Pos.CENTER_RIGHT);
    buttonFunction();

    // load online google maps
    mapView.getEngine().load("https://www.google.com/maps/@?api=1&map_action=map");

    buttonsList.toFront();
    directionsList.toFront();
  }

  /** adding on action functionality to the buttons in the JFXNodeslist */
  private void buttonFunction() {
    hospitalBtn.setOnAction(
        actionEvent -> {
          // navigate hospital campus
          SwitchScene.goToParentMobile("/Views/MobileApp/MobileCovidSurvey.fxml", actionEvent);
        });

    textBtn.setOnAction(
        actionEvent -> {
          // page of just text directions
          MainScreenController.isBackGoogle = true;
          SwitchScene.goToParentMobile("/Views/MobileApp/MobileDirections.fxml", actionEvent);
        });

    exitBtn.setOnAction(
        // exits to main phone screen page
        actionEvent -> {
          SwitchScene.goToParentMobile("/Views/MobileApp/MainScreen.fxml", actionEvent);
        });

    startBtn.setOnAction(
        // exits to main phone screen page
        actionEvent -> {
          displayRoute(startLoc.getText(), endLoc.getText());
        });
  }

  /**
   * displays the route on the web view based on from adn to text boxes
   *
   * @param fromLocation
   * @param toLocation
   */
  private void displayRoute(String fromLocation, String toLocation) {
    try {
      directions = Directions.getDirections(fromLocation, toLocation);
    } catch (ApiException | IOException | InterruptedException e) {
      PopupMaker.invalidLocationMobile(popupPane);
    }

    directionsURL =
        "https://www.google.com/maps/dir/?api=1&origin="
            + Directions.urlForm(fromLocation)
            + "&destination="
            + Directions.urlForm(toLocation);
    mapView.getEngine().load(directionsURL);
  }

  public static String getDirectionsURL() {
    return directionsURL;
  }

  public static ArrayList<DirectionsStep> getDirections() {
    return directions;
  }
}
