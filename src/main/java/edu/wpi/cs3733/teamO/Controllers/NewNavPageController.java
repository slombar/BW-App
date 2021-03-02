package edu.wpi.cs3733.teamO.Controllers;

import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import edu.wpi.cs3733.teamO.Database.DataHandling;
import edu.wpi.cs3733.teamO.Database.NodesAndEdges;
import edu.wpi.cs3733.teamO.Database.UserHandling;
import edu.wpi.cs3733.teamO.GraphSystem.Graph;
import edu.wpi.cs3733.teamO.HelperClasses.Autocomplete;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import edu.wpi.cs3733.teamO.Opp;
import edu.wpi.cs3733.teamO.model.Node;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javax.imageio.ImageIO;

public class NewNavPageController implements Initializable {

  // edit map components
  @FXML private GridPane innerGrid;
  @FXML private JFXButton uploadCSVBtn;
  @FXML private JFXButton saveCSVBtn;
  @FXML private JFXButton saveEBtn;
  @FXML private JFXButton saveNBtn;
  @FXML private JFXButton uploadEBtn;
  @FXML private JFXButton uploadNBtn;
  @FXML private JFXButton share;
  @FXML private JFXButton clearBtn1;
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
  @FXML private JFXToggleButton showEdgesToggle;
  private boolean addNodeMode;
  private boolean addNodeDB;

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
  private boolean displayingRoute = false;
  boolean navigating = true;

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
    editToggle.setVisible(false);

    if (UserHandling.getEmployee()) {
      System.out.println("EMPLOYEE");
      sideMenuUrl = "/Views/SideMenuStaff.fxml";
      if (UserHandling.getAdmin()) {
        sideMenuUrl = "/Views/SideMenuAdmin.fxml";
        System.out.println("ADMIN");
        editToggle.setVisible(true);
      }
    } else {
      sideMenuUrl = "/Views/SideMenu.fxml";
    }

    // Set drawer to SideMenu
    try {
      VBox vbox = FXMLLoader.load(getClass().getResource(sideMenuUrl));
      drawer.setSidePane(vbox);
    } catch (IOException e) {
      e.printStackTrace();
    }

    // TODO: change to visible nodes if PATIENT/GUEST
    if (navigating) {
      graph.drawVisibleNodes("G", startNode, endNode);
    } else {
      graph.drawAllNodes("G", startNode, endNode);
      if (showEdgesToggle.isSelected()) graph.drawAllEdges("G");
    }

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
    addNodeMode = false;
    addNodeDB = false;
    // autocompletes the node Id for start and end
    Autocomplete.autoComplete(Autocomplete.autoNodeData("nodeID"), startNodeID);
    Autocomplete.autoComplete(Autocomplete.autoNodeData("nodeID"), endNodeID);
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

  public void autocompleteEditMap(Node clickedNode) {
    nodeID.setText(clickedNode.getID());
    xCoord.setText(Integer.toString(clickedNode.getXCoord()));
    yCoord.setText(Integer.toString(clickedNode.getYCoord()));
    floor.setText(clickedNode.getFloor());
    building.setText(clickedNode.getBuilding());
    nodeType.setText(clickedNode.getNodeType());
    longName.setText(clickedNode.getLongName());
    shortName.setText(clickedNode.getShortName());
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
    if (displayingRoute) {
      graph.drawCurrentPath(sFloor, startNode, endNode);
    } else {
      if (navigating) {
        graph.drawVisibleNodes(sFloor, startNode, endNode);
      } else {
        graph.drawAllNodes(sFloor, startNode, endNode);
        if (showEdgesToggle.isSelected()) graph.drawAllEdges(sFloor);
      }
    }
  }

  public void doPathfind(ActionEvent actionEvent) {
    if (startNode != null && endNode != null) {
      graph.resetPath();
      graph.findPath(startNode, endNode);
      graph.drawCurrentPath(sFloor, startNode, endNode);
      displayingRoute = true;
    }
    // TODO: else -> throw exception? or make popup or something? idk
  }

  public void goToSideMenu(MouseEvent mouseEvent) {}

