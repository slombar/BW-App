package edu.wpi.cs3733.teamO.model;

import java.util.Objects;

public class Node {
  String ID, building, nodeType, longName, shortName, floor, team;
  int xCoord, yCoord;
  private boolean visible;
  private double priority;

  /**
   * Constructor for Node
   *
   * @param xCoord
   * @param yCoord
   * @param floor
   * @param building
   * @param nodeType
   * @param longName
   * @param team
   */
  public Node(
      int xCoord,
      int yCoord,
      String floor,
      String building,
      String nodeType,
      String longName,
      String team) {
    // TODO generate ID
    this.building = building;
    this.nodeType = nodeType;
    this.longName = longName;
    this.floor = floor;
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.team = team;
    this.visible = !nodeType.equals("WALK") && !nodeType.equals("HALL");

    this.ID = randIDGen();
  }

  public String randIDGen() {
    // generates random 4 digit number;
    Integer randID = ((int) (Math.random() * 10000));

    return team + nodeType + randID.toString() + floor;
  }

  // TODO get these ID's auto generating
  public Node(
      String nodeID,
      int xCoord,
      int yCoord,
      String floor,
      String building,
      String nodeType,
      String longName,
      String team) {

    this.ID = nodeID;
    this.building = building;
    this.nodeType = nodeType;
    this.longName = longName;
    this.floor = floor;
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.team = team;
    this.visible = !nodeType.equals("WALK") && !nodeType.equals("HALL");
  }

  public Node() {
    this.building = null;
    this.nodeType = null;
    this.longName = null;
    this.floor = null;
    this.xCoord = -11111;
    this.yCoord = -11111;
    this.team = null;
    this.visible = !nodeType.equals("WALK") && !nodeType.equals("HALL");
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Node node = (Node) o;
    return xCoord == node.xCoord
        && yCoord == node.yCoord
        && ID.equals(node.ID)
        && building.equals(node.building)
        && nodeType.equals(node.nodeType)
        && longName.equals(node.longName)
        && floor.equals(node.floor)
        && team.equals(node.team);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ID);
  }

  public String getTeam() {
    return team;
  }

  public void setTeam(String team) {
    this.team = team;
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

  public boolean isVisible() {
    return visible;
  }

  public void setVisible(boolean visible) {
    this.visible = visible;
  }

  public void setPriority(double p) {

    this.priority = p;
  }
}
