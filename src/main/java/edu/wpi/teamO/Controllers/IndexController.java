package edu.wpi.teamO.Controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.teamO.Controllers.model.Node;
import edu.wpi.teamO.GraphSystem.GraphSystem;
import edu.wpi.teamO.Opp;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javax.imageio.ImageIO;

// TODO: make all these private
public class IndexController implements Initializable {
  public MenuItem edgeEditorButton;
  public MenuItem nodeEditorButton;
  public JFXButton securityButton;
  public MenuItem maintenanceButton;
  public JFXButton exitButton;
  public JFXButton pathfindingButton;
  public MenuItem loc1Button;
  public MenuItem loc2Button;
  public MenuItem loc3Button;
  public MenuItem dest1Button;
  public MenuItem dest2Button;
  public MenuItem dest3Button;
  public MenuButton menu;
  public Label label;
  public JFXButton editButton;
  @FXML private StackPane popupPane;
  @FXML private AnchorPane bigAnchor;
  public JFXButton locButton;
  public JFXButton destButton;
  public JFXButton resetButton;
  public StackPane sharePane;

  // @FXML private Button edgeEditorButton; are these suposed to look like this or what they are
  // now?

  String loc = "-1";
  String dest = "-1";
  boolean selectingLoc = true;
  // Graph testGraph;
  // these variables show which of the three locations/destinations respectively is currently being
  // tracked
  public ImageView mapimage;
  // the campus image is 2989 x 2457
  public Canvas mapcanvas;
  public JFXButton saveBtn;
  public AnchorPane mapanchor;
  private ObservableList<Node> nodeList = FXCollections.observableArrayList();
  private Hashtable<String, Circle> stringCircleHashtable;
  private GraphicsContext gc;
  double cW = 10.0;

  public static final Image bwLogo =
      new Image("Brigham_and_Womens_Hospital_logo.png", 116, 100, true, true);

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    nodeList = DatabaseFunctionality.showNodes(nodeList);
    stringCircleHashtable = new Hashtable<>();

    //////////////////////////////////// SCALING//////////////////////////////////

    double scale = 1.75;
    mapimage.setScaleX(scale);
    mapimage.setScaleY(scale);
    mapcanvas.setScaleX(scale);
    mapcanvas.setScaleY(scale);
    mapimage.setTranslateX(270);
    mapcanvas.setTranslateX(270);
    mapimage.setTranslateY(225);
    mapcanvas.setTranslateY(225);
    mapcanvas.toFront();

    /////////////////////////////////////////////////////////////////////

    gc = mapcanvas.getGraphicsContext2D();

    // draws circles on canvas
    drawNodeCircles();
    customizeButtons();
    sharePane.toFront();

