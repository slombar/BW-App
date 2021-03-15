package edu.wpi.cs3733.teamO.Controllers.Mobile;

import static edu.wpi.cs3733.teamO.GraphSystem.Graph.*;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXNodesList;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.teamO.HelperClasses.Autocomplete;
import edu.wpi.cs3733.teamO.HelperClasses.PopupMaker;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import edu.wpi.cs3733.teamO.Model.Node;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

public class MobileHospitalNavController implements Initializable {

  @FXML private JFXButton clearBtn;
  @FXML private ImageView imageView;
  @FXML private Canvas mapCanvas;
  @FXML private StackPane stackPane;
  @FXML private JFXNodesList directionsList;
  @FXML private JFXNodesList buttonsList;
  @FXML private JFXNodesList floorList;

  // everything for pathfinding and drawing the hospital map
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

  // floor buttons
  private final JFXButton floorSelectionB = new JFXButton("F", null);
  private final JFXButton floorGB = new JFXButton("G", null);
  private final JFXButton floor1B = new JFXButton("1", null);
  private final JFXButton floor2B = new JFXButton("2", null);
  private final JFXButton floor3B = new JFXButton("3", null);
  private final JFXButton floor4B = new JFXButton("4", null);
  private final JFXButton floor5B = new JFXButton("5", null);

  // components for location textfields
  JFXTextField startLoc = new JFXTextField();
  JFXTextField endLoc = new JFXTextField();
  VBox locBox = new VBox();

  public boolean isFirstStart = false;

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

    // set up for location selection
    ArrayList<String> longNameNodes = Autocomplete.autoNodeData("longName");
    Autocomplete.autoComplete(longNameNodes, startLoc);
    Autocomplete.autoComplete(longNameNodes, endLoc);
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

    floorSelectionB.getStyleClass().addAll("nav-menu-button");
    floorSelectionB.setButtonType(JFXButton.ButtonType.RAISED);
    floorGB.getStyleClass().addAll("floor-boxes");
    floorGB.setButtonType(JFXButton.ButtonType.RAISED);
    floor1B.getStyleClass().addAll("floor-boxes");
    floor1B.setButtonType(JFXButton.ButtonType.RAISED);
    floor2B.getStyleClass().addAll("floor-boxes");
    floor2B.setButtonType(JFXButton.ButtonType.RAISED);
    floor3B.getStyleClass().addAll("floor-boxes");
    floor3B.setButtonType(JFXButton.ButtonType.RAISED);
    floor4B.getStyleClass().addAll("floor-boxes");
    floor4B.setButtonType(JFXButton.ButtonType.RAISED);
    floor5B.getStyleClass().addAll("floor-boxes");
    floor5B.setButtonType(JFXButton.ButtonType.RAISED);

    // add buttons to bottom left animated node list (additional buttons)
    buttonsList.addAnimatedNode(addBtn);
    buttonsList.addAnimatedNode(textBtn);
    buttonsList.addAnimatedNode(parkingBtn);
    buttonsList.addAnimatedNode(exitBtn);
    buttonsList.setSpacing(20);
    buttonsList.setRotate(180);
    buttonsList.setAlignment(Pos.CENTER_LEFT);

    // add buttons to top left animated node list (directions)
    directionsList.addAnimatedNode(directionsBtn);
    directionsList.addAnimatedNode(locBox);
    directionsList.addAnimatedNode(startBtn);
    directionsList.setSpacing(10);
    directionsList.setRotate(0);
    directionsList.setAlignment(Pos.CENTER_RIGHT);

    /** Add to the floor selection* */
    floorList.addAnimatedNode(floorSelectionB);
    floorList.addAnimatedNode(floor5B);
    floorList.addAnimatedNode(floor4B);
    floorList.addAnimatedNode(floor3B);
    floorList.addAnimatedNode(floor2B);
    floorList.addAnimatedNode(floor1B);
    floorList.addAnimatedNode(floorGB);
    floorList.setSpacing(4);
    floorList.setRotate(90);

    buttonFunction(); // adds on action functionality to buttons

    mapCanvas.toFront();
    gc = mapCanvas.getGraphicsContext2D();

    imageView.setImage(campusMap);
    currentViewport = new Rectangle2D(0, 0, campusMap.getWidth(), campusMap.getHeight());
    imageView.setViewport(currentViewport);

    GRAPH.setGraphicsContext(gc);

    buttonsList.toFront();
    directionsList.toFront();
    floorList.toFront();
    clearBtn.toFront();

