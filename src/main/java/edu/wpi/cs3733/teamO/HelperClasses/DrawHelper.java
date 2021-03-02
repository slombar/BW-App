package edu.wpi.cs3733.teamO.HelperClasses;

import edu.wpi.cs3733.teamO.model.Node;
import java.util.ArrayList;
import java.util.Hashtable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

// class that SOLEY draws shit
public class DrawHelper {

  /**
   * Draws every Circle from the given Hashtable corresponding to each Node in the ArrayList
   *
   * @param gc the GraphicsContext on which the Circles will be drawn
   * @param ncTable the Hashtable in which the Circles are stored
   * @param nodeList the list of Nodes whose Circles should be drawn
   */
  public static void drawNodeCircles(
      GraphicsContext gc,
      Hashtable<Node, Circle> ncTable,
      ArrayList<Node> nodeList,
      Node startNode,
      Node endNode) {

    Canvas mapcanvas = gc.getCanvas();
    gc.clearRect(0, 0, mapcanvas.getWidth(), mapcanvas.getHeight());

    Circle tempCir = new Circle();

    // for each node in the DB, add their circle to the map
    for (Node n : nodeList) {
      gc.setGlobalAlpha(1.0);
      gc.setFill(Color.YELLOW);
      gc.setStroke(Color.BLACK);
      gc.setLineWidth(2.0);

      tempCir = ncTable.get(n);
      double tempCirX = tempCir.getCenterX() - tempCir.getRadius();
      double tempCirY = tempCir.getCenterY() - tempCir.getRadius();
      double diameter = 2 * tempCir.getRadius();

      if (n.getNodeType().equals("STAI")) {
        gc.setFill(Color.GREEN);
      } else if (n.getNodeType().equals("ELEV")) {
        gc.setFill(Color.PURPLE);
      }

      if (n.getNodeType().equals("EXIT")) {
        gc.setFill(Color.ORANGE);
      }

      if (n.equals(startNode)) {
        gc.setFill(Color.BLUE);
      } else if (n.equals(endNode)) {
        gc.setFill(Color.RED);
      }

      gc.fillOval(tempCirX, tempCirY, diameter, diameter);
      gc.strokeOval(tempCirX, tempCirY, diameter, diameter);
    }

    /*//////////////////// FOR TESTING: ////////////////////
    gc.setStroke(Color.RED);
    gc.setLineWidth(3.0);

    gc.strokeOval(0, 0, 5, 5);
    gc.strokeOval(0, mapcanvas.getHeight(), 5, 5);
    gc.strokeOval(mapcanvas.getWidth(), 0, 5, 5);
    gc.strokeOval(mapcanvas.getWidth(), mapcanvas.getHeight(), 5, 5);

    gc.strokeRect(0, 0, mapcanvas.getWidth(), mapcanvas.getHeight());
    gc.strokeLine(0, 0, mapcanvas.getWidth(), mapcanvas.getHeight());
    gc.strokeLine(0, mapcanvas.getHeight(), mapcanvas.getWidth(), 0);*/
  }

  /**
   * Draws a single line with an arrowhead halfway from point a to point b
   *
   * @param gc
   * @param circleA
   * @param circleB
   */
  public static void drawMidArrow(GraphicsContext gc, Circle circleA, Circle circleB) {
    double arrowLength = 6;
    final double arrowWidth = 4;
    final double minArrowDistSq = 180;
    // ^ do the dist you wanted squared (probably want 5*(arrowLength^2))

    double ax = circleA.getCenterX();
    double ay = circleA.getCenterY();
    double bx = circleB.getCenterX();
    double by = circleB.getCenterY();

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

      gc.setLineWidth(3.0);
      gc.strokeLine(arrow1startX, arrow1startY, cx, cy);
      gc.strokeLine(arrow2startX, arrow2startY, cx, cy);
    } else {
      boolean dummy = true;
    }
    gc.strokeLine(ax, ay, bx, by);
  }

  public static void drawEdge(GraphicsContext gc, Circle circleA, Circle circleB) {
    gc.setLineWidth(3.0);
    gc.strokeLine(
        circleA.getCenterX(), circleA.getCenterY(), circleB.getCenterX(), circleB.getCenterY());
  }

  /**
   * Draws the given Circle in the given color
   *
   * @param gc GraphicsContext on which to draw
   * @param circle circle (node) being drawn
   * @param paint Color.COLOR being drawn
   */
  public static void drawSingleNode(GraphicsContext gc, Circle circle, Paint paint) {
    gc.setGlobalAlpha(1.0);
    gc.setFill(paint);
    gc.setStroke(Color.BLACK);
    gc.setLineWidth(2.0);

    double tempCirX = circle.getCenterX() - circle.getRadius();
    double tempCirY = circle.getCenterY() - circle.getRadius();
    double diameter = 2 * circle.getRadius();

    gc.fillOval(tempCirX, tempCirY, diameter, diameter);
    gc.strokeOval(tempCirX, tempCirY, diameter, diameter);
  }
}