    System.out.println("Initalized");
  }

  // Customize buttons
  private void customizeButtons() {
    // #b4d1ed is pretty nice too
    editButton.setStyle("-fx-background-color: #c3d6e8");
    securityButton.setStyle("-fx-background-color: #c3d6e8");
    exitButton.setStyle("-fx-background-color: #c3d6e8");
    locButton.setStyle("-fx-background-color: #c3d6e8");
    destButton.setStyle("-fx-background-color: #c3d6e8");
    resetButton.setStyle("-fx-background-color: #c3d6e8");
    pathfindingButton.setStyle("-fx-background-color: #c3d6e8");
    saveBtn.setStyle("-fx-background-color: #ffffff");
  }

  /** draws the circles on the canvas */
  public void drawNodeCircles(/*ObservableList<Node> nodeList*/ ) {
    // divide them by a scale factor (image is ~2937 pixels wide?) --
    // would be imageWidth/canvasWidth and imageHeight/canvasHeight
    double scaleX = 2989 / mapcanvas.getWidth();
    double scaleY = 2457 / mapcanvas.getHeight();

    // circle widths:
    // double cW = 10.0;
    // TODO: (x,y) should already adjust when scrolling, but probably should also change radius

    // for each node in the DB, add their circle to the map
    for (Node n : nodeList) {
      double difference = 0;
      Circle circle = new Circle();
      /*add functionality to make the route path
      go left... go right?
       */

      double nodeX = Double.valueOf(n.getXCoord()) / scaleX;
      double nodeY = Double.valueOf(n.getYCoord()) / scaleY;

      circle.setCenterX(nodeX);
      circle.setCenterY(nodeY);
      circle.setRadius(cW / 2);
      stringCircleHashtable.put(n.getID(), circle);

      gc.setFill(Color.YELLOW); // default nodes are yellow
      gc.setGlobalAlpha(.75); // will make things drawn slightly transparent (if we want to)
      // DON'T DELETE -> JUST SET TO "1.0" IF NO TRANSPARENCY IS WANTED

      // sets color to blue/red if loc or dest are selected
      if (!loc.equals("-1") && n.getID().equals(loc)) {
        gc.setFill(Color.BLUE);
      }
      if (!dest.equals("-1") && n.getID().equals(dest)) {
        gc.setFill(Color.RED);
      }

      // create the circle utilizing the algorithm
      gc.fillOval(circle.getCenterX() - cW / 2, circle.getCenterY() - cW / 2, cW, cW);

      // sets alpha to 1.0 and draw a black border around circle
      gc.setGlobalAlpha(1.0);
      gc.strokeOval(circle.getCenterX() - cW / 2, circle.getCenterY() - cW / 2, cW, cW);

      circle.getCenterX();
      circle.getCenterY();
    }

    // draws the BW logo and text
    gc.setFill(Color.WHITE);
    gc.setGlobalAlpha(.75);
    gc.drawImage(bwLogo, 0, 0);
    gc.fillRect(87, -5, 350, 45);
    gc.strokeText("Brigham and Women's Faulkner Hospital Campus", 90, 30);
  }

  public void pathfindingPress(ActionEvent actionEvent) {
    // make new graph system and get the path
    GraphSystem gs = new GraphSystem();
    LinkedList<String> pathSTRING = gs.findPath(loc, dest);

    // then clear canvas and draw given path
    gc.clearRect(0, 0, mapcanvas.getWidth(), mapcanvas.getHeight());
    drawPath(pathSTRING);
  }

  // helper that actually draws the provided path
  public void drawPath(LinkedList<String> path) {
    // want to just draw start and end nodes, then draw lines (will be arrows eventually)
    // TODO: should probably make a drawCircle(), singular, helper at some point
    Circle locC = stringCircleHashtable.get(loc);
    Circle destC = stringCircleHashtable.get(dest);

    // draw start node and outline
    gc.setGlobalAlpha(0.75); // TODO: should make alpha a variable at some point
    gc.setFill(Color.BLUE);
    gc.fillOval(locC.getCenterX() - cW / 2, locC.getCenterY() - cW / 2, cW, cW);
    gc.setGlobalAlpha(1.0);
    gc.strokeOval(locC.getCenterX() - cW / 2, locC.getCenterY() - cW / 2, cW, cW);

    // draw dest node and outline
    gc.setGlobalAlpha(0.75);
    gc.setFill(Color.RED);
    gc.fillOval(destC.getCenterX() - cW / 2, destC.getCenterY() - cW / 2, cW, cW);
    gc.setGlobalAlpha(1.0);
    gc.strokeOval(destC.getCenterX() - cW / 2, destC.getCenterY() - cW / 2, cW, cW);

    gc.setGlobalAlpha(1.0); // makes the lines fully opaque
    for (int i = 0; i < path.size() - 1; i++) {
      Circle a = stringCircleHashtable.get(path.get(i));
      Circle b = stringCircleHashtable.get(path.get(i + 1));

      // gc.strokeLine(a.getCenterX(), a.getCenterY(), b.getCenterX(), b.getCenterY());
      drawMidArrow(a.getCenterX(), a.getCenterY(), b.getCenterX(), b.getCenterY());
    }
  }

  private static final double arrowLength = 6;
  private static final double arrowWidth = 4;
  private static final double minArrowDistSq = 108;
  // ^ do the dist you wanted squared (probably want 3*(arrowLength^2))

  // draws an arrow from a to b, with the arrowhead halfway between them
  public void drawMidArrow(double ax, double ay, double bx, double by) {

    double distSq = Math.pow(Math.abs(ax - bx), 2.0) + Math.pow(Math.abs(ay - by), 2.0);

    if (distSq >= minArrowDistSq) {
      double cx = (ax + bx) / 2;
      double cy = (ay + by) / 2;

      double hypot = Math.hypot(ax - cx, ay - cy);
      double factor = arrowLength / hypot;
      double factorO = arrowWidth / hypot;

      // part in direction of main line
      double dx = (ax - cx) * factor;
      double dy = (ay - cy) * factor;

      // part orthogonal to main line
      double ox = (ax - cx) * factorO;
      double oy = (ay - cy) * factorO;

      double arrow1startX = (cx + dx - oy);
      double arrow1startY = (cy + dy + ox);
      double arrow2startX = (cx + dx + oy);
      double arrow2startY = (cy + dy - ox);

      gc.strokeLine(arrow1startX, arrow1startY, cx, cy);
      gc.strokeLine(arrow2startX, arrow2startY, cx, cy);
    }

    gc.strokeLine(ax, ay, bx, by);
  }

  public void exit(ActionEvent actionEvent) {
    Platform.exit();
  }

  public void goToSecurityRequest(ActionEvent actionEvent) throws IOException {
    // add the scene switch
    AnchorPane root = FXMLLoader.load(getClass().getResource("/Views/SecurityForm.fxml"));
    Opp.getPrimaryStage().setFullScreen(false);
    Opp.getPrimaryStage().getScene().setRoot(root);
  }

  public void goToEditNodes(ActionEvent actionEvent) throws IOException {
    // add the scene switch
    try {
      AnchorPane root = FXMLLoader.load(getClass().getResource("/Views/EditPage.fxml"));
      Opp.getPrimaryStage().getScene().setRoot(root);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public void save(ActionEvent actionEvent) throws IOException {
    sharePane.toBack();
    GraphicsContext gc = mapcanvas.getGraphicsContext2D();

    String home = System.getProperty("user.home");
    File outputFile = new File(home + "/Downloads/" + "mapImageThingy.png");

    WritableImage map = mapanchor.snapshot(new SnapshotParameters(), null);
    ImageIO.write(SwingFXUtils.fromFXImage(map, null), "png", outputFile);

    // add the scene switch
    AnchorPane root = FXMLLoader.load(getClass().getResource("/Views/EmailPage.fxml"));
    // errors TODO: fix
    Opp.getPrimaryStage().getScene().setRoot(root);
  }

  /**
   * sdfgh
   *
   * @param mouseEvent
   */
  public void canvasClick(MouseEvent mouseEvent) {
    double clickX = mouseEvent.getX();
    double clickY = mouseEvent.getY();
    System.out.println("CANVAS CLICKING");
    String closestID = closestCircle(clickX, clickY);
    // System.out.println(closestID); // for debugging

    if (selectingLoc) {
      loc = closestID;
    } else {
      dest = closestID;
    }

    // clear canvas and redraw circles
    gc.clearRect(0, 0, mapcanvas.getWidth(), mapcanvas.getHeight());
    drawNodeCircles();
  }

  // helper that return nodeID of closest node to click
  public String closestCircle(double x, double y) {
    double currentDist = 1000000000;
    String nodeID = "-1";

    for (Node n : nodeList) {
      Circle c = stringCircleHashtable.get(n.getID());
      double cX = c.getCenterX();
      double cY = c.getCenterY();

      double dist = Math.pow(Math.abs(x - cX), 2.0) + Math.pow(Math.abs(y - cY), 2.0);
      if (dist < currentDist) {
        currentDist = dist;
        nodeID = n.getID();
      }
    }

    // dummy return
    return nodeID;
  }

  public void locClick(ActionEvent actionEvent) {
    selectingLoc = true;
  }

  public void destClick(ActionEvent actionEvent) {
    selectingLoc = false;
  }

  public void resetClick(ActionEvent actionEvent) {
    gc.clearRect(0, 0, mapcanvas.getWidth(), mapcanvas.getHeight());
    loc = "-1";
    dest = "-1";
    drawNodeCircles();
  }
}
