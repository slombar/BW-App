package Controllers.model;

import Controllers.DatabaseFunctionality;
import Controllers.EditPageController;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;

public class Edge extends RecursiveTreeObject<Edge> {
  String ID, start, end;
  Button update;

  public Edge(String ID, String start, String end, Button update) {
    this.ID = ID;
    this.start = start;
    this.end = end;
    this.update = update;

    update.setOnAction(
        e -> {
          ObservableList<TreeItem<Edge>> edges =
              EditPageController.edgeTable2.getSelectionModel().getSelectedItems();

          for (TreeItem<Edge> item : edges) {
            if (item.getValue().getUpdate() == update) {
              System.out.println("ID: " + item.getValue().getID());
              System.out.println("Start: " + item.getValue().getStart());
              System.out.println("End: " + item.getValue().getEnd());

              DatabaseFunctionality.editEdge(
                  item.getValue().getID(), item.getValue().getStart(), item.getValue().getEnd());
            }
          }
        });
  }

  public Edge() {
    this.ID = null;
    this.start = null;
    this.end = null;
    this.update = null;
  }

  public String getID() {
    return ID;
  }

  public void setID(String ID) {
    this.ID = ID;
  }

  public String getStart() {
    return start;
  }

  public void setStart(String start) {
    this.start = start;
  }

  public String getEnd() {
    return end;
  }

  public void setEnd(String end) {
    this.end = end;
  }

  public Button getUpdate() {
    return update;
  }

  public void setUpdate(Button update) {
    this.update = update;
  }
}
