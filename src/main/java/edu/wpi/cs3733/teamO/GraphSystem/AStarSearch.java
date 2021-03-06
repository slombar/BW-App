package edu.wpi.cs3733.teamO.GraphSystem;

import edu.wpi.cs3733.teamO.Database.Graph;
import edu.wpi.cs3733.teamO.model.Node;
import java.util.*;

public class AStarSearch implements AlgorithmStrategy {

  public AStarSearch() {
    this.graph = Graph.getInstance();
  }

  private static PriorityQueue<Node> frontier; // expanding frontier of search
  private static Hashtable<Node, Node> cameFrom; // NodeID and the NodeID of the node to get to it
  private static Hashtable<Node, Double> costSoFar; // NodeID and that nodes current cost so far

  Graph graph = Graph.getInstance();

  public ArrayList<Node> reverseArrayList(ArrayList<Node> alist) {

    ArrayList<Node> revArrayList = new ArrayList<Node>();
    for (int i = alist.size() - 1; i >= 0; i--) {
      // Append the elements in reverse order
      revArrayList.add(alist.get(i));
    }

    // Return the reversed arraylist
    return revArrayList;
  }

  public ArrayList<Node> findPath(Node start, Node target) {
    Node currentNode;
    currentNode = aStar(start, target);

    if (currentNode == null) {
      return null;
    }

    ArrayList<Node> path = new ArrayList<>();

    // while there is still nodes behind our current (parents)
    while (currentNode.getParent() != null) {
      path.add(currentNode);
      currentNode = currentNode.getParent();
    }

    path.add(currentNode);

    return reverseArrayList(path);
  }

  public Node aStar(Node start, Node target) {
    // get the list of nodes
    for (Node n : graph.listOfNodes) {
      // set the priority, the parent, and the distance from start
      n.setPriority(0.0);
      n.setParent(null);
      n.distanceFromStart = 0;
    }
    // make the open and closed lists for storing nodes we want to keep in our path and those we
    // don't want to visit anymore/call upon
    TreeSet<Node> closedList = new TreeSet<>();
    TreeSet<Node> openList = new TreeSet<>();

    // get the estimated distance between the start and the next node
    start.estimatedDistanceBetween = start.distanceFromStart + calculateHeuristic(start, target);
    // add to the list of nodes we want to view
    openList.add(start);

    // while the list is not empty(has nodes in it)
    while (!openList.isEmpty()) {

      Node n = openList.first();

      if (n == target) {
        return n;
      }

      // for each neighbor in the graph map
      for (Node neighbor : graph.map.get(n)) {

        // calculate the weight of the edge from the tart to the next neighbor
        double totalWeight = n.distanceFromStart + graph.getEdgeWeight(n, neighbor);

        // if the node is brand new, aka. has never been processed
        if (!openList.contains(neighbor) && !closedList.contains(neighbor)) {
          // set the parent ot the neighbor to be n
          neighbor.setParent(n);
          // set the distance from the start
          neighbor.distanceFromStart = totalWeight;

          neighbor.estimatedDistanceBetween =
              neighbor.distanceFromStart + calculateHeuristic(neighbor, target);
          // add the neighbor to the open path
          openList.add(neighbor);
          // if we have already visited this node
        } else {
          // if the total weight of this edge is less than the distance from the start of the
          // current neighbor, (found better route)
          if (totalWeight < neighbor.distanceFromStart) {
            neighbor.setParent(n);
            neighbor.distanceFromStart = totalWeight;
            neighbor.estimatedDistanceBetween =
                neighbor.distanceFromStart + calculateHeuristic(neighbor, target);

            if (closedList.contains(neighbor)) {
              closedList.remove(neighbor);
              openList.add(neighbor);
            }
          }
        }
      }
      openList.remove(n);
      closedList.add(n);
    }
    return null;
  }

  private double calculateHeuristic(Node start, Node target) {
    // TODO:
    return 0.0;
  }

  /**
   * @param next
   * @return
   */
  private static double heuristic(Node next) {
    // this method is literally just returning the dist between next and target Ryan you dummy
    // return dist(next, targetNode);
    return 0.0;
    // TODO: have this return an actual heuristic
  }
}
