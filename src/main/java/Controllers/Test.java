package Controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class Test {
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
  // Graph testGraph;
  // these variables show which of the three locations/destinations respectivly is currently being
  // tracked
  public ImageView mapImage;

  public void initialize() {
    mapImage.setImage(new Image("PartCTestGraph.jpeg"));

    /*GraphNode n1 = new GraphNode("node1", 1, 1);
    GraphNode n2 = new GraphNode("node2", 2, 1);
    GraphNode n3 = new GraphNode("node3", 4, 1);
    GraphNode n4 = new GraphNode("node4", 3, 2);
    GraphNode n5 = new GraphNode("node5", 1, 3);
    GraphNode n6 = new GraphNode("node6", 2, 4);
    GraphNode n7 = new GraphNode("node7", 4, 4);

    n1.addNeighbour(n2);
    n1.addNeighbour(n5);
    n2.addNeighbour(n3);
    n2.addNeighbour(n4);
    n2.addNeighbour(n5);
    n3.addNeighbour(n4);
    n4.addNeighbour(n7);
    n5.addNeighbour(n6);
    n6.addNeighbour(n7);

    testGraph = new Graph();
    testGraph.addNode(n1);
    testGraph.addNode(n2);
    testGraph.addNode(n3);
    testGraph.addNode(n4);
    testGraph.addNode(n5);
    testGraph.addNode(n6);
    testGraph.addNode(n7);*/
  }

  public void pathfindingPress(ActionEvent actionEvent) {
    /*AStarSearch aStar = new AStarSearch(testGraph, loc, dest);
    LinkedList<GraphNode> pathNODE = aStar.findRoute();
    LinkedList<String> pathSTRING = new LinkedList<>();

    for (GraphNode node : pathNODE) {
      pathSTRING.add(node.getNodeID());
    }

    label.setText(String.valueOf(pathSTRING));*/
  }

  public void loc1Press(ActionEvent actionEvent) {
    loc = "node1";
  }

  public void loc2Press(ActionEvent actionEvent) {
    loc = "node2";
  }

  public void loc3press(ActionEvent actionEvent) {
    loc = "node3";
  }

  public void dest1Press(ActionEvent actionEvent) {
    dest = "node5";
  }

  public void dest2Press(ActionEvent actionEvent) {
    dest = "node6";
  }

  public void dest3Press(ActionEvent actionEvent) {
    dest = "node7";
  }

  // after any of these the loc/destDot should be moved so they can check if its right. The line
  // doesn't move until the pathfindingPress is activated.

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
    Parent parent = FXMLLoader.load(getClass().getResource("/Views/EditNodes.fxml"));
    Scene scene = new Scene(parent);
    // this gets Stage info
    Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    window.setTitle("Edit Nodes");
    // this sets the scene to the new one specified above
    window.setScene(scene);
    window.show();
  }
}
