package edu.wpi.cs3733.teamO.DinoGame;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingNode;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javax.swing.*;

public class DinoGameController implements Initializable {
  // JFrame mainWindow = new JFrame("T-Rex Run");
  // Stage mainStage = Opp.getPrimaryStage(); // or make new stage

  public static int WIDTH = 800;
  public static int HEIGHT = 500;
  public AnchorPane anchorPane;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    createAndShowGUI();
  }

  public void createAndShowGUI() {
    // ! mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //    mainStage.setOnCloseRequest(
    //        event -> {
    //          event.consume();
    //        });

    //    GamePanel gamePanel = new GamePanel();
    //    gamePanel.addKeyListener(gamePanel);
    //    gamePanel.setFocusable(true);

    // build scene from pane
    // ! Container container = mainWindow.getContentPane();

    final SwingNode swingNode = new SwingNode();

    createSwingContent(swingNode);

    // TODO: should be Stack Pane existing in fxml

    anchorPane.getChildren().add(new Label("test label"));
    anchorPane.getChildren().add(swingNode);

    // Scene scene = new Scene(anchorPane, WIDTH, HEIGHT);
    System.out.println("dino game initialized?");

    // just a borderpane
    // ! container.setLayout(new BorderLayout());
    // ! container.add(gamePanel, BorderLayout.CENTER);

    //    mainStage.setScene(scene);
    //    mainStage.show();

    // ! mainWindow.setSize(WIDTH, HEIGHT);
    // ! mainWindow.setResizable(false);
    // ! mainWindow.setVisible(true);

    //    mainStage.setMaxWidth(WIDTH);
    //    mainStage.setMaxHeight(HEIGHT);
    //    mainStage.setResizable(false);
  }

  private void createSwingContent(final SwingNode swingNode) {
    SwingUtilities.invokeLater(
        new Runnable() {
          @Override
          public void run() {
            GamePanel gamePanel = new GamePanel();
            gamePanel.addKeyListener(gamePanel);
            gamePanel.setFocusable(true);

            swingNode.setContent(gamePanel);
          }
        });
  }

  // --------------------
  // main

  //  public static void main(String[] args) {
  //    javax.swing.SwingUtilities.invokeLater(
  //        new Runnable() {
  //          @SneakyThrows
  //          public void run() {
  //            new DinoGameController().createAndShowGUI();
  //          }
  //        });
  //  }
}
