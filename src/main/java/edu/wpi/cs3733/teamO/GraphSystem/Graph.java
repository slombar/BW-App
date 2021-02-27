package edu.wpi.cs3733.teamO.GraphSystem;

import edu.wpi.cs3733.teamO.model.Edge;
import edu.wpi.cs3733.teamO.model.Node;
import edu.wpi.cs3733.teamO.Database.NodesAndEdges;

import java.util.Hashtable;
import java.util.LinkedList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

class Graph {
  private static Hashtable<Node, Circle> nodeCircleHashtable;
  private int size; // not necessary?
  //private Hashtable<String, Node> listOfNodes; // <NodeID, Node>
  // what is the purpose of this?
  private LinkedList<String> listOfNodeIDs; // not necessary?
  private ObservableList<Node> listOfNodes;
  private ObservableList<Edge> listOfEdges;
  private static GraphicsContext gc;
  private ImageView imgView;


  Graph(GraphicsContext gc) {
    /*listOfNodeIDs = new LinkedList<String>();
    size = 0;
    listOfNodes = new Hashtable<>();*/
    listOfNodes = FXCollections.observableArrayList();
    listOfNodes = NodesAndEdges.getAllNodes();
    size = listOfNodes.size();
    listOfEdges = FXCollections.observableArrayList();
    listOfEdges = NodesAndEdges.getAllEdges();
    this.gc = gc;
  }

  /**
   * this constructor is purely for testing purposes
   * @param test dummy parameter
   */
  Graph(boolean test) {
    listOfNodeIDs = new LinkedList<String>();
    size = 0;
    //listOfNodes = new Hashtable<>();
  }

  public void initialize() {

    /*// first reset everything (as though creating new graph):
    listOfNodeIDs = new LinkedList<String>();
    size = 0;
    listOfNodes = new Hashtable<>();

    // then do this stuff:
    Node node;

    // initialize a graph
    // new graph graph??

    // get the nodes (and edges) from the database function
    ObservableList<Node> nodeList = FXCollections.observableArrayList();
    nodeList = NodesAndEdges.getAllNodes();

    ObservableList<Edge> edgeList = FXCollections.observableArrayList();
    edgeList = NodesAndEdges.getAllEdges();

    // freakk inteilkijdh
    // initialize all the graph nodes
    for (Node n : nodeList) {

      String nodeID = n.getID();

      // add to nodeIDs list?? idk what it is

      int xcoord = n.getX();
      int ycoord = n.getY();

      // creates a single node with the given entered parameters (above)
      node = new Node(nodeID, xcoord, ycoord);

      // ADDS THE NODE
      this.addNode(node);
    }

    // grab edges from database
    // edgeList = DatabaseFunctionality.showEdges(edgeList);
    String[] nodeIDA;
    String node1ID = "";
    String node2ID = "";

    // for each edge, parse the first half of ID, add to the
    for (Edge e : edgeList) {
      // get the first half (delimiter = _)
      nodeIDA = (e.getID()).split("_", 10);

      node1ID = nodeIDA[0];
      node2ID = nodeIDA[1];

      link(node1ID, node2ID);
    }*/
  }

  /*LinkedList<String> getNodeIDList() {
    return listOfNodeIDs;
  }*/

  /*void addNode(Node node) {
    // put node into listOfNodes with nodeID as the key
    String key = node.getID();
    listOfNodes.put(key, node);
    listOfNodeIDs.add(node.getID()); // also add nodeID to listOfNodeIDs for easy access

    // increment size
    size++;
  }*/

  // adds a bi-directional edge to the nodes corresponding to both ID's
  void link(Node node1, Node node2) {
    // check if both exist
    if(listOfNodes.contains(node1) && listOfNodes.contains(node2)) {
      node1.addNeighbour(node2);
      node2.addNeighbour(node1);
    }
  }

  Node getNode(String nodeID) {
    return listOfNodes.get(nodeID);
  }

  int getSize() {
    return size;
  }

  public Node closestNode(double x, double y) throws NullPointerException{
    double currentDist = 1000000000;
    Node node = null;

    for (Node n : listOfNodes) {
      double dist = Math.pow(Math.abs(x - node.getX()), 2.0) + Math.pow(Math.abs(y - node.getY()), 2.0);
      if (dist < currentDist) {
        currentDist = dist;
        node = n;
      }
    }

    return node;
  }

  /**
   * Creates the circle objects to be drawn on the canvas
   */
  public void createCircles(){

    for (Node n : listOfNodes) {
      Circle circle = new Circle();
      circle.addEventHandler("click", /*TODO add the clicking function*/);

      double nodeX = (double) n.getX() / scaleX;
      double nodeY = (double) n.getY() / scaleY;

      circle.setCenterX(nodeX);
      circle.setCenterY(nodeY);
      circle.setRadius(cW / 2);
      nodeCircleHashtable.put(n, circle);

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
  }

  public static Hashtable<String, Circle> getStringCircleHashtable() {
    return stringCircleHashtable;
  }
}
