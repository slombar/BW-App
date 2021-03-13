package edu.wpi.cs3733.teamO.Controllers.Revamped;

import static edu.wpi.cs3733.teamO.GraphSystem.Graph.*;
import static edu.wpi.cs3733.teamO.GraphSystem.Graph.floor5Map;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXNodesList;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javax.swing.*;

public class NavController implements Initializable {

  public AnchorPane anchorPane;
  public Canvas mapCanvas;
  public FlowPane hamburger;
  public ImageView imageView;
  @FXML private JFXNodesList parking;
  @FXML private FlowPane bottomRightInfo;
  @FXML private JFXNodesList directions;
  @FXML private JFXNodesList editing;
  @FXML private JFXNodesList help;
  @FXML private JFXNodesList floors;
  private final JFXButton helpB = new JFXButton(null, null);
  private final JFXButton parkingB = new JFXButton(null, null);
  private final JFXButton editB = new JFXButton(null, null);
  private final JFXButton showEdgesB = new JFXButton(null, null);
  private final JFXButton saveB = new JFXButton(null, null);
  private final JFXButton uploadB = new JFXButton(null, null);
  private final JFXButton navB = new JFXButton(null, null);
  private final JFXButton floorSelectionB = new JFXButton(null, null);
  private final JFXButton floorGB = new JFXButton(null, null);
  private final JFXButton floor1B = new JFXButton(null, null);
  private final JFXButton floor2B = new JFXButton(null, null);
  private final JFXButton floor3B = new JFXButton(null, null);
  private final JFXButton floor4B = new JFXButton(null, null);
  private final JFXButton floor5B = new JFXButton(null, null);
  VBox directionsBox = new VBox();
  HBox directionButtons = new HBox();
  JFXTextField startLoc = new JFXTextField();
  JFXTextField endLoc = new JFXTextField();
  private final JFXButton submitPath = new JFXButton("GO");
  private final JFXButton clearPath = new JFXButton("Clear");

  /**
   * Create buttons for: directions editing help floors
   *
   * <p>make hamburger
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {

    // Add Buttons to their respective list
    /** Add to the editing dropdown * */
    editing.addAnimatedNode(editB);
    editing.addAnimatedNode(showEdgesB);
    editing.addAnimatedNode(saveB);
    editing.addAnimatedNode(uploadB);

    /** Add to the floor selection* */
    floors.addAnimatedNode(floorSelectionB);
    floors.addAnimatedNode(floorGB);
    floors.addAnimatedNode(floor1B);
    floors.addAnimatedNode(floor2B);
    floors.addAnimatedNode(floor3B);
    floors.addAnimatedNode(floor4B);
    floors.addAnimatedNode(floor5B);

    /** hELP?* */
    help.addAnimatedNode(helpB);
    // parking
    parking.addAnimatedNode(parkingB);

    /** Navigation Button* */
    directions.addAnimatedNode(navB);

    // add stuff to vbox TODO(make horizontal)
    directions.setRotate(90);

    directionsBox.getChildren().addAll(startLoc, endLoc);
    directionButtons.getChildren().addAll(clearPath, submitPath);
    directionsBox.getChildren().add(directionButtons);

    directions.addAnimatedNode(directionsBox);
    // TODO bind to flowpane

    // set onaction events for all buttons
    generalButtons();
  }

  public void generalButtons() {

    parkingB.setOnAction(
        e -> {
          // change the first location field to the saved parking spot
        });

    helpB.setOnAction(
        e -> {
          // show tutorial/help
        });

    submitPath.setOnAction(
        e -> {
          // send path to do pathfinding actions
        });

    clearPath.setOnAction(
        e -> {
          // clear start and end locations
        });

    /** Floor onactions* */
    floorGB.setOnAction(
        e -> {
          imageView.setImage(campusMap);
        });
    floor1B.setOnAction(
        e -> {
          imageView.setImage(floor1Map);
        });
    floor2B.setOnAction(
        e -> {
          imageView.setImage(floor2Map);
        });
    floor3B.setOnAction(
        e -> {
          imageView.setImage(floor3Map);
        });
    floor4B.setOnAction(
        e -> {
          imageView.setImage(floor4Map);
        });
    floor5B.setOnAction(
        e -> {
          imageView.setImage(floor5Map);
        });

    /** Edit onactions* */
    editB.setOnAction(
        e -> {
          // toggle edit mode
        });
    uploadB.setOnAction(
        e -> {
          // upload csv to DB
        });
    saveB.setOnAction(
        e -> {
          // save csv to computer
        });
    showEdgesB.setOnAction(
        e -> {
          // show all edges
        });
  }
}
