package GraphSystem;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.PriorityQueue;

class AStarSearch {

  private Graph graph;
  private int graphSize;
  private String startID;
  private String targetID;

  private PriorityQueue<GraphNode> frontier; // expanding frontier of search
  private Hashtable<String, String> cameFrom; // NodeID and the NodeID of the node to get to it
  private Hashtable<String, Double> costSoFar; // NodeID and that nodes current cost so far

  private LinkedList<GraphNode> foundRoute; // most recent found root for this A* object

  // need default constructor (for subsystem design)
  AStarSearch() {
    // just initializing everything for now
    // TODO: figure out if/how things should be initialized here
    graph = new Graph(); // may not want/need to initialize graph here
    graphSize = -1;
    startID = "-1";
    targetID = "-1";
    frontier = new PriorityQueue<GraphNode>();
  }

  // constructor
  AStarSearch(Graph g, String start, String target) {
    graph = g;
    graphSize = graph.getSize();
    startID = start;
    targetID = target;

    frontier = new PriorityQueue<>();
    cameFrom = new Hashtable<>();
    costSoFar = new Hashtable<>();
    foundRoute = new LinkedList<>();
  }

  // actual search method
  // returns found route (in order) as LL<GraphSystem.GraphNode>
  LinkedList<GraphNode> findRoute() {
    // sets start node based on startID provided in constructor
    GraphNode startNode = graph.getNode(startID);

    // path, but in reverse order
    LinkedList<GraphNode> path = new LinkedList<>();

    frontier.add(startNode);
    cameFrom.put(startID, "-1"); // didn't come from anywhere at start
    costSoFar.put(startID, 0.0); // didn't cost anything at start

    boolean foundPath = false;

    while (!frontier.isEmpty()) {
      GraphNode current = frontier.poll(); // continues searching from next frontier node

      if (current.getNodeID().equals(targetID)) {
        foundPath = true;
        break;
      }

      // iterates through current nodes neighbours
      int llsize = current.getNeighbourList().size();
      for (int i = 0; i < llsize; i++) {
        // gets next node in neighbours
        // sets next's cost so far to current's cost so far + edge cost
        GraphNode next = current.getNeighbourList().get(i);
        double newCost = costSoFar.get(current.getNodeID()) + dist(current, next);

        // if cost to next hasn't been calculated yet, or if the newCost is less
        //    than the previously calc'ed cost, replace with newCost
        if (costSoFar.get(next.getNodeID()) == null || newCost < costSoFar.get(next.getNodeID())) {
          costSoFar.put(next.getNodeID(), newCost);

          // calculates priority (cost from start + distance to target --> lower is better)
          double priority = newCost + heuristic(next);
          next.setPriority(priority);
          frontier.add(next);
          cameFrom.put(next.getNodeID(), current.getNodeID()); // next came from current
        }
      }
    }

    if (foundPath) {
      // backtrack to add to path:
      // start by adding target node, then iterate through cameFrom,
      // appending next node to the front of path
      path.add(graph.getNode(targetID));
      String cameFromID = targetID;

      while (!cameFromID.equals(startID)) { // goes until it appends startNode
        String nID = cameFrom.get(cameFromID);
        path.addFirst(graph.getNode(nID));
        cameFromID = nID;
      }

      // DUMMY RETURN:
      return path;
    } else {
      return new LinkedList<GraphNode>();
    }
  }

  // helper for findRoute()
  // first parameter is next node being added to frontier,
  // and it compares to target node
  private double heuristic(GraphNode next) {
    int x1 = next.getX();
    int x2 = graph.getNode(targetID).getX();
    int y1 = next.getY();
    int y2 = graph.getNode(targetID).getY();

    // normal distance formula, sqrt((|x1-x2|)^2 + (|y1-y2|)^2)
    // might need to change in favor of faster approximation
    //  but an approximation needs to be LESS than the actual distance
    //  (actual distance might mean actual distance via graph??)
    double distSq = Math.pow(Math.abs(x1 - x2), 2.0) + Math.pow(Math.abs(y1 - y2), 2.0);
    return Math.sqrt(distSq);
  }

  // finds distance between two nodes (length/weight of edge)
  private double dist(GraphNode a, GraphNode b) {
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
