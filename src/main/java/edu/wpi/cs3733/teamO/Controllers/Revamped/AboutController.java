package edu.wpi.cs3733.teamO.Controllers.Revamped;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class AboutController implements Initializable {
  @FXML private Label teamCoaches;
  @FXML private ImageView selina;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    teamCoaches.setWrapText(true);
    selina.addEventHandler(
        javafx.scene.input.MouseEvent.MOUSE_MOVED,
        event -> {
          System.out.println("Tile pressed ");

          event.consume();
        });
    // TODO when you hover over image, displays name and position
    /*

    Tooltip tooltip = new Tooltip();
            tooltip.setGraphic(new ImageView("@../../TeamOPictures/Selina.PNG"));
              setTooltip(tooltip);
              Tooltip.install(selina, new Tooltip("Selina Spry, Project Manager"));


       */
  }
}
