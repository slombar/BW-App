package edu.wpi.cs3733.teamO.Controllers.Revamped;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class ProfilePageController implements Initializable {
  @FXML private JFXButton backBtn;
  @FXML private Label password;
  @FXML private JFXButton editBtn;
  @FXML private JFXTextField name;
  @FXML private JFXTextField email;
  @FXML private JFXTextField username;
  @FXML private Label parkingNumber;
  @FXML private JFXButton saveBtn;

  @Override
  public void initialize(URL location, ResourceBundle resources) {}

  public void goBack(ActionEvent actionEvent) {
    SwitchScene.goToParent("/RevampedViews/DesktopApp/MainStaffScreen.fxml");
  }

  public void edit(ActionEvent actionEvent) {}

  public void saveChanges(ActionEvent actionEvent) {}
}
