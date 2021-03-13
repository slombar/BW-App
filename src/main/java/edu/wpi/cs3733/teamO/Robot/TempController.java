package edu.wpi.cs3733.teamO.Robot;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import me.aflak.arduino.Arduino;

public class TempController extends Thread implements Initializable {

  public Label tempLabel;
  private static String port = (new SPortScan()).getActivePort(); // <--- get the port
  // create connection to arduino
  private static Arduino ArduinoCon = new Arduino(port, 9600);

  // create connection to adruino, check the com port in adruino IDE and paste it instead "COM8"
  private static Arduino AdruinoCon = new Arduino("COM5", 9600);

  public void retry(ActionEvent actionEvent) {
    // AdruinoCon.serialWrite(this.commands[commandIndex]); // pick a command from an array and send
    // it to USB
    String arduinoOutput = AdruinoCon.serialRead();
    int count = 0;
    for (char c : arduinoOutput.toCharArray()) {
      System.out.println("Character " + count + " " + c);
      count++;
    }
  }

  public void submit(ActionEvent actionEvent) {
    AdruinoCon.closeConnection();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    AdruinoCon.openConnection();
    // whenever connection is estabilished the adruino is restarted, we need to wait for it
    // otherwise it wont listen
    try {
      Thread.sleep(4000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
