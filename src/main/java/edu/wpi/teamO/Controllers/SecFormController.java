package edu.wpi.teamO.Controllers;

import edu.wpi.teamO.Opp;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class SecFormController {
  /**
   * Scene switching
   *
   * @param actionEvent
   * @throws IOException
   */
  @FXML
  public void goToHomePage(ActionEvent actionEvent) throws IOException {
    AnchorPane root = FXMLLoader.load(getClass().getResource("/Views/Index.fxml"));
    // errors TODO: fix
    Opp.getPrimaryStage().getScene().setRoot(root);
  }
}
