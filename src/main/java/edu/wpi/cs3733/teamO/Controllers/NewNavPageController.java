package edu.wpi.cs3733.teamO.Controllers;

import com.jfoenix.controls.*;
import edu.wpi.cs3733.teamO.GraphSystem.Graph;
import edu.wpi.cs3733.teamO.Opp;
import java.awt.*;
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
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

public class NewNavPageController implements Initializable {

  @FXML private HBox hboxRef;
  @FXML private VBox vboxRef;
  @FXML private RowConstraints row0;
  @FXML private RowConstraints row1;
  @FXML private ColumnConstraints col1;
  @FXML private GridPane gridPane;
  @FXML private JFXDrawer drawerSM1;
  @FXML private JFXHamburger hamburgerMainBtn1;
  @FXML private VBox topMenu;
  @FXML private JFXDrawer drawerSM;
  @FXML private JFXHamburger hamburgerMainBtn;
  @FXML private BorderPane borderPane;
  @FXML private JFXToggleButton editToggle;

  @FXML private JFXComboBox<String> floorSelectionBtn;
  @FXML private JFXButton startLocBtn;
  @FXML private JFXButton endLocBtn;
  @FXML private JFXButton pathfindBtn;

  @FXML private ImageView imageView;
  @FXML private Canvas mapCanvas;
  private GraphicsContext gc;
  private String selectedFloor = "Campus";

  private Graph graph;

  ObservableList<String> listOfFloors =
      FXCollections.observableArrayList(
          "Campus", "Floor 1", "Floor 2", "Floor 3", "Floor 4", "Floor 5");

  public static Image campusMap = new Image("FaulknerCampus_Updated.png");
  public static Image floor1Map = new Image("Faulkner1_Updated.png");
  public static Image floor2Map = new Image("Faulkner2_Updated.png");
  public static Image floor3Map = new Image("Faulkner3_Updated.png");
  public static Image floor4Map = new Image("Faulkner4_Updated.png");
  public static Image floor5Map = new Image("Faulkner5_Updated.png");

  ///// Methods: /////

  public NewNavPageController() {}

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    floorSelectionBtn.setItems(listOfFloors);

    resizableMap();

    mapCanvas.toBack();
    gc = mapCanvas.getGraphicsContext2D();

    graph = new Graph(gc);

    // TODO: change to visible nodes if PATIENT/GUEST
    graph.drawAllNodes("G");

    // just for testing
    System.out.println("NewNavPageController Initialized");
  }

  public GridPane resizableMap() {
    imageView.setPreserveRatio(true);
    imageView
        .fitHeightProperty()
        .bind(Opp.getPrimaryStage().getScene().heightProperty().subtract(vboxRef.heightProperty()));
    imageView.fitWidthProperty().bind(hboxRef.widthProperty());
    return gridPane;
  }

  /**
   * Creates a resizable GridPane with map image, menu buttons, etc.
   *
   * @return GridPane
   */
  public GridPane resizableWindow() {
    imageView.setPreserveRatio(true);
    imageView
        .fitHeightProperty()
        .bind(Opp.getPrimaryStage().getScene().heightProperty().subtract(vboxRef.heightProperty()));
    imageView.fitWidthProperty().bind(hboxRef.widthProperty());

    mapCanvas.heightProperty().setValue(imageView.getBoundsInParent().getHeight());
    mapCanvas.widthProperty().setValue(imageView.getBoundsInParent().getWidth());
    return gridPane;
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

  public void floorSelection(ActionEvent actionEvent) {
    mapCanvas.toFront();
    selectedFloor = floorSelectionBtn.getValue();
    String floor = "";
    // System.out.println(floorSelected);

    // switch case basically = if, else if, etc...
    switch (selectedFloor) {
      case "Campus":
        imageView.setImage(campusMap);
        floor = "G";
        resizableWindow();
        break;
      case "Floor 1":
        imageView.setImage(floor1Map);
        floor = "1";
        resizableWindow();
        break;
      case "Floor 2":
        imageView.setImage(floor2Map);
        floor = "2";
        resizableWindow();
        break;
      case "Floor 3":
        imageView.setImage(floor3Map);
        floor = "3";
        resizableWindow();
        break;
      case "Floor 4":
        imageView.setImage(floor4Map);
        floor = "4";
        resizableWindow();
        break;
      case "Floor 5":
        imageView.setImage(floor5Map);
        floor = "5";
        resizableWindow();
        break;
    }

    // TODO: only draw visible if patient/guest
    graph.drawAllNodes(floor);
  }

  public void endLocSelection(ActionEvent actionEvent) {}

  public void doPathfind(ActionEvent actionEvent) {}

  public void goToSideMenu(MouseEvent mouseEvent) {}

  public void canvasClick(MouseEvent mouseEvent) {
    System.out.println("Click");
  }

  public void startLocSelection(ActionEvent actionEvent) {}
}
