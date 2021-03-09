package edu.wpi.cs3733.teamO.Controllers.Mobile;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.teamO.GraphSystem.Graph;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MobileDirectionsController implements Initializable {

  @FXML private VBox directionVBox;
  @FXML private JFXButton backButton;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    // TODO: popuplate (Label) textDirections with text directions
    if (MainScreenController.isBackGoogle) {

    } else {
      for (String d : Graph.findTextDirection()) {
        addTextToDirectionBox(d);
      }
    }
    backButton.setButtonType(JFXButton.ButtonType.RAISED);
  }

  /**
   * returns the previous map page based on booleans
   *
   * @param actionEvent
   */
  public void goBack(ActionEvent actionEvent) {
    SwitchScene.goToParentMobile("/Views/MobileApp/MobileHospitalNav.fxml", actionEvent);
  }

  private void addTextToDirectionBox(String text) {
    Text newText = new Text(text + "\n");
    newText.setFont(Font.font("leelawadee ui", 12.0));
    directionVBox.getChildren().add(newText);
  }
}
