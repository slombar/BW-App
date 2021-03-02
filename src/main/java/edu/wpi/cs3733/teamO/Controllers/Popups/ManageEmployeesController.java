package edu.wpi.cs3733.teamO.Controllers.Popups;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;

public class ManageEmployeesController implements Initializable {
  public StackPane popupPane;
  public JFXComboBox<String> currentEmployeesCombobox;
  public JFXButton rmEmployeeButton;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    ObservableList<String> employeeList = FXCollections.observableArrayList();
    // TODO nothing here yet waiting on sadie
    currentEmployeesCombobox.setItems(employeeList);
  }

  public void close(ActionEvent actionEvent) {
    SwitchScene.goToParent("/Views/StaffMainPage.fxml");
  }

  public void addEmployee(ActionEvent actionEvent) {
    SwitchScene.goToParent("/Views/CreateEmployeeAccount.fxml");
  }

  public void rmEmployee(ActionEvent actionEvent) {}
}
