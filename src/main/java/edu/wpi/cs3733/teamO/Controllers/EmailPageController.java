package edu.wpi.cs3733.teamO.Controllers;

import com.jfoenix.controls.*;
import com.mashape.unirest.http.exceptions.UnirestException;
import edu.wpi.cs3733.teamO.HelperClasses.RegexBoi;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import edu.wpi.cs3733.teamO.Sharing.EmailThreader;
import edu.wpi.cs3733.teamO.Sharing.ImgurFunctionality;
import edu.wpi.cs3733.teamO.Sharing.QRCodeThreader;
import edu.wpi.cs3733.teamO.Sharing.SharingFunctionality;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javax.imageio.ImageIO;

public class EmailPageController implements Initializable {

  @FXML private StackPane spinnerPane;
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
  private String errorMsg = "";

  /**
   * Initializes all components of email page including: setting screenshots to images, button
   * styles, and QR code positioning
   *
   * @param url
   * @param res
   */
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
    String home = System.getProperty("user.home");
    String inputfile = home + "/Downloads/" + "qr.png";
    BufferedImage img = null;
    try {
      img = ImageIO.read(new File(inputfile));
    } catch (IOException e) {
    }
    Image image = SwingFXUtils.toFXImage(img, null);
    QRView.setImage(image);
    QRView.setScaleX(2);
    QRView.setScaleY(2);
    QRView.setTranslateY(250);
  }

  /**
   * Sets screenshots of map images on canvas
   *
   * @param sc1
   * @param sc2
   * @param sc3
   * @param sc4
   * @param sc5
   * @param sc6
   */
  public static void setScreenShot(
      Image sc1, Image sc2, Image sc3, Image sc4, Image sc5, Image sc6) {
    screenShot1 = sc1;
    screenShot2 = sc2;
    screenShot3 = sc3;
    screenShot4 = sc4;
    screenShot5 = sc5;
    screenShot6 = sc6;
  }

  /**
   * Triggers back button functionality
   *
   * @param actionEvent
   * @throws IOException
   */
  public void back(ActionEvent actionEvent) throws IOException {
    SwitchScene.goToParent("/Views/NewNavPage.fxml");
  }

  /**
   * Triggers functions to send MMS text with map images to user
   *
   * @param actionEvent
   * @throws IOException
   * @throws UnirestException
   */
  public void sendText(ActionEvent actionEvent) throws IOException, UnirestException {

    String phoneString = phoneNum.getText();
    System.out.println(phoneString);

    // TODO reimplement regexboi checker for +1 area codes
    // if (RegexBoi.checkPhoneNum(phoneString)) {

    LinkedList<String> albumInfo = ImgurFunctionality.createImgurAlbum();
    String albumID = albumInfo.get(0);
    String albumDeleteHash = albumInfo.get(1);

    String outputFile1 = ImgurFunctionality.uploadImage("mapimg1.png");
    String outputFile2 = ImgurFunctionality.uploadImage("mapimg2.png");
    String outputFile3 = ImgurFunctionality.uploadImage("mapimg3.png");
    String outputFile4 = ImgurFunctionality.uploadImage("mapimg4.png");
    String outputFile5 = ImgurFunctionality.uploadImage("mapimg5.png");
    String outputFile6 = ImgurFunctionality.uploadImage("mapimg6.png");

    SharingFunctionality.sendSMSTwillio(
        phoneString, outputFile1, outputFile2, outputFile3, outputFile4, outputFile5, outputFile6);
    submissionPopup();

    // }
    // else {
    /*errorMsg = "Phone number is invalid. Try again with only numerical characters. (0-9)";
    System.out.print(errorMsg);
    invalidPopup();
    // }*/
  }

  /**
   * Triggers functions to prepare for generation of QR Code
   *
   * @throws IOException
   * @throws UnirestException
   */
  public static void prepareQR() throws IOException, UnirestException {
    // TODO reimplement regexboi checker for +1 area codes
    // if (RegexBoi.checkPhoneNum(phoneString)) {

    LinkedList<String> albumInfo = ImgurFunctionality.createImgurAlbum();
    String albumID = albumInfo.get(0);
    String albumDeleteHash = albumInfo.get(1);

    ImgurFunctionality.uploadToImgurAlbum("mapimg1.png", albumDeleteHash);
    ImgurFunctionality.uploadToImgurAlbum("mapimg1.png", albumDeleteHash);
    ImgurFunctionality.uploadToImgurAlbum("mapimg2.png", albumDeleteHash);
    ImgurFunctionality.uploadToImgurAlbum("mapimg3.png", albumDeleteHash);
    ImgurFunctionality.uploadToImgurAlbum("mapimg4.png", albumDeleteHash);
    ImgurFunctionality.uploadToImgurAlbum("mapimg5.png", albumDeleteHash);
    ImgurFunctionality.uploadToImgurAlbum("mapimg6.png", albumDeleteHash);

    String albumLink = "https://imgur.com/a/" + albumID;


    SharingFunctionality.createQR(albumLink);

    // submissionPopup();

    // }
    /*else {
      errorMsg = "Phone number is invalid. Try again with only numerical characters. (0-9)";
      System.out.print(errorMsg);
      invalidPopup();
    }*/
  }

  /**
   * Triggers action to send email of map images to user
   *
   * @param actionEvent
   * @throws IOException
   */
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

    } else {
      errorMsg =
          "Email is invalid. Make sure your email is spelled correctly and follows typical conventions. (email@company.com)";
      System.out.print("Email invalid.");
      invalidPopup();
    }
  }

  /** Popup for when user inputs invalid values for text fields */
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

  /** Popup for when user has successfully submits email/phone number */
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
