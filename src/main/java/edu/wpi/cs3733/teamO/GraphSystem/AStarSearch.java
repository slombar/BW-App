package edu.wpi.cs3733.teamO.GraphSystem;

import edu.wpi.cs3733.teamO.model.Node;

import java.util.*;

class AStarSearch {

  private Graph graph;
  private int graphSize;
  private String startNode;
  private String targetNode;

  private PriorityQueue<Node> frontier; // expanding frontier of search
  private Hashtable<String, String> cameFrom; // NodeID and the NodeID of the node to get to it
  private Hashtable<String, Double> costSoFar; // NodeID and that nodes current cost so far

  private LinkedList<Node> foundRoute; // most recent found root for this A* object

  // need default constructor (for subsystem design)
  /*AStarSearch() {
    graph = new Graph(); // may not want/need to initialize graph here
    graphSize = -1;
    startID = "-1";
    targetID = "-1";
    frontier = new PriorityQueue<Node>();
  }*/

  AStarSearch(boolean test) {
    graph = new Graph(test); // may not want/need to initialize graph here
    graphSize = -1;
    startNode = "-1";
    targetNode = "-1";
    frontier = new PriorityQueue<Node>();
  }

  // constructor
  AStarSearch(Graph g, String startID, String targetID) {
    graph = g;
    graphSize = graph.getSize();
    startNode = startID;
    targetNode = targetID;

    frontier = new PriorityQueue<>();
    cameFrom = new Hashtable<>();
    costSoFar = new Hashtable<>();
    foundRoute = new LinkedList<>();
  }

  // actual search method
  // returns found route (in order) as LL<edu.wpi.teamO.GraphSystem.Node>
  List<Node> findRoute() {
    // sets start node based on startID provided in constructor
    Node startNode = graph.getNode(this.startNode);

    // path, but in reverse order
    LinkedList<Node> path = new LinkedList<>();

    frontier.add(startNode);
    cameFrom.put(this.startNode, "-1"); // didn't come from anywhere at start
    costSoFar.put(this.startNode, 0.0); // didn't cost anything at start

    boolean foundPath = false;

    while (!frontier.isEmpty()) {
      Node current = frontier.poll(); // continues searching from next frontier node

      if (current.getID().equals(targetNode)) {
        foundPath = true;
        break;
      }

      // iterates through current nodes neighbours
      for (Node next : current.getNeighbourList()) {
        // sets next's cost so far to current's cost so far + edge cost
        double newCost = costSoFar.get(current.getID()) + dist(current, next);

        // if cost to next hasn't been calculated yet, or if the newCost is less
        //    than the previously calc'ed cost, replace with newCost
        if (costSoFar.get(next.getID()) == null || newCost < costSoFar.get(next.getID())) {
          costSoFar.put(next.getID(), newCost);

          // calculates priority (cost from start + distance to target --> lower is better)
          double priority = newCost + heuristic(next);
          next.setPriority(priority);
          frontier.add(next);
          cameFrom.put(next.getID(), current.getID()); // next came from current
        }
      }
    }

    if (foundPath) {
      // backtrack to add to path:
      // start by adding target node, then iterate through cameFrom,
      // appending next node to the front of path
      path.add(graph.getNode(targetNode));
      String cameFromID = targetNode;

      while (!cameFromID.equals(this.startNode)) { // goes until it appends startNode
        String nID = cameFrom.get(cameFromID);
        path.addFirst(graph.getNode(nID));
        cameFromID = nID;
      }

      // DUMMY RETURN:
      return path;
    } else {
      return new LinkedList<Node>();
    }
  }

  /**
   * @param next
   * @return
   */
  private double heuristic(Node next) {
    // this method is literally just returning the dist between next and target Ryan you dummy
    return dist(next, graph.getNode(targetNode));
  }

  // finds distance between two nodes (length/weight of edge)
  double dist(Node a, Node b) {
    int x1 = a.getX();
    int x2 = b.getX();
    int y1 = a.getY();
    int y2 = b.getY();

    // distance formula, sqrt((|x1-x2|)^2 + (|y1-y2|)^2),
    // probably can't use approximation (because calculating edge cost)
    double distSq = Math.pow(Math.abs(x1 - x2), 2.0) + Math.pow(Math.abs(y1 - y2), 2.0);
    return Math.sqrt(distSq);
  }
}
