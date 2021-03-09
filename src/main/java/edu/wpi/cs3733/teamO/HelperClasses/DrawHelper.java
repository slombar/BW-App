package edu.wpi.cs3733.teamO.HelperClasses;

import edu.wpi.cs3733.teamO.Model.Node;
import java.util.ArrayList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

// class that SOLEY draws shit
public class DrawHelper {

  // these control the size of the nodes (different for desktop/mobile)
  //  -> dperc makes the circles a certain percent of the image adjusted based on the zoom level
  //  -> dmin is the minimum diameter (in pixels), so if you're zoomed out too much, they won't be
  // too small
  // which is used is based on Math.MAX()
  private static double dperc = 0.015;
  private static double dmin = 10;
  private static double dpercMOBILE = 0.005;
  private static double dminMOBILE = 4;

  /**
   * Draws every Circle from the given Hashtable corresponding to each Node in the ArrayList
   *
   * @param gc the GraphicsContext on which the Circles will be drawn
   * @param ncTable the Hashtable in which the Circles are stored
   * @param nodeList the list of Nodes whose Circles should be drawn
   */
  /*public static void drawNodeCircles(
  GraphicsContext gc,
  Hashtable<String, Circle> ncTable,
  ArrayList<Node> nodeList,
  Node startNode,
  Node endNode)*/

