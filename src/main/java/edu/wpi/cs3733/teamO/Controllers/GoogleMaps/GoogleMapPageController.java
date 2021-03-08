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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GoogleMapPageController implements Initializable {

  @FXML private JFXTextField toBox;
  @FXML private StackPane popupPane;
  @FXML private JFXDrawer drawer;
  @FXML private JFXHamburger hamburger;
  @FXML private JFXScrollPane scrollPane;
  @FXML private JFXTextField fromBox;

  VBox dirVbox = new VBox();

  ArrayList<DirectionsStep> directions;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    scrollPane.autosize();
    Text title = new Text("Directions");
    title.setFont(Font.font("leelawadee ui", 24.0));
    scrollPane.getTopBar().getChildren().add(title);
    Color darkBlue = new Color(13, 51, 166);
    scrollPane
        .getTopBar()
        .setStyle("-fx-background-color:" + darkBlue + "; -fx-fill:" + darkBlue + ";"); //TODO dont know how this works
  }

  public void goToMainMenu(MouseEvent mouseEvent) {
    SwitchScene.goToParent("/Views/MainPage.fxml");
  }

  public void checkEnter(KeyEvent keyEvent) {
    if (keyEvent.getCode() == KeyCode.ENTER) {
      displayTextRoute(
          fromBox.getText(), toBox.getText()); // TODO maybe disable text boxes for a second??
    }
  }

  public void shareGoogleRoute(ActionEvent actionEvent) {} // TODO this too!!

  private void displayTextRoute(String fromLocation, String toLocation) {
    try {
      directions = Directions.getDirections(fromLocation, toLocation);
    } catch (ApiException ignored) {
    } catch (InterruptedException ignored) { // TODO figure out what these should do
    } catch (IOException ignored) {
    }

    dirVbox.getChildren().clear();

    scrollPane.setContent(dirVbox);

    for (int i = 0; i < directions.size(); i++) {
      // System.out.println("Step " + i + ": " +
      // Directions.html2text(directions.get(i).htmlInstructions));
      addTextToDirectionBox(directions.get(i).htmlInstructions);
    }
    scrollPane.setContent(dirVbox);
  }

  private void addTextToDirectionBox(String text) {
    Text newText = new Text(Directions.html2text(text) + "\n");
    newText.setFont(Font.font("leelawadee ui", 16.0));
    dirVbox.getChildren().add(newText);
  }
}
