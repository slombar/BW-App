package edu.wpi.cs3733.teamO.HelperClasses;

import com.jfoenix.controls.*;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class PopupMaker {
  /**
   * this is a test popup that says it is a test; used for testing
   *
   * @param popupPane is the stack pane on which the popup needs to be made on
   */
  public static void testPopup(StackPane popupPane) {
    JFXDialogLayout testLayout = new JFXDialogLayout();
    testLayout.setHeading(new Text("Test Heading"));
    testLayout.setBody(new Text("Test Body"));
    JFXButton closeButton = new JFXButton("Close");
    testLayout.setActions(closeButton); // sets it to be at bottom
    JFXDialog testDialog = new JFXDialog(popupPane, testLayout, JFXDialog.DialogTransition.BOTTOM);
    closeButton.setOnAction(
        event -> {
          testDialog.close();
          popupPane.toBack();
        });
    popupPane.toFront();
    testDialog.show();
  }

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
        new JFXDialog(popupPane, warning, JFXDialog.DialogTransition.BOTTOM, false);
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
