package Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class EmailPageController {
  @FXML private JFXTextField phoneNum;
  @FXML private ImageView QRView;
  @FXML private JFXButton backBtn;
  @FXML private JFXSpinner spinner;
  @FXML private JFXTextField email;
  @FXML private JFXButton confirmBtn;
  @FXML private ImageView mapView;

  public void back(ActionEvent actionEvent) throws IOException {

    Parent parent = FXMLLoader.load(getClass().getResource("/Views/Index.fxml"));
    Scene scene = new Scene(parent);
    // this gets Stage info
    Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    window.setTitle("Map Homepage");
    // this sets the scene to the new one specified above
    window.setScene(scene);
    window.show();
  }

  public void sendEmail(ActionEvent actionEvent) throws IOException {

    String emailString = email.getText();
    System.out.println(emailString);

    String home = System.getProperty("user.home");
    String outputFile = home + "/Downloads/" + "mapImageThingy.png";

    SharingFunctionality.sendEmailAttachment(emailString, outputFile);

    System.out.println("Starting Up");
    Parent parent = FXMLLoader.load(getClass().getResource("/Views/Submitted.fxml"));
    Scene scene = new Scene(parent);
    // this gets Stage info
    Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    window.setTitle("Submitted");
    // this sets the scene to the new one specified above
    window.setScene(scene);
    window.show();
  }

  public void sendText(ActionEvent actionEvent) throws IOException {

    String phoneString = phoneNum.getText();
    System.out.println(phoneString);

    String home = System.getProperty("user.home");
    String outputFile = home + "/Downloads/" + "mapImageThingy.png";

    SharingFunctionality.sendSMS(phoneString, outputFile);
    // phoneString, outputFile

    System.out.println("Starting Up");
    Parent parent = FXMLLoader.load(getClass().getResource("/Views/Submitted.fxml"));
    Scene scene = new Scene(parent);
    // this gets Stage info
    Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    window.setTitle("Submitted");
    // this sets the scene to the new one specified above
    window.setScene(scene);
    window.show();
  }
}