package edu.wpi.cs3733.teamO.Controllers;

import com.jfoenix.controls.*;
import edu.wpi.cs3733.teamO.HelperClasses.RegexBoi;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import edu.wpi.cs3733.teamO.Sharing.SharingFunctionality;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class EmailPageController implements Initializable {

  @FXML private ImageView mapView5;
  @FXML private ImageView mapView3;
  @FXML private ImageView mapView2;
  @FXML private ImageView mapView1;
  @FXML private ImageView mapView0;
  @FXML private ImageView mapView4;
  @FXML private JFXButton textButton;
  @FXML private StackPane stackPane;
  @FXML private JFXTextField phoneNum;
  @FXML private ImageView QRView;
  @FXML private JFXButton backBtn;
  @FXML private JFXSpinner spinner;
  @FXML private JFXTextField email;
  @FXML private JFXButton confirmBtn;
  @FXML private static Image screenShot1;
  @FXML private static Image screenShot2;
  @FXML private static Image screenShot3;
  @FXML private static Image screenShot4;
  @FXML private static Image screenShot5;
  @FXML private static Image screenShot6;
  @FXML private StackPane sharePane; // this thing might fuck up our code :))=

  @Override
  public void initialize(URL url, ResourceBundle res) {

    mapView0.setImage(screenShot1);
    mapView1.setImage(screenShot2);
    mapView2.setImage(screenShot3);
    mapView3.setImage(screenShot4);
    mapView4.setImage(screenShot5);
    mapView5.setImage(screenShot6);
    backBtn.setStyle("-fx-background-color: #c3d6e8");
    confirmBtn.setStyle("-fx-background-color: #c3d6e8");
    textButton.setStyle("-fx-background-color: #c3d6e8");
  }

  public static void setScreenShot(
      Image sc1, Image sc2, Image sc3, Image sc4, Image sc5, Image sc6) {
    screenShot1 = sc1;
    screenShot2 = sc2;
    screenShot3 = sc3;
    screenShot4 = sc4;
    screenShot5 = sc5;
    screenShot6 = sc6;
  }

  private String errorMsg = "";

  public void back(ActionEvent actionEvent) throws IOException {
    SwitchScene.goToParent("/Views/NewNavPage.fxml");
  }

  public void sendText(ActionEvent actionEvent) throws IOException {

    String phoneString = phoneNum.getText();
    System.out.println(phoneString);

    if (RegexBoi.checkPhoneNum(phoneString)) {

      String home = System.getProperty("user.home");
      String outputFile = home + "/Downloads/" + "mapimg.png";

      SharingFunctionality.sendSMSTwillio(phoneString, outputFile);
      submissionPopup();

    } else {
      errorMsg = "Phone number is invalid. Try again with only numerical characters. (0-9)";
      System.out.print(errorMsg);
      invalidPopup();
    }
  }

  public void sendEmail(ActionEvent actionEvent) throws IOException {

    String emailString = email.getText();
    if (RegexBoi.checkEmail(emailString)) {
      String home = System.getProperty("user.home");
      String outputFile1 = home + "/Downloads/" + "mapimg1.png";
      String outputFile2 = home + "/Downloads/" + "mapimg2.png";
      String outputFile3 = home + "/Downloads/" + "mapimg3.png";
      String outputFile4 = home + "/Downloads/" + "mapimg4.png";
      String outputFile5 = home + "/Downloads/" + "mapimg5.png";
      String outputFile6 = home + "/Downloads/" + "mapimg6.png";

      SharingFunctionality.sendEmailAttachment(
          emailString,
          outputFile1,
          outputFile2,
          outputFile3,
          outputFile4,
          outputFile5,
          outputFile6);

      submissionPopup();

      //      mapView0 = new ImageView(outputFile1);
      //      mapView1 = new ImageView(outputFile2);
      //      mapView2 = new ImageView(outputFile3);
      //      mapView3 = new ImageView(outputFile4);
      //      mapView4 = new ImageView(outputFile5);
      //      mapView5 = new ImageView(outputFile6);

    } else {
      errorMsg =
          "Email is invalid. Make sure your email is spelled correctly and follows typical conventions. (email@company.com)";
      System.out.print("Email invalid.");
      invalidPopup();
    }
  }

  public void invalidPopup() {
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
          SwitchScene.goToParent("/Views/MainPage.fxml");
          /*AnchorPane root = null;
          try {
            root = FXMLLoader.load(getClass().getResource("/Views/MainPage.fxml"));
          } catch (IOException e) {
            e.printStackTrace();
          }
          Opp.getPrimaryStage().getScene().setRoot(root);*/
        });
    submissionDialog.show();
  }

  public void submissionPopup() {

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
    dialogVBox.getChildren().addAll(new Text("The message has been sent successfully."), buttonBox);
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
          SwitchScene.goToParent("/Views/NewNavPage.fxml");
        });
    submissionDialog.show();
  }
}
