package edu.wpi.teamO.Controllers;

import edu.wpi.teamO.Opp;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

// hello??????
public class EmailPageController {

  public TextField email;
  public Button backBtn;
  public Button confirmBtn;
  public ImageView mapimage;
  public Canvas mapcanvas;
  public Button saveBtn;
  public AnchorPane mapanchor;

  public void back(ActionEvent actionEvent) throws IOException {
    try {
      AnchorPane root = FXMLLoader.load(getClass().getResource("/Views/Index.fxml"));
      // errors TODO: fix
      Opp.getPrimaryStage().getScene().setRoot(root);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  @FXML
  public void sendEmail(ActionEvent actionEvent) throws IOException {
    String emailString = email.getText();
    System.out.println(emailString);

    String home = System.getProperty("user.home");
    String outputFile = home + "/Downloads/" + "mapImageThingy.png";

    SharingFunctionality.sendEmailAttachment(emailString, outputFile);

    AnchorPane root = FXMLLoader.load(getClass().getResource("/Views/Index.fxml"));
    // errors TODO: fix
    Opp.getPrimaryStage().getScene().setRoot(root);
  }

  public void sendText(ActionEvent actionEvent) {}
}
