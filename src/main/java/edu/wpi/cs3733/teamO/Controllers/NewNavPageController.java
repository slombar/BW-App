package edu.wpi.cs3733.teamO.Controllers;

import static edu.wpi.cs3733.teamO.GraphSystem.Graph.*;

import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import com.mashape.unirest.http.exceptions.UnirestException;
import edu.wpi.cs3733.teamO.Database.NodesAndEdges;
import edu.wpi.cs3733.teamO.Database.UserHandling;
import edu.wpi.cs3733.teamO.GraphSystem.Graph;
import edu.wpi.cs3733.teamO.HelperClasses.DrawHelper;
import edu.wpi.cs3733.teamO.HelperClasses.PopupMaker;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import edu.wpi.cs3733.teamO.Model.Node;
import edu.wpi.cs3733.teamO.Opp;
import edu.wpi.cs3733.teamO.UserTypes.Settings;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javax.imageio.ImageIO;

public class NewNavPageController implements Initializable {
  public VBox directionvbox;
  public JFXButton alignVButton;
  public JFXButton alignHButton;
  public Button camp;
  public Button F1;
  public Button F2;
  public Button F3;
  public Button F4;
  public Button F5;
  public JFXToggleButton elevatorOnlyToggle;
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
  @FXML private JFXTextField startNodeID;
  @FXML private JFXTextField endNodeID;
  @FXML private JFXComboBox algoStratCBox;
  @FXML private StackPane nodeWarningPane;
  @FXML private Label displayLongName;
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
  private double percImageView = 1.0;
  private Rectangle2D currentViewport;
  private String sFloor = "G";
  private String sideMenuUrl;
  private String pathFloors = "";

  ArrayList<String> alignList = new ArrayList<>();

  Node startNode = null;
  Node endNode = null;
  Node selectedNode = null;
  Node selectedNodeB = null;

  String strategy = Settings.getInstance().getAlgoChoice();
  ObservableList<String> listOfStrats =
      FXCollections.observableArrayList("A*", "Djikstra", "DFS", "BFS");

  /*ObservableList<String> listOfFloors =
  FXCollections.observableArrayList(
      "Campus", "Floor 1", "Floor 2", "Floor 3", "Floor 4", "Floor 5");*/

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
  private boolean selectingAlign = false;

  private void setEditFalse() {
    selectingEditNode = false;
    addNodeMode = false;
    addNodeDBMode = false;
    addingEdgeBD = false;
    showingEdges = false;
    showEdgesToggle.setSelected(false);
    selectedNode = null;
  }

  ////////////////////
  ///// Methods: /////
  ////////////////////

  public NewNavPageController() {}

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    // floorSelectionBtn.setItems(listOfFloors);
    // floorSelectionBtn.setValue("Campus");
    // camp.setVisible(false);
    camp.setStyle("-fx-background-color: #fec107;");
    algoStratCBox.setItems(listOfStrats);

    mapCanvas.toFront();
    gc = mapCanvas.getGraphicsContext2D();

    imageView.setImage(campusMap);
    currentViewport = new Rectangle2D(0, 0, campusMap.getWidth(), campusMap.getHeight());
    imageView.setViewport(currentViewport);
    resizableWindow();

    GRAPH.setGraphicsContext(gc);

    editToggle.setVisible(false);