  public void canvasClick(MouseEvent mouseEvent) {
    // displayingRoute = false;
    Node clickedNode = Graph.closestNode(sFloor, mouseEvent.getX(), mouseEvent.getY());

    if (addNodeMode) {
      Node n = new Node();
      n.setXCoord((int) mouseEvent.getX());
      n.setYCoord((int) mouseEvent.getY());

      autocompleteEditMap(n);

      addNodeMode = false;
    } else if (editToggle.isSelected()) {
      autocompleteEditMap(clickedNode);
    } else {
      if (selectingStart) {
        startNode = clickedNode;
      } else {
        endNode = clickedNode;
      }

      if (navigating) {
        graph.drawVisibleNodes(sFloor, startNode, endNode);
      } else {
        graph.drawAllNodes(sFloor, startNode, endNode);
        if (showEdgesToggle.isSelected()) graph.drawAllEdges(sFloor);
      }
    }
    System.out.println("Click");
  }

  // TODO: set start/end to different colors
  public void startLocSelection(ActionEvent actionEvent) {
    selectingStart = true;
  }

  public void endLocSelection(ActionEvent actionEvent) {
    selectingStart = false;
  }

  // TODO: reset button??? (needs to set startNode and endNode to null)
  public void toSharePage(ActionEvent actionEvent) throws IOException {

    // sharePane.toBack();
    GraphicsContext gc = mapCanvas.getGraphicsContext2D();

    mapCanvas.getGraphicsContext2D();
    String home = System.getProperty("user.home");
    File outputFile1 = new File(home + "/Downloads/" + "mapimg1.png");
    File outputFile2 = new File(home + "/Downloads/" + "mapimg2.png");
    File outputFile3 = new File(home + "/Downloads/" + "mapimg3.png");
    File outputFile4 = new File(home + "/Downloads/" + "mapimg4.png");
    File outputFile5 = new File(home + "/Downloads/" + "mapimg5.png");
    File outputFile6 = new File(home + "/Downloads/" + "mapimg6.png");

    imageView.setImage(campusMap);
    sFloor = "G";
    resizeCanvas();
    if (displayingRoute) {
      graph.drawCurrentPath(sFloor, startNode, endNode);
    } else {
      if (navigating) {
        graph.drawVisibleNodes(sFloor, startNode, endNode);
      } else {
        graph.drawAllNodes(sFloor, startNode, endNode);
        if (showEdgesToggle.isSelected()) graph.drawAllEdges(sFloor);
      }
    }
    WritableImage map1 = innerGrid.snapshot(new SnapshotParameters(), null);
    ImageIO.write(SwingFXUtils.fromFXImage(map1, null), "png", outputFile1);
    imageView.setImage(floor1Map);
    sFloor = "1";
    resizeCanvas();
    if (displayingRoute) {
      graph.drawCurrentPath(sFloor, startNode, endNode);
    } else {
      if (navigating) {
        graph.drawVisibleNodes(sFloor, startNode, endNode);
      } else {
        graph.drawAllNodes(sFloor, startNode, endNode);
        if (showEdgesToggle.isSelected()) graph.drawAllEdges(sFloor);
      }
    }
    WritableImage map2 = innerGrid.snapshot(new SnapshotParameters(), null);
    ImageIO.write(SwingFXUtils.fromFXImage(map2, null), "png", outputFile2);
    imageView.setImage(floor2Map);
    sFloor = "2";
    resizeCanvas();
    if (displayingRoute) {
      graph.drawCurrentPath(sFloor, startNode, endNode);
    } else {
      if (navigating) {
        graph.drawVisibleNodes(sFloor, startNode, endNode);
      } else {
        graph.drawAllNodes(sFloor, startNode, endNode);
      }
    }
    WritableImage map3 = innerGrid.snapshot(new SnapshotParameters(), null);
    ImageIO.write(SwingFXUtils.fromFXImage(map3, null), "png", outputFile3);
    imageView.setImage(floor3Map);
    sFloor = "3";
    resizeCanvas();
    if (displayingRoute) {
      graph.drawCurrentPath(sFloor, startNode, endNode);
    } else {
      if (navigating) {
        graph.drawVisibleNodes(sFloor, startNode, endNode);
      } else {
        graph.drawAllNodes(sFloor, startNode, endNode);
        if (showEdgesToggle.isSelected()) graph.drawAllEdges(sFloor);
      }
    }
    WritableImage map4 = innerGrid.snapshot(new SnapshotParameters(), null);
    ImageIO.write(SwingFXUtils.fromFXImage(map4, null), "png", outputFile4);
    imageView.setImage(floor4Map);
    sFloor = "4";
    resizeCanvas();
    if (displayingRoute) {
      graph.drawCurrentPath(sFloor, startNode, endNode);
    } else {
      if (navigating) {
        graph.drawVisibleNodes(sFloor, startNode, endNode);
      } else {
        graph.drawAllNodes(sFloor, startNode, endNode);
        if (showEdgesToggle.isSelected()) graph.drawAllEdges(sFloor);
      }
    }
    WritableImage map5 = innerGrid.snapshot(new SnapshotParameters(), null);
    ImageIO.write(SwingFXUtils.fromFXImage(map5, null), "png", outputFile5);
    imageView.setImage(floor5Map);
    sFloor = "5";
    resizeCanvas();
    if (displayingRoute) {
      graph.drawCurrentPath(sFloor, startNode, endNode);
    } else {
      if (navigating) {
        graph.drawVisibleNodes(sFloor, startNode, endNode);
      } else {
        graph.drawAllNodes(sFloor, startNode, endNode);
        if (showEdgesToggle.isSelected()) graph.drawAllEdges(sFloor);
      }
    }
    WritableImage map6 = innerGrid.snapshot(new SnapshotParameters(), null);
    ImageIO.write(SwingFXUtils.fromFXImage(map6, null), "png", outputFile6);

    EmailPageController.setScreenShot(map1, map2, map3, map4, map5, map6);

    SwitchScene.goToParent("/Views/EmailPage.fxml");
  }

