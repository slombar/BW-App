package edu.wpi.cs3733.teamO.Controllers.Revamped;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.controls.JFXTextField;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class DrawerController {
  public JFXScrollPane directionsScrollPane;
  @FXML private VBox directionsDisplayVbox;
  public JFXTextField longName;
  public JFXCheckBox visible;
  public JFXTextField yCoord;
  public JFXTextField xCoord;
  public JFXTextField nodeType;
  public JFXTextField building;
  public JFXTextField shortName;

  public void addDirectionChildren(List<String> directions) {

    for (String d : directions) {

      Label newText = new Label(d);
      newText.setFont(Font.font("Leelawadee UI", 16.0));

      directionsDisplayVbox.getChildren().add(newText);

      // add directions label to vbox
      // informationOnPage.getDirectionVBox().getChildren().add(newText);
      // informationOnPage.addDirectionChild(newText);
      // DrawerController.addDirectionChild(newText);
    }
  }

  public void removeDirectionChildren() {
    directionsDisplayVbox.getChildren().clear();
  }

  public VBox getDirectionVBox() {
    return directionsDisplayVbox;
  }

  public void share(ActionEvent actionEvent) {}

  public void saveNode(ActionEvent actionEvent) {}
}
