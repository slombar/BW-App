package edu.wpi.cs3733.teamO.GraphSystem;

import edu.wpi.cs3733.teamO.Database.NodesAndEdges;
import edu.wpi.cs3733.teamO.HelperClasses.DrawHelper;
import edu.wpi.cs3733.teamO.Model.Edge;
import edu.wpi.cs3733.teamO.Model.Node;
import java.sql.SQLException;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Graph {

  // *****************************//
  // maps and related variables:  //
  // *****************************//

  // public static Images of each floor map
  public static Image campusMap = new Image("FaulknerCampus_Updated.png");
  public static Image floor1Map = new Image("Faulkner1_Updated.png");
  public static Image floor2Map = new Image("Faulkner2_Updated.png");
  public static Image floor3Map = new Image("Faulkner3_Updated.png");
  public static Image floor4Map = new Image("Faulkner4_Updated.png");
  public static Image floor5Map = new Image("Faulkner5_Updated.png");

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

  // *****************************//
  // singleton-related stuff:     //
  // *****************************//

  // want eager initialization here (may cause problems with threading)
  public static Graph GRAPH = new Graph();

  private Graph() {
    initialize();
  }

  // *****************************//
  // Graph's parameters/methods:  //
  // *****************************//

  private int size; // not really necessary? idk
  private static ObservableList<Node> listOfNodes;
  private static ObservableList<Edge> listOfEdges;
  private static Hashtable<String, Node> stringNodeHashtable;
  private static Hashtable<String, Edge> stringEdgeHashtable;
  private static Hashtable<String, Circle>
      stringCircleHashtable; // changed back to <NodeID, Circle>
  private AlgorithmStrategy strategy;
  List<Node> path;

  private GraphicsContext gc;

  /** initializes GRAPH based on the valued retrieved from the database */
  private void initialize() {
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

    stringCircleHashtable = new Hashtable<>();
  }

  public void setGraphicsContext(GraphicsContext gc) {
    this.gc = gc;
    for (Node n : listOfNodes) {
      createCircle(n);
    }
  }

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

  /**
   * Returns the Node closest to the given (x,y) on the given floor
   *
   * @param floor floor currently displaying when clicked
   * @param x x-coordinate of the ClickEvent
   * @param y y-coordinate of the ClickEvent
   * @return Node closest to the ClickEvent
   * @throws NullPointerException will return null if it doesn't find a closest node
   */
  public static Node closestNode(String floor, double x, double y, boolean editing)
      throws NullPointerException {
    double currentDist = 1000000000;
    Node node = null;

    for (Node n : listOfNodes) {
      if (n.getFloor().equals(floor) && (n.isVisible() || editing)) {
        Circle c = stringCircleHashtable.get(n.getID());
        double dist =
            Math.pow(Math.abs(x - c.getCenterX()), 2.0)
                + Math.pow(Math.abs(y - c.getCenterY()), 2.0);
        if (dist < currentDist) {
          currentDist = dist;
          node = n;
        }
      }
    }

    return node;
  }

  /**
   * Creates a properly-scaled Circle the given Node and adds it to stringCircleHashtable with it's
   * corresponding Node's ID as the key
   */
  public void createCircle(Node n) {

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

    /* implement the clicking function for the circles(?) - probably not
    circle.addEventHandler("click", clickCircle);*/

    stringCircleHashtable.put(n.getID(), circle);
  }

  /**
   * adds the given Node to the graph appropriately
   *
   * @param n Node to be added
   */
  public void addNode(Node n, boolean addNodeDBMode) throws SQLException {
    // if not adding (editing), do edit stuff, otherwise just add
    if (!addNodeDBMode) {

      // try editing in DB (throws SQLException)
      NodesAndEdges.editNode(
          n.getID(),
          n.getXCoord(),
          n.getYCoord(),
          n.getFloor(),
          n.getBuilding(),
          n.getNodeType(),
          n.getLongName(),
          n.getShortName(),
          "O",
          n.isVisible());

      // if NodesAndEdges.addNode() doesn't throw exception, add to graph itself:
      // get old version of Node and it's related things
      Node prev = stringNodeHashtable.get(n.getID());
      HashSet<Node> prevNList = prev.getNeighbourList();
      Hashtable<Node, Edge> prevNEList = prev.getNodeEdgeHashtable();

      // remove old version from Node list
      listOfNodes.remove(prev);

      // set new version's neighbours = old's
      n.setNeighbourList(prevNList);
      n.setNodeEdgeHashtable(prevNEList);

      // n needs new Circle (in case x,y changed)
      stringCircleHashtable.remove(n.getID());
      createCircle(n);

      // add new node and corresponding
      listOfNodes.add(n);
      stringNodeHashtable.put(n.getID(), n);
      return;
    }

    // try adding to DB (throws SQLException)
    NodesAndEdges.addNode(
        n.getID(),
        Integer.toString(n.getXCoord()),
        Integer.toString(n.getYCoord()),
        n.getFloor(),
        n.getBuilding(),
        n.getNodeType(),
        n.getLongName(),
        n.getShortName(),
        "O",
        n.isVisible());

    // adding new Node and Circle:

    // add circle for n
    createCircle(n);

    // rewrites the hash
    listOfNodes.add(n);
    stringNodeHashtable.put(n.getID(), n);
  }

  /** adds the given Edge to the graph appropriately */
  public void addEdge(String startID, String endID) throws SQLException {
    String eID = "not set yet";
    String eID1 = startID + "_" + endID;
    String eID2 = endID + "_" + startID;
    // if Edge with eID1 exists, have NodesAndEdges throw exception
    if (stringEdgeHashtable.containsKey(eID1)) {
      throw new SQLException();
    }
    // else, if Edge with eID2 exists, have NodesAndEdges throw exception
    else if (stringEdgeHashtable.containsKey(eID2)) {
      throw new SQLException();
    }
    // else, Edge doesn't exist already, so actually add it
    else {
      // add to DB
      eID = eID1;
      NodesAndEdges.addNewEdge(startID, endID);
    }

    // make new Edge
    Edge e = new Edge(eID, startID, endID, 0.0);

    // add to graph
    Node node1 = stringNodeHashtable.get(e.getStart());
    Node node2 = stringNodeHashtable.get(e.getEnd());
    // add edge to graph
    link(node1, node2, e);

    stringEdgeHashtable.put(e.getID(), e);
    listOfEdges.add(e);
  }

  /**
   * removes the given Node from the graph appropriately (including all of it's Edges)
   *
   * @param nodeID the Node to be removed
   */
  public void deleteNode(String nodeID) throws SQLException {
    // (try) deleting from DB
    NodesAndEdges.deleteNode(nodeID);

    Node n = stringNodeHashtable.get(nodeID);

    // removes all edges for each neighbor of the given deleting node
    for (Edge e : listOfEdges) {
      if (e.getStart().equals(nodeID) || e.getEnd().equals(nodeID)) {
        try {
          this.deleteEdge(e.getStart(), e.getEnd());
        } catch (SQLException throwables) {
          throwables.printStackTrace();
        }
      }
    }

    // remove from graph
    listOfNodes.remove(n);
    stringCircleHashtable.remove(n.getID());
    stringNodeHashtable.remove(n.getID());
  }

  /**
   * removes the given Edge from the graph
   *
   * @param startNodeID ID (String) of one of the Nodes connected by the Edge
   * @param endNodeID ID (String) of the other Node connected by the Edge
   * @throws SQLException thrown by NodesAndEdges.deleteEdge(edgeID)
   */
  public void deleteEdge(String startNodeID, String endNodeID) throws SQLException {
    String eID = "not set yet";
    String eID1 = startNodeID + "_" + endNodeID;
    String eID2 = endNodeID + "_" + startNodeID;
    if (stringEdgeHashtable.containsKey(eID1)) {
      eID = eID1;
    } else if (stringEdgeHashtable.containsKey(eID2)) {
      eID = eID2;
    } else {
      throw new SQLException();
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
    node1.getNodeEdgeHashtable().remove(node2);
    node2.getNeighbourList().remove(node1);
    node2.getNodeEdgeHashtable().remove(node1);

    // remove edge from list
    listOfEdges.remove(e);
    stringEdgeHashtable.remove(eID);
  }

  /**
   * Draws all nodes for the given floor + selected node as Color.GREEN, or draws both right-clicked
   * nodes Color.BLUE + a blue edge (to be added/deleted)
   *
   * @param floor "G", "1", "2", "3", "4", or "5"
   */
  public void drawAllNodes(
      String floor, Node selectedNodeA, Node selectedNodeB, boolean selectingEditNode) {
    ArrayList<Node> floorNodes = new ArrayList<>();

    for (Node n : listOfNodes) {
      if (n.getFloor().equals(floor)) floorNodes.add(n);
    }

    // draws all Nodes on floor
    DrawHelper.drawNodeCircles(gc, stringCircleHashtable, floorNodes, null, null);

    // if one Node selected, draws it as green
    if (selectedNodeB == null
        && listOfNodes.contains(selectedNodeA)
        && selectedNodeA.getFloor().equals(floor)) {

      DrawHelper.drawSingleNode(gc, stringCircleHashtable.get(selectedNodeA.getID()), Color.GREEN);
    }
    // otherwise, if two Nodes selected, draws both as blue
    else if (listOfNodes.contains(selectedNodeA) && listOfNodes.contains(selectedNodeB)) {
      Paint paint = Color.BLUE;
      if (selectedNodeA.getNeighbourList().contains(selectedNodeB)) {
        paint = Color.RED;
      }

      boolean isFloorA = selectedNodeA.getFloor().equals(floor);
      boolean isFloorB = selectedNodeB.getFloor().equals(floor);

      // TODO: not sure if should draw edge only if both Nodes on same floor,
      //    or just if at least one is on current floor (since edges can go between floors)
      //    -- currently drawing if just one of 2 Nodes is on current floor ( || )
      if (isFloorA || isFloorB) {
        DrawHelper.drawEdge(
            gc,
            stringCircleHashtable.get(selectedNodeA.getID()),
            stringCircleHashtable.get(selectedNodeB.getID()),
            paint);
      }

      // currently has first selectedNode always GREEN
      if (isFloorA) {
        DrawHelper.drawSingleNode(
            gc, stringCircleHashtable.get(selectedNodeA.getID()), Color.GREEN);
      }
      if (isFloorB) {
        DrawHelper.drawSingleNode(gc, stringCircleHashtable.get(selectedNodeB.getID()), paint);
      }
    }
  }

  /**
   * draws all Nodes whose visible property = true
   *
   * @param floor "G", "1", "2", "3", "4", or "5"
   * @param startNode Node currently set as the start
   * @param endNode Node currently set as the end
   */
  public void drawVisibleNodes(String floor, Node startNode, Node endNode) {
    ArrayList<Node> floorNodes = new ArrayList<>();

    for (Node n : listOfNodes) {
      if (n.isVisible() && n.getFloor().equals(floor)) {
        floorNodes.add(n);
      }
    }

    DrawHelper.drawNodeCircles(gc, stringCircleHashtable, floorNodes, startNode, endNode);
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
        Circle circleA = stringCircleHashtable.get(nodeA.getID());
        Circle circleB = stringCircleHashtable.get(nodeB.getID());

        DrawHelper.drawMidArrow(gc, circleA, circleB);
      }
    }
  }

  /**
   * draws all Edges on the given floor
   *
   * @param floor "G", "1", "2", "3", "4", or "5"
   */
  public void drawAllEdges(String floor, Node selectedNodeA, Node selectedNodeB) {
    gc.setStroke(Color.BLACK);
    for (Edge e : listOfEdges) {
      try {
        Node nodeA = stringNodeHashtable.get(e.getStart());
        Node nodeB = stringNodeHashtable.get(e.getEnd());

        if (nodeA.getFloor().equals(floor) && nodeB.getFloor().equals(floor)) {
          Circle circleA = stringCircleHashtable.get(nodeA.getID());
          Circle circleB = stringCircleHashtable.get(nodeB.getID());

          if (nodeA.equals(selectedNodeA) && nodeB.equals(selectedNodeB)) {
            DrawHelper.drawEdge(gc, circleA, circleB, Color.RED);
          } else DrawHelper.drawEdge(gc, circleA, circleB, Color.BLACK);
        }
      } catch (NullPointerException ignored) {
        // TODO: use this catch block to filter out bad/extraneous data?
        // for now it just ignores them and draws the edges that do actually exist
      }
    }
  }

  /**
   * Getter for path
   *
   * @return path
   */
  public List<Node> getPath() {
    return path;
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
        Circle c = stringCircleHashtable.get(n.getID());
        DrawHelper.drawSingleNode(gc, c, Color.GREEN);
      } else if (n.getFloor().equals(floor) && n.getNodeType().equals("ELEV")) {
        Circle c = stringCircleHashtable.get(n.getID());
        DrawHelper.drawSingleNode(gc, c, Color.PURPLE);
      }

      if (n.getFloor().equals(floor) && n.getNodeType().equals("EXIT")) {
        Circle c = stringCircleHashtable.get(n.getID());
        DrawHelper.drawSingleNode(gc, c, Color.ORANGE);
      }
    }

    drawMidArrows(floor);

    if (startNode.getFloor().equals(floor)) {
      Circle c = stringCircleHashtable.get(startNode.getID());
      DrawHelper.drawSingleNode(gc, c, Color.BLUE);
    }
    if (endNode.getFloor().equals(floor)) {
      Circle c = stringCircleHashtable.get(endNode.getID());
      DrawHelper.drawSingleNode(gc, c, Color.RED);
    }
  }

  /**
   * sets this Graph's path = the path found by the given algorithm from the given start to end
   * Nodes
   *
   * @param strat "A*", "DFS", or "BFS"
   * @param startNode start Node of the path
   * @param targetNode end Node of the path
   */
  public void findPath(String strat, Node startNode, Node targetNode) {
    for (Node n : listOfNodes) {
      n.setVisited(false);
    }

    switch (strat) {
      case "A*":
        strategy = new AStarSearch();
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

  /** resets this graph's path to a new LinkedList */
  public void resetPath() {
    path = new LinkedList<>();
  }

  /**
   * gets the list of all Nodes in this graph
   *
   * @return list of all Nodes
   */
  public ObservableList<Node> getListOfNodes() {
    return listOfNodes;
  }
}
