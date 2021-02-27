package edu.wpi.cs3733.teamO.model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class Edge extends RecursiveTreeObject<Edge> {
  String ID, start, end;
  double length;

  public Edge(String ID, String start, String end, double length) {
    this.ID = ID;
    this.start = start;
    this.end = end;
    this.length = length;
  }

  public Edge() {
    this.ID = null;
    this.start = null;
    this.end = null;
    this.length = 0;
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

  public double getLength() {
    return length;
  }

  public void setLength(double length) {
    this.length = length;
  }
}
