package Controllers;

import Controllers.model.*;
import Controllers.model.Node;
import com.jfoenix.controls.JFXTreeTableView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;

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
    initNodeTable();
  }

  private void initNodeTable() {
    nodeTable2 = nodeTable;
    nodeTable.setShowRoot(false);
    initNodeCols();
    loadNodeData();
  }

  private void loadNodeData() {
    nodeList = FXCollections.observableArrayList();
    DatabaseFunctionality.showNodes(nodeList);
    ObservableList<TreeItem<Node>> nodeTreeList;
    nodeTreeList = FXCollections.observableArrayList();

    for (Node node : nodeList) {
      nodeTreeList.add(new TreeItem<>(node));
    }
    TreeItem<Node> rootNode = new TreeItem<>(new Node());
    rootNode.getChildren().addAll(nodeTreeList);
    nodeTable.setRoot(rootNode);
  }

  private void initNodeCols() {

    nodeIDCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("ID"));
    nodeIDCol.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());

    xCoordCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("xCoord"));
    xCoordCol.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());

    yCoordCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("yCoord"));
    yCoordCol.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());

    floorCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("Floor"));
    floorCol.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());

    buildingCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("Building"));
    buildingCol.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());

    nodeTypeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("NodeType"));
    nodeTypeCol.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());

    longNameCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("LongName"));
    longNameCol.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());

    shortNameCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("ShortName"));
    shortNameCol.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());

    updateNodeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("Update"));

    editableNodeCols();
  }

  private void editableNodeCols() {
    nodeTable.setEditable(true);
    // Turn on multiple-selection mode for the TreeTableView
    TreeTableView.TreeTableViewSelectionModel<Node> selection = nodeTable.getSelectionModel();
    selection.setSelectionMode(SelectionMode.MULTIPLE);

    // Enable cell-level selection
    selection.setCellSelectionEnabled(true);
  }

  // Node functionality
  public void nodeTabSelect(Event event) {
    initNodeTable();
  }

  public void addNode(ActionEvent actionEvent) {}

  public void deleteNode(ActionEvent actionEvent) {}

  // Edge functionality
  public void edgeTabSelect(Event event) {}

  public void addEdge(ActionEvent actionEvent) {}

  public void deleteEdge(ActionEvent actionEvent) {}
}
