package Controllers;

import java.util.*;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;

public class SharingFunctionality {

  // WORK IN PROGRESS
  public static void getEmail() {}

  public static void getMapImage() {}
  /**
   * Sends email with given attachment to given email address
   *
   * @param sendingTo
   * @param fileToBeSent
   */
  public static void sendEmailAttachment(String sendingTo, String fileToBeSent) {

    String to = sendingTo; // recipient's email
    String from = "lahoy1015@gmail.com"; // sender's email, need default sender email !
    String host = "localhost"; // where email is being sent from

    Properties properties = System.getProperties(); // set up mail server
    properties.put("mail.smtp.host", host);

    Session session = Session.getDefaultInstance(properties);

    try {

      MimeMessage message = new MimeMessage(session); // Create a default MimeMessage object

      // Setting Email Headers
      message.setFrom(new InternetAddress(from)); // Set From: header
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(to)); // Set To: header
      message.setSubject("This is the Subject Line!"); // Set Subject: header

      // PART 1 - BODY OF EMAIL ----------------------------------------------------------------
      BodyPart messageBodyPart = new MimeBodyPart();

      // Actual Message
      messageBodyPart.setText("This is message body");

      // Create a multipar message + set text message part
      Multipart multipart = new MimeMultipart();
      multipart.addBodyPart(messageBodyPart);

      // PART 2 - ATTACHMENT -------------------------------------------------------------------
      messageBodyPart = new MimeBodyPart();
      String filename = fileToBeSent; // file to be sent, map image
      DataSource source = new FileDataSource(filename);
      messageBodyPart.setDataHandler(new DataHandler(source));
      messageBodyPart.setFileName(filename);
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
