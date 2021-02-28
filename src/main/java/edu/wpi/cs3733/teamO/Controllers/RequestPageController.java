package edu.wpi.cs3733.teamO.Controllers;

import edu.wpi.cs3733.teamO.Opp;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

public class RequestPageController implements Initializable {
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {}

  public void goToCheckReq(ActionEvent actionEvent) {}

  /**
   * helper method that changes the scene to the form
   *
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

  /**
   * below are all on action methods for the buttons on the request page it uses the goToRequest
   * helper and then leads to the specific form
   *
   * @param actionEvent
   */
  public void goToComputerReq(ActionEvent actionEvent) {
    goToRequestForm("/Views/ComputerServiceRequest.fxml");
  }

  public void goToFloralReq(ActionEvent actionEvent) {
    goToRequestForm("/Views/RequestPage.fxml");
  }

  public void goToLanguageReq(ActionEvent actionEvent) {
    goToRequestForm("/Views/RequestPage.fxml");
  }

  public void goToLaundryReq(ActionEvent actionEvent) {
    goToRequestForm("/Views/LaundryRequest.fxml");
  }

  public void goToGiftReq(ActionEvent actionEvent) {
    goToRequestForm("/Views/RequestPage.fxml");
  }

  public void goToTransportReq(ActionEvent actionEvent) {
    goToRequestForm("/Views/RequestPage.fxml");
  }

  public void goToMaintenance(ActionEvent actionEvent) {
    goToRequestForm("/Views/FacilitiesMaintenanceRequest.fxml");
  }

  public void goToMedicineReq(ActionEvent actionEvent) {
    goToRequestForm("/Views/RequestPage.fxml");
  }

  public void goToSecurityReq(ActionEvent actionEvent) {
    goToRequestForm("/Views/RequestPage.fxml");
  }

  public void goToSanitationReq(ActionEvent actionEvent) {
    goToRequestForm("/Views/RequestPage.fxml");
  }
}
