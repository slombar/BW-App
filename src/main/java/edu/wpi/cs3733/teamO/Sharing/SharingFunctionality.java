package edu.wpi.cs3733.teamO.Sharing;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.vonage.client.VonageClient;
import com.vonage.client.sms.MessageStatus;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.messages.TextMessage;
import java.net.URI;
import java.util.Arrays;

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

  public static void sendSMSTwillio(String sendingTo, String linkToFile) {
    String ACCOUNT_SID = "ACccaa37332a0f79e457bfcb6f393b25e8";
    String AUTH_TOKEN = "98a818e03c58110dc0fbc752695d9e40";

    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    Message message =
        Message.creator(
                new com.twilio.type.PhoneNumber(sendingTo), //replace with cindy's number if testing
                new com.twilio.type.PhoneNumber("+16173560972"),
                "This is the ship that made the Kessel Run in fourteen parsecs?")
            .setMediaUrl(Arrays.asList(URI.create("https://i.imgur.com/7KpyjV4.mp4")))
            .create();


    // file:/C:/users/cindy/Downloads/mapimg.png

    System.out.println(message.getSid());
  }

  // check email threader for previous code
  public static void sendEmailAttachment(String sendingTo, String fileToBeSent) {
    EmailThreader emailThreader = new EmailThreader(sendingTo, fileToBeSent);
    emailThreader.start();
  }

  /* for testing purposes, change sendingTo param to cindy's number
  public static void main(String[] args) {
    sendSMSTwillio("hi", "bye");
  }
  */

}
