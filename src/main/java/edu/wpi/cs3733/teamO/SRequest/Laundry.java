package edu.wpi.cs3733.teamO.SRequest;

import edu.wpi.cs3733.teamO.UserTypes.Staff;
import java.util.Date;

public class Laundry extends Request {

  String specialInstructions;
  String washSettings;
  String drySettings;

  public Laundry(
      String requestID,
      Staff requestedBy,
      Staff fulfilledBy,
      Date dateRequested,
      Date dateNeeded,
      String requestType,
      String summary,
      String locationNodeID) {
    super(
        requestID,
        requestedBy,
        fulfilledBy,
        dateRequested,
        dateNeeded,
        requestType,
        summary,
        locationNodeID);
    this.specialInstructions = specialInstructions;
    this.washSettings = washSettings;
    this.drySettings = drySettings;
  }

  public void setSpecialInstructions(String specialInstructions) {
    this.specialInstructions = specialInstructions;
  }
}
