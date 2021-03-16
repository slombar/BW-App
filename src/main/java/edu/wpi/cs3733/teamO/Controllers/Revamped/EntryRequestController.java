package edu.wpi.cs3733.teamO.Controllers.Revamped;

import com.jfoenix.controls.*;
import edu.wpi.cs3733.teamO.Database.EntryRequestHandling;
import edu.wpi.cs3733.teamO.Database.UserHandling;
import edu.wpi.cs3733.teamO.HelperClasses.PopupMaker;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import edu.wpi.cs3733.teamO.SRequest.DisplayRequest;
import edu.wpi.cs3733.teamO.SRequest.EntryRequest;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class EntryRequestController implements Initializable {
  public ScrollPane scrollPane;
  @FXML private JFXButton assignButton;
  @FXML private JFXButton updateButton;
  @FXML private JFXButton addButton;
  @FXML private VBox reqBox;
  @FXML private StackPane popUpPane;
  private static ObservableList<EntryRequest> reqList;

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
  public void displayOneRequest(EntryRequest r) {

    int reqID = r.getRequestID();
    String requestedBy = r.getRequestedBy();
    String fulfilledBy = r.getFulfilledBy();
    Date dateRequested = r.getDateRequested();
    String location = r.getLocationNodeID();
    Boolean symptoms = r.getIfSymptoms();
    Boolean check1 = r.getCheck1();
    Boolean check2 = r.getCheck2();

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

    Label loc = new Label(location);
    loc.setStyle("-fx-max-width: 150; -fx-min-width: 150; " + loc.getStyle());

    Label symp = new Label(String.valueOf(symptoms));
    symp.setStyle("-fx-max-width: 100; -fx-min-width: 100; " + symp.getStyle());

    Label c1 = new Label(String.valueOf(check1));
    c1.setStyle("-fx-max-width: 100; -fx-min-width: 100; " + c1.getStyle());

    Label c2 = new Label(String.valueOf(check2));
    c2.setStyle("-fx-max-width: 100; -fx-min-width: 100; " + c2.getStyle());

    JFXButton markDone = new JFXButton();

    reqBox.setSpacing(15);

    String status = "";

    try {
      status = EntryRequestHandling.getStatus(reqID);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

    markDone.setOnAction(
        e -> {
          // mark the thing as done
          try {
            EntryRequestHandling.setStatus(reqID, "Complete");
            SwitchScene.goToParent("/Views/RequestList.fxml");
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
    addBox.getChildren().add(loc);
    addBox.getChildren().add(symp);
    addBox.getChildren().add(c1);
    addBox.getChildren().add(c2);
    /*
       if (par1 != null && !par1.equals("null")) {
         addBox.getChildren().add(p1);
       }
       if (par2 != null && !par2.equals("null")) {
         addBox.getChildren().add(p2);
       }
       if (par3 != null && !par3.equals("null")) {
         addBox.getChildren().add(p3);
       }

    */

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
            e -> {
              PopupMaker.serviceReqPopup(
                  popUpPane, ((Label) addBox.getChildren().get(0)).getText());
            });
    reqBox.getChildren().add(addBox);
  }

  /**
   * Displays the list on the page by going through obs. list of requests
   *
   * @param requests
   */
  public void displayList(ObservableList<EntryRequest> requests) {

    for (EntryRequest r : requests) {
      displayOneRequest(r);
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    reqList = DisplayRequest.getSpecificEntryReqList();
    displayList(reqList);
    JFXScrollPane.smoothScrolling(scrollPane);

    assignButton.setDisable(!UserHandling.getAdmin());
    assignButton.setVisible(UserHandling.getAdmin());

    // updateButton.setDisable(!typeOfRequest.equals("CV19"));
    // updateButton.setVisible(typeOfRequest.equals("CV19"));
    // addButton.setDisable(typeOfRequest.equals("CV19"));
    // addButton.setVisible(!typeOfRequest.equals("CV19"));
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
            SwitchScene.goToParent("/Views/RequestList.fxml");
          }
        });
    assignStaffDialog.show();
  }

  public void back(ActionEvent actionEvent) {
    SwitchScene.goToParent("/RevampedViews/DesktopApp/ServiceRequests/RequestPage.fxml");
  }

  /**
   * update functionality for COVID SURVEY ENTRY REQUEST ONLY
   *
   * @param actionEvent
   */
  /*
  public void update(ActionEvent actionEvent) {
    JFXDialogLayout assignStaffLayout = new JFXDialogLayout();
    assignStaffLayout.setHeading(new Text("Update Service Request"));
    VBox assignStaffVBox = new VBox(12);

    // Creat/*ing an HBox of buttons
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
            EntryRequest selectedRequest =
                EntryRequestHandling.getRequest(Integer.parseInt(listOfFields.get(0).getText()));
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
  /*
              EntryRequestHandling.updateRequest(
                  Integer.parseInt(listOfFields.get(0).getText()), listOfFields.get(1).getText());
              WaitingPageController.setSurveyApproved(true);

              dialog.close();
              popUpPane.toBack();
              SwitchScene.goToParent("/Views/ServiceRequests/RequestList.fxml");
            }
          });
      dialog.show();
    }
  */
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
            EntryRequestHandling.deleteEntryRequest(
                Integer.parseInt(listOfFields.get(0).getText()));

            dialog.close();
            popUpPane.toBack();
            SwitchScene.goToParent("/Views/RequestList.fxml");
          }
        });
    dialog.show();
  }
}
