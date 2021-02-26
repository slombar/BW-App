package edu.wpi.cs3733.teamO.Controllers;

import com.jfoenix.controls.*;
import edu.wpi.teamO.Controllers.model.Node;
import edu.wpi.teamO.GraphSystem.GraphSystem;
import edu.wpi.teamO.HelperClasses.PopupMaker;
import edu.wpi.teamO.Opp;
import edu.wpi.cs3733.teamO.Controllers.model.Node;
import edu.wpi.cs3733.teamO.GraphSystem.GraphSystem;
import edu.wpi.cs3733.teamO.GraphSystem.Pathfinding;
import edu.wpi.cs3733.teamO.Opp;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
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
  public StackPane stackPane;
  @FXML private AnchorPane bigAnchor;
  public JFXButton locButton;
  public JFXButton destButton;
  public JFXButton resetButton;
  @FXML private StackPane sharePane;

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
    Pathfinding.drawNodeCircles();
    customizeButtons();
    sharePane.toFront();

    //Sam has been using the following line for testing, if it is not commented, it is his fault, blame him for all errors in code from now on
    //PopupMaker.incompletePopup(stackPane);

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

  public void pathfindingPress(ActionEvent actionEvent) {
    // make new graph system and get the path
    GraphSystem gs = new GraphSystem();
    LinkedList<String> pathSTRING = gs.findPath(loc, dest);

    // then clear canvas and draw given path
    gc.clearRect(0, 0, mapcanvas.getWidth(), mapcanvas.getHeight());
    Pathfinding.drawPath(pathSTRING);
  }

  private static final double arrowLength = 6;
  private static final double arrowWidth = 4;
  private static final double minArrowDistSq = 108;
  // ^ do the dist you wanted squared (probably want 3*(arrowLength^2))

  // draws an arrow from a to b, with the arrowhead halfway between them

  public void exit(ActionEvent actionEvent) {
    Platform.exit();
  }

  public void goToSecurityRequest(ActionEvent actionEvent) throws IOException {
    // addNodePopup has the content of the popup
    // addNodeDialog creates the dialog popup
    JFXDialogLayout addNodePopup = new JFXDialogLayout();
    addNodePopup.setHeading(new Text("Security Form"));
    VBox addSecVBox = new VBox(12);

    // Creating contents of form
    HBox buttonBox = new HBox(20);
    JFXButton closeButton = new JFXButton("Close");
    JFXButton clearButton = new JFXButton("Clear");
    JFXButton submitButton = new JFXButton("Submit");
    buttonBox.getChildren().addAll(closeButton, clearButton, submitButton);

    JFXTextField locationText = new JFXTextField();
    locationText.setPromptText("Where is security needed?");
    JFXTextField threatText = new JFXTextField();
    threatText.setPromptText("What type of threat?");
    JFXTextArea additionalInfo = new JFXTextArea();
    additionalInfo.setPromptText("Additional Important Information");
    HBox sliderBox = new HBox(20);
    Label sliderLabel = new Label("Urgency Level:");
    JFXSlider slider = new JFXSlider();
    slider.setMin(1);
    slider.setValue(1);
    slider.setMax(5);
    slider.setMajorTickUnit(1);
    slider.setMinorTickCount(0);
    slider.setShowTickLabels(true);
    slider.setShowTickMarks(true);
    slider.setSnapToTicks(true);
    slider.setBlockIncrement(1);
    sliderBox.getChildren().addAll(sliderLabel, slider);
    HBox checkboxBox = new HBox(20);
    Label checkLabel = new Label("Armed Security?");
    JFXCheckBox checkbox = new JFXCheckBox();
    checkboxBox.getChildren().addAll(checkLabel, checkbox);

    // Creating the form with a VBox
    addSecVBox
        .getChildren()
        .addAll(locationText, threatText, additionalInfo, sliderBox, checkboxBox, buttonBox);
    addNodePopup.setBody(addSecVBox);

    // Bringing the popup screen to the front and disabling the background
    stackPane.toFront();
    JFXDialog addNodeDialog =
        new JFXDialog(stackPane, addNodePopup, JFXDialog.DialogTransition.BOTTOM);
    addNodeDialog.setOverlayClose(false);

    // Closing the popup
    closeButton.setOnAction(
        event -> {
          addNodeDialog.close();
          stackPane.toBack();
        });
    // Clearing the form, keeps the popup open
    clearButton.setOnAction(
        event -> {
          locationText.clear();
          threatText.clear();
          additionalInfo.clear();
          slider.setValue(1);
          checkbox.setSelected(false);
        });
    // Submits addition to the database
    submitButton.setOnAction(
        event -> {
          addNodeDialog.close();
          stackPane.toBack();
        });
    addNodeDialog.show();
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

  // private ImageView m;

  public void save(ActionEvent actionEvent) throws IOException {
    sharePane.toBack();
    GraphicsContext gc = mapcanvas.getGraphicsContext2D();

    String home = System.getProperty("user.home");
    File outputFile = new File(home + "/Downloads/" + "mapimg.png");

    WritableImage map = mapanchor.snapshot(new SnapshotParameters(), null);
    ImageIO.write(SwingFXUtils.fromFXImage(map, null), "png", outputFile);
    Image newimg = map;

    EmailPageController.setScreenShot(newimg);

    // add the scene switch
    AnchorPane root = FXMLLoader.load(getClass().getResource("/Views/EmailPage.fxml"));
    Opp.getPrimaryStage().getScene().setRoot(root);
  }

  public void canvasClick(MouseEvent mouseEvent) {
    double clickX = mouseEvent.getX();
    double clickY = mouseEvent.getY();
    System.out.println("CANVAS CLICKING");
    String closestID = Pathfinding.closestCircle(clickX, clickY);
    // System.out.println(closestID); // for debugging

    if (selectingLoc) {
      loc = closestID;
    } else {
      dest = closestID;
    }

    // clear canvas and redraw circles
    gc.clearRect(0, 0, mapcanvas.getWidth(), mapcanvas.getHeight());
    Pathfinding.drawNodeCircles();
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
    Pathfinding.drawNodeCircles();
  }
}
