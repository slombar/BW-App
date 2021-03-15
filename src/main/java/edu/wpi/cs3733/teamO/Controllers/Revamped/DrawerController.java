package edu.wpi.cs3733.teamO.Controllers.Revamped;

import static edu.wpi.cs3733.teamO.GraphSystem.Graph.GRAPH;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.teamO.HelperClasses.PopupMaker;
import edu.wpi.cs3733.teamO.Model.Node;
import java.sql.SQLException;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class DrawerController {
  private NavController navController;
  public JFXScrollPane directionsScrollPane;
  @FXML private VBox directionsDisplayVbox;
  public JFXTextField longName;
  public JFXCheckBox visible;
  public JFXTextField yCoord;
  public JFXTextField xCoord;
  public JFXTextField nodeType;
  public JFXTextField building;
  public JFXTextField shortName;

  public void addDirectionChildren(List<String> directions) {

    for (String d : directions) {

      Label newText = new Label(d);
      newText.setFont(Font.font("Leelawadee UI", 16.0));

      directionsDisplayVbox.getChildren().add(newText);

      // add directions label to vbox
      // informationOnPage.getDirectionVBox().getChildren().add(newText);
      // informationOnPage.addDirectionChild(newText);
      // DrawerController.addDirectionChild(newText);
    }
  }

  public void removeDirectionChildren() {
    directionsDisplayVbox.getChildren().clear();
  }

  public VBox getDirectionVBox() {
    return directionsDisplayVbox;
  }

  public void share(ActionEvent actionEvent) {}

  public void saveNode(ActionEvent actionEvent) {
    // if any fields are empty, show appropriate warning
    if (isNodeInfoEmpty()) {
      PopupMaker.incompletePopup(navController.nodeWarningPane);
    }
    // else, add/edit Node (depending on addNodeDBMode = t/f)
    else {
      try {
        // TODO: get / generate nodeID (?)
        Node n =
            new Node(
                "x", // nodeID.getText(),
                Integer.parseInt(xCoord.getText()),
                Integer.parseInt(yCoord.getText()),
                navController.sFloor,
                building.getText(),
                nodeType.getText(),
                longName.getText(),
                shortName.getText(),
                "O",
                visible.isSelected());

        GRAPH.addNode(n, false);
        clearNodeInfo();
        navController.selectedNode = null; // when clear Node info, also de-select Node

      } catch (SQLException throwables) {
        PopupMaker.nodeAlreadyExists(navController.nodeWarningPane);
      }
    }

    // addNodeDBMode = false;

    // selectingEditNode = true;
    navController.draw();
  }

  /**
   * checks if the any of the node fields are empty
   *
   * @return true if any node fields are empty
   */
  private boolean isNodeInfoEmpty() {
    // nodeID.getText().isEmpty()
    return xCoord.getText().isEmpty()
        || yCoord.getText().isEmpty()
        // || floor.getText().isEmpty()
        || building.getText().isEmpty()
        || nodeType.getText().isEmpty()
        || longName.getText().isEmpty()
        || shortName.getText().isEmpty();
  }

  /** clears all info in node textfields */
  private void clearNodeInfo() {
    // nodeID.clear();
    xCoord.clear();
    yCoord.clear();
    // floor.clear();
    building.clear();
    nodeType.clear();
    longName.clear();
    shortName.clear();
    visible.setSelected(false);
  }

  public void setNavController(NavController nc) {
    navController = nc;
  }
}
