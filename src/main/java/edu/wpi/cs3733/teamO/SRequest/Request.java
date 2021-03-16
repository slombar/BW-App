package edu.wpi.cs3733.teamO.SRequest;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.util.Date;

public class Request extends RecursiveTreeObject<Request> {
  private int requestID;
  private String requestedBy;
  public String fulfilledBy;
  private Date dateRequested;
  private Date dateNeeded;
  private String summary;
  private String requestType;
  private String locationNodeID;

  public Request(
      int requestID,
      String requestedBy,
      String fulfilledBy,
      Date dateRequested,
      Date dateNeeded,
      String requestType,
      String locationNodeID,
      String summary) {
    this.requestID = requestID;
    this.requestedBy = requestedBy;
    this.dateRequested = dateRequested;
    this.requestType = requestType;
    this.fulfilledBy = fulfilledBy;
    this.dateNeeded = dateNeeded;
    this.locationNodeID = locationNodeID;
    this.summary = summary;
  }

  public Request() {}

  public int getRequestID() {
    return requestID;
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

  public String getLocationNodeID() {
    return locationNodeID;
  }

  public void setLocationNodeID(String locationNodeID) {
    this.locationNodeID = locationNodeID;
  }

  public String getFulfilledBy() {
    return fulfilledBy;
  }

  public void setFulfilledBy(String fulfilledBy) {
    this.fulfilledBy = fulfilledBy;
  }

  public String getRequestType() {
    return requestType;
  }

  public void setRequestType(String requestType) {
    this.requestType = requestType;
  }
}
