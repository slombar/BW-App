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
  private final String fileToBeSent1;
  private final String fileToBeSent2;
  private final String fileToBeSent3;
  private final String fileToBeSent4;
  private final String fileToBeSent5;
  private final String fileToBeSent6;

  public EmailThreader(String sendingTo, String fileToBeSent1, String fileToBeSent2, String fileToBeSent3, String fileToBeSent4, String fileToBeSent5, String fileToBeSent6) {
    this.sendingTo = sendingTo;
    this.fileToBeSent1 = fileToBeSent1;
    this.fileToBeSent2 = fileToBeSent2;
    this.fileToBeSent3 = fileToBeSent3;
    this.fileToBeSent4 = fileToBeSent4;
    this.fileToBeSent5 = fileToBeSent5;
    this.fileToBeSent6 = fileToBeSent6;
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
      BodyPart messageBodyPart1 = new MimeBodyPart();
      BodyPart messageBodyPart2 = new MimeBodyPart();
      BodyPart messageBodyPart3 = new MimeBodyPart();
      BodyPart messageBodyPart4 = new MimeBodyPart();
      BodyPart messageBodyPart5 = new MimeBodyPart();
      BodyPart messageBodyPart6 = new MimeBodyPart();
      BodyPart messageBodyPart0 = new MimeBodyPart();

      // Check if user is employee/admin/patient or guest

      if (UserHandling.getLoginStatus())
        messageBodyPart0.setText(
            "Hello "
                + UserHandling.getFirstName()
                + "! Below is the image of your pathfinding route.");
      else messageBodyPart0.setText("Hello! Below is the image of your pathfinding route.");

      // Create a multipart message + set text message part
      Multipart multipart = new MimeMultipart();
      multipart.addBodyPart(messageBodyPart0);

      // PART 2 - ATTACHMENT -------------------------------------------------------------------
      String filename1 = fileToBeSent1; // file to be sent, map image
      String filename2 = fileToBeSent2; // file to be sent, map image
      String filename3 = fileToBeSent3; // file to be sent, map image
      String filename4 = fileToBeSent4; // file to be sent, map image
      String filename5 = fileToBeSent5; // file to be sent, map image
      String filename6 = fileToBeSent6; // file to be sent, map image
      DataSource source1 = new FileDataSource(filename1);
      messageBodyPart1.setDataHandler(new DataHandler(source1));
      messageBodyPart1.setFileName("GroundRoute.png");
      multipart.addBodyPart(messageBodyPart1);
      DataSource source2 = new FileDataSource(filename2);
      messageBodyPart2.setDataHandler(new DataHandler(source2));
      messageBodyPart2.setFileName("Floor1Route.png");
      multipart.addBodyPart(messageBodyPart2);
      DataSource source3 = new FileDataSource(filename3);
      messageBodyPart3.setDataHandler(new DataHandler(source3));
      messageBodyPart3.setFileName("Floor2Route.png");
      multipart.addBodyPart(messageBodyPart3);
      DataSource source4 = new FileDataSource(filename4);
      messageBodyPart4.setDataHandler(new DataHandler(source4));
      messageBodyPart4.setFileName("Floor3Route.png");
      multipart.addBodyPart(messageBodyPart4);
      DataSource source5 = new FileDataSource(filename5);
      messageBodyPart5.setDataHandler(new DataHandler(source5));
      messageBodyPart5.setFileName("Floor4Route.png");
      multipart.addBodyPart(messageBodyPart5);
      DataSource source6 = new FileDataSource(filename6);
      messageBodyPart6.setDataHandler(new DataHandler(source6));
      messageBodyPart6.setFileName("Floor5Route.png");
      multipart.addBodyPart(messageBodyPart6);

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
