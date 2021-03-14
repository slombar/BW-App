package edu.wpi.cs3733.teamO.Robot;

import static java.lang.System.out;
import static java.util.Arrays.asList;
import static jssc.SerialPort.*;

import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

/**
 * Packt Book: JavaFX 8 essentials Chapter 7. Monitoring & Controlling Arduino with JavaFX
 *
 * @author Mohamed Taman
 */
public final class Serial {
  /* List of usual serial ports. Add more or remove those you don't need */

  private static final List<String> USUAL_PORTS =
      asList(
          "/dev/tty.usbmodem",
          "/dev/tty.usbserial", // Mac OS X
          "/dev/usbdev",
          "/dev/ttyUSB",
          "/dev/ttyACM",
          "/dev/serial", // Linux
          // "COM3",
          // "COM4",
          "COM5",
          "COM6" // Windows
          );
  private final String ardPort;
  private SerialPort serPort;
  private StringBuilder sb = new StringBuilder();
  private final StringProperty line = new SimpleStringProperty("");

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
    asList(SerialPortList.getPortNames()).stream()
        .filter(
            name ->
                ((!ardPort.isEmpty() && name.equals(ardPort))
                    || (ardPort.isEmpty()
                        && USUAL_PORTS.stream().anyMatch(p -> name.startsWith(p)))))
        .findFirst()
        .ifPresent(
            name -> {
              try {
                serPort = new SerialPort(name);
                out.println("Connecting to " + serPort.getPortName());
                if (serPort.openPort()) {
                  out.println("Before Params");
                  serPort.setParams(BAUDRATE_9600, DATABITS_8, STOPBITS_1, PARITY_NONE);
                  out.println("after Params");
                  serPort.setEventsMask(MASK_RXCHAR);
                  out.println("after Set events");
                  serPort.addEventListener(
                      event -> {
                        if (event.isRXCHAR()) {
                          try {
                            sb.append(serPort.readString(event.getEventValue()));
                            String ch = sb.toString();
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
                out.println("ERROR: Port '" + name + "': " + ex.toString());
              }
            });
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
