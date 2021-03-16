package edu.wpi.cs3733.teamO.SRequest;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.util.Date;

public class Request extends RecursiveTreeObject<Request> {
  private int requestID;
  private String requestedBy;
  private String assignedTo;
  private Date dateRequested;
  private Date dateNeeded;
  private String summary;
  private String requestType;
  private String requestLocation;
  private String status;

  public Request(
      int requestID,
      String requestedBy,
      String fulfilledBy,
      Date dateRequested,
      Date dateNeeded,
      String requestType,
      String requestLocation,
      String summary) {
    this.requestID = requestID;
    this.requestedBy = requestedBy;
    this.dateRequested = dateRequested;
    this.requestType = requestType;
    this.assignedTo = fulfilledBy;
    this.dateNeeded = dateNeeded;
    this.requestLocation = requestLocation;
    this.summary = summary;
    this.status = "Not Assigned";
  }

  public Request() {}

  public String getAssignedTo() {
    return assignedTo;
  }

  public void setAssignedTo(String assignedTo) {
    this.assignedTo = assignedTo;
  }

  public int getRequestID() {
    return requestID;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setRequestID(int requestID) {
    this.requestID = requestID;
  }

  public String getRequestedBy() {
    return requestedBy;
  }

  public void setRequestedBy(String requestedBy) {
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

  public String getRequestLocation() {
    return requestLocation;
  }

  public void setRequestLocation(String requestLocation) {
    this.requestLocation = requestLocation;
  }

  public String getRequestType() {
    return requestType;
  }

  public void setRequestType(String requestType) {
    this.requestType = requestType;
  }
}
