package edu.wpi.cs3733.teamO.Controllers;

import com.jfoenix.controls.*;
import edu.wpi.cs3733.teamO.Opp;
import java.io.IOException;
import edu.wpi.cs3733.teamO.Opp;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

public class NewNavPageController implements Initializable {

  @FXML private HBox hboxRef;
  @FXML private VBox vboxRef;
  @FXML private RowConstraints row0;
  @FXML private RowConstraints row1;
  @FXML private ColumnConstraints col1;
  @FXML private GridPane gridPane;
  @FXML private JFXDrawer drawerSM1;
  @FXML private JFXHamburger hamburgerMainBtn1;
  @FXML private AnchorPane anchorPane;
  @FXML private Canvas canvas;
  @FXML private VBox topMenu;
  @FXML private JFXDrawer drawerSM;
  @FXML private JFXHamburger hamburgerMainBtn;
  @FXML private BorderPane borderPane;
  @FXML private JFXToggleButton editToggle;
  @FXML private ImageView imageView;
  @FXML private JFXComboBox<String> floorSelectionBtn;
  @FXML private JFXButton startLocBtn;
  @FXML private JFXButton endLocBtn;
  @FXML private JFXButton pathfindBtn;

  ObservableList<String> listOfFloors =
      FXCollections.observableArrayList("Ground", "Floor 2", "Floor 3", "Floor 4", "Floor 5");

  public NewNavPageController() {}

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    floorSelectionBtn.setItems(listOfFloors);
    resizableWindow();
    canvas.toFront();

    // Drawer Stuff

    //    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/sideMenu.fxml"));
    //    VBox box = null;
    //    try {
    //      box = loader.load();
    //    } catch (IOException e) {
    //      e.printStackTrace();
    //    }
    //    SideMenuController controller = loader.getController();
    //    drawerSM.setSidePane(box);
    //
    //    HamburgerBackArrowBasicTransition transition =
    //        new HamburgerBackArrowBasicTransition(hamburgerMainBtn);
    //    transition.setRate(-1);
    //    hamburgerMainBtn.addEventHandler(
    //        MouseEvent.MOUSE_PRESSED,
    //        (e) -> {
    //          transition.setRate(transition.getRate() * -1);
    //          transition.play();
    //
    //          if (drawerSM.isOpened()) {
    //            drawerSM.close();
    //          } else {
    //            drawerSM.open();
    //          }
    //        });
  }

  //  /**
  //   * Create a resizable navigation map with editing features available for admin
  //   *
  //   * @return border pane
  //   */
  //  public BorderPane resizableWindow() {
  //    borderPane.setPadding(new Insets(5));
  //
  //    // campusMap.fitWidthProperty().bind(borderPane.widthProperty());
  //    // campusMap.fitHeightProperty().bind(borderPane.heightProperty());
  //    anchorPane.prefWidthProperty().bind(borderPane.widthProperty());
  //    anchorPane.prefHeightProperty().bind(borderPane.heightProperty());
  //    imageView.fitWidthProperty().bind(borderPane.widthProperty());
  //    imageView.fitHeightProperty().bind(borderPane.heightProperty());
  //    canvas.widthProperty().bind(imageView.fitWidthProperty());
  //    canvas.heightProperty().bind(imageView.fitHeightProperty());
  //
  //    BorderPane.setAlignment(imageView, Pos.TOP_CENTER);
  //    borderPane.setCenter(imageView);
  //    // borderPane.setTop(topMenu);
  //    // topMenu.prefWidthProperty().bind(borderPane.widthProperty());
  //
  //    return borderPane;
  //  }

  /**
   * Create a resizable navigation map with editing features available for admin
   *
   * @return grid pane
   */
  public GridPane resizableWindow() {
    imageView.setPreserveRatio(true);
    imageView
        .fitHeightProperty()
        .bind(Opp.getPrimaryStage().getScene().heightProperty().subtract(vboxRef.heightProperty()));
    imageView.fitWidthProperty().bind(hboxRef.widthProperty());

    canvas.heightProperty().bind(imageView.fitHeightProperty());
    canvas.widthProperty().bind(imageView.fitWidthProperty());

    return gridPane;
  }

  public void editMode(ActionEvent actionEvent) {}

  public void goToMain(ActionEvent actionEvent) {
    try {
      BorderPane root = FXMLLoader.load(getClass().getResource("/Views/MainPage.fxml"));
      Opp.getPrimaryStage().getScene().setRoot(root);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public void floorSelection(ActionEvent actionEvent) {}

  public void startlocslection(ActionEvent actionEvent) {}

  public void endLocSelection(ActionEvent actionEvent) {}

  public void doPathfind(ActionEvent actionEvent) {}

  public void goToSideMenu(MouseEvent mouseEvent) {}

  public void canvasClick(MouseEvent mouseEvent) {
    System.out.println("Click");
  }
}
