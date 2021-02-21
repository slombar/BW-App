package GraphSystem;

import java.util.LinkedList;
import java.util.Stack;

class DFS {

  private Stack<GraphNode> stack;
  private LinkedList<String> llVisited;

  // Constructor
  DFS() {
    this.stack = new Stack<>();
    llVisited = new LinkedList<String>();
  }

  // main method for depth-first search
  void dfs(GraphNode g) {
    /*for (GraphNode g : nodeList) {
      if (!g.isVisited()) {
        g.setVisited(true);
        llVisited.add(g);
        dfsStack(g);
      }
    }*/

    g.setVisited(true);
    llVisited.add(g.getNodeID());
    dfsStack(g);
  }

  // Helper method for dfs method
  // test comment
  void dfsStack(GraphNode rootNode) {
    this.stack.add(rootNode);
    rootNode.setVisited(true);

    while (!stack.isEmpty()) {

      GraphNode actualNode = this.stack.pop();
      // System.out.print(actualNode.getNodeID() + " "); // changed to Node.nodeID

      for (GraphNode g : actualNode.getNeighbourList()) {
        if (!g.isVisited()) {
          g.setVisited(true);
          llVisited.add(g.getNodeID());
          this.stack.push(g);
        }
      }
    }
  }

  LinkedList<String> getLLVisited() {
    return llVisited;
  }
}