  public void clearSelection(ActionEvent actionEvent) {
    startNode = null;
    endNode = null;
    displayingRoute = false;
    graph.resetPath();
    resizeCanvas();
    if (navigating) {
      graph.drawVisibleNodes(sFloor, startNode, endNode);
    } else {
      graph.drawAllNodes(sFloor, startNode, endNode);
      if (showEdgesToggle.isSelected()) graph.drawAllEdges(sFloor);
    }
  }

  // TODO: reset button??? (needs to set startNode and endNode to null)
  public void deleteNode(ActionEvent actionEvent) {}

  public void addNode(ActionEvent actionEvent) {
    addNodeMode = true;
    addNodeDB = true;
  }

  public void editEdge(ActionEvent actionEvent) {
    NodesAndEdges.editEdge(edgeID.getText(), startNodeID.getText(), endNodeID.getText(), 0);
    edgeID.clear();
    startNodeID.clear();
    endNodeID.clear();
  }

  public void addEdge(ActionEvent actionEvent) {
    NodesAndEdges.addNewEdge(startNodeID.getText(), endNodeID.getText());
    edgeID.clear();
    startNodeID.clear();
    endNodeID.clear();
  }

  public void deleteEdge(ActionEvent actionEvent) {
    NodesAndEdges.deleteEdge(edgeID.getText());
    edgeID.clear();
    startNodeID.clear();
    endNodeID.clear();
  }

  public void editNode(ActionEvent actionEvent) {
    if (addNodeDB) {
      NodesAndEdges.addNode(
          nodeID.getText(),
          xCoord.getText(),
          yCoord.getText(),
          floor.getText(),
          building.getText(),
          nodeType.getText(),
          longName.getText(),
          shortName.getText(),
          "O",
          true);

      nodeID.clear();
      xCoord.clear();
      yCoord.clear();
      floor.clear();
      building.clear();
      nodeType.clear();
      longName.clear();
      shortName.clear();
      addNodeDB = false;

    } else {
      NodesAndEdges.editNode(
          nodeID.getText(),
          Integer.parseInt(xCoord.getText()),
          Integer.parseInt(yCoord.getText()),
          floor.getText(),
          building.getText(),
          nodeType.getText(),
          longName.getText(),
          shortName.getText(),
          "O",
          true);
      nodeID.clear();
      xCoord.clear();
      yCoord.clear();
      floor.clear();
      building.clear();
      nodeType.clear();
      longName.clear();
      shortName.clear();
    }
  }

  public void uploadN(ActionEvent actionEvent) {
    DataHandling.importExcelData(true);
  }

  public void uploadE(ActionEvent actionEvent) {
    DataHandling.importExcelData(false);
  }

  public void saveN(ActionEvent actionEvent) {
    DataHandling.save(true);
  }

  public void saveE(ActionEvent actionEvent) {
    DataHandling.save(false);
  }

  public void showEdgesOnAction(ActionEvent actionEvent) {
    if (showEdgesToggle.isSelected()) {
      graph.drawAllEdges(sFloor);
    }
  }
}
