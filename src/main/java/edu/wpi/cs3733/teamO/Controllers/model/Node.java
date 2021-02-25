package edu.wpi.cs3733.teamO.Controllers.model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import java.util.LinkedList;

public class Node extends RecursiveTreeObject<Node> {
  String ID, building, nodeType, longName, shortName, floor, team;
  int xCoord, yCoord;

  //added from GraphNode
  private LinkedList<Node> neighbourList;
  private double priority;
  private boolean visited;

  /**
   * Constructor for Node
   * @param ID
   * @param xCoord
   * @param yCoord
   * @param floor
   * @param building
   * @param nodeType
   * @param longName
   * @param shortName
   * @param team
   */
  public Node(String ID, int xCoord, int yCoord, String floor, String building, String nodeType, String longName, String shortName, String team) {
    this.ID = ID;
    this.building = building;
    this.nodeType = nodeType;
    this.longName = longName;
    this.shortName = shortName;
    this.floor = floor;
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.team = team;
    this.visited = false;
    this.neighbourList = new LinkedList<>();
  }

  public Node(String nodeID, int x, int y){
    this.ID = nodeID;
    this.xCoord = x;
    this.yCoord = y;
    this.visited = false;
    this.neighbourList = new LinkedList<>();
  }

  public Node() {
    this.ID = null;
    this.building = null;
    this.nodeType = null;
    this.longName = null;
    this.shortName = null;
    this.floor = null;
    this.xCoord = 0;
    this.yCoord = 0;
  }

  public int compareTo(Node node) {
    return Double.compare(priority, node.getPriority());
  }

  public void addNeighbour(Node graphNode) {
    this.neighbourList.add(graphNode);
    graphNode.neighbourList.add(this);
  }

  public String getTeam() {
    return team;
  }

  public void setTeam(String team) {
    this.team = team;
  }

  public LinkedList<Node> getNeighbourList() {
    return neighbourList;
  }

  public void setNeighbourList(LinkedList<Node> neighbourList) {
    this.neighbourList = neighbourList;
  }

  public double getPriority() {
    return priority;
  }

  public void setPriority(double priority) {
    this.priority = priority;
  }

  public boolean isVisited() {
    return visited;
  }

  public void setVisited(boolean visited) {
    this.visited = visited;
  }

  public String getID() {
    return ID;
  }

  public void setID(String ID) {
    this.ID = ID;
  }

  public String getBuilding() {
    return building;
  }

  public void setBuilding(String building) {
    this.building = building;
  }

  public String getNodeType() {
    return nodeType;
  }

  public void setNodeType(String nodeType) {
    this.nodeType = nodeType;
  }

  public String getLongName() {
    return longName;
  }

  public void setLongName(String longName) {
    this.longName = longName;
  }

  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  public String getFloor() {
    return floor;
  }

  public void setFloor(String floor) {
    this.floor = floor;
  }

  public int getXCoord() {
    return xCoord;
  }

  public void setXCoord(int xCoord) {
    this.xCoord = xCoord;
  }

  public int getYCoord() {
    return yCoord;
  }

  public void setYCoord(int yCoord) {
    this.yCoord = yCoord;
  }
}