  public static void drawNodeCircles(
      GraphicsContext gc,
      ArrayList<Node> nodeList,
      Node startNode,
      Node endNode,
      ImageView imageView,
      boolean isMobile) {

    Canvas mapcanvas = gc.getCanvas();
    gc.clearRect(0, 0, mapcanvas.getWidth(), mapcanvas.getHeight());

    double canvasW = gc.getCanvas().getWidth();
    double canvasH = gc.getCanvas().getHeight();

    double imageW = imageView.getImage().getWidth();
    double imageH = imageView.getImage().getHeight();

    double vpX = imageView.getViewport().getMinX() / imageW;
    double vpY = imageView.getViewport().getMinY() / imageH;
    double wp = imageView.getViewport().getWidth() / imageW;
    double hp = imageView.getViewport().getHeight() / imageH;

    for (Node n : nodeList) {
      gc.setGlobalAlpha(1.0);
      gc.setFill(Color.YELLOW);
      gc.setStroke(Color.BLACK);
      gc.setLineWidth(2.0);

      double nxp = n.getXCoord() / imageW;
      double nyp = n.getYCoord() / imageH;

      double diameter = -1;
      if (isMobile) {
        diameter = Math.max(dminMOBILE, dpercMOBILE * (1 - wp) * imageW);
      } else {
        diameter = Math.max(dmin, dperc * (1 - wp) * imageW);
      }

      double circleX = (((nxp - vpX) / wp) * canvasW) - diameter / 2;
      double circleY = (((nyp - vpY) / hp) * canvasH) - diameter / 2;

      // ------------------
      // color stuff:
      // makes stairs green and elevators purple
      if (n.getNodeType().equals("STAI")) {
        gc.setGlobalAlpha(0.25);
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.GREEN);
      } else if (n.getNodeType().equals("ELEV")) {
        gc.setGlobalAlpha(0.25);
        gc.setFill(Color.PURPLE);
        gc.setStroke(Color.PURPLE);
      }

      // exits are orange
      if (n.getNodeType().equals("EXIT")) {
        gc.setFill(Color.ORANGE);
      }

      // invisible nodes in navigator aren't filled in in editor
      if (!n.isVisible()) {
        gc.setFill(Color.TRANSPARENT);
      }

      // start node is blue and end is red
      if (n.equals(startNode)) {
        gc.setFill(Color.BLUE);
      } else if (n.equals(endNode)) {
        gc.setFill(Color.RED);
      }
      // ------------------

      gc.fillOval(circleX, circleY, diameter, diameter);
      gc.setGlobalAlpha(1.0);
      gc.strokeOval(circleX, circleY, diameter, diameter);
    }
  }

  /**
   * Draws a single line with an arrowhead halfway from point a to point b
   *
   * @param gc
   * @param circleA
   * @param circleB
   */
  public static void drawMidArrow(
      GraphicsContext gc, Node nodeA, Node nodeB, ImageView imageView, boolean isMobile) {
    double arrowLength = 6;
    final double arrowWidth = 4;
    final double minArrowDistSq = 216;
    // ^ do the dist you wanted squared (currently have 6*(arrowLength^2))

    double canvasW = gc.getCanvas().getWidth();
    double canvasH = gc.getCanvas().getHeight();

    double imageW = imageView.getImage().getWidth();
    double imageH = imageView.getImage().getHeight();

    double vpX = imageView.getViewport().getMinX() / imageW;
    double vpY = imageView.getViewport().getMinY() / imageH;
    double wp = imageView.getViewport().getWidth() / imageW;
    double hp = imageView.getViewport().getHeight() / imageH;

    double diameter = -1;
    if (isMobile) {
      diameter = Math.max(dminMOBILE, dpercMOBILE * (1 - wp) * imageW);
    } else {
      diameter = Math.max(dmin, dperc * (1 - wp) * imageW);
    }

    double nxpA = nodeA.getXCoord() / imageW;
    double nypA = nodeA.getYCoord() / imageH;

    double ax = (((nxpA - vpX) / wp) * canvasW);
    double ay = (((nypA - vpY) / hp) * canvasH);

    double nxpB = nodeB.getXCoord() / imageW;
    double nypB = nodeB.getYCoord() / imageH;

    double bx = (((nxpB - vpX) / wp) * canvasW);
    double by = (((nypB - vpY) / hp) * canvasH);

    double distSq = Math.pow(Math.abs(ax - bx), 2.0) + Math.pow(Math.abs(ay - by), 2.0);

    gc.setLineWidth(3.0);
    gc.setStroke(Color.BLACK);

    // only will draw arrowhead if line will be at least min length
    if (distSq >= minArrowDistSq) {
      // a bunch of math to draw arrowhead in line with line's direction:
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

      // draws arrowhead lines
      gc.strokeLine(arrow1startX, arrow1startY, cx, cy);
      gc.strokeLine(arrow2startX, arrow2startY, cx, cy);
    }

    gc.strokeLine(ax, ay, bx, by);
  }

  public static void drawEdge(
      GraphicsContext gc,
      Node nodeA,
      Node nodeB,
      Paint color,
      ImageView imageView,
      boolean isMobile) {
    gc.setLineWidth(3.0);
    gc.setStroke(color);

    double canvasW = gc.getCanvas().getWidth();
    double canvasH = gc.getCanvas().getHeight();

    double imageW = imageView.getImage().getWidth();
    double imageH = imageView.getImage().getHeight();

    double vpX = imageView.getViewport().getMinX() / imageW;
    double vpY = imageView.getViewport().getMinY() / imageH;
    double wp = imageView.getViewport().getWidth() / imageW;
    double hp = imageView.getViewport().getHeight() / imageH;

    double diameter = -1;
    if (isMobile) {
      diameter = Math.max(dminMOBILE, dpercMOBILE * (1 - wp) * imageW);
    } else {
      diameter = Math.max(dmin, dperc * (1 - wp) * imageW);
    }

    // ------------------------------------

    double nxpA = nodeA.getXCoord() / imageW;
    double nypA = nodeA.getYCoord() / imageH;

    double circleXA = (((nxpA - vpX) / wp) * canvasW);
    double circleYA = (((nypA - vpY) / hp) * canvasH);

    double nxpB = nodeB.getXCoord() / imageW;
    double nypB = nodeB.getYCoord() / imageH;

    double circleXB = (((nxpB - vpX) / wp) * canvasW);
    double circleYB = (((nypB - vpY) / hp) * canvasH);

    gc.strokeLine(circleXA, circleYA, circleXB, circleYB);
  }

  /**
   * Draws the given Circle in the given color WITHOUT CLEARING CANVAS FIRST
   *
   * @param gc GraphicsContext on which to draw
   * @param circle circle (node) being drawn
   * @param paint Color.COLOR being drawn
   */
  public static void drawSingleNode(
      GraphicsContext gc, Node n, Paint paint, ImageView imageView, boolean isMobile) {
    gc.setGlobalAlpha(1.0);
    gc.setFill(paint);
    gc.setStroke(Color.BLACK);
    gc.setLineWidth(2.0);

    Canvas mapcanvas = gc.getCanvas();

    double canvasW = gc.getCanvas().getWidth();
    double canvasH = gc.getCanvas().getHeight();

    double imageW = imageView.getImage().getWidth();
    double imageH = imageView.getImage().getHeight();

    double vpX = imageView.getViewport().getMinX() / imageW;
    double vpY = imageView.getViewport().getMinY() / imageH;
    double wp = imageView.getViewport().getWidth() / imageW;
    double hp = imageView.getViewport().getHeight() / imageH;

    double nxp = n.getXCoord() / imageW;
    double nyp = n.getYCoord() / imageH;

    double diameter = -1;
    if (isMobile) {
      diameter = Math.max(dminMOBILE, dpercMOBILE * (1 - wp) * imageW);
    } else {
      diameter = Math.max(dmin, dperc * (1 - wp) * imageW);
    }

    double circleX = (((nxp - vpX) / wp) * canvasW) - diameter / 2;
    double circleY = (((nyp - vpY) / hp) * canvasH) - diameter / 2;

    gc.fillOval(circleX, circleY, diameter, diameter);
    gc.strokeOval(circleX, circleY, diameter, diameter);
  }
}
