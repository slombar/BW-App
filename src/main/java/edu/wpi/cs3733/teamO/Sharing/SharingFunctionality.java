package edu.wpi.cs3733.teamO.Sharing;

import static edu.wpi.cs3733.teamO.GraphSystem.Graph.GRAPH;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import edu.wpi.cs3733.teamO.GraphSystem.Graph;
import edu.wpi.cs3733.teamO.Model.Node;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.Map;
import javax.imageio.ImageIO;

public class SharingFunctionality {

  /**
   * Sends a MMS to given phone number with 6 map images
   *
   * @param sendingTo
   * @param linkToFile1
   * @param linkToFile2
   * @param linkToFile3
   * @param linkToFile4
   * @param linkToFile5
   * @param linkToFile6
   */
  public static void sendSMSTwillio(
      String sendingTo,
      String linkToFile1,
      String linkToFile2,
      String linkToFile3,
      String linkToFile4,
      String linkToFile5,
      String linkToFile6) {
    String ACCOUNT_SID = "ACccaa37332a0f79e457bfcb6f393b25e8";
    String AUTH_TOKEN = "29c1347315b388cb034d93e5aff4685f";

    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

    // did same thing as emails
    if (GRAPH.equals(null)) {
      Message message =
          Message.creator(
                  new com.twilio.type.PhoneNumber(
                      sendingTo), // if testing, please use verified number. will not work otherwise
                  new com.twilio.type.PhoneNumber("+16173560972"),
                  "Hello! Here are your map images!")
              .setMediaUrl(
                  Arrays.asList(
                      URI.create(linkToFile1),
                      URI.create(linkToFile2),
                      URI.create(linkToFile3),
                      URI.create(linkToFile4),
                      URI.create(linkToFile5),
                      URI.create(linkToFile6)))
              .create();

      System.out.println(message.getSid());
    } else {
      Graph graph = GRAPH;
      String pathFloors = "";
      for (Node n : graph.getPath()) {
        if (!pathFloors.contains(n.getFloor())) pathFloors += n.getFloor();
      }

      // check for campus, check for last thing
      String firstLast = pathFloors.substring(0, 1) + pathFloors.substring(pathFloors.length() - 1);
      String lastFloor = pathFloors.substring(pathFloors.length() - 1);
      System.out.println(firstLast);

      // this time, i made a list of the links we need since the constructor to make the text !=
      // allow
      // for concat
      LinkedList<String> toPrint = new LinkedList<String>();

      if (firstLast.contains("G")) {
        toPrint.add(linkToFile1);
        if (!lastFloor.contains("1")) toPrint.add(linkToFile2);
      }

      if (firstLast.contains("1")) toPrint.add(linkToFile2);
      if (firstLast.contains("2")) toPrint.add(linkToFile3);
      if (firstLast.contains("3")) toPrint.add(linkToFile4);
      if (firstLast.contains("4")) toPrint.add(linkToFile5);
      if (firstLast.contains("5")) toPrint.add(linkToFile6);
      System.out.println(toPrint.get(0));

      // check how many links we need to send, calls message constructor with that amount of URLs
      if (toPrint.size() == 1) {
        Message message =
            Message.creator(
                    new com.twilio.type.PhoneNumber(
                        sendingTo), // if testing, please use verified number. will not work
                    // otherwise
                    new com.twilio.type.PhoneNumber("+16173560972"),
                    "Hello! Here are your map images!")
                .setMediaUrl(Arrays.asList(URI.create(toPrint.get(0))))
                .create();
        System.out.println(message.getSid());

        System.out.println(message.getSid());

      } else if (toPrint.size() == 2) {
        Message message =
            Message.creator(
                    new com.twilio.type.PhoneNumber(
                        sendingTo), // if testing, please use verified number. will not work
                    // otherwise
                    new com.twilio.type.PhoneNumber("+16173560972"),
                    "Hello! Here are your map images!")
                .setMediaUrl(Arrays.asList(URI.create(toPrint.get(0)), URI.create(toPrint.get(1))))
                .create();
        System.out.println(message.getSid());

        System.out.println(message.getSid());

      } else if (toPrint.size() == 3) {
        Message message =
            Message.creator(
                    new com.twilio.type.PhoneNumber(
                        sendingTo), // if testing, please use verified number. will not work
                    // otherwise
                    new com.twilio.type.PhoneNumber("+16173560972"),
                    "Hello! Here are your map images!")
                .setMediaUrl(
                    Arrays.asList(
                        URI.create(toPrint.get(0)),
                        URI.create(toPrint.get(1)),
                        URI.create(toPrint.get(2))))
                .create();
        System.out.println(message.getSid());
        // if not 1,2,3, then just send all 6 maps for now

      }
    }
  }

  // working code between trying to only send necessary maps
  /*
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
                    sendingTo), // if testing, please use verified number. will not work otherwise
                new com.twilio.type.PhoneNumber("+16173560972"),
                "Hello! Here are your map images!")
            .setMediaUrl(
                Arrays.asList(
                    URI.create(linkToFile1),
                    URI.create(linkToFile2),
                    URI.create(linkToFile3),
                    URI.create(linkToFile4),
                    URI.create(linkToFile5),
                    URI.create(linkToFile6)))
            .create();

    System.out.println(message.getSid());
  }
   */

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

  public static void createQRCode(
      String fileToBeSent1,
      String fileToBeSent2,
      String fileToBeSent3,
      String fileToBeSent4,
      String fileToBeSent5,
      String fileToBeSent6) {
    QRCodeThreader qrThreader =
        new QRCodeThreader(
            fileToBeSent1,
            fileToBeSent2,
            fileToBeSent3,
            fileToBeSent4,
            fileToBeSent5,
            fileToBeSent6);
    qrThreader.start();
  }

  /**
   * Generates QR code with a given link
   *
   * @param link
   */
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
          "\nCongratulations.. You have successfully created QR Code.. \n"
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
  // !!! keeping to test when we figure out how to send only necessary maps
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

      // CHANGE COLOR OF QR HERE
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
          "\nCongratulations... You have successfully created QR Code.. \n"
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
