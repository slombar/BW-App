package edu.wpi.cs3733.teamO.GraphSystem;

import edu.wpi.cs3733.teamO.model.Node;
import java.util.ArrayList;

public class TextDirection {

  public static double ZERO = 0.0;
  public static ArrayList<String> Directions = new ArrayList<>();

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
    if (v2_x == 0 && v2_y == 0) return 100;

    // return ZERO if dot_product is zero.
    if (angle == 0.0) return Math.abs(angle);

    // return RIGHT if cross product is positive
    if (cross_product > 0) return angle;

    // return LEFT if cross product is negative
    if (cross_product < 0) return angle * -1;

    // return ZERO if cross product is zero.
    return ZERO;
  }

  public static ArrayList<String> textDirections(ArrayList<Node> path) {

    Directions.add("Start from " + path.get(0).getLongName() + " to " + path.get(1).getLongName());

    for (int i = 0; i < path.size() - 2; i++) {
      double angle = direction(path.get(i), path.get(i + 1), path.get(i + 2));
      // to support floor.
      if (angle == 100) {
        Directions.add(
            "Walk towards "
                + path.get(i + 1).getLongName()
                + " and use "
                + path.get(i + 2).getLongName()
                + " to go to floor "
                + path.get(i + 2).getFloor());
      }
      if (angle < -25) {
        // Lefts
        if (angle < -120) {
          Directions.add(
              "Walk towards "
                  + path.get(i + 1).getLongName()
                  + " and turn around facing "
                  + path.get(i + 2).getLongName());

        } else if (angle < -60) {
          Directions.add(
              "Turn left from "
                  + path.get(i + 1).getLongName()
                  + " to "
                  + path.get(i + 2).getLongName());

        } else {
          Directions.add(
              "Bear left from "
                  + path.get(i + 1).getLongName()
                  + " to "
                  + path.get(i + 2).getLongName());
        }

      } else if (angle > 25) {
        // The Rights
        if (angle > 120) {
          Directions.add(
              "Walk towards "
                  + path.get(i + 1).getLongName()
                  + " and turn around facing "
                  + path.get(i + 2).getLongName());

        } else if (angle > 60) {
          Directions.add(
              "Turn right from "
                  + path.get(i + 1).getLongName()
                  + " to "
                  + path.get(i + 2).getLongName());

        } else {
          Directions.add(
              "Bear right from "
                  + path.get(i + 1).getLongName()
                  + " to "
                  + path.get(i + 2).getLongName());
        }
      } else {
        Directions.add(
            "Continue Straight from "
                + path.get(i + 1).getLongName()
                + " to "
                + path.get(i + 2).getLongName());
      }
    }

    Directions.add("You have reached your destination.");
    return Directions;
  }
}
