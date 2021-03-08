package edu.wpi.cs3733.teamO.Controllers.Mobile;

import static edu.wpi.cs3733.teamO.GraphSystem.Graph.*;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXNodesList;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.teamO.HelperClasses.PopupMaker;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import edu.wpi.cs3733.teamO.Model.Node;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

public class MobileHospitalNavController implements Initializable {
  @FXML private JFXButton clearBtn;
  @FXML private JFXComboBox<String> floorSelectionBtn;
  @FXML private ImageView imageView;
  @FXML private Canvas mapCanvas;
  @FXML private StackPane stackPane;
  @FXML private JFXNodesList directionsList;
  @FXML private JFXNodesList buttonsList;

  private GraphicsContext gc;
  private double percImageView = 1.0;
  private Rectangle2D currentViewport;
  private String selectedFloor = "Campus";
  private String sFloor = "G";
  private String sideMenuUrl;
  private String pathFloors = "";

  Node startNode = null;
  Node endNode = null;
  Node selectedNode = null;
  Node selectedNodeB = null;

  ObservableList<String> listOfFloors =
      FXCollections.observableArrayList(
          "Campus", "Floor 1", "Floor 2", "Floor 3", "Floor 4", "Floor 5");

  // navigating bools:
  private boolean selectingStart = false;
  private boolean selectingEnd = false;
  private boolean displayingRoute = false;

  private void setNavFalse() {
    selectingStart = false;
    selectingEnd = true;
    displayingRoute = false;
    startNode = null;
    endNode = null;
  }

  // creating icons for buttons
  Image addIcon = new Image(getClass().getResourceAsStream("/Icons/addBlack.png"));
  ImageView addIconView = new ImageView(addIcon);
  Image parkingIcon = new Image(getClass().getResourceAsStream("/Icons/parkingBlack.png"));
  ImageView parkingIconView = new ImageView(parkingIcon);
  Image exitIcon = new Image(getClass().getResourceAsStream("/Icons/exitBlack.png"));
  ImageView exitIconView = new ImageView(exitIcon);
  Image navIcon = new Image(getClass().getResourceAsStream("/Icons/navIconBlack.png"));
  ImageView navIconView = new ImageView(navIcon);
  Image startIcon = new Image(getClass().getResourceAsStream("/Icons/arrowIconBlack.png"));
  ImageView startIconView = new ImageView(startIcon);
  Image textIcon = new Image(getClass().getResourceAsStream("/Icons/bookIconBlack.png"));
  ImageView textIconView = new ImageView(textIcon);

  // adding icons to buttons
  private final JFXButton addBtn = new JFXButton(null, addIconView);
  private final JFXButton parkingBtn = new JFXButton("Save Parking Spot", parkingIconView);
  private final JFXButton exitBtn = new JFXButton("Exit Navigation", exitIconView);
  private final JFXButton directionsBtn = new JFXButton(null, navIconView);
  private final JFXButton startBtn = new JFXButton("Start Navigation", startIconView);
  private final JFXButton textBtn = new JFXButton("Text Directions", textIconView);

  JFXTextField startLoc = new JFXTextField();
  JFXTextField endLoc = new JFXTextField();

  HBox hbox = new HBox();
  VBox locBox = new VBox();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // set the icons sizes
    addIconView.setFitWidth(15);
    addIconView.setFitHeight(15);
    parkingIconView.setFitWidth(25);
    parkingIconView.setFitHeight(25);
    exitIconView.setFitWidth(20);
    exitIconView.setFitHeight(20);
    navIconView.setFitWidth(15);
    navIconView.setFitHeight(15);
    startIconView.setFitWidth(15);
    startIconView.setFitHeight(15);
    textIconView.setFitWidth(25);
    textIconView.setFitHeight(25);

    // set prompt text
    startLoc.setPromptText("Start Location");
    endLoc.setPromptText("Destination");
    locBox.getChildren().addAll(startLoc, endLoc);
    locBox.setPadding(new Insets(5, 15, 15, 15));

    // style the buttons
    addBtn.getStyleClass().addAll("nav-menu-button");
    addBtn.setButtonType(JFXButton.ButtonType.RAISED);
    parkingBtn.getStyleClass().addAll("nav-buttons");
    parkingBtn.setButtonType(JFXButton.ButtonType.RAISED);
    exitBtn.getStyleClass().addAll("nav-buttons");
    exitBtn.setButtonType(JFXButton.ButtonType.RAISED);
    directionsBtn.getStyleClass().addAll("nav-menu-button");
    directionsBtn.setButtonType(JFXButton.ButtonType.RAISED);
    textBtn.getStyleClass().addAll("nav-buttons");
    textBtn.setButtonType(JFXButton.ButtonType.RAISED);
    startBtn.getStyleClass().addAll("nav-buttons");
    locBox.getStyleClass().addAll("nav-text");

    // add them to be in an animated node list
    buttonsList.addAnimatedNode(addBtn);
    buttonsList.addAnimatedNode(textBtn);
    buttonsList.addAnimatedNode(parkingBtn);
    buttonsList.addAnimatedNode(exitBtn);
    buttonsList.setSpacing(20);
    buttonsList.setRotate(180);
    buttonsList.setAlignment(Pos.CENTER_RIGHT);

