package edu.wpi.teamO.Controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.teamO.Opp;
import java.io.IOException;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class EmailPageController {

  @FXML private StackPane stackPane;
  @FXML private JFXTextField phoneNum;
  @FXML private ImageView QRView;
  @FXML private JFXButton backBtn;
  @FXML private JFXSpinner spinner;
  @FXML private JFXTextField email;
  @FXML private JFXButton confirmBtn;
  @FXML private ImageView mapView;

  private boolean popUp = false;
  private boolean success = false;

  public void back(ActionEvent actionEvent) throws IOException {
    AnchorPane root = FXMLLoader.load(getClass().getResource("/Views/Index.fxml"));
    Opp.getPrimaryStage().getScene().setRoot(root);
  }

  public static boolean isValid(String email) {
    String emailRegex =
        "^[a-zA-Z0-9_+&*-]+(?:\\."
            + "[a-zA-Z0-9_+&*-]+)*@"
            + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
            + "A-Z]{2,7}$";

    Pattern pat = Pattern.compile(emailRegex);
    if (email == null) return false;
    return pat.matcher(email).matches();
  }

  public void sendEmail(ActionEvent actionEvent) throws IOException {
    String emailString = email.getText();
    System.out.println(emailString);

    if (isValid(emailString)) {
      System.out.print("Email valid. Sending...");
      success = true;
    } else {
      System.out.print("Email invalid.");
      success = false;
    }

    String home = System.getProperty("user.home");
    String outputFile = home + "/Downloads/" + "mapImageThingy.png";

    SharingFunctionality.sendEmailAttachment(emailString, outputFile);

    // still need to test if this works
    if (success) {
      submissionPopup();

    } else {
      invalidEmailPopup();
    }
  }

  public void sendText(ActionEvent actionEvent) throws IOException {

    String phoneString = phoneNum.getText();
    System.out.println(phoneString);

    String home = System.getProperty("user.home");
    String outputFile = home + "/Downloads/" + "mapImageThingy.png";

    SharingFunctionality.sendSMS(phoneString, outputFile);
    // phoneString, outputFile
    Boolean paneBool = submissionPopup();
  }

  public void invalidEmailPopup() {
    // dialogContent has the conetnt of the popup
    JFXDialogLayout dialogContent = new JFXDialogLayout();
    dialogContent.setHeading(new Text("Failed. Invalid Information."));
    VBox dialogVBox = new VBox(12);

    // Creating an HBox of buttons
    HBox buttonBox = new HBox(20);
    JFXButton closeButton = new JFXButton("Close");
    JFXButton homeButton = new JFXButton("Return to Homepage");
    buttonBox.getChildren().addAll(closeButton, homeButton);

    // Creating the format
    dialogVBox
        .getChildren()
        .addAll(
            new Text("The message has not been sent, your credentials are invalid."), buttonBox);
    dialogContent.setBody(dialogVBox);

    // Bringing the popup screen to the front and disabling the background
    stackPane.toFront();
    JFXDialog submissionDialog =
        new JFXDialog(stackPane, dialogContent, JFXDialog.DialogTransition.BOTTOM);
    submissionDialog.setOverlayClose(false);

    // Closing the popup
    closeButton.setOnAction(
        event -> {
          submissionDialog.close();
          stackPane.toBack();
        });

    // go to Index/Homepage
    homeButton.setOnAction(
        event -> {
          submissionDialog.close();
          stackPane.toBack();
          AnchorPane root = null;
          try {
            root = FXMLLoader.load(getClass().getResource("/Views/Index.fxml"));
          } catch (IOException e) {
            e.printStackTrace();
          }
          Opp.getPrimaryStage().getScene().setRoot(root);
        });
    submissionDialog.show();
  }

  public boolean submissionPopup() {

    if (!popUp) {
      popUp = true;

      // dialogContent has the conetnt of the popup
      JFXDialogLayout dialogContent = new JFXDialogLayout();
      dialogContent.setHeading(new Text("Success!"));
      VBox dialogVBox = new VBox(12);

      // Creating an HBox of buttons
      HBox buttonBox = new HBox(20);
      JFXButton closeButton = new JFXButton("Close");
      JFXButton homeButton = new JFXButton("Return to Homepage");
      buttonBox.getChildren().addAll(closeButton, homeButton);

      // Creating the format
      dialogVBox
          .getChildren()
          .addAll(new Text("The message has been sent successfully."), buttonBox);
      dialogContent.setBody(dialogVBox);

      // Bringing the popup screen to the front and disabling the background
      stackPane.toFront();
      JFXDialog submissionDialog =
          new JFXDialog(stackPane, dialogContent, JFXDialog.DialogTransition.BOTTOM);
      submissionDialog.setOverlayClose(false);

      // Closing the popup
      closeButton.setOnAction(
          event -> {
            submissionDialog.close();
            stackPane.toBack();
            popUp = false;
          });

      // go to Index/Homepage
      homeButton.setOnAction(
          event -> {
            submissionDialog.close();
            stackPane.toBack();
            AnchorPane root = null;
            try {
              root = FXMLLoader.load(getClass().getResource("/Views/Index.fxml"));
            } catch (IOException e) {
              e.printStackTrace();
            }
            Opp.getPrimaryStage().getScene().setRoot(root);
            popUp = false;
          });
      submissionDialog.show();
    }
    return success;
  }
}
