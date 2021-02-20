package Controllers;

import Controllers.model.Node;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

public class EditNodesController implements Initializable {
  @FXML private TableView<Node> nodeTable;
  @FXML public static TableView<Node> nodeTable2;
  @FXML private TableColumn<Node, String> IDCol;
  @FXML private TableColumn<Node, String> xCoordCol;
  @FXML private TableColumn<Node, String> yCoordCol;
  @FXML private TableColumn<Node, String> floorCol;
  @FXML private TableColumn<Node, String> buildingCol;
  @FXML private TableColumn<Node, String> nodeTypeCol;
  @FXML private TableColumn<Node, String> longNameCol;
  @FXML private TableColumn<Node, String> shortNameCol;
  @FXML private TableColumn<Node, String> teamCol;
  @FXML private TableColumn<Node, Button> updateCol;
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

  private void initCols() {
    IDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
    xCoordCol.setCellValueFactory(new PropertyValueFactory<>("xCoord"));
    yCoordCol.setCellValueFactory(new PropertyValueFactory<>("yCoord"));
    floorCol.setCellValueFactory(new PropertyValueFactory<>("Floor"));
    buildingCol.setCellValueFactory(new PropertyValueFactory<>("Building"));
    nodeTypeCol.setCellValueFactory(new PropertyValueFactory<>("NodeType"));
    longNameCol.setCellValueFactory(new PropertyValueFactory<>("LongName"));
    shortNameCol.setCellValueFactory(new PropertyValueFactory<>("ShortName"));
    teamCol.setCellValueFactory(new PropertyValueFactory<>("Team"));
    updateCol.setCellValueFactory(new PropertyValueFactory<>("Update"));

    editableCols();
  }

  private void editableCols() {
    IDCol.setCellFactory(TextFieldTableCell.forTableColumn());
    IDCol.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<Node, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<Node, String> e) {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setID(e.getNewValue());
          }
        });
    xCoordCol.setCellFactory(TextFieldTableCell.forTableColumn());
    xCoordCol.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<Node, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<Node, String> e) {
            e.getTableView()
                .getItems()
                .get(e.getTablePosition().getRow())
                .setXCoord(e.getNewValue());
          }
        });
    yCoordCol.setCellFactory(TextFieldTableCell.forTableColumn());
    yCoordCol.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<Node, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<Node, String> e) {
            e.getTableView()
                .getItems()
                .get(e.getTablePosition().getRow())
                .setYCoord(e.getNewValue());
          }
        });
    floorCol.setCellFactory(TextFieldTableCell.forTableColumn());
    floorCol.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<Node, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<Node, String> e) {
            e.getTableView()
                .getItems()
                .get(e.getTablePosition().getRow())
                .setFloor(e.getNewValue());
          }
        });
    buildingCol.setCellFactory(TextFieldTableCell.forTableColumn());
    buildingCol.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<Node, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<Node, String> e) {
            e.getTableView()
                .getItems()
                .get(e.getTablePosition().getRow())
                .setBuilding(e.getNewValue());
          }
        });
    nodeTypeCol.setCellFactory(TextFieldTableCell.forTableColumn());
    nodeTypeCol.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<Node, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<Node, String> e) {
            e.getTableView()
                .getItems()
                .get(e.getTablePosition().getRow())
                .setNodeType(e.getNewValue());
          }
        });
    longNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
    longNameCol.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<Node, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<Node, String> e) {
            e.getTableView()
                .getItems()
                .get(e.getTablePosition().getRow())
                .setLongName(e.getNewValue());
          }
        });
    shortNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
    shortNameCol.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<Node, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<Node, String> e) {
            e.getTableView()
                .getItems()
                .get(e.getTablePosition().getRow())
                .setShortName(e.getNewValue());
          }
        });
    teamCol.setCellFactory(TextFieldTableCell.forTableColumn());
    teamCol.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<Node, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<Node, String> e) {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setTeam(e.getNewValue());
          }
        });

    nodeTable.setEditable(true);
  }

  private void loadData() {
    nodeList = FXCollections.observableArrayList();
    nodeList = DatabaseFunctionality.showNodes(nodeList);
    nodeTable.setItems(nodeList);
  }

  public void goToHomePage(ActionEvent actionEvent) throws IOException {
    System.out.println("Starting Up");
    Parent parent = FXMLLoader.load(getClass().getResource("/Views/test.fxml"));
    Scene scene = new Scene(parent);
    // this gets Stage info
    Stage window = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
    window.setTitle("Map Homepage");
    // this sets the scene to the new one specified above
    window.setScene(scene);
    window.show();
  }

  public void goToEditEdges(ActionEvent actionEvent) throws IOException {
    System.out.println("Starting Up");
    Parent parent = FXMLLoader.load(getClass().getResource("/Views/EditEdges.fxml"));
    Scene scene = new Scene(parent);
    // this gets Stage info
    Stage window = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
    window.setTitle("Map Homepage");
    // this sets the scene to the new one specified above
    window.setScene(scene);
    window.show();
  }
}
