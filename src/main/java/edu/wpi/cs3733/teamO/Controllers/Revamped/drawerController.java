package edu.wpi.cs3733.teamO.Controllers.Revamped;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class drawerController {
  @FXML private JFXCheckBox visible;
  @FXML private JFXTextField yCoord;
  @FXML private JFXTextField xCoord;
  @FXML private JFXTextField nodeType;
  @FXML private JFXTextField longName;
  @FXML private JFXScrollPane directionsScrollPane;

  public JFXScrollPane getDirectionsScrollPane() {
    return directionsScrollPane;
  }

  public JFXCheckBox getVisible() {
    return visible;
  }

  public JFXTextField getyCoord() {
    return yCoord;
  }

  public void setyCoordText(String input) {
    this.xCoord.setText(input);
  }

  public JFXTextField getxCoord() {
    return xCoord;
  }

  public void setxCoordText(String input) {
    this.xCoord.setText(input);
  }

  public JFXTextField getNodeType() {
    return nodeType;
  }

  public void setNodeTypeText(String input) {
    this.nodeType.setText(input);
  }

  public JFXTextField getLongName() {
    return longName;
  }

  public void setLongNameText(String input) {
    this.longName.setText(input);
  }

  public void share(ActionEvent actionEvent) {}

  public void saveNode(ActionEvent actionEvent) {}

  public void setDirectionsScrollPaneContent(VBox allDirections) {

    directionsScrollPane.setContent(allDirections);
  }
}
