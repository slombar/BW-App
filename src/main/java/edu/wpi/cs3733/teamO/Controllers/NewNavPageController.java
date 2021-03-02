package edu.wpi.cs3733.teamO.Controllers;

import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import edu.wpi.cs3733.teamO.Database.UserHandling;
import edu.wpi.cs3733.teamO.GraphSystem.Graph;
import edu.wpi.cs3733.teamO.Opp;
import edu.wpi.cs3733.teamO.model.Node;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
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

  // edit map components
  @FXML private VBox editVBox;
  @FXML private JFXTextField nodeID;
  @FXML private JFXTextField xCoord;
  @FXML private JFXTextField yCoord;
  @FXML private JFXTextField floor;
  @FXML private JFXTextField building;
  @FXML private JFXTextField nodeType;
  @FXML private JFXTextField longName;
  @FXML private JFXTextField shortName;
  @FXML private JFXButton editNodeBtn;
  @FXML private JFXButton addNodeBtn;
  @FXML private JFXButton delNodeBtn;
  @FXML private JFXTextField edgeID;
  @FXML private JFXTextField startNodeID;
  @FXML private JFXTextField endNodeID;
  @FXML private JFXButton editEdgeBtn;
  @FXML private JFXButton addEdgeBtn;
  @FXML private JFXButton delEdgeBtn;

  @FXML private JFXDrawer drawer;
  @FXML private JFXHamburger hamburger;
  @FXML private HBox hboxRef;
  @FXML private VBox vboxRef;
  @FXML private RowConstraints row0;
  @FXML private RowConstraints row1;
  @FXML private ColumnConstraints col1;
  @FXML private GridPane gridPane;
  @FXML private JFXDrawer drawerSM1;
  @FXML private VBox topMenu;
  @FXML private JFXDrawer drawerSM;
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
  private String sFloor = "G";
  private String sideMenuUrl;

  private Graph graph;
  boolean selectingStart = true;
  Node startNode = null;
  Node endNode = null;

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
    floorSelectionBtn.setValue("Campus");

    mapCanvas.toFront();
    gc = mapCanvas.getGraphicsContext2D();

    imageView.setImage(campusMap);
    resizableWindow();

    graph = new Graph(gc);

    if (UserHandling.getEmployee()) {
      if (UserHandling.getAdmin()) {
        sideMenuUrl = "/Views/SideMenuAdmin.fxml";
      } else sideMenuUrl = "/Views/SideMenuStaff.fxml";
    } else sideMenuUrl = "/Views/SideMenu.fxml";

    // Set drawer to SideMenu
    try {
      VBox vbox = FXMLLoader.load(getClass().getResource(sideMenuUrl));
      drawer.setSidePane(vbox);
    } catch (IOException e) {
      e.printStackTrace();
    }
    // TODO: change to visible nodes if PATIENT/GUEST
    graph.drawAllNodes("G", startNode, endNode);

    // just for testing

    System.out.println("NewNavPageController Initialized");

    // transition animation of Hamburger icon
    HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
    transition.setRate(-1);

    // click event - mouse click
    hamburger.addEventHandler(
        MouseEvent.MOUSE_PRESSED,
        (e) -> {
          transition.setRate(transition.getRate() * -1);
          transition.play();

          if (drawer.isOpened()) drawer.close(); // this will close slide pane
          else drawer.open(); // this will open slide pane
        });

    if (!editToggle.isSelected()) {
      editVBox.setVisible(false);
    } else {
      editVBox.setVisible(true);
    }
    autocompleteEditMap();
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

    // resizeCanvas();

    return gridPane;
    // 350 / 1920
  }

  /** Resizes Canvas to be the current size of the Image */
  public void resizeCanvas() {
    mapCanvas.heightProperty().setValue(1000);
    mapCanvas.widthProperty().setValue(1000);

    mapCanvas.heightProperty().setValue(imageView.getBoundsInParent().getHeight());
    mapCanvas.widthProperty().setValue(imageView.getBoundsInParent().getWidth());
  }

  public void editMode(ActionEvent actionEvent) {
    if (!editToggle.isSelected()) {
      editVBox.setVisible(false);
    } else {
      editVBox.setVisible(true);
    }
  }

  public void autocompleteEditMap() {
    //    Autocomplete.autoComplete(Autocomplete.autoNodeData("nodeID"), nodeID);
    //    Autocomplete.autoComplete(Autocomplete.autoNodeData("xCoord"), xCoord);
    //    Autocomplete.autoComplete(Autocomplete.autoNodeData("yCoord"), yCoord);
    //    Autocomplete.autoComplete(Autocomplete.autoNodeData("floor"), floor);
    //    Autocomplete.autoComplete(Autocomplete.autoNodeData("building"), building);
    //    Autocomplete.autoComplete(Autocomplete.autoNodeData("nodeType"), nodeType);
    //    Autocomplete.autoComplete(Autocomplete.autoNodeData("longName"), longName);
    //    Autocomplete.autoComplete(Autocomplete.autoNodeData("shortName"), shortName);
  }

  public void goToMain(ActionEvent actionEvent) {
    try {
      BorderPane root = FXMLLoader.load(getClass().getResource("/Views/MainPage.fxml"));
      Opp.getPrimaryStage().getScene().setRoot(root);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public void floorSelection(ActionEvent actionEvent) {
    selectedFloor = floorSelectionBtn.getValue();
    // System.out.println(floorSelected);

    // switch case basically = if, else if, etc...
    switch (selectedFloor) {
      case "Campus":
        imageView.setImage(campusMap);
        sFloor = "G";
        break;
      case "Floor 1":
        imageView.setImage(floor1Map);
        sFloor = "1";
        break;
      case "Floor 2":
        imageView.setImage(floor2Map);
        sFloor = "2";
        break;
      case "Floor 3":
        imageView.setImage(floor3Map);
        sFloor = "3";
        break;
      case "Floor 4":
        imageView.setImage(floor4Map);
        sFloor = "4";
        break;
      case "Floor 5":
        imageView.setImage(floor5Map);
        sFloor = "5";
        break;
    }

    resizeCanvas();
    // TODO: only draw visible if patient/guest
    graph.drawAllNodes(sFloor, startNode, endNode);
  }

  public void doPathfind(ActionEvent actionEvent) {
    List<Node> path = graph.findPath(startNode, endNode);

    for (int i = 0; i < path.size() - 1; i++) {
      Node nodeA = path.get(i);
      Node nodeB = path.get(i + 1);

      graph.drawMidArrow(nodeA, nodeB);
      // DrawHelper.drawMidArrow(
      //   gc, nodeA.getXCoord(), nodeA.getYCoord(), nodeB.getXCoord(), nodeB.getYCoord());
    }
  }

  public void goToSideMenu(MouseEvent mouseEvent) {}

  public void canvasClick(MouseEvent mouseEvent) {
    Node clickedNode = Graph.closestNode(sFloor, mouseEvent.getX(), mouseEvent.getY());

    if (editToggle.isSelected()) {
      nodeID.setText(clickedNode.getID());
      xCoord.setText(String.valueOf(clickedNode.getXCoord()));
      yCoord.setText(String.valueOf(clickedNode.getYCoord()));
      floor.setText(clickedNode.getFloor());
      building.setText(clickedNode.getBuilding());
      nodeType.setText(clickedNode.getNodeType());
      longName.setText(clickedNode.getLongName());
      shortName.setText(clickedNode.getShortName());
    } else {
      if (selectingStart) {
        startNode = clickedNode;
      } else {
        endNode = clickedNode;
      }

      graph.drawAllNodes(sFloor, startNode, endNode);
      System.out.println("Click");
    }
  }

  // TODO: set start/end to different colors
  public void startLocSelection(ActionEvent actionEvent) {
    selectingStart = true;
  }

  public void endLocSelection(ActionEvent actionEvent) {
    selectingStart = false;
  }
}
