package edu.wpi.cs3733.teamO.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXToggleButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class NewNavPageController implements Initializable {

  @FXML private BorderPane borderPane;
  @FXML private JFXToggleButton editToggle;
  @FXML private ImageView campusMap;
  @FXML private JFXComboBox<String> floorSelectionBtn;
  @FXML private JFXButton startLocBtn;
  @FXML private JFXButton endLocBtn;
  @FXML private JFXButton pathfindBtn;

  ObservableList<String> listOfFloors =
      FXCollections.observableArrayList("Ground", "Floor 2", "Floor 3", "Floor 4", "Floor 5");

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    floorSelectionBtn.setItems(listOfFloors);
    resizableWindow();
  }

  /**
   * Create a resizable navigation map with editing features available for admin
   *
   * @return border pane
   */
  public BorderPane resizableWindow() {
    borderPane.setPadding(new Insets(5));

    campusMap.fitWidthProperty().bind(borderPane.widthProperty());
    campusMap.fitHeightProperty().bind(borderPane.heightProperty());
    BorderPane.setAlignment(campusMap, Pos.TOP_CENTER);
    borderPane.setCenter(campusMap);

    return borderPane;
  }

  public void editMode(ActionEvent actionEvent) {}

  public void goToMain(ActionEvent actionEvent) {}

  public void floorSelection(ActionEvent actionEvent) {}

  public void startLocSlection(ActionEvent actionEvent) {}

  public void endLocSelection(ActionEvent actionEvent) {}

  public void doPathfind(ActionEvent actionEvent) {}
}
