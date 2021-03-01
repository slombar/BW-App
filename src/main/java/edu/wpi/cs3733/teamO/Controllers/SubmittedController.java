package edu.wpi.cs3733.teamO.Controllers;

import edu.wpi.cs3733.teamO.Opp;
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
    AnchorPane root = FXMLLoader.load(getClass().getResource("/Views/Archive/Index.fxml"));
    // errors TODO: fix
    Opp.getPrimaryStage().setFullScreen(true);
    Opp.getPrimaryStage().getScene().setRoot(root);
  }
}
