package edu.wpi.cs3733.teamO.Controllers;

import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class RequestPageController implements Initializable {
  @FXML private JFXComboBox<String> requestCombo;
  ObservableList<String> listOfRequests =
      FXCollections.observableArrayList("Laundry", "Maintenance", "Security");

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    requestCombo.setItems(listOfRequests);
  }

  public void goToCheckReq(ActionEvent actionEvent) {}

  public void newRequest(ActionEvent actionEvent) {}
}
