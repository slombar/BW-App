package edu.wpi.cs3733.teamO.GraphSystem;

import edu.wpi.cs3733.teamO.Database.NodesAndEdges;
import edu.wpi.cs3733.teamO.HelperClasses.DrawHelper;
import edu.wpi.cs3733.teamO.model.Edge;
import edu.wpi.cs3733.teamO.model.Node;
import java.sql.SQLException;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Graph {

  private int size; // necessary
  private static ObservableList<Node> listOfNodes;
  private static ObservableList<Edge> listOfEdges;
  private static Hashtable<String, Node> stringNodeHashtable;
  private static Hashtable<String, Edge> stringEdgeHashtable;
  private static Hashtable<Node, Circle> nodeCircleHashtable;
  private AlgorithmStrategy strategy;
  List<Node> path;

  // GC/Canvas-related attributes:
  private GraphicsContext gc;

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

    stringEdgeHashtable = new Hashtable<>();

    for (Edge e : listOfEdges) {
      stringEdgeHashtable.put(e.getID(), e);
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
      circle.radiusProperty().bind(gc.getCanvas().widthProperty().multiply(0.00625));
      circle.centerXProperty().bind(gc.getCanvas().widthProperty().multiply(nXperc));
      circle.centerYProperty().bind(gc.getCanvas().heightProperty().multiply(nYperc));

      /*TODO implement the clicking function for the circles
      circle.addEventHandler("click", clickCircle);*/

      nodeCircleHashtable.put(n, circle);
    }
  }

  public void addNode(Node n) {
    // if ID already exists, then editing -> need node's neighborlist
    if (stringNodeHashtable.containsKey(n.getID())) {
      Node prev = stringNodeHashtable.get(n.getID());
      HashSet<Node> prevNList = prev.getNeighbourList();
      Hashtable<Node, Edge> prevNEList = prev.getNodeEdgeHashtable();
      Circle prevC = nodeCircleHashtable.get(prev);

      listOfNodes.remove(prev);
      nodeCircleHashtable.remove(prev);

      n.setNeighbourList(prevNList);
      n.setNodeEdgeHashtable(prevNEList);

      listOfNodes.add(n);
      stringNodeHashtable.put(n.getID(), n);
      nodeCircleHashtable.put(n, prevC);
      return;
    }

    // add circle
    // add node to graph
    String nodeID = n.getID();
    Circle c = new Circle();

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

    // copied from above, should workerino
    c.radiusProperty().bind(gc.getCanvas().widthProperty().multiply(0.00625));
    c.centerXProperty().bind(gc.getCanvas().widthProperty().multiply(nXperc));
    c.centerYProperty().bind(gc.getCanvas().heightProperty().multiply(nYperc));

    // rewrites the hash
    listOfNodes.add(n);
    stringNodeHashtable.put(nodeID, n);
    nodeCircleHashtable.put(n, c);
  }

  public void addEdge(Edge e) {
    Node node1 = stringNodeHashtable.get(e.getStart());
    Node node2 = stringNodeHashtable.get(e.getEnd());
    // add edge to graph
    link(node1, node2, e);

    stringEdgeHashtable.put(e.getID(), e);
    listOfEdges.add(e);
  }

  public void deleteNode(String nodeID) {
    Node n = stringNodeHashtable.get(nodeID);
    // delete from graph
    nodeCircleHashtable.remove(n);
    // remove from the string hashtable
    stringNodeHashtable.remove(n.getID());
    // removes all edges
    // for each neighbor of the given deleting node
    for (Node node : n.getNeighbourList()) {
      // remove the neighbor from the list
      n.getNeighbourList().remove(node);

      // check edgelist to see if the variable Node "node" is equal
      // to the start node or end node of any edge in the list
      listOfEdges.removeIf(
          e -> e.getStart().equals(node.getID()) || e.getEnd().equals(node.getID()));
    }
    listOfNodes.remove(n);
  }

  public void deleteEdge(String startNodeID, String endNodeID) throws SQLException {
    String eID = "not set yet";
    String eID1 = startNodeID + "_" + endNodeID;
    String eID2 = endNodeID + "_" + startNodeID;
    if (stringEdgeHashtable.containsKey(eID1)) {
      eID = eID1;
    } else if (stringEdgeHashtable.containsKey(eID2)) {
      eID = eID2;
    } else {
      return; // neither key/ID is in the hashtable -> just return
    }

    Edge e = stringEdgeHashtable.get(eID);
    NodesAndEdges.deleteEdge(eID);
    // delete edge from graph
    // go into neighbor list, unlink the startnode and endnode of the edge

    // retrieve start and end nodes
    String sNode = e.getStart();
    String eNode = e.getEnd();

    // get the proper
    Node node1 = stringNodeHashtable.get(sNode);
    Node node2 = stringNodeHashtable.get(eNode);

    node1.getNeighbourList().remove(node2);
    // TODO also remove from the hashtable of neighbors
    node2.getNeighbourList().remove(node1);
    // TODO also remove from the hashtable of neighbors

    // remove edge from list
    listOfEdges.remove(e);
  }

  /**
   * Draws all nodes for the given floor
   *
   * @param floor G, 1, 2, 3, 4, or 5
   */
  public void drawAllNodes(String floor, Node selectedNode) {
    ArrayList<Node> floorNodes = new ArrayList<>();

    for (Node n : listOfNodes) {
      if (n.getFloor().equals(floor)) floorNodes.add(n);
    }

    DrawHelper.drawNodeCircles(gc, nodeCircleHashtable, floorNodes, null, null);
    if (listOfNodes.contains(selectedNode) && selectedNode.getFloor().equals(floor)) {
      DrawHelper.drawSingleNode(gc, nodeCircleHashtable.get(selectedNode), Color.BLUE);
    }
  }

  public void drawVisibleNodes(String floor, Node startNode, Node endNode) {
    ArrayList<Node> floorNodes = new ArrayList<>();

    for (Node n : listOfNodes) {
      if (n.isVisible() && n.getFloor().equals(floor)) {
        floorNodes.add(n);
      }
    }

    DrawHelper.drawNodeCircles(gc, nodeCircleHashtable, floorNodes, startNode, endNode);
  }

  /**
   * Draws all the arrows for the most recently calculated path that are on the given floor
   *
   * @param floor floor to draw on
   */
  private void drawMidArrows(String floor) {
    for (int i = 0; i < path.size() - 1; i++) {
      Node nodeA = path.get(i);
      Node nodeB = path.get(i + 1);

      if (nodeA.getFloor().equals(floor) && nodeB.getFloor().equals(floor)) {
        Circle circleA = nodeCircleHashtable.get(nodeA);
        Circle circleB = nodeCircleHashtable.get(nodeB);

        DrawHelper.drawMidArrow(gc, circleA, circleB);
      }
    }
  }

  public void drawAllEdges(String floor) {
    for (Edge e : listOfEdges) {
      try {
        Node nodeA = stringNodeHashtable.get(e.getStart());
        Node nodeB = stringNodeHashtable.get(e.getEnd());

        if (nodeA.getFloor().equals(floor) && nodeB.getFloor().equals(floor)) {
          Circle circleA = nodeCircleHashtable.get(nodeA);
          Circle circleB = nodeCircleHashtable.get(nodeB);

          DrawHelper.drawEdge(gc, circleA, circleB);
        }
      } catch (NullPointerException ignored) {
        // TODO: use this catch block to filter out bad/extraneous data
        // for now it just ignores them and draws the edges that do actually exist
      }
    }
  }

  /**
   * Draws the current path stored by this Graph
   *
   * @param floor selected floor
   * @param startNode start Node of path
   * @param endNode end Node of path
   */
  public void drawCurrentPath(String floor, Node startNode, Node endNode) {
    Canvas canvas = gc.getCanvas();
    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

    for (Node n : path) {
      if (n.getFloor().equals(floor) && n.getNodeType().equals("STAI")) {
        Circle c = nodeCircleHashtable.get(n);
        DrawHelper.drawSingleNode(gc, c, Color.GREEN);
      } else if (n.getFloor().equals(floor) && n.getNodeType().equals("ELEV")) {
        Circle c = nodeCircleHashtable.get(n);
        DrawHelper.drawSingleNode(gc, c, Color.PURPLE);
      }

      if (n.getFloor().equals(floor) && n.getNodeType().equals("EXIT")) {
        Circle c = nodeCircleHashtable.get(n);
        DrawHelper.drawSingleNode(gc, c, Color.ORANGE);
      }
    }

    drawMidArrows(floor);

    if (startNode.getFloor().equals(floor)) {
      Circle c = nodeCircleHashtable.get(startNode);
      DrawHelper.drawSingleNode(gc, c, Color.BLUE);
    }
    if (endNode.getFloor().equals(floor)) {
      Circle c = nodeCircleHashtable.get(endNode);
      DrawHelper.drawSingleNode(gc, c, Color.RED);
    }
  }

  public void findPath(String strat, Node startNode, Node targetNode) {
    for (Node n : listOfNodes) {
      n.setVisited(false);
    }

    switch (strat) {
      case "A*":
        strategy = new AStarSearch(this);
        break;
      case "DFS":
        strategy = new DFS();
        break;
      case "BFS":
        strategy = new BFS();
        break;
    }

    path = strategy.findRoute(startNode, targetNode);
  }

  // add function calls for direction at points.
  public static double findDirectionForPoints(Node node1, Node node2, Node node3) {
    return TextDirection.direction(node1, node2, node3);
  }

  // add function calls for text direction.
  public static ArrayList<String> findTextDirection(ArrayList<Node> path) {
    return TextDirection.textDirections(path);
  }

  public void resetPath() {
    path = new LinkedList<>();
  }

  public ObservableList<Node> getListOfNodes() {
    return listOfNodes;
  }
}
