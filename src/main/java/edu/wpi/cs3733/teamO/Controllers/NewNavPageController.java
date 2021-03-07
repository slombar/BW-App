package edu.wpi.cs3733.teamO.Controllers;

import static edu.wpi.cs3733.teamO.GraphSystem.Graph.*;

import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import com.mashape.unirest.http.exceptions.UnirestException;
import edu.wpi.cs3733.teamO.Database.DataHandling;
import edu.wpi.cs3733.teamO.Database.UserHandling;
import edu.wpi.cs3733.teamO.GraphSystem.Graph;
import edu.wpi.cs3733.teamO.HelperClasses.Autocomplete;
import edu.wpi.cs3733.teamO.HelperClasses.DrawHelper;
import edu.wpi.cs3733.teamO.HelperClasses.PopupMaker;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import edu.wpi.cs3733.teamO.Model.Edge;
import edu.wpi.cs3733.teamO.Model.Node;
import edu.wpi.cs3733.teamO.Opp;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javax.imageio.ImageIO;

public class NewNavPageController implements Initializable {
  // edit map components
  @FXML private JFXToggleButton editToggle;
  @FXML private VBox editVBox;
  @FXML private JFXTextField nodeID;
  @FXML private JFXTextField xCoord;
  @FXML private JFXTextField yCoord;
  @FXML private JFXTextField floor;
  @FXML private JFXTextField building;
  @FXML private JFXTextField nodeType;
  @FXML private JFXTextField longName;
  @FXML private JFXTextField shortName;
  @FXML private JFXCheckBox setVisibility;
  @FXML private JFXToggleButton showEdgesToggle;
  @FXML private JFXTextField edgeID;
  @FXML private JFXTextField startNodeID;
  @FXML private JFXTextField endNodeID;
  @FXML private JFXComboBox algoStratCBox;
  @FXML private StackPane nodeWarningPane;

  // side menu
  @FXML private JFXDrawer drawer;
  @FXML private JFXHamburger hamburger;

  // map
  @FXML private HBox hboxRef;
  @FXML private VBox vboxRef;
  @FXML private GridPane gridPane;
  @FXML private ImageView imageView;
  @FXML private Canvas mapCanvas;
  @FXML private GridPane innerGrid;

  // pathfinding
  @FXML private JFXComboBox<String> floorSelectionBtn;

  private GraphicsContext gc;
  private String selectedFloor = "Campus";
  private String sFloor = "G";
  private String sideMenuUrl;

  Node startNode = null;
  Node endNode = null;
  Node selectedNode = null;

  String strategy = "A*";
  ObservableList<String> listOfStrats = FXCollections.observableArrayList("A*", "DFS", "BFS");

  ObservableList<String> listOfFloors =
      FXCollections.observableArrayList(
          "Campus", "Floor 1", "Floor 2", "Floor 3", "Floor 4", "Floor 5");

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
    algoStratCBox.setItems(listOfStrats);

    mapCanvas.toFront();
    gc = mapCanvas.getGraphicsContext2D();

    imageView.setImage(campusMap);
    resizableWindow();

    GRAPH.setGraphicsContext(gc);

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

  /**
   * switches between editing and pathfinding for admin users
   *
   * @param actionEvent
   */
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

  /**
   * fills in a nodes info when it is clicked
   *
   * @param clickedNode
   */
  public void autocompleteEditMap(Node clickedNode) {
    nodeID.setText(clickedNode.getID());
    xCoord.setText(Integer.toString(clickedNode.getXCoord()));
    yCoord.setText(Integer.toString(clickedNode.getYCoord()));
    floor.setText(clickedNode.getFloor());
    building.setText(clickedNode.getBuilding());
    nodeType.setText(clickedNode.getNodeType());
    longName.setText(clickedNode.getLongName());
    shortName.setText(clickedNode.getShortName());
    setVisibility.setSelected(clickedNode.isVisible());
  }

  /**
   * goes to the main page (changes based on user logged in)
   *
   * @param actionEvent
   */
  public void goToMain(ActionEvent actionEvent) {
    String MenuUrl = "/Views/MainPage.fxml";
    if (UserHandling.getEmployee() || UserHandling.getAdmin())
      MenuUrl = "/Views/StaffMainPage.fxml";
    SwitchScene.goToParent(MenuUrl);
  }

