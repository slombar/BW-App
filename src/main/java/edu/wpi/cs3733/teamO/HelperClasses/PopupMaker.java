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

public static void invalidLogin(StackPane popupPane) {
    popupPane.toFront();

    // Creates the content for the popup
    JFXDialogLayout warning = new JFXDialogLayout();
    warning.setHeading(new Text("WARNING!"));
    warning.setBody(new Text("Incorrect Username or passowrd"));
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

  public static void invalidUsername(StackPane popupPane) {
    popupPane.toFront();

    // Creates the content for the popup
    JFXDialogLayout warning = new JFXDialogLayout();
    warning.setHeading(new Text("Invalid Username"));
    warning.setBody(new Text("1. Username consists of alphanumeric characters (a-zA-Z0-9), lowercase, or uppercase. 2.\n" +
            "   * Username allowed of the dot (.), underscore (_), and hyphen (-). 3. The dot (.), underscore\n" +
            "   * (_), or hyphen (-) must not be the first or last character. 4. The dot (.), underscore (_), or\n" +
            "   * hyphen (-) does not appear consecutively, e.g., java..regex 5. The number of characters must be\n" +
            "   * between 3 to 20."));
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



}
