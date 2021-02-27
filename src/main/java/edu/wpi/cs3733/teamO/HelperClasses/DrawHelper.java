package edu.wpi.cs3733.teamO.HelperClasses;

import edu.wpi.cs3733.teamO.model.Node;
import java.util.HashMap;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

// class that SOLEY draws shit
public class DrawHelper {

  private static GraphicsContext gc;

  public static void drawNodeCircles(
      Canvas mapcanvas, HashMap<Node, Circle> cmap, ObservableList<Node> nodeList) {
    gc = mapcanvas.getGraphicsContext2D();
    double diameter = 10;
    int x = 0;
    int y = 0;
    gc.clearRect(0, 0, mapcanvas.getWidth(), mapcanvas.getHeight());
    double scaleX = 2989 / mapcanvas.getWidth();
    double scaleY = 2457 / mapcanvas.getHeight();
    Circle tempCir = new Circle();

    // circle widths:
    // double cW = 10.0;
    // TODO: (x,y) should already adjust when scrolling, but probably should also change radius

    // for each node in the DB, add their circle to the map
    for (Node n : nodeList) {
      tempCir = cmap.get(n);

      gc.fillOval(tempCir.getCenterX(), tempCir.getCenterY(), diameter, diameter);
      gc.setFill(Color.PAPAYAWHIP);
    }
  }

  public static void drawMidArrow(double ax, double ay, double bx, double by) {
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
