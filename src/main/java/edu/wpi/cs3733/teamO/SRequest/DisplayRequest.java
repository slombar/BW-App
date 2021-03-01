package edu.wpi.cs3733.teamO.SRequest;

import edu.wpi.cs3733.teamO.Database.RequestHandling;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DisplayRequest {

  private static ObservableList<Request> reqList = RequestHandling.getAllRequests();

  public static ObservableList<Request> getSpecificReqList(String typeOfRequest) {
    reqList = RequestHandling.getAllRequests();
    ObservableList<Request> specList = FXCollections.observableArrayList();

    if (reqList.size() > 0) {
      for (Request req : reqList) {

        if (req.getRequestType().equals(typeOfRequest)) {
          reqList.add(req);
        }
      }
    } else {
      System.out.println("Nothing in list");
    }

    return specList;
  }
}
