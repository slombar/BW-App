package edu.wpi.teamO.Controllers;

import edu.wpi.teamO.Opp;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class SubmittedController {
  /**
   * Send user back to index.fxml
   *
   * @param actionEvent
   * @throws IOException
   */
  public void back(ActionEvent actionEvent) throws IOException {
    AnchorPane root = FXMLLoader.load(getClass().getResource("/Views/Index.fxml"));
    // errors TODO: fix
    Opp.getPrimaryStage().getScene().setRoot(root);
  }
}
