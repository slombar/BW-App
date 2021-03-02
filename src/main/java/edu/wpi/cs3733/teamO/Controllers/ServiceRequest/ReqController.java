package edu.wpi.cs3733.teamO.Controllers.ServiceRequest;

import static edu.wpi.cs3733.teamO.Controllers.Archive.EditPageController.createFields;
import static edu.wpi.cs3733.teamO.Controllers.ServiceRequest.RequestPageController.getReqType;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.teamO.Database.UserHandling;
import edu.wpi.cs3733.teamO.HelperClasses.PopupMaker;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import edu.wpi.cs3733.teamO.Opp;
import edu.wpi.cs3733.teamO.SRequest.DisplayRequest;
import edu.wpi.cs3733.teamO.SRequest.Request;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ReqController implements Initializable {

  @FXML private VBox reqBox;
  @FXML private StackPane popUpPane;
  private static ObservableList<Request> reqList;
  private static String typeOfRequest;

  /** Display a single request from the request list */
  public void displayOneRequest(Request r) {
    Stage s = null;
    Scene scene = null;
    s = Opp.getPrimaryStage();
    scene = s.getScene();

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

    boolean check = false;

    id.getStyleClass().add("label");
    reqBy.getStyleClass().add("label");
    filledBy.getStyleClass().add("label");
    dReq.getStyleClass().add("label");
    dNeed.getStyleClass().add("label");
    loc.getStyleClass().add("label");
    p1.getStyleClass().add("label");
    p2.getStyleClass().add("label");
    p3.getStyleClass().add("label");

    addBox.getChildren().add(id);
    addBox.getChildren().add(reqBy);
    addBox.getChildren().add(filledBy);
    addBox.getChildren().add(dReq);
    addBox.getChildren().add(dNeed);
    addBox.getChildren().add(loc);
    addBox.getChildren().add(p1);
    addBox.getChildren().add(p2);
    addBox.getChildren().add(p3);

    check = reqBox.getChildren().add(addBox);
    System.out.println("Addbox check: " + check);
  }

  public void displayList(ObservableList<Request> requests) {

    for (Request r : requests) {
      displayOneRequest(r);
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    typeOfRequest = getReqType();
    System.out.println("RequestType: " + typeOfRequest);
    reqList = DisplayRequest.getSpecificReqList(typeOfRequest);
    displayList(reqList);
  }

  public void addNewRequest(ActionEvent actionEvent) {
    if (typeOfRequest.equals("COMP")) {
      SwitchScene.goToParent("/Views/ServiceRequests/ComputerServiceRequest.fxml");
    }
    if (typeOfRequest.equals("GIFT")) {
      SwitchScene.goToParent("/Views/ServiceRequests/GiftDeliveryService.fxml");
    }
    if (typeOfRequest.equals("TRAN")) {
      SwitchScene.goToParent("/Views/ServiceRequests/InternalTransportForm.fxml");
    }
    if (typeOfRequest.equals("LAUN")) {
      SwitchScene.goToParent("/Views/ServiceRequests/LaundryRequest.fxml");
    }
    if (typeOfRequest.equals("MEDI")) {
      SwitchScene.goToParent("/Views/ServiceRequests/MedicineDeliveryService.fxml");
    }
    if (typeOfRequest.equals("SECU")) {
      SwitchScene.goToParent("/Views/ServiceRequests/SecurityRequest.fxml");
    }
    if (typeOfRequest.equals("MAIT")) {
      SwitchScene.goToParent("/Views/ServiceRequests/FacilitiesMaintenanceRequest.fxml");
    }
    if (typeOfRequest.equals("LANG")) {
      SwitchScene.goToParent("/Views/ServiceRequests/InterpreterForm.fxml");
    }
    if (typeOfRequest.equals("SANA")) {
      SwitchScene.goToParent("/Views/ServiceRequests/SANA.fxml");
    }
    /*TODO: add kyle's component*/
    if (typeOfRequest.equals("FLOR")) {
      SwitchScene.goToParent("/Views/ServiceRequests/FloralDeliveryRequest.fxml");
    }
  }

  public void assignStaff(ActionEvent actionEvent) {

    // addEdgePopup has the content of the popup
    // addEdgeDialog creates the dialog popup

    System.out.println("Running!");
    JFXDialogLayout assignStaffLayout = new JFXDialogLayout();
    assignStaffLayout.setHeading(new Text("Assign Staff to Service Request"));
    VBox assignStaffVBox = new VBox(12);

    // Creating an HBox of buttons
    HBox buttonBox = new HBox(20);
    JFXButton closeButton = new JFXButton("Close");
    JFXButton submitButton = new JFXButton("Assign");
    buttonBox.getChildren().addAll(closeButton, submitButton);

    // Creating a list of labels to create the textfields
    ArrayList<String> assignStaffLabels =
        new ArrayList<String>(Arrays.asList("Request ID", "Employee Name"));
    ArrayList<JFXTextField> listOfFields = createFields(assignStaffLabels);

    // Creating the form with a VBox
    assignStaffVBox.getChildren().addAll(listOfFields.get(0), listOfFields.get(1), buttonBox);
    assignStaffLayout.setBody(assignStaffVBox);

    // Bringing the popup screen to the front and disabling the background
    popUpPane.toFront();
    JFXDialog assignStaffDialog =
        new JFXDialog(popUpPane, assignStaffLayout, JFXDialog.DialogTransition.BOTTOM);

    // Closing the popup
    closeButton.setOnAction(
        event -> {
          assignStaffDialog.close();
          popUpPane.toBack();
        });

    // Submits edit to the database
    submitButton.setOnAction(
        event -> {
          // If incomplete form, sends an error msg
          // Otherwise, sends to database and closes popup
          if (listOfFields.get(0).getText().isEmpty() || listOfFields.get(1).getText().isEmpty()) {
            //              incompletePopup();
            PopupMaker.incompletePopup(popUpPane);
          } else {
            UserHandling.assignEmployee(
                listOfFields.get(0).getText(), listOfFields.get(1).getText());

            assignStaffDialog.close();
            popUpPane.toBack();
            SwitchScene.goToParent("/Views/ServiceRequests/RequestList.fxml");
          }
        });
    assignStaffDialog.show();
  }
}
