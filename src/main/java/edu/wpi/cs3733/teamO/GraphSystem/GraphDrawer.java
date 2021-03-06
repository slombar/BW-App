package edu.wpi.cs3733.teamO.GraphSystem;

import edu.wpi.cs3733.teamO.Database.Graph;
import edu.wpi.cs3733.teamO.HelperClasses.DrawHelper;
import edu.wpi.cs3733.teamO.model.Node;
import java.util.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GraphDrawer {

  private static Hashtable<Node, Circle> nodeCircleHashtable;
  private AlgorithmStrategy strategy;
  List<Node> path;

  // GC/Canvas-related attributes:
  private GraphicsContext gc;
  private Graph graph;

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
  public GraphDrawer(GraphicsContext gc) {
    graph = Graph.getInstance();
    nodeCircleHashtable = new Hashtable<>();
    this.gc = gc;
    createCircles();
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
  public Node closestNode(String floor, double x, double y) throws NullPointerException {
    double currentDist = 1000000000;
    Node node = null;

    for (Node n : graph.listOfNodes) {
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

    for (Node n : graph.listOfNodes) {
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

  /**
   * Draws all nodes for the given floor
   *
   * @param floor G, 1, 2, 3, 4, or 5
   */
  public void drawAllNodes(String floor, Node selectedNode) {
    ArrayList<Node> floorNodes = new ArrayList<>();

    for (Node n : graph.listOfNodes) {
      if (n.getFloor().equals(floor)) floorNodes.add(n);
    }

    DrawHelper.drawNodeCircles(gc, nodeCircleHashtable, floorNodes, null, null);
    if (graph.listOfNodes.contains(selectedNode) && selectedNode.getFloor().equals(floor)) {
      DrawHelper.drawSingleNode(gc, nodeCircleHashtable.get(selectedNode), Color.BLUE);
    }
  }

  public void drawVisibleNodes(String floor, Node startNode, Node endNode) {
    ArrayList<Node> floorNodes = new ArrayList<>();

    for (Node n : graph.listOfNodes) {
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

  /**
   * Draws all of the edges from the database
   *
   * @param floor
   */
  public void drawAllEdges(String floor) {
    gc.setStroke(Color.BLACK);
    for (Node n : graph.listOfNodes) {
      try {
        /*Node nodeA = stringNodeHashtable.get(e.getStart());
        Node nodeB = stringNodeHashtable.get(e.getEnd());*/

        Node nodeA = n;
        Node nodeB;
        List<Node> neighborList = graph.map.get(n);

        for (Node neighbor : neighborList) {
          nodeB = neighbor;

          // if they are on the same, current floor
          if (nodeA.getFloor().equals(floor) && nodeB.getFloor().equals(floor)) {
            // create circles
            Circle circleA = nodeCircleHashtable.get(nodeA);
            Circle circleB = nodeCircleHashtable.get(nodeB);

            DrawHelper.drawEdge(gc, circleA, circleB);
          }
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
    // clears the canvas
    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

    // for each node in the path, draw on canvas
    for (Node n : path) {
      // if the current node is on the floor, and its t
      if (n.getNodeType().equals("STAI") && n.getFloor().equals(floor)) {
        Circle c = nodeCircleHashtable.get(n);

        DrawHelper.drawSingleNode(gc, c, Color.GREEN);
      } else if (n.getNodeType().equals("ELEV") && n.getFloor().equals(floor)) {
        Circle c = nodeCircleHashtable.get(n);

        DrawHelper.drawSingleNode(gc, c, Color.PURPLE);
      }

      if (n.getNodeType().equals("EXIT") && n.getFloor().equals(floor)) {
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
//
//  // TODO figure out something to do about the fucking visited thing
//  public void findPath(String strat, Node startNode, Node targetNode) {
//
//    //    switch (strat) {
//    //      case "A*":
//    //strategy = new AStarSearch();
//    /*
//    break;
//     TODO implement new methods to pathfind (KE)
//    case "DFS":
//      strategy = new DFS();
//      break;
//    case "BFS":
//      strategy = new BFS();
//      break;
//      8?
//       */
//    // }
//
//    path = (startNode, targetNode);
//  }

  // add function calls for direction at points.
  public static double findDirectionForPoints(Node node1, Node node2, Node node3) {
    return TextDirection.direction(node1, node2, node3);
  }

  /**
   * @param path
   * @return
   */
  public static ArrayList<String> findTextDirection(ArrayList<Node> path) {
    return TextDirection.textDirections(path);
  }

  public void resetPath() {
    path = new LinkedList<>();
  }
}
