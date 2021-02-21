package GraphSystem;

import java.util.LinkedList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestGraphNode {
  // GraphNode node1 = GraphNode(
  //       String nodeid, int xcoord, int ycoord,
  //      String building, String nodetype, String longname, String shortname, String teamassigned);
  GraphNode node1;
  GraphNode node2;

  @Before
  public void setUp() {
    node1 = new GraphNode("OWALK00201", 10, 10, "Faulkner", "WALK", "Walkway 2", "WALK_002", "O");

    node2 = new GraphNode("OWALK00301", 20, 30, "Faulkner", "WALK", "Walkway 3", "WALK_003", "O");
  }

  // Tests adding node2 to node1's list of neighbor nodes
  @Test
  public void testAddNeighbor() {
    node1.addNeighbour(node2);
    LinkedList<GraphNode> node1List = new LinkedList<GraphNode>();
    node1List.add(node2);
    Assert.assertEquals(node1.getNeighbourList(), node1List);
  }

  // Checks to see node1 isn't a neighbor of node1 after adding node2 to node1's List
  @Test
  public void testAddNeighborOneDirection() {
    node1.addNeighbour(node2);
    LinkedList<GraphNode> node2List = new LinkedList<GraphNode>();
    Assert.assertEquals(node2.getNeighbourList(), node2List);
  }

  // Tests whether the compareTo method returns 0 when both nodes have the same priority
  @Test
  public void testCompareToSamePriority() {
    node1.setPriority(2);
    node2.setPriority(2);
    Assert.assertEquals(0, node1.compareTo(node2));
  }

  // Tests whether the compareTo method returns a negative number when the node calling the method
  // has less
  //  priority than input node
  @Test
  public void testCompareToLessPriority() {
    node1.setPriority(2);
    node2.setPriority(3);
    Assert.assertEquals(true, node1.compareTo(node2) < 0);
  }

  // Tests whether the compareTo method returns a positive number when the node calling the method
  // has more
  //  priority than input node
  @Test
  public void testCompareToMorePriority() {
    node1.setPriority(2);
    node2.setPriority(3);
    Assert.assertEquals(true, node2.compareTo(node1) > 0);
  }
}
