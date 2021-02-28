package edu.wpi.cs3733.teamO.Controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.teamO.HelperClasses.Effects;
import edu.wpi.cs3733.teamO.Opp;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

public class RequestPageController implements Initializable {
  @FXML private JFXButton computerBtn;
  @FXML private JFXButton floralBtn;
  @FXML private JFXButton languageBtn;
  @FXML private JFXButton laundryBtn;
  @FXML private JFXButton giftBtn;
  @FXML private JFXButton transportBtn;
  @FXML private JFXButton maintenanceBtn;
  @FXML private JFXButton medicineBtn;
  @FXML private JFXButton laundryBtn2;
  @FXML private JFXButton securityBtn;
  @FXML private JFXButton sanitationBtn;
  @FXML private JFXButton moreBtn;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    hoverAllBtn();
  }

  public void hoverAllBtn() {
    Effects.hoverEffect(computerBtn);
    Effects.hoverEffect(floralBtn);
    Effects.hoverEffect(languageBtn);
    Effects.hoverEffect(laundryBtn);
    Effects.hoverEffect(giftBtn);
    Effects.hoverEffect(transportBtn);
    Effects.hoverEffect(maintenanceBtn);
    Effects.hoverEffect(medicineBtn);
    Effects.hoverEffect(laundryBtn2);
    Effects.hoverEffect(securityBtn);
    Effects.hoverEffect(sanitationBtn);
    Effects.hoverEffect(moreBtn);
  }

  public void goToCheckReq(ActionEvent actionEvent) {
    goToRequestForm("/Views/RequestStatus.fxml");
  }

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
    goToRequestForm("/Views/GiftDeliveryService.fxml");
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
