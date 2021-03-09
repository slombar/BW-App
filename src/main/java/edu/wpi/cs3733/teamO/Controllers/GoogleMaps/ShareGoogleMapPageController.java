package edu.wpi.cs3733.teamO.Controllers.GoogleMaps;

import com.google.maps.model.DirectionsStep;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.teamO.Database.UserHandling;
import edu.wpi.cs3733.teamO.HelperClasses.RegexBoi;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

public class ShareGoogleMapPageController implements Initializable {

  public JFXTextField emailInput;
  public JFXTextField textInput;
  private String url;
  private ArrayList<DirectionsStep> directions;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.url = GoogleMapPageController.getDirectionsURL();
    this.directions = GoogleMapPageController.getDirections();
  }

  public void goToMainMenu(MouseEvent mouseEvent) {
    String MenuUrl = "/Views/MainPage.fxml";
    if (UserHandling.getEmployee() || UserHandling.getAdmin())
      MenuUrl = "/Views/StaffMainPage.fxml";
    SwitchScene.goToParent(MenuUrl);
  }

  public void goToLink(ActionEvent actionEvent) {
    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
      try {
        Desktop.getDesktop().browse(new URI(url));
      } catch (IOException | URISyntaxException e) {
        e.printStackTrace();
      }
    }
  }

  public void goBack(ActionEvent actionEvent) {
    SwitchScene.goToParent("/Views/GoogleMaps/GoogleMapPage.fxml");
  }

  public void mouseEnteredButton(MouseEvent mouseEvent) {
    JFXButton button = (JFXButton) mouseEvent.getSource();
    button.setButtonType(JFXButton.ButtonType.RAISED);
  }

  public void mouseExitedButton(MouseEvent mouseEvent) {
    JFXButton button = (JFXButton) mouseEvent.getSource();
    button.setButtonType(JFXButton.ButtonType.FLAT);
  }

  public void emailBoth(ActionEvent actionEvent) {
    String email = emailInput.getText();
    if (RegexBoi.checkEmail(email)) {
      GoogleEmailThreader get = new GoogleEmailThreader(email, directions, url);
      get.start();
    }
  }

  public void emailDir(ActionEvent actionEvent) {
    String email = emailInput.getText();
    if (RegexBoi.checkEmail(email)) {
      GoogleEmailThreader get = new GoogleEmailThreader(email, directions);
      get.start();
    }
  }

  public void emailLink(ActionEvent actionEvent) {
    String email = emailInput.getText();
    if (RegexBoi.checkEmail(email)) {
      GoogleEmailThreader get = new GoogleEmailThreader(email, url);
      get.start();
    }
  }

  public void textBoth(ActionEvent actionEvent) {
    String num = textInput.getText();
    System.out.println(RegexBoi.checkPhoneNum(num));
    if (RegexBoi.checkPhoneNum(num)) {
      GoogleTextThreader gtt = new GoogleTextThreader(num, directions, url);
      gtt.start();
    }
  }

  public void textDir(ActionEvent actionEvent) {
    String num = textInput.getText();
    if (RegexBoi.checkPhoneNum(num)) {
      GoogleTextThreader gtt = new GoogleTextThreader(num, directions);
      gtt.start();
    }
  }

  public void textLink(ActionEvent actionEvent) {
    String num = textInput.getText();
    if (RegexBoi.checkPhoneNum(num)) {
      GoogleTextThreader gtt = new GoogleTextThreader(num, url);
      gtt.start();
    }
  }
}
