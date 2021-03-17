package edu.wpi.cs3733.teamO.SRequest;

import edu.wpi.cs3733.teamO.Database.EntryRequestHandling;
import edu.wpi.cs3733.teamO.Database.RequestHandling;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DisplayRequest {

  private static ObservableList<Request> reqList;
  private static ObservableList<EntryRequest> entryReqList;

  public static ObservableList<Request> getSpecificReqList(String typeOfRequest) {
    reqList = RequestHandling.getRequests(typeOfRequest);
    return reqList;
  }

  public static ObservableList<EntryRequest> getSpecificEntryReqList() {
    entryReqList = EntryRequestHandling.getEntryRequests();
    ObservableList<EntryRequest> specList = FXCollections.observableArrayList();

    if (entryReqList.size() > 0) {
      for (int x = 0; x < entryReqList.size(); x++) {

        specList.add(entryReqList.get(x));
      }
    }
    return specList;
  }
}
