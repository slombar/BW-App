package edu.wpi.cs3733.teamO.Controllers;

import edu.wpi.cs3733.teamO.SRequest.DisplayRequest;
import edu.wpi.cs3733.teamO.SRequest.Request;
import edu.wpi.cs3733.teamO.model.Node;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RequestDisplayController implements Initializable {

  private static String typeOfRequest;
  private static ObservableList<Request> reqList;
  @FXML private static VBox reqBox;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    reqList = DisplayRequest.getSpecificReqList(typeOfRequest);
    displayList();
    System.out.println("got here");
  }

  public String getType() {
    return typeOfRequest;
  }

  public static void setType(String t) {
    typeOfRequest = t;
  }

  /** Display a single request from the request list */
  public static void displayOneRequest(Request r) {
    Node locationN = new Node();
    String reqID = r.getRequestID();
    String requestedBy = r.getRequestedBy();
    String fulfilledBy = r.getFulfilledBy();
    Date dateNeeded = r.getDateNeeded();
    Date dateRequested = r.getDateRequested();
    String location = r.getLocationNodeID();
    String par1 = r.getPara1();
    String par2 = r.getPara2();
    String par3 = r.getPara3();

    HBox addBox = new HBox();

    Label id = new Label(reqID);
    Label reqBy = new Label(requestedBy);
    Label filledBy = new Label(fulfilledBy);
    Label dReq = new Label(dateRequested.toString());
    Label dNeed = new Label(dateNeeded.toString());
    Label loc = new Label(location);
    Label p1 = new Label(par1);
    Label p2 = new Label(par2);
    Label p3 = new Label(par3);

    addBox.getChildren().add(id);
    addBox.getChildren().add(reqBy);
    addBox.getChildren().add(filledBy);
    addBox.getChildren().add(dReq);
    addBox.getChildren().add(dNeed);
    addBox.getChildren().add(loc);
    addBox.getChildren().add(p1);
    addBox.getChildren().add(p2);
    addBox.getChildren().add(p3);

    reqBox.getChildren().add(addBox);
    reqBox.setVisible(true);
  }

  public static void displayList() {

    if (reqList.size() > 0) {
      for (Request r : reqList) {
        displayOneRequest(r);
      }
    } else {
      System.out.println("nothing in list");
    }
  }
}
