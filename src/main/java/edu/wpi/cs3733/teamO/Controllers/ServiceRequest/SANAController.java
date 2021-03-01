package edu.wpi.cs3733.teamO.Controllers.ServiceRequest;

import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import javafx.event.ActionEvent;

public class SANAController {

  public void back(ActionEvent actionEvent) {
    SwitchScene.goToParent("/Views/ServiceRequests/MainPage.fxml");
  }

  public void clear(ActionEvent actionEvent) {}

  public void submit(ActionEvent actionEvent) {}
}
