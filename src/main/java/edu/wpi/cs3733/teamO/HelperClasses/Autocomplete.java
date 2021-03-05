package edu.wpi.cs3733.teamO.HelperClasses;

import com.jfoenix.controls.JFXAutoCompletePopup;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.teamO.Database.NodesAndEdges;
import edu.wpi.cs3733.teamO.model.Node;
import java.util.ArrayList;
import javafx.collections.ObservableList;

public class Autocomplete {
  /**
   * Autocompletes textfields based on an ArrayList<String>
   *
   * @param list
   * @param textfield
   */
  public static void autoComplete(ArrayList<String> list, JFXTextField textfield) {
    JFXAutoCompletePopup<String> autoComplete = new JFXAutoCompletePopup<>();
    autoComplete.getSuggestions().addAll(list);

    autoComplete.setSelectionHandler(
        event -> {
          textfield.setText(event.getObject());
        });

    // Filtering the list given
    textfield
        .textProperty()
        .addListener(
            observable -> {
              autoComplete.filter(
                  string -> string.toLowerCase().contains(textfield.getText().toLowerCase()));
              if (autoComplete.getFilteredSuggestions().isEmpty()
                  || textfield.getText().isEmpty()) {
                autoComplete.hide();
              } else {
                autoComplete.show(textfield);
              }
            });
  }

  /**
   * gets all the node data for a specific field adn can be used to autocomplete textfields
   *
   * @param nodeProperty a string that identifies the specific node detail
   * @return an array list of string of all the node's specific property
   */
  public static ArrayList<String> autoNodeData(String nodeProperty) {
    ObservableList<Node> nodeList;
    nodeList = NodesAndEdges.getAllNodes();

    ArrayList<String> data = new ArrayList<>();
    switch (nodeProperty) {
      case "nodeID":
        for (Node node : nodeList) {
          data.add(node.getID());
        }
        break;
      case "xCoord":
        for (Node node : nodeList) {
          data.add(String.valueOf(node.getXCoord()));
        }
        break;
      case "yCoord":
        for (Node node : nodeList) {
          data.add(String.valueOf(node.getYCoord()));
        }
        break;
      case "floor":
        for (Node node : nodeList) {
          data.add(node.getFloor());
        }
        break;
      case "building":
        for (Node node : nodeList) {
          data.add(node.getBuilding());
        }
        break;
      case "nodeType":
        for (Node node : nodeList) {
          data.add(node.getNodeType());
        }
        break;
      case "longName":
        for (Node node : nodeList) {
          data.add(node.getLongName());
        }
        break;
      case "shortName":
        for (Node node : nodeList) {
          data.add(node.getShortName());
        }
        break;
    }
    return data;
  }

  /**
   * checks if the given text fields are empty
   *
   * @param textFields
   * @return
   */
  public static boolean areFieldsEmpty(ArrayList<JFXTextField> textFields) {
    for (JFXTextField text : textFields) {
      if (text.getText().isEmpty()) {
        return true;
      }
    }
    return false;
  }

  /**
   * checks if the given text fields are null
   *
   * @param textFields
   * @return
   */
  public static boolean areFieldsNull(ArrayList<JFXTextField> textFields) {
    for (JFXTextField text : textFields) {
      if (text.getText() == null) {
        return true;
      }
    }
    return false;
  }

  /**
   * clear all textfields
   *
   * @param textFields
   * @return
   */
  public static void clearAllFields(ArrayList<JFXTextField> textFields) {
    for (JFXTextField text : textFields) {
      text.clear();
    }
  }
}
