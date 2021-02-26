package edu.wpi.cs3733.teamO.GraphSystem;

import edu.wpi.cs3733.teamO.model.Edge;
import edu.wpi.cs3733.teamO.model.Node;
import edu.wpi.cs3733.teamO.Database.NodesAndEdges;

import java.util.LinkedList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Circle;

class Graph {

  private int size; // not necessary?
  //private Hashtable<String, Node> listOfNodes; // <NodeID, Node>
  // what is the purpose of this?
  private LinkedList<String> listOfNodeIDs; // not necessary?
  private ObservableList<Node> listOfNodes;
  private ObservableList<Edge> listOfEdges;

  private static GraphicsContext gc;

  // default constructor
  Graph() {
    /*listOfNodeIDs = new LinkedList<String>();
    size = 0;
    listOfNodes = new Hashtable<>();*/


  }

  Graph(boolean test) {
    listOfNodeIDs = new LinkedList<String>();
    size = 0;
    //listOfNodes = new Hashtable<>();
  }

  public void initialize() {
    listOfNodes = FXCollections.observableArrayList();
    listOfNodes = NodesAndEdges.getAllNodes();
    size = listOfNodes.size();
    listOfEdges = FXCollections.observableArrayList();
    listOfEdges = NodesAndEdges.getAllEdges();

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

      int xcoord = n.getXCoord();
      int ycoord = n.getYCoord();

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

  /*Node getNode(String nodeID) {
    return listOfNodes.get(nodeID);
  }*/

  int getSize() {
    return size;
  }

  public Node closestNode(double x, double y) throws NullPointerException{
    double currentDist = 1000000000;
    Node node = null;

    for (Node n : listOfNodes) {
      double dist = Math.pow(Math.abs(x - node.getXCoord()), 2.0) + Math.pow(Math.abs(y - node.getYCoord()), 2.0);
      if (dist < currentDist) {
        currentDist = dist;
        node = n;
      }
    }

    return node;
  }
}
