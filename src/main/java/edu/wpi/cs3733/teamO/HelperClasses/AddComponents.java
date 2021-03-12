package edu.wpi.cs3733.teamO.HelperClasses;

import com.jfoenix.controls.JFXTextField;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class AddComponents {

  public static ArrayList<JFXTextField> test;
  public static VBox test2;
  public static HBox test3;

  /**
   * @param labels
   * @return
   */
  public static ArrayList<JFXTextField> createField(ArrayList<String> labels) {
    return test;
  }

  /** @return */
  public static VBox addToVBox() {
    return test2;
  }

  /**
   * description of method
   *
   * @return
   */
  public static HBox addToHBox() {
    return test3;
  }

  public static void dateAndTime(Label label) {
    Timeline clock =
        new Timeline(
            new KeyFrame(
                Duration.ZERO,
                e -> {
                  DateTimeFormatter formatter =
                      DateTimeFormatter.ofPattern("EEE. LLL. dd, yyyy hh:mm:ss a");
                  label.setText(LocalDateTime.now().format(formatter));
                }),
            new KeyFrame(Duration.seconds(1)));
    clock.setCycleCount(Animation.INDEFINITE);
    clock.play();
  }
}
