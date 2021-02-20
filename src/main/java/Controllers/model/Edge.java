package Controllers.model;

import Controllers.DatabaseFunctionality;
import Controllers.EditEdgesController;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;

public class Edge {
  String ID, start, end;
  Button update;

  public Edge(String ID, String start, String end, Button update) {
    this.ID = ID;
    this.start = start;
    this.end = end;
    this.update = update;

    update.setOnAction(
        e -> {
          ObservableList<Edge> edges =
              EditEdgesController.edgeTable2.getSelectionModel().getSelectedItems();

          for (Edge edge : edges) {
            if (edge.getUpdate() == update) {
              System.out.println("ID: " + edge.getID());
              System.out.println("Start: " + edge.getStart());
              System.out.println("End: " + edge.getEnd());

              DatabaseFunctionality.editEdge(edge.getID(), edge.getStart(), edge.getEnd());
            }
          }
        });
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
