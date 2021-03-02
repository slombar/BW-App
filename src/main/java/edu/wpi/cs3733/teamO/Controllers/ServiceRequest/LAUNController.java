package edu.wpi.cs3733.teamO.Controllers.ServiceRequest;

import static edu.wpi.cs3733.teamO.Controllers.ServiceRequest.RequestPageController.getReqType;

import com.jfoenix.controls.*;
import edu.wpi.cs3733.teamO.Database.RequestHandling;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class LAUNController implements Initializable {

  @FXML private JFXComboBox sizeComboBox;
  @FXML private JFXDatePicker dateNeeded;
  @FXML private JFXTextField locationF;
  @FXML private JFXTextArea summary;
  @FXML private JFXTextField field1;
  @FXML private JFXCheckBox field2;
  private String loadSize = "";

  ObservableList<String> listOfSizes = FXCollections.observableArrayList(
      "XS", "S", "M", "L", "XL");

  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }

  public void back(ActionEvent actionEvent) {
    SwitchScene.goToParent("/Views/MainPage.fxml");
  }

  public void clear(ActionEvent actionEvent) {
    locationF.clear();
    dateNeeded.getEditor().clear();
    summary.clear();
    field1.clear();
    field2.setSelected(false);
  }

  public void submit(ActionEvent actionEvent) {
    // send values to DB TODO implement proper username
    String requestedBy = "user"; // getUsername();
    java.sql.Date dateN = Date.valueOf(dateNeeded.getValue());
    String requestType = getReqType();
    String loc = locationF.getText();
    String sum = summary.getText();
    String f1 = field1.getText();
    String f2 = field2.getText();
    String f3 = loadSize;

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

  public void comboBoxAction(ActionEvent actionEvent) {
    loadSize = (String) sizeComboBox.getValue();
  }

}
