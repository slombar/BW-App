package edu.wpi.cs3733.teamO.Controllers.ServiceRequest;

import static edu.wpi.cs3733.teamO.Controllers.ServiceRequest.RequestPageController.getReqType;
import static edu.wpi.cs3733.teamO.Database.UserHandling.getUsername;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.teamO.Database.RequestHandling;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import java.sql.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class COMPController {

  @FXML private JFXDatePicker dateNeeded;
  @FXML private JFXTextField locationF;
  @FXML private JFXTextArea summary;
  @FXML private JFXTextField field1;
  @FXML private JFXCheckBox field2;

  public void back(ActionEvent actionEvent) {
    SwitchScene.goToParent("/Views/ServiceRequests/RequestList.fxml");
  }

  public void clear(ActionEvent actionEvent) {
    locationF.clear();
    dateNeeded.getEditor().clear();
    summary.clear();
    field1.clear();
    field2.setSelected(false);
  }

  public void submit(ActionEvent actionEvent) {

    String requestedBy = getUsername();
    java.sql.Date dateN = Date.valueOf(dateNeeded.getValue());
    String requestType = getReqType();
    String loc = locationF.getText();
    String sum = summary.getText();
    String f1 = field1.getText();
    String f2 = null;
    String f3 = null;

    System.out.println(
        "Adding this to DB: "
            + requestedBy
            + dateN.toString()
            + requestType
            + loc
            + sum
            + f1
            + f2
            + f3);

    RequestHandling.addRequest(requestedBy, dateN, requestType, loc, sum, f1, f2, f3);

    SwitchScene.goToParent("/Views/ServiceRequests/RequestList.fxml");
  }
}
