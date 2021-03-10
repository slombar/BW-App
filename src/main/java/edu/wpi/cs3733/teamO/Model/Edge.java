package edu.wpi.cs3733.teamO.Model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class Edge extends RecursiveTreeObject<Edge> {
  String ID, start, end;

  public Edge(String ID, String startNode, String endNode) {
    this.ID = ID;
    this.start = startNode;
    this.end = endNode;
  }

  public Edge(String id) {
    this.ID = id;
    String[] split = id.split("_");
    this.start = split[0];
    this.end = split[1];
  }

  public String getID() {
    return ID;
  }

  public void setID(String ID) {
    this.ID = ID;
  }

  public String getStart() {
    return start;
  }

  public void setStart(String start) {
    this.start = start;
  }

  public String getEnd() {
    return end;
  }

  public void setEnd(String end) {
    this.end = end;
  }
}
