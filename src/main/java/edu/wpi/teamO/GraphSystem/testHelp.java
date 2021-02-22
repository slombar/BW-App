package edu.wpi.teamO.GraphSystem;

import edu.wpi.teamO.Controllers.DatabaseFunctionality;
import java.util.ArrayList;

public class testHelp {

  // 1st 2nd and last db entry
  public Graph makeTestGraph() {

    Graph graph = null;

    String nodeID1 = "OWALK00101"; // 1st db entry
    String nodeID2 = "OWALK00102"; // 2nd db entry
    String nodeID3 = "OEXIT00301"; // last db entry

    // grabs values from database
    ArrayList<String> node1 = DatabaseFunctionality.getNode(nodeID1);
    ArrayList<String> node2 = DatabaseFunctionality.getNode(nodeID2);
    ArrayList<String> node3 = DatabaseFunctionality.getNode(nodeID3);

    // create graph nodes
    GraphNode g1 = null;
    GraphNode g2 = null;
    GraphNode g3 = null;

    // sets the first graphnode's properties
    g1.setNodeID(node1.get(0)); // set id
    g1.setX(Integer.parseInt(node1.get(1))); // set x
    g1.setY(Integer.parseInt(node1.get(2))); // set y

    // sets the 2nd graphnodes props
    g2.setNodeID(node2.get(0)); // set id
    g2.setX(Integer.parseInt(node2.get(1))); // set x
    g2.setY(Integer.parseInt(node2.get(2))); // set y

    // sets the third graphnodes props
    g3.setNodeID(node3.get(0)); // set id
    g3.setX(Integer.parseInt(node3.get(1))); // set x
    g3.setY(Integer.parseInt(node3.get(2))); // set y

    graph.addNode(g1);
    graph.addNode(g2);
    graph.addNode(g3);

    return graph;
  }
}
