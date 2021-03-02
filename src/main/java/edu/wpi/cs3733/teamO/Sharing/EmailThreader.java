package edu.wpi.cs3733.teamO.Sharing;

import edu.wpi.cs3733.teamO.Database.UserHandling;
import java.io.File;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailThreader extends Thread {
  private final String sendingTo;
  private final String fileToBeSent;

  public EmailThreader(String sendingTo, String fileToBeSent) {
    this.sendingTo = sendingTo;
    this.fileToBeSent = fileToBeSent;
  }

  @Override
  public void run() {

    String to = sendingTo; // recipient's email
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
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(to)); // Set To: header
      message.setSubject("BW-App Pathfinding Route"); // Set Subject: header

      // PART 1 - BODY OF EMAIL ----------------------------------------------------------------
      BodyPart messageBodyPart = new MimeBodyPart();

      // Check if user is employee/admin/patient or guest

      if (UserHandling.getLoginStatus())
        messageBodyPart.setText(
            "Hello "
                + UserHandling.getFirstName()
                + "! Below is the image of your pathfinding route.");
      else messageBodyPart.setText("Hello! Below is the image of your pathfinding route.");

      // Create a multipart message + set text message part
      Multipart multipart = new MimeMultipart();
      multipart.addBodyPart(messageBodyPart);

      // PART 2 - ATTACHMENT -------------------------------------------------------------------
      messageBodyPart = new MimeBodyPart();
      String filename = fileToBeSent; // file to be sent, map image
      DataSource source = new FileDataSource(filename);
      messageBodyPart.setDataHandler(new DataHandler(source));
      messageBodyPart.setFileName("yourMapRoute.png");
      multipart.addBodyPart(messageBodyPart);

      // Send the complete message parts
      message.setContent(multipart);

      // Send Email
      Transport.send(message);
      System.out.println("Sent message successfully...");

    } catch (MessagingException mex) {
      mex.printStackTrace();
    }
  }
}
