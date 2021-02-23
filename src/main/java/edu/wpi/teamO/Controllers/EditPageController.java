package edu.wpi.teamO.Controllers;

import com.jfoenix.controls.*;
import edu.wpi.teamO.Controllers.model.Edge;
import edu.wpi.teamO.Controllers.model.Node;
import edu.wpi.teamO.Opp;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class EditPageController implements Initializable {
  @FXML private JFXButton backButton;
  @FXML private JFXButton addNodeButton;
  @FXML private JFXButton deleteNodeButton;
  @FXML private JFXButton addEdgeButton;
  @FXML private JFXButton deleteEdgeButton;

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
    customizeButtons();
  }

  // Customize
  private void customizeButtons() {
    // can also use lightgray
    backButton.setStyle("-fx-background-color: #c3d6e8");
    addNodeButton.setStyle("-fx-background-color: #c3d6e8");
    deleteNodeButton.setStyle("-fx-background-color: #c3d6e8");
    addEdgeButton.setStyle("-fx-background-color: #c3d6e8");
    deleteEdgeButton.setStyle("-fx-background-color: #c3d6e8");
  }

  ///////////////////////////// NODES TABLE //////////////////////////////
  // Initializes the node table and ensures that its updated
  private void initNodeTable() {
    nodeTable2 = nodeTable;
    nodeTable.setShowRoot(false);
    initNodeCols();
    loadNodeData();
  }

  // Loads the node data from the database and puts it into tree table
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

  // Initializes all the columns of the table
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

  // Makes the columns of the node table editable
  private void editableNodeCols() {
    nodeTable.setEditable(true);
    // Turn on multiple-selection mode for the TreeTableView
    TreeTableView.TreeTableViewSelectionModel<Node> selection = nodeTable.getSelectionModel();
    selection.setSelectionMode(SelectionMode.MULTIPLE);

    // Enable cell-level selection
    selection.setCellSelectionEnabled(true);
  }

  ///////////////////////////// EDGES TABLE //////////////////////////////
  // Initializes the edge table and ensures that its updated
  private void initEdgeTable() {
    edgeTable2 = edgeTable;
    edgeTable.setShowRoot(false);
    initEdgeCols();
    loadEdgeData();
  }

  // Loads the node data from the database and puts it into tree table
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

  // Initializes all the columns of the table
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

  // Makes the columns of the node table editable
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
  // Selecting the node tab initializes the node table
  public void nodeTabSelect(Event event) {
    initNodeTable();
  }

  // Adding the an edge to the database
  public void addNode(ActionEvent actionEvent) {
    // checking to make sure there are currently no other popups
    if (!popUp) {
      popUp = true;

      // addNodePopup has the content of the popup
      // addNodeDialog creates the dialog popup
      JFXDialogLayout addNodePopup = new JFXDialogLayout();
      addNodePopup.setHeading(new Text("Add a new node"));
      VBox addNodeVBox = new VBox(12);

      // Creating an HBox of buttons
      HBox buttonBox = new HBox(20);
      JFXButton closeButton = new JFXButton("Close");
      JFXButton clearButton = new JFXButton("Clear");
      JFXButton submitButton = new JFXButton("Submit");
      buttonBox.getChildren().addAll(closeButton, clearButton, submitButton);

      // Creating a list of labels to create the textfields
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

      // Creating the form with a VBox
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

      // Bringing the popup screen to the front and disabling the background
      stackPane.toFront();
      JFXDialog addNodeDialog =
          new JFXDialog(stackPane, addNodePopup, JFXDialog.DialogTransition.BOTTOM);
      addNodeDialog.setOverlayClose(false);
      nodeTableTab.setDisable(true);
      edgeTableTab.setDisable(true);

      // Closing the popup
      closeButton.setOnAction(
          event -> {
            addNodeDialog.close();
            stackPane.toBack();
            popUp = false;
            nodeTableTab.setDisable(false);
            edgeTableTab.setDisable(false);
          });
      // Clearing the form, keeps the popup open
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
      // Submits addition to the database
      submitButton.setOnAction(
          event -> {
            // If incomplete form, sends an error msg
            // Otherwise, sends to database and closes popup
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
              DatabaseFunctionality.addNode(
                  listOfFields.get(0).getText(),
                  listOfFields.get(1).getText(),
                  listOfFields.get(2).getText(),
                  listOfFields.get(3).getText(),
                  listOfFields.get(4).getText(),
                  listOfFields.get(5).getText(),
                  listOfFields.get(6).getText(),
                  listOfFields.get(7).getText(),
                  "O");

              //              System.out.println(listOfFields.get(0).getText());
              //              System.out.println(listOfFields.get(1).getText());
              //              System.out.println(listOfFields.get(2).getText());
              //              System.out.println(listOfFields.get(3).getText());
              //              System.out.println(listOfFields.get(4).getText());
              //              System.out.println(listOfFields.get(5).getText());
              //              System.out.println(listOfFields.get(6).getText());
              //              System.out.println(listOfFields.get(7).getText());
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

      // Creating an HBox of buttons
      HBox buttonBox = new HBox(20);
      JFXButton closeButton = new JFXButton("Close");
      JFXButton clearButton = new JFXButton("Clear");
      JFXButton submitButton = new JFXButton("Submit");
      buttonBox.getChildren().addAll(closeButton, clearButton, submitButton);

      // Creating a list of labels to create the textfields
      ArrayList<String> deleteNodeLabels = new ArrayList<String>(Arrays.asList("Node ID"));
      ArrayList<JFXTextField> listOfFields = createFields(deleteNodeLabels);

      // Creating the form with a VBox
      deleteNodeVBox.getChildren().addAll(listOfFields.get(0), buttonBox);
      deleteNodePopup.setBody(deleteNodeVBox);

      // Getting column data to make textfield autocomplete
      ArrayList<String> nodeIDColData = new ArrayList<>();
      for (int i = 0; i < nodeTable.getExpandedItemCount(); i++) {
        nodeIDColData.add(nodeIDCol.getCellData(i));
      }
      autoComplete(nodeIDColData, listOfFields.get(0));

      // Bringing the popup screen to the front and disabling the background
      stackPane.toFront();
      JFXDialog deleteNodeDialog =
          new JFXDialog(stackPane, deleteNodePopup, JFXDialog.DialogTransition.BOTTOM);
      deleteNodeDialog.setOverlayClose(false);
      nodeTableTab.setDisable(true);
      edgeTableTab.setDisable(true);

      // Closing the popup
      closeButton.setOnAction(
          event -> {
            deleteNodeDialog.close();
            stackPane.toBack();
            popUp = false;
            nodeTableTab.setDisable(false);
            edgeTableTab.setDisable(false);
          });
      // Clearing the form, keeps the popup open
      clearButton.setOnAction(
          event -> {
            listOfFields.get(0).clear();
          });
      // Submits deletion from the database
      submitButton.setOnAction(
          event -> {
            // If incomplete form, sends an error msg
            // If edge is not in database, send sn error msg
            // Otherwise, sends to database and closes popup
            if (listOfFields.get(0).getText().isEmpty()) {
              incompletePopup();
            } else if (!nodeIDColData.contains(listOfFields.get(0).getText())) {
              nonexistantPopup();
            } else {
              DatabaseFunctionality.deleteNode(listOfFields.get(0).getText());

              //              System.out.println(listOfFields.get(0).getText());
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
  // Selecting the edge tab initializes the edge table
  public void edgeTabSelect(Event event) {
    initEdgeTable();
  }

  // Adding the an edge to the database
  public void addEdge(ActionEvent actionEvent) {
    // Checking to make sure there are currently no other popups
    if (!popUp) {
      popUp = true;

      // addEdgePopup has the content of the popup
      // addEdgeDialog creates the dialog popup
      JFXDialogLayout addEdgePopup = new JFXDialogLayout();
      addEdgePopup.setHeading(new Text("Add a new edge"));
      VBox addEdgeVBox = new VBox(12);

      // Creating an HBox of buttons
      HBox buttonBox = new HBox(20);
      JFXButton closeButton = new JFXButton("Close");
      JFXButton clearButton = new JFXButton("Clear");
      JFXButton submitButton = new JFXButton("Submit");
      buttonBox.getChildren().addAll(closeButton, clearButton, submitButton);

      // Creating a list of labels to create the textfields
      ArrayList<String> addEdgeLabels =
          new ArrayList<String>(Arrays.asList("Edge ID", "Start Node ID", "End Node ID"));
      ArrayList<JFXTextField> listOfFields = createFields(addEdgeLabels);

      // Creating the form with a VBox
      addEdgeVBox
          .getChildren()
          .addAll(listOfFields.get(0), listOfFields.get(1), listOfFields.get(2), buttonBox);
      addEdgePopup.setBody(addEdgeVBox);

      // Bringing the popup screen to the front and disabling the background
      stackPane.toFront();
      JFXDialog addEdgeDialog =
          new JFXDialog(stackPane, addEdgePopup, JFXDialog.DialogTransition.BOTTOM);
      addEdgeDialog.setOverlayClose(false);
      nodeTableTab.setDisable(true);
      edgeTableTab.setDisable(true);

      // Closing the popup
      closeButton.setOnAction(
          event -> {
            addEdgeDialog.close();
            stackPane.toBack();
            popUp = false;
            nodeTableTab.setDisable(false);
            edgeTableTab.setDisable(false);
          });
      // Clearing the form, keeps the popup open
      clearButton.setOnAction(
          event -> {
            listOfFields.get(0).clear();
            listOfFields.get(1).clear();
            listOfFields.get(2).clear();
          });
      // Submits addition to the database
      submitButton.setOnAction(
          event -> {
            // If incomplete form, sends an error msg
            // Otherwise, sends to database and closes popup
            if (listOfFields.get(0).getText().isEmpty()
                || listOfFields.get(1).getText().isEmpty()
                || listOfFields.get(2).getText().isEmpty()) {
              incompletePopup();
            } else {
              DatabaseFunctionality.addEdge(
                  listOfFields.get(0).getText(),
                  listOfFields.get(1).getText(),
                  listOfFields.get(2).getText());

              //              System.out.println(listOfFields.get(0).getText());
              //              System.out.println(listOfFields.get(1).getText());
              //              System.out.println(listOfFields.get(2).getText());
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

  // Deleting the Edge from the database
  public void deleteEdge(ActionEvent actionEvent) {
    // checking to make sure there are currently no other popups
    if (!popUp) {
      popUp = true;

      // deleteEdgePopup has the content of the popup
      // deleteEdgeDialog creates the dialog popup
      JFXDialogLayout deleteEdgePopup = new JFXDialogLayout();
      deleteEdgePopup.setHeading(new Text("Delete an Edge"));
      VBox deleteEdgeVBox = new VBox(12);

      // Creating an HBox of buttons
      HBox buttonBox = new HBox(20);
      JFXButton closeButton = new JFXButton("Close");
      JFXButton clearButton = new JFXButton("Clear");
      JFXButton submitButton = new JFXButton("Submit");
      buttonBox.getChildren().addAll(closeButton, clearButton, submitButton);

      // Creating a list of labels to create teh textfields
      ArrayList<String> deleteEdgeLabels = new ArrayList<String>(Arrays.asList("Edge ID"));
      ArrayList<JFXTextField> listOfFields = createFields(deleteEdgeLabels);

      // Creating the form with a VBox
      deleteEdgeVBox.getChildren().addAll(listOfFields.get(0), buttonBox);
      deleteEdgePopup.setBody(deleteEdgeVBox);

      // Getting column data to make textfield autocomplete
      ArrayList<String> edgeIDColData = new ArrayList<>();
      for (int i = 0; i < edgeTable.getExpandedItemCount(); i++) {
        edgeIDColData.add(edgeIDCol.getCellData(i));
      }
      autoComplete(edgeIDColData, listOfFields.get(0));

      // Bringing the popup screen to the front and disabling the background
      stackPane.toFront();
      JFXDialog deleteEdgeDialog =
          new JFXDialog(stackPane, deleteEdgePopup, JFXDialog.DialogTransition.BOTTOM);
      deleteEdgeDialog.setOverlayClose(false);
      nodeTableTab.setDisable(true);
      edgeTableTab.setDisable(true);

      // Closing the popup
      closeButton.setOnAction(
          event -> {
            deleteEdgeDialog.close();
            stackPane.toBack();
            popUp = false;
            nodeTableTab.setDisable(false);
            edgeTableTab.setDisable(false);
          });
      // Clearing the form, keeps the popup open
      clearButton.setOnAction(
          event -> {
            listOfFields.get(0).clear();
          });
      // Submits deletion to the database
      submitButton.setOnAction(
          event -> {
            // If incomplete form, sends an error msg
            // If edge is not in database, send sn error msg
            // Otherwise, sends to database and closes popup
            if (listOfFields.get(0).getText().isEmpty()) {
              incompletePopup();
            } else if (!edgeIDColData.contains(listOfFields.get(0).getText())) {
              nonexistantPopup();
            } else {
              DatabaseFunctionality.deleteEdge(listOfFields.get(0).getText());

              //              System.out.println(listOfFields.get(0).getText());
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

  /////////////////////////// Additional Helper Functions ///////////////////////////////////
  // Creates the textfieds for the popup forms, returns a list of textfields
  private ArrayList<JFXTextField> createFields(ArrayList<String> labels) {
    ArrayList<JFXTextField> listOfFields = new ArrayList<JFXTextField>();
    for (String label : labels) {
      JFXTextField text = new JFXTextField();
      text.setPromptText(label);
      listOfFields.add(text);
    }
    return listOfFields;
  }

  // Creates a warning popup for an incomplete form
  private void incompletePopup() {
    warningPane.toFront();

    // Creates the content for the popup
    JFXDialogLayout warning = new JFXDialogLayout();
    warning.setHeading(new Text("WARNING!"));
    warning.setBody(new Text("Text fields cannot be left blank."));
    JFXButton closeButton = new JFXButton("Close");
    warning.setActions(closeButton);

    // Creates the actual popup
    JFXDialog warningDialog =
        new JFXDialog(warningPane, warning, JFXDialog.DialogTransition.BOTTOM);
    warningDialog.setOverlayClose(false);
    stackPane.setDisable(true);

    // Closes the popup
    closeButton.setOnAction(
        event -> {
          warningDialog.close();
          warningPane.toBack();
          stackPane.setDisable(false);
        });
    warningDialog.show();
  }

  // Creates a warning popup for nonexistant nodes/edges
  private void nonexistantPopup() {
    warningPane.toFront();

    // Creates the content for the popup
    JFXDialogLayout warning = new JFXDialogLayout();
    warning.setHeading(new Text("WARNING!"));
    warning.setBody(new Text("The given ID does not exist in the database."));
    JFXButton closeButton = new JFXButton("Close");
    warning.setActions(closeButton);

    // Creates the actual popup
    JFXDialog warningDialog =
        new JFXDialog(warningPane, warning, JFXDialog.DialogTransition.BOTTOM);
    warningDialog.setOverlayClose(false);
    stackPane.setDisable(true);

    // Closes the popup
    closeButton.setOnAction(
        event -> {
          warningDialog.close();
          warningPane.toBack();
          stackPane.setDisable(false);
        });
    warningDialog.show();
  }

  // Autocompletes textfields based on an ArrayList<String>
  private void autoComplete(ArrayList<String> list, JFXTextField textfield) {
    JFXAutoCompletePopup<String> autoComplete = new JFXAutoCompletePopup<>();
    autoComplete.getSuggestions().addAll(list);

    autoComplete.setSelectionHandler(
        event -> {
          textfield.setText(event.getObject());
        });

    // Filtering the list given
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

  /**
   * Send user back to index.fxml
   *
   * @param actionEvent
   * @throws IOException
   */
  public void goToIndex(ActionEvent actionEvent) throws IOException {
    AnchorPane root = FXMLLoader.load(getClass().getResource("/Views/Index.fxml"));
    Opp.getPrimaryStage().getScene().setRoot(root);
  }

  public void loadCSV(ActionEvent actionEvent) {
    // dialogContent has the conetnt of the popup
    JFXDialogLayout dialogContent = new JFXDialogLayout();
    dialogContent.setHeading(new Text("Load CSV text file."));
    VBox dialogVBox = new VBox(12);

    // Creating an HBox of buttons
    HBox buttonBox = new HBox(20);
    JFXButton closeButton = new JFXButton("Close");
    JFXButton clearButton = new JFXButton("Clear");
    JFXButton submitButton = new JFXButton("Load");
    buttonBox.getChildren().addAll(closeButton, clearButton, submitButton);

    // Creating a list of labels to create teh textfields
    ArrayList<String> label = new ArrayList<String>(Arrays.asList("CSV File Path"));
    ArrayList<JFXTextField> listOfFields = createFields(label);

    // Creating the form with a VBox
    dialogVBox.getChildren().addAll(listOfFields.get(0), buttonBox);
    dialogContent.setBody(dialogVBox);

    // Bringing the popup screen to the front and disabling the background
    stackPane.toFront();
    JFXDialog loadDialog =
        new JFXDialog(stackPane, dialogContent, JFXDialog.DialogTransition.BOTTOM);
    loadDialog.setOverlayClose(false);

    // Closing the popup
    closeButton.setOnAction(
        event -> {
          loadDialog.close();
          stackPane.toBack();
        });
    // Clearing the form, keeps the popup open
    clearButton.setOnAction(
        event -> {
          listOfFields.get(0).clear();
        });
    // Submits deletion to the database
    submitButton.setOnAction(
        event -> {
          // If incomplete form, sends an error msg
          // If edge is not in database, send sn error msg
          // Otherwise, sends to database and closes popup
          if (listOfFields.get(0).getText().isEmpty()) {
            incompletePopup();
          } else {
            String filePath = listOfFields.get(0).getText();
            // 1 = is a node
            DatabaseFunctionality.importExcelData(filePath, true);
            loadDialog.close();
            stackPane.toBack();
          }
        });
    loadDialog.show();
  }
}
