package edu.wpi.cs3733.teamO.GraphSystem;

import edu.wpi.cs3733.teamO.Database.NodesAndEdges;
import edu.wpi.cs3733.teamO.model.Edge;
import edu.wpi.cs3733.teamO.model.Node;
import java.util.LinkedList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Circle;

class Graph {

  private int size; // not necessary?
  private LinkedList<String> listOfNodeIDs; // not necessary?
  private static ObservableList<Node> listOfNodes;
  private static ObservableList<Edge> listOfEdges;
  private static GraphicsContext gc;

  private

  /** @param gc - the graphics controller for the canvas */
  Graph(GraphicsContext gc) {
    listOfNodes = FXCollections.observableArrayList();
    listOfNodes = NodesAndEdges.getAllNodes();
    size = listOfNodes.size();
    listOfEdges = FXCollections.observableArrayList();
    listOfEdges = NodesAndEdges.getAllEdges();
    this.gc = gc;
  }

  Graph(boolean test) {
    listOfNodeIDs = new LinkedList<String>();
    size = 0;
    // listOfNodes = new Hashtable<>();
  }

  public Graph() {}

  public void initialize() {}

  // adds a bi-directional edge to the nodes corresponding to both ID's
  void link(Node node1, Node node2) {
    // check if both exist
    if (listOfNodes.contains(node1) && listOfNodes.contains(node2)) {
      node1.addNeighbour(node2);
      node2.addNeighbour(node1);
    }
  }

  int getSize() {
    return size;
  }

  public static Node closestNode(double x, double y) throws NullPointerException {
    double currentDist = 1000000000;
    Node node = null;

    for (Node n : listOfNodes) {
      double dist =
          Math.pow(Math.abs(x - node.getXCoord()), 2.0)
              + Math.pow(Math.abs(y - node.getYCoord()), 2.0);
      if (dist < currentDist) {
        currentDist = dist;
        node = n;
      }
    }

    return node;
  }

  /**
   * @param scaleX
   * @param scaleY,
   * @param cW, the circle width
   */
  public void createCircles(double scaleX, double scaleY, int cW) {

    for (Node n : listOfNodes) {
      Circle circle = new Circle();
      /*TODO implement the clicking function for the circles
      circle.addEventHandler("click", clickCircle);*/

      double nodeX = (double) n.getXCoord() / scaleX;
      double nodeY = (double) n.getYCoord() / scaleY;

      circle.setCenterX(nodeX);
      circle.setCenterY(nodeY);
      circle.setRadius(cW / 2);
    }
  }
}
