package edu.wpi.cs3733.teamO.Controllers.ServiceRequest;

import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import javafx.event.ActionEvent;

public class ServicesController {

  public static String reqType;

  public void goToComputerReq(ActionEvent actionEvent) {
    reqType = "COMP";
    SwitchScene.goToParent("/Views/ServiceRequests/RequestList.fxml");
  }

  public void goToFloralReq(ActionEvent actionEvent) {
    reqType = "FLOR";
    SwitchScene.goToParent("/Views/ServiceRequests/RequestList.fxml");
  }

  public void goToLanguageReq(ActionEvent actionEvent) {
    reqType = "LANG";
    SwitchScene.goToParent("/Views/ServiceRequests/RequestList.fxml");
  }

  public void goToLaundryReq(ActionEvent actionEvent) {
    reqType = "LAUN";
    SwitchScene.goToParent("/Views/ServiceRequests/RequestList.fxml");
  }

  public void goToGiftReq(ActionEvent actionEvent) {
    reqType = "GIFT";
    SwitchScene.goToParent("/Views/ServiceRequests/RequestList.fxml");
  }

  public void goToTransportReq(ActionEvent actionEvent) {
    reqType = "TRAN";
    SwitchScene.goToParent("/Views/ServiceRequests/RequestList.fxml");
  }

  public void goToMaintenance(ActionEvent actionEvent) {
    reqType = "MAIT";
    SwitchScene.goToParent("/Views/ServiceRequests/RequestList.fxml");
  }

  public void goToMedicineReq(ActionEvent actionEvent) {
    reqType = "MEDI";
    SwitchScene.goToParent("/Views/ServiceRequests/RequestList.fxml");
  }

  public void goToSecurityReq(ActionEvent actionEvent) {
    reqType = "SECU";
    SwitchScene.goToParent("/Views/ServiceRequests/RequestList.fxml");
  }

  public void goToSanitationReq(ActionEvent actionEvent) {
    reqType = "SANA";
    SwitchScene.goToParent("/Views/ServiceRequests/RequestList.fxml");
  }

  public void goToCOVIDSurveyReq(ActionEvent actionEvent) {
    reqType = "CV19";
    SwitchScene.goToParent("/Views/ServiceRequests/RequestList.fxml");
  }
}