  /**
   * switches between images and canvases for different floors selected in the combobox
   *
   * @param actionEvent
   */
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

  /**
   * resets path and creates a new path depending on start and end nodes
   *
   * @param actionEvent
   */
  public void doPathfind(ActionEvent actionEvent) {
    if (startNode != null && endNode != null) {
      GRAPH.resetPath();
      GRAPH.findPath(strategy, startNode, endNode);
      displayingRoute = true;
      selectingStart = false;
      selectingEnd = false;
    } else {
      PopupMaker.invalidPathfind(nodeWarningPane);
    }

    draw();
  }

  /**
   * determines closest node to mouse click on canvas, used for both navigating and editing the map
   *
   * @param mouseEvent
   */
  public void canvasClick(MouseEvent mouseEvent) {
    // displayingRoute = false;
    Node clickedNode = Graph.closestNode(sFloor, mouseEvent.getX(), mouseEvent.getY());
    Circle c = null;

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
        selectedNode = clickedNode;
      } else if (addNodeMode) {
        Node n = getRealXY(sFloor, mouseEvent);
        n.setFloor(sFloor);

        c = new Circle();
        c.setCenterX(mouseEvent.getX());
        c.setCenterY(mouseEvent.getY());
        c.setRadius(mapCanvas.getWidth() * 0.00625);

        autocompleteEditMap(n);
      }
    }

    if (addNodeMode) {
      selectedNode = null;
      draw();
      DrawHelper.drawSingleNode(gc, c, Color.BLUE);
      addNodeMode = false;
      selectingEditNode = true;
    } else {
      draw();
    }

    System.out.println("mapCanvas click");
  }

  /**
   * gets the xy coordinates of the mouse and scales it to the image
   *
   * @param floor
   * @param mouseEvent
   * @return
   */
  private Node getRealXY(String floor, MouseEvent mouseEvent) {
    Node n = new Node();
    double imgX = 0;
    double imgY = 0;
    switch (floor) {
      case "G":
        imgX = campusMap.getWidth();
        imgY = campusMap.getHeight();
        break;
      case "1":
        imgX = floor1Map.getWidth();
        imgY = floor1Map.getHeight();
        break;
      case "2":
        imgX = floor2Map.getWidth();
        imgY = floor2Map.getHeight();
        break;
      case "3":
        imgX = floor3Map.getWidth();
        imgY = floor3Map.getHeight();
        break;
      case "4":
        imgX = floor4Map.getWidth();
        imgY = floor4Map.getHeight();
        break;
      case "5":
        imgX = floor5Map.getWidth();
        imgY = floor5Map.getHeight();
        break;
    }

    double nPercX = mouseEvent.getX() / gc.getCanvas().getWidth();
    double nPercY = mouseEvent.getY() / gc.getCanvas().getHeight();
    n.setXCoord((int) (nPercX * imgX));
    n.setYCoord((int) (nPercY * imgY));
    return n;
  }

  /**
   * sets the start location for pathfinding
   *
   * @param actionEvent
   */
  public void startLocSelection(ActionEvent actionEvent) {
    selectingStart = true;
    selectingEnd = false;
  }

  /**
   * sets the end desination for pathfinding
   *
   * @param actionEvent
   */
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
  public void toSharePage(ActionEvent actionEvent) throws IOException, UnirestException {

    GraphicsContext gc = mapCanvas.getGraphicsContext2D();
    mapCanvas.getGraphicsContext2D();
    WritableImage map1 = grabImage(campusMap, "G", createOutputFile("mapimg1.png"));
    WritableImage map2 = grabImage(floor1Map, "1", createOutputFile("mapimg2.png"));
    WritableImage map3 = grabImage(floor2Map, "2", createOutputFile("mapimg3.png"));
    WritableImage map4 = grabImage(floor3Map, "3", createOutputFile("mapimg4.png"));
    WritableImage map5 = grabImage(floor4Map, "4", createOutputFile("mapimg5.png"));
    WritableImage map6 = grabImage(floor5Map, "5", createOutputFile("mapimg6.png"));
    EmailPageController.setScreenShot(map1, map2, map3, map4, map5, map6);
    // TODO: Insert method call that write qr.png to download folder
    //    SharingFunctionality.createQRCode(
    //        "mapimg1.png", "mapimg2.png", "mapimg3.png", "mapimg4.png", "mapimg5.png",
    // "mapimg6.png");
    SwitchScene.goToParent("/Views/EmailPage.fxml");
  }

  public void clearSelection(ActionEvent actionEvent) {
    setNavFalse();

    GRAPH.resetPath();

    resizeCanvas();
    draw();
  }

  /**
   * sets the mode for adding a new node
   *
   * @param actionEvent
   */
  public void addNode(ActionEvent actionEvent) {
    addNodeMode = true;
    addNodeDBMode = true;
    selectingEditNode = false;
    addingEdgeBD = false;
  }

  /**
   * Edits the node that is currently selected. If the map is page is in adding new node mode,
   * clicking this button will add the new node to the DB. Otherwise, will edit existing node. Once
   * changes are made, the fields will be cleared
   *
   * @param actionEvent
   */
  public void editNode(ActionEvent actionEvent) {
    // if any fields are empty, show appropriate warning
    if (isNodeInfoEmpty()) {
      PopupMaker.incompletePopup(nodeWarningPane);
    }
    // else, add/edit Node (depending on addNodeDBMode = t/f)
    else {
      try {
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

        GRAPH.addNode(n, addNodeDBMode);
        clearNodeInfo();
      } catch (SQLException throwables) {
        PopupMaker.nodeAlreadyExists(nodeWarningPane);
      }
    }

    addNodeDBMode = false;

    selectingEditNode = true;
    draw();
  }

  /**
   * will delete the node that is currently selected
   *
   * @param actionEvent
   */
  public void deleteNode(ActionEvent actionEvent) {
    // if any fields are empty, show appropriate warning
    if (nodeID.getText().isEmpty()) {
      PopupMaker.incompletePopup(nodeWarningPane);
    }
    // else, delete selected Node
    else {
      try {
        GRAPH.deleteNode(nodeID.getText());
        clearNodeInfo();
      } catch (SQLException throwables) {
        PopupMaker.nodeDoesntExist(nodeWarningPane);
      }
    }

    draw();
  }

  /**
   * will add a new edge based on the start and end node IDs
   *
   * @param actionEvent
   */
  public void addEdge(ActionEvent actionEvent) {
    // if any fields are empty, show appropriate warning
    if (startNodeID.getText().isEmpty() || endNodeID.getText().isEmpty()) {
      PopupMaker.incompletePopup(nodeWarningPane);
    }
    // else, add appropriate edge
    else {
      try {
        String eID = startNodeID.getText() + "_" + endNodeID.getText();
        Edge e = new Edge(eID, startNodeID.getText(), endNodeID.getText(), 0.0);
        GRAPH.addEdge(e);
        clearEdgeInfo();
      } catch (SQLException throwables) {
        PopupMaker.edgeAlreadyExists(nodeWarningPane);
      }
    }

    draw();
  }

  /**
   * will delete a node based on start and end node IDs
   *
   * @param actionEvent
   * @throws SQLException
   */
  public void deleteEdge(ActionEvent actionEvent) throws SQLException {
    // if any fields are empty, show appropriate warning
    if (startNodeID.getText().isEmpty() || endNodeID.getText().isEmpty()) {
      PopupMaker.incompletePopup(nodeWarningPane);
    } else {
      try {
        GRAPH.deleteEdge(startNodeID.getText(), endNodeID.getText());
        clearEdgeInfo();
      } catch (SQLException throwables) {
        PopupMaker.edgeDoesntExists(nodeWarningPane);
      }
    }

    draw();
  }

  /**
   * checks if the any of the node fields are null
   *
   * @return true if any node fields are null
   */
  private boolean isNodeInfoNull() {
    if ((nodeID.getText() == null)
        || (xCoord.getText() == null)
        || (yCoord.getText() == null)
        || (floor.getText() == null)
        || (building.getText() == null)
        || (nodeType.getText() == null)
        || (longName.getText() == null)
        || (shortName.getText() == null)) {
      return true;
    }
    return false;
  }

  /**
   * checks if the any of the node fields are empty
   *
   * @return true if any node fields are empty
   */
  private boolean isNodeInfoEmpty() {
    if (nodeID.getText().isEmpty()
        || xCoord.getText().isEmpty()
        || yCoord.getText().isEmpty()
        || floor.getText().isEmpty()
        || building.getText().isEmpty()
        || nodeType.getText().isEmpty()
        || longName.getText().isEmpty()
        || shortName.getText().isEmpty()) {
      return true;
    }
    return false;
  }

  /** clears all info in node textfields */
  private void clearNodeInfo() {
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

  /** clears all info in edge textfields */
  private void clearEdgeInfo() {
    edgeID.clear();
    startNodeID.clear();
    endNodeID.clear();
  }

  /**
   * will upload node data from a CSV
   *
   * @param actionEvent
   */
  public void uploadN(ActionEvent actionEvent) {
    DataHandling.importExcelData(true);
    // TODO: re-initialize Graph after uploading excel file?
  }

  /**
   * will upload edge data from a CSV
   *
   * @param actionEvent
   */
  public void uploadE(ActionEvent actionEvent) {
    DataHandling.importExcelData(false);
    // TODO: re-initialize Graph after uploading excel file?
  }

  /**
   * will save current node data to a CSV
   *
   * @param actionEvent
   */
  public void saveN(ActionEvent actionEvent) {
    DataHandling.save(true);
  }

  /**
   * will save current edge data to a CSV
   *
   * @param actionEvent
   */
  public void saveE(ActionEvent actionEvent) {
    DataHandling.save(false);
  }

  /**
   * will show all edges on the map if toggled
   *
   * @param actionEvent
   */
  public void showEdgesOnAction(ActionEvent actionEvent) {
    if (editing) {
      showingEdges = showEdgesToggle.isSelected();
    }
    draw();
  }

  /** can draw the path, nodes, and edges based on booleans */
  private void draw() {
    resizeCanvas();

    // i know these can be simplified but i don't care -- this is more organized imo

    if (!editing && !displayingRoute) {
      // draw the visible Node (navigating) on sFloor + highlight start and end (if selected)
      GRAPH.drawVisibleNodes(sFloor, startNode, endNode);
    } else if (!editing && displayingRoute) {
      // draw the portion on sFloor + highlight start and end
      GRAPH.drawCurrentPath(sFloor, startNode, endNode);
    } else if (editing) {
      // draw ALL the nodes (editing) + highlight selected node (if selected)
      GRAPH.drawAllNodes(sFloor, selectedNode);
      // and if "show edges" is selected, draw them as well
      if (showingEdges) {
        GRAPH.drawAllEdges(sFloor);
      }
    }
  }

  // ignore this -- BUT DON'T DELETE IT!!!!!!!!!!!!!!
  private void draw(int i) {
    // i know these can be simplified but i don't care -- this is more organized
    if (!editing && !displayingRoute) {
      GRAPH.drawVisibleNodes(sFloor, startNode, endNode);
    } else if (!editing && displayingRoute) {
      GRAPH.drawCurrentPath(sFloor, startNode, endNode);
    } else if (editing) {
      GRAPH.drawAllNodes(sFloor, selectedNode);
      if (showingEdges) {
        GRAPH.drawAllEdges(sFloor);
      }
    }
  }

  /**
   * automatically generates the edgeID from the start and end node IDs once starting to type in the
   * start and end node ID textfields
   *
   * @param actionEvent
   */
  public void updateEdgeID(KeyEvent actionEvent) {
    edgeID.setText(startNodeID.getText() + "_" + endNodeID.getText());
  }

  /**
   * will update the edge ID once clicking on the edge ID textfield
   *
   * @param mouseEvent
   */
  public void updateEdgeIDMouse(MouseEvent mouseEvent) {
    edgeID.setText(startNodeID.getText() + "_" + endNodeID.getText());
  }

  /**
   * chooses the pathfinding algorithm used
   *
   * @param actionEvent
   */
  public void chooseStrat(ActionEvent actionEvent) {
    strategy = (String) algoStratCBox.getValue();
  }
}