    if (UserHandling.getEmployee()) {

      sideMenuUrl = "/Views/SideMenuStaff.fxml";
      if (UserHandling.getAdmin()) {
        sideMenuUrl = "/Views/SideMenuAdmin.fxml";

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
    // Autocomplete.autoComplete(Autocomplete.autoNodeData("nodeID"), startNodeID);
    // Autocomplete.autoComplete(Autocomplete.autoNodeData("nodeID"), endNodeID);
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

    if (editToggle.isSelected() || GRAPH.allConnected()) {
      editing = editToggle.isSelected();
    } else {

      editing = true;
      editToggle.setSelected(true);

      PopupMaker.unconnectedPopup(nodeWarningPane);
      return;
    }

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
    try {
      displayLongName.setText(NodesAndEdges.getNodeLongName(clickedNode.getID()));
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
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
   * resets path and creates a new path depending on start and end nodes
   *
   * @param actionEvent
   */
  public void doPathfind(ActionEvent actionEvent) {
    if (startNode != null && endNode != null) {
      GRAPH.resetPath();
      GRAPH.findPath(Settings.getInstance().getAlgoChoice(), startNode, endNode);
      displayingRoute = true;
      selectingStart = false;
      selectingEnd = false;
    } else {
      PopupMaker.invalidPathfind(nodeWarningPane);
    }

    draw();
    pathFloors = "";
    for (Node n : GRAPH.getPath()) {
      if (!pathFloors.contains(n.getFloor())) pathFloors += n.getFloor();
    }
    for (String d : Graph.findTextDirection()) {
      addTextToDirectionBox(d);
    }
  }

  /**
   * determines closest node to mouse click on canvas, used for both navigating and editing the map
   *
   * @param mouseEvent
   */
  public void canvasClick(MouseEvent mouseEvent) {
    // displayingRoute = false;
    Node clickedNode =
        GRAPH.closestNode(sFloor, mouseEvent.getX(), mouseEvent.getY(), editing, imageView);

    // block for SHIFT CLICK
    if (editing && mouseEvent.isShiftDown() && mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
      if ((selectingEditNode && selectedNode == null)) {
        selectedNode = clickedNode;

      } else if (selectingEditNode && selectedNode != null && selectedNode != clickedNode) {
        alignList.add(clickedNode.getID());
      }
      selectedNodeB = null;
      autocompleteEditMap(clickedNode);
    }
    // ----------------------
    // block for LEFT CLICK
    else if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
      alignList = new ArrayList<>();

      if (!addNodeMode) {
        selectingEditNode = true;
      }

      selectedNodeB = null;
      Circle c = null;
      Node n = null;

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
          n = getRealXY(mouseEvent);
          n.setFloor(sFloor);

          autocompleteEditMap(n);
        }
      }

      if (addNodeMode) {
        selectedNode = null;
        draw();
        DrawHelper.drawSingleNode(gc, n, Color.BLUE, imageView, false);
        addNodeMode = false;
        selectingEditNode = true;
        return;
      } else {
        draw();
      }
    }
    // ----------------------
    // block for RIGHT CLICK
    else if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
      alignList = new ArrayList<>();

      // if navigating
      if (!editing) {
        draw();
      }
      // if editing
      else {
        if (selectingEditNode && selectedNode == null) {
          autocompleteEditMap(clickedNode);
          selectedNode = clickedNode;
          selectingEditNode = false;
        } else {
          autocompleteEditMap(clickedNode);
          selectedNodeB = clickedNode;
        }
      }

      // if selectedNode != null or selectedNodeB != null, set those Edge text fields
      if (selectedNode != null) {
        startNodeID.setText(selectedNode.getID());
      }
      if (selectedNodeB != null) {
        endNodeID.setText(selectedNodeB.getID());
      }

      /**
       * If the middle mouse is pressed, we want the node to be dragged with the user's cursor. Once
       * it is pressed up, we will drop the node in that location
       */
    }

