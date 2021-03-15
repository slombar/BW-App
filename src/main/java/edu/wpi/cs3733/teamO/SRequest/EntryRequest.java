package edu.wpi.cs3733.teamO.SRequest;

import java.util.Date;

public class EntryRequest {
  private int entryreqID;
  private String requestedBy;
  public String fulfilledBy;
  private Date dateRequested;
  private String locationNodeID;
  private Boolean hasSymptoms;
  private Boolean check1;
  private Boolean check2;

  public EntryRequest(
      int entryreqID,
      String requestedBy,
      String fulfilledBy,
      Date dateRequested,
      String locationNodeID,
      Boolean hasSymptoms,
      Boolean check1,
      Boolean check2) {
    this.entryreqID = entryreqID;
    this.requestedBy = requestedBy;
    this.dateRequested = dateRequested;
    this.fulfilledBy = fulfilledBy;
    this.locationNodeID = locationNodeID;
    this.hasSymptoms = hasSymptoms;
    this.check1 = check1;
    this.check2 = check2;
  }

  public EntryRequest() {}

  public int getRequestID() {
    return entryreqID;
  }

  public void setRequestID(int requestID) {
    this.entryreqID = requestID;
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

  public void setCheck1(Boolean check1) {
    this.check1 = check1;
  }

  public Boolean getCheck1() {
    return check1;
  }

  public void setCheck2(Boolean check2) {
    this.check2 = check2;
  }

  public Boolean getCheck2() {
    return check2;
  }

  public Boolean getIfSymptoms() {
    return hasSymptoms;
  }

  public void setIfSymptoms(Boolean b) {
    this.hasSymptoms = b;
  }
}
