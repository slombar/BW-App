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

  // check email threader for previous code
  public static void sendEmailAttachment(String sendingTo, String fileToBeSent) {
    EmailThreader emailThreader = new EmailThreader(sendingTo, fileToBeSent);
    emailThreader.start();
  }
}
