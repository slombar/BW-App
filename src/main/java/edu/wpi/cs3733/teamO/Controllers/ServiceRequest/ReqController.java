package edu.wpi.cs3733.teamO.Controllers.ServiceRequest;

import static edu.wpi.cs3733.teamO.Controllers.ServiceRequest.RequestPageController.getReqType;

import com.jfoenix.controls.*;
import edu.wpi.cs3733.teamO.Controllers.Mobile.WaitingPageController;
import edu.wpi.cs3733.teamO.Database.RequestHandling;
import edu.wpi.cs3733.teamO.Database.UserHandling;
import edu.wpi.cs3733.teamO.HelperClasses.PopupMaker;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import edu.wpi.cs3733.teamO.SRequest.DisplayRequest;
import edu.wpi.cs3733.teamO.SRequest.Request;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class ReqController implements Initializable {

  public ScrollPane scrollPane;
  @FXML private JFXButton assignButton;
  @FXML private JFXButton updateButton;
  @FXML private JFXButton addButton;
  @FXML private VBox reqBox;
  @FXML private StackPane popUpPane;
  private static ObservableList<Request> reqList;
  private static String typeOfRequest;

  public static ArrayList<JFXTextField> createFields(ArrayList<String> labels) {
    ArrayList<JFXTextField> listOfFields = new ArrayList<>();
    for (String label : labels) {
      JFXTextField text = new JFXTextField();
      text.setPromptText(label);
      listOfFields.add(text);
    }
    return listOfFields;
  }

  /** Display a single request from the request list */
  public void displayOneRequest(Request r) {

    int reqID = r.getRequestID();
    String requestedBy = r.getRequestedBy();
    String fulfilledBy = r.getFulfilledBy();
    Date dateNeeded = r.getDateNeeded();
    Date dateRequested = r.getDateRequested();
    String location = r.getLocationNodeID();
    String par1 = r.getPara1();
    String par2 = r.getPara2();
    String par3 = r.getPara3();

    HBox addBox = new HBox();
    addBox.setSpacing(30);
    addBox.setPrefWidth(1400);
    addBox.setBackground(
        new Background(
            new BackgroundFill(Color.color(.95, .95, .95), new CornerRadii(5), Insets.EMPTY)));
    Label id = new Label(String.valueOf(reqID));
    id.setStyle("-fx-max-width: 50; -fx-min-width: 50; " + id.getStyle());

    Label reqBy = new Label(requestedBy);
    reqBy.setStyle("-fx-max-width: 100; -fx-min-width: 100; " + reqBy.getStyle());

    Label filledBy = new Label(fulfilledBy);
    filledBy.setStyle("-fx-max-width: 100; -fx-min-width: 100; " + filledBy.getStyle());

    Label dReq = new Label(dateRequested.toString());
    dReq.setStyle("-fx-max-width: 100; -fx-min-width: 100; " + dReq.getStyle());

    Label dNeed = new Label(dateNeeded.toString());
    dNeed.setStyle("-fx-max-width: 100; -fx-min-width: 100; " + dNeed.getStyle());

    Label loc = new Label(location);
    loc.setStyle("-fx-max-width: 50; -fx-min-width: 50; " + loc.getStyle());

    Label p1 = new Label(par1);
    p1.setStyle("-fx-max-width: 100; -fx-min-width: 100; " + p1.getStyle());

    Label p2 = new Label(par2);
    p2.setStyle("-fx-max-width: 100; -fx-min-width: 100; " + p2.getStyle());

    Label p3 = new Label(par3);
    p3.setStyle("-fx-max-width: 100; -fx-min-width: 100; " + p3.getStyle());

    JFXButton markDone = new JFXButton();

    reqBox.setSpacing(15);

    String status = "";

    try {
      status = RequestHandling.getStatus(reqID);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

    markDone.setOnAction(
        e -> {
          // mark the thing as done
          try {
            RequestHandling.setStatus(reqID, "Complete");
            SwitchScene.goToParent("/Views/ServiceRequests/RequestList.fxml");
          } catch (SQLException throwables) {
            // TODO @sam add input scrubbing / verification?
            throwables.printStackTrace();
          }
        });

    markDone.setText("Mark Complete");
    markDone.setStyle(
        "-fx-background-color: #CFE2F3; -fx-text-fill: #3a5369; -fx-border-radius: 5px; -fx-font-family: 'Leelawadee UI'; -fx-font-size: 10pt; -fx-font-weight: BOLD;");

    addBox.getChildren().add(id);
    addBox.getChildren().add(reqBy);
    addBox.getChildren().add(filledBy);
    addBox.getChildren().add(dReq);
    addBox.getChildren().add(dNeed);
    addBox.getChildren().add(loc);

    if (par1 != null && !par1.equals("null")) {
      addBox.getChildren().add(p1);
    }
    if (par2 != null && !par2.equals("null")) {
      addBox.getChildren().add(p2);
    }
    if (par3 != null && !par3.equals("null")) {
      addBox.getChildren().add(p3);
    }

    switch (status) {
      case "Not Assigned":
        addBox.setStyle("-fx-border-color:  #ffaca4; -fx-border-width: 5px;");

        break;
      case "Assigned":
        addBox.setStyle("-fx-border-color:  #fec107; -fx-border-width: 5px;");

        break;
      case "Complete":
        addBox.setStyle("-fx-border-color:  #72db8e; -fx-border-width: 5px;");
        break;
    }
    // add button
    addBox.getChildren().add(markDone);

    for (Node n : addBox.getChildren()) {
      if (!n.getClass().equals(JFXButton.class)) {
        String temp = n.getStyle();
        n.setStyle("-fx-text-fill:  #3A5369; -fx-font-size: 14pt;" + temp);
      }
    }
    addBox
        .onMouseClickedProperty()
        .set(
            (EventHandler<MouseEvent>)
                (MouseEvent t) -> {
                  String minimizedAddBoxStyle = addBox.getStyle();
                  String minimizedNStyle = id.getStyle();
                  minimizedNStyle = minimizedNStyle.replace("-fx-max-width: 50;", "");
                  minimizedNStyle = minimizedNStyle.replace("-fx-min-width: 50;", "");
                  for (Node n : addBox.getChildren()) {
                    if (n.getClass().equals(Label.class)) {
                      n.setStyle(
                          "-fx-max-height: INFINITY; -fx-min-height: -1; -fx-wrap-text: true; "
                              + n.getStyle());
                    }
                  }
                  JFXButton minimize = new JFXButton("-");
                  String finalMinimizedNStyle = minimizedNStyle;
                  minimize.setOnAction(
                      e -> {
                        for (Node n : addBox.getChildren()) {
                          if (n.getClass().equals(Label.class)) {
                            n.setStyle(finalMinimizedNStyle);
                            System.out.println(n.getStyle());
                          }
                        }
                        addBox.setStyle(minimizedAddBoxStyle);
                      });
                  addBox.getChildren().add(minimize);
                  addBox.setStyle(
                      "-fx-max-height: INFINITY; -fx-min-height: -1; " + addBox.getStyle());
                });
    reqBox.getChildren().add(addBox);
  }

  /**
   * Displays the list on the page by going through obs. list of requests
   *
   * @param requests
   */
  public void displayList(ObservableList<Request> requests) {

    for (Request r : requests) {
      displayOneRequest(r);
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    typeOfRequest = getReqType();
    System.out.println("RequestType: " + typeOfRequest);
    if (typeOfRequest != null) {
      reqList = DisplayRequest.getSpecificReqList(typeOfRequest);
      displayList(reqList);
    }
    JFXScrollPane.smoothScrolling(scrollPane);

    assignButton.setDisable(!UserHandling.getAdmin());
    assignButton.setVisible(UserHandling.getAdmin());

    updateButton.setDisable(!typeOfRequest.equals("CV19"));
    updateButton.setVisible(typeOfRequest.equals("CV19"));
    // addButton.setDisable(typeOfRequest.equals("CV19"));
    // addButton.setVisible(!typeOfRequest.equals("CV19"));
  }

  /**
   * Button control for adding a new request, depending on the type you will get sent to the proper
   * fxml
   *
   * @param actionEvent
   */
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

    if (typeOfRequest.equals("FLOR")) {
      SwitchScene.goToParent("/Views/ServiceRequests/FloralDeliveryRequest.fxml");
    }
    // TODO change this
    if (typeOfRequest.equals("CV19")) {
      // replace with covid 19 survey
      SwitchScene.goToParent("/Views/MobileApp/MobileCovidSurvey.fxml");
    }
  }

  /**
   * Button functionality that will assign the user to the request
   *
   * @param actionEvent
   */
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
                Integer.parseInt(listOfFields.get(0).getText()), listOfFields.get(1).getText());

            assignStaffDialog.close();
            popUpPane.toBack();
            SwitchScene.goToParent("/Views/ServiceRequests/RequestList.fxml");
          }
        });
    assignStaffDialog.show();
  }

  public void back(ActionEvent actionEvent) {
    SwitchScene.goToParent("/Views/ServiceRequests/RequestPage.fxml");
  }

  /**
   * update functionality for COVID SURVEY ENTRY REQUEST ONLY
   *
   * @param actionEvent
   */
  public void update(ActionEvent actionEvent) {
    JFXDialogLayout assignStaffLayout = new JFXDialogLayout();
    assignStaffLayout.setHeading(new Text("Update Service Request"));
    VBox assignStaffVBox = new VBox(12);

    // Creating an HBox of buttons
    HBox buttonBox = new HBox(20);
    JFXButton closeButton = new JFXButton("Close");
    JFXButton submitButton = new JFXButton("Update");
    buttonBox.getChildren().addAll(closeButton, submitButton);

    // Creating a list of labels to create the textfields
    ArrayList<String> assignStaffLabels =
        new ArrayList<>(Arrays.asList("Request ID", "Approved Entrance"));
    ArrayList<JFXTextField> listOfFields = createFields(assignStaffLabels);

    // Creating the form with a VBox
    assignStaffVBox.getChildren().addAll(listOfFields.get(0), listOfFields.get(1), buttonBox);
    assignStaffLayout.setBody(assignStaffVBox);

    // Bringing the popup screen to the front and disabling the background
    popUpPane.toFront();
    JFXDialog dialog =
        new JFXDialog(popUpPane, assignStaffLayout, JFXDialog.DialogTransition.BOTTOM, false);

    // Closing the popup
    closeButton.setOnAction(
        event -> {
          dialog.close();
          popUpPane.toBack();
        });

    // Submits edit to the database
    submitButton.setOnAction(
        event -> {
          // If incomplete form, sends an error msg
          // Otherwise, sends to database and closes popup
          if (listOfFields.get(0).getText().isEmpty()) {
            //           incompletePopup();
            PopupMaker.incompletePopup(popUpPane);
          } else {
            Request selectedRequest = RequestHandling.getRequest(Integer.parseInt(listOfFields.get(0).getText()));
            System.out.println(listOfFields.get(0).getText());
            /*RequestHandling.editRequest(
            Integer.parseInt(listOfFields.get(0).getText()),
            selectedRequest.getFulfilledBy(),
            "CV19",
            listOfFields.get(1).getText(),
            selectedRequest.getSummary(),
            selectedRequest.getPara1(),
            selectedRequest.getPara2(),
            selectedRequest.getPara3());*/

            RequestHandling.updateRequest(
                Integer.parseInt(listOfFields.get(0).getText()), listOfFields.get(1).getText());
            WaitingPageController.setSurveyApproved(true);

            dialog.close();
            popUpPane.toBack();
            SwitchScene.goToParent("/Views/ServiceRequests/RequestList.fxml");
          }
        });
    dialog.show();
  }

  /**
   * Delete button functionality. will delete service request from DB
   *
   * @param actionEvent
   */
  public void delete(ActionEvent actionEvent) {
    JFXDialogLayout assignStaffLayout = new JFXDialogLayout();
    assignStaffLayout.setHeading(new Text("Delete Service Request"));
    VBox assignStaffVBox = new VBox(12);

    // Creating an HBox of buttons
    HBox buttonBox = new HBox(20);
    JFXButton closeButton = new JFXButton("Close");
    JFXButton submitButton = new JFXButton("Delete");
    buttonBox.getChildren().addAll(closeButton, submitButton);

    // Creating a list of labels to create the textfields
    ArrayList<String> assignStaffLabels = new ArrayList<>(Arrays.asList("Request ID"));
    ArrayList<JFXTextField> listOfFields = createFields(assignStaffLabels);

    // Creating the form with a VBox
    assignStaffVBox.getChildren().addAll(listOfFields.get(0), buttonBox);
    assignStaffLayout.setBody(assignStaffVBox);

    // Bringing the popup screen to the front and disabling the background
    popUpPane.toFront();
    JFXDialog dialog =
        new JFXDialog(popUpPane, assignStaffLayout, JFXDialog.DialogTransition.BOTTOM, false);

    // Closing the popup
    closeButton.setOnAction(
        event -> {
          dialog.close();
          popUpPane.toBack();
        });

    // Submits edit to the database
    submitButton.setOnAction(
        event -> {
          // If incomplete form, sends an error msg
          // Otherwise, sends to database and closes popup
          if (listOfFields.get(0).getText().isEmpty()) {
            //           incompletePopup();
            PopupMaker.incompletePopup(popUpPane);
          } else {
            RequestHandling.deleteRequest(Integer.parseInt(listOfFields.get(0).getText()));

            dialog.close();
            popUpPane.toBack();
            SwitchScene.goToParent("/Views/ServiceRequests/RequestList.fxml");
          }
        });
    dialog.show();
  }
}
