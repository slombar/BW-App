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
  LinkedList<Node> dfs(Node g, Node end) {

    if (g.equals(end)) new LinkedList<Node>();
    g.setVisited(true);
    llVisited.add(g.getID());
    LinkedList<Node> path = dfsStack(g, end);
    return path;
  }

  // Helper method for dfs method
  LinkedList<Node> dfsStack(Node rootNode, Node end) {
    this.stack.add(rootNode);
    rootNode.setVisited(true);
    LinkedList<Node> path = new LinkedList<>();

    while (!stack.isEmpty()) {

      Node actualNode = this.stack.pop();

      for (Node g : actualNode.getNeighbourList()) {
        if (!g.isVisited()) {
          g.setVisited(true);
          llVisited.add(g.getID());
          this.stack.push(g);
          path.add(g);
          if(g.equals(end)) return path;
        }
      }
    }
    return new LinkedList<>(); // returns empty LinkedList if endNode wasn't found
  }

  LinkedList<String> getLLVisited() {
    return llVisited;
  }
}
