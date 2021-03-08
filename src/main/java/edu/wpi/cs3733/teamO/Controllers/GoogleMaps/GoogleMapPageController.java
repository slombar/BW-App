package edu.wpi.cs3733.teamO.Controllers.GoogleMaps;

import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsStep;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import edu.wpi.cs3733.teamO.Maps.Directions;
import java.awt.*;
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

  public ScrollPane scrollPane;
  public WebView mapView;

  @FXML private JFXTextField toBox;
  @FXML private StackPane popupPane;
  @FXML private JFXDrawer drawer;
  @FXML private JFXHamburger hamburger;
  @FXML private JFXTextField fromBox;
  @FXML private VBox dirVbox;

  ArrayList<DirectionsStep> directions;
  Text title;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    title = new Text("Directions\n");
    title.setFont(Font.font("leelawadee ui", 24.0));
    dirVbox.getChildren().add(title);
    JFXScrollPane.smoothScrolling(scrollPane);
    mapView.getEngine().load("https://www.google.com/maps/@?api=1&map_action=map");
  }

  public void goToMainMenu(MouseEvent mouseEvent) {
    SwitchScene.goToParent("/Views/MainPage.fxml");
  }

  public void checkEnter(KeyEvent keyEvent) {
    if (keyEvent.getCode() == KeyCode.ENTER) {
      toBox.setDisable(true);
      fromBox.setDisable(true);
      displayTextRoute(fromBox.getText(), toBox.getText());
    }
  }

  public void shareGoogleRoute(ActionEvent actionEvent) {} // TODO this too!!

  private void displayTextRoute(String fromLocation, String toLocation) {
    dirVbox.maxWidth(45);
    try {
      directions = Directions.getDirections(fromLocation, toLocation);
    } catch (ApiException ignored) {
    } catch (InterruptedException ignored) { // TODO figure out what these should do
    } catch (IOException ignored) {
    }

    dirVbox.getChildren().clear();
    dirVbox.getChildren().add(title);

    toBox.setDisable(false);
    fromBox.setDisable(false);

    for (DirectionsStep direction : directions) {
      addTextToDirectionBox(direction.htmlInstructions);
    }
    mapView
        .getEngine()
        .load(
            "https://www.google.com/maps/dir/?api=1&origin="
                + Directions.urlForm(fromLocation)
                + "&destination="
                + Directions.urlForm(toLocation));
  }

  private void addTextToDirectionBox(String text) {
    Text newText = new Text(Directions.html2text(text) + "\n");
    newText.setFont(Font.font("leelawadee ui", 16.0));
    dirVbox.getChildren().add(newText);
  }
}
