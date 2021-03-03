package edu.wpi.cs3733.teamO.Sharing;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.vonage.client.VonageClient;
import com.vonage.client.sms.MessageStatus;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.messages.TextMessage;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import javax.imageio.ImageIO;

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

  public static void sendSMSTwillio(
      String sendingTo,
      String linkToFile1,
      String linkToFile2,
      String linkToFile3,
      String linkToFile4,
      String linkToFile5,
      String linkToFile6) {
    String ACCOUNT_SID = "ACccaa37332a0f79e457bfcb6f393b25e8";
    String AUTH_TOKEN = "98a818e03c58110dc0fbc752695d9e40";

    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    Message message =
        Message.creator(
                new com.twilio.type.PhoneNumber(
                    sendingTo), // replace with cindy's number if testing
                new com.twilio.type.PhoneNumber("+16173560972"),
                "This is the ship that made the Kessel Run in fourteen parsecs?")
            .setMediaUrl(
                Arrays.asList(
                    URI.create(linkToFile1),
                    URI.create(linkToFile2),
                    URI.create(linkToFile3),
                    URI.create(linkToFile4),
                    URI.create(linkToFile5),
                    URI.create(linkToFile6)))
            .create();

    // file:/C:/users/cindy/Downloads/mapimg.png

    System.out.println(message.getSid());
  }

  // check email threader for previous code
  public static void sendEmailAttachment(
      String sendingTo,
      String fileToBeSent1,
      String fileToBeSent2,
      String fileToBeSent3,
      String fileToBeSent4,
      String fileToBeSent5,
      String fileToBeSent6) {
    EmailThreader emailThreader =
        new EmailThreader(
            sendingTo,
            fileToBeSent1,
            fileToBeSent2,
            fileToBeSent3,
            fileToBeSent4,
            fileToBeSent5,
            fileToBeSent6);
    emailThreader.start();
  }

  // creates QR code in the downloads folder
  public static void createQR(String link) {

    String home = System.getProperty("user.home");
    File QRFile = new File(home + "/Downloads/" + "qr.png");
    String myCodeText = link;
    String filePath = home + "/Downloads/";
    int size = 512;
    String QRFileType = "png";
    try {

      Map<EncodeHintType, Object> QRHintType =
          new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
      QRHintType.put(EncodeHintType.CHARACTER_SET, "UTF-8");

      // Now with version 3.4.1 you could change margin (white border size)
      QRHintType.put(EncodeHintType.MARGIN, 1); /* default = 4 */
      Object put = QRHintType.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

      QRCodeWriter mYQRCodeWriter = new QRCodeWriter(); // throws com.google.zxing.WriterException
      BitMatrix QRBitMatrix =
          mYQRCodeWriter.encode(myCodeText, BarcodeFormat.QR_CODE, size, size, QRHintType);
      int QRWidth = QRBitMatrix.getWidth();

      // The BufferedImage subclass describes an Image with an accessible buffer of QRImage
      // data.
      BufferedImage QRImage = new BufferedImage(QRWidth, QRWidth, BufferedImage.TYPE_INT_RGB);

      // Creates a Graphics2D, which can be used to draw into this BufferedImage.
      QRImage.createGraphics();

      // This Graphics2D class extends the Graphics class to provide more sophisticated control over
      // geometry, coordinate transformations, color management, and text layout.
      // This is the fundamental class for rendering 2-dimensional shapes, text and images on the
      // Java(tm) platform.
      Graphics2D QRGraphics = (Graphics2D) QRImage.getGraphics();

      // setColor() sets this graphics context's current color to the specified color.
      // All subsequent graphics operations using this graphics context use this specified color.
      QRGraphics.setColor(Color.white);

      // fillRect() fills the specified rectangle. The left and right edges of the rectangle are at
      // x and x + width - 1.
      QRGraphics.fillRect(0, 0, QRWidth, QRWidth);

      QRGraphics.setColor(new Color(58, 83, 105));

      for (int i = 0; i < QRWidth; i++) {
        for (int j = 0; j < QRWidth; j++) {
          if (QRBitMatrix.get(i, j)) {
            QRGraphics.fillRect(i, j, 1, 1);
          }
        }
      }

      // A class containing static convenience methods for locating
      // ImageReaders and ImageWriters, and performing simple encoding and decoding.
      ImageIO.write(QRImage, QRFileType, QRFile);

      System.out.println(
          "\nCongratulation.. You have successfully created QR Code.. \n"
              + "Check your code here: "
              + filePath);
    } catch (WriterException e) {
      System.out.println("\nSorry.. Something went wrong...\n");
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // for testing purposes, change sendingTo param to cindy's number
  public static void main(String[] args) {
    // sendSMSTwillio("16176061459", "https://i.imgur.com/ImYycv8.png");

    String home = System.getProperty("user.home");
    File crunchifyFile = new File(home + "/Downloads/" + "qr.png");
    String myCodeText = "https://imgur.com/a/4GdeKiq";
    String filePath = home + "/Downloads/";
    int size = 512;
    String crunchifyFileType = "png";
    try {

      Map<EncodeHintType, Object> crunchifyHintType =
          new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
      crunchifyHintType.put(EncodeHintType.CHARACTER_SET, "UTF-8");

      // Now with version 3.4.1 you could change margin (white border size)
      crunchifyHintType.put(EncodeHintType.MARGIN, 1); /* default = 4 */
      Object put = crunchifyHintType.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

      QRCodeWriter mYQRCodeWriter = new QRCodeWriter(); // throws com.google.zxing.WriterException
      BitMatrix crunchifyBitMatrix =
          mYQRCodeWriter.encode(myCodeText, BarcodeFormat.QR_CODE, size, size, crunchifyHintType);
      int CrunchifyWidth = crunchifyBitMatrix.getWidth();

      // The BufferedImage subclass describes an Image with an accessible buffer of crunchifyImage
      // data.
      BufferedImage crunchifyImage =
          new BufferedImage(CrunchifyWidth, CrunchifyWidth, BufferedImage.TYPE_INT_RGB);

      // Creates a Graphics2D, which can be used to draw into this BufferedImage.
      crunchifyImage.createGraphics();

      // This Graphics2D class extends the Graphics class to provide more sophisticated control over
      // geometry, coordinate transformations, color management, and text layout.
      // This is the fundamental class for rendering 2-dimensional shapes, text and images on the
      // Java(tm) platform.
      Graphics2D crunchifyGraphics = (Graphics2D) crunchifyImage.getGraphics();

      // setColor() sets this graphics context's current color to the specified color.
      // All subsequent graphics operations using this graphics context use this specified color.
      crunchifyGraphics.setColor(Color.white);

      // fillRect() fills the specified rectangle. The left and right edges of the rectangle are at
      // x and x + width - 1.
      crunchifyGraphics.fillRect(0, 0, CrunchifyWidth, CrunchifyWidth);

      // TODO: Please change this color as per your need
      crunchifyGraphics.setColor(new Color(58, 83, 105));

      for (int i = 0; i < CrunchifyWidth; i++) {
        for (int j = 0; j < CrunchifyWidth; j++) {
          if (crunchifyBitMatrix.get(i, j)) {
            crunchifyGraphics.fillRect(i, j, 1, 1);
          }
        }
      }

      // A class containing static convenience methods for locating
      // ImageReaders and ImageWriters, and performing simple encoding and decoding.
      ImageIO.write(crunchifyImage, crunchifyFileType, crunchifyFile);

      System.out.println(
          "\nCongratulation.. You have successfully created QR Code.. \n"
              + "Check your code here: "
              + filePath);
    } catch (WriterException e) {
      System.out.println("\nSorry.. Something went wrong...\n");
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
