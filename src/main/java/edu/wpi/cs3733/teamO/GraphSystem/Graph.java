package edu.wpi.cs3733.teamO.GraphSystem;

import edu.wpi.cs3733.teamO.Controllers.model.Edge;
import edu.wpi.cs3733.teamO.Controllers.model.Node;
import edu.wpi.cs3733.teamO.Database.NodesandEdges;
import java.util.Hashtable;
import java.util.LinkedList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

class Graph {

  private int size;
  private Hashtable<String, Node> listOfNodes; // <NodeID, Node>
  // what is the purpose of this?
  private LinkedList<String> listOfNodeIDs;

  // default constructor
  Graph() {
    /*listOfNodeIDs = new LinkedList<String>();
    size = 0;
    listOfNodes = new Hashtable<>();*/
    this.initialize();
  }

  Graph(boolean test) {
    listOfNodeIDs = new LinkedList<String>();
    size = 0;
    listOfNodes = new Hashtable<>();
  }

  public void initialize() {
    // first reset everything (as though creating new graph):
    listOfNodeIDs = new LinkedList<String>();
    size = 0;
    listOfNodes = new Hashtable<>();

    // then do this stuff:
    Node node;

    // initialize a graph
    // new graph graph??

    // get the nodes (and edges) from the database function
    ObservableList<Node> nodeList = FXCollections.observableArrayList();
    nodeList = NodesandEdges.getAllNodes();

    ObservableList<Edge> edgeList = FXCollections.observableArrayList();
    edgeList = NodesandEdges.getAllEdges();

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
    }
  }

  LinkedList<String> getNodeIDList() {
    return listOfNodeIDs;
  }

  void addNode(Node node) {
    // put node into listOfNodes with nodeID as the key
    String key = node.getID();
    listOfNodes.put(key, node);
    listOfNodeIDs.add(node.getID()); // also add nodeID to listOfNodeIDs for easy access

    // increment size
    size++;
  }

  // adds a bi-directional edge to the nodes corresponding to both ID's
  void link(String node1ID, String node2ID) {
    // checks to make sure listOfNodes has both nodes before linking
    if (listOfNodes.containsKey(node1ID) && listOfNodes.containsKey(node2ID)) {
      // adds each node to each others list of neighbours
      listOfNodes.get(node1ID).addNeighbour(listOfNodes.get(node2ID));
      listOfNodes.get(node2ID).addNeighbour(listOfNodes.get(node1ID));
    }
  }

  Node getNode(String nodeID) {
    return listOfNodes.get(nodeID);
  }

  int getSize() {
    return size;
  }
}
