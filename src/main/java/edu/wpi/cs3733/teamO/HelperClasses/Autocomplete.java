package edu.wpi.cs3733.teamO.HelperClasses;

import com.jfoenix.controls.JFXAutoCompletePopup;
import com.jfoenix.controls.JFXTextField;
import java.util.ArrayList;

public class Autocomplete {
  /**
   * Autocompletes textfields based on an ArrayList<String>
   *
   * @param list
   * @param textfield
   */
  public static void autoComplete(ArrayList<String> list, JFXTextField textfield) {
    JFXAutoCompletePopup<String> autoComplete = new JFXAutoCompletePopup<>();
    autoComplete.getSuggestions().addAll(list);

    autoComplete.setSelectionHandler(
        event -> {
          textfield.setText(event.getObject());
        });

    // Filtering the list given
    textfield
        .textProperty()
        .addListener(
            observable -> {
              autoComplete.filter(
                  string -> string.toLowerCase().contains(textfield.getText().toLowerCase()));
              if (autoComplete.getFilteredSuggestions().isEmpty()
                  || textfield.getText().isEmpty()) {
                autoComplete.hide();
              } else {
                autoComplete.show(textfield);
              }
            });
  }
}
