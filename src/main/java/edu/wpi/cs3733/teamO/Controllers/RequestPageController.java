package edu.wpi.cs3733.teamO.Controllers;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.teamO.Opp;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

public class RequestPageController implements Initializable {
  @FXML private JFXComboBox<String> requestCombo;

  ObservableList<String> listOfRequests =
      FXCollections.observableArrayList("Computer Service", "Laundry", "Maintenance", "Security");

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    requestCombo.setItems(listOfRequests);
  }

  public void goToCheckReq(ActionEvent actionEvent) {}

  /**
   * method that checks the selection in teh drop down, if selected, should lead to that specific form
   * @param actionEvent
   */
  public void newRequest(ActionEvent actionEvent) {
    if (requestCombo.getValue().equals("Computer Service")) {
      goToRequestForm("/Views/ComputerServiceRequest.fxml");
    }
//    else if (requestCombo.getValue().equals("Laundry")) {
//      goToRequestForm("/Views/ComputerServiceRequest.fxml");
//    }
//    else if (requestCombo.getValue().equals("Maintenance")) {
//      goToRequestForm("/Views/ComputerServiceRequest.fxml");
//    }
//    else if (requestCombo.getValue().equals("Security")) {
//      goToRequestForm("/Views/ComputerServiceRequest.fxml");
//    }
  }

  /**
   * helper method that changes the scene to the form
   * @param path is the path of the desired fxml
   */
  private void goToRequestForm(String path) {
    try {
      BorderPane root = FXMLLoader.load(getClass().getResource(path));
      Opp.getPrimaryStage().getScene().setRoot(root);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
