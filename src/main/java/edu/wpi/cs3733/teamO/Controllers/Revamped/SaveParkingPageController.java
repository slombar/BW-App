package edu.wpi.cs3733.teamO.Controllers.Revamped;

import static edu.wpi.cs3733.teamO.GraphSystem.Graph.GRAPH;

import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.teamO.Controllers.RevampedMobile.MainMobileScreenController;
import edu.wpi.cs3733.teamO.Database.UserHandling;
import edu.wpi.cs3733.teamO.HelperClasses.Autocomplete;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import edu.wpi.cs3733.teamO.Model.Node;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class SaveParkingPageController implements Initializable {
  @FXML private JFXTextField input;
  @FXML private Label currentSpot;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    currentSpot.setText(UserHandling.getParkingSpot());

    // setting up auto complete for parking nodes
    ArrayList<String> parkingNodes = new ArrayList<>();
    for (Node node : GRAPH.getListOfNodes()) {
      if (node.getNodeType().equals("PARK")) { // checking if its a parking node
        parkingNodes.add(node.getLongName()); // adds the node's long name to list
      }
    }
    // actually autocompleting with the parking nodes (displays the long name)
    Autocomplete.autoComplete(parkingNodes, input);
  }

  /**
   * returns the previous map page based on booleans
   *
   * @param actionEvent
   */
  public void goBack(ActionEvent actionEvent) {
    if (MainMobileScreenController.isBackHome) {
      SwitchScene.goToParentMobile("/RevampedViews/MobileApp/MainMobileScreen.fxml", actionEvent);
      MainMobileScreenController.isBackHome = false;
    } else {
      SwitchScene.goToParentMobile("/Views/MobileApp/MobileHospitalNav.fxml", actionEvent);
      MainMobileScreenController.isBackHome = false;
    }
  }

  public void saveSpot(ActionEvent actionEvent) {
    String spot = input.getText().substring(13);
    UserHandling.setParkingSpot(spot);
    System.out.println(spot);
    currentSpot.setText(spot);
  }
}
