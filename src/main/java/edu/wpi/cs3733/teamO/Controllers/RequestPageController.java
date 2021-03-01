package edu.wpi.cs3733.teamO.Controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.teamO.HelperClasses.Effects;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

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

  /** hovering over the button will make the buttons darker */
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
    SwitchScene.goToBorderPane("/Views/RequestStatus.fxml");
  }

  /**
   * below are all on action methods for the buttons on the request page it uses the goToRequest
   * helper and then leads to the specific form
   *
   * @param actionEvent
   */
  public void goToComputerReq(ActionEvent actionEvent) {
    RequestDisplayController.setType("COMP");
    SwitchScene.goToParent("/Views/RequestList.fxml");
  }

  public void goToFloralReq(ActionEvent actionEvent) {
    SwitchScene.goToBorderPane("/Views/RequestPage.fxml");
  }

  public void goToLanguageReq(ActionEvent actionEvent) {
    SwitchScene.goToBorderPane("/Views/RequestPage.fxml");
  }

  public void goToLaundryReq(ActionEvent actionEvent) {
    SwitchScene.goToBorderPane("/Views/LaundryRequest.fxml");
  }

  public void goToGiftReq(ActionEvent actionEvent) {
    SwitchScene.goToBorderPane("/Views/GiftDeliveryService.fxml");
  }

  public void goToTransportReq(ActionEvent actionEvent) {
    SwitchScene.goToBorderPane("/Views/InternalTransportForm.fxml");
  }

  public void goToMaintenance(ActionEvent actionEvent) {
    SwitchScene.goToBorderPane("/Views/FacilitiesMaintenanceRequest.fxml");
  }

  public void goToMedicineReq(ActionEvent actionEvent) {
    SwitchScene.goToBorderPane("/Views/MedicineDeliveryService.fxml");
  }

  public void goToSecurityReq(ActionEvent actionEvent) {
    SwitchScene.goToBorderPane("/Views/LaundryRequest.fxml");
  }

  public void goToSanitationReq(ActionEvent actionEvent) {
    SwitchScene.goToBorderPane("/Views/RequestPage.fxml");
  }
}
