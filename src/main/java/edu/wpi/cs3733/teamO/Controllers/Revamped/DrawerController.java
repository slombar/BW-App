package edu.wpi.cs3733.teamO.Controllers.Revamped;

import static edu.wpi.cs3733.teamO.GraphSystem.Graph.GRAPH;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.teamO.Database.NodesAndEdges;
import edu.wpi.cs3733.teamO.HelperClasses.PopupMaker;
import edu.wpi.cs3733.teamO.Model.Node;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class DrawerController {
  public JFXTextField nodeID;
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
    directionsDisplayVbox.getChildren().clear();
    for (String d : directions) {

      Label newText = new Label(d);
      newText.setFont(Font.font("Leelawadee UI", 16.0));

      directionsDisplayVbox.getChildren().add(newText);
    }
  }

  public VBox getDirectionVBox() {
    return directionsDisplayVbox;
  }

  public void share(ActionEvent actionEvent) throws IOException {
    // NavController.share();
    // get email/number/display qr here

  }

  public void saveNode(ActionEvent actionEvent) {
    String nid = nodeID.getText();
    String x = xCoord.getText();
    String y = yCoord.getText();
    String f = navController.sFloor;
    String b = building.getText();
    String nt = nodeType.getText();
    String ln = longName.getText();
    String sn = shortName.getText();
    String t = "O";
    boolean v = visible.isSelected();

    // if any fields are empty, show appropriate warning
    if (isNodeInfoEmpty()) {
      PopupMaker.incompletePopup(navController.nodeWarningPane);
    }
    // else, add/edit Node (depending on addNodeDBMode = t/f)
    else {
      try {
        Node n = null;
        if (navController.addingNode) {
           n = new Node(nid, Integer.parseInt(x), Integer.parseInt(y), f, b, nt, ln, sn, t, v);
          NodesAndEdges.addNode(nid, x, y, f, b, nt, ln, sn, t, v);

        } else {
          n = new Node(nid, Integer.parseInt(x), Integer.parseInt(y), f, b, nt, ln, sn, t, v);
          NodesAndEdges.editNode(nid, Integer.parseInt(x), Integer.parseInt(y), f, b, nt, ln, sn, t, v);
          nid = navController.selectedNode.getID();
        }

        GRAPH.addNode(n, navController.addingNode);

        clearNodeInfo();
        navController.selectedNode = null; // when clear Node info, also de-select Node
        navController.addingNode = false;

      } catch (SQLException throwables) {
        PopupMaker.nodeAlreadyExists(navController.nodeWarningPane);
      }
    }

    navController.unpairCircle();
    navController.draw();
  }

  /**
   * checks if the any of the node fields are empty
   *
   * @return true if any node fields are empty
   */
  private boolean isNodeInfoEmpty() {
    // nodeID.getText().isEmpty()
    return xCoord.getText() == null
        || yCoord.getText() == null
        // || floor.getText().isEmpty()
        // || building.getText().isEmpty()
        || nodeType.getText() == null
        || longName.getText() == null
        || shortName.getText() == null;
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

  public void QRCodeGeneration(ActionEvent actionEvent) {}

  public void sendText(ActionEvent actionEvent) {}

  public void sendEmail(ActionEvent actionEvent) {}

  public void removeDirectionChildren() {
    directionsDisplayVbox.getChildren().clear();
  }
}
