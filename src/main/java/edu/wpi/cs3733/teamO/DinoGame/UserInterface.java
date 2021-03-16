package edu.wpi.cs3733.teamO.DinoGame;

import edu.wpi.cs3733.teamO.Opp;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javax.imageio.*;
import javax.swing.*;
import lombok.SneakyThrows;

public class UserInterface extends Pane {
  JFrame mainWindow = new JFrame("T-Rex Run");
  Stage mainStage = Opp.getPrimaryStage(); // or make new stage

  public static int WIDTH = 800;
  public static int HEIGHT = 500;

  public void createAndShowGUI() throws IOException {
    // ! mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //    mainStage.setOnCloseRequest(
    //        event -> {
    //          event.consume();
    //        });

    GamePanel gamePanel = new GamePanel();
    gamePanel.addKeyListener(gamePanel);
    gamePanel.setFocusable(true);

    // build scene from pane
    // ! Container container = mainWindow.getContentPane();

    Group rt1 = new Group((Collection<Node>) gamePanel);
    Scene scene = new Scene(rt1, WIDTH, HEIGHT);

    // just a borderpane
    // ! container.setLayout(new BorderLayout());
    // ! container.add(gamePanel, BorderLayout.CENTER);

    mainStage.setScene(scene);
    mainStage.show();


    // ! mainWindow.setSize(WIDTH, HEIGHT);
    // ! mainWindow.setResizable(false);
    // ! mainWindow.setVisible(true);

    mainStage.setMaxWidth(WIDTH);
    mainStage.setMaxHeight(HEIGHT);
    mainStage.setResizable(false);
  }

  public static void main(String[] args) {
    javax.swing.SwingUtilities.invokeLater(
        new Runnable() {
          @SneakyThrows
          public void run() {
            new UserInterface().createAndShowGUI();
          }
        });
  }
}
