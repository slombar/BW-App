package edu.wpi.cs3733.teamO.GraphSystem;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestAStarSearch {
  static GraphNode n1, n2, n3, n4, n5, n6, n7;
  static Graph testGraph;

  @BeforeEach
  public void setUp() {
    n1 = new GraphNode("node1", 1, 1);
    n2 = new GraphNode("node2", 2, 1);
    n3 = new GraphNode("node3", 4, 1);
    n4 = new GraphNode("node4", 3, 2);
    n5 = new GraphNode("node5", 1, 3);
    n6 = new GraphNode("node6", 2, 4);
    n7 = new GraphNode("node7", 4, 4);

    testGraph = new Graph(true);
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

  // Tests the findRoute function of an AStar Search to see if the search program
  // can find a route between two consecutive nodes
  @Test
  public void testFindRouteConsecutiveNodes() {
    LinkedList<GraphNode> nodeList = new LinkedList<GraphNode>();
    nodeList.add(n1);
    nodeList.add(n2);

    AStarSearch starSearch = new AStarSearch(testGraph, n1.getNodeID(), n2.getNodeID());
    boolean flag = true;
    for (GraphNode n : starSearch.findRoute()) {
      if (!nodeList.contains(n)) {
        flag = false;
      }
    }
    assertTrue(flag);
  }

  // Tests the findRoute function of an AStar Search to see if the search program
  // can find a route between two nonconsecutive nodes
  @Test
  public void testFindRouteNonConsecutive() {
    LinkedList<GraphNode> nodeList = new LinkedList<GraphNode>();
    nodeList.add(n1);
    nodeList.add(n2);
    nodeList.add(n4);

    AStarSearch starSearch = new AStarSearch(testGraph, n1.getNodeID(), n4.getNodeID());
    boolean flag = true;
    for (GraphNode n : starSearch.findRoute()) {
      if (!nodeList.contains(n)) {
        flag = false;
      }
    }
    assertTrue(flag);
  }

  // Tests the dist function of an AStar Search to see if the search program
  // can find the distance between two consecutive nodes
  @Test
  public void testDistConsecutiveNodes() {

    AStarSearch starSearch = new AStarSearch(testGraph, n1.getNodeID(), n2.getNodeID());
    assertEquals(1, starSearch.dist(n1, n2), 0.01);
  }

  // Tests the dist function of an AStar Search to see if the search program
  // can find the distance between two nonconsecutive nodes
  @Test
  public void testDistNonConsecutive() {
    AStarSearch starSearch = new AStarSearch(testGraph, n1.getNodeID(), n4.getNodeID());
    assertEquals(2.23, starSearch.dist(n1, n4), 0.01);
  }
}
