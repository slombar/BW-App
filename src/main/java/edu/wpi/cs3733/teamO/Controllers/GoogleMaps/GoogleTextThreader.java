package edu.wpi.cs3733.teamO.Controllers.GoogleMaps;

import com.google.maps.model.DirectionsStep;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import edu.wpi.cs3733.teamO.Maps.Directions;
import java.util.ArrayList;

public class GoogleTextThreader extends Thread {

  private ArrayList<DirectionsStep> directions = null;
  private String url = null;
  private final String sendingTo; // = "+16176061459"

  public GoogleTextThreader(String sendingTo, ArrayList<DirectionsStep> directions, String url) {
    this.directions = directions;
    this.url = url;
    if (sendingTo.charAt(0) != '+') {
      this.sendingTo = "+" + sendingTo;
    } else {
      this.sendingTo = sendingTo;
    }
  }

  public GoogleTextThreader(String sendingTo, ArrayList<DirectionsStep> directions) {
    this.directions = directions;
    if (sendingTo.charAt(0) != '+') {
      this.sendingTo = "+" + sendingTo;
    } else {
      this.sendingTo = sendingTo;
    }
  }

  public GoogleTextThreader(String sendingTo, String url) {
    this.url = url;
    if (sendingTo.charAt(0) != '+') {
      this.sendingTo = "+" + sendingTo;
    } else {
      this.sendingTo = sendingTo;
    }
  }

  public void run() {
    String ACCOUNT_SID = "ACccaa37332a0f79e457bfcb6f393b25e8";
    String AUTH_TOKEN = "29c1347315b388cb034d93e5aff4685f";

    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

    StringBuilder tempMessage = new StringBuilder("Here are your directions:\n");

    //////////////////////////////////////////////////////////////////////////////////////

    if (url != null) {
      tempMessage.append(url).append("\n");
    }

    if (directions != null) {
      for (DirectionsStep d : directions) {
        tempMessage.append(Directions.html2text(d.htmlInstructions)).append("\n");
      }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    Message message =
        Message.creator(
                new com.twilio.type.PhoneNumber(
                    sendingTo), // if testing, please use verified number. will not work otherwise
                new com.twilio.type.PhoneNumber("+16173560972"),
                tempMessage.toString())
            .create();

    System.out.println(message.getSid());
  }
}
