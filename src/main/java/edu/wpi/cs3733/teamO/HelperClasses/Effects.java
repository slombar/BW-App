package edu.wpi.cs3733.teamO.HelperClasses;

import com.jfoenix.controls.JFXButton;
import javafx.beans.binding.Bindings;

public class Effects {
  public static void hoverEffect(JFXButton button) {
    button
        .styleProperty()
        .bind(
            Bindings.when(button.hoverProperty())
                .then("-fx-background-color: #243442")
                .otherwise("-fx-background-color: #3a5369"));
  }
}
