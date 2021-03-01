package edu.wpi.cs3733.teamO.SRequest;

import edu.wpi.cs3733.teamO.Database.RequestHandling;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DisplayRequest {

  private static ObservableList<Request> reqList = RequestHandling.getAllRequests();

  public static ObservableList<Request> getSpecificReqList(String typeOfRequest) {

    ObservableList<Request> specList = FXCollections.observableArrayList();

    for (Request r : reqList) {

      if (r.getRequestType().equals(typeOfRequest)) {
        reqList.add(r);
      }
    }

    return specList;
  }
}
