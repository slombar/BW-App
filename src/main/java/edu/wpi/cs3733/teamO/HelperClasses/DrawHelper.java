package edu.wpi.cs3733.teamO.HelperClasses;

import edu.wpi.cs3733.teamO.model.Node;
import java.util.ArrayList;
import java.util.Hashtable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
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
      GraphicsContext gc, Hashtable<Node, Circle> ncTable, ArrayList<Node> nodeList) {

    Canvas mapcanvas = gc.getCanvas();
    gc.clearRect(0, 0, mapcanvas.getWidth(), mapcanvas.getHeight());

    Circle tempCir = new Circle();

    // TODO: figure out the color situation (eg. changing when selected)
    gc.setGlobalAlpha(0.9);
    gc.setFill(Color.YELLOW);
    gc.setStroke(Color.BLACK);
    gc.setLineWidth(1.0);
    // for each node in the DB, add their circle to the map
    for (Node n : nodeList) {
      tempCir = ncTable.get(n);
      double tempCirX = tempCir.getCenterX() - tempCir.getRadius();
      double tempCirY = tempCir.getCenterY() - tempCir.getRadius();
      double diameter = 2 * tempCir.getRadius();

      gc.fillOval(tempCirX, tempCirY, diameter, diameter);
      gc.strokeOval(tempCirX, tempCirY, diameter, diameter);
    }

    //////////////////// FOR TESTING: ////////////////////
    gc.setStroke(Color.RED);
    gc.setLineWidth(3.0);

    gc.strokeOval(0, 0, 5, 5);
    gc.strokeOval(0, mapcanvas.getHeight(), 5, 5);
    gc.strokeOval(mapcanvas.getWidth(), 0, 5, 5);
    gc.strokeOval(mapcanvas.getWidth(), mapcanvas.getHeight(), 5, 5);

    gc.strokeRect(0, 0, mapcanvas.getWidth(), mapcanvas.getHeight());
    gc.strokeLine(0, 0, mapcanvas.getWidth(), mapcanvas.getHeight());
    gc.strokeLine(0, mapcanvas.getHeight(), mapcanvas.getWidth(), 0);
  }

  /**
   * Draws a single line with an arrowhead halfway from point a to point b
   * @param gc the GraphicsContext on which the arrow will be drawn
   * @param ax x-coordinate of point a
   * @param ay y-coordinate of point a
   * @param bx x-coordinate of point b
   * @param by y-coordinate of point b
   */
  public static void drawMidArrow(GraphicsContext gc, double ax, double ay, double bx, double by) {
    double arrowLength = 6;
    final double arrowWidth = 4;
    final double minArrowDistSq = 108;

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
