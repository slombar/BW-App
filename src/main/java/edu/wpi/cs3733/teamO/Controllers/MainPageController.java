package edu.wpi.cs3733.teamO.Controllers;

import edu.wpi.cs3733.teamO.Opp;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public class MainPageController {
  public void goToNav(ActionEvent actionEvent) {
    try {
      BorderPane root = FXMLLoader.load(getClass().getResource("/Views/NewNavPage.fxml"));
      Opp.getPrimaryStage().getScene().setRoot(root);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public void goToGifts(ActionEvent actionEvent) {}

  public void goToCovid(ActionEvent actionEvent) {}

  public void goToParking(ActionEvent actionEvent) {}
}
