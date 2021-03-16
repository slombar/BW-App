package edu.wpi.cs3733.teamO.DinoGame;

import edu.wpi.cs3733.teamO.DinoGame.components.Dino;
import edu.wpi.cs3733.teamO.DinoGame.components.Ground;
import edu.wpi.cs3733.teamO.DinoGame.components.Obstacles;
import edu.wpi.cs3733.teamO.Opp;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.util.*;

public class UserInterface extends Pane {
  JFrame mainWindow = new JFrame("T-Rex Run");
  Stage mainStage = Opp.getPrimaryStage(); //or make new stage

  public static int WIDTH = 800;
  public static int HEIGHT = 500;

  public void createAndShowGUI() throws IOException {
    mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    mainStage.setOnCloseRequest(event -> {
      event.consume();
    });


    //build scene from pane
    Container container = mainWindow.getContentPane();
    Parent root =
            FXMLLoader.load(getClass().getResource("/RevampedViews/DesktopApp/SignInPage.fxml"));
    Scene scene = new Scene(root);
    //Scene scene = new Scene(root, 600, 400);



    GamePanel gamePanel = new GamePanel();
    gamePanel.addKeyListener(gamePanel);
    gamePanel.setFocusable(true);


    //just a borderpane
    container.setLayout(new BorderLayout());
    container.add(gamePanel, BorderLayout.CENTER);

    BorderPane border = new BorderPane();
    AnchorPane anchor = new AnchorPane();
    HBox hbox = new HBox();
    VBox vbox = new VBox();

    border.setTop(hbox);

    //anchor.getChildren().add(gamePanel); //HOW TO PASS IN GAMEPANEL
    //vbox.getChildren().add(gamePanel);


// public GamePanel() {
//      WIDTH = UserInterface.WIDTH;
//      HEIGHT = UserInterface.HEIGHT;
//
//      ground = new Ground(HEIGHT);
//      dino = new Dino();
//      obstacles = new Obstacles((int) (WIDTH * 1.5));
//
//      score = 0;
//
//      setSize(WIDTH, HEIGHT);
//      setVisible(true);
////    }

    mainWindow.setSize(WIDTH, HEIGHT);
    mainWindow.setResizable(false);
    mainWindow.setVisible(true);

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
