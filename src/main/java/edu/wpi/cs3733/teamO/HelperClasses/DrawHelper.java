package edu.wpi.cs3733.teamO.HelperClasses;

import edu.wpi.cs3733.teamO.Database.Graph;
import edu.wpi.cs3733.teamO.model.Node;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class DrawHelper {

  private final double campusWidth = 2989;
  private final double campusHeight = 2457;
  private final double buildingWidth = 2476;
  private final double buildingHeight = 1486;


  private Hashtable<Node, Circle> nodeCircleHashtable;
  Graph graph;

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

  public void drawNodes(GraphicsContext gc, ArrayList<Node> nodeList) {

    Canvas mapcanvas = gc.getCanvas();
    gc.clearRect(0, 0, mapcanvas.getWidth(), mapcanvas.getHeight());

    Circle tempCir = new Circle();

    // for each node in the DB, add their circle to the map
    for (Node n : nodeList) {
      gc.setGlobalAlpha(1.0);
      gc.setFill(Color.YELLOW);
      gc.setStroke(Color.BLACK);
      gc.setLineWidth(2.0);

      tempCir = nodeCircleHashtable.get(n);
      double tempCirX = tempCir.getCenterX() - tempCir.getRadius();
      double tempCirY = tempCir.getCenterY() - tempCir.getRadius();
      double diameter = 2 * tempCir.getRadius();

      if (n.getNodeType().equals("STAI")) {
        gc.setGlobalAlpha(0.25);
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.GREEN);
      } else if (n.getNodeType().equals("ELEV")) {
        gc.setGlobalAlpha(0.25);
        gc.setFill(Color.PURPLE);
        gc.setStroke(Color.PURPLE);
      }

      if (n.getNodeType().equals("EXIT")) {
        gc.setFill(Color.ORANGE);
      }

      if (!n.isVisible()) {
        gc.setFill(Color.TRANSPARENT);
      }

      gc.fillOval(tempCirX, tempCirY, diameter, diameter);
      gc.setGlobalAlpha(1.0);
      gc.strokeOval(tempCirX, tempCirY, diameter, diameter);
    }
  }

  public void createCircles() {
    graph = Graph.getInstance();
    nodeCircleHashtable = new Hashtable<>();

    int radius = 5;

    for (Node n : graph.listOfNodes) {
      // get node's x and y (and floor)
      double nX = n.getXCoord();
      double nY = n.getYCoord();
      String nFloor = n.getFloor();

      Circle c = new Circle();
      c.setCenterX(nX);
      c.setCenterY(nY);
      c.setRadius(radius);

      nodeCircleHashtable.put(n, c);
    }
  }

  public void drawVisibleNodes(GraphicsContext gc, String floor) {
    ArrayList<Node> visibleNodes = new ArrayList<>();

    for (Node n : graph.listOfNodes) {
      if (n.isVisible() && n.getFloor().equals(floor)) {
        visibleNodes.add(n);
      }
    }

    drawNodes(gc, visibleNodes);
  }

  public static void drawSingleEdge(GraphicsContext gc, Node circleA, Node circleB) {
    gc.setLineWidth(3.0);
    gc.strokeLine(
        circleA.getXCoord(), circleA.getYCoord(), circleB.getXCoord(), circleB.getYCoord());
  }

  public void drawEdges(GraphicsContext gc, String floor) {
    gc.setStroke(Color.BLACK);
    for (Node n : graph.listOfNodes) {
      try {

        Node nodeA = n;
        Node nodeB;
        List<Node> neighborList = graph.map.get(n);

        for (Node neighbor : neighborList) {
          nodeB = neighbor;

          // if they are on the same, current floor
          if (nodeA.getFloor().equals(floor) && nodeB.getFloor().equals(floor)) {

            drawSingleEdge(gc, nodeA, nodeB);
          }
        }

      } catch (NullPointerException ignored) {
        // TODO: use this catch block to filter out bad/extraneous data
        // for now it just ignores them and draws the edges that do actually exist
      }
    }
  }

  /**
   * Draws the current path stored by this Graph
   *
   * @param floor selected floor
   */
  public void drawPath(GraphicsContext gc, ArrayList<Node> path, String floor) {
    Canvas canvas = gc.getCanvas();
    // clears the canvas
    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

    // for each node in the path, draw on canvas
    for (int x = 0; x < path.size(); x++) {
      Node n = path.get(x);
      // if the current node is on the floor, and its t
      if (n.getNodeType().equals("STAI") && n.getFloor().equals(floor)) {
        Circle c = nodeCircleHashtable.get(n);

        DrawHelper.drawSingleNode(gc, c, Color.GREEN);
      } else if (n.getNodeType().equals("ELEV") && n.getFloor().equals(floor)) {
        Circle c = nodeCircleHashtable.get(n);

        DrawHelper.drawSingleNode(gc, c, Color.PURPLE);
      }

      if (n.getNodeType().equals("EXIT") && n.getFloor().equals(floor)) {
        Circle c = nodeCircleHashtable.get(n);
        DrawHelper.drawSingleNode(gc, c, Color.ORANGE);
      }

      if (x < path.size() - 1) {
        drawSingleEdge(gc, n, path.get(x + 1));
      }
    }

    // Coloring for nodes start and end
    if (path.get(0).getFloor().equals(floor)) {
      Circle c = nodeCircleHashtable.get(path.get(0));
      DrawHelper.drawSingleNode(gc, c, Color.BLUE);
    }
    if (path.get(path.size() - 1).getFloor().equals(floor)) {
      Circle c = nodeCircleHashtable.get(path.get(path.size() - 1));
      DrawHelper.drawSingleNode(gc, c, Color.RED);
    }
  }
}