    directionsList.addAnimatedNode(directionsBtn);
    directionsList.addAnimatedNode(locBox);
    directionsList.addAnimatedNode(startBtn);
    directionsList.setSpacing(10);
    directionsList.setRotate(0);
    directionsList.setAlignment(Pos.CENTER_RIGHT);
    buttonFunction();

    floorSelectionBtn.setItems(listOfFloors);
    floorSelectionBtn.setValue("Campus");

    mapCanvas.toFront();
    gc = mapCanvas.getGraphicsContext2D();

    imageView.setImage(campusMap);
    currentViewport = new Rectangle2D(0, 0, campusMap.getWidth(), campusMap.getHeight());
    imageView.setViewport(currentViewport);

    GRAPH.setGraphicsContext(gc);

    buttonsList.toFront();
    directionsList.toFront();

    selectingStart = false;
    selectingEnd = true;
    doPathfind();
    clearBtn.setButtonType(JFXButton.ButtonType.RAISED);
  }

  /** adding on action functionality to the buttons in the JFXNodeslist */
  private void buttonFunction() {
    parkingBtn.setOnAction(
        actionEvent -> {
          // TODO: save parking spot
        });

    textBtn.setOnAction(
        actionEvent -> {
          // page of just text directions
          MainScreenController.isBackGoogle = false;
          SwitchScene.goToParentMobile("/Views/MobileApp/MobileDirections.fxml", actionEvent);
        });

    exitBtn.setOnAction(
        actionEvent -> {
          SwitchScene.goToParentMobile("/Views/MobileApp/MainScreen.fxml", actionEvent);
        });
  }

  /** Resizes Canvas to be the current size of the Image */
  public void resizeCanvas() {
    mapCanvas.heightProperty().setValue(1000);
    mapCanvas.widthProperty().setValue(1000);

    mapCanvas.heightProperty().setValue(imageView.getBoundsInParent().getHeight());
    mapCanvas.widthProperty().setValue(imageView.getBoundsInParent().getWidth());
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

    currentViewport =
        new Rectangle2D(0, 0, imageView.getImage().getWidth(), imageView.getImage().getHeight());
    imageView.setViewport(currentViewport);
    percImageView = 1.0;

    resizeCanvas();
    draw();
  }

  /** resets path and creates a new path depending on start and end nodes */
  public void doPathfind() {
    startBtn.setOnAction(
        actionEvent -> {
          if (startNode != null && endNode != null) {
            GRAPH.resetPath();
            GRAPH.findPath("A*", startNode, endNode);
            displayingRoute = true;
            selectingStart = false;
            selectingEnd = false;
          } else {
            PopupMaker.invalidPathfind(stackPane);
          }

          draw();
          pathFloors = "";
          for (Node n : GRAPH.getPath()) {
            if (!pathFloors.contains(n.getFloor())) pathFloors += n.getFloor();
          }
        });
  }

  /**
   * determines closest node to mouse click on canvas, used for both navigating and editing the map
   *
   * @param mouseEvent
   */
  public void canvasClick(MouseEvent mouseEvent) {
    selectingStart = !selectingStart;
    selectingEnd = !selectingEnd;
    Node clickedNode =
        GRAPH.closestNode(sFloor, mouseEvent.getX(), mouseEvent.getY(), false, imageView);

    // ----------------------
    // block for LEFT CLICK
    if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
      selectedNodeB = null;
      Circle c = null;
      Node n = null;

      if (selectingStart) {
        startNode = clickedNode;
      } else if (selectingEnd) {
        endNode = clickedNode;
      }
      draw();
    }
    // ----------------------
    // block for RIGHT CLICK
    else if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
      draw();

      /**
       * If the middle mouse is pressed, we want the node to be dragged with the user's cursor. Once
       * it is pressed up, we will drop the node in that location
       */
    } else if (mouseEvent.getButton().equals(MouseButton.MIDDLE)) {

      Node draggedNode =
          GRAPH.closestNode(sFloor, mouseEvent.getX(), mouseEvent.getY(), false, imageView);

      Circle draggedCircle;
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
    // TODO: don't need this -- want to use other methods below since those accommodate zooming
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

  /** can draw the path, nodes, and edges based on booleans */
  private void draw() {
    resizeCanvas();

    // i know these can be simplified but i don't care -- this is more organized imo

    if (!displayingRoute) {
      // draw the visible Node (navigating) on sFloor + highlight start and end (if selected)
      GRAPH.drawVisibleNodes(sFloor, startNode, endNode, imageView);
    } else if (displayingRoute) {
      // draw the portion on sFloor + highlight start and end
      GRAPH.drawCurrentPath(sFloor, startNode, endNode, imageView);
    }
  }

  public void clearPath(ActionEvent actionEvent) {
    setNavFalse();

    GRAPH.resetPath();

    resizeCanvas();
    draw();
  }
}
