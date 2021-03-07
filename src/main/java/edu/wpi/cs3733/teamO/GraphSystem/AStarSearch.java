package edu.wpi.cs3733.teamO.GraphSystem;

import edu.wpi.cs3733.teamO.Model.Node;
import java.util.*;

class AStarSearch extends AStarVariant implements AlgorithmStrategy {

  // private LinkedList<Node> foundRoute; // most recent found root for this A* object

  /*AStarSearch(boolean test) {
    graph = new Graph(test); // may not want/need to initialize graph here
    graphSize = -1;
    startID = "-1";
    targetID = "-1";
    frontier = new PriorityQueue<Node>();
  }*/

  /** creates a new AStarSearch object that will search on the given Graph */
  AStarSearch() {}

  /**
   * determines the heuristic from Node next to the target Node (must be less than the actual
   * distance)
   *
   * @param next the Node from which the heuristic is calculated
   * @param targetNode the target Node used in the heuristic calculation
   * @return heuristic <= actual distance from next to target
   */
  protected double heuristic(Node next, Node targetNode) {
    // this method is literally just returning the dist between next and target Ryan you dummy
    // return dist(next, targetNode);
    return 0.0;
    // TODO: have this return an actual heuristic
  }

  // TODO: needs to take floors into account

}
