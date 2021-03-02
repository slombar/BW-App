package edu.wpi.cs3733.teamO.Controllers;

import com.jfoenix.controls.*;
import edu.wpi.cs3733.teamO.HelperClasses.RegexBoi;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import edu.wpi.cs3733.teamO.Opp;
import edu.wpi.cs3733.teamO.Sharing.SharingFunctionality;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javax.imageio.ImageIO;

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
  @FXML private StackPane sharePane;    // this thing might fuck up our code :))

  public Canvas mapcanvas;
  public AnchorPane mapanchor;

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
    SwitchScene.goToParent("/Views/NewNavPage.fxml");
  }

  public void sendText(ActionEvent actionEvent) throws IOException {

    String phoneString = phoneNum.getText();
    System.out.println(phoneString);

    if (RegexBoi.checkPhoneNum(phoneString)) {

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

  public void save(ActionEvent actionEvent) throws IOException {
    sharePane.toBack();
    GraphicsContext gc = mapcanvas.getGraphicsContext2D();

    String home = System.getProperty("user.home");
    File outputFile = new File(home + "/Downloads/" + "mapimg.png");

    WritableImage map = mapanchor.snapshot(new SnapshotParameters(), null);
    ImageIO.write(SwingFXUtils.fromFXImage(map, null), "png", outputFile);
    Image newimg = map;

    EmailPageController.setScreenShot(newimg);

    // add the scene switch
    AnchorPane root = FXMLLoader.load(getClass().getResource("/Views/EmailPage.fxml"));
    Opp.getPrimaryStage().getScene().setRoot(root);
  }

  public void sendEmail(ActionEvent actionEvent) throws IOException {
    String emailString = email.getText();
    System.out.println(emailString);

    if (RegexBoi.checkEmail(emailString)) {
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
            root = FXMLLoader.load(getClass().getResource("/Views/Archive/Index.fxml"));
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
            root = FXMLLoader.load(getClass().getResource("/Views/Archive/Index.fxml"));
          } catch (IOException e) {
            e.printStackTrace();
          }
          Opp.getPrimaryStage().getScene().setRoot(root);
        });
    submissionDialog.show();
  }
}
