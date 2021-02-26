package edu.wpi.cs3733.teamO.Controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.teamO.Opp;
import edu.wpi.cs3733.teamO.Sharing.SharingFunctionality;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class EmailPageController implements Initializable {

  @FXML private JFXButton textButton;
  @FXML private StackPane stackPane;
  @FXML private JFXTextField phoneNum;
  @FXML private ImageView QRView;
  @FXML private JFXButton backBtn;
  @FXML private JFXSpinner spinner;
  @FXML private JFXTextField email;
  @FXML private JFXButton confirmBtn;
  @FXML private ImageView mapView;
  @FXML private static Image screenShot;

  @Override
  public void initialize(URL url, ResourceBundle res) {

    mapView.setImage(screenShot);
    backBtn.setStyle("-fx-background-color: #c3d6e8");
    confirmBtn.setStyle("-fx-background-color: #c3d6e8");
    textButton.setStyle("-fx-background-color: #c3d6e8");
  }

  public static void setScreenShot(Image sc) {
    screenShot = sc;
  }

  private String errorMsg = "";

  public void back(ActionEvent actionEvent) throws IOException {
    AnchorPane root = FXMLLoader.load(getClass().getResource("/Views/Index.fxml"));
    Opp.getPrimaryStage().setFullScreen(true);
    Opp.getPrimaryStage().getScene().setRoot(root);
  }

  public static boolean isValidEmail(String email) {
    String emailRegex =
        "^[a-zA-Z0-9_+&*-]+(?:\\."
            + "[a-zA-Z0-9_+&*-]+)*@"
            + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
            + "A-Z]{2,7}$";

    Pattern pat = Pattern.compile(emailRegex);
    if (email == null) return false;
    return pat.matcher(email).matches();
  }

  public static boolean isValidNum(String s) {
    // The given argument to compile() method
    // is regular expression. With the help of
    // regular expression we can validate mobile
    // number.
    // 1) Begins with 0 or 91
    // 2) Then contains 7 or 8 or 9.
    // 3) Then contains 9 digits
    Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}");

    // Pattern class contains matcher() method
    // to find matching between given number
    // and regular expression
    Matcher m = p.matcher(s);
    return (m.find() && m.group().equals(s));
  }

  public void sendText(ActionEvent actionEvent) throws IOException {

    String phoneString = phoneNum.getText();
    System.out.println(phoneString);

    if (isValidNum(phoneString)) {

      String home = System.getProperty("user.home");
      String outputFile = home + "/Downloads/" + "mapimg.png";

      SharingFunctionality.sendSMS(phoneString, outputFile);
      submissionPopup();

    } else {
      errorMsg = "Phone number is invalid. Try again with only numerical characters. (0-9)";
      System.out.print(errorMsg);
      invalidPopup();
    }
  }

  public void sendEmail(ActionEvent actionEvent) throws IOException {
    String emailString = email.getText();
    System.out.println(emailString);

    if (isValidEmail(emailString)) {
      String home = System.getProperty("user.home");
      String outputFile = home + "/Downloads/" + "mapimg.png";

      SharingFunctionality.sendEmailAttachment(emailString, outputFile);

      submissionPopup();

      mapView = new ImageView(outputFile);

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
}
