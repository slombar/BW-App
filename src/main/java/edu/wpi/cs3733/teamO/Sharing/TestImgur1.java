package edu.wpi.cs3733.teamO.Sharing;

import com.google.api.client.util.Base64;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
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
    String home = System.getProperty("user.home");
    String file = home + "/Downloads/" + "mapimg.png";

    File f = new File("/C:/Users/Cindy/Downloads/mapimg.png");
    String encodstring = encodeFileToBase64Binary(f);

    Unirest.setTimeouts(0, 0);
    HttpResponse<String> response =
        Unirest.post("https://api.imgur.com/3/upload")
            .header("Authorization", "Bearer 98aa9173f1f8f842e3e412945de0886797ba7b87")
            .field("image", new File("C:/Users/Cindy/Downloads/mapimg.png"))
            .asString();

    String res = response.getBody();

    System.out.println(response.getBody());

    /* testing string
    String b =
        "{\"status\":200,\"success\":true,\"data\":{\"id\":\"ImYycv8\",\"deletehash\":\"626l0igShHmm0bE\",\"account_id\":146010003,\"account_url\":\"bwolive3733\",\"ad_type\":null,\"ad_url\":null,\"title\":null,\"description\":null,\"name\":\"\",\"type\":\"image/png\",\"width\":1288,\"height\":1059,\"size\":209967,\"views\":0,\"section\":null,\"vote\":null,\"bandwidth\":0,\"animated\":false,\"favorite\":false,\"in_gallery\":false,\"in_most_viral\":false,\"has_sound\":false,\"is_ad\":false,\"nsfw\":null,\"link\":\"https://i.imgur.com/ImYycv8.png\",\"tags\":[],\"datetime\":1614654552,\"mp4\":\"\",\"hls\":\"\"}}";
    String a = b.substring(b.indexOf("https"));
    System.out.print(a.substring(0, a.indexOf("png") + 3));
    */
  }
}
