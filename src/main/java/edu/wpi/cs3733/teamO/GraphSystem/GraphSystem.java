package edu.wpi.cs3733.teamO.GraphSystem;

import edu.wpi.cs3733.teamO.Controllers.model.Node;
import java.util.LinkedList;

// main class of the 'edu.wpi.teamO.GraphSystem' subsystem
public class GraphSystem {

  // attributes
  Graph graph;
  AStarSearch aStarSearch;
  DFS dfs;

  LinkedList<String> unreachableNodes; // nodeIDs of unreachable nodes via DFS

  // constructor
  public GraphSystem() {
    graph = new Graph(); // maybe have Graph initialize based on CSV?
  }

  public GraphSystem(boolean test) {
    graph = new Graph(true);
  }

  public void initializeGraph() {
    graph.initialize();
  }

  public boolean hasUnreachableNodes() {
    initializeGraph(); // first, initialize map (bc CSV updated)
    dfs = new DFS(); // next, instantiate new DFS

    LinkedList<String> listOfNodes = graph.getNodeIDList();
    // run DFS:
    dfs.dfs(graph.getNode(listOfNodes.get(0)));
    // get list of visited nodes:
    LinkedList<String> visitedNodes = dfs.getLLVisited();

    boolean hasUnreachable = false;
    unreachableNodes = new LinkedList<>();

    for (String nodeID : listOfNodes) {
      if (!visitedNodes.contains(nodeID)) {
        unreachableNodes.add(nodeID);
        hasUnreachable = true;
      }
    }

    return hasUnreachable;
  }

  public LinkedList<String> unreachableNodes() {
    return unreachableNodes;
  }

  public LinkedList<String> findPath(String startID, String targetID) {
    initializeGraph();
    aStarSearch = new AStarSearch(graph, startID, targetID);
    LinkedList<Node> route = aStarSearch.findRoute();

    LinkedList<String> routeIDs = new LinkedList<>();
    for (Node node : route) {
      routeIDs.add(node.getID());
    }

    return routeIDs;
  }
}