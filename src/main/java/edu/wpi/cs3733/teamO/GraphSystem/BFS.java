package edu.wpi.cs3733.teamO.GraphSystem;

import edu.wpi.cs3733.teamO.model.Node;
import java.util.LinkedList;
import java.util.Queue;

public class BFS {

  private Queue<Node> queue;
  private LinkedList<String> visited;

  BFS() {
    this.queue = new LinkedList<Node>();
    this.visited = new LinkedList<String>();
  }

  // main method for breadth-first search
  void bfs(Node n) {
    // mark root node as visited
    n.setVisited(true);
    queue.add(n);

    while (queue.size() != 0) {

      // dequeue node from queue and print it
      n = queue.poll();
      System.out.print(n.getID() + " ");

      for (Node g : n.getNeighbourList()) {
        if (!g.isVisited()) {
          g.setVisited(true);
          queue.add(g);
        }
      }
    }
  }

  // simple testing of bfs algorithm
  public static void main(String[] args) {
    Node n1 = new Node("1", 2, 3);
    Node n0 = new Node("0", 3, 2);
    Node n2 = new Node("2", 4, 3);
    Node n3 = new Node("3", 4, 5);

    /*
    n0.addNeighbour(n2,n0);
    n0.addNeighbour(n1);
    n2.addNeighbour(n0);
    n2.addNeighbour(n3);
    n1.addNeighbour(n2);
     */

    // output should be 2 0 3 1
    BFS bfs = new BFS();
    bfs.bfs(n2);
  }
}

