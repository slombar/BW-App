package edu.wpi.cs3733.teamO.Controllers.ServiceRequest;

import static edu.wpi.cs3733.teamO.Controllers.ServiceRequest.RequestPageController.getReqType;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.teamO.Database.RequestHandling;
import edu.wpi.cs3733.teamO.Database.UserHandling;
import edu.wpi.cs3733.teamO.SRequest.Request;
import java.sql.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class GIFTController {

  @FXML private JFXDatePicker dateNeeded;
  @FXML private JFXTextField locationF;
  @FXML private JFXTextArea summary;
  @FXML private JFXTextField field1;
  @FXML private JFXTextField field2;
  @FXML private JFXCheckBox field3;

  public void clear(ActionEvent actionEvent) {
    locationF.clear();
    dateNeeded.getEditor().clear();
    summary.clear();
    field1.clear();
    field2.clear();
    field3.setSelected(false);
  }

  public void submit(ActionEvent actionEvent) {
    // send values to DB
    String requestedBy = UserHandling.getSessionUsername();
    java.sql.Date dateN = Date.valueOf(dateNeeded.getValue());
    String requestType = getReqType();
    String loc = locationF.getText();
    String sum = summary.getText();
    String f1 = field1.getText();
    String f2 = field2.getText();
    String f3 = String.valueOf(field3.isSelected());

    Request r = new Request();
    r.setRequestedBy(requestedBy);
    r.setDateNeeded(dateN);
    r.setRequestType(requestType);
    r.setRequestLocation(loc);
    sum += " GiftType: " + f1 + " Message: " + f2 + "Signature?: " + f3;
    r.setSummary(sum);
    RequestHandling.addRequest(r);
  }
}
