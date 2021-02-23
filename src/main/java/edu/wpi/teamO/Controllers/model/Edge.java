package edu.wpi.teamO.Controllers.model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class Edge extends RecursiveTreeObject<Edge> {
  String ID, start, end;

  public Edge(String ID, String start, String end) {
    this.ID = ID;
    this.start = start;
    this.end = end;
  }

  public Edge() {
    this.ID = null;
    this.start = null;
    this.end = null;
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
