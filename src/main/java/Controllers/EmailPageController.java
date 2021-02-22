package Controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class EmailPageController {

  public TextField email;
  public Button backBtn;
  public Button confirmBtn;
  public ImageView mapimage;
  public Canvas mapcanvas;
  public Button saveBtn;
  public AnchorPane mapanchor;

  public void back(ActionEvent actionEvent) throws IOException {

    System.out.println("Starting Up");
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

  public void sendText(ActionEvent actionEvent) {}
}
