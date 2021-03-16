package edu.wpi.cs3733.teamO.DinoGame;

import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class UserInterface {
  JFrame mainWindow = new JFrame("T-Rex Run");
  Stage mainStage = new Stage();

  public static int WIDTH = 800;
  public static int HEIGHT = 500;

  public void createAndShowGUI() {
    mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    Container container = mainWindow.getContentPane();

    GamePanel gamePanel = new GamePanel();
    gamePanel.addKeyListener(gamePanel);
    gamePanel.setFocusable(true);

    container.setLayout(new BorderLayout());

    container.add(gamePanel, BorderLayout.CENTER);

    mainWindow.setSize(WIDTH, HEIGHT);
    mainWindow.setResizable(false);
    mainWindow.setVisible(true);
  }

  public static void main(String[] args) {
    javax.swing.SwingUtilities.invokeLater(
        new Runnable() {
          public void run() {
            new UserInterface().createAndShowGUI();
          }
        });
  }
}