    draw();
  }

  /**
   * gets the xy coordinates of the mouse and scales it to the image
   *
   * @param mouseEvent
   * @return
   */
  private Node getRealXY(MouseEvent mouseEvent) {
    Node n = new Node();
    n.setXCoord((int) getImgX(mouseEvent.getX()));
    n.setYCoord((int) getImgY(mouseEvent.getY()));
    return n;
  }

  public void onCanvasScroll(ScrollEvent scrollEvent) {
    double scrollDeltaY = scrollEvent.getDeltaY();
    // if scroll is at least a certain amount, then zoom (idk, maybe change this??)
    if (Math.abs(scrollDeltaY) > 10) {
      // if positive, then scrolling up (zooming in)
      if (scrollDeltaY > 0) {
        if (percImageView <= 0.4) {
          return;
        } else {
          percImageView -= 0.05;
        }

      }
      // else, scrolling down (zooming out)
      else {
        if (percImageView >= 1.0) {
          return;
        } else {
          percImageView += 0.05;
        }
      }
    }

    double a = getImgX(scrollEvent.getX());
    double b = getImgY(scrollEvent.getY());
    double vX = percImageView * imageView.getImage().getWidth();
    double vY = percImageView * imageView.getImage().getHeight();
    // zoom option A:
    currentViewport =
        new Rectangle2D(
            (a * (1 - percImageView) + imageView.getImage().getWidth() * 0.5 * percImageView)
                - vX / 2,
            (b * (1 - percImageView) + imageView.getImage().getHeight() * 0.5 * percImageView)
                - vY / 2,
            vX,
            vY);
    // zoom option B:
    /*double percCanvasA = scrollEvent.getX() / mapCanvas.getWidth();
    double percCanvasB = scrollEvent.getY() / mapCanvas.getHeight();
    currentViewport = new Rectangle2D(a - (percCanvasA * vX), b - (percCanvasB * vY), vX, vY);*/

    imageView.setViewport(currentViewport);
    draw();
  }

  public double getImgX(double canvasX) {
    double percCanvasX = canvasX / mapCanvas.getWidth();
    return (currentViewport.getMinX() + (percCanvasX * currentViewport.getWidth()));
  }

  public double getImgY(double canvasY) {
    double percCanvasY = canvasY / mapCanvas.getHeight();
    return (currentViewport.getMinY() + ((percCanvasY * currentViewport.getHeight())));
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
    // TODO: Insert method call that write qr.png to download folder
    //    SharingFunctionality.createQRCode(
    //        "mapimg1.png", "mapimg2.png", "mapimg3.png", "mapimg4.png", "mapimg5.png",
    // "mapimg6.png");

    WritableImage map1 = grabImage(campusMap, "G", createOutputFile("mapimg1.png"));
    WritableImage map2 = grabImage(floor1Map, "1", createOutputFile("mapimg2.png"));
    WritableImage map3 = grabImage(floor2Map, "2", createOutputFile("mapimg3.png"));
    WritableImage map4 = grabImage(floor3Map, "3", createOutputFile("mapimg4.png"));
    WritableImage map5 = grabImage(floor4Map, "4", createOutputFile("mapimg5.png"));
    WritableImage map6 = grabImage(floor5Map, "5", createOutputFile("mapimg6.png"));
    LinkedList<WritableImage> listOfImages = new LinkedList<>();
    if (pathFloors.contains("G")) {
      listOfImages.add(map1);
    }
    if (pathFloors.contains("1")) {
      listOfImages.add(map2);
    }
    if (pathFloors.contains("2")) {
      listOfImages.add(map3);
    }
    if (pathFloors.contains("3")) {
      listOfImages.add(map4);
    }
    if (pathFloors.contains("4")) {
      listOfImages.add(map5);
    }
    if (pathFloors.contains("5")) {
      listOfImages.add(map6);
    }
    if (listOfImages.isEmpty()) {
      // TODO: Throw a error "you did not do pathfind"
    }
    SharingPageController.setScreenShot(map1, map2, map3, map4, map5, map6);
    // EmailPageController.setScreenShot(listOfImages);
    SwitchScene.goToParent("/Views/EmailPage.fxml");
  }

  public void clearSelection(ActionEvent actionEvent) {
    setNavFalse();

    GRAPH.resetPath();

    resizeCanvas();
    draw();
    directionvbox.getChildren().clear();
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
        selectedNode = null; // when clear Node info, also de-select Node

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
        selectedNode = null;
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
        GRAPH.addEdge(startNodeID.getText(), endNodeID.getText());
        clearEdgeInfo(); // when clear info, de-select Nodes
        selectedNode = null;
        selectedNodeB = null;

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
        clearEdgeInfo(); // when clear info, de-select Nodes
        selectedNode = null;
        selectedNodeB = null;

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
    startNodeID.clear();
    endNodeID.clear();
  }

  /**
   * will upload node data from a CSV
   *
   * @param actionEvent
   */
  public void uploadN(ActionEvent actionEvent) {
    // DataHandling.importExcelData(true);
    // TODO: re-initialize Graph after uploading excel file?
  }

  /**
   * will upload edge data from a CSV
   *
   * @param actionEvent
   */
  public void uploadE(ActionEvent actionEvent) {
    // DataHandling.importExcelData(false);
    // TODO: re-initialize Graph after uploading excel file?
  }

  /**
   * will save current node data to a CSV
   *
   * @param actionEvent
   */
  public void saveN(ActionEvent actionEvent) {
    // DataHandling.save(true);
  }

  /**
   * will save current edge data to a CSV
   *
   * @param actionEvent
   */
  public void saveE(ActionEvent actionEvent) {
    // DataHandling.save(false);
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
    gc.clearRect(0, 0, mapCanvas.getWidth(), mapCanvas.getHeight());

    // i know these can be simplified but i don't care -- this is more organized imo

    if (!editing && !displayingRoute) {
      // draw the visible Node (navigating) on sFloor + highlight start and end (if selected)
      // GRAPH.drawVisibleNodes(sFloor, startNode, endNode, imageView, false);
    } else if (!editing && displayingRoute) {
      // draw the portion on sFloor + highlight start and end
      Polyline path = GRAPH.drawCurrentPath(sFloor, startNode, endNode, imageView, false);

      // stackPane.getChildren().add(path);
      // TODO: fix this if necessary (it's jank rn)
      //      gridPane.setMinWidth(1920);
      //      Group group = new Group(gridPane, path);
      //      Stage stage = Opp.getPrimaryStage();
      //      stage.getScene().setRoot(group);
      //      // stage.setFullScreen(true);
      //      stage.show();

    } else if (editing) {
      // draw ALL the nodes (editing) + highlight selected node (if selected)
      GRAPH.drawAllNodes(sFloor, selectedNode, selectedNodeB, selectingEditNode, imageView, false);
      // and if "show edges" is selected, draw them as well
      if (showingEdges) {
        GRAPH.drawAllEdges(sFloor, selectedNode, selectedNodeB, imageView, false);
      }
    }

    if (!alignList.isEmpty()) {
      for (String s : alignList) {
        Node n = GRAPH.getNodeByID(s);
        DrawHelper.drawSingleNode(gc, n, Color.BLUE, imageView, false);
      }
    }
  }

  // ignore this, BUT DON'T DELETE IT!!!!! ...i have my reasons
  private void draw(int i) {
    // i know these can be simplified but i don't care -- this is more organized
    if (!editing && !displayingRoute) {
      // GRAPH.drawVisibleNodes(sFloor, startNode, endNode, imageView, false);
    } else if (!editing && displayingRoute) {
      GRAPH.drawCurrentPath(sFloor, startNode, endNode, imageView, false);
    } else if (editing) {
      GRAPH.drawAllNodes(sFloor, selectedNode, selectedNodeB, selectingEditNode, imageView, false);
      if (showingEdges) {
        GRAPH.drawAllEdges(sFloor, selectedNode, selectedNodeB, imageView, false);
      }
    }
  }

  //  /**
  //   * automatically generates the edgeID from the start and end node IDs once starting to type in
  // the
  //   * start and end node ID textfields
  //   *
  //   * @param actionEvent
  //   */
  //  public void updateEdgeID(KeyEvent actionEvent) {
  //    edgeID.setText(startNodeID.getText() + "_" + endNodeID.getText());
  //  }

  //  /**
  //   * will update the edge ID once clicking on the edge ID textfield
  //   *
  //   * @param mouseEvent
  //   */
  //  public void updateEdgeIDMouse(MouseEvent mouseEvent) {
  //    edgeID.setText(startNodeID.getText() + "_" + endNodeID.getText());
  //  }

  /**
   * chooses the pathfinding algorithm used
   *
   * @param actionEvent
   */
  public void chooseStrat(ActionEvent actionEvent) {
    strategy = (String) algoStratCBox.getValue();
    Settings.getInstance().setAlgoChoice(strategy);
  }

  /**
   * Drags the node based on user's mouse x and y coords
   *
   * @param mouseEvent
   */
  public void nodeDrag(MouseEvent mouseEvent) {
    if (editing) {
      Node draggedNode =
          GRAPH.closestNode(sFloor, mouseEvent.getX(), mouseEvent.getY(), editing, imageView);

      int x = (int) (getImgX(mouseEvent.getX()));
      int y = (int) (getImgY(mouseEvent.getY()));

      draggedNode.setXCoord(x);
      draggedNode.setYCoord(y);
      draw();
    }
  }

  private void addTextToDirectionBox(String text) {

    Text newText = new Text(text + "\n");
    newText.setFont(Font.font("leelawadee ui", 16.0));
    directionvbox.getChildren().add(newText);
  }

  public void alignVertically(ActionEvent actionEvent) {
    for (String s : alignList) {
      Node n = GRAPH.getNodeByID(s);
      n.setYCoord(selectedNode.getYCoord());
    }

    alignList = new ArrayList<>();
    draw();
  }

  public void alignHorizontally(ActionEvent actionEvent) {
    for (String s : alignList) {
      Node n = GRAPH.getNodeByID(s);
      n.setXCoord(selectedNode.getXCoord());
    }

    alignList = new ArrayList<>();
    draw();
  }

  public void goCamp(ActionEvent actionEvent) {
    if (sFloor.equals("G")) {
      return;
    }
    camp.setStyle("-fx-background-color: #fec107;");
    F1.setStyle("-fx-background-color: #cfe2f3;");
    F2.setStyle("-fx-background-color: #cfe2f3;");
    F3.setStyle("-fx-background-color: #cfe2f3;");
    F4.setStyle("-fx-background-color: #cfe2f3;");
    F5.setStyle("-fx-background-color: #cfe2f3;");

    imageView.setImage(campusMap);
    sFloor = "G";

    currentViewport =
        new Rectangle2D(0, 0, imageView.getImage().getWidth(), imageView.getImage().getHeight());
    imageView.setViewport(currentViewport);
    percImageView = 1.0;

    resizeCanvas();
    draw();
  }

  public void goF1(ActionEvent actionEvent) {
    if (sFloor.equals("1")) {
      return;
    }
    camp.setStyle("-fx-background-color: #cfe2f3;");
    F1.setStyle("-fx-background-color: #fec107;");
    F2.setStyle("-fx-background-color: #cfe2f3;");
    F3.setStyle("-fx-background-color: #cfe2f3;");
    F4.setStyle("-fx-background-color: #cfe2f3;");
    F5.setStyle("-fx-background-color: #cfe2f3;");

    imageView.setImage(floor1Map);
    sFloor = "1";

    currentViewport =
        new Rectangle2D(0, 0, imageView.getImage().getWidth(), imageView.getImage().getHeight());
    imageView.setViewport(currentViewport);
    percImageView = 1.0;

    resizeCanvas();
    draw();
  }

  public void goF2(ActionEvent actionEvent) {
    if (sFloor.equals("2")) {
      return;
    }
    camp.setStyle("-fx-background-color: #cfe2f3;");
    F1.setStyle("-fx-background-color: #cfe2f3;");
    F2.setStyle("-fx-background-color: #fec107;");
    F3.setStyle("-fx-background-color: #cfe2f3;");
    F4.setStyle("-fx-background-color: #cfe2f3;");
    F5.setStyle("-fx-background-color: #cfe2f3;");

    imageView.setImage(floor2Map);
    sFloor = "2";

    currentViewport =
        new Rectangle2D(0, 0, imageView.getImage().getWidth(), imageView.getImage().getHeight());
    imageView.setViewport(currentViewport);
    percImageView = 1.0;

    resizeCanvas();
    draw();
  }

  public void goF3(ActionEvent actionEvent) {
    if (sFloor.equals("3")) {
      return;
    }
    camp.setStyle("-fx-background-color: #cfe2f3;");
    F1.setStyle("-fx-background-color: #cfe2f3;");
    F2.setStyle("-fx-background-color: #cfe2f3;");
    F3.setStyle("-fx-background-color: #fec107;");
    F4.setStyle("-fx-background-color: #cfe2f3;");
    F5.setStyle("-fx-background-color: #cfe2f3;");

    imageView.setImage(floor3Map);
    sFloor = "3";

    currentViewport =
        new Rectangle2D(0, 0, imageView.getImage().getWidth(), imageView.getImage().getHeight());
    imageView.setViewport(currentViewport);
    percImageView = 1.0;

    resizeCanvas();
    draw();
  }

  public void goF4(ActionEvent actionEvent) {
    if (sFloor.equals("4")) {
      return;
    }
    camp.setStyle("-fx-background-color: #cfe2f3;");
    F1.setStyle("-fx-background-color: #cfe2f3;");
    F2.setStyle("-fx-background-color: #cfe2f3;");
    F3.setStyle("-fx-background-color: #cfe2f3;");
    F4.setStyle("-fx-background-color: #fec107;");
    F5.setStyle("-fx-background-color: #cfe2f3;");

    imageView.setImage(floor4Map);
    sFloor = "4";

    currentViewport =
        new Rectangle2D(0, 0, imageView.getImage().getWidth(), imageView.getImage().getHeight());
    imageView.setViewport(currentViewport);
    percImageView = 1.0;

    resizeCanvas();
    draw();
  }

  public void goF5(ActionEvent actionEvent) {
    if (sFloor.equals("5")) {
      return;
    }

    camp.setStyle("-fx-background-color: #cfe2f3;");
    F1.setStyle("-fx-background-color: #cfe2f3;");
    F2.setStyle("-fx-background-color: #cfe2f3;");
    F3.setStyle("-fx-background-color: #cfe2f3;");
    F4.setStyle("-fx-background-color: #cfe2f3;");
    F5.setStyle("-fx-background-color: #fec107;");

    imageView.setImage(floor5Map);
    sFloor = "5";

    currentViewport =
        new Rectangle2D(0, 0, imageView.getImage().getWidth(), imageView.getImage().getHeight());
    imageView.setViewport(currentViewport);
    percImageView = 1.0;

    resizeCanvas();
    draw();
  }

  public void elevatorOnlyClick(ActionEvent actionEvent) {
    Settings.getInstance().setElevatorOnly(elevatorOnlyToggle.isSelected());
  }
}
  /**
   * switches between images and canvases for different floors selected in the combobox
   *
   * @param actionEvent
   */
  /*public void floorSelection(ActionEvent actionEvent) {
    selectedFloor = floorSelectionBtn.getValue();
    //

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

    currentViewport =
            new Rectangle2D(0, 0, imageView.getImage().getWidth(), imageView.getImage().getHeight());
    imageView.setViewport(currentViewport);
    percImageView = 1.0;

    resizeCanvas();
    draw();
  }*/
