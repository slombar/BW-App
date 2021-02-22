package Controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SubmittedController {

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
}
