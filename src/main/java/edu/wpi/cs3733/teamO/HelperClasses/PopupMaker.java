package edu.wpi.cs3733.teamO.HelperClasses;

import com.jfoenix.controls.*;
import edu.wpi.cs3733.teamO.Database.EntryRequestHandling;
import edu.wpi.cs3733.teamO.Database.RequestHandling;
import edu.wpi.cs3733.teamO.SRequest.EntryRequest;
import edu.wpi.cs3733.teamO.SRequest.Request;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class PopupMaker {

  /**
   * Creates a warning popup for an incomplete form
   *
   * @param popupPane is the stack pane on which the popup needs to be made on
   */
  public static void incompletePopup(StackPane popupPane) {
    popupPane.toFront();

    // Creates the content for the popup
    JFXDialogLayout warning = new JFXDialogLayout();
    warning.setHeading(new Text("WARNING!"));
    warning.setBody(new Text("Fields cannot be left blank."));
    JFXButton closeButton = new JFXButton("Close");
    warning.setActions(closeButton);

    // Creates the actual popup
    JFXDialog warningDialog =
        new JFXDialog(popupPane, warning, JFXDialog.DialogTransition.CENTER, false);

    // Closes the popup
    closeButton.setOnAction(
        event -> {
          warningDialog.close();
          popupPane.toBack();
        });
    warningDialog.show();
  }

  public static void unconnectedPopup(StackPane popupPane) {
    popupPane.toFront();

    // Creates the content for the popup
    JFXDialogLayout warning = new JFXDialogLayout();
    warning.setHeading(new Text("WARNING!"));
    warning.setBody(
        new Text(
            "There are nodes unreachable from others, try turning on view all edges and adding some more. Then you can exit edit mode."));
    JFXButton closeButton = new JFXButton("Close");
    warning.setActions(closeButton);

    // Creates the actual popup
    JFXDialog warningDialog =
        new JFXDialog(popupPane, warning, JFXDialog.DialogTransition.CENTER, false);

    // Closes the popup
    closeButton.setOnAction(
        event -> {
          warningDialog.close();
          popupPane.toBack();
        });
    warningDialog.show();
  }

  /**
   * creates a popup to notify the user that a selected ID does not exist
   *
   * @param popupPane is the stack pane on which the popup needs to be made on
   */
  public static void nonexistentPopup(StackPane popupPane) {
    popupPane.toFront();

    // Creates the content for the popup
    JFXDialogLayout warning = new JFXDialogLayout();
    warning.setHeading(new Text("WARNING!"));
    warning.setBody(new Text("The given ID does not exist in the database."));
    JFXButton closeButton = new JFXButton("Close");
    warning.setActions(closeButton);

    // Creates the actual popup
    JFXDialog warningDialog =
        new JFXDialog(popupPane, warning, JFXDialog.DialogTransition.CENTER, false);

    // Closes the popup
    closeButton.setOnAction(
        event -> {
          warningDialog.close();
          popupPane.toBack();
        });
    warningDialog.show();
  }

  /**
   * Creates a popup to notify that the login failed due to the username or password being incorrect
   *
   * @param popupPane is the stack pane on which the popup needs to be made on
   */
  public static void invalidLogin(StackPane popupPane) {
    popupPane.toFront();

    // Creates the content for the popup
    JFXDialogLayout warning = new JFXDialogLayout();
    warning.setHeading(new Text("Login Failed"));
    warning.setBody(new Text("Incorrect Username or Password"));
    JFXButton closeButton = new JFXButton("Close");
    warning.setActions(closeButton);

    // Creates the actual popup
    JFXDialog warningDialog =
        new JFXDialog(popupPane, warning, JFXDialog.DialogTransition.CENTER, false);

    // Closes the popup
    closeButton.setOnAction(
        event -> {
          warningDialog.close();
          popupPane.toBack();
        });
    warningDialog.show();
  }

  public static void usernameAlreadyInUse(StackPane popupPane) {
    popupPane.toFront();

    // Creates the content for the popup
    JFXDialogLayout warning = new JFXDialogLayout();
    warning.setHeading(new Text("Account Creation Failed"));
    warning.setBody(
        new Text(
            "Username is already in use. \n"
                + "Try another username or go to sign in page to change password if the account belongs to you."));
    JFXButton closeButton = new JFXButton("Close");
    warning.setActions(closeButton);

    // Creates the actual popup
    JFXDialog warningDialog =
        new JFXDialog(popupPane, warning, JFXDialog.DialogTransition.CENTER, false);

    // Closes the popup
    closeButton.setOnAction(
        event -> {
          warningDialog.close();
          popupPane.toBack();
        });
    warningDialog.show();
  }

  /**
   * Creates a popup to notify the user of invalid username and why this may be
   *
   * @param popupPane is the stack pane on which the popup needs to be made on
   */
  public static void invalidUsername(StackPane popupPane) {
    popupPane.toFront();

    // Creates the content for the popup
    JFXDialogLayout warning = new JFXDialogLayout();
    warning.setHeading(new Text("Invalid Username"));
    warning.setBody(
        new Text(
            "1. Username consists of only alphanumeric characters\n"
                + "2. Username is allowed to use (.), (_), and (-)\n"
                + "3. The (.), (_), or (-) may not be the first or last character\n"
                + "4. The (.), (_), or (-) may not be consecutive\n"
                + "5. Username must be between 3 and 20 characters\n"));
    JFXButton closeButton = new JFXButton("Close");
    warning.setActions(closeButton);

    // Creates the actual popup
    JFXDialog warningDialog =
        new JFXDialog(popupPane, warning, JFXDialog.DialogTransition.CENTER, false);

    // Closes the popup
    closeButton.setOnAction(
        event -> {
          warningDialog.close();
          popupPane.toBack();
        });
    warningDialog.show();
  }

  /**
   * Creates a popup to notify the user of invalid email
   *
   * @param popupPane is the stack pane on which the popup needs to be made on
   */
  public static void invalidEmail(StackPane popupPane) {
    popupPane.toFront();

    // Creates the content for the popup
    JFXDialogLayout warning = new JFXDialogLayout();
    warning.setHeading(new Text("Invalid Username"));
    warning.setBody(new Text("Please ensure that email is typed correctly"));
    JFXButton closeButton = new JFXButton("Close");
    warning.setActions(closeButton);

    // Creates the actual popup
    JFXDialog warningDialog =
        new JFXDialog(popupPane, warning, JFXDialog.DialogTransition.CENTER, false);

    // Closes the popup
    closeButton.setOnAction(
        event -> {
          warningDialog.close();
          popupPane.toBack();
        });
    warningDialog.show();
  }

  /**
   * Creates a popup to notify the user of common symptoms of COVID-19
   *
   * @param popupPane is the stack pane on which the popup needs to be made on
   */
  public static void covidSymptoms(StackPane popupPane) {
    popupPane.toFront();

    // Creates the content for the popup
    JFXDialogLayout symptoms = new JFXDialogLayout();
    symptoms.setHeading(new Text("Symptoms of COVID-19:"));
    symptoms.setBody(
        new Text(
            "Fever or chills\n"
                + "Cough\n"
                + "Shortness of breath or difficulty breathing\n"
                + "Fatigue\n"
                + "Muscle or body aches\n"
                + "Headache\n"
                + "New loss of taste or smell\n"
                + "Sore throat\n"
                + "Congestion or runny nose\n"
                + "Nausea or vomiting\n"
                + "Diarrhea\n"
                + "\nThis list does not include all possible symptoms\n"));
    JFXButton closeButton = new JFXButton("Close");
    symptoms.setActions(closeButton);

    // Creates the actual popup
    JFXDialog symptomsDialog =
        new JFXDialog(popupPane, symptoms, JFXDialog.DialogTransition.CENTER, false);

    // Closes the popup
    closeButton.setOnAction(
        event -> {
          symptomsDialog.close();
          popupPane.toBack();
        });
    symptomsDialog.show();
  }

  /**
   * Creates a popup to notify the user not to enter the hospital due to the risk of spreading
   * COVID-19
   *
   * @param popupPane is the stack pane on which the popup needs to be made on
   */
  public static void covidRisk(StackPane popupPane) {
    popupPane.toFront();

    // Creates the content for the popup
    JFXDialogLayout symptoms = new JFXDialogLayout();
    symptoms.setHeading(new Text("High Risk of COVID-19!"));
    symptoms.setBody(
        new Text(
            "You are at high risk of spreading COVID-19!\n"
                + "\nPlease do not enter the hospital\n"));
    JFXButton closeButton = new JFXButton("Close");
    symptoms.setActions(closeButton);

    // Creates the actual popup
    JFXDialog warningDialog =
        new JFXDialog(popupPane, symptoms, JFXDialog.DialogTransition.CENTER, false);

    // Closes the popup
    closeButton.setOnAction(
        event -> {
          warningDialog.close();
          popupPane.toBack();
        });
    warningDialog.show();
  }

  /**
   * Creates a popup to notify the user to select both start and end of path
   *
   * @param popupPane is the stack pane on which the popup needs to be made on
   */
  public static void invalidPathfind(StackPane popupPane) {
    popupPane.toFront();

    // Creates the content for the popup
    JFXDialogLayout warning = new JFXDialogLayout();
    warning.setHeading(new Text("Invalid Pathfinding"));
    warning.setBody(new Text("Please select starting and ending destination"));
    JFXButton closeButton = new JFXButton("Close");
    warning.setActions(closeButton);

    // Creates the actual popup
    JFXDialog warningDialog =
        new JFXDialog(popupPane, warning, JFXDialog.DialogTransition.CENTER, false);

    // Closes the popup
    closeButton.setOnAction(
        event -> {
          warningDialog.close();
          popupPane.toBack();
        });
    warningDialog.show();
  }

  /**
   * Creates a popup to notify the user that the new node has a duplicated NODEID
   *
   * @param popupPane is the stack pane on which the popup needs to be made on
   */
  public static void nodeAlreadyExists(StackPane popupPane) {
    popupPane.toFront();

    // Creates the content for the popup
    JFXDialogLayout warning = new JFXDialogLayout();
    warning.setHeading(new Text("Invalid Node"));
    warning.setBody(new Text("The given node ID already exists"));
    JFXButton closeButton = new JFXButton("Close");
    warning.setActions(closeButton);

    // Creates the actual popup
    JFXDialog warningDialog =
        new JFXDialog(popupPane, warning, JFXDialog.DialogTransition.CENTER, false);

    // Closes the popup
    closeButton.setOnAction(
        event -> {
          warningDialog.close();
          popupPane.toBack();
        });
    warningDialog.show();
  }

  /**
   * Creates a popup to notify the user that the node they are trying to access does not exist
   *
   * @param popupPane is the stack pane on which the popup needs to be made on
   */
  public static void nodeDoesntExist(StackPane popupPane) {
    popupPane.toFront();

    // Creates the content for the popup
    JFXDialogLayout warning = new JFXDialogLayout();
    warning.setHeading(new Text("Invalid Node"));
    warning.setBody(new Text("The given node ID does not exist and cannot be edited"));
    JFXButton closeButton = new JFXButton("Close");
    warning.setActions(closeButton);

    // Creates the actual popup
    JFXDialog warningDialog =
        new JFXDialog(popupPane, warning, JFXDialog.DialogTransition.CENTER, false);

    // Closes the popup
    closeButton.setOnAction(
        event -> {
          warningDialog.close();
          popupPane.toBack();
        });
    warningDialog.show();
  }

  /**
   * Creates a popup to notify the user that the new edge has a duplicated EDGEID
   *
   * @param popupPane is the stack pane on which the popup needs to be made on
   */
  public static void edgeAlreadyExists(StackPane popupPane) {
    popupPane.toFront();

    // Creates the content for the popup
    JFXDialogLayout warning = new JFXDialogLayout();
    warning.setHeading(new Text("Invalid Edge"));
    warning.setBody(new Text("The given edge already exists"));
    JFXButton closeButton = new JFXButton("Close");
    warning.setActions(closeButton);

    // Creates the actual popup
    JFXDialog warningDialog =
        new JFXDialog(popupPane, warning, JFXDialog.DialogTransition.CENTER, false);

    // Closes the popup
    closeButton.setOnAction(
        event -> {
          warningDialog.close();
          popupPane.toBack();
        });
    warningDialog.show();
  }

  /**
   * Creates a popup to notify the user that the edge they are trying to access doesnt exist
   *
   * @param popupPane is the stack pane on which the popup needs to be made on
   */
  public static void edgeDoesntExists(StackPane popupPane) {
    popupPane.toFront();

    // Creates the content for the popup
    JFXDialogLayout warning = new JFXDialogLayout();
    warning.setHeading(new Text("Invalid Edge"));
    warning.setBody(new Text("The given edge does not exist"));
    JFXButton closeButton = new JFXButton("Close");
    warning.setActions(closeButton);

    // Creates the actual popup
    JFXDialog warningDialog =
        new JFXDialog(popupPane, warning, JFXDialog.DialogTransition.CENTER, false);

    // Closes the popup
    closeButton.setOnAction(
        event -> {
          warningDialog.close();
          popupPane.toBack();
        });
    warningDialog.show();
  }

  /**
   * creating a popup to inform patient to enter using main entrance
   *
   * @param popupPane
   */
  public static void mainEntranceNotif(StackPane popupPane) {
    popupPane.toFront();

    // Creates the content for the popup
    JFXDialogLayout warning = new JFXDialogLayout();
    Text heading = new Text("Welcome to B&W Faulkner Hospital");
    heading.setWrappingWidth(200);
    warning.setHeading(heading);
    Text text =
        new Text(
            "Your entrance request has been approved. Please use the MAIN ATRIUM ENTRANCE. Have a great day!");
    text.setWrappingWidth(200);
    warning.setBody(text);
    JFXButton closeButton = new JFXButton("Close");
    warning.setActions(closeButton);

    // Creates the actual popup
    JFXDialog warningDialog =
        new JFXDialog(popupPane, warning, JFXDialog.DialogTransition.CENTER, false);
    warningDialog.setPrefWidth(200);

    // Closes the popup
    closeButton.setOnAction(
        event -> {
          warningDialog.close();
          popupPane.toBack();
        });
    warningDialog.show();
  }

  /**
   * creating a popup to inform patient to enter using covid entrance
   *
   * @param popupPane
   */
  public static void covidEntranceNotif(StackPane popupPane) {
    popupPane.toFront();

    // Creates the content for the popup
    JFXDialogLayout warning = new JFXDialogLayout();
    Text heading = new Text("Welcome to B&W Faulkner Hospital");
    heading.setWrappingWidth(200);
    warning.setHeading(heading);
    Text text =
        new Text(
            "Your entrance request has been approved. Please use the EMERGENCY ENTRANCE. Have a great day!");
    text.setWrappingWidth(200);
    warning.setBody(text);
    JFXButton closeButton = new JFXButton("Close");
    warning.setActions(closeButton);

    // Creates the actual popup
    JFXDialog warningDialog =
        new JFXDialog(popupPane, warning, JFXDialog.DialogTransition.CENTER, false);
    warningDialog.setPrefWidth(200);

    // Closes the popup
    closeButton.setOnAction(
        event -> {
          warningDialog.close();
          popupPane.toBack();
        });
    warningDialog.show();
  }

  /**
   * bada e rfe4
   *
   * @param popupPane
   */
  public static void invalidLocationA(StackPane popupPane) {
    popupPane.toFront();

    // Creates the content for the popup
    JFXDialogLayout warning = new JFXDialogLayout();
    warning.setHeading(new Text("Invalid Route"));
    warning.setBody(
        new Text(
            "Could not find a route using the locations specified,\n" + "Please try again..."));
    JFXButton closeButton = new JFXButton("Close");
    warning.setActions(closeButton);

    // Creates the actual popup
    JFXDialog warningDialog =
        new JFXDialog(popupPane, warning, JFXDialog.DialogTransition.CENTER, false);

    // Closes the popup
    closeButton.setOnAction(
        event -> {
          warningDialog.close();
          popupPane.toBack();
        });
    warningDialog.show();
  }

  /**
   * bada e rfe4
   *
   * @param popupPane
   */
  public static void invalidLocationMobile(StackPane popupPane) {
    popupPane.toFront();

    // Creates the content for the popup
    JFXDialogLayout warning = new JFXDialogLayout();
    warning.setHeading(new Text("Invalid Route"));
    Text text =
        new Text("Could not find a route using the locations specified.\n" + "Please try again...");
    text.setWrappingWidth(200);
    warning.setBody(text);
    JFXButton closeButton = new JFXButton("Close");
    warning.setActions(closeButton);

    // Creates the actual popup
    JFXDialog warningDialog =
        new JFXDialog(popupPane, warning, JFXDialog.DialogTransition.CENTER, false);
    warningDialog.setPrefWidth(200);
    // Closes the popup
    closeButton.setOnAction(
        event -> {
          warningDialog.close();
          popupPane.toBack();
        });
    warningDialog.show();
  }

  public static void serviceReqPopup(StackPane popupPane, String reqId) {
    popupPane.toFront();
    int wrapLength = 650;

    System.out.println("String: " + reqId);

    // Creates the content for the popup
    JFXDialogLayout warning = new JFXDialogLayout();
    Request request = RequestHandling.getRequest(Integer.parseInt(reqId));
    Text heading = new Text("Request " + reqId);
    heading.setStyle(
        "-fx-font-family: 'Leelawadee UI'; -fx-font-size: 18pt; -fx-font-weight: 700; ");
    warning.setHeading(heading);

    Text reqBy = new Text("Requested By: " + request.getRequestedBy());
    reqBy.setWrappingWidth(wrapLength);

    Text fulfilledBy = new Text("Fulfilled By: " + request.getFulfilledBy());
    fulfilledBy.setWrappingWidth(wrapLength);

    Text dateReq = new Text("Date Requested: " + request.getDateRequested().toString());
    dateReq.setWrappingWidth(wrapLength);

    Text dateNeeded = new Text("Date Needed: " + request.getDateNeeded().toString());
    dateNeeded.setWrappingWidth(wrapLength);

    Text location = new Text("Location: " + request.getLocationNodeID());
    location.setWrappingWidth(wrapLength);

    Text summary = new Text("Summary: " + request.getSummary());
    summary.setWrappingWidth(wrapLength);

    Text p1 = new Text("para1: " + request.getPara1());
    p1.setWrappingWidth(wrapLength);

    Text p2 = new Text("para2: " + request.getPara2());
    p2.setWrappingWidth(wrapLength);

    Text p3 = new Text("para3: " + request.getPara3());
    p3.setWrappingWidth(wrapLength);

    VBox addvbox = new VBox();
    addvbox
        .getChildren()
        .addAll(reqBy, fulfilledBy, dateReq, dateNeeded, location, summary, p1, p2, p3);
    addvbox.setStyle(
        "-fx-wrap-text: true; -fx-font-family: 'Leelawadee UI'; -fx-font-size: 16pt; -fx-spacing: 16pt; ");
    // text.setWrappingWidth(200);
    warning.setBody(addvbox);
    JFXButton closeButton = new JFXButton("Close");
    warning.setActions(closeButton);

    // Creates the actual popup
    JFXDialog warningDialog =
        new JFXDialog(popupPane, warning, JFXDialog.DialogTransition.CENTER, false);
    // Closes the popup
    closeButton.setOnAction(
        event -> {
          warningDialog.close();
          popupPane.toBack();
        });
    warningDialog.show();
  }

  public static void entryReqPopup(StackPane popupPane, String reqId) {
    popupPane.toFront();
    int wrapLength = 650;

    System.out.println("String: " + reqId);

    // Creates the content for the popup
    JFXDialogLayout warning = new JFXDialogLayout();
    EntryRequest request = EntryRequestHandling.getRequest(Integer.parseInt(reqId));
    Text heading = new Text("Request " + reqId);
    heading.setStyle(
        "-fx-font-family: 'Leelawadee UI'; -fx-font-size: 18pt; -fx-font-weight: 700; ");
    warning.setHeading(heading);

    Text reqBy = new Text("Requested By: " + request.getRequestedBy());
    reqBy.setWrappingWidth(wrapLength);

    Text fulfilledBy = new Text("Fulfilled By: " + request.getFulfilledBy());
    fulfilledBy.setWrappingWidth(wrapLength);

    Text dateReq = new Text("Date Requested: " + request.getDateRequested().toString());
    dateReq.setWrappingWidth(wrapLength);

    Text location = new Text("Location: " + request.getLocationNodeID());
    location.setWrappingWidth(wrapLength);

    Text symptoms = new Text("Summary: " + request.getIfSymptoms());
    symptoms.setWrappingWidth(wrapLength);

    Text check1 = new Text("para1: " + request.getCheck1());
    check1.setWrappingWidth(wrapLength);

    Text check2 = new Text("para2: " + request.getCheck2());
    check2.setWrappingWidth(wrapLength);

    VBox addvbox = new VBox();
    addvbox.getChildren().addAll(reqBy, fulfilledBy, dateReq, location, symptoms, check1, check2);
    addvbox.setStyle(
        "-fx-wrap-text: true; -fx-font-family: 'Leelawadee UI'; -fx-font-size: 16pt; -fx-spacing: 16pt; ");
    // text.setWrappingWidth(200);
    warning.setBody(addvbox);
    JFXButton closeButton = new JFXButton("Close");
    warning.setActions(closeButton);

    // Creates the actual popup
    JFXDialog warningDialog =
        new JFXDialog(popupPane, warning, JFXDialog.DialogTransition.CENTER, false);
    // Closes the popup
    closeButton.setOnAction(
        event -> {
          warningDialog.close();
          popupPane.toBack();
        });
    warningDialog.show();
  }
}
