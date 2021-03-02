package edu.wpi.cs3733.teamO.Sharing;

import com.google.api.client.util.Base64;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class TestImgur1 {

  private static String link1;
  private static String link2;
  private static String link3;
  private static String link4;
  private static String link5;
  private static String link6;
  private static String imageID;

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

  public static String uploadToImgur(String imageName) throws UnirestException {
    String home = System.getProperty("user.home");
    String file = home + "/Downloads/" + imageName;
    // String file = home + "/Downloads/" + "mapimg1.png";

    File f = new File("/C:/Users/Cindy/Downloads/mapimg1.png");
    String encodstring = encodeFileToBase64Binary(f);

    Unirest.setTimeouts(0, 0);
    HttpResponse<String> response =
        Unirest.post("https://api.imgur.com/3/upload")
            .header("Authorization", "Bearer 98aa9173f1f8f842e3e412945de0886797ba7b87")
            .field("image", new File(file))
            .asString();

    setImageID("");

    String res = response.getBody();

    System.out.println(response.getBody());
    String a = res.substring(res.indexOf("https"));
    return a.substring(0, a.indexOf("png") + 3);
  }

  public static String createImgurAlbum(
      String id1, String id2, String id3, String id4, String id5, String id6)
      throws UnirestException {

    Unirest.setTimeouts(0, 0);
    HttpResponse<String> response2 =
        Unirest.post("https://api.imgur.com/3/album")
            .header("Authorization", "Bearer 98aa9173f1f8f842e3e412945de0886797ba7b87")
            .field("ids[]", id1)
            .field("ids[]", id2)
            .field("ids[]", id3)
            .field("ids[]", id4)
            .field("ids[]", id5)
            .field("ids[]", id6)
            .field("title", "My Map Images")
            .asString();

    String res = response2.getBody();

    System.out.println(response2.getBody());
    return response2.getBody();

    // String a = res.substring(res.indexOf("https"));
    // a.substring(0, a.indexOf("png") + 3);
  }

  public static String parseID(String msgBody) {
    msgBody = msgBody.substring(msgBody.indexOf("'id':'"));
    msgBody = msgBody.substring(0, msgBody.indexOf("','"));
    return msgBody;
  }

  public static void main(String[] args) throws Exception {
    /*
        String id1 = parseID(uploadToImgur("mapimg1.png"));
        String id2 = parseID(uploadToImgur("mapimg2.png"));
        String id3 = parseID(uploadToImgur("mapimg3.png"));
        String id4 = parseID(uploadToImgur("mapimg4.png"));
        String id5 = parseID(uploadToImgur("mapimg5.png"));
        String id6 = parseID(uploadToImgur("mapimg6.png"));

        String hi = createImgurAlbum(id1, id2, id3, id4, id5, id6);
        System.out.println(hi);
    */

    Unirest.setTimeouts(0, 0);
    HttpResponse<String> response =
        Unirest.get("https://api.imgur.com/3/album/npcktIt/images")
            .header("Authorization", "Bearer 98aa9173f1f8f842e3e412945de0886797ba7b87")
            .asString();

    System.out.println(response.getBody());

    /*
       testing string
       String b =
           "{\"status\":200,\"success\":true,\"data\":{\"id\":\"ImYycv8\",\"deletehash\":\"626l0igShHmm0bE\",\"account_id\":146010003,\"account_url\":\"bwolive3733\",\"ad_type\":null,\"ad_url\":null,\"title\":null,\"description\":null,\"name\":\"\",\"type\":\"image/png\",\"width\":1288,\"height\":1059,\"size\":209967,\"views\":0,\"section\":null,\"vote\":null,\"bandwidth\":0,\"animated\":false,\"favorite\":false,\"in_gallery\":false,\"in_most_viral\":false,\"has_sound\":false,\"is_ad\":false,\"nsfw\":null,\"link\":\"https://i.imgur.com/ImYycv8.png\",\"tags\":[],\"datetime\":1614654552,\"mp4\":\"\",\"hls\":\"\"}}";

       String a = b.substring(b.indexOf("https"));
       System.out.print(a.substring(0, a.indexOf("png") + 3));
    */
  }

  public static String getLink1() {
    return link1;
  }

  public static String getLink2() {
    return link2;
  }

  public static String getLink3() {
    return link3;
  }

  public static String getLink4() {
    return link4;
  }

  public static String getLink5() {
    return link5;
  }

  public static String getLink6() {
    return link6;
  }

  public static void setLink1(String l) {
    link1 = l;
  }

  public static void setLink2(String l) {
    link2 = l;
  }

  public static void setLink3(String l) {
    link3 = l;
  }

  public static void setLink4(String l) {
    link4 = l;
  }

  public static void setLink5(String l) {
    link5 = l;
  }

  public static void setLink6(String l) {
    link6 = l;
  }

  public static void setImageID(String s) {
    imageID = s;
  }

  public static String getImageID() {
    return imageID;
  }
}
