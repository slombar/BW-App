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

    // TODO when you hover over image, displays name and position
    /*
    Tooltip tooltip = new Tooltip();
    tooltip.setGraphic(new ImageView(selina));

    button.setTooltip(tooltip);
    Tooltip.install(selina, new Tooltip("Selina Spry, Project Manager"));

     */
  }
}
