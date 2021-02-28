package edu.wpi.cs3733.teamO.SRequest;

import edu.wpi.cs3733.teamO.Database.NodesAndEdges;
import edu.wpi.cs3733.teamO.UserTypes.Staff;
import edu.wpi.cs3733.teamO.model.Node;
import java.util.Date;

public class Request {
  private String requestID;
  private Staff requestedBy;
  public Staff fulfilledBy;
  private Date dateRequested;
  private Date dateNeeded;
  private String summary;
  private String requestType;
  private String locationNodeID;
  private Node locationNode;

  public Request(
      String requestID,
      Staff requestedBy,
      Staff fulfilledBy,
      Date dateRequested,
      Date dateNeeded,
      String requestType,
      String summary,
      String locationNodeID) {
    this.requestID = requestID;
    this.requestedBy = requestedBy;
    this.dateRequested = dateRequested;
    this.requestType = requestType;
    this.fulfilledBy = fulfilledBy;
    this.dateNeeded = dateNeeded;
    this.summary = summary;
    this.locationNodeID = locationNodeID;
    this.locationNode = NodesAndEdges.getNode(locationNodeID);
  }

  public String getRequestID() {
    return requestID;
  }

  public void setRequestID(String requestID) {
    this.requestID = requestID;
  }

  public Staff getRequestedBy() {
    return requestedBy;
  }

  public void setRequestedBy(Staff requestedBy) {
    this.requestedBy = requestedBy;
  }

  public Date getDateRequested() {
    return dateRequested;
  }

  public void setDateRequested(Date dateRequested) {
    this.dateRequested = dateRequested;
  }

  public Date getDateNeeded() {
    return dateNeeded;
  }

  public void setDateNeeded(Date dateNeeded) {
    this.dateNeeded = dateNeeded;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public String getLocationNodeID() {
    return locationNodeID;
  }

  public void setLocationNodeID(String locationNodeID) {
    this.locationNodeID = locationNodeID;
  }

  public Node getLocationNode() {
    return locationNode;
  }

  public void setLocationNode(Node locationNode) {
    this.locationNode = locationNode;
  }
}