    selectingStart = false;
    selectingEnd = true;
    pathfindOnActionBtn();
    clearBtn.setButtonType(JFXButton.ButtonType.RAISED);
    //    if (WaitingPageController.isMainEntrance) {
    //      endLoc.setText("Exit 3 Atrium Main Entrance");
    //      endNode = GRAPH.getNodeByLongName(endLoc.getText());
    //      isFirstStart = true;
    //    } else {
    //      endLoc.setText("Exit 2 Emergency Entrance");
    //      endNode = GRAPH.getNodeByLongName(endLoc.getText());
    //      isFirstStart = true;
    //    }
  }

  /** adding on action functionality to the buttons in the JFXNodeslist */
  private void buttonFunction() {
    parkingBtn.setOnAction(
        actionEvent -> {
          SwitchScene.goToParentMobile("/Views/MobileApp/SaveParking.fxml", actionEvent);
        });

    textBtn.setOnAction(
        actionEvent -> {
          MainScreenController.isBackGoogle = false;
          SwitchScene.goToParentMobile("/Views/MobileApp/MobileDirections.fxml", actionEvent);
        });

    exitBtn.setOnAction(
        actionEvent -> {
          SwitchScene.goToParentMobile(
              "/RevampedViews/MobileApp/MainMobileScreen.fxml", actionEvent);
        });

    /** Floor onactions* */
    floorGB.setOnAction(
        e -> {
          imageView.setImage(campusMap);
          setMapViewDraw("G");
        });
    floor1B.setOnAction(
        e -> {
          imageView.setImage(floor1Map);
          setMapViewDraw("1");
        });
    floor2B.setOnAction(
        e -> {
          imageView.setImage(floor2Map);
          setMapViewDraw("2");
        });
    floor3B.setOnAction(
        e -> {
          imageView.setImage(floor3Map);
          setMapViewDraw("3");
        });
    floor4B.setOnAction(
        e -> {
          imageView.setImage(floor4Map);
          setMapViewDraw("4");
        });
    floor5B.setOnAction(
        e -> {
          imageView.setImage(floor5Map);
          setMapViewDraw("5");
        });
  }

  /** Resizes Canvas to be the current size of the Image */
  public void resizeCanvas() {
    mapCanvas.heightProperty().setValue(1000);
    mapCanvas.widthProperty().setValue(1000);

    mapCanvas.heightProperty().setValue(imageView.getBoundsInParent().getHeight());
    mapCanvas.widthProperty().setValue(imageView.getBoundsInParent().getWidth());
  }

  /** resets path and creates a new path depending on start and end nodes */
  public void doPathfind() {
    if (startNode != null && endNode != null) {
      GRAPH.resetPath();
      GRAPH.findPath("A*", startNode, endNode);
      displayingRoute = true;
      selectingStart = false;
      selectingEnd = false;
    } else if (!startLoc.getText().isEmpty() && !endLoc.getText().isEmpty()) {
      startNode = GRAPH.getNodeByLongName(startLoc.getText());
      endNode = GRAPH.getNodeByLongName(endLoc.getText());
      GRAPH.resetPath();
      GRAPH.findPath("A*", startNode, endNode);
      displayingRoute = true;
      selectingStart = false;
      selectingEnd = false;
    } else {
      PopupMaker.invalidLocationMobile(stackPane);
    }

    draw();
    pathFloors = "";
    for (Node n : GRAPH.getPath()) {
      if (!pathFloors.contains(n.getFloor())) pathFloors += n.getFloor();
    }
  }

  public void pathfindOnActionBtn() {
    startBtn.setOnAction(
        actionEvent -> {
          doPathfind();
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
        startLoc.setText(startNode.getLongName());
        if (isFirstStart) {
          isFirstStart = false;
          doPathfind();
        }
      } else if (selectingEnd) {
        endNode = clickedNode;
        endLoc.setText(endNode.getLongName());
        doPathfind();
      }
      draw();
    }
    // ----------------------
    // block for RIGHT CLICK
    else if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
      draw();
    }

    System.out.println("mapCanvas click");
  }

  public void onCanvasScroll(ScrollEvent scrollEvent) {
    double scrollDeltaY = scrollEvent.getDeltaY();
    // if scroll is at least a certain amount, then zoom (idk, maybe change this??)
    if (Math.abs(scrollDeltaY) > 10) {
      // if positive, then scrolling up (zooming in)
      if (scrollDeltaY > 0) {
        // selina - this is where you change how far you can zoom in (i lowered it but just letting
        // you know)
        if (percImageView <= 0.1) {
          return;
        } else {
          percImageView -= 0.05;
        }

      }
      // else, scrolling down (zooming out)
      else {
        if (percImageView >= 2.0) {
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
    /*currentViewport =
    new Rectangle2D(
        (a * (1 - percImageView) + imageView.getImage().getWidth() * 0.5 * percImageView)
            - vX / 2,
        (b * (1 - percImageView) + imageView.getImage().getHeight() * 0.5 * percImageView)
            - vY / 2,
        vX,
        vY);*/
    // zoom option B:
    double percCanvasA = scrollEvent.getX() / mapCanvas.getWidth();
    double percCanvasB = scrollEvent.getY() / mapCanvas.getHeight();
    currentViewport = new Rectangle2D(a - (percCanvasA * vX), b - (percCanvasB * vY), vX, vY);
    // i felt like option B actually feels a lot better with the much smaller screen for the mobile
    // version
    // but obviously it can be changed back to option A if wanted
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
      GRAPH.drawVisibleNodes(sFloor, startNode, endNode, imageView, true);
    } else if (displayingRoute) {
      // draw the portion on sFloor + highlight start and end
      GRAPH.drawCurrentPath(sFloor, startNode, endNode, imageView, true);
    }
  }

  /**
   * clear button to clear path
   *
   * @param actionEvent
   */
  public void clearPath(ActionEvent actionEvent) {
    setNavFalse();

    GRAPH.resetPath();

    resizeCanvas();
    draw();
  }

  public void setMapViewDraw(String floorSelected) {
    if (sFloor.equals(floorSelected)) {
      return;
    }
    sFloor = floorSelected;
    floorSelectionB.setText(floorSelected);

    currentViewport =
        new Rectangle2D(0, 0, imageView.getImage().getWidth(), imageView.getImage().getHeight());
    imageView.setViewport(currentViewport);
    percImageView = 1.0;

    resizeCanvas();
    draw();
  }
}
