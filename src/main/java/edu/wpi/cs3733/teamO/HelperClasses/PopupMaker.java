package edu.wpi.cs3733.teamO.HelperClasses;

import com.jfoenix.controls.*;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class PopupMaker {

  // TODO: handle stopping multiple popups

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
    warning.setBody(new Text("Text fields cannot be left blank."));
    JFXButton closeButton = new JFXButton("Close");
    warning.setActions(closeButton);

    // Creates the actual popup
    JFXDialog warningDialog =
        new JFXDialog(popupPane, warning, JFXDialog.DialogTransition.CENTER, true);

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
        new JFXDialog(popupPane, warning, JFXDialog.DialogTransition.CENTER, true);

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
        new JFXDialog(popupPane, warning, JFXDialog.DialogTransition.CENTER, true);

    // Closes the popup
    closeButton.setOnAction(
        event -> {
          warningDialog.close();
          popupPane.toBack();
        });
    warningDialog.show();
  }

  /**
   * Creates a popup to notify the user of invalid username or email and why this may be
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
        new JFXDialog(popupPane, warning, JFXDialog.DialogTransition.CENTER, true);

    // Closes the popup
    closeButton.setOnAction(
        event -> {
          warningDialog.close();
          popupPane.toBack();
        });
    warningDialog.show();
  }

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
        new JFXDialog(popupPane, warning, JFXDialog.DialogTransition.CENTER, true);
    warningDialog.setOverlayClose(false);

    // Closes the popup
    closeButton.setOnAction(
        event -> {
          warningDialog.close();
          popupPane.toBack();
        });
    warningDialog.show();
  }

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
        new JFXDialog(popupPane, symptoms, JFXDialog.DialogTransition.CENTER, true);

    // Closes the popup
    closeButton.setOnAction(
        event -> {
          symptomsDialog.close();
          popupPane.toBack();
        });
    symptomsDialog.show();
  }

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
                new JFXDialog(popupPane, warning, JFXDialog.DialogTransition.CENTER, true);
        warningDialog.setOverlayClose(false);

        // Closes the popup
        closeButton.setOnAction(
                event -> {
                    warningDialog.close();
                    popupPane.toBack();
                });
        warningDialog.show();
    }
}
