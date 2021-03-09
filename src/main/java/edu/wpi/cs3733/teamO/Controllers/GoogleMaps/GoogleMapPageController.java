package edu.wpi.cs3733.teamO.Controllers.GoogleMaps;

import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsStep;
import com.jfoenix.controls.*;
import edu.wpi.cs3733.teamO.Controllers.Maps.Directions;
import edu.wpi.cs3733.teamO.Database.UserHandling;
import edu.wpi.cs3733.teamO.HelperClasses.PopupMaker;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;

public class GoogleMapPageController implements Initializable {

  @FXML private ScrollPane scrollPane;
  @FXML private WebView mapView;
  @FXML private JFXButton shareBtn;
  @FXML private JFXTextField toBox;
  @FXML private StackPane popupPane;
  @FXML private JFXDrawer drawer;
  @FXML private JFXHamburger hamburger;
  @FXML private JFXTextField fromBox;
  @FXML private VBox dirVbox;

  private static ArrayList<DirectionsStep> directions;
  Text title;
  private static String directionsURL;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    shareBtn.setDisable(true);
    title = new Text("Directions\n");
    title.setFont(Font.font("leelawadee ui", 24.0));
    dirVbox.getChildren().add(title);
    JFXScrollPane.smoothScrolling(scrollPane);
    mapView.getEngine().load("https://www.google.com/maps/@?api=1&map_action=map");
  }

  public void goToMainMenu(MouseEvent mouseEvent) {
    String MenuUrl = "/Views/MainPage.fxml";
    if (UserHandling.getEmployee() || UserHandling.getAdmin())
      MenuUrl = "/Views/StaffMainPage.fxml";
    SwitchScene.goToParent(MenuUrl);
  }

  public void checkEnter(KeyEvent keyEvent) {
    if (keyEvent.getCode() == KeyCode.ENTER) {
      displayRoute(fromBox.getText(), toBox.getText());
    }
  }

  public void shareGoogleRoute(ActionEvent actionEvent) {
    SwitchScene.goToParent("/Views/GoogleMaps/ShareGoogleMapPath.fxml");
  }

  private void displayRoute(String fromLocation, String toLocation) {
    try {
      directions = Directions.getDirections(fromLocation, toLocation);
    } catch (ApiException | IOException | InterruptedException e) {
      PopupMaker.invalidLocationA(popupPane);
    }

    dirVbox.getChildren().clear();
    dirVbox.getChildren().add(title);

    for (DirectionsStep direction : directions) {
      addTextToDirectionBox(direction.htmlInstructions);
    }

    directionsURL =
        "https://www.google.com/maps/dir/?api=1&origin="
            + Directions.urlForm(fromLocation)
            + "&destination="
            + Directions.urlForm(toLocation);
    mapView.getEngine().load(directionsURL);

    if (directionsURL != null) {
      shareBtn.setDisable(false);
    }
  }

  private void addTextToDirectionBox(String text) {
    Text newText = new Text(Directions.html2text(text) + "\n");
    newText.setFont(Font.font("leelawadee ui", 16.0));
    dirVbox.getChildren().add(newText);
  }

  public static String getDirectionsURL() {
    return directionsURL;
  }

  public static ArrayList<DirectionsStep> getDirections() {
    return directions;
  }
}
