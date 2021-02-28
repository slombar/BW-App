package edu.wpi.cs3733.teamO.HelperClasses;

import com.jfoenix.controls.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
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
        new JFXDialog(popupPane, warning, JFXDialog.DialogTransition.BOTTOM, true);
    warningDialog.setOverlayClose(false);
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
        new JFXDialog(popupPane, warning, JFXDialog.DialogTransition.BOTTOM, true);
    warningDialog.setOverlayClose(false);

    // Closes the popup
    closeButton.setOnAction(
        event -> {
          warningDialog.close();
          popupPane.toBack();
        });
    warningDialog.show();
  }

  /**
   * creates the popup for a patient to sign in
   *
   * @param popupPane is the stack pane on which the popup needs to be made on
   */
  public static void patientSignInPopup(StackPane popupPane) {
    popupPane.toFront();
    JFXDialogLayout signInLayout = new JFXDialogLayout();
    signInLayout.setStyle("-fx-background-color: #F2F2F2");
    String popupLayout = "/Views/PatientSignInPopup.fxml";
    try {
      GridPane root = FXMLLoader.load(PopupMaker.class.getResource(popupLayout));
      signInLayout.setBody(root);
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Could not find resource " + popupLayout);
    }
    JFXDialog patientSignInDialog =
        new JFXDialog(popupPane, signInLayout, JFXDialog.DialogTransition.CENTER, true);
    patientSignInDialog.setOverlayClose(false);
    patientSignInDialog.show();
  }
  /**
   * creates the popup for an employee to sign in
   *
   * @param popupPane is the stack pane on which the popup needs to be made on
   */
  public static void employeeSignInPopup(StackPane popupPane) {
    popupPane.toFront();
    JFXDialogLayout signInLayout = new JFXDialogLayout();
    signInLayout.setStyle("-fx-background-color: #F2F2F2");
    String popupLayout = "/Views/EmployeeSignInPopup.fxml";
    // TODO make this file!
    try {
      GridPane root = FXMLLoader.load(PopupMaker.class.getResource(popupLayout));
      signInLayout.setBody(root);
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Could not find resource " + popupLayout);
    }
    JFXDialog employeeSignInDialog =
        new JFXDialog(popupPane, signInLayout, JFXDialog.DialogTransition.CENTER);
    employeeSignInDialog.setOverlayClose(true);
    employeeSignInDialog.show();
  }
}
