package edu.wpi.cs3733.teamO.Sharing;

import com.google.api.client.util.Base64;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import jdk.nashorn.internal.parser.Token;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class TestImgur1 {

  public static BufferedImage toBufferedImage(Image img) {
    if (img instanceof BufferedImage) {
      return (BufferedImage) img;
    }

    // Create a buffered image with transparency
    BufferedImage bimage = new BufferedImage(1059, 1288, BufferedImage.TYPE_INT_ARGB);

    // Draw the image on to the buffered image
    Graphics2D bGr = bimage.createGraphics();
    bGr.drawImage(img, 0, 0, null);
    bGr.dispose();

    // Return the buffered image
    return bimage;
  }

  private static String encodeFileToBase64Binary(File file) {
    String encodedfile = null;
    try {
      FileInputStream fileInputStreamReader = new FileInputStream(file);
      byte[] bytes = new byte[(int) file.length()];
      fileInputStreamReader.read(bytes);
      encodedfile = new String(Base64.encodeBase64(bytes), "UTF-8");
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return encodedfile;
  }

  public static void main(String[] args) throws Exception {

    String IMGUR_POST_URL = "https://api.imgur.com/3/upload.xml";
    String IMGUR_AUTHORIZE_URL = "https://api.imgur.com/3/account/bwolive3733";
    String IMGUR_CLIENT_ID = "546c25a59c58ad7";
    String IMGUR_CLIENT_SECRET = "ac0681fb73723d536f748647a96b7aa5c4120338";
    String IMGUR_USERNAME = "bwolive3733";

    String home = System.getProperty("user.home");
    String file = home + "/Downloads/" + "mapimg.png";

    // String stringToReverse = URLEncoder.encode(IMGUR_CLIENT_SECRET, "UTF-8");

    /*
    URL url = new URL(IMGUR_AUTHORIZE_URL);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("GET");
    connection.setRequestProperty("Authorization", "Client-ID " + IMGUR_CLIENT_ID);


      URL urlupload = new URL(IMGUR_POST_URL);
      HttpURLConnection conne = (HttpURLConnection) urlupload.openConnection();
      conne.setRequestMethod("POST");
      conne.setRequestProperty("Authorization", "Bearer 5eeae49394cd929e299785c8805bd168fc675280");
      System.out.println(conne.getHeaderField(1));
    */

    File f = new File("/C:/Users/Dimas/Pictures/amongus.png");
    String encodstring = encodeFileToBase64Binary(f);

    Unirest.setTimeouts(0, 0);
    HttpResponse<String> response =
        Unirest.post("https://api.imgur.com/3/upload")
            .header("Authorization", "Bearer 5eeae49394cd929e299785c8805bd168fc675280")
            .field("file", new File("C:/Users/Dimas/Downloads/Screenshot_2 (3).png"))
            .asString();

    System.out.println(response.getBody());

    /*
    BufferedReader bin = null;
    bin = new BufferedReader(new InputStreamReader(connection.getInputStream()));

    // below will print out bin
    String line;
    while ((line = bin.readLine()) != null) System.out.println(line);
    bin.close();
     */
  }
}
