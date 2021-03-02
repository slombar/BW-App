package edu.wpi.cs3733.teamO.Controllers;

import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import edu.wpi.cs3733.teamO.Database.DataHandling;
import edu.wpi.cs3733.teamO.Database.NodesAndEdges;
import edu.wpi.cs3733.teamO.Database.UserHandling;
import edu.wpi.cs3733.teamO.GraphSystem.Graph;
import edu.wpi.cs3733.teamO.HelperClasses.Autocomplete;
import edu.wpi.cs3733.teamO.HelperClasses.PopupMaker;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import edu.wpi.cs3733.teamO.Opp;
import edu.wpi.cs3733.teamO.model.Edge;
import edu.wpi.cs3733.teamO.model.Node;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javax.imageio.ImageIO;

public class NewNavPageController implements Initializable {

  @FXML private StackPane nodeWarningPane;
  @FXML private JFXCheckBox setVisibility;
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
  Node startNode = null;
  Node endNode = null;
  Node selectedNode = null;

  ObservableList<String> listOfFloors =
      FXCollections.observableArrayList(
          "Campus", "Floor 1", "Floor 2", "Floor 3", "Floor 4", "Floor 5");

  public static Image campusMap = new Image("FaulknerCampus_Updated.png");
  public static Image floor1Map = new Image("Faulkner1_Updated.png");
  public static Image floor2Map = new Image("Faulkner2_Updated.png");
  public static Image floor3Map = new Image("Faulkner3_Updated.png");
  public static Image floor4Map = new Image("Faulkner4_Updated.png");
  public static Image floor5Map = new Image("Faulkner5_Updated.png");

  // booleans:

  private boolean editing = false;

  // navigating bools:
  private boolean selectingStart = false;
  private boolean selectingEnd = false;
  private boolean displayingRoute = false;

  private void setNavFalse() {
    selectingStart = false;
    selectingEnd = false;
    displayingRoute = false;
    startNode = null;
    endNode = null;
  }

  // editing bools:
  private boolean selectingEditNode = false;
  private boolean addNodeMode = false;
  private boolean addNodeDBMode = false;
  private boolean addingEdgeBD = false;
  // private boolean addingEdgeN1 = false;
  // private boolean addingEdgeN2 = false;

  private boolean showingEdges = false;

  private void setEditFalse() {
    selectingEditNode = false;
    addNodeMode = false;
    addNodeDBMode = false;
    addingEdgeBD = false;
    showingEdges = false;
    selectedNode = null;
  }

  ////////////////////
  ///// Methods: /////
  ////////////////////

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

    // draws appropriately accordingly to combination of booleans
    draw(1);

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

    editVBox.setVisible(editToggle.isSelected());

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
    editing = editToggle.isSelected();
    editVBox.setVisible(editing);

    if (editing) {
      setNavFalse();
      selectingEditNode = true;
    } else {
      setEditFalse();
    }

