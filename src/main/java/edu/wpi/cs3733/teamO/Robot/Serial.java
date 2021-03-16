package edu.wpi.cs3733.teamO.Robot;

import static java.lang.System.out;
import static java.util.Arrays.asList;
import static jssc.SerialPort.*;

import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import jssc.SerialPort;
import jssc.SerialPortException;

/**
 * Packt Book: JavaFX 8 essentials Chapter 7. Monitoring & Controlling Arduino with JavaFX
 *
 * @author Mohamed Taman
 */
public final class Serial {
  /* List of usual serial ports. Add more or remove those you don't need */

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

    try {
      serPort = new SerialPort("COM5");
      out.println("Connecting to " + serPort.getPortName());

      if (serPort.openPort()) {
        out.println("Before Params");
        serPort.setParams(BAUDRATE_9600, DATABITS_8, STOPBITS_1, PARITY_NONE);
        out.println("after Params");
        serPort.setEventsMask(MASK_RXCHAR);
        out.println("after Set events");
        startTime = System.currentTimeMillis();
        serPort.addEventListener(
            event -> {
              if (event.isRXCHAR()) {
                try {
                  sb.append(serPort.readString(event.getEventValue()));
                  String ch = sb.toString();
                  out.println(ch);
                  if (ch.equals("1")) {
                    disconnect();
                    String MenuUrl = "/Views/CovidSurvey.fxml";
                    SwitchScene.goToParent(MenuUrl);
                  }

                  if ((startTime - System.currentTimeMillis()) > 3000) {
                    disconnect();
                    String MenuUrl = "/Views/AboutPage.fxml";
                    SwitchScene.goToParent(MenuUrl);
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
