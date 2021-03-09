package edu.wpi.cs3733.teamO.Controllers.GoogleMaps;

import com.google.maps.model.DirectionsStep;
import java.io.File;
import java.util.ArrayList;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class GoogleEmailThreader extends Thread {

  private ArrayList<DirectionsStep> directions = null;
  private String url = null;
  private final String sendingTo;

  public GoogleEmailThreader(String sendingTo, ArrayList<DirectionsStep> directions, String url) {
    this.url = url;
    this.directions = directions;
    this.sendingTo = sendingTo;
  }

  public GoogleEmailThreader(String sendingTo, ArrayList<DirectionsStep> directions) {
    this.directions = directions;
    this.sendingTo = sendingTo;
  }

  public GoogleEmailThreader(String sendingTo, String url) {
    this.url = url;
    this.sendingTo = sendingTo;
  }

  public void run() {
    String from = "bwolive3733@gmail.com"; // sender's email, need default sender email !
    String host = "smtp.gmail.com"; // where email is being sent from

    Properties properties = System.getProperties(); // set up mail server
    properties.put("mail.smtp.host", host);
    properties.put("mail.smtp.port", "465");
    properties.put("mail.smtp.ssl.enable", "true");
    properties.put("mail.smtp.auth", "true");

    String home = System.getProperty("user.home");
    File outputFile = new File(home + "/Downloads/" + "mapimg.png");

    Session session =
        Session.getInstance(
            properties,
            new javax.mail.Authenticator() {
              protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(from, "oliveopossum69");
              }
            });

    // Session session = Session.getDefaultInstance(properties);

    try {

      MimeMessage message = new MimeMessage(session); // Create a default MimeMessage object

      // Setting Email Headers
      message.setFrom(new InternetAddress(from)); // Set From: header
      message.addRecipient(
          Message.RecipientType.TO, new InternetAddress(sendingTo)); // Set To: header
      message.setSubject("Route to Brigham and Women's Hospital"); // Set Subject: header

      // PART 1 - BODY OF EMAIL
      // ----------------------------------------------------------------

      //////////////////////////////////////////////////////////////////////////////////////

      StringBuilder tempMessage =
          new StringBuilder(
              "<!DOCTYPE html>\n" + "<html>\n" + "<body>\n" + "Here are your directions:\n ");

      if (url != null) {
        tempMessage.append(url).append("\n");
        System.out.println("sending url");
      }

      if (directions != null) {
        for (DirectionsStep d : directions) {
          tempMessage.append(d.htmlInstructions).append("\n");
          System.out.println("sending directions");
        }
      }

      tempMessage.append("</body>\n" + "</html>\n");
      ////////////////////////////////////////////////////////////////////////////////////////////////

      // Send the complete message parts
      message.setContent(tempMessage.toString(), "text/html");

      // Send Email
      Transport.send(message);
      System.out.println("Sent message successfully...");
    } catch (MessagingException mex) {
      mex.printStackTrace();
    }
  }
}
