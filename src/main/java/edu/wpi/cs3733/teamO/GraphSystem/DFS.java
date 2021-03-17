package edu.wpi.cs3733.teamO.GraphSystem;

import static edu.wpi.cs3733.teamO.GraphSystem.Graph.GRAPH;

import edu.wpi.cs3733.teamO.Model.Node;
import java.util.*;

public class DFS implements AlgorithmStrategy {
  /*
  private Graph graph;
  private int graphSize;

  BFS(Graph g) {
    graph = g;
    graphSize = graph.getSize();
  }

  ArrayList<Node> visited = new ArrayList<>();

  @Override
  public ArrayList<Node> findRoute(Node startNode, Node targetNode) {

    return findRouteforBFS(startNode, targetNode);
  }

  ArrayList<Node> path = new ArrayList<>();

  public ArrayList<Node> findRouteforBFS(Node startNode, Node targetNode) {
    if (visited.contains(startNode)) { // get node visited find its true or not.
      return null;
    } else {
      visited.add(startNode); // get node visited find its true or not.
    }
    ArrayList<Node> neighbors = new ArrayList<>();
    HashSet<Node> neighbors2 = startNode.getNeighbourList();
    for (Node N : neighbors2) {
      neighbors.add(N);
    }
    if (neighbors.size() != 0) {
      for (int i = 0; i < neighbors.size(); i++) {
        if (neighbors.get(i).getID().equals(targetNode.getID())) {
          path.add(neighbors.get(i));
          return path;
        }
      }
      // Run recursively on any non-visited
      for (int i = 0; i < neighbors.size(); i++) {
        if (!visited.contains(startNode)) {
          ArrayList<Node> breadth = findRouteforBFS(neighbors.get(i), targetNode); // getnode
          if (breadth != null) {
            // breadth.add(0, graph.); // add in
            return breadth;
          }
        }
      }
    }
    return null;
  }*/

  /*  public List<Node> findRouteForDFS(Node startNode, Node targetNode) {
    path.add(startNode);
    startNode.setVisited(true);


    HashSet<Node> allNeighbors = startNode.getNeighbourList();
    if (targetNode.equals(startNode)) {
      return path;
    } else {
      for (Node neighbor : allNeighbors) {
        if (!neighbor.isVisited()) {

          findRouteForDFS(neighbor, targetNode);
        }
      }
    }
    return path;
  } */

  @Override
  public List<Node> findRoute(Node startNode, Node targetNode) {

    for (Node N : GRAPH.getListOfNodes()) {
      N.setVisited(false);
    }

    if (startNode.equals(targetNode)) {}

    Stack<Node> nodeStack = new Stack<>();
    ArrayList<Node> visitedNodes = new ArrayList<>();

    nodeStack.add(startNode);

    while (!nodeStack.isEmpty()) {
      Node current = nodeStack.pop();
      if (!current.isVisited()) {
        if (current.equals(targetNode)) {

          visitedNodes.add(current);
          return visitedNodes;
        } else {
          current.setVisited(true);

          visitedNodes.add(current);
          nodeStack.addAll(current.getNeighbourList());
        }
      }
    }
    return null;
  }
}
