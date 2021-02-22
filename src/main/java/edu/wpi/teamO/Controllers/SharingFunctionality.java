package edu.wpi.teamO.Controllers;

import com.vonage.client.VonageClient;
import com.vonage.client.sms.MessageStatus;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.messages.TextMessage;
import java.io.*;
import java.util.*;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;

public class SharingFunctionality {

  // TODO:implement sending image to phone &  get a better service?
  public static void sendSMS(String sendingTo, String fileToBeSent) {

    VonageClient client =
        VonageClient.builder().apiKey("390a6808").apiSecret("vdFIoqy6XZ9nFRQn").build();

    TextMessage message =
        new TextMessage("18888571289", sendingTo, "A text message sent using the Vonage SMS API");

    SmsSubmissionResponse response = client.getSmsClient().submitMessage(message);

    if (response.getMessages().get(0).getStatus() == MessageStatus.OK) {
      System.out.println("Message sent successfully.");
    } else {
      System.out.println(
          "Message failed with error: " + response.getMessages().get(0).getErrorText());
    }
  }

  public static void sendEmailAttachment(String sendingTo, String fileToBeSent) {

    String to = sendingTo; // recipient's email
    String from = "bwappteamo@gmail.com"; // sender's email, need default sender email !
    String host = "smtp.gmail.com"; // where email is being sent from

    Properties properties = System.getProperties(); // set up mail server
    properties.put("mail.smtp.host", host);
    properties.put("mail.smtp.port", "465");
    properties.put("mail.smtp.ssl.enable", "true");
    properties.put("mail.smtp.auth", "true");

    String home = System.getProperty("user.home");
    File outputFile = new File(home + "/Downloads/" + "mapImageThingy.png");

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
      message.setSubject("This is the Subject Line!"); // Set Subject: header

      // PART 1 - BODY OF EMAIL ----------------------------------------------------------------
      BodyPart messageBodyPart = new MimeBodyPart();

      // Actual Message
      messageBodyPart.setText("This is message body");

      // Create a multipart message + set text message part
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
