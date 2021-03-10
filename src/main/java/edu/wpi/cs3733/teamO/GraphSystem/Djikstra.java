package edu.wpi.cs3733.teamO.GraphSystem;

import edu.wpi.cs3733.teamO.Model.Node;
import java.util.*;

public class Djikstra extends AStarVariant implements AlgorithmStrategy {

  protected Djikstra() {}

  /**
   * determines the heuristic from Node next to the target Node (must be less than the actual
   * distance)
   *
   * @param next the Node from which the heuristic is calculated
   * @param targetNode the target Node used in the heuristic calculation
   * @return 0.0 because thats how djikstra works
   */
  protected double heuristic(Node next, Node targetNode) {
    return 0.0;
  }
}
