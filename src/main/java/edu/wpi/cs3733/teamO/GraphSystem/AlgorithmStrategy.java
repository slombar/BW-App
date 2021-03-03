package edu.wpi.cs3733.teamO.GraphSystem;

import edu.wpi.cs3733.teamO.model.Node;
import java.util.List;

interface AlgorithmStrategy {

  List<Node> findRoute(Node startNode, Node targetNode);
}
