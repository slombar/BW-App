/*package edu.wpi.teamO.GraphSystem;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.teamO.Controllers.DatabaseFunctionality;
import edu.wpi.teamO.Controllers.model.Node;
import java.util.LinkedList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestGraphSystem {

  GraphSystem gs1;
  GraphSystem gs2;
  ObservableList<Node> nodeList = FXCollections.observableArrayList();

  @BeforeAll
  public void setup() {
    gs1 = new GraphSystem();
    gs2 = new GraphSystem(true);
    nodeList = DatabaseFunctionality.showNodes(nodeList);
  }

  @Test
  public void testGraphInit() {
    boolean hasAllNodes = true;
    LinkedList<String> GNodeLL = gs1.graph.getNodeIDList();

    for (Node node : nodeList) {
      if (!GNodeLL.contains(node.getID())) {
        hasAllNodes = false;
        break;
      }
    }
  }

  @Test
  public void testFindPath() {
    GraphNode n1 = new GraphNode("node1", 1, 1);
    GraphNode n2 = new GraphNode("node2", 2, 1);
    GraphNode n3 = new GraphNode("node3", 4, 1);
    GraphNode n4 = new GraphNode("node4", 3, 2);
    GraphNode n5 = new GraphNode("node5", 1, 3);
    GraphNode n6 = new GraphNode("node6", 2, 4);
    GraphNode n7 = new GraphNode("node7", 4, 4);

    gs2.graph.link(n1.getNodeID(), n2.getNodeID());
    gs2.graph.link(n1.getNodeID(), n5.getNodeID());
    gs2.graph.link(n2.getNodeID(), n5.getNodeID());
    gs2.graph.link(n2.getNodeID(), n3.getNodeID());
    gs2.graph.link(n2.getNodeID(), n4.getNodeID());
    gs2.graph.link(n3.getNodeID(), n4.getNodeID());
    gs2.graph.link(n4.getNodeID(), n7.getNodeID());
    gs2.graph.link(n5.getNodeID(), n6.getNodeID());
    gs2.graph.link(n6.getNodeID(), n7.getNodeID());

    /////////////////////

    LinkedList<String> nodeList = new LinkedList<>();
    nodeList.add(n1.getNodeID());
    nodeList.add(n2.getNodeID());
    nodeList.add(n4.getNodeID());

    boolean flag = true;
    for (String n : gs2.findPath("node1", "node4")) {
      if (!nodeList.contains(n)) {
        flag = false;
      }
    }
    assertTrue(flag);
  }
}
*/
