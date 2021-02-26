package edu.wpi.cs3733.teamO.HelperClasses;

import edu.wpi.cs3733.teamO.model.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Set;

public class DrawHelper {

  /**
   * Draws a Circle with click and drag event handlers for the given nodeList on the given
   * @param gc
   * @param mapcanvas
   * @param nodeList
   */
  public static void drawNodeCircles(GraphicsContext gc, Canvas mapcanvas, Set<Node> nodeList) {
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
      circle.addEventHandler("click", clicked);
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
}
