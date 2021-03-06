package edu.wpi.cs3733.teamO.Controllers;

import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import com.mashape.unirest.http.exceptions.UnirestException;
import edu.wpi.cs3733.teamO.Database.DataHandling;
import edu.wpi.cs3733.teamO.Database.Graph;
import edu.wpi.cs3733.teamO.Database.UserHandling;
import edu.wpi.cs3733.teamO.GraphSystem.AStarSearch;
import edu.wpi.cs3733.teamO.HelperClasses.Autocomplete;
import edu.wpi.cs3733.teamO.HelperClasses.DrawHelper;
import edu.wpi.cs3733.teamO.HelperClasses.PopupMaker;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import edu.wpi.cs3733.teamO.Opp;
import edu.wpi.cs3733.teamO.model.Node;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
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
  @FXML public Canvas mapCanvas;
  @FXML private GridPane innerGrid;

  // pathfinding
  @FXML private JFXComboBox<String> floorSelectionBtn;

  public static GraphicsContext gc;
  private String selectedFloor = "Campus";
  private String sFloor = "G";
  private String sideMenuUrl;
  Graph graph;
  String team = "O";

  Node startNode = null;
  Node endNode = null;
  Node selectedNode = null;

  String strategy = "A*";
  ObservableList<String> listOfStrats = FXCollections.observableArrayList("A*", "DFS", "BFS");

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

  public static GraphicsContext getGC() {
    return gc;
  }

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

  DrawHelper graphDrawer;

  ////////////////////
  ///// Methods: /////
  ////////////////////

  public NewNavPageController() {}

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    graph = Graph.getInstance();
    floorSelectionBtn.setItems(listOfFloors);
    floorSelectionBtn.setValue("Campus");
    algoStratCBox.setItems(listOfStrats);

    mapCanvas.toFront();
    gc = mapCanvas.getGraphicsContext2D();
    graphDrawer = new DrawHelper();
    graphDrawer.createCircles();
    graphDrawer.drawNodes(gc, graph.listOfNodes);
    graphDrawer.drawEdges(gc, "G");

    imageView.setImage(campusMap);
    resizableWindow();
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

    Autocomplete autocomplete = new Autocomplete();

    // autocompletes the node Id for start and end
    Autocomplete.autoComplete(autocomplete.autoNodeData("nodeID"), startNodeID);
    Autocomplete.autoComplete(autocomplete.autoNodeData("nodeID"), endNodeID);
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
  }

  /**
   * resets path and creates a new path depending on start and end nodes
   *
   * @param actionEvent
   */
  public void doPathfind(ActionEvent actionEvent) {

    if (startNode != null && endNode != null) {
      AStarSearch pathFindingStrategy = new AStarSearch();

      ArrayList<Node> path = pathFindingStrategy.findPath(startNode, endNode);
      graphDrawer.drawPath(gc, path, selectedFloor);

      displayingRoute = true;
      selectingStart = false;
      selectingEnd = false;
    } else {
      PopupMaker.invalidPathfind(nodeWarningPane);
    }
  }

  /**
   * Returns the Node closest to the given (x,y) on the given floor
   *
   * @param floor floor currently displaying when clicked
   * @param x x-coordinate of the ClickEvent
   * @param y y-coordinate of the ClickEvent
   * @return Node closest to the ClickEvent
   * @throws NullPointerException
   */
  public Node closestNode(String floor, double x, double y) throws NullPointerException {
    double currentDist = 1000000000;
    Node node = null;

    for (Node n : graph.listOfNodes) {
      int xcoord = n.getXCoord();
      int ycoord = n.getYCoord();

      if (n.getFloor().equals(floor)) {

        double dist = Math.pow(Math.abs(x - xcoord), 2.0) + Math.pow(Math.abs(y - ycoord), 2.0);
        if (dist < currentDist) {
          currentDist = dist;
          node = n;
        }
      }
    }

    // TODO: nodeCircleHashtable.get(n).actionEvent();
    return node;
  }

  /**
   * determines closest node to mouse click on canvas, used for both navigating and editing the map
   *
   * @param mouseEvent
   */
  public void canvasClick(MouseEvent mouseEvent) {
    // displayingRoute = false;
    Node clickedNode = closestNode(sFloor, mouseEvent.getX(), mouseEvent.getY());
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
      DrawHelper.drawSingleNode(gc, c, Color.BLUE);
      addNodeMode = false;
      selectingEditNode = true;
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
   * sets teh start location for pathfinding
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
    EmailPageController.prepareQR();
    SwitchScene.goToParent("/Views/EmailPage.fxml");
  }

  public void clearSelection(ActionEvent actionEvent) {
    setNavFalse();
    resizeCanvas();
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

  // Graph. Putting here bc this shit is a mess smh

  /**
   * Edits the node that is currently selected. If the map is page is in adding new node mode,
   * clicking this button will add the new node to the DB. Otherwise, will edit existing node. Once
   * changes are made, the fields will be cleared
   *
   * @param actionEvent
   */
  public void editNode(ActionEvent actionEvent) {
    // TODO: i think this is where we would need to parse the text fields to validate them
    if (addNodeDBMode) {
      if (isNodeInfoNull()) {
        PopupMaker.incompletePopup(nodeWarningPane);
      } else {

        Node newNode =
            new Node(
                Integer.valueOf(xCoord.getText()),
                Integer.valueOf(yCoord.getText()),
                floor.getText(),
                building.getText(),
                nodeType.getText(),
                longName.getText(),
                team);
        graph.addNode(newNode);

        addNodeDBMode = false;
      }
      // editing node part
    } else {
      if (isNodeInfoEmpty()) {
        PopupMaker.incompletePopup(nodeWarningPane);
      }
      Node pastNode = graph.key.get(nodeID.getText());

      Node futureNode =
          new Node(
              Integer.parseInt(xCoord.getText()),
              Integer.parseInt(yCoord.getText()),
              floor.getText(),
              building.getText(),
              nodeType.getText(),
              longName.getText(),
              team);

      graph.editNode(pastNode, futureNode);
    }
    clearNodeInfo();
    selectingEditNode = true;
  }

  /**
   * will delete the node that is currently selected
   *
   * @param actionEvent
   */
  public void deleteNode(ActionEvent actionEvent) {

    if (nodeID.getText().isEmpty()) {
      PopupMaker.incompletePopup(nodeWarningPane);
    } else {

      graph.deleteNode(nodeID.getText());

      graph.deleteNode(nodeID.getText());
      clearNodeInfo();
    }
  }

  /**
   * will add a new edge based on the start and end node IDs
   *
   * @param actionEvent
   */
  public void addEdge(ActionEvent actionEvent) {
    if (startNodeID.getText().isEmpty() || endNodeID.getText().isEmpty()) {
      PopupMaker.incompletePopup(nodeWarningPane);
    } else {
      try {
        graph.addEdge(startNodeID.getText(), endNodeID.getText());

      } catch (Exception e) {
        PopupMaker.edgeAlreadyExists(nodeWarningPane);
      }
      clearEdgeInfo();
    }
  }

  /**
   * will delete a node based on start and end node IDs
   *
   * @param actionEvent
   * @throws SQLException
   */
  public void deleteEdge(ActionEvent actionEvent) throws SQLException {

    if (startNodeID.getText().isEmpty() || endNodeID.getText().isEmpty()) {
      PopupMaker.incompletePopup(nodeWarningPane);
    } else {
      try {
        // NodesAndEdges.deleteEdge(startNodeID.getText() + "_" + endNodeID.getText());
        graph.deleteEdge(startNodeID.getText(), endNodeID.getText());
      } catch (Exception throwables) {
        PopupMaker.edgeDoesntExists(nodeWarningPane);
      }
      clearEdgeInfo();
    }
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
    DataHandling d = new DataHandling();
    d.importExcelData();
    // TODO: re-initialize Graph after uploading excel file?
  }

  /**
   * will upload edge data from a CSV
   *
   * @param actionEvent
   */
  public void uploadE(ActionEvent actionEvent) {
    // fuck this
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
