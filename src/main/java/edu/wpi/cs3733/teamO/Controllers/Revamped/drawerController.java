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

public class drawerController {
  public JFXScrollPane directionsScrollPane;
  @FXML private VBox directionsDisplayVbox;
  @FXML private JFXTextField longName;
  @FXML private JFXCheckBox visible;
  @FXML private JFXTextField yCoord;
  @FXML private JFXTextField xCoord;
  @FXML private JFXTextField nodeType;

  public void addDirectionChildren(List<String> directions) {

    for (String d : directions) {

      Label newText = new Label(d);
      newText.setFont(Font.font("Leelawadee UI", 16.0));

      directionsDisplayVbox.getChildren().add(newText);

      // add directions label to vbox
      // informationOnPage.getDirectionVBox().getChildren().add(newText);
      // informationOnPage.addDirectionChild(newText);
      // drawerController.addDirectionChild(newText);
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
