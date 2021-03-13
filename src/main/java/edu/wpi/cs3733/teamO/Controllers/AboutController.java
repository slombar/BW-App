package edu.wpi.cs3733.teamO.Controllers;

import edu.wpi.cs3733.teamO.Database.UserHandling;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class AboutController {


    public void goToHome(ActionEvent actionEvent) {
        if (UserHandling.getEmployee()) {
            SwitchScene.goToParent("/Views/StaffMainPage.fxml");
        } else {
            SwitchScene.goToParent("/Views/MainPage.fxml");
        }
    }
}
