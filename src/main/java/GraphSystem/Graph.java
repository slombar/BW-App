package GraphSystem;

import java.util.Hashtable;
import java.util.LinkedList;

class Graph {

  private int size;
  private Hashtable<String, GraphNode> listOfNodes; // <NodeID, Node>
  private LinkedList<String> listOfNodeIDs;

  // default constructor
  Graph() {
    // TODO: figure out how Graph should end up being initialized/setup
    // (haven't changed this constructor yet -- it may not need to?)
    size = 0;
    listOfNodes = new Hashtable<>();
  }

  void initializeGraph() {
    //
    // Graph initialization (based on CSV files) goes here
    //
  }

  LinkedList<String> getNodeIDList() {
    return listOfNodeIDs;
  }

  void addNode(GraphNode node) {
    // put node into listOfNodes with nodeID as the key
    String key = node.getNodeID();
    listOfNodes.put(key, node);
    listOfNodeIDs.add(node.getNodeID()); // also add nodeID to listOfNodeIDs for easy access

    // increment size
    size++;
  }

  // adds a bi-directional edge to the nodes corresponding to both ID's
  void link(String node1ID, GraphNode node2ID) {
    // checks to make sure listOfNodes has both nodes before linking
    if (listOfNodes.containsKey(node1ID) && listOfNodes.containsKey(node2ID)) {
      // adds each node to each others list of neighbours
      listOfNodes.get(node1ID).addNeighbour(listOfNodes.get(node2ID));
      listOfNodes.get(node2ID).addNeighbour(listOfNodes.get(node1ID));
    }
  }

  GraphNode getNode(String nodeID) {
    return listOfNodes.get(nodeID);
  }

  int getSize() {
    return size;
  }
}
