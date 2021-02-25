package edu.wpi.cs3733.teamO.HelperClasses;

import com.jfoenix.controls.*;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class PopupMaker {

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
}
