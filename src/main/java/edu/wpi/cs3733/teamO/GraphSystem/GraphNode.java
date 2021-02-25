package edu.wpi.cs3733.teamO.GraphSystem;

import java.util.LinkedList;

class GraphNode implements Comparable<GraphNode> {

  private String nodeID;
  private int xCoord;
  private int yCoord;
  private boolean visited;
  private String building;
  private String nodeType;
  private String longName;
  private String shortName;
  private String teamAssigned;
  private LinkedList<GraphNode> neighbourList;
  private double priority; // used to order PrioQueue in A* (less is better)

  // blank constructor for testing
  GraphNode() {}

  // all parameter; I guess
  GraphNode(
      String nodeid,
      int xcoord,
      int ycoord,
      String building,
      String nodetype,
      String longname,
      String shortname,
      String teamassigned) {
    this.nodeID = nodeid;
    this.xCoord = xcoord;
    this.yCoord = ycoord;
    this.building = building;
    this.nodeType = nodetype;
    this.longName = longname;
    this.shortName = shortname;
    this.teamAssigned = teamassigned;
    this.visited = false;
    this.neighbourList = new LinkedList<>();
  }

  // some constructor; simple one
  GraphNode(String nodeid, int xcoord, int ycoord) {
    this.nodeID = nodeid;
    this.xCoord = xcoord;
    this.yCoord = ycoord;
    this.visited = false;
    this.neighbourList = new LinkedList<>();
  }

  // compareTo() IMPLEMENTATION:
  public int compareTo(GraphNode node) {
    return Double.compare(priority, node.getPriority());
  }

  void addNeighbour(GraphNode graphNode) {
    this.neighbourList.add(graphNode);
    graphNode.neighbourList.add(this);
  }

  boolean isVisited() {
    return visited;
  }

  void setVisited(boolean visited) {
    this.visited = visited;
  }

  LinkedList<GraphNode> getNeighbourList() {
    return neighbourList;
  }

  void setNeighbourList(LinkedList<GraphNode> neighbourList) {
    this.neighbourList = neighbourList;
  }

  String getNodeID() {
    return nodeID;
  }

  void setNodeID(String nodeID) {
    this.nodeID = nodeID;
  }

  double getPriority() {
    return priority;
  }

  void setPriority(double p) {
    priority = p;
  }

  int getX() {
    return xCoord;
  }

  void setX(int xCoord) {
    this.xCoord = xCoord;
  }

  int getY() {
    return yCoord;
  }

  void setY(int yCoord) {
    this.yCoord = yCoord;
  }
}
