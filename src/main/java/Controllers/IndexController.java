package Controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public class IndexController implements Initializable {
  public MenuItem edgeEditorButton;
  public MenuItem nodeEditorButton;
  public MenuItem securityButton;
  public MenuItem maintenanceButton;
  public Button exitButton;
  public Button pathfindingButton;
  public MenuItem loc1Button;
  public MenuItem loc2Button;
  public MenuItem loc3Button;
  public MenuItem dest1Button;
  public MenuItem dest2Button;
  public MenuItem dest3Button;
  public MenuButton menu;
  public Label label;

  // @FXML private Button edgeEditorButton; are these suposed to look like this or what they are
  // now?

  String loc = "node1";
  String dest = "node5";
  boolean selectingLoc = true;
  // Graph testGraph;
  // these variables show which of the three locations/destinations respectivly is currently being
  // tracked
  public ImageView mapimage;
  //the campus image is 2989 x 2457
  public Canvas mapcanvas;
  public Button saveBtn;
  public AnchorPane mapanchor;
  private ArrayList<Circle> circleList;
  GraphicsContext gc;
  
  ObservableList<Controllers.model.Node> nodeList;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    nodeList = FXCollections.observableArrayList();
    nodeList = DatabaseFunctionality.showNodes(nodeList);
    circleList=new ArrayList<>();

    GraphicsContext gc = mapcanvas.getGraphicsContext2D();
    gc.fillRect(5, 5, 5, 5);

    drawNodeCircles(nodeList);
    System.out.println("Initalized");
  }

  public void drawNodeCircles(ObservableList<Controllers.model.Node> nodeList) {
    // divide them by a scale factor (image is ~2937 pixels wide?) --
    // would be imageWidth/canvasWidth and imageHeight/canvasHeight
    double scaleX = 2989 / mapcanvas.getWidth();
    double scaleY = 2457 / mapcanvas.getHeight();
    
    // circle widths:
    double cW = 10.0;

    for (Controllers.model.Node n : nodeList) {
      Circle circle = new Circle();

      double nodeX = Double.valueOf(n.getXCoord()) / scaleX;
      double nodeY = Double.valueOf(n.getYCoord()) / scaleY;
      
      circle.setCenterX(nodeX);
      circle.setCenterY(nodeY);
      circle.setRadius(cW/2);
      circle.setFill(Paint.valueOf("yellow"));
      
      circleList.add(circle);
      
      gc.fillOval(circle.getCenterX() - cW/2, circle.getCenterY() - cW/2, cW, cW);
    }

  }
  /*@Override
  public void initialize() {
    GraphicsContext gc = mapcanvas.getGraphicsContext2D();
    gc.fillRect(5, 5, 5, 5);

    double nodeX = Double.valueOf(nodeList.get(0).getXCoord());
    double nodeY = Double.valueOf(nodeList.get(0).getYCoord());
    System.out.println("Initalized");
  }*/

  public void pathfindingPress(ActionEvent actionEvent) {
    /*AStarSearch aStar = new AStarSearch(testGraph, loc, dest);
    LinkedList<GraphNode> pathNODE = aStar.findRoute();
    LinkedList<String> pathSTRING = new LinkedList<>();

    for (GraphNode node : pathNODE) {
      pathSTRING.add(node.getNodeID());
    }

    label.setText(String.valueOf(pathSTRING));*/
  }

  public void exit(ActionEvent actionEvent) {
    Platform.exit();
  }

  public void goToSecurityRequest(ActionEvent actionEvent) throws IOException {
    System.out.println("Starting Up");
    Parent parent = FXMLLoader.load(getClass().getResource("/Views/SecurityForm.fxml"));
    Scene scene = new Scene(parent);
    // this gets Stage info
    Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    window.setTitle("Edit Nodes");
    // this sets the scene to the new one specified above
    window.setScene(scene);
    window.show();
  }

  public void goToEditNodes(ActionEvent actionEvent) throws IOException {
    System.out.println("Starting Up");
    Parent parent = FXMLLoader.load(getClass().getResource("/Views/EditPage.fxml"));
    Scene scene = new Scene(parent);
    // this gets Stage info
    Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    window.setTitle("Edit Nodes");
    // this sets the scene to the new one specified above
    window.setScene(scene);
    window.show();
  }

  public void save(ActionEvent actionEvent) throws IOException {

    GraphicsContext gc = mapcanvas.getGraphicsContext2D();
    gc.fillRect(5, 5, 5, 5);

    String home = System.getProperty("user.home");
    File outputFile = new File(home + "/Downloads/" + "mapImageThingy.png");

    WritableImage map = mapanchor.snapshot(new SnapshotParameters(), null);
    ImageIO.write(SwingFXUtils.fromFXImage(map, null), "png", outputFile);

    System.out.println("Starting Up");
    Parent parent = FXMLLoader.load(getClass().getResource("/Views/EmailPage.fxml"));
    Scene scene = new Scene(parent);
    // this gets Stage info
    Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    window.setTitle("Share Image");
    // this sets the scene to the new one specified above
    window.setScene(scene);
    window.show();
  }

  public void canvasClick(MouseEvent mouseEvent) {
    double clickx = mouseEvent.getX();
    double clicky = mouseEvent.getY();

    double nodeX = Double.valueOf(nodeList.get(0).getXCoord());
    double nodeY = Double.valueOf(nodeList.get(0).getYCoord());
  }

  public void locClick(ActionEvent actionEvent) {
    selectingLoc = true;
  }

  public void destClick(ActionEvent actionEvent) {
    selectingLoc = false;
  }
}
