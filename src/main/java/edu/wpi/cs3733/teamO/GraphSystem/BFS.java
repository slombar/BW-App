package edu.wpi.cs3733.teamO.GraphSystem;

import static edu.wpi.cs3733.teamO.GraphSystem.Graph.GRAPH;

import edu.wpi.cs3733.teamO.Model.Node;
import java.util.*;

public class BFS implements AlgorithmStrategy {

  @Override
  public List<Node> findRoute(Node startNode, Node targetNode) {

    for (Node N : GRAPH.getListOfNodes()) {
      N.setVisited(false);
    }

    Hashtable<Node, Node> cameFrom = new Hashtable<>();
    LinkedList<Node> path = new LinkedList<>();

    // Just so we handle receiving an uninitialized Node, otherwise an
    // exception will be thrown when we try to add it to queue
    if ((startNode == null) || (targetNode == null)) {
      return null;
    }

    // Creating the queue, and adding the first node (step 1)
    LinkedList<Node> queue = new LinkedList<>();
    queue.add(startNode);

    while (!queue.isEmpty()) {

      Node currentFirst = queue.removeFirst();

      if (currentFirst.equals(targetNode)) {
        break;
      }
      // In some cases we might have added a particular node more than once before
      // actually visiting that node, so we make sure to check and skip that node if we have
      // encountered it before
      if (currentFirst.isVisited()) {
        continue;
      }

      // Mark the node as visited
      currentFirst.setVisited(true);
      System.out.print(currentFirst.getID() + " ");

      HashSet<Node> allNeighbors = currentFirst.getNeighbourList();
      // We have to check whether the list of neighbors is null before proceeding, otherwise
      // the for-each loop will throw an exception
      if (allNeighbors == null) continue;

      for (Node neighbor : allNeighbors) {
        // We only add unvisited neighbors
        if (!neighbor.isVisited()) {
          cameFrom.put(neighbor, currentFirst);
          queue.add(neighbor);
        }
      }
    }

    // backtrack to add to path:
    // start by adding target node, then iterate through cameFrom,
    // appending next node to the front of path
    path.add(targetNode);
    Node cameFromNode = targetNode;

    while (!cameFromNode.equals(startNode)) { // goes until it appends startNode
      Node n = cameFrom.get(cameFromNode);
      // doesn't add consecutive elevator/stair nodes to path
      if ((cameFromNode.getNodeType().equals("STAI") || cameFromNode.getNodeType().equals("ELEV"))
          && !(n.getNodeType().equals("STAI") || n.getNodeType().equals("ELEV"))) {
        path.addFirst(cameFromNode);
        path.addFirst(n);
      } else if (!((cameFromNode.getNodeType().equals("STAI") && n.getNodeType().equals("STAI"))
          || (cameFromNode.getNodeType().equals("ELEV") && n.getNodeType().equals("ELEV")))) {
        path.addFirst(n);
      }

      cameFromNode = n;
    }

    return path;
  }
}
