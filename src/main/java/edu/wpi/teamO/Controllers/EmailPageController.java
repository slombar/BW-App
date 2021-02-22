package edu.wpi.teamO.Controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.teamO.Opp;
import java.io.IOException;
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

  public void back(ActionEvent actionEvent) throws IOException {
    AnchorPane root = FXMLLoader.load(getClass().getResource("/Views/Index.fxml"));
    Opp.getPrimaryStage().getScene().setRoot(root);
  }

  public void sendEmail(ActionEvent actionEvent) throws IOException {
    String emailString = email.getText();
    System.out.println(emailString);

    String home = System.getProperty("user.home");
    String outputFile = home + "/Downloads/" + "mapImageThingy.png";

    SharingFunctionality.sendEmailAttachment(emailString, outputFile);

    sumbissionPopup();

    //    AnchorPane root = FXMLLoader.load(getClass().getResource("/Views/Submitted.fxml"));
    //    Opp.getPrimaryStage().getScene().setRoot(root);
  }

  public void sendText(ActionEvent actionEvent) throws IOException {

    String phoneString = phoneNum.getText();
    System.out.println(phoneString);

    String home = System.getProperty("user.home");
    String outputFile = home + "/Downloads/" + "mapImageThingy.png";

    SharingFunctionality.sendSMS(phoneString, outputFile);
    // phoneString, outputFile

    AnchorPane root = FXMLLoader.load(getClass().getResource("/Views/Submitted.fxml"));
    Opp.getPrimaryStage().getScene().setRoot(root);
  }

  public void sumbissionPopup() {
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
    }
  }
}
