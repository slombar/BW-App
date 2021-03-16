package edu.wpi.cs3733.teamO.Robot;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;

public class UndetectedController {
  private final Serial serial = new Serial();
  private final BooleanProperty connection = new SimpleBooleanProperty(false);

  private void connectArduino() {
    // Connect to Arduino port and start listening
    if (!connection.get()) {
      serial.connect();
      connection.set(!serial.getPortName().isEmpty());
    }
  }

  public void recheckTemp(ActionEvent actionEvent) {
    connectArduino();
  }
}
