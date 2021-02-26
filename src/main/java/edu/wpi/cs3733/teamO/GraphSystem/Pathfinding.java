package edu.wpi.cs3733.teamO.GraphSystem;

import edu.wpi.cs3733.teamO.model.Node;

import java.beans.EventHandler;
import java.util.Hashtable;
import java.util.LinkedList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Pathfinding {
  private static Hashtable<String, Circle> stringCircleHashtable;

  public double cw;
  public static String loc;
  private static ObservableList<Node> nodeList = FXCollections.observableArrayList();
  static String dest = "-1";
  boolean selectingLoc = true;
  static double cW = 10.0;
  public static Canvas mapcanvas;
  public static final Image bwLogo =
      new Image("Brigham_and_Womens_Hospital_logo.png", 116, 100, true, true);



  public static void drawPath(LinkedList<String> path) {
    // want to just draw start and end nodes, then draw lines (will be arrows eventually)
    // TODO: should probably make a drawCircle(), singular, helper at some point
    Circle locC = stringCircleHashtable.get(loc);
    Circle destC = stringCircleHashtable.get(dest);

    // draw start node and outline
    gc.setGlobalAlpha(0.75); // TODO: should make alpha a variable at some point
    gc.setFill(Color.BLUE);
    gc.fillOval(locC.getCenterX() - cW / 2, locC.getCenterY() - cW / 2, cW, cW);
    gc.setGlobalAlpha(1.0);
    gc.strokeOval(locC.getCenterX() - cW / 2, locC.getCenterY() - cW / 2, cW, cW);

    // draw dest node and outline
    gc.setGlobalAlpha(0.75);
    gc.setFill(Color.RED);
    gc.fillOval(destC.getCenterX() - cW / 2, destC.getCenterY() - cW / 2, cW, cW);
    gc.setGlobalAlpha(1.0);
    gc.strokeOval(destC.getCenterX() - cW / 2, destC.getCenterY() - cW / 2, cW, cW);

    gc.setGlobalAlpha(1.0); // makes the lines fully opaque
    for (int i = 0; i < path.size() - 1; i++) {
      Circle a = stringCircleHashtable.get(path.get(i));
      Circle b = stringCircleHashtable.get(path.get(i + 1));

      // gc.strokeLine(a.getCenterX(), a.getCenterY(), b.getCenterX(), b.getCenterY());
      drawMidArrow(a.getCenterX(), a.getCenterY(), b.getCenterX(), b.getCenterY());
    }
  }

  private static final double arrowLength = 6;
  private static final double arrowWidth = 4;
  private static final double minArrowDistSq = 108;

  public static void drawMidArrow(double ax, double ay, double bx, double by) {

    double distSq = Math.pow(Math.abs(ax - bx), 2.0) + Math.pow(Math.abs(ay - by), 2.0);

    if (distSq >= minArrowDistSq) {
      double cx = (ax + bx) / 2;
      double cy = (ay + by) / 2;

      double hypot = Math.hypot(ax - cx, ay - cy);
      double factor = arrowLength / hypot;
      double factorO = arrowWidth / hypot;

      // part in direction of main line
      double dx = (ax - cx) * factor;
      double dy = (ay - cy) * factor;

      // part orthogonal to main line
      double ox = (ax - cx) * factorO;
      double oy = (ay - cy) * factorO;

      double arrow1startX = (cx + dx - oy);
      double arrow1startY = (cy + dy + ox);
      double arrow2startX = (cx + dx + oy);
      double arrow2startY = (cy + dy - ox);

      gc.strokeLine(arrow1startX, arrow1startY, cx, cy);
      gc.strokeLine(arrow2startX, arrow2startY, cx, cy);
    }

    gc.strokeLine(ax, ay, bx, by);
  }
}
