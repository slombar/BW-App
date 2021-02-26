package edu.wpi.cs3733.teamO.GraphSystem;

import edu.wpi.cs3733.teamO.Controllers.model.Node;
import java.util.Hashtable;
import java.util.LinkedList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javax.swing.*;

public class Pathfinding {
  private static Hashtable<String, Circle> stringCircleHashtable;
  private static GraphicsContext gc;
  public double cw;
  public static String loc;
  private static ObservableList<Node> nodeList = FXCollections.observableArrayList();;
  static String dest = "-1";
  boolean selectingLoc = true;
  static double cW = 10.0;
  public static Canvas mapcanvas;
  public static final Image bwLogo =
      new Image("Brigham_and_Womens_Hospital_logo.png", 116, 100, true, true);

  public static String closestCircle(double x, double y) {
    double currentDist = 1000000000;
    String nodeID = "-1";

    for (Node n : nodeList) {
      Circle c = stringCircleHashtable.get(n.getID());
      double cX = c.getCenterX();
      double cY = c.getCenterY();

      double dist = Math.pow(Math.abs(x - cX), 2.0) + Math.pow(Math.abs(y - cY), 2.0);
      if (dist < currentDist) {
        currentDist = dist;
        nodeID = n.getID();
      }
    }
    // dummy return
    return nodeID;
  }

  // Problem is probably in the gc variable
  public static void drawNodeCircles(GraphicsContext gc) {
    mapcanvas = gc.getCanvas();
    gc.clearRect(0, 0, mapcanvas.getWidth(), mapcanvas.getHeight());
    // divide them by a scale factor (image is ~2937 pixels wide?) --
    // would be imageWidth/canvasWidth and imageHeight/canvasHeight
    double scaleX = 2989 / mapcanvas.getWidth();
    double scaleY = 2457 / mapcanvas.getHeight();

    // circle widths:
    // double cW = 10.0;
    // TODO: (x,y) should already adjust when scrolling, but probably should also change radius

    // for each node in the DB, add their circle to the map
    for (Node n : nodeList) {
      double difference = 0;
      Circle circle = new Circle();
      /*add functionality to make the route path
      go left... go right?
       */

      double nodeX = (double) n.getXCoord() / scaleX;
      double nodeY = (double) n.getYCoord() / scaleY;

      circle.setCenterX(nodeX);
      circle.setCenterY(nodeY);
      circle.setRadius(cW / 2);
      stringCircleHashtable.put(n.getID(), circle);

      gc.setFill(Color.YELLOW); // default nodes are yellow
      gc.setGlobalAlpha(.75); // will make things drawn slightly transparent (if we want to)
      // DON'T DELETE -> JUST SET TO "1.0" IF NO TRANSPARENCY IS WANTED

      // sets color to blue/red if loc or dest are selected
      if (!loc.equals("-1") && n.getID().equals(loc)) {
        gc.setFill(Color.BLUE);
      }
      if (!dest.equals("-1") && n.getID().equals(dest)) {
        gc.setFill(Color.RED);
      }

      // create the circle utilizing the algorithm
      gc.fillOval(circle.getCenterX() - cW / 2, circle.getCenterY() - cW / 2, cW, cW);

      // sets alpha to 1.0 and draw a black border around circle
      gc.setGlobalAlpha(1.0);
      gc.strokeOval(circle.getCenterX() - cW / 2, circle.getCenterY() - cW / 2, cW, cW);

      circle.getCenterX();
      circle.getCenterY();
    }

    // draws the BW logo and text
    gc.setFill(Color.WHITE);
    gc.setGlobalAlpha(.75);
    gc.drawImage(bwLogo, 0, 0);
    gc.fillRect(87, -5, 350, 45);
    gc.strokeText("Brigham and Women's Faulkner Hospital Campus", 90, 30);
  }

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
