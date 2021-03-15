package edu.wpi.cs3733.teamO.SRequest;

import edu.wpi.cs3733.teamO.Database.RequestHandling;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DisplayRequest {

  private static ObservableList<Request> reqList;

  public static ObservableList<Request> getSpecificReqList(String typeOfRequest) {
    reqList = RequestHandling.getRequests();
    ObservableList<Request> specList = FXCollections.observableArrayList();

    if (reqList.size() > 0) {
      for (int x = 0; x < reqList.size(); x++) {

        if (reqList.get(x).getRequestType().equals(typeOfRequest)) {
          specList.add(reqList.get(x));

          System.out.println(reqList.get(x).getRequestID());
        }
      }
    }
    return specList;
  }
}
