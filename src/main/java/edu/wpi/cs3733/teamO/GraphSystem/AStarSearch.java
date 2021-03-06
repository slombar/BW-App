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

  /**
   * creates a new AStarSearch object that will search on the given Graph
   * @param g Graph to be searched on
   */
  AStarSearch(Graph g) {
    graph = g;
    graphSize = graph.getSize();

    //    frontier = new PriorityQueue<>();
    //    cameFrom = new Hashtable<>();
    //    costSoFar = new Hashtable<>();
    //    foundRoute = new LinkedList<>();
  }

  /**
   * returns the list of Nodes representing the shortest path from the start Node to the end Node (in order)
   * @param startNode start Node of the search
   * @param targetNode destination Node of the search
   * @return LinkedList of Nodes from start to end in order
   */
  public List<Node> findRoute(Node startNode, Node targetNode) {
    for (Node n : graph.getListOfNodes()) {
      n.setPriority(0.0);
    }

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
          double priority = newCost + heuristic(next, targetNode);
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
   * determines the heuristic from Node next to the target Node (must be less than the actual distance)
   * @param next the Node from which the heuristic is calculated
   * @param targetNode the target Node used in the heuristic calculation
   * @return heuristic <= actual distance from next to target
   */
  private static double heuristic(Node next, Node targetNode) {
    // this method is literally just returning the dist between next and target Ryan you dummy
    //return dist(next, targetNode);
    return 0.0;
    // TODO: have this return an actual heuristic
  }

  // TODO: needs to take floors into account
  // finds distance between two nodes (length/weight of edge)
  /**
   * calculates distance from Node a to Node b
   * @param a first Node
   * @param b second Node
   * @return sqrt((|x1-x2|)^2 + (|y1-y2|)^2)
   */
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
