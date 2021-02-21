package GraphSystem;

import java.util.LinkedList;
import java.util.List;

// main class of the 'GraphSystem' subsystem
public class GraphSystem {

  // attributes
  Graph graph;
  AStarSearch aStarSearch;
  DFS dfs;
  
  LinkedList<String> unreachableNodes;  // nodeIDs of unreachable nodes via DFS

  // constructor
  public GraphSystem() {
    // (semi-redundant) TODO: figure out how everything will end up initializing
    graph = new Graph(); // maybe have Graph initialize based on CSV?
    // could also be separate method so then it can easily update if CSV changes
    aStarSearch = new AStarSearch(); // maybe initialize in 'findPath()'
    // dfs = new DFS();  // possibly initialize in 'hasUnreachableNodes()'
  }

  public void initializeGraph() {
    graph = new Graph();
  }

  boolean hasUnreachableNodes() {
    initializeGraph(); // first, initialize map (bc CSV updated)
    dfs = new DFS(); // next, instantiate new DFS
    
    LinkedList<String> listOfNodes = graph.getNodeIDList();
    // run DFS:
    dfs.dfs(graph.getNode(listOfNodes.get(0)));
    // get list of visited nodes:
    LinkedList<String> visitedNodes = dfs.getLLVisited();
    
    boolean hasUnreachable = false;
    unreachableNodes = new LinkedList<>();
    
    for(String nodeID : listOfNodes) {
      if(!visitedNodes.contains(nodeID)) {
        unreachableNodes.add(nodeID);
        hasUnreachable = true;
      }
    }
    
    // dummy return
    return hasUnreachable;
  }
  
  LinkedList<String> unreachableNodes() {
    return unreachableNodes;
  }

  LinkedList<String> findPath(String startID, String targetID) {

    // dummmy return
    return new LinkedList<>();
  }
}
