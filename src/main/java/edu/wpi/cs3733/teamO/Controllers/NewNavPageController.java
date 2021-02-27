package edu.wpi.cs3733.teamO.Controllers;

import com.jfoenix.controls.JFXDrawer;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class NewNavPageController implements Initializable {

  @FXML private ImageView image;
  @FXML private BorderPane border;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    resize();
  }

  public BorderPane resize() {
    //    border.getStyleClass().add("bg-1");
    border.setPadding(new Insets(5));

    //    Image map = new Image("FaulknerCampus.png", 1200, 1400, true, true);
    //    ImageView image = new ImageView(map);
    image.fitWidthProperty().bind(border.heightProperty().multiply(1.2));
    image.fitHeightProperty().bind(border.heightProperty());
    BorderPane.setAlignment(image, Pos.TOP_CENTER);
    border.setCenter(image);

//    JFXDrawer drawerStack = new JFXDrawer();
//    drawerStack.setPrefWidth(75);
//    border.setRight(drawerStack);

    return border;
  }
}
