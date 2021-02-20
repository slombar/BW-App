package Controllers.model;

import Controllers.DatabaseFunctionality;
import Controllers.EditNodesController;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;

public class Node extends RecursiveTreeObject<Node> {
  String ID, building, nodeType, longName, shortName, floor, xCoord, yCoord;
  Button update;

  public Node(
      String ID,
      String xCoord,
      String yCoord,
      String floor,
      String building,
      String nodeType,
      String longName,
      String shortName,
      Button update) {
    this.ID = ID;
    this.building = building;
    this.nodeType = nodeType;
    this.longName = longName;
    this.shortName = shortName;
    this.floor = floor;
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.update = update;

    update.setOnAction(
        e -> {
          ObservableList<Node> nodes =
              EditNodesController.nodeTable2.getSelectionModel().getSelectedItems();

          for (Node node : nodes) {
            if (node.getUpdate() == update) {
              System.out.println("ID: " + node.getID());
              System.out.println("xCoord: " + node.getXCoord());
              System.out.println("yCoord: " + node.getYCoord());
              System.out.println("Floor: " + node.getFloor());
              System.out.println("Building: " + node.getBuilding());
              System.out.println("NodeType: " + node.getNodeType());
              System.out.println("LongName: " + node.getLongName());
              System.out.println("ShortName: " + node.getShortName());

              DatabaseFunctionality.editNode(
                  node.getID(),
                  Integer.parseInt(node.getXCoord()),
                  Integer.parseInt(node.getYCoord()),
                  node.getFloor(),
                  node.getBuilding(),
                  node.getNodeType(),
                  node.getLongName(),
                  node.getShortName(),
                  "O");
            }
          }
        });
  }

  public Node() {
    this.ID = null;
    this.building = null;
    this.nodeType = null;
    this.longName = null;
    this.shortName = null;
    this.floor = null;
    this.xCoord = null;
    this.yCoord = null;
    this.update = null;
  }

  public String getID() {
    return ID;
  }

  public void setID(String ID) {
    this.ID = ID;
  }

  public String getBuilding() {
    return building;
  }

  public void setBuilding(String building) {
    this.building = building;
  }

  public String getNodeType() {
    return nodeType;
  }

  public void setNodeType(String nodeType) {
    this.nodeType = nodeType;
  }

  public String getLongName() {
    return longName;
  }

  public void setLongName(String longName) {
    this.longName = longName;
  }

  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  public String getFloor() {
    return floor;
  }

  public void setFloor(String floor) {
    this.floor = floor;
  }

  public String getXCoord() {
    return xCoord;
  }

  public void setXCoord(String xCoord) {
    this.xCoord = xCoord;
  }

  public String getYCoord() {
    return yCoord;
  }

  public void setYCoord(String yCoord) {
    this.yCoord = yCoord;
  }

  public Button getUpdate() {
    return update;
  }

  public void setUpdate(Button update) {
    this.update = update;
  }
}