    draw();
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
    draw();
  }

  public void doPathfind(ActionEvent actionEvent) {
    if (startNode != null && endNode != null) {
      graph.resetPath();
      graph.findPath(startNode, endNode);
      displayingRoute = true;
      selectingStart = false;
      selectingEnd = false;
    }
    // TODO: else -> throw exception? or make popup or something? idk
    draw();
  }

  public void canvasClick(MouseEvent mouseEvent) {
    // displayingRoute = false;
    Node clickedNode = Graph.closestNode(sFloor, mouseEvent.getX(), mouseEvent.getY());

    // if navigating
    if (!editing) {
      if (selectingStart) {
        startNode = clickedNode;
      } else if (selectingEnd) {
        endNode = clickedNode;
      }
    }
    // if editing
    else {
      if (selectingEditNode) {
        autocompleteEditMap(clickedNode);
      } else if (addNodeMode) {
        Node n = new Node();
        n.setXCoord((int) mouseEvent.getX());
        n.setYCoord((int) mouseEvent.getY());

        autocompleteEditMap(n);
      }
    }

    draw();

    if (addNodeMode) {
      // TODO: draw circle

      addNodeMode = false;
      selectingEditNode = false; // (still)
    }

    System.out.println("mapCanvas click");
  }

  public void startLocSelection(ActionEvent actionEvent) {
    selectingStart = true;
    selectingEnd = false;
  }

  public void endLocSelection(ActionEvent actionEvent) {
    selectingStart = false;
    selectingEnd = true;
  }

  /**
   * creates an output file
   *
   * @param fileName
   * @return file
   */
  public File createOutputFile(String fileName) {
    String home = System.getProperty("user.home");
    File outputFile = new File(home + "/Downloads/" + fileName);
    return outputFile;
  }

  /**
   * grabs an image from a floor and gives it an output file
   *
   * @param image
   * @param floor
   * @param outputFile
   * @return WritableImage
   * @throws IOException
   */
  public WritableImage grabImage(Image image, String floor, File outputFile) throws IOException {

    imageView.setImage(image);
    sFloor = floor;
    resizeCanvas();
    draw();
    WritableImage map = innerGrid.snapshot(new SnapshotParameters(), null);
    ImageIO.write(SwingFXUtils.fromFXImage(map, null), "png", outputFile);
    return map;
  }

  /**
   * takes pictures of every floor to email and navigates to email page
   *
   * @param actionEvent
   * @throws IOException
   */
  public void toSharePage(ActionEvent actionEvent) throws IOException {

    GraphicsContext gc = mapCanvas.getGraphicsContext2D();
    mapCanvas.getGraphicsContext2D();
    WritableImage map1 = grabImage(campusMap, "G", createOutputFile("mapimg1.png"));
    WritableImage map2 = grabImage(floor1Map, "1", createOutputFile("mapimg2.png"));
    WritableImage map3 = grabImage(floor2Map, "2", createOutputFile("mapimg3.png"));
    WritableImage map4 = grabImage(floor3Map, "3", createOutputFile("mapimg4.png"));
    WritableImage map5 = grabImage(floor4Map, "4", createOutputFile("mapimg5.png"));
    WritableImage map6 = grabImage(floor5Map, "5", createOutputFile("mapimg6.png"));
    EmailPageController.setScreenShot(map1, map2, map3, map4, map5, map6);
    SwitchScene.goToParent("/Views/EmailPage.fxml");
  }

  public void clearSelection(ActionEvent actionEvent) {
    setNavFalse();

    graph.resetPath();

    resizeCanvas();
    draw();
  }

  public void addNode(ActionEvent actionEvent) {
    addNodeMode = true;
    addNodeDBMode = true;
    selectingEditNode = false;
    addingEdgeBD = false;
  }

  // TODO: make sure nodes take the checkbox value for VISIBLE
  public void editNode(ActionEvent actionEvent) {
    // TODO: i think this is where we would need to parse the text fields to validate them
    if (addNodeDBMode) {
      if ((nodeID.getText() == null)
          || (xCoord.getText() == null)
          || (yCoord.getText() == null)
          || (floor.getText() == null)
          || (building.getText() == null)
          || (nodeType.getText() == null)
          || (longName.getText() == null)
          || (shortName.getText() == null)) {
        PopupMaker.incompletePopup(nodeWarningPane);
      } else {
        try {
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
              setVisibility.isSelected());
          Node n =
              new Node(
                  nodeID.getText(),
                  Integer.parseInt(xCoord.getText()),
                  Integer.parseInt(yCoord.getText()),
                  floor.getText(),
                  building.getText(),
                  nodeType.getText(),
                  longName.getText(),
                  shortName.getText(),
                  "O",
                  setVisibility.isSelected());

          graph.addNode(n);
        } catch (SQLException throwables) {
          PopupMaker.nodeAlreadyExists(nodeWarningPane);
        }

        addNodeDBMode = false;
      }
      // editing node part
    } else {
      if (nodeID.getText().isEmpty()
          || xCoord.getText().isEmpty()
          || yCoord.getText().isEmpty()
          || floor.getText().isEmpty()
          || building.getText().isEmpty()
          || nodeType.getText().isEmpty()
          || longName.getText().isEmpty()
          || shortName.getText().isEmpty()) {
        PopupMaker.incompletePopup(nodeWarningPane);
      }
      try {
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
            setVisibility.isSelected());
        Node n =
            new Node(
                nodeID.getText(),
                Integer.parseInt(xCoord.getText()),
                Integer.parseInt(yCoord.getText()),
                floor.getText(),
                building.getText(),
                nodeType.getText(),
                longName.getText(),
                shortName.getText(),
                "O",
                setVisibility.isSelected());

        graph.addNode(n);
      } catch (SQLException throwables) {
        PopupMaker.nodeDoesntExist(nodeWarningPane);
      }
    }

    nodeID.clear();
    xCoord.clear();
    yCoord.clear();
    floor.clear();
    building.clear();
    nodeType.clear();
    longName.clear();
    shortName.clear();
    setVisibility.setSelected(false);

    selectingEditNode = true;
    draw();
  }

  public void deleteNode(ActionEvent actionEvent) {

    if (nodeID.getText().isEmpty()) {
      PopupMaker.incompletePopup(nodeWarningPane);
    } else {
      try {
        NodesAndEdges.deleteNode(nodeID.getText());
      } catch (SQLException throwables) {
        PopupMaker.nodeDoesntExist(nodeWarningPane);
      }
      graph.deleteNode(nodeID.getText());
      nodeID.clear();
      xCoord.clear();
      yCoord.clear();
      floor.clear();
      building.clear();
      nodeType.clear();
      longName.clear();
      shortName.clear();
      setVisibility.setSelected(false);
    }
    draw();
  }

  public void addEdge(ActionEvent actionEvent) {
    if (startNodeID.getText().isEmpty() || endNodeID.getText().isEmpty()) {
      PopupMaker.incompletePopup(nodeWarningPane);
    } else {
      try {
        NodesAndEdges.addNewEdge(startNodeID.getText(), endNodeID.getText());
        String eID = startNodeID.getText() + "_" + endNodeID.getText();
        Edge e = new Edge(eID, startNodeID.getText(), endNodeID.getText(), 0.0);
        graph.addEdge(e);
      } catch (SQLException throwables) {
        PopupMaker.edgeAlreadyExists(nodeWarningPane);
      }

      edgeID.clear();
      startNodeID.clear();
      endNodeID.clear();
    }
    draw();
  }

  public void deleteEdge(ActionEvent actionEvent) throws SQLException{

    if (startNodeID.getText().isEmpty() || endNodeID.getText().isEmpty()) {
      PopupMaker.incompletePopup(nodeWarningPane);
    } else {
      try {
        NodesAndEdges.deleteEdge(startNodeID.getText() + "_" + endNodeID.getText());
        graph.deleteEdge(edgeID.getText());
      } catch (SQLException throwables) {
        PopupMaker.edgeDoesntExists(nodeWarningPane);
      }
      edgeID.clear();
      startNodeID.clear();
      endNodeID.clear();
    }
    draw();
  }

  public void uploadN(ActionEvent actionEvent) {
    DataHandling.importExcelData(true);
    // TODO: re-initialize Graph after uploading excel file?
  }

  public void uploadE(ActionEvent actionEvent) {
    DataHandling.importExcelData(false);
    // TODO: re-initialize Graph after uploading excel file?
  }

  public void saveN(ActionEvent actionEvent) {
    DataHandling.save(true);
  }

  public void saveE(ActionEvent actionEvent) {
    DataHandling.save(false);
  }

  public void showEdgesOnAction(ActionEvent actionEvent) {
    if (editing) {
      showingEdges = showEdgesToggle.isSelected();
    }
    draw();
  }

  private void draw() {
    resizeCanvas();

    // i know these can be simplified but i don't care -- this is more organized
    if (!editing && !displayingRoute) {
      graph.drawVisibleNodes(sFloor, startNode, endNode);
    } else if (!editing && displayingRoute) {
      graph.drawCurrentPath(sFloor, startNode, endNode);
    } else if (editing) {
      graph.drawAllNodes(sFloor, selectedNode);
      if (showingEdges) {
        graph.drawAllEdges(sFloor);
      }
    }
  }

  // ignore this -- BUT DON'T DELETE IT!!!!!!!!!!!!!!
  private void draw(int i) {
    // i know these can be simplified but i don't care -- this is more organized
    if (!editing && !displayingRoute) {
      graph.drawVisibleNodes(sFloor, startNode, endNode);
    } else if (!editing && displayingRoute) {
      graph.drawCurrentPath(sFloor, startNode, endNode);
    } else if (editing) {
      graph.drawAllNodes(sFloor, selectedNode);
      if (showingEdges) {
        graph.drawAllEdges(sFloor);
      }
    }
  }

  public void updateEdgeID(KeyEvent actionEvent) {
    edgeID.setText(startNodeID.getText() + "_" + endNodeID.getText());
  }

  public void updateEdgeIDMouse(MouseEvent mouseEvent) {
    edgeID.setText(startNodeID.getText() + "_" + endNodeID.getText());
  }
}
