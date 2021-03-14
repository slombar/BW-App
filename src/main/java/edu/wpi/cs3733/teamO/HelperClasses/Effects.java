package edu.wpi.cs3733.teamO.HelperClasses;

import com.jfoenix.controls.JFXButton;
import javafx.beans.binding.Bindings;

public class Effects {

  /**
   * hovering over a button will make the button darker
   *
   * @param button
   */
  public static void hoverEffect(JFXButton button) {
    button
        .styleProperty()
        .bind(
            Bindings.when(button.hoverProperty())
                .then("-fx-background-color: #243442")
                .otherwise("-fx-background-color: #3a5369"));
  }

  /**
   * hovering over a button will make the button darker
   *
   * @param button
   */
  public static void hoverEffectGray(JFXButton button) {
    button
        .styleProperty()
        .bind(
            Bindings.when(button.hoverProperty())
                .then("-fx-background-color: #84909c")
                .otherwise("-fx-background-color: #9AA8B5"));
  }
}
