package edu.wpi.cs3733.teamO.GraphSystem;

import static edu.wpi.cs3733.teamO.GraphSystem.Graph.GRAPH;

import edu.wpi.cs3733.teamO.Model.Node;
import java.util.ArrayList;
import java.util.List;

public class TextDirection {

  public static double ZERO = 0.0;

  /**
   * get the direction angle ready for direction text.
   *
   * @param node1
   * @param node2
   * @param node3
   * @return
   */
  public static double direction(Node node1, Node node2, Node node3) {

    double v1_x = node2.getXCoord() - node1.getXCoord(); // 1
    double v1_y = node2.getYCoord() - node1.getYCoord(); // sqrt3

    double v2_x = node3.getXCoord() - node2.getXCoord(); // sqrt3
    double v2_y = node3.getYCoord() - node2.getYCoord(); // 1

    double dot_product = (v1_x * v2_x) + (v1_y * v2_y);

    double v1Mag = Math.sqrt(Math.pow(v1_x, 2) + Math.pow(v1_y, 2)); // 2
    double v2Mag = Math.sqrt(Math.pow(v2_x, 2) + Math.pow(v2_y, 2)); // 2
    double angle =
        (180 / Math.PI)
            * Math.acos(dot_product / (v1Mag * v2Mag)); // angle between using dot-product

    // Determining cross Product
    double cross_product =
        (node2.getXCoord() * node3.getYCoord() - node2.getYCoord() * node3.getXCoord());

    // for the different floors.
    if (node3.getLongName().equals(node2.getLongName())) {
      return 100.0;
    }

    // for the outdoor to .
    if (node3.getNodeType().equals("EXIT") && (node3.getNodeType().equals(node2.getNodeType()))) {
      return 200.0;
    }

    // return ZERO if dot_product is zero.
    if (angle == 0.0) return Math.abs(angle);

    // return RIGHT if cross product is positive
    if (cross_product > 0) return angle;

    // return LEFT if cross product is negative
    if (cross_product < 0) return (-1 * angle);

    // return ZERO if cross product is zero.
    return ZERO;
  }

  /**
   * calculate the distances between 2 nodes.
   *
   * @param node1
   * @param node2
   * @return
   */
  public static String realDistance(Node node1, Node node2) {
    double distance =
        Math.sqrt(
            Math.pow((node2.getXCoord() - node1.getXCoord()), 2)
                + Math.pow((node2.getYCoord() - node1.getYCoord()), 2));
    double ratio = 0.5;
    String strDouble = String.format("%.1f", distance * ratio);
    if (strDouble.equals("NaN")) {
      return "0.0";
    }
    return strDouble;
  }

  /**
   * text out the distance based on different angle in an arraylist.
   *
   * @return
   */
  public static ArrayList<String> textDirections() {
    List<Node> path = GRAPH.getPath();
    ArrayList<String> directions = new ArrayList<>();
    directions.add(
        "Start From "
            + path.get(0).getLongName()
            + " --(Walk Straight)--> "
            + path.get(1).getLongName()
            + " ("
            + realDistance(path.get(0), path.get(1))
            + " ft.)");

    for (int i = 0; i < path.size() - 2; i++) {
      double angle = direction(path.get(i), path.get(i + 1), path.get(i + 2));
      // to support floor.
      if (angle == 100) {
        directions.add(
            "Use "
                + path.get(i + 2).getLongName()
                + " to go to floor "
                + path.get(i + 2).getFloor()
                + " ("
                + realDistance(path.get(i + 1), path.get(i + 2))
                + " ft.)");
      }
      // enter the building or leave the building.
      if (angle == 200) {
        directions.add(
            "Use " + path.get(i + 1).getLongName() + " to enter " + path.get(i + 2).getLongName());
      } else if (angle < -25) {
        // Lefts
        if (angle < -120) {
          directions.add(
              path.get(i + 1).getLongName()
                  + " --(Turn Around)--> "
                  + path.get(i + 2).getLongName()
                  + " ("
                  + realDistance(path.get(i + 1), path.get(i + 2))
                  + " ft.)");

        } else if (angle < -60) {
          directions.add(
              path.get(i + 1).getLongName()
                  + " --(Turn Left)--> "
                  + path.get(i + 2).getLongName()
                  + " ("
                  + realDistance(path.get(i + 1), path.get(i + 2))
                  + " ft.)");

        } else {
          directions.add(
              path.get(i + 1).getLongName()
                  + " --(Slightly Left)--> "
                  + path.get(i + 2).getLongName()
                  + " ("
                  + realDistance(path.get(i + 1), path.get(i + 2))
                  + " ft.)");
        }

      } else if (angle > 25) {
        // The Rights
        if (angle > 120) {
          directions.add(
              path.get(i + 1).getLongName()
                  + " --(Turn Around)--> "
                  + path.get(i + 2).getLongName()
                  + " ("
                  + realDistance(path.get(i + 1), path.get(i + 2))
                  + " ft.)");

        } else if (angle > 60) {
          directions.add(
              path.get(i + 1).getLongName()
                  + " --(Turn Right)--> "
                  + path.get(i + 2).getLongName()
                  + " ("
                  + realDistance(path.get(i + 1), path.get(i + 2))
                  + " ft.)");

        } else {
          directions.add(
              path.get(i + 1).getLongName()
                  + " --(Slightly Right)--> "
                  + path.get(i + 2).getLongName()
                  + " ("
                  + realDistance(path.get(i + 1), path.get(i + 2))
                  + " ft.)");
        }
      } else {
        directions.add(
            path.get(i + 1).getLongName()
                + " --(Walk Straight)--> "
                + path.get(i + 2).getLongName()
                + " ("
                + realDistance(path.get(i + 1), path.get(i + 2))
                + " ft.)");
      }
    }

    directions.add("You have reached your destination.");
    return directions;
  }
}
