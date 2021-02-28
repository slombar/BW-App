package edu.wpi.cs3733.teamO.Controllers;

import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import edu.wpi.cs3733.teamO.Opp;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

public class NewNavPageController implements Initializable {

  @FXML private JFXDrawer drawer;
  @FXML private JFXHamburger hamburger;
  @FXML private HBox hboxRef;
  @FXML private VBox vboxRef;
  @FXML private RowConstraints row0;
  @FXML private RowConstraints row1;
  @FXML private ColumnConstraints col1;
  @FXML private GridPane gridPane;
  @FXML private JFXDrawer drawerSM1;
  @FXML private JFXHamburger hamburgerMainBtn1;
  @FXML private AnchorPane anchorPane;
  @FXML private Canvas canvas;
  @FXML private VBox topMenu;
  @FXML private JFXDrawer drawerSM;
  @FXML private JFXHamburger hamburgerMainBtn;
  @FXML private BorderPane borderPane;
  @FXML private JFXToggleButton editToggle;
  @FXML private ImageView imageView;
  @FXML private JFXComboBox<String> floorSelectionBtn;
  @FXML private JFXButton startLocBtn;
  @FXML private JFXButton endLocBtn;
  @FXML private JFXButton pathfindBtn;

  ObservableList<String> listOfFloors =
      FXCollections.observableArrayList("Ground", "Floor 2", "Floor 3", "Floor 4", "Floor 5");

  public NewNavPageController() {}

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    floorSelectionBtn.setItems(listOfFloors);
    resizableWindow();
    canvas.toFront();

    // Set drawer to SideMenu
    try {
      VBox vbox = FXMLLoader.load(getClass().getResource("/Views/SideMenu.fxml"));
      drawer.setSidePane(vbox);
    } catch (IOException e) {
      e.printStackTrace();
    }

    // transition animation of Hamburger icon
    HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);

    // click event - mouse click
    hamburger.addEventHandler(
        MouseEvent.MOUSE_PRESSED,
        (e) -> {
          transition.setRate(transition.getRate() * -1);
          transition.play();

          if (drawer.isOpened()) drawer.close(); // this will close slide pane
          else drawer.open(); // this will open slide pane
        });
  }

  /**
   * Create a resizable navigation map with editing features available for admin
   *
   * @return grid pane
   */
  public GridPane resizableWindow() {
    imageView.setPreserveRatio(true);
    imageView
        .fitHeightProperty()
        .bind(Opp.getPrimaryStage().getScene().heightProperty().subtract(vboxRef.heightProperty()));
    imageView.fitWidthProperty().bind(hboxRef.widthProperty());

    canvas.heightProperty().bind(imageView.fitHeightProperty());
    canvas.widthProperty().bind(imageView.fitWidthProperty());

    return gridPane;
    // 350 / 1920
  }

  public void editMode(ActionEvent actionEvent) {}

  public void goToMain(ActionEvent actionEvent) {
    try {
      BorderPane root = FXMLLoader.load(getClass().getResource("/Views/MainPage.fxml"));
      Opp.getPrimaryStage().getScene().setRoot(root);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public void floorSelection(ActionEvent actionEvent) {}

  public void endLocSelection(ActionEvent actionEvent) {}

  public void doPathfind(ActionEvent actionEvent) {}

  public void goToSideMenu(MouseEvent mouseEvent) {}

  public void canvasClick(MouseEvent mouseEvent) {
    System.out.println("Click");
  }

  public void startLocSlection(ActionEvent actionEvent) {}
}
