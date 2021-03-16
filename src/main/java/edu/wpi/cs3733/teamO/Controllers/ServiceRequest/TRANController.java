package edu.wpi.cs3733.teamO.Controllers.ServiceRequest;

import static edu.wpi.cs3733.teamO.Database.UserHandling.getUsername;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.teamO.Database.RequestHandling;
import edu.wpi.cs3733.teamO.SRequest.Request;
import java.sql.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class TRANController {
  @FXML private JFXDatePicker dateNeeded;
  @FXML private JFXTextField locationF;
  @FXML private JFXTextArea summary;
  @FXML private JFXTextField field1;

  public void clear(ActionEvent actionEvent) {
    locationF.clear();
    dateNeeded.getEditor().clear();
    summary.clear();
    field1.clear();
  }

  public void submit(ActionEvent actionEvent) {
    String requestedBy = getUsername();
    java.sql.Date dateN = Date.valueOf(dateNeeded.getValue());
    String requestType = "TRAN";
    String loc = locationF.getText();
    String sum = summary.getText();
    String f1 = field1.getText();

    Request r = new Request();
    r.setRequestedBy(requestedBy);
    r.setDateNeeded(dateN);
    r.setRequestType(requestType);
    r.setLocationNodeID(loc);
    sum += ", Transport to: " + f1;
    r.setSummary(sum);
    RequestHandling.addRequest(r);
  }
}
