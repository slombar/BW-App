package Controllers;

import Controllers.model.Node;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import Controllers.model.*;

import java.net.URL;
import java.util.ResourceBundle;

public class EditPageController implements Initializable {
  @FXML private Tab nodeTableTab;
  @FXML private Tab edgeTableTab;

  @FXML private JFXTreeTableView<Node> nodeTable;
  public static JFXTreeTableView<Node> nodeTable2;

  @FXML private TreeTableColumn<Node, String> nodeIDCol;
  @FXML private TreeTableColumn<Node, String> xCoordCol;
  @FXML private TreeTableColumn<Node, String> yCoordCol;
  @FXML private TreeTableColumn<Node, String> floorCol;
  @FXML private TreeTableColumn<Node, String> buildingCol;
  @FXML private TreeTableColumn<Node, String> nodeTypeCol;
  @FXML private TreeTableColumn<Node, String> longNameCol;
  @FXML private TreeTableColumn<Node, String> shortNameCol;
  @FXML private TreeTableColumn<Node, String> updateNodeCol;

  @FXML private JFXTreeTableView<Edge> edgeTable;
  public static JFXTreeTableView<Edge> edgeTable2;

  @FXML private TreeTableColumn<Edge, String> edgeIDCol;
  @FXML private TreeTableColumn<Edge, String> startCol;
  @FXML private TreeTableColumn<Edge, String> endCol;
  @FXML private TreeTableColumn<Edge, String> updateEdgeCol;



    public static ObservableList<Node> nodeList;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    nodeTable2 = nodeTable;
    initTable();
    loadData();
  }

  private void initTable() {
    initCols();
  }


    // Node functionality
    public void nodeTabSelect(Event event) {
    }

    public void addNode(ActionEvent actionEvent) {
    }

    public void deleteNode(ActionEvent actionEvent) {
    }


    // Edge functionality
    public void edgeTabSelect(Event event) {
    }

    public void addEdge(ActionEvent actionEvent) {
    }

    public void deleteEdge(ActionEvent actionEvent) {
    }


}
