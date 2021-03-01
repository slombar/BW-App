package edu.wpi.cs3733.teamO.GraphSystem;

import edu.wpi.cs3733.teamO.Database.NodesAndEdges;
import edu.wpi.cs3733.teamO.model.Node;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javafx.collections.ObservableList;

// main class of the 'edu.wpi.teamO.GraphSystem' subsystem
public class GraphSystem {

  // attributes
  Graph graph;
  AStarSearch aStarSearch;
  DFS dfs;
  BFS bfs;

  LinkedList<String> unreachableNodesforDFS; // nodeIDs of unreachable nodes via DFS
  LinkedList<String> unreachableNodesforBFS; // nodeIDs of unreachable nodes via BFS
  boolean hasUnreachableforDFS = false; // initial
  boolean hasUnreachableforBFS = false; // initial

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


  // DFS
  public boolean hasUnreachableNodesDFS() {
    initializeGraph(); // first, initialize map (bc CSV updated)
    dfs = new DFS(); // next, instantiate new DFS

    ObservableList<Node> listOfNodesforDFS = NodesAndEdges.getAllNodes();
    // run DFS:
    dfs.dfs(NodesAndEdges.getNode(listOfNodesforDFS.get(0).getID()));
    // get list of visited nodes:
    LinkedList<String> visitedNodes = dfs.getLLVisited();

    unreachableNodesforDFS = new LinkedList<>();

    for (Node n : listOfNodesforDFS) {
      if (!visitedNodes.contains(n)) {
        unreachableNodesforDFS.add(n.getID());
        hasUnreachableforDFS = true;
      }
    }

    return hasUnreachableforDFS;
  }

  //BFS
  public boolean hasUnreachableNodesBFS() {
    initializeGraph(); // first, initialize map (bc CSV updated)
    bfs = new BFS(); // next, instantiate new DFS

    ObservableList<Node> listOfNodesforBFS = NodesAndEdges.getAllNodes();
    // run BFS:
    bfs.bfs(NodesAndEdges.getNode(listOfNodesforBFS.get(0).getID()));
    // get list of visited nodes:
    Queue<Node> visitedNodes = bfs.getqueue();

    unreachableNodesforBFS = new LinkedList<>();

    for (Node n : listOfNodesforBFS) {
      if (!visitedNodes.contains(n)) {
        unreachableNodesforBFS.add(n.getID());
        hasUnreachableforBFS = true;
      }
    }

    return hasUnreachableforBFS;
  }

  public LinkedList<String> unreachableNodesforDFS() {
    return unreachableNodesforDFS;
  }
  public LinkedList<String> unreachableNodesforBFS() {
    return unreachableNodesforBFS;
  }

  //A star
  public LinkedList<String> findPathforAStar(String startID, String targetID) {
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

  //DFS: still working on it.
  public LinkedList<String> findPathforDFS(String startID, String targetID) {
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

  //BFS: still working on it.
  public LinkedList<String> findPathforBFS(String startID, String targetID) {
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
