package edu.wpi.cs3733.teamO.HelperClasses;

import com.jfoenix.controls.JFXTextField;
import java.util.ArrayList;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
}
