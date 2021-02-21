package GraphSystem;

import java.util.LinkedList;

import GraphSystem.GraphNode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// ^ JUST MAKING SURE YOU KNOW THE ABOVE IS NECESSARY FOR SHIT TO WORK

public class TestGraph {

  // Graph graph;
  GraphNode n1,n2,n3,n4,n5,n6,n7;
  Graph testGraph;
  @BeforeAll
  public void setUp() {
    GraphNode n1 = new GraphNode("node1", 1, 1);
    GraphNode n2 = new GraphNode("node2", 2, 1);
    GraphNode n3 = new GraphNode("node3", 4, 1);
    GraphNode n4 = new GraphNode("node4", 3, 2);
    GraphNode n5 = new GraphNode("node5", 1, 3);
    GraphNode n6 = new GraphNode("node6", 2, 4);
    GraphNode n7 = new GraphNode("node7", 4, 4);

    n1.addNeighbour(n2); n1.addNeighbour(n5);
    n2.addNeighbour(n5); n2.addNeighbour(n3); n2.addNeighbour(n4);
    n3.addNeighbour(n4);
    n4.addNeighbour(n7);
    n5.addNeighbour(n6);
    n6.addNeighbour(n7);

    testGraph = new Graph();
    testGraph.addNode(n1);
    testGraph.addNode(n2);
    testGraph.addNode(n3);
    testGraph.addNode(n4);
    testGraph.addNode(n5);
    testGraph.addNode(n6);
    testGraph.addNode(n7);

    testGraph.link(n1.getNodeID(),n2.getNodeID());
    testGraph.link(n1.getNodeID(),n5.getNodeID());
    testGraph.link(n2.getNodeID(),n5.getNodeID());
    testGraph.link(n2.getNodeID(),n3.getNodeID());
    testGraph.link(n2.getNodeID(),n4.getNodeID());
    testGraph.link(n3.getNodeID(),n4.getNodeID());
    testGraph.link(n4.getNodeID(),n7.getNodeID());
    testGraph.link(n5.getNodeID(),n6.getNodeID());
    testGraph.link(n6.getNodeID(),n7.getNodeID());
  }

  @Test
  public void testAddNode() {
    assertEquals(n1, testGraph.getNode(n1.getNodeID()));
  }
}
