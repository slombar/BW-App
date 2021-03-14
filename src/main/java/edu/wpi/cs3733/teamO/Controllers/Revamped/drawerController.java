package edu.wpi.cs3733.teamO.Controllers.Revamped;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.controls.JFXTextField;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class drawerController {
  public static JFXScrollPane directionsScrollPane = new JFXScrollPane();
  @FXML private static VBox directionsDisplayVbox = new VBox();
  @FXML private JFXTextField longName;
  @FXML private JFXCheckBox visible;
  @FXML private JFXTextField yCoord;
  @FXML private JFXTextField xCoord;
  @FXML private JFXTextField nodeType;

  public static void addDirectionChildren(List<String> directions) {

    for (String d : directions) {

      Text newText = new Text(d + "\n");
      newText.setFont(Font.font("Leelawadee UI", 16.0));

      directionsDisplayVbox.getChildren().add(newText);

      // add directions label to vbox
      // informationOnPage.getDirectionVBox().getChildren().add(newText);
      // informationOnPage.addDirectionChild(newText);
      // drawerController.addDirectionChild(newText);
    }

    directionsScrollPane.setContent(directionsDisplayVbox);
  }

  public VBox getDirectionVBox() {
    return directionsDisplayVbox;
  }

  public void share(ActionEvent actionEvent) {


  }

  public void saveNode(ActionEvent actionEvent) {}
}
