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

public ArrayList<Node> findPath(Node start, Node target){
  aStar(start, target);
  //TODO
  return null;
}

  public Node aStar(Node start, Node target) {
    for (Node n : graph.listOfNodes) {
      n.setPriority(0.0);
      n.setParent(null);
      n.distanceFromStart = 0;
    }
    TreeSet<Node> closedList = new TreeSet<>();
    TreeSet<Node> openList = new TreeSet<>();

    start.estimatedDistanceBetween = start.distanceFromStart + calculateHeuristic(start, target);
    openList.add(start);

    while(!openList.isEmpty()){
      Node n = openList.first();
      if(n == target){
        return n;
      }

      for(Node neighbor : graph.map.get(n)){
        double totalWeight = n.distanceFromStart + neighbor.weight;

        if(!openList.contains(neighbor) && !closedList.contains(neighbor)){
          neighbor.parent = n;
          neighbor.distanceFromStart = totalWeight;
          neighbor.estimatedDistanceBetween = neighbor.distanceFromStart + neighbor.calculateHeuristic(target);
          openList.add(neighbor);
        } else {
          if(totalWeight < neighbor.distanceFromStart){
            neighbor.parent = n;
            neighbor.distanceFromStart = totalWeight;
            neighbor.estimatedDistanceBetween = neighbor.distanceFromStart + neighbor.calculateHeuristic(target);

            if(closedList.contains(neighbor)){
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
    //TODO:
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
