package edu.wpi.cs3733.teamO.Robot;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.out;
import static java.util.Arrays.asList;
import static jssc.SerialPort.*;

import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import jssc.SerialPort;
import jssc.SerialPortException;

public final class Serial {

  public static String str = "";

  private static final List<String> USUAL_PORTS =
      asList(
          // "COM3",
          // "COM4",
          "COM5", "COM6" // Windows
          );
  private final String ardPort;
  private SerialPort serPort;
  private StringBuilder sb = new StringBuilder();
  private final StringProperty line = new SimpleStringProperty("");
  private long startTime;
  private int feverCounter = 0;
  private int noFeverCounter = 0;
  private int undetectedCounter = 0;

  public Serial() {
    ardPort = "";
  }

  public Serial(String port) {
    ardPort = port;
  }

  /*
   * connect() looks for a valid serial port with an Arduino board connected.
   * If it is found, it's opened and a listener is added, so every tim
   * a line is returned, the stringProperty is set with that line.
   * For that, a StringBuilder is used to store the chars and extract the line
   * content whenever '\r\n' is found.
   */
  public boolean connect() {
    out.println("Serial port is opening now...");
    startTime = System.currentTimeMillis();

    try {
      serPort = new SerialPort("COM5");
      out.println("Connecting to " + serPort.getPortName());

      if (serPort.openPort()) {
        out.println("Before Params");
        serPort.setParams(BAUDRATE_9600, DATABITS_8, STOPBITS_1, PARITY_NONE);
        out.println("after Params");
        serPort.setEventsMask(MASK_RXCHAR);
        out.println("after Set events");
        startTime = currentTimeMillis();
        serPort.addEventListener(
            event -> {
              if (event.isRXCHAR()) {
                try {
                  sb.append(serPort.readString(event.getEventValue()));
                  String ch = sb.toString();
                  out.println(ch);

                  // Checks for Crit Temp
                  if (ch.contains("0")) {
                    // Reads a value for a healthy temp
                    SwitchScene.goToParent("/Views/ROBOT/NoFever.fxml");
                    disconnect();
                  } else if (ch.contains("1")) {
                    // Reads a value of a fever
                    SwitchScene.goToParent("/Views/ROBOT/Fever.fxml");
                    disconnect();
                  } else if (ch.contains("2")) {
                    // Reads a value of a fever
                    SwitchScene.goToParent("/Views/ROBOT/Undetected.fxml");
                    disconnect();
                  }

                  if (ch.endsWith("\r\n")) {

                    line.set(ch.substring(0, ch.indexOf("\r\n")));
                    sb = new StringBuilder();
                  }
                } catch (SerialPortException e) {
                  out.println("SerialEvent error:" + e.toString());
                }
              }
            });
      }
    } catch (SerialPortException ex) {
      out.println("ERROR: Port '" + serPort.getPortName() + "': " + ex.toString());
    }
    return serPort != null;
  }

  /** Disconnects the arduino from the serial and stops reading data coming from the Arduino */
  public void disconnect() {
    if (serPort != null) {
      try {
        serPort.removeEventListener();
        if (serPort.isOpened()) {
          serPort.closePort();
        }
      } catch (SerialPortException ex) {
        out.println("ERROR closing port exception: " + ex.toString());
      }
      out.println("Disconnecting: comm port closed.");
    }
  }

  public StringProperty getLine() {
    return line;
  }

  public String getPortName() {
    return serPort != null ? serPort.getPortName() : "";
  }

  public static void main(String args[]) {
    Serial sPort = new Serial();
    sPort.connect();

    sPort.disconnect();
  }
}
