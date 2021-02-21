package GraphSystem;

import java.util.LinkedList;

// main class of the 'GraphSystem' subsystem
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

  public void initializeGraph() {
    graph = new Graph();
  }

  boolean hasUnreachableNodes() {
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

  LinkedList<String> unreachableNodes() {
    return unreachableNodes;
  }

  LinkedList<String> findPath(String startID, String targetID) {
    initializeGraph();
    aStarSearch = new AStarSearch(graph, startID, targetID);
    LinkedList<GraphNode> route = aStarSearch.findRoute();

    LinkedList<String> routeIDs = new LinkedList<>();
    for (GraphNode node : route) {
      routeIDs.add(node.getNodeID());
    }

    return routeIDs;
  }
}
