package edu.wpi.cs3733.teamO.Controllers;

import edu.wpi.cs3733.teamO.Database.UserHandling;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import edu.wpi.cs3733.teamO.Opp;
import javafx.event.ActionEvent;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;

public class AboutController {

  public void goToHome(ActionEvent actionEvent) {
    if (UserHandling.getEmployee()) {
      SwitchScene.goToParent("/RevampedViews/DesktopApp/MainStaffScreen.fxml");
    } else {
      SwitchScene.goToParent("/RevampedViews/DesktopApp/MainPatientScreen.fxml");
    }
  }

  public void goToCreditsPage(MouseEvent mouseEvent) {
    SwitchScene.goToParent("/Views/CreditsPage.fxml");
  }

  public void handCursor(MouseEvent mouseEvent) {
    Opp.getPrimaryStage().getScene().setCursor(Cursor.HAND); // Change cursor to hand
  }

  public void normalCursor(MouseEvent mouseEvent) {
    Opp.getPrimaryStage().getScene().setCursor(Cursor.DEFAULT); // Change cursor to normal arrow
  }
}
