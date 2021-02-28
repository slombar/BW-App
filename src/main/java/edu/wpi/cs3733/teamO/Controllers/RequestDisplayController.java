package edu.wpi.cs3733.teamO.Controllers;

import edu.wpi.cs3733.teamO.SRequest.DisplayRequest;
import edu.wpi.cs3733.teamO.SRequest.Request;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class RequestDisplayController implements Initializable {

    private static String typeOfRequest;
    private static ObservableList<Request> reqList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reqList = DisplayRequest.getSpecificReqList(typeOfRequest);
    }

    public String getType() {
        return typeOfRequest;
    }

    public static void setType(String t) {
        typeOfRequest = t;
    }

    /**
     * Display a single request from the request list
     */
    public static void displayOneRequest(){

        String reqID = "";
        String requestedBy = "";
        String fulfilledBy = "";
        Date dateNeeded = new Date();
        Date dateFulfilled = new Date();

    }

    public static void displayList(){
        for (Request r : reqList) {



        }
    }
}
