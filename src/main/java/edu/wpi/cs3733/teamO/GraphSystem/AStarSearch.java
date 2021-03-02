package edu.wpi.cs3733.teamO.GraphSystem;

import edu.wpi.cs3733.teamO.model.Node;
import java.util.*;

class AStarSearch implements AlgorithmStrategy {

  private Graph graph;
  private int graphSize;

  private static PriorityQueue<Node> frontier; // expanding frontier of search
  private static Hashtable<Node, Node> cameFrom; // NodeID and the NodeID of the node to get to it
  private static Hashtable<Node, Double> costSoFar; // NodeID and that nodes current cost so far

  // private LinkedList<Node> foundRoute; // most recent found root for this A* object

  /*AStarSearch(boolean test) {
    graph = new Graph(test); // may not want/need to initialize graph here
    graphSize = -1;
    startID = "-1";
    targetID = "-1";
    frontier = new PriorityQueue<Node>();
  }*/

  // constructor
  AStarSearch(Graph g) {
    graph = g;
    graphSize = graph.getSize();

    //    frontier = new PriorityQueue<>();
    //    cameFrom = new Hashtable<>();
    //    costSoFar = new Hashtable<>();
    //    foundRoute = new LinkedList<>();
  }

  /**
   * OLD METHOD - returns route from startNode to endNode as a LL of NodeIDs
   *
   * @return LinkedList of Nodes
   */
  /*static List<String> findRouteOLD(Node startNode, Node endNode) {
    // sets start node based on startID provided in constructor

    // path, but in reverse order
    LinkedList<String> path = new LinkedList<>();

    frontier.add(startNode);
    cameFrom.put(startNode, "-1"); // didn't come from anywhere at start
    costSoFar.put(targetNode, 0.0); // didn't cost anything at start

    boolean foundPath = false;

    while (!frontier.isEmpty()) {
      Node current = frontier.poll(); // continues searching from next frontier node

      if (current.getID().equals(targetID)) {
        foundPath = true;
        break;
      }

      // iterates through current nodes neighbours
      int llsize = current.getNeighbourList().size();
      for (int i = 0; i < llsize; i++) {
        // gets next node in neighbours
        // sets next's cost so far to current's cost so far + edge cost
        Node next = current.getNeighbourList().iterator().next();
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
      path.add(startNode.getID());
      String cameFromID = targetID;

      while (!cameFromID.equals(startID)) { // goes until it appends startNode
        String nID = cameFrom.get(cameFromID);
        path.addFirst(nID);
        cameFromID = nID;
      }

      // DUMMY RETURN:
      return path;

    } else {
      return null;
    }
  }*/

  List<Node> findRoute(Node startNode, Node targetNode) {
    frontier = new PriorityQueue<>();
    cameFrom = new Hashtable<>();
    costSoFar = new Hashtable<>();

    // path, but in reverse order
    LinkedList<Node> path = new LinkedList<>();

    frontier.add(startNode);
    cameFrom.put(startNode, new Node()); // didn't come from anywhere at start
    costSoFar.put(startNode, 0.0); // didn't cost anything at start

    boolean foundPath = false;

    while (!frontier.isEmpty()) {
      Node current = frontier.poll(); // continues searching from next frontier node

      if (current.equals(targetNode)) {
        foundPath = true;
        break;
      }

      // iterates through current nodes neighbours
      int llsize = current.getNeighbourList().size();

      Set<Node> nList = current.getNeighbourList();

      Iterator<Node> iterator = nList.iterator();

      while (iterator.hasNext()) {
        // gets next node in neighbours
        // sets next's cost so far to current's cost so far + edge cost
        Node next = iterator.next();
        // TODO: change dist to get Edge length
        double newCost = costSoFar.get(current) + dist(current, next);

        // if cost to next hasn't been calculated yet, or if the newCost is less
        //    than the previously calc'ed cost, replace with newCost
        if (costSoFar.get(next) == null || newCost < costSoFar.get(next)) {
          costSoFar.put(next, newCost);

          // calculates priority (cost from start + distance to target --> lower is better)
          // TODO: change heuristic to take into account floor diff
          double priority = newCost + heuristic(next);
          next.setPriority(priority);
          frontier.add(next);
          cameFrom.put(next, current); // next came from current
        }
      }
    }

    if (foundPath) {
      // backtrack to add to path:
      // start by adding target node, then iterate through cameFrom,
      // appending next node to the front of path
      path.add(targetNode);
      Node cameFromNode = targetNode;

      while (!cameFromNode.equals(startNode)) { // goes until it appends startNode
        Node n = cameFrom.get(cameFromNode);
        path.addFirst(n);
        cameFromNode = n;
      }

      return path;

    } else {
      // TODO: throw PathNotFoundException or something
      return null;
    }
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

  // TODO: needs to take floors into account
  // finds distance between two nodes (length/weight of edge)
  static double dist(Node a, Node b) {
    int x1 = a.getXCoord();
    int x2 = b.getXCoord();
    int y1 = a.getYCoord();
    int y2 = b.getYCoord();

    // distance formula, sqrt((|x1-x2|)^2 + (|y1-y2|)^2),
    // probably can't use approximation (because calculating edge cost)
    double distSq = Math.pow(Math.abs(x1 - x2), 2.0) + Math.pow(Math.abs(y1 - y2), 2.0);
    return Math.sqrt(distSq);
  }
}
