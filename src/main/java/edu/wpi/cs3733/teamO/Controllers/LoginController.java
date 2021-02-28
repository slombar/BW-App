package edu.wpi.cs3733.teamO.Controllers;

import edu.wpi.cs3733.teamO.HelperClasses.PopupMaker;
import edu.wpi.cs3733.teamO.Opp;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
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
    loginPane.toFront();
    PopupMaker.incompletePopup(loginPane);
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
}
