package edu.wpi.teamO.Controllers.model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class Node extends RecursiveTreeObject<Node> {
  String ID, building, nodeType, longName, shortName, floor, xCoord, yCoord;

  public Node(
      String ID,
      String xCoord,
      String yCoord,
      String floor,
      String building,
      String nodeType,
      String longName,
      String shortName) {
    this.ID = ID;
    this.building = building;
    this.nodeType = nodeType;
    this.longName = longName;
    this.shortName = shortName;
    this.floor = floor;
    this.xCoord = xCoord;
    this.yCoord = yCoord;
  }

  public Node() {
    this.ID = null;
    this.building = null;
    this.nodeType = null;
    this.longName = null;
    this.shortName = null;
    this.floor = null;
    this.xCoord = null;
    this.yCoord = null;
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

  public String getXCoord() {
    return xCoord;
  }

  public void setXCoord(String xCoord) {
    this.xCoord = xCoord;
  }

  public String getYCoord() {
    return yCoord;
  }

  public void setYCoord(String yCoord) {
    this.yCoord = yCoord;
  }
}
