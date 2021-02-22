package edu.wpi.teamO.GraphSystem;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// ^ JUST MAKING SURE YOU KNOW THE ABOVE IS NECESSARY FOR SHIT TO WORK

public class TestGraph {

  // Graph graph;
  static GraphNode n1, n2, n3, n4, n5, n6, n7;
  static Graph testGraph;
  static LinkedList<String> nodeIDs = new LinkedList<String>();

  @BeforeEach
  public void setUp() {
    n1 = new GraphNode("node1", 1, 1);
    n2 = new GraphNode("node2", 2, 1);
    n3 = new GraphNode("node3", 4, 1);
    n4 = new GraphNode("node4", 3, 2);
    n5 = new GraphNode("node5", 1, 3);
    n6 = new GraphNode("node6", 2, 4);
    n7 = new GraphNode("node7", 4, 4);

    nodeIDs.add(n1.getNodeID());
    nodeIDs.add(n2.getNodeID());
    nodeIDs.add(n3.getNodeID());
    nodeIDs.add(n4.getNodeID());
    nodeIDs.add(n5.getNodeID());
    nodeIDs.add(n6.getNodeID());
    nodeIDs.add(n7.getNodeID());

    testGraph = new Graph();
    testGraph.addNode(n1);
    testGraph.addNode(n2);
    testGraph.addNode(n3);
    testGraph.addNode(n4);
    testGraph.addNode(n5);
    testGraph.addNode(n6);
    testGraph.addNode(n7);

    testGraph.link(n1.getNodeID(), n2.getNodeID());
    testGraph.link(n1.getNodeID(), n5.getNodeID());
    testGraph.link(n2.getNodeID(), n5.getNodeID());
    testGraph.link(n2.getNodeID(), n3.getNodeID());
    testGraph.link(n2.getNodeID(), n4.getNodeID());
    testGraph.link(n3.getNodeID(), n4.getNodeID());
    testGraph.link(n4.getNodeID(), n7.getNodeID());
    testGraph.link(n5.getNodeID(), n6.getNodeID());
    testGraph.link(n6.getNodeID(), n7.getNodeID());
  }

  // Tests the addNode function of a graph to see if nodes were successfully added
  @Test
  public void testAddNode() {
    assertEquals(n1, testGraph.getNode(n1.getNodeID()));
  }

  // Tests if the method getSize returns the how many nodes are in the list
  @Test
  public void testGetSize() {
    assertEquals(7, testGraph.getSize());
  }

  // Tests if the getNodeIDList returns a list of all node IDs of nodes
  // added to the graph.
  @Test
  public void testGetNodeIDs() {
    boolean flag = true;
    for (String id : nodeIDs) {
      if (!testGraph.getNodeIDList().contains(id)) {
        flag = false;
      }
    }
    assertTrue(flag);
  }

  // Tests to see whether linking 2 nodes together adds them to each
  // neighbor's list when one of the node doesn't exist in the graph
  @Test
  public void testLinkNoNode() {
    GraphNode n8 = new GraphNode("node 8", 8, 8);

    testGraph.link(n7.getNodeID(), n8.getNodeID());

    assertFalse(n7.getNeighbourList().contains(n8));
  }

  // Tests to see whether linking 2 nodes together adds them to each
  // neighbor's list.
  @Test
  public void testLink() {
    GraphNode n9 = new GraphNode("node 9", 9, 9);
    testGraph.addNode(n9);
    testGraph.link(n7.getNodeID(), n9.getNodeID());

    assertTrue(n7.getNeighbourList().contains(n9));
  }
}
