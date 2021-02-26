package edu.wpi.cs3733.teamO.GraphSystem;

import edu.wpi.cs3733.teamO.model.Node;
import java.util.LinkedList;
import java.util.Stack;

class DFS {

  private Stack<Node> stack;
  private LinkedList<String> llVisited;

  // Constructor
  DFS() {
    this.stack = new Stack<>();
    llVisited = new LinkedList<String>();
  }

  // main method for depth-first search
  void dfs(Node g) {

    g.setVisited(true);
    llVisited.add(g.getID());
    dfsStack(g);
  }

  // Helper method for dfs method
  void dfsStack(Node rootNode) {
    this.stack.add(rootNode);
    rootNode.setVisited(true);

    while (!stack.isEmpty()) {

      Node actualNode = this.stack.pop();

      for (Node g : actualNode.getNeighbourList()) {
        if (!g.isVisited()) {
          g.setVisited(true);
          llVisited.add(g.getID());
          this.stack.push(g);
        }
      }
    }
  }

  LinkedList<String> getLLVisited() {
    return llVisited;
  }
}
