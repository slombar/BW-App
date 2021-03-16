package edu.wpi.cs3733.teamO.Controllers.ServiceRequest;

import static edu.wpi.cs3733.teamO.Controllers.ServiceRequest.RequestPageController.getReqType;
import static edu.wpi.cs3733.teamO.Database.UserHandling.getSessionUsername;

import com.jfoenix.controls.*;
import edu.wpi.cs3733.teamO.Database.RequestHandling;
import edu.wpi.cs3733.teamO.SRequest.Request;
import java.sql.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class SECUController {

  @FXML private JFXDatePicker dateNeeded;
  @FXML private JFXTextField locationF;
  @FXML private JFXTextArea summary;
  @FXML private JFXTextField field1;
  @FXML private JFXSlider field2;
  @FXML private JFXCheckBox field3;

  public void clear(ActionEvent actionEvent) {
    locationF.clear();
    dateNeeded.getEditor().clear();
    summary.clear();
    field1.clear();
    field2.setValue(1);
  }

  public void submit(ActionEvent actionEvent) {

    String requestedBy = getSessionUsername();
    java.sql.Date dateN = Date.valueOf(dateNeeded.getValue());
    String requestType = getReqType();
    String loc = locationF.getText();
    String sum = summary.getText();
    String f1 = field1.getText();
    String f2 = String.valueOf(field2.getValue());
    String f3 = String.valueOf(field3.isSelected());

    Request r = new Request();
    r.setRequestedBy(requestedBy);
    r.setDateNeeded(dateN);
    r.setRequestType(requestType);
    r.setRequestLocation(loc);
    sum += " Threat Level: " + f2 + " Armed?: " + f3 + "Situation: " + f1;
    r.setSummary(sum);
    RequestHandling.addRequest(r);
  }
}
