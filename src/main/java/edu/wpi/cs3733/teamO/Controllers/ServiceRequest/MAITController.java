package edu.wpi.cs3733.teamO.Controllers.ServiceRequest;

import static edu.wpi.cs3733.teamO.Controllers.ServiceRequest.RequestPageController.getReqType;
import static edu.wpi.cs3733.teamO.Database.UserHandling.getSessionUsername;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.teamO.Database.RequestHandling;
import edu.wpi.cs3733.teamO.SRequest.Request;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class MAITController implements Initializable {
  @FXML private JFXDatePicker dateNeeded;
  @FXML private JFXTextField locationF;
  @FXML private JFXTextArea summary;
  @FXML private JFXTextField field1;
  @FXML private JFXComboBox selectbox;
  private String type = "";

  private ObservableList<String> typesOfRequest =
      FXCollections.observableArrayList("Electrical", "Motor Vehicle", "Door Repair", "Plumbing");

  public void comboBoxAction(ActionEvent actionEvent) {
    type = selectbox.getValue().toString();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    selectbox.setItems(typesOfRequest);
  }

  public void clear(ActionEvent actionEvent) {
    locationF.clear();
    dateNeeded.getEditor().clear();
    summary.clear();
    field1.clear();
  }

  public void submit(ActionEvent actionEvent) {

    String requestedBy = getSessionUsername();
    java.sql.Date dateN = Date.valueOf(dateNeeded.getValue());
    String requestType = getReqType();
    String loc = locationF.getText();
    String sum = summary.getText();
    String f1 = field1.getText();
    String f2 = type;
    String f3 = null;

    Request r = new Request();
    r.setRequestedBy(requestedBy);
    r.setDateNeeded(dateN);
    r.setRequestType(requestType);
    r.setRequestLocation(loc);
    sum += ". Type of Maintenance: " + f2 + ". Personnel: " + f1 + ".";
    r.setSummary(sum);
    RequestHandling.addRequest(r);
  }
}
