package edu.wpi.cs3733.teamO.GraphSystem;

import edu.wpi.cs3733.teamO.Database.NodesAndEdges;
import edu.wpi.cs3733.teamO.HelperClasses.DrawHelper;
import edu.wpi.cs3733.teamO.model.Edge;
import edu.wpi.cs3733.teamO.model.Node;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;

public class Graph {

  private int size; // not necessary?
  private static ObservableList<Node> listOfNodes;
  private static ObservableList<Edge> listOfEdges;
  private static Hashtable<String, Node> stringNodeHashtable;
  private static Hashtable<Node, Circle> nodeCircleHashtable;
  private AStarSearch aStarSearch;

  // GC/Canvas-related attributes:
  private GraphicsContext gc;
  private double circlePercentRadius;
  private double scaleX;
  private double scaleY;

  // TODO: put these as public static final somewhere?
  private final Image campusMap = new Image("FaulknerCampus_Updated.png");
  private final Image floor1Map = new Image("Faulkner1_Updated.png");
  private final Image floor2Map = new Image("Faulkner2_Updated.png");
  private final Image floor3Map = new Image("Faulkner3_Updated.png");
  private final Image floor4Map = new Image("Faulkner4_Updated.png");
  private final Image floor5Map = new Image("Faulkner5_Updated.png");

  private final double widthG = campusMap.getWidth();
  private final double heightG = campusMap.getHeight();
  private final double width1 = floor1Map.getWidth();
  private final double height1 = floor1Map.getHeight();
  private final double width2 = floor2Map.getWidth();
  private final double height2 = floor2Map.getHeight();
  private final double width3 = floor3Map.getWidth();
  private final double height3 = floor3Map.getHeight();
  private final double width4 = floor4Map.getWidth();
  private final double height4 = floor4Map.getHeight();
  private final double width5 = floor5Map.getWidth();
  private final double height5 = floor5Map.getHeight();

  /**
   * Creates a Graph from the database that will display on the provided GraphicsContext
   *
   * @param gc the GraphicsContext which the Graph will be displayed on
   */
  public Graph(GraphicsContext gc) {
    // initialize nodes based on DB
    listOfNodes = FXCollections.observableArrayList();
    listOfNodes = NodesAndEdges.getAllNodes();
    stringNodeHashtable = new Hashtable<>();
    for (Node n : listOfNodes) {
      stringNodeHashtable.put(n.getID(), n);
    }

    size = listOfNodes.size();

    // initialize edges based on DB
    listOfEdges = FXCollections.observableArrayList();
    listOfEdges = NodesAndEdges.getAllEdges();

    for (Edge e : listOfEdges) {
      Node nodeA = stringNodeHashtable.get(e.getStart());
      Node nodeB = stringNodeHashtable.get(e.getEnd());

      link(nodeA, nodeB, e);
    }

    nodeCircleHashtable = new Hashtable<>();
    this.gc = gc;
    createCircles();
  }

  /**
   * Graph constructor purely for testing purposes
   *
   * @param test dummy parameter (can be true or false)
   */
  /*Graph(boolean test) {
    listOfNodeIDs = new LinkedList<String>();
    size = 0;
    // listOfNodes = new Hashtable<>();
  }*/

  /**
   * Adds each Node to the other's list of neighbouring Nodes
   *
   * @param node1 first node
   * @param node2 second node
   */
  void link(Node node1, Node node2, Edge edge) {
    // TODO: calculate edge distance
    // TODO:   --> if stairs, dist = 1.0, if elevator, dist = 0.0

    // check if both exist
    if (listOfNodes.contains(node1) && listOfNodes.contains(node2)) {
      node1.addNeighbour(node2, edge);
      node2.addNeighbour(node1, edge);
    }
  }

  int getSize() {
    return size;
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
  public static Node closestNode(String floor, double x, double y) throws NullPointerException {
    double currentDist = 1000000000;
    Node node = null;

    for (Node n : listOfNodes) {
      if (n.getFloor().equals(floor)) {
        Circle tempCircle = nodeCircleHashtable.get(n);
        double dist =
            Math.pow(Math.abs(x - tempCircle.getCenterX()), 2.0)
                + Math.pow(Math.abs(y - tempCircle.getCenterY()), 2.0);
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
   * Creates a properly-scaled Circle for every Node in the database and adds it to
   * nodeCircleHashtable with it's corresponding Node as the key
   */
  public void createCircles() {

    for (Node n : listOfNodes) {
      // get node's x and y (and floor)
      double nX = n.getXCoord();
      double nY = n.getYCoord();
      double nXperc = 0.0;
      double nYperc = 0.0;
      String nFloor = n.getFloor();

      // set nX/Yperc to be the node's x/y as a percentage of the image's x/y
      // switch case basically = if, else if, etc...
      switch (nFloor) {
        case "G":
          nXperc = nX / widthG;
          nYperc = nY / heightG;
          break;
        case "1":
          nXperc = nX / width1;
          nYperc = nY / height1;
          break;
        case "2":
          nXperc = nX / width2;
          nYperc = nY / height2;
          break;
        case "3":
          nXperc = nX / width3;
          nYperc = nY / height3;
          break;
        case "4":
          nXperc = nX / width4;
          nYperc = nY / height4;
          break;
        case "5":
          nXperc = nX / width5;
          nYperc = nY / height5;
          break;
      }

      Circle circle = new Circle();
      // set radius to be percentage of canvas height, and bind circle's x/y to the canvas
      // width/height * percent
      circle.radiusProperty().bind(gc.getCanvas().heightProperty().multiply(0.01));
      circle.centerXProperty().bind(gc.getCanvas().widthProperty().multiply(nXperc));
      circle.centerYProperty().bind(gc.getCanvas().heightProperty().multiply(nYperc));

      /*TODO implement the clicking function for the circles
      circle.addEventHandler("click", clickCircle);*/

      nodeCircleHashtable.put(n, circle);
    }
  }

  /**
   * Draws all nodes for the given floor
   *
   * @param floor G, 1, 2, 3, 4, or 5
   */
  public void drawAllNodes(String floor, Node startNode, Node endNode) {
    ArrayList<Node> floorNodes = new ArrayList<>();

    for (Node n : listOfNodes) {
      if (n.getFloor().equals(floor)) floorNodes.add(n);
    }

    DrawHelper.drawNodeCircles(gc, nodeCircleHashtable, floorNodes, startNode, endNode);
  }

  public List<Node> findPath(Node startNode, Node targetNode, AlgorithmStrategy ag) {
    // TODO: change to accommodate Admin choice of A*, DFS, or BFS
    //aStarSearch = new AStarSearch(this, startNode, targetNode);
    //List<Node> route = AStarSearch.findRoute();
    List<Node> route = AlgorithmStrategy.findRoute(this,startNode,targetNode);
    //    LinkedList<Node> routeNodes = new LinkedList<>();
    //    for (Node node : route) {
    //      routeNodes.add(node);
    //    }

    return route;
  }

  public void drawMidArrow(Node nodeA, Node nodeB) {
    Circle circleA = nodeCircleHashtable.get(nodeA);
    Circle circleB = nodeCircleHashtable.get(nodeB);

    DrawHelper.drawMidArrow(gc, circleA, circleB);
  }
}
