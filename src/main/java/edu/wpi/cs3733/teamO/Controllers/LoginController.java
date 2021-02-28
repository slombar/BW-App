package edu.wpi.cs3733.teamO.Controllers;

import edu.wpi.cs3733.teamO.Database.RequestHandling;
import edu.wpi.cs3733.teamO.Opp;
import edu.wpi.cs3733.teamO.UserTypes.Staff;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class LoginController implements Initializable {

  @FXML private BorderPane borderPane;
  @FXML private StackPane loginPane;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    loginPane.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    loginPane.toBack();
  }

  /**
   * when the the circles or the images on the circle or the text below the circles are clicked, the
   * login is pop up is prompted
   *
   * @param mouseEvent
   */
  public void goToLogin(MouseEvent mouseEvent) {
    try {
      GridPane root = FXMLLoader.load(getClass().getResource("/Views/PatientSignInPopup.fxml"));
      Opp.getPrimaryStage().getScene().setRoot(root);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * when the log in as guest button is pressed, the apllication goes directly to the main page
   *
   * @param actionEvent
   */
  public void goToMain(ActionEvent actionEvent) {
    try {
      BorderPane root = FXMLLoader.load(getClass().getResource("/Views/MainPage.fxml"));
      Opp.getPrimaryStage().getScene().setRoot(root);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public void sadie(ActionEvent actionEvent) {
    String random = "dog";
    Staff staff = new Staff();
    Date date = new Date(2020, 10, 10);
    RequestHandling.addRequest(
        staff, staff, date, date, random, random, random, random, random, random);
  }

  public void tempBTN(ActionEvent actionEvent) {
    try {
      BorderPane root = FXMLLoader.load(getClass().getResource("/Views/StaffMainPage.fxml"));
      Opp.getPrimaryStage().getScene().setRoot(root);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
