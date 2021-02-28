package edu.wpi.cs3733.teamO.GraphSystem;

import edu.wpi.cs3733.teamO.Database.NodesAndEdges;
import edu.wpi.cs3733.teamO.model.Node;
import java.util.LinkedList;
import java.util.List;
import javafx.collections.ObservableList;

// main class of the 'edu.wpi.teamO.GraphSystem' subsystem
public class GraphSystem {

  // attributes
  Graph graph;
  AStarSearch aStarSearch;
  DFS dfs;

  LinkedList<String> unreachableNodes; // nodeIDs of unreachable nodes via DFS

  // constructor
  public GraphSystem() {
    this.graph = new Graph(); // maybe have Graph initialize based on CSV?
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

    ObservableList<Node> listOfNodes = NodesAndEdges.getAllNodes();
    // run DFS:
    dfs.dfs(NodesAndEdges.getNode(listOfNodes.get(0).getID()));
    // get list of visited nodes:
    LinkedList<String> visitedNodes = dfs.getLLVisited();

    boolean hasUnreachable = false;
    unreachableNodes = new LinkedList<>();

    for (Node n : listOfNodes) {
      if (!visitedNodes.contains(n)) {
        unreachableNodes.add(n.getID());
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
    List<String> route =
        AStarSearch.findRoute(NodesAndEdges.getNode(startID), NodesAndEdges.getNode(targetID));

    LinkedList<String> routeIDs = new LinkedList<>();
    for (String node : route) {

      routeIDs.add(NodesAndEdges.getNode(node).getID());
    }

    return routeIDs;
  }
}
