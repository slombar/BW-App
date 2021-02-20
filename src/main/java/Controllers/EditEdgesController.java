package Controllers;

import Controllers.model.Edge;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

public class EditEdgesController implements Initializable {

  @FXML private TableView<Edge> edgeTable;
  @FXML public static TableView<Edge> edgeTable2;
  @FXML private TableColumn<Edge, String> IDCol;
  @FXML private TableColumn<Edge, String> startCol;
  @FXML private TableColumn<Edge, String> endCol;
  @FXML private TableColumn<Edge, Button> updateCol;
  public static ObservableList<Edge> edgeList;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    edgeTable2 = edgeTable;
    initTable();
    loadData();
  }

  private void initTable() {
    initCols();
  }

  private void initCols() {
    IDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
    startCol.setCellValueFactory(new PropertyValueFactory<>("Start"));
    endCol.setCellValueFactory(new PropertyValueFactory<>("End"));
    updateCol.setCellValueFactory(new PropertyValueFactory<>("Update"));

    editableCols();
  }

  private void editableCols() {
    IDCol.setCellFactory(TextFieldTableCell.forTableColumn());
    IDCol.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<Edge, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<Edge, String> e) {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setID(e.getNewValue());
          }
        });

    startCol.setCellFactory(TextFieldTableCell.forTableColumn());
    startCol.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<Edge, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<Edge, String> e) {
            e.getTableView()
                .getItems()
                .get(e.getTablePosition().getRow())
                .setStart(e.getNewValue());
          }
        });

    endCol.setCellFactory(TextFieldTableCell.forTableColumn());
    endCol.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<Edge, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<Edge, String> e) {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setEnd(e.getNewValue());
          }
        });

    edgeTable.setEditable(true);
  }

  private void loadData() {
    edgeList = FXCollections.observableArrayList();
    edgeList = DatabaseFunctionality.showEdges(edgeList);
    edgeTable.setItems(edgeList);
  }

  public void goToHomePage(ActionEvent actionEvent) throws IOException {
    System.out.println("Starting Up");
    Parent parent = FXMLLoader.load(getClass().getResource("/Views/Index.fxml"));
    Scene scene = new Scene(parent);
    // this gets Stage info
    Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    window.setTitle("Map Homepage");
    // this sets the scene to the new one specified above
    window.setScene(scene);
    window.show();
  }

  public void goToEditNodes(ActionEvent actionEvent) throws IOException {
    System.out.println("Starting Up");
    Parent parent = FXMLLoader.load(getClass().getResource("/Views/EditNodes.fxml"));
    Scene scene = new Scene(parent);
    // this gets Stage info
    Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    window.setTitle("Edit Nodes");
    // this sets the scene to the new one specified above
    window.setScene(scene);
    window.show();
  }
}
