package Controllers;

import Controllers.model.*;
import Controllers.model.Node;
import com.jfoenix.controls.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class EditPageController implements Initializable {
  @FXML private StackPane warningPane;
  @FXML private StackPane stackPane;
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
  public static ObservableList<Edge> edgeList;

  private boolean popUp = false;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    initNodeTable();
    initEdgeTable();
  }

  ///////////////////////////// NODES TABLE //////////////////////////////

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

  ///////////////////////////// EDGES TABLE //////////////////////////////

  private void initEdgeTable() {
    edgeTable2 = edgeTable;
    edgeTable.setShowRoot(false);
    initEdgeCols();
    loadEdgeData();
  }

  private void loadEdgeData() {
    edgeList = FXCollections.observableArrayList();
    DatabaseFunctionality.showEdges(edgeList);
    ObservableList<TreeItem<Edge>> edgeTreeList;
    edgeTreeList = FXCollections.observableArrayList();

    for (Edge edge : edgeList) {
      edgeTreeList.add(new TreeItem<>(edge));
    }
    TreeItem<Edge> rootEdge = new TreeItem<>(new Edge());
    rootEdge.getChildren().addAll(edgeTreeList);
    edgeTable.setRoot(rootEdge);
  }

  private void initEdgeCols() {
    edgeIDCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("ID"));
    edgeIDCol.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());

    startCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("Start"));
    startCol.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());

    endCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("End"));
    endCol.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());

    updateEdgeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("Update"));

    editableEdgeCols();
  }

  private void editableEdgeCols() {
    edgeTable.setEditable(true);
    // Turn on multiple-selection mode for the TreeTableView
    TreeTableView.TreeTableViewSelectionModel<Edge> selection = edgeTable.getSelectionModel();
    selection.setSelectionMode(SelectionMode.MULTIPLE);

    // Enable cell-level selection
    selection.setCellSelectionEnabled(true);
  }

  ////////////////////////////////////// FXML onActions/////////////////////////////////

  ///////////////////////// FXML onAction: Edge Functionality //////////////////////////
  public void nodeTabSelect(Event event) {
    initNodeTable();
  }

  public void addNode(ActionEvent actionEvent) {
    // checking to make sure there are currently no other popups
    if (!popUp) {
      popUp = true;

      // addNodePopup has the content of the popup
      // addNodeDialog creates the dialog popup

      JFXDialogLayout addNodePopup = new JFXDialogLayout();
      addNodePopup.setHeading(new Text("Add a new node"));
      VBox addNodeVBox = new VBox(12);

      HBox buttonBox = new HBox(20);
      JFXButton closeButton = new JFXButton("Close");
      JFXButton clearButton = new JFXButton("Clear");
      JFXButton submitButton = new JFXButton("Submit");
      buttonBox.getChildren().addAll(closeButton, clearButton, submitButton);

      ArrayList<String> addNodeLabels =
          new ArrayList<String>(
              Arrays.asList(
                  "Node ID",
                  "X Coordinate",
                  "Y Coordinate",
                  "Floor",
                  "Building",
                  "Node Type",
                  "Long Name",
                  "Short Name"));

      ArrayList<JFXTextField> listOfFields = createFields(addNodeLabels);

      addNodeVBox
          .getChildren()
          .addAll(
              listOfFields.get(0),
              listOfFields.get(1),
              listOfFields.get(2),
              listOfFields.get(3),
              listOfFields.get(4),
              listOfFields.get(5),
              listOfFields.get(6),
              listOfFields.get(7),
              buttonBox);
      addNodePopup.setBody(addNodeVBox);

      stackPane.toFront();
      JFXDialog addNodeDialog =
          new JFXDialog(stackPane, addNodePopup, JFXDialog.DialogTransition.BOTTOM);
      addNodeDialog.setOverlayClose(false);
      nodeTableTab.setDisable(true);
      edgeTableTab.setDisable(true);

      closeButton.setOnAction(
          event -> {
            addNodeDialog.close();
            stackPane.toBack();
            popUp = false;
            nodeTableTab.setDisable(false);
            edgeTableTab.setDisable(false);
          });
      clearButton.setOnAction(
          event -> {
            listOfFields.get(0).clear();
            listOfFields.get(1).clear();
            listOfFields.get(2).clear();
            listOfFields.get(3).clear();
            listOfFields.get(4).clear();
            listOfFields.get(5).clear();
            listOfFields.get(6).clear();
            listOfFields.get(7).clear();
          });
      submitButton.setOnAction(
          event -> {
            if (listOfFields.get(0).getText().isEmpty()
                || listOfFields.get(1).getText().isEmpty()
                || listOfFields.get(2).getText().isEmpty()
                || listOfFields.get(3).getText().isEmpty()
                || listOfFields.get(4).getText().isEmpty()
                || listOfFields.get(5).getText().isEmpty()
                || listOfFields.get(6).getText().isEmpty()
                || listOfFields.get(7).getText().isEmpty()) {
              incompletePopup();
            } else {
              //                      DatabaseFunctionality.addNode(listOfFields.get(0).getText(),
              //                              listOfFields.get(1).getText(),
              //                              listOfFields.get(2).getText(),
              //                              listOfFields.get(3).getText(),
              //                              listOfFields.get(4).getText(),
              //                              listOfFields.get(5).getText(),
              //                              listOfFields.get(6).getText(),
              //                              listOfFields.get(7).getText(),
              //                              "O");

              System.out.println(listOfFields.get(0).getText());
              System.out.println(listOfFields.get(1).getText());
              System.out.println(listOfFields.get(2).getText());
              System.out.println(listOfFields.get(3).getText());
              System.out.println(listOfFields.get(4).getText());
              System.out.println(listOfFields.get(5).getText());
              System.out.println(listOfFields.get(6).getText());
              System.out.println(listOfFields.get(7).getText());
              addNodeDialog.close();
              stackPane.toBack();
              popUp = false;
              nodeTableTab.setDisable(false);
              edgeTableTab.setDisable(false);
            }
          });
      addNodeDialog.show();
    }
  }

  public void deleteNode(ActionEvent actionEvent) {
    // checking to make sure there are currently no other popups
    if (!popUp) {
      popUp = true;

      // deleteNodePopup has the content of the popup
      // deleteNodeDialog creates the dialog popup

      JFXDialogLayout deleteNodePopup = new JFXDialogLayout();
      deleteNodePopup.setHeading(new Text("Delete a Node"));
      VBox deleteNodeVBox = new VBox(12);

      HBox buttonBox = new HBox(20);
      JFXButton closeButton = new JFXButton("Close");
      JFXButton clearButton = new JFXButton("Clear");
      JFXButton submitButton = new JFXButton("Submit");
      buttonBox.getChildren().addAll(closeButton, clearButton, submitButton);

      ArrayList<String> deleteNodeLabels = new ArrayList<String>(Arrays.asList("Node ID"));

      ArrayList<JFXTextField> listOfFields = createFields(deleteNodeLabels);

      deleteNodeVBox.getChildren().addAll(listOfFields.get(0), buttonBox);
      deleteNodePopup.setBody(deleteNodeVBox);

      ArrayList<String> nodeIDColData = new ArrayList<>();
      for (int i = 0; i < nodeTable.getExpandedItemCount(); i++) {
        nodeIDColData.add(nodeIDCol.getCellData(i));
      }
      autoComplete(nodeIDColData, listOfFields.get(0));

      stackPane.toFront();
      JFXDialog deleteNodeDialog =
          new JFXDialog(stackPane, deleteNodePopup, JFXDialog.DialogTransition.BOTTOM);
      deleteNodeDialog.setOverlayClose(false);
      nodeTableTab.setDisable(true);
      edgeTableTab.setDisable(true);

      closeButton.setOnAction(
          event -> {
            deleteNodeDialog.close();
            stackPane.toBack();
            popUp = false;
            nodeTableTab.setDisable(false);
            edgeTableTab.setDisable(false);
          });
      clearButton.setOnAction(
          event -> {
            listOfFields.get(0).clear();
          });
      submitButton.setOnAction(
          event -> {
            if (listOfFields.get(0).getText().isEmpty()) {
              incompletePopup();
            } else if (!nodeIDColData.contains(listOfFields.get(0).getText())) {
              nonexistantPopup();
            } else {
              //               DatabaseFunctionality.deleteNode(listOfFields.get(0).getText());

              System.out.println(listOfFields.get(0).getText());
              deleteNodeDialog.close();
              stackPane.toBack();
              popUp = false;
              nodeTableTab.setDisable(false);
              edgeTableTab.setDisable(false);
            }
          });
      deleteNodeDialog.show();
    }
  }

  ///////////////////////// FXML onAction: Edge Functionality //////////////////////////
  public void edgeTabSelect(Event event) {
    initEdgeTable();
  }

  public void addEdge(ActionEvent actionEvent) {
    // checking to make sure there are currently no other popups
    if (!popUp) {
      popUp = true;

      // addNodePopup has the content of the popup
      // addNodeDialog creates the dialog popup

      JFXDialogLayout addEdgePopup = new JFXDialogLayout();
      addEdgePopup.setHeading(new Text("Add a new edge"));
      VBox addEdgeVBox = new VBox(12);

      HBox buttonBox = new HBox(20);
      JFXButton closeButton = new JFXButton("Close");
      JFXButton clearButton = new JFXButton("Clear");
      JFXButton submitButton = new JFXButton("Submit");
      buttonBox.getChildren().addAll(closeButton, clearButton, submitButton);

      ArrayList<String> addEdgeLabels =
          new ArrayList<String>(Arrays.asList("Edge ID", "Start Node ID", "End Node ID"));

      ArrayList<JFXTextField> listOfFields = createFields(addEdgeLabels);

      addEdgeVBox
          .getChildren()
          .addAll(listOfFields.get(0), listOfFields.get(1), listOfFields.get(2), buttonBox);
      addEdgePopup.setBody(addEdgeVBox);

      stackPane.toFront();
      JFXDialog addEdgeDialog =
          new JFXDialog(stackPane, addEdgePopup, JFXDialog.DialogTransition.BOTTOM);
      addEdgeDialog.setOverlayClose(false);
      nodeTableTab.setDisable(true);
      edgeTableTab.setDisable(true);

      closeButton.setOnAction(
          event -> {
            addEdgeDialog.close();
            stackPane.toBack();
            popUp = false;
            nodeTableTab.setDisable(false);
            edgeTableTab.setDisable(false);
          });
      clearButton.setOnAction(
          event -> {
            listOfFields.get(0).clear();
            listOfFields.get(1).clear();
            listOfFields.get(2).clear();
          });
      submitButton.setOnAction(
          event -> {
            if (listOfFields.get(0).getText().isEmpty()
                || listOfFields.get(1).getText().isEmpty()
                || listOfFields.get(2).getText().isEmpty()) {
              incompletePopup();
            } else {
              //                      DatabaseFunctionality.addEdge(listOfFields.get(0).getText(),
              //                              listOfFields.get(1).getText(),
              //                              listOfFields.get(2).getText());

              System.out.println(listOfFields.get(0).getText());
              System.out.println(listOfFields.get(1).getText());
              System.out.println(listOfFields.get(2).getText());
              addEdgeDialog.close();
              stackPane.toBack();
              popUp = false;
              nodeTableTab.setDisable(false);
              edgeTableTab.setDisable(false);
            }
          });
      addEdgeDialog.show();
    }
  }

  public void deleteEdge(ActionEvent actionEvent) {
    // checking to make sure there are currently no other popups
    if (!popUp) {
      popUp = true;

      // deleteEdgePopup has the content of the popup
      // deleteEdgeDialog creates the dialog popup

      JFXDialogLayout deleteEdgePopup = new JFXDialogLayout();
      deleteEdgePopup.setHeading(new Text("Delete an Edge"));
      VBox deleteEdgeVBox = new VBox(12);

      HBox buttonBox = new HBox(20);
      JFXButton closeButton = new JFXButton("Close");
      JFXButton clearButton = new JFXButton("Clear");
      JFXButton submitButton = new JFXButton("Submit");
      buttonBox.getChildren().addAll(closeButton, clearButton, submitButton);

      ArrayList<String> deleteEdgeLabels = new ArrayList<String>(Arrays.asList("Edge ID"));

      ArrayList<JFXTextField> listOfFields = createFields(deleteEdgeLabels);

      deleteEdgeVBox.getChildren().addAll(listOfFields.get(0), buttonBox);
      deleteEdgePopup.setBody(deleteEdgeVBox);

      ArrayList<String> edgeIDColData = new ArrayList<>();
      for (int i = 0; i < edgeTable.getExpandedItemCount(); i++) {
        edgeIDColData.add(edgeIDCol.getCellData(i));
      }
      autoComplete(edgeIDColData, listOfFields.get(0));

      stackPane.toFront();
      JFXDialog deleteEdgeDialog =
          new JFXDialog(stackPane, deleteEdgePopup, JFXDialog.DialogTransition.BOTTOM);
      deleteEdgeDialog.setOverlayClose(false);
      nodeTableTab.setDisable(true);
      edgeTableTab.setDisable(true);

      closeButton.setOnAction(
          event -> {
            deleteEdgeDialog.close();
            stackPane.toBack();
            popUp = false;
            nodeTableTab.setDisable(false);
            edgeTableTab.setDisable(false);
          });
      clearButton.setOnAction(
          event -> {
            listOfFields.get(0).clear();
          });
      submitButton.setOnAction(
          event -> {
            if (listOfFields.get(0).getText().isEmpty()) {
              incompletePopup();
            } else if (!edgeIDColData.contains(listOfFields.get(0).getText())) {
              nonexistantPopup();
            } else {
              //               DatabaseFunctionality.deleteEdge(listOfFields.get(0).getText());

              System.out.println(listOfFields.get(0).getText());
              deleteEdgeDialog.close();
              stackPane.toBack();
              popUp = false;
              nodeTableTab.setDisable(false);
              edgeTableTab.setDisable(false);
            }
          });
      deleteEdgeDialog.show();
    }
  }

  ////////////////////////////////////// Additional Helper Functions
  // ////////////////////////////////////
  private ArrayList<JFXTextField> createFields(ArrayList<String> labels) {
    ArrayList<JFXTextField> listOfFields = new ArrayList<JFXTextField>();
    for (String label : labels) {
      JFXTextField text = new JFXTextField();
      text.setPromptText(label);
      listOfFields.add(text);
    }
    return listOfFields;
  }

  private void incompletePopup() {
    warningPane.toFront();
    JFXDialogLayout warning = new JFXDialogLayout();
    warning.setHeading(new Text("WARNING!"));
    warning.setBody(new Text("Text fields cannot be left blank."));
    JFXButton closeButton = new JFXButton("Close");
    warning.setActions(closeButton);

    JFXDialog warningDialog =
        new JFXDialog(warningPane, warning, JFXDialog.DialogTransition.BOTTOM);
    warningDialog.setOverlayClose(false);
    stackPane.setDisable(true);

    closeButton.setOnAction(
        event -> {
          warningDialog.close();
          warningPane.toBack();
          stackPane.setDisable(false);
        });
    warningDialog.show();
  }

  private void nonexistantPopup() {
    warningPane.toFront();
    JFXDialogLayout warning = new JFXDialogLayout();
    warning.setHeading(new Text("WARNING!"));
    warning.setBody(new Text("The given ID does not exist in the database."));
    JFXButton closeButton = new JFXButton("Close");
    warning.setActions(closeButton);

    JFXDialog warningDialog =
        new JFXDialog(warningPane, warning, JFXDialog.DialogTransition.BOTTOM);
    warningDialog.setOverlayClose(false);
    stackPane.setDisable(true);

    closeButton.setOnAction(
        event -> {
          warningDialog.close();
          warningPane.toBack();
          stackPane.setDisable(false);
        });
    warningDialog.show();
  }

  private void autoComplete(ArrayList<String> list, JFXTextField textfield) {
    JFXAutoCompletePopup<String> autoComplete = new JFXAutoCompletePopup<>();
    autoComplete.getSuggestions().addAll(list);

    autoComplete.setSelectionHandler(
        event -> {
          textfield.setText(event.getObject());
        });

    // filtering
    textfield
        .textProperty()
        .addListener(
            observable -> {
              autoComplete.filter(
                  string -> string.toLowerCase().contains(textfield.getText().toLowerCase()));
              if (autoComplete.getFilteredSuggestions().isEmpty()
                  || textfield.getText().isEmpty()) {
                autoComplete.hide();
              } else {
                autoComplete.show(textfield);
              }
            });
  }
}
