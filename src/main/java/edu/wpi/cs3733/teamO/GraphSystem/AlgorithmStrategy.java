package edu.wpi.cs3733.teamO.GraphSystem;

import edu.wpi.cs3733.teamO.Model.Node;
import java.util.List;

interface AlgorithmStrategy {

  /**
   * returns the list of Nodes representing th path from start to end found by the algorithm (in
   * order)
   *
   * @param startNode start Node of the search
   * @param targetNode destination Node of the search
   */
  List<Node> findRoute(Node startNode, Node targetNode);
}
