package edu.wpi.cs3733.teamO.Controllers.ServiceRequest;

import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class FacilitiesMaintenanceController implements Initializable {

  @FXML private JFXComboBox comboBox;

  ObservableList<String> listOfFloors =
      FXCollections.observableArrayList(
          "Device Maintenance",
          "Furniture Maintenance",
          "Pluming Maintenance",
          "Electrical Maintenance",
          "Other");

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    comboBox.setItems(listOfFloors);
  }
}
