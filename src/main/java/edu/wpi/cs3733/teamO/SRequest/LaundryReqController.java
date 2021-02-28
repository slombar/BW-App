package edu.wpi.cs3733.teamO.SRequest;

import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class LaundryReqController implements Initializable {
  @FXML private JFXComboBox sizeComboBox;

  ObservableList<String> listOfSizes = FXCollections.observableArrayList("XS", "S", "M", "L", "XL");

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    sizeComboBox.setItems(listOfSizes);
  }
}
